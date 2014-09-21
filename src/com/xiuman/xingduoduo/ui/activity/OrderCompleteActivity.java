package com.xiuman.xingduoduo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.android.app.sdk.AliPay;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.Mylog;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskAlipayBack;
import com.xiuman.xingduoduo.callback.TaskSendAliPayStatusCodeBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.ActionValuePay;
import com.xiuman.xingduoduo.model.AliPayStatus;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.pay.alipay.Result;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * @名称：OrderCompleteActivity.java
 * @描述：订单生成成功界面
 * @author danding 2014-9-19
 */
public class OrderCompleteActivity extends Base2Activity implements
		OnClickListener {

	/*------------------------------组件--------------------------------*/
	// 返回
	private Button btn_back;
	// 标题栏
	private TextView tv_title;
	// 右侧
	private Button btn_right;
	// 订单支付状态
	private TextView tv_order_pay_status;
	// 订单图片
	private ImageView iv_order_complete_poster;
	// 订单总额
	private TextView tv_order_complete_total;
	// 订单商品数量
	private TextView tv_order_complete_quanity;
	// 去支付（已支付则隐藏，货到付款也隐藏）
	private Button btn_order_complete_go2pay;
	// 查看订单详情
	private Button btn_order_complete_order_info;
	// 进度加载
	private LoadingDialog loadingdialog;

	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*-----------------------------------接收数据-------------------------------*/
	//付款方式
	private int pay_tag;
	//订单号
	private String orderId;
	//订单总额
	private String goods_total;
	//订单商品数量
	private String goods_number;
	//支付宝状态返回
	private ActionValue<AliPayStatus> get_pay_status;
	//订单poster
	private String order_poster;
	
	

	/*----------------------------去支付----------------------------*/
	// 请求支付宝私钥
	private ActionValuePay value_alipay;
	// 传递支付结果
	private ActionValue<AliPayStatus> value_pay_status;

	// 支付宝线程返回 值类型
	private static final int RQF_PAY = 1;
	// 支付宝签名字串
	private String alipaySign;

	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_ERROR_NOTNET:// 网络连接失败

				loadingdialog.dismiss();
				break;
			case AppConfig.ALIPAY_BACK:
				value_alipay = (ActionValuePay) msg.obj;
				if (value_alipay.isSuccess()) {

					alipaySign = value_alipay.getDatasource();
					submitAlipayOrder();

				} else {
					ToastUtil.ToastView(OrderCompleteActivity.this,
							value_alipay.getMessage());
				}
				loadingdialog.dismiss();

				break;
			case RQF_PAY:// 支付结果返回

				Result result = new Result((String) msg.obj);
				result.parseResult();
				if (result.rs.equals("9000") && result.isSignOk) {
					ToastUtil.ToastView(OrderCompleteActivity.this, "支付成功");
				} else {
					ToastUtil.ToastView(OrderCompleteActivity.this,
							result.getResult());
				}
				// 将支付结果传递到后台
				HttpUrlProvider.getIntance().sendPayStatusCode(
						OrderCompleteActivity.this,
						new TaskSendAliPayStatusCodeBack(handler),
						URLConfig.PAY_STATUS_CODE, orderId, result.rs);
				break;
			case AppConfig.SEND_STATUS_CODE:// 获取传递支付结果到后台的返回值
				value_pay_status = (ActionValue<AliPayStatus>) msg.obj;
				ToastUtil.ToastView(OrderCompleteActivity.this,
						value_pay_status.getMessage());
				if (!value_pay_status.isSuccess()) {

				} else {
					get_pay_status = value_pay_status;
					setOrderCompleteInfo();
				}

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_complete);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
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

		Bundle bundle = getIntent().getExtras();
		//付款方式
		pay_tag = bundle.getInt("pay_tag");
		get_pay_status = (ActionValue<AliPayStatus>) bundle.getSerializable("AliPayStatus");
		goods_number = bundle.getString("goods_number");
		goods_total = bundle.getString("goods_total");
		order_poster = bundle.getString("order_poster");
		orderId = bundle.getString("orderId");
	
		System.out.println("订单id"+orderId);
		
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		btn_order_complete_go2pay = (Button) findViewById(R.id.btn_order_complete_go2pay);
		btn_order_complete_order_info = (Button) findViewById(R.id.btn_order_complete_order_info);
		iv_order_complete_poster = (ImageView) findViewById(R.id.iv_order_complete_poster);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		tv_order_pay_status = (TextView) findViewById(R.id.tv_order_pay_status);
		tv_order_complete_total = (TextView) findViewById(R.id.tv_order_complete_total);
		tv_order_complete_quanity = (TextView) findViewById(R.id.tv_order_complete_quanity);

	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText("订单提醒");
		
		//设置订单信息以及状态
		setOrderCompleteInfo();
		
		//加载图片
		imageLoader.displayImage(URLConfig.IMG_IP+order_poster, iv_order_complete_poster, options);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_order_complete_go2pay.setOnClickListener(this);
		btn_order_complete_order_info.setOnClickListener(this);
	}

	/**
	 * @描述：设置界面信息
	 * 2014-9-19
	 */
	private void setOrderCompleteInfo(){
		if(pay_tag==0){//支付宝
			orderId = get_pay_status.getDatasource().get(0).getOrderId();
			if(get_pay_status.isSuccess()){
				btn_order_complete_go2pay.setVisibility(View.INVISIBLE);
				tv_order_pay_status.setText("您已支付成功，商品整装待发...");
			}else{
				btn_order_complete_go2pay.setVisibility(View.VISIBLE);
				tv_order_pay_status.setText("您的订单已生成，请及时支付...");
			}
			
			
		}else if(pay_tag==1){//货到付款
			
			tv_order_pay_status.setText("您的订单已生成，商品整装待发...");
			btn_order_complete_go2pay.setVisibility(View.INVISIBLE);
		}
		tv_order_complete_total.setText("总价：￥"+goods_total);
		tv_order_complete_quanity.setText("商品数量："+goods_number);
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
		case R.id.btn_order_complete_go2pay:// 未支付时显示该按钮让用户去支付
			getAliPayParams();
			break;
		case R.id.btn_order_complete_order_info:// 产看订购单详情
			Intent intent = new Intent(this, OrderInfoActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("order_id", orderId);
			intent.putExtras(bundle);
			startActivity(intent);
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		}

	}

	/**
	 * @描述：请求获取支付参数 2014-9-19
	 */
	private void getAliPayParams() {
		// 请求获取支付参数
		HttpUrlProvider.getIntance().getAlipay(this,
				new TaskAlipayBack(handler), URLConfig.ALIPAY_URL, orderId);
		loadingdialog.show();
	}

	/**
	 * @描述：提交到支付宝 2014-8-26
	 */
	private void submitAlipayOrder() {

		try {

			final String orderInfo = alipaySign;
			Mylog.i("支付宝发送签名", "test = " + orderInfo);
			new Thread() {
				public void run() {
					AliPay alipay = new AliPay(OrderCompleteActivity.this,
							handler);

					// 设置为沙箱模式，不设置默认为线上环境
					// alipay.setSandBox(true);

					String result = alipay.pay(orderInfo);

					Message msg = new Message();
					msg.what = RQF_PAY;
					msg.obj = result;
					handler.sendMessage(msg);
				}
			}.start();

		} catch (Exception ex) {
			ex.printStackTrace();
			ToastUtil.ToastView(OrderCompleteActivity.this, "支付宝调用失败");
		}

	}
}
