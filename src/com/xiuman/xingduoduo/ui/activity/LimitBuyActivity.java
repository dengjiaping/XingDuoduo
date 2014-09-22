package com.xiuman.xingduoduo.ui.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.LimitBuyGiftListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskCenterClassifyGoodsBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshScrollView;

/**
 * @名称：LimitBuyActivity.java
 * @描述：限时抢购
 * @author danding 2014-9-17
 */
public class LimitBuyActivity extends Base2Activity implements OnClickListener {

	/*----------------------------------组件------------------------------------*/
	// 返回
	private Button btn_back;
	// 标题栏
	private TextView tv_title;
	// 右侧
	private Button btn_right;

	// 下拉刷新sv
	private PullToRefreshScrollView pullsv_limitbuy;
	// 可刷新的sv
	private ScrollView sv_limitbuy;
	// ListView
	private ListView lv_limitbuy_container;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 商品为空为空时显示的布局
	private LinearLayout llyt_null_goods;
	// 夹在数据时显示的Dialog
	private LoadingDialog loadingdialog;
	//倒计时
	private TextView tv_center_daojishi;

	/*-------------------------------------ImageLoader-------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*----------------------------------标记----------------------------------*/
	// 是上拉还是下拉
	private boolean isUp = true;

	/*----------------------------------数据---------------------------------*/
	// 接收到的分类名，测试数据
	private String classify_name;
	// 接收到的分类地址后缀
	private String classify_url;
	// 请求接口得到的商品数据
	private ActionValue<GoodsOne> value;
	// （商品列表）
	private ArrayList<GoodsOne> goods_get = new ArrayList<GoodsOne>();
	// 当前现实的商品列表
	private ArrayList<GoodsOne> goods_current = new ArrayList<GoodsOne>();
	// 当前页
	private int currentPage = 1;
	private LimitBuyGiftListViewAdapter adapter;

	// 消息处理Handler
	@SuppressLint("HandlerLeak")
	@SuppressWarnings("unchecked")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 获取数据成功
				value = (ActionValue<GoodsOne>) msg.obj;

				goods_get = (ArrayList<GoodsOne>) value.getDatasource();
				if (!value.isSuccess()) {
					llyt_null_goods.setVisibility(View.VISIBLE);
				} else {
					if (isUp) {// 下拉
						goods_current = goods_get;
						adapter = new LimitBuyGiftListViewAdapter(
								LimitBuyActivity.this, goods_current, options,
								imageLoader);
						// 下拉加载完成
						pullsv_limitbuy.onPullDownRefreshComplete();
						lv_limitbuy_container.setAdapter(adapter);
					} else {// 上拉
						goods_current.addAll(goods_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pullsv_limitbuy.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value.getTotalpage()) {
							// pullsv_limitbuy
							// .setHasMoreData(true);
						} else {
							// pullsv_limitbuy
							// .setHasMoreData(false);
						}
					}
					TimeUtil.setLastUpdateTime3(pullsv_limitbuy);
					
					llyt_null_goods.setVisibility(View.INVISIBLE);
				}
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				llyt_network_error.setVisibility(View.VISIBLE);
				llyt_null_goods.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_limitby);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		options = new DisplayImageOptions.Builder()
		// .showStubImage(R.drawable.weiboitem_pic_loading) //
		// 在ImageView加载过程中显示图片
				.showImageForEmptyUri(R.drawable.onloading_goods_poster) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading_goods_poster) // image加载失败
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.imageScaleType(ImageScaleType.NONE).build();

		// 获取Intent传递的数据
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		classify_name = bundle.getString("classify_name");
		classify_url = bundle.getString("classify_url");
		currentPage = 1;

	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		loadingdialog = new LoadingDialog(this);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_null_goods = (LinearLayout) findViewById(R.id.llyt_goods_null);
		

		pullsv_limitbuy = (PullToRefreshScrollView) findViewById(R.id.pullsv_limtbuy);
		pullsv_limitbuy.setScrollLoadEnabled(true);
		pullsv_limitbuy.setPullLoadEnabled(true);
		sv_limitbuy = pullsv_limitbuy.getRefreshableView();

		View view = View.inflate(this, R.layout.include_limitbuy_container,
				null);
		lv_limitbuy_container = (ListView) view
				.findViewById(R.id.lv_limitbuy_container);
		tv_center_daojishi = (TextView) view.findViewById(R.id.tv_center_daojishi);

		sv_limitbuy.addView(view);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(classify_name);

		// 加载数据，测试数据，添加操作
		initFirstData(currentPage);
		// 设置刷新时间
		TimeUtil.setLastUpdateTime3(pullsv_limitbuy);
		//设置倒计时
		startDaojishi();
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);

		// 下拉刷新，上拉加载
		pullsv_limitbuy
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						isUp = true;
						currentPage = 1;
						initFirstData(currentPage);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						isUp = false;
						if (value.getPage() < value.getTotalpage()) {
							currentPage += 1;
							initFirstData(currentPage);
						} else {
							ToastUtil.ToastView(LimitBuyActivity.this,
									getResources().getString(R.string.no_more));
							// 下拉加载完成
							pullsv_limitbuy.onPullDownRefreshComplete();
							// 上拉刷新完成
							pullsv_limitbuy.onPullUpRefreshComplete();
							// 设置是否有更多的数据
							// pullsv_limitbuy
							// .setHasMoreData(false);
						}
					}
				});

		lv_limitbuy_container.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_limitbuy_container
						.getItemAtPosition(position);
				// 将商品数据传递给
				if (obj instanceof GoodsOne) {
					GoodsOne goods_one = (GoodsOne) obj;
					Intent intent = new Intent(
							LimitBuyActivity.this,
							GoodsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("goods_one", goods_one);
					bundle.putSerializable("goods_id",
							goods_one.getId());
					bundle.putInt("pic_tag", 1);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;

		default:
			break;
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage) {
		HttpUrlProvider.getIntance().getCenterClassifyGoods(
				LimitBuyActivity.this,
				new TaskCenterClassifyGoodsBack(handler),
				URLConfig.CENTER_HOME_PLATE, currentPage, classify_url);
		loadingdialog.show();
	}
	
	/*------------------------------------倒计时-------------------------------*/
	private Calendar mDate2;
	private int mYear, mMonth, mDay;
	private int mHour, mMinute;
	private String date;
	private Handler mHandler = new Handler();// 全局handler
	int time = 0;// 时间差
	private void updateDateTime() {
		mDate2 = Calendar.getInstance();
		mYear = mDate2.get(Calendar.YEAR);
		mMonth = mDate2.get(Calendar.MONTH);
		mDay = mDate2.get(Calendar.DAY_OF_MONTH);
		mHour = mDate2.get(Calendar.HOUR_OF_DAY);
		mMinute = mDate2.get(Calendar.MINUTE);

		date = mYear + "-" + (getDateFormat(mMonth + 1)) + "-"
				+ getDateFormat(mDay) + " " + 24 + ":"
				+ "00" + ":00";
//		if(mHour>=17){
//			date = mYear + "-" + (getDateFormat(mMonth + 1)) + "-"
//					+ getDateFormat(mDay+1) + " " + 17 + ":"
//					+ "00" + ":00";
//		}

	}

	public String getDateFormat(int time) {
		String date = time + "";
		if (time < 10) {
			date = "0" + date;
		}
		return date;
	}
	class TimeCount implements Runnable
	{
		@Override
		public void run()
		{
			while (time > 0)// 整个倒计时执行的循环
			{
				time--;
				mHandler.post(new Runnable() // 通过它在UI主线程中修改显示的剩余时间
				{
					public void run()
					{
						tv_center_daojishi.setText(getInterval(time));// 显示剩余时间
					}
				});
				try
				{
					Thread.sleep(1000);// 线程休眠一秒钟 这个就是倒计时的间隔时间
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			// 下面是倒计时结束逻辑
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					tv_center_daojishi.setText("设定的时间到。");
				}
			});
		}
	}
	/**
	 * 设定显示文字
	 */
	public static String getInterval(int time)
	{
		String txt = null;
		if (time >= 0)
		{
			long hour = time % (24 * 3600) / 3600;// 小时
			long minute = time % 3600 / 60;// 分钟
			long second = time % 60;// 秒
			
			txt = hour + "小时" + minute + "分" + second + "秒";
		} 
		else
		{
			txt="已过期";
		}
		return txt;
	}
	
	private void startDaojishi(){
		updateDateTime();
		time = getTimeInterval(date);
		new Thread(new TimeCount()).start();// 开启线程
	}
	/**
	 * 获取两个日期的时间差
	 */
	public static int getTimeInterval(String data)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int interval = 0;
		try
		{
			Date currentTime = new Date();// 获取现在的时间
			Date beginTime = dateFormat.parse(data);
			interval = (int) ((beginTime.getTime() - currentTime.getTime()) / (1000));// 时间差 单位秒
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return interval;
	}
}
