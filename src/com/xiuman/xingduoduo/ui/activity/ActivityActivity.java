package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.xiuman.xingduoduo.view.pulltorefresh.PullToRefreshListView;

/**
 * @名称：LiPinActivity.java
 * @描述：礼品专区(活动专区)
 * @author danding 2014-9-17
 */
public class ActivityActivity extends Base2Activity implements OnClickListener {

	/*---------------------------组件------------------------*/
	// 返回
	private Button btn_back;
	// 右侧
	private Button btn_right;
	// 标题栏
	private TextView tv_title;

	// 可下拉刷新ListView
	private PullToRefreshListView pulllv_lipin;
	// 可刷新的listVeiw
	private ListView lv_lipin;
	// 网络连接失败显示的布局
	private LinearLayout llyt_network_error;
	// 商品为空为空时显示的布局
	private LinearLayout llyt_null_goods;
	// 夹在数据时显示的Dialog
	private LoadingDialog loadingdialog;

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
	private ActionValue<GoodsOne> value = new ActionValue<GoodsOne>();
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
								ActivityActivity.this, goods_current, options,
								imageLoader);
						// 下拉加载完成
						pulllv_lipin.onPullDownRefreshComplete();
						lv_lipin.setAdapter(adapter);
					} else {// 上拉
						goods_current.addAll(goods_get);
						adapter.notifyDataSetChanged();

						// 上拉刷新完成
						pulllv_lipin.onPullUpRefreshComplete();
						// 设置是否有更多的数据
						if (currentPage < value.getTotalpage()) {
							pulllv_lipin.setHasMoreData(true);
						} else {
							pulllv_lipin.setHasMoreData(false);
						}
					}
					TimeUtil.setLastUpdateTime(pulllv_lipin);

					llyt_null_goods.setVisibility(View.INVISIBLE);
				}
				loadingdialog.dismiss(ActivityActivity.this);
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(ActivityActivity.this);
				llyt_network_error.setVisibility(View.VISIBLE);
				llyt_null_goods.setVisibility(View.INVISIBLE);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lipin);
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
				.showImageOnLoading(R.drawable.onloading)
				.showImageForEmptyUri(R.drawable.onloading)
				// image连接地址为空时
				.showImageOnFail(R.drawable.onloading)
				// image加载失败
				.cacheInMemory(false)
				// 加载图片时会在内存中加载缓存
				.cacheOnDisc(true)
				// 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
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

		pulllv_lipin = (PullToRefreshListView) findViewById(R.id.pulllv_lipin);
		pulllv_lipin.setPullLoadEnabled(false);
		pulllv_lipin.setPullRefreshEnabled(false);

		lv_lipin = pulllv_lipin.getRefreshableView();
		lv_lipin.setDividerHeight(1);
		lv_lipin.setDivider(new ColorDrawable(android.R.color.transparent));
		lv_lipin.setSelector(R.drawable.whole_bg_normal_selector);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(classify_name);
		llyt_network_error.setOnClickListener(this);
		// 加载数据，测试数据，添加操作
		initFirstData(currentPage);
		// 设置刷新时间
		TimeUtil.setLastUpdateTime(pulllv_lipin);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);

		pulllv_lipin.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = true;
				currentPage = 1;
				initFirstData(currentPage);
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				isUp = false;
				if (value.getPage() < value.getTotalpage()) {
					currentPage += 1;
					initFirstData(currentPage);
				} else {
					ToastUtil.ToastView(ActivityActivity.this, getResources()
							.getString(R.string.no_more));
					// 下拉加载完成
					pulllv_lipin.onPullDownRefreshComplete();
					// 上拉刷新完成
					pulllv_lipin.onPullUpRefreshComplete();
					// 设置是否有更多的数据
					pulllv_lipin.setHasMoreData(false);
				}
			}
		});

		lv_lipin.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object obj = lv_lipin.getItemAtPosition(position);
				// 将商品数据传递给
				if (obj instanceof GoodsOne) {
					GoodsOne goods_one = (GoodsOne) obj;
					Intent intent = new Intent(ActivityActivity.this,
							GoodsActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("goods_one", goods_one);
					bundle.putSerializable("goods_id", goods_one.getId());
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(
							R.anim.translate_horizontal_start_in,
							R.anim.translate_horizontal_start_out);
				}
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(loadingdialog==null){
			loadingdialog = new LoadingDialog(this);
		}
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
		case R.id.llyt_network_error:// 重新加载
			currentPage = 1;
			isUp = true;
			initFirstData(currentPage);
			break;
		}
	}

	/**
	 * @描述：加载数据(首次加载)--测试数据，添加操作
	 * @date：2014-6-25
	 */
	private void initFirstData(int currentPage) {
		HttpUrlProvider.getIntance().getCenterClassifyGoods(
				ActivityActivity.this,
				new TaskCenterClassifyGoodsBack(handler),
				URLConfig.CENTER_HOME_PLATE, currentPage, classify_url);
		loadingdialog.show(ActivityActivity.this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(ActivityActivity.this);
		loadingdialog = null;
		imageLoader.stop();
		imageLoader.clearMemoryCache();
	}
}
