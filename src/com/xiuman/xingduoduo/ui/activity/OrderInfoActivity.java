package com.xiuman.xingduoduo.ui.activity;

import android.annotation.SuppressLint;
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
import com.xiuman.xingduoduo.adapter.OrderSubmitListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskDeleteOrderBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Order;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
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
	// 实付款
	private TextView tv_order_info_total;
	//删除订单
	private Button btn_order_info_delte;
	//查看物流
	private Button btn_order_info_logistics_order;
	//确认收货
	private Button btn_order_info_take_order;

	//删除订单弹出对话框
	private CustomDialog dialog_delete;
	//删除进度
	private LoadingDialog loadingdialog;
	
	// -----------------------ImageLoader-----------------------------
	// ImageLoader
	public ImageLoader imageLoader = ImageLoader.getInstance();
	// 配置图片加载及显示选项
	public DisplayImageOptions options;

	/*-----------------------------------数据-------------------------------*/
	// 从上级界面接收到的订单数据
	private Order order;
	
	/*---------------------------------Adapter------------------------------*/
	private OrderSubmitListViewAdapter adapter;
	
	
	/*------------------------------请求数据------------------------------*/
	//删除结果
	private ActionValue<?> value;
	
	//消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.UPDATE_ORDER://删除结果
				value = (ActionValue<?>) msg.obj;
				if(value.isSuccess()){//删除成功
					ToastUtil.ToastView(OrderInfoActivity.this, value.getMessage());
					finish();
				}else{//删除失败
					ToastUtil.ToastView(OrderInfoActivity.this, value.getMessage());
				}
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
		
		order = (Order) getIntent().getExtras().getSerializable("order_info");
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
		tv_order_info_order_words = (TextView) findViewById(R.id.tv_order_info_order_words);
		tv_order_info_id = (TextView) findViewById(R.id.tv_order_info_id);
		tv_order_info_time = (TextView) findViewById(R.id.tv_order_info_time);
		tv_order_info_trans_pay = (TextView) findViewById(R.id.tv_order_info_trans_pay);
		tv_order_info_preferential = (TextView) findViewById(R.id.tv_order_info_preferential);
		tv_order_info_total = (TextView) findViewById(R.id.tv_order_info_total);
		lv_order_info_goods_list = (ListView) findViewById(R.id.lv_order_info_goods_list);
		btn_order_info_delte = (Button) findViewById(R.id.btn_order_info_delete_order);
		btn_order_info_logistics_order = (Button) findViewById(R.id.btn_order_info_logistics_order);
		btn_order_info_take_order = (Button) findViewById(R.id.btn_order_info_take_order);
	}

	@Override
	protected void initUI() {
		btn_right.setVisibility(View.INVISIBLE);
		tv_title.setText(R.string.order_info);
		
		tv_order_info_payway.setText(order.getOrder_pay_way());
		tv_order_info_order_state.setText(order.getOrder_state());
		tv_order_info_taker_name.setText("收货人："+order.getOrder_user_address().getReceiveName());
		tv_order_info_taker_phone.setText(order.getOrder_user_address().getTelephone());
		tv_order_info_address_detail.setText("收货地址："+order.getOrder_user_address().getAreaId()+order.getOrder_user_address().getAddress());
		tv_order_info_order_words.setText(order.getOrder_user_words());
		tv_order_info_id.setText(order.getOrder_id());
		tv_order_info_time.setText(order.getOrder_time());
		tv_order_info_trans_pay.setText(order.getOrder_trans_pay());
		tv_order_info_preferential.setText(order.getOrder_preferential());
		tv_order_info_total.setText(order.getOrder_total()+"");
		
		adapter = new OrderSubmitListViewAdapter(OrderInfoActivity.this, order.getGoods_list(), options, imageLoader);
		lv_order_info_goods_list.setAdapter(adapter);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_order_info_delte.setOnClickListener(this);
		btn_order_info_logistics_order.setOnClickListener(this);
		btn_order_info_take_order.setOnClickListener(this);
	}
	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back://返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in, R.anim.translate_horizontal_finish_out);
			
			break;
		case R.id.btn_order_info_delete_order://删除订单
			dialog_delete = new CustomDialog(this, getString(R.string.dialog_order_history_title), getString(R.string.dialog_order_history_message));

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
			break;
		case R.id.btn_order_info_logistics_order://查看物流
			
			break;
		case R.id.btn_order_info_take_order://确认收货
			
			break;
		}
	}
	/**
	 * @描述：请求删除订单
	 * 2014-8-14
	 */
	private void deleteOrder(){
		HttpUrlProvider.getIntance().getDeleteOrder(
				OrderInfoActivity.this,
				new TaskDeleteOrderBack(handler),
				URLConfig.DELETE_ORDER, order.getOrder_id());
		loadingdialog.show();
	}

}
