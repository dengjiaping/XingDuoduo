package com.xiuman.xingduoduo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alipay.android.app.sdk.AliPay;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.OrderInfoListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.Mylog;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskAlipayBack;
import com.xiuman.xingduoduo.callback.TaskCancelOrderBack;
import com.xiuman.xingduoduo.callback.TaskDeleteOrderBack;
import com.xiuman.xingduoduo.callback.TaskOrderInfoBack;
import com.xiuman.xingduoduo.callback.TaskSendAliPayStatusCodeBack;
import com.xiuman.xingduoduo.callback.TaskTakeOrderBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.ActionValuePay;
import com.xiuman.xingduoduo.model.AliPayStatus;
import com.xiuman.xingduoduo.model.Order;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.pay.alipay.Result;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.TimeUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CustomDialog;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * @名称：OrderInfoActivity.java
 * @描述：订单详情界面
 * @author danding 2014-8-5
 */
public class OrderInfoActivity extends Base2Activity implements OnClickListener {

	/*---------------------------------------组件----------------------------------------*/
	// 返回
	private Button btn_back;
	// 右侧
	private Button btn_right;
	// 标题栏
	private TextView tv_title;
	// 支付方式
	private TextView tv_order_info_payway;
	// 订单状态
	private TextView tv_order_info_order_state;
	// 收货人姓名
	private TextView tv_order_info_taker_name;
	// 电话
	private TextView tv_order_info_taker_phone;
	// 详细地址
	private TextView tv_order_info_address_detail;
	// 买家留言
	private LinearLayout llyt_order_info_order_words;
	private TextView tv_order_info_order_words;
	// 订单号
	private TextView tv_order_info_id;
	// 创建时间
	private TextView tv_order_info_time;
	// 商品列表
	private ListView lv_order_info_goods_list;
	// 运费
	private TextView tv_order_info_trans_pay;
	// 优惠
	private TextView tv_order_info_preferential;
	// 订单总金额
	private TextView tv_order_info_total;
	// 已付款
	private TextView tv_order_info_pay;
	// 删除订单
	private Button btn_order_info_delte;
	// 确认收货
	private Button btn_order_info_take_order;

	// 删除订单弹出对话框
	private CustomDialog dialog_delete;
	// 删除进度
	private LoadingDialog loadingdialog;
	// 无网络
	private LinearLayout llyt_network_error;
	// 订单信息
	private ScrollView sv_order_info;
	// 订单底部菜单
	private LinearLayout llyt_order_info_bottom;

	// -----------------------ImageLoader-----------------------------
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*-----------------------------------数据-------------------------------*/
	// 从上级界面接收到的订单id
	private String order_id;
	// 请求接口获取到的订单详情
	private Order order;

	/*---------------------------------Adapter------------------------------*/
	private OrderInfoListViewAdapter adapter;

	/*------------------------------请求数据------------------------------*/
	// 详情返回结果
	private ActionValue<Order> value_order;
	// 删除结果
	private ActionValue<?> value_delete;
	// 取消结果
	private ActionValue<?> value_cancel;

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
			case AppConfig.NET_SUCCED:// 请求订单成功
				value_order = (ActionValue<Order>) msg.obj;
				if (value_order.isSuccess()) {
					order = value_order.getDatasource().get(0);
					setOrderInfo();
				} else {
					ToastUtil.ToastView(OrderInfoActivity.this,
							value_order.getMessage());
					finish();
				}
				llyt_network_error.setVisibility(View.INVISIBLE);
				sv_order_info.setVisibility(View.VISIBLE);
				llyt_order_info_bottom.setVisibility(View.VISIBLE);
				loadingdialog.dismiss();
				break;
			case AppConfig.UPDATE_ORDER:// 删除结果
				value_delete = (ActionValue<?>) msg.obj;
				if (value_delete.isSuccess()) {// 删除成功
					ToastUtil.ToastView(OrderInfoActivity.this,
							value_delete.getMessage());
					setResult(AppConfig.RESULT_CODE_OK);
					finish();
				} else {// 删除失败
					ToastUtil.ToastView(OrderInfoActivity.this,
							value_delete.getMessage());
				}
				loadingdialog.dismiss();
				break;
			case AppConfig.CANCEL_ORDER:// 取消订单
				value_cancel = (ActionValue<?>) msg.obj;
				if(value_cancel.isSuccess()){
					ToastUtil.ToastView(OrderInfoActivity.this,
							value_cancel.getMessage());
					setResult(AppConfig.RESULT_CODE_OK_2);
					getOrderInfo();
				} else {// 取消失败
					ToastUtil.ToastView(OrderInfoActivity.this,
							value_cancel.getMessage());
				}
				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				llyt_network_error.setVisibility(View.VISIBLE);
				sv_order_info.setVisibility(View.INVISIBLE);
				llyt_order_info_bottom.setVisibility(View.INVISIBLE);
				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_COMMON:// 删除网络出错
				loadingdialog.dismiss();
				break;
			case AppConfig.ALIPAY_BACK:
				value_alipay = (ActionValuePay) msg.obj;
				if (value_alipay.isSuccess()) {

					alipaySign = value_alipay.getDatasource();
					submitAlipayOrder();

				} else {
					ToastUtil.ToastView(OrderInfoActivity.this,
							value_alipay.getMessage());
				}
				loadingdialog.dismiss();

				break;
			case RQF_PAY:// 支付结果返回

				Result result = new Result((String) msg.obj);
				result.parseResult();
				if (result.rs.equals("9000") && result.isSignOk) {
					ToastUtil.ToastView(OrderInfoActivity.this, "支付成功");
				} else {
					ToastUtil.ToastView(OrderInfoActivity.this,
							result.getResult());
				}
				// 将支付结果传递到后台
				HttpUrlProvider.getIntance().sendPayStatusCode(
						OrderInfoActivity.this,
						new TaskSendAliPayStatusCodeBack(handler),
						URLConfig.PAY_STATUS_CODE, order_id, result.rs);
				break;
			case AppConfig.SEND_STATUS_CODE:// 获取传递支付结果到后台的返回值
				value_pay_status = (ActionValue<AliPayStatus>) msg.obj;
				ToastUtil.ToastView(OrderInfoActivity.this,
						value_pay_status.getMessage());
				if (!value_pay_status.isSuccess()) {

				} else {
					getOrderInfo();
					setResult(AppConfig.RESULT_CODE_OK_2);
				}

				break;
			case AppConfig.TAKE_ORDER:// 确认收货
				getOrderInfo();

				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_info);
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

		order_id = getIntent().getExtras().getString("order_id");
		// 设置默认返回值cancel(列表界面是否刷新)
		setResult(AppConfig.RESULT_CODE_CANCEL);
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		tv_order_info_payway = (TextView) findViewById(R.id.tv_order_info_payway);
		tv_order_info_order_state = (TextView) findViewById(R.id.tv_order_info_order_state);
		tv_order_info_taker_name = (TextView) findViewById(R.id.tv_order_info_taker_name);
		tv_order_info_taker_phone = (TextView) findViewById(R.id.tv_order_info_taker_phone);
		tv_order_info_address_detail = (TextView) findViewById(R.id.tv_order_info_address_detail);
		llyt_order_info_order_words = (LinearLayout) findViewById(R.id.llyt_order_info_order_words);
		tv_order_info_order_words = (TextView) findViewById(R.id.tv_order_info_order_words);
		tv_order_info_id = (TextView) findViewById(R.id.tv_order_info_id);
		tv_order_info_time = (TextView) findViewById(R.id.tv_order_info_time);
		tv_order_info_trans_pay = (TextView) findViewById(R.id.tv_order_info_trans_pay);
		tv_order_info_preferential = (TextView) findViewById(R.id.tv_order_info_preferential);
		tv_order_info_total = (TextView) findViewById(R.id.tv_order_info_total);
		tv_order_info_pay = (TextView) findViewById(R.id.tv_order_info_pay);
		lv_order_info_goods_list = (ListView) findViewById(R.id.lv_order_info_goods_list);
		btn_order_info_delte = (Button) findViewById(R.id.btn_order_info_delete_order);
		btn_order_info_take_order = (Button) findViewById(R.id.btn_order_info_take_order);

		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		sv_order_info = (ScrollView) findViewById(R.id.sv_order_info);
		llyt_order_info_bottom = (LinearLayout) findViewById(R.id.llyt_order_info_bottom);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(R.string.order_info);

		// 请求订单详情
		getOrderInfo();
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_order_info_delte.setOnClickListener(this);
		btn_order_info_take_order.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);
	}

	/**
	 * @描述：设置订单信息 2014-9-18
	 */
	private void setOrderInfo() {
		tv_order_info_payway.setText(order.getDeliveryName());
		tv_order_info_order_state.setText(order.getOrderStatus());
		tv_order_info_taker_name.setText("收货人：" + order.getShipName());
		tv_order_info_taker_phone.setText(order.getShip_mobile());
		tv_order_info_address_detail.setText("收货地址："
				+ order.getShip_area_store() + order.getShip_address());
		tv_order_info_order_words.setText(order.getMemo());
		tv_order_info_id.setText(order.getOrderSn());

		tv_order_info_time.setText(TimeUtil.second2Time(Long.parseLong(order
				.getCreate_date())));

		tv_order_info_trans_pay.setText(order.getDelivery());

		tv_order_info_total.setText(order.getTotalAmount());
		tv_order_info_pay.setText(order.getPaidAmount());
		tv_order_info_preferential.setText("无");
		if (order.getDeliveryName().equals("货到付款")) {
			if (Float.parseFloat(order.getTotalAmount()) > 218) {
				tv_order_info_preferential.setText("满218包邮");
			}
		} else if (order.getDeliveryName().equals("支付宝")) {
			if (Float.parseFloat(order.getTotalAmount()) > 88) {
				tv_order_info_preferential.setText("满88包邮");
			}
		}

		if (order.getMemo().equals("")) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
					0);
			llyt_order_info_order_words.setLayoutParams(params);
		}
		adapter = new OrderInfoListViewAdapter(OrderInfoActivity.this,
				order.getProductDetail(), options, imageLoader);
		lv_order_info_goods_list.setAdapter(adapter);

		// 根据付款方式来动态改变按钮动作
		setButtonAction();
	}

	/**
	 * @描述：设置按钮动作
	 * @param status
	 *            2014-9-19
	 */
	private void setButtonAction() {
		String pay_way = order.getDeliveryName().trim();
		String pay_status = order.getOrderStatus().trim();
		if (pay_way.equals("货到付款")) {
			if (pay_status.equals("待付款")) {
				btn_order_info_delte.setText("取消订单");
				btn_order_info_delte.setVisibility(View.VISIBLE);
				btn_order_info_take_order.setVisibility(View.INVISIBLE);
			} else if (pay_status.equals("待收货")) {
				btn_order_info_take_order.setVisibility(View.VISIBLE);
				btn_order_info_take_order.setText("确认收货");
				btn_order_info_delte.setVisibility(View.INVISIBLE);
			} else if (pay_status.equals("待评价")) {
				btn_order_info_take_order.setVisibility(View.VISIBLE);
				btn_order_info_take_order.setText("去评价");
				btn_order_info_delte.setVisibility(View.INVISIBLE);
			} else if (pay_status.equals("已完成")) {
				btn_order_info_take_order.setVisibility(View.INVISIBLE);
				btn_order_info_delte.setVisibility(View.VISIBLE);
				btn_order_info_delte.setText("删除订单");
			} else if (pay_status.equals("已作废")) {
				btn_order_info_take_order.setVisibility(View.INVISIBLE);
				btn_order_info_delte.setVisibility(View.VISIBLE);
				btn_order_info_delte.setText("删除订单");
			}
		} else if (pay_way.equals("支付宝")) {
			btn_order_info_take_order.setVisibility(View.VISIBLE);
			if (pay_status.equals("待付款")) {
				btn_order_info_delte.setText("取消订单");
				btn_order_info_delte.setVisibility(View.VISIBLE);
				btn_order_info_take_order.setText("去支付");
			} else if (pay_status.equals("待收货")) {
				btn_order_info_delte.setVisibility(View.INVISIBLE);
				btn_order_info_take_order.setText("确认收货");
			} else if (pay_status.equals("待评价")) {
				btn_order_info_take_order.setText("去评价");
				btn_order_info_delte.setVisibility(View.INVISIBLE);
			} else if (pay_status.equals("已完成")) {
				btn_order_info_take_order.setVisibility(View.INVISIBLE);
				btn_order_info_delte.setVisibility(View.VISIBLE);
				btn_order_info_delte.setText("删除订单");
			} else if (pay_status.equals("已作废")) {
				btn_order_info_take_order.setVisibility(View.INVISIBLE);
				btn_order_info_delte.setVisibility(View.VISIBLE);
				btn_order_info_delte.setText("删除订单");
			}
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
		case R.id.btn_order_info_delete_order:// 删除订单
			if (btn_order_info_delte.getText().equals("删除订单")) {
				showDeleteDialog();
			} else if (btn_order_info_delte.getText().equals("取消订单")) {
				showCancelDialog();
			}
			break;
		case R.id.btn_order_info_take_order:// 确认收货
			if (btn_order_info_take_order.getText().toString().equals("去支付")) {// 去支付
				getAliPayParams();
			} else if (btn_order_info_take_order.getText().toString()
					.equals("确认收货")) {
				takeOrder();
			} else if (btn_order_info_take_order.getText().toString()
					.equals("去评价")) {
				Intent intent = new Intent(OrderInfoActivity.this,
						DisucssOrderActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("order", order);
				intent.putExtras(bundle);
				startActivityForResult(intent, AppConfig.REQUEST_CODE);
				overridePendingTransition(R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
			}
			break;
		case R.id.llyt_network_error://
			getOrderInfo();
			break;
		}
	}

	/**
	 * @描述：删除订单 2014-9-21
	 */
	private void showDeleteDialog() {
		dialog_delete = new CustomDialog(this,
				getString(R.string.dialog_order_history_title),
				getString(R.string.dialog_order_history_message));

		dialog_delete.show();
		// 确定删除订单(提交到服务器)
		dialog_delete.btn_custom_dialog_sure
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_delete.dismiss();
						deleteOrder();
					}
				});
		// 取消移除
		dialog_delete.btn_custom_dialog_cancel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_delete.dismiss();

					}
				});
	}

	/**
	 * @描述：取消订单 2014-9-21
	 */
	private void showCancelDialog() {
		dialog_delete = new CustomDialog(this,
				getString(R.string.dialog_order_cancel_title),
				getString(R.string.dialog_order_cancel_message));

		dialog_delete.show();
		// 确定取消订单(提交到服务器)
		dialog_delete.btn_custom_dialog_sure
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_delete.dismiss();
						cancelOrder();
					}
				});
		// 取消移除
		dialog_delete.btn_custom_dialog_cancel
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog_delete.dismiss();

					}
				});
	}

	/**
	 * @描述：请求删除订单 2014-8-14
	 */
	private void deleteOrder() {
		HttpUrlProvider.getIntance().getDeleteOrder(OrderInfoActivity.this,
				new TaskDeleteOrderBack(handler), URLConfig.DELETE_ORDER,
				order_id);
		loadingdialog.show(OrderInfoActivity.this);
	}

	/**
	 * @描述：取消订单 2014-9-21
	 */
	private void cancelOrder() {
		HttpUrlProvider.getIntance().getCancelOrder(OrderInfoActivity.this,
				new TaskCancelOrderBack(handler), URLConfig.CANCEL_ORDR,
				order_id);
		loadingdialog.show(OrderInfoActivity.this);
	}

	/**
	 * @描述：请求的获取订单详情 2014-9-18
	 */
	private void getOrderInfo() {
		HttpUrlProvider.getIntance().getOrderInfo(this,
				new TaskOrderInfoBack(handler), URLConfig.ORDER_INFO, order_id);
		loadingdialog.show(OrderInfoActivity.this);
	}

	/**
	 * @描述：请求获取支付参数 2014-9-19
	 */
	private void getAliPayParams() {
		// 请求获取支付参数
		HttpUrlProvider.getIntance().getAlipay(this,
				new TaskAlipayBack(handler), URLConfig.ALIPAY_URL, order_id);
		loadingdialog.show(OrderInfoActivity.this);
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
					AliPay alipay = new AliPay(OrderInfoActivity.this, handler);

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
			ToastUtil.ToastView(OrderInfoActivity.this, "支付宝调用失败");
		}

	}

	/**
	 * @描述：确认收货 2014-9-19
	 */
	private void takeOrder() {
		HttpUrlProvider.getIntance()
				.getOrderTaker(this, new TaskTakeOrderBack(handler),
						URLConfig.ORDER_TAKER, order_id);
		loadingdialog.show(OrderInfoActivity.this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == AppConfig.REQUEST_CODE) {
			if (resultCode == AppConfig.RESULT_CODE_OK) {// 评价成功刷新界面
				getOrderInfo();
				setResult(AppConfig.RESULT_CODE_OK_2);
			}
		}
	}
}
