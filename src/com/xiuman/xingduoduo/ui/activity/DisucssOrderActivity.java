package com.xiuman.xingduoduo.ui.activity;

import u.aly.T;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.DiscussOrderListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskDiscussGoodsBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Order;
import com.xiuman.xingduoduo.model.OrderDiscuss;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * @名称：DisucssOrderActivity.java
 * @描述：评价订单
 * @author danding 2014-9-20
 */
public class DisucssOrderActivity extends Base2Activity implements
		OnClickListener {

	/*-----------------------------------组件------------------------------*/
	// 返回
	private Button btn_back;
	// 标题
	private TextView tv_title;
	// 右侧
	private Button btn_right;
	// 订单创建日期
	private TextView tv_discuss_order_time;
	// 商品列表
	private ListView lv_discuss_order_goods_list;
	// 提交评价
	private Button btn_submit_discuss;
	// 进度
	private LoadingDialog loadingdialog;

	// 当前登录的用户
	private User user;

	// -------------Adapter------------------------
	// 商品列表
	private DiscussOrderListViewAdapter adapter;

	// -----------------------ImageLoader-----------------------------
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*------------------------------数据----------------------------------*/
	// 上级界面传递过来的订单信息
	private Order order;

	// 评价成功数
	private int success_number = 0;

	// 返回值
	private ActionValue<?> value_discuss = new ActionValue<T>();

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:
				value_discuss = (ActionValue<?>) msg.obj;
				success_number++;
				if (success_number == order.getProductDetail().size()) {
					ToastUtil.ToastView(DisucssOrderActivity.this,
							value_discuss.getMessage());
					setResult(AppConfig.RESULT_CODE_OK);
					finish();
					overridePendingTransition(
							R.anim.translate_horizontal_finish_in,
							R.anim.translate_horizontal_finish_out);
				}
				loadingdialog.dismiss(DisucssOrderActivity.this);
				break;
			case AppConfig.NET_ERROR_NOTNET://
				loadingdialog.dismiss(DisucssOrderActivity.this);
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss_order);
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
				.showImageForEmptyUri(R.drawable.onloading) // image连接地址为空时
				.showImageOnFail(R.drawable.onloading) // image加载失败
				.cacheInMemory(false) // 加载图片时会在内存中加载缓存
				.cacheOnDisc(true) // 加载图片时会在磁盘中加载缓存
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.NONE).build();

		order = (Order) getIntent().getExtras().getSerializable("order");
		user = MyApplication.getInstance().getUserInfo();
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		tv_discuss_order_time = (TextView) findViewById(R.id.tv_discuss_order_time);
		lv_discuss_order_goods_list = (ListView) findViewById(R.id.lv_discuss_order_goods_list);
		btn_submit_discuss = (Button) findViewById(R.id.btn_submit_discuss);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText("发表评价");

		tv_discuss_order_time
				.setText("成交时间："
						+ order
								.getCreate_date());

		adapter = new DiscussOrderListViewAdapter(this,
				order.getProductDetail(), options, imageLoader);
		lv_discuss_order_goods_list.setAdapter(adapter);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_submit_discuss.setOnClickListener(this);
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
		case R.id.btn_submit_discuss:// 提交评论
			if (adapter.getDiscuss_contents().size() > 0) {
				loadingdialog.show(DisucssOrderActivity.this);
				for (int i = 0; i <adapter.getDiscuss_contents().size(); i++) {
					OrderDiscuss discuss = adapter.getDiscuss_contents().get(i);
					
					HttpUrlProvider.getIntance().getDisCussGoods(this,
							new TaskDiscussGoodsBack(handler),
							URLConfig.DISCUSS_GOODS, user.getUserId(),
							discuss.getId(),discuss.getContent(),discuss.getGoodsQuality(),discuss.getServiceAttitude(),discuss.getDeliverySpeed());
				}
			} else {
				ToastUtil.ToastView(DisucssOrderActivity.this, "您还没有输入任何评论哦！");
			}
			
			break;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(DisucssOrderActivity.this);
		loadingdialog = null;
		imageLoader.stop();
	}
}

