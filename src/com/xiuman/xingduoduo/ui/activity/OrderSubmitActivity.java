package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.android.app.sdk.AliPay;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.OrderSubmitListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.Mylog;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskAddOrderBack;
import com.xiuman.xingduoduo.callback.TaskAlipayBack;
import com.xiuman.xingduoduo.callback.TaskSendAliPayStatusCodeBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.ActionValuePay;
import com.xiuman.xingduoduo.model.AliPayStatus;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.model.OrderId;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.model.UserAddress;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.pay.alipay.Result;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * 
 * @名称：OrderSubmitActivity.java
 * @描述：订单提交确认页
 * @author danding
 * @version
 * @date：2014-7-25
 */

public class OrderSubmitActivity extends Base2Activity implements
		OnClickListener {
	/*--------------------------------组件--------------------------------*/
	// 返回
	private Button btn_back;
	// 右侧
	private Button btn_right;
	// 标题
	private TextView tv_title;

	// 收货地址------------------------------------------------------
	private LinearLayout llyt_submit_order_address;
	// 无收货地址
	private LinearLayout llyt_submit_order_null;
	// 有收货地址
	private LinearLayout llyt_submit_order_not_null;
	// 收货人
	private TextView tv_submit_order_taker_name;
	// 电话
	private TextView tv_submit_order_taker_phone;
	// 详细地址
	private TextView tv_submit_order_address_detail;

	// 支付方式-----------------------------------------------------
	// 到付-------------------------------------------------
	private LinearLayout llyt_order_pay_way_daofu;
	// 到付tag(多少包邮啥的)
	private TextView tv_order_pay_daofu_tag;
	// CheckBox选项
	private CheckBox cb_order_pay_daofu;
	// 支付宝-----------------------------------------------
	private LinearLayout llyt_order_pay_way_zhifubao;
	// 支付宝tag
	private TextView tv_order_pay_zhifubao_tag;
	// 选项
	private CheckBox cb_order_pay_zhifubao;
	// 银联-------------------------------------------------
	private LinearLayout llyt_order_pay_way_yinlian;
	// 银联tag
	private TextView tv_order_pay_yinlian_tag;
	// 选项
	private CheckBox cb_order_pay_yinlian;
	// 财付通-----------------------------------------------
	private LinearLayout llyt_order_pay_way_caifutong;
	// 财付通tag
	private TextView tv_order_pay_caifutong_tag;
	// 选项
	private CheckBox cb_order_pay_caifutong;

	// 商品清单---------------------------------------------
	private LinearLayout llyt_order_submit_goods_list;
	// 商品数
	private TextView tv_order_submit_goods_number;
	// 商品总价
	private TextView tv_order_submit_goods_total;
	// 表示商品列表是否显示
	private ImageView iv_order_list_right;
	// 商品列表ListView
	private ListView lv_order_submit_goods_list;
	// 商品列表包裹
	private LinearLayout llyt_order_submit_goods_list_container;

	// 运费
	private TextView tv_order_submit_freight;
	// 优惠
	private TextView tv_order_submit_preferential;
	// 留言
	private EditText et_order_submit_message;

	// 提交订单----------------------------------------------
	// 订单合计(包含运费)
	private TextView tv_order_submit_total;
	// 含活动商品的提示
	private TextView tv_order_submit_total_tip;
	// 确认提交
	private Button btn_order_submit_sure;
	// 提交订单加载进度框
	private LoadingDialog loadingdialog;
	/*-----------------------ImageLoader-----------------------------*/
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*------------------------------------------数据-------------------------------------*/
	// 当前登录的用户
	private User user;
	// 付款方式
	private String[] pay_ways;
	// 付款方式
	private String current_pay;
	// 运费(4种方式)
	private String[] trans_ways;
	// 当前选择的运费方式
	private String current_trans;
	// 全局shopid
	// private String shopId = "";
	// (用户地址)
	private UserAddress userAddress;
	// 从上级界面传递而来的购物车数据
	private ArrayList<GoodsCart> cart_goods;

	// 商品总数
	private int goods_number = 0;
	// 商品总价
	private double goods_total = 0;
	// 订单总价(运费)
	private double order_total = 0;

	/*----------------------------------------Adapter--------------------------------------*/
	// 商品列表Adapter
	private OrderSubmitListViewAdapter adapter;

	/*------------------------------------请求提交订单的返回结果-----------------------------*/
	// 创建订单结果返回
	private ActionValue<OrderId> value_create;
	// 请求支付宝私钥
	private ActionValuePay value_alipay;
	// 传递支付结果
	private ActionValue<AliPayStatus> value_pay_status;

	// 支付宝线程返回 值类型
	private static final int RQF_PAY = 1;
	// 订单id
	private String orderId;
	// 支付宝签名字串
	private String alipaySign;

	// ***传递到订单生成完成界面的付款方式标记---------------------------------------------------
	private int pay_tag;// (0-->代表支付宝，1--->代表货到付款)

	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.CREATE_ORDER:// 创建订单返回
				value_create = (ActionValue<OrderId>) msg.obj;
				if (value_create.isSuccess()) {
					// 支付宝
					if (cb_order_pay_zhifubao.isChecked()) {
						// 获取订单号，并提交支付宝支付
						orderId = value_create.getDatasource().get(0)
								.getOrderId();
						// 请求获取支付参数
						HttpUrlProvider.getIntance().getAlipay(
								OrderSubmitActivity.this,
								new TaskAlipayBack(handler),
								URLConfig.ALIPAY_URL, orderId);
						// 货到付款
					} else if (cb_order_pay_daofu.isChecked()) {
						ToastUtil.ToastView(OrderSubmitActivity.this,
								value_create.getMessage());
						orderId = value_create.getDatasource().get(0)
								.getOrderId();
						pay_tag = 1;
						Intent intent_daofu = new Intent(
								OrderSubmitActivity.this,
								OrderCompleteActivity.class);
						Bundle bundle_daofu = new Bundle();

						bundle_daofu.putInt("pay_tag", pay_tag);
						bundle_daofu.putString("orderId", orderId);
						bundle_daofu.putString("order_poster", cart_goods
								.get(0).getSmallGoodsImagePath());
						bundle_daofu.putString("goods_total",
								tv_order_submit_total.getText().toString());
						bundle_daofu.putString("goods_number", goods_number
								+ "");
						intent_daofu.putExtras(bundle_daofu);
						startActivity(intent_daofu);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_horizontal_start_out);
						finish();
					}
					// 重新设置购物车商品数量
					MyApplication.getInstance().setCartGoodsNumber(
							MyApplication.getInstance().getCartGoodsNumber()
									- goods_number);
				} else {
					ToastUtil.ToastView(OrderSubmitActivity.this,
							value_create.getMessage());
				}
				loadingdialog.dismiss();
				break;
			case AppConfig.NET_ERROR_NOTNET:// 网络连接失败

				loadingdialog.dismiss();
				break;
			case AppConfig.ALIPAY_BACK:
				value_alipay = (ActionValuePay) msg.obj;
				if (value_alipay.isSuccess()) {

					alipaySign = value_alipay.getDatasource();
					submitAlipayOrder();

				} else {
					ToastUtil.ToastView(OrderSubmitActivity.this,
							value_alipay.getMessage());
				}
				loadingdialog.dismiss();

				break;
			case RQF_PAY:// 支付结果返回

				Result result = new Result((String) msg.obj);
				result.parseResult();
				if (result.rs.equals("9000") && result.isSignOk) {
					ToastUtil.ToastView(OrderSubmitActivity.this, "支付成功");
				} else {
					ToastUtil.ToastView(OrderSubmitActivity.this,
							result.getResult());
				}
				// 将支付结果传递到后台
				HttpUrlProvider.getIntance().sendPayStatusCode(
						OrderSubmitActivity.this,
						new TaskSendAliPayStatusCodeBack(handler),
						URLConfig.PAY_STATUS_CODE, orderId, result.rs);
				break;
			case AppConfig.SEND_STATUS_CODE:// 获取传递支付结果到后台的返回值，传递到下一界面
				value_pay_status = (ActionValue<AliPayStatus>) msg.obj;
				ToastUtil.ToastView(OrderSubmitActivity.this,
						value_pay_status.getMessage());
				pay_tag = 0;

				Intent intent_ali = new Intent(OrderSubmitActivity.this,
						OrderCompleteActivity.class);
				Bundle bundle_ali = new Bundle();
				bundle_ali.putInt("pay_tag", pay_tag);
				bundle_ali.putSerializable("AliPayStatus", value_pay_status);
				bundle_ali.putString("order_poster", cart_goods.get(0)
						.getSmallGoodsImagePath());
				bundle_ali.putString("goods_total", tv_order_submit_total
						.getText().toString());
				bundle_ali.putString("goods_number", goods_number + "");
				intent_ali.putExtras(bundle_ali);
				startActivity(intent_ali);
				overridePendingTransition(R.anim.translate_horizontal_start_in,
						R.anim.translate_horizontal_start_out);
				finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_submit);
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

		// 获取配置文件中的4种运费方式
		trans_ways = getResources().getStringArray(R.array.trans_pay);
		// 获取配置文件中的2种支付方式
		pay_ways = getResources().getStringArray(R.array.pay_ways);

		// 读取本地默认用户地址(如果存在)
		userAddress = MyApplication.getInstance().getDefaultAddress();

		// 上级界面传来的数据
		cart_goods = (ArrayList<GoodsCart>) getIntent().getExtras()
				.getSerializable("balance_goods");
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		// 收货地址
		llyt_submit_order_address = (LinearLayout) findViewById(R.id.llyt_submit_order_address);
		llyt_submit_order_null = (LinearLayout) findViewById(R.id.llyt_submit_order_null);
		llyt_submit_order_not_null = (LinearLayout) findViewById(R.id.llyt_submit_order_not_null);
		tv_submit_order_taker_name = (TextView) findViewById(R.id.tv_submit_order_taker_name);
		tv_submit_order_taker_phone = (TextView) findViewById(R.id.tv_submit_order_taker_phone);
		tv_submit_order_address_detail = (TextView) findViewById(R.id.tv_submit_order_address_detail);
		// 到付
		llyt_order_pay_way_daofu = (LinearLayout) findViewById(R.id.llyt_order_pay_way_daofu);
		tv_order_pay_daofu_tag = (TextView) findViewById(R.id.tv_order_pay_daofu_tag);
		cb_order_pay_daofu = (CheckBox) findViewById(R.id.cb_order_pay_daofu);
		// 支付宝
		llyt_order_pay_way_zhifubao = (LinearLayout) findViewById(R.id.llyt_order_pay_way_zhifubao);
		tv_order_pay_zhifubao_tag = (TextView) findViewById(R.id.tv_order_pay_zhifubao_tag);
		cb_order_pay_zhifubao = (CheckBox) findViewById(R.id.cb_order_pay_zhifubao);
		// 银联
		llyt_order_pay_way_yinlian = (LinearLayout) findViewById(R.id.llyt_order_pay_way_yinlian);
		tv_order_pay_yinlian_tag = (TextView) findViewById(R.id.tv_order_pay_yinlian_tag);
		cb_order_pay_yinlian = (CheckBox) findViewById(R.id.cb_order_pay_yinlian);
		// 财付通
		llyt_order_pay_way_caifutong = (LinearLayout) findViewById(R.id.llyt_order_pay_way_caifutong);
		tv_order_pay_caifutong_tag = (TextView) findViewById(R.id.tv_order_pay_caifutong_tag);
		cb_order_pay_caifutong = (CheckBox) findViewById(R.id.cb_order_pay_caifutong);
		// 商品清单
		llyt_order_submit_goods_list = (LinearLayout) findViewById(R.id.llyt_order_submit_goods_list);
		tv_order_submit_goods_number = (TextView) findViewById(R.id.tv_order_submit_goods_number);
		tv_order_submit_goods_total = (TextView) findViewById(R.id.tv_order_submit_goods_total);
		iv_order_list_right = (ImageView) findViewById(R.id.iv_order_list_right);
		lv_order_submit_goods_list = (ListView) findViewById(R.id.lv_order_submit_goods_list);
		llyt_order_submit_goods_list_container = (LinearLayout) findViewById(R.id.llyt_order_submit_goods_list_container);

		// 运费
		tv_order_submit_freight = (TextView) findViewById(R.id.tv_order_submit_freight);
		tv_order_submit_preferential = (TextView) findViewById(R.id.tv_order_submit_preferential);
		et_order_submit_message = (EditText) findViewById(R.id.et_order_submit_message);

		// 合计
		tv_order_submit_total = (TextView) findViewById(R.id.tv_order_submit_total);
		tv_order_submit_total_tip = (TextView) findViewById(R.id.tv_order_submit_total_tip);
		btn_order_submit_sure = (Button) findViewById(R.id.btn_order_submit_sure);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(getResources().getString(
				R.string.order_submit_sure_order));

		// 收货地址初始化
		if (userAddress == null) {
			llyt_submit_order_null.setVisibility(View.VISIBLE);
			llyt_submit_order_not_null.setVisibility(View.INVISIBLE);
		} else {
			tv_submit_order_taker_name.setTextKeepState("收货人："
					+ userAddress.getReceiveName());
			tv_submit_order_taker_phone.setText(userAddress.getTelephone());
			tv_submit_order_address_detail.setTextKeepState("收货地址："
					+ userAddress.getAreaId() + userAddress.getAddress());
			llyt_submit_order_null.setVisibility(View.INVISIBLE);
			llyt_submit_order_not_null.setVisibility(View.VISIBLE);
		}

		// 设置商品数据信息
		if (cart_goods.size() > 0) {

			for (int i = 0; i < cart_goods.size(); i++) {
				goods_number += cart_goods.get(i).getQuanity();
				goods_total += Double
						.valueOf(cart_goods.get(i).getTotalPrice());
			}
			tv_order_submit_goods_number.setText(goods_number + "");
			tv_order_submit_goods_total.setText(goods_total + "");
		}

		// 设置Adapter
		adapter = new OrderSubmitListViewAdapter(this, cart_goods, options,
				imageLoader);
		lv_order_submit_goods_list.setAdapter(adapter);

		// 设置默认付款方式支付宝
		cb_order_pay_zhifubao.setChecked(true);
		setTrans();

	}

	/**
	 * @描述：根据邮费设置订单总价
	 * @date：2014-7-28
	 */
	private void setOrderTotal() {
		// 根据邮费设置订单总价
		if (!tv_order_submit_freight.getText().equals("包邮")) {
			order_total = goods_total
					+ Double.valueOf(tv_order_submit_freight.getText() + "");
		} else {
			order_total = goods_total;
		}
		// 如果有活动商品，则根据付款方式加上相应的费用
		int goods_activity_total = 0;
		int goods_activity_nmber = 0;
		int activity_count = 0;
		for (int i = 0; i < cart_goods.size(); i++) {
			if (cart_goods.get(i).isActivities()) {
				tv_order_submit_total_tip.setVisibility(View.VISIBLE);
				goods_activity_nmber += cart_goods.get(i).getQuanity();
				activity_count++;
			}
		}
		// 所有商品均是活动商品
		if (activity_count == cart_goods.size()) {
			if(cb_order_pay_zhifubao.isChecked()){
				current_trans = trans_ways[1];
			}else if(cb_order_pay_daofu.isChecked()){
				current_trans = trans_ways[3];
			}
			tv_order_submit_freight.setText("22");
			order_total = goods_activity_total = 22 * goods_activity_nmber;
		} else {
			goods_activity_total = 22 * goods_activity_nmber;
			order_total += goods_activity_total;
		}

		
		tv_order_submit_total.setText(order_total + "");
	}

	/**
	 * @描述：根据支付方式以及商品总价来确定运费 2014-8-26
	 */
	private void setTrans() {
		if (cb_order_pay_zhifubao.isChecked()) {
			current_pay = pay_ways[0];
			if (goods_total >= 88) {
				// 包邮
				current_trans = trans_ways[1];
				tv_order_submit_freight.setText("包邮");
				tv_order_submit_preferential.setText(tv_order_pay_zhifubao_tag
						.getText());
			} else {
				// 10元
				current_trans = trans_ways[0];
				tv_order_submit_freight.setText("10.00");
				tv_order_submit_preferential.setText("无");
			}

		} else if (cb_order_pay_daofu.isChecked()) {
			current_pay = pay_ways[1];
			if (goods_total >= 218) {
				// 包邮
				current_trans = trans_ways[3];
				tv_order_submit_freight.setText("包邮");
				tv_order_submit_preferential.setText(tv_order_pay_daofu_tag
						.getText());
			} else {
				// 12元
				current_trans = trans_ways[2];
				tv_order_submit_freight.setText("12.00");
				tv_order_submit_preferential.setText("无");
			}
		}
		// 设置订单总价
		setOrderTotal();

	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		llyt_submit_order_address.setOnClickListener(this);
		llyt_order_pay_way_daofu.setOnClickListener(this);
		llyt_order_pay_way_zhifubao.setOnClickListener(this);
		llyt_order_pay_way_yinlian.setOnClickListener(this);
		llyt_order_pay_way_caifutong.setOnClickListener(this);
		llyt_order_submit_goods_list.setOnClickListener(this);
		btn_order_submit_sure.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		user = MyApplication.getInstance().getUserInfo();
		if (user == null) {
			Intent intent = new Intent(this, UserLoginActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.translate_vertical_start_in,
					R.anim.translate_vertical_start_out);
		}
	}

	/**
	 * 
	 * @描述：点击事件
	 * @date：2014-7-25
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.llyt_submit_order_address:// 收货地址(返回信息的收货地址)
			Intent intent = new Intent(OrderSubmitActivity.this,
					UserAddressManagerActivity.class);
			Bundle bundle = new Bundle();
			bundle.putBoolean("inFlag", true);
			intent.putExtras(bundle);
			startActivityForResult(intent, AppConfig.REQUEST_CODE);
			break;
		case R.id.llyt_order_pay_way_daofu:// 货到付款
			clearCheck();
			current_pay = pay_ways[1];
			cb_order_pay_daofu.setChecked(true);
			// 设置订单总价以及邮费
			setTrans();
			break;
		case R.id.llyt_order_pay_way_zhifubao:// 支付宝
			clearCheck();
			current_pay = pay_ways[0];
			cb_order_pay_zhifubao.setChecked(true);
			// 设置订单总价以及邮费
			setTrans();
			break;
		case R.id.llyt_order_pay_way_yinlian:// 银联
			clearCheck();
			current_pay = "银联";
			cb_order_pay_yinlian.setChecked(true);
			tv_order_submit_preferential.setText(tv_order_pay_yinlian_tag
					.getText());
			break;
		case R.id.llyt_order_pay_way_caifutong:// 财付通
			clearCheck();
			current_pay = "财付通";
			cb_order_pay_caifutong.setChecked(true);
			tv_order_submit_preferential.setText(tv_order_pay_caifutong_tag
					.getText());
			break;
		case R.id.llyt_order_submit_goods_list:// 查看商品列表
			if (lv_order_submit_goods_list.getVisibility() == View.INVISIBLE) {// 如果不可见则变为可见
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				llyt_order_submit_goods_list_container.setLayoutParams(params);
				lv_order_submit_goods_list.setLayoutParams(params);
				lv_order_submit_goods_list.setVisibility(View.VISIBLE);
				iv_order_list_right
						.setImageResource(R.drawable.ic_bottom_black);

			} else {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, 0);
				lv_order_submit_goods_list.setLayoutParams(params);
				lv_order_submit_goods_list.setVisibility(View.INVISIBLE);
				iv_order_list_right.setImageResource(R.drawable.ic_right_black);
				llyt_order_submit_goods_list_container.setLayoutParams(params);
			}

			break;
		case R.id.btn_order_submit_sure:// 确认提交订单
			submitOrder();

			break;
		}
	}

	/**
	 * @描述：提交订单（先进行判断） 2014-8-22
	 */
	private void submitOrder() {
		if (userAddress == null) {
			ToastUtil.ToastView(this, "您还没有添加收货地址哦！");
			return;
		}
		if (current_pay == null) {
			ToastUtil.ToastView(this, "请选择支付方式");
			return;
		}
		String shopId = "";

		for (int i = 0; i < cart_goods.size(); i++) {
			if (i == cart_goods.size() - 1) {
				shopId += cart_goods.get(i).getCartItemId();
			} else {
				shopId += cart_goods.get(i).getCartItemId() + ",";
			}
		}

		HttpUrlProvider.getIntance().getCreateOrder(this,
				new TaskAddOrderBack(handler), URLConfig.CREATE_ORDER,
				user.getUserId(), current_pay, current_trans,
				userAddress.getReceiveId(),
				et_order_submit_message.getText().toString().trim(), shopId);
		loadingdialog.show(OrderSubmitActivity.this);
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
					AliPay alipay = new AliPay(OrderSubmitActivity.this,
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
			ToastUtil.ToastView(OrderSubmitActivity.this, "支付宝调用失败");
		}

	}

	/**
	 * 
	 * @描述：清除以选择的付款方式
	 * @date：2014-7-25
	 */
	private void clearCheck() {
		cb_order_pay_daofu.setChecked(false);
		cb_order_pay_zhifubao.setChecked(false);
		cb_order_pay_yinlian.setChecked(false);
		cb_order_pay_caifutong.setChecked(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == AppConfig.REQUEST_CODE) {
			switch (resultCode) {
			case AppConfig.ORDER_SUBMIT_ADDRESS:// 从地址管理界面获取返回的收货地址
				if (data != null) {
					userAddress = (UserAddress) data.getExtras()
							.getSerializable("address");
					tv_submit_order_taker_name.setTextKeepState("收货人："
							+ userAddress.getReceiveName());
					tv_submit_order_taker_phone.setText(userAddress
							.getTelephone());
					tv_submit_order_address_detail.setTextKeepState("收货地址："
							+ userAddress.getAreaId()
							+ userAddress.getAddress());
					llyt_submit_order_null.setVisibility(View.INVISIBLE);
					llyt_submit_order_not_null.setVisibility(View.VISIBLE);
				}
				break;
			}
		}

	}
}
