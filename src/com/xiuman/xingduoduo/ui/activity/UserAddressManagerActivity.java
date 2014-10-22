package com.xiuman.xingduoduo.ui.activity;

import java.util.ArrayList;

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
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.adapter.UserAddressListViewAdapter;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskUserAddressesBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.model.UserAddress;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * @名称：UserAddressManagerActivity.java
 * @描述：用户收货地址管理
 * @author danding 2014-8-19
 */
public class UserAddressManagerActivity extends Base2Activity implements
		OnClickListener {

	/*------------------------------组件------------------------------*/
	// 返回
	private Button btn_back;
	// 新增
	private Button btn_right;
	// 标题栏
	private TextView tv_title;
	// 收货地址列表
	private ListView lv_user_addresses;
	// 新增收货地址
	private Button btn_add_address;
	// 无网络布局
	private LinearLayout llyt_network_error;
	// 数据为空
	private LinearLayout llyt_null_address;
	// 数据加载
	private LoadingDialog loadingdialog;

	/*------------------------------数据--------------------------------*/
	// 当前登录的用户
	private User user;

	/*-------------------------------Adapter------------------------------*/
	// 数据列表
	private UserAddressListViewAdapter adapter;

	/*-----------------------------请求数据-------------------------------*/
	// 收货地址列表
	private ActionValue<UserAddress> value_address = new ActionValue<UserAddress>();
	// 请求获取的用户收货地址列表
	private ArrayList<UserAddress> addresses = new ArrayList<UserAddress>();

	/**** ----------------------------从上级界面接收到的标记值----------------------------- ****/
	// 标识是从个人信息界面进入还是从订单提交界面(使用flag标记(boolean))(默认是从个人信息界面进入false)
	private boolean inFlag = false;// false从个人信息，true提交订单

	// 消息处理
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求地址列表成功
				value_address = (ActionValue<UserAddress>) msg.obj;
				if (!value_address.isSuccess()) {
					llyt_null_address.setVisibility(View.VISIBLE);
				} else {
					addresses = value_address.getDatasource();
					adapter = new UserAddressListViewAdapter(
							UserAddressManagerActivity.this, addresses);
					lv_user_addresses.setAdapter(adapter);
					llyt_null_address.setVisibility(View.INVISIBLE);
				}
				loadingdialog.dismiss(UserAddressManagerActivity.this);
				llyt_network_error.setVisibility(View.INVISIBLE);
				break;
			case AppConfig.NET_ERROR_NOTNET:// 请求无网络
				llyt_network_error.setVisibility(View.VISIBLE);
				llyt_null_address.setVisibility(View.INVISIBLE);
				loadingdialog.dismiss(UserAddressManagerActivity.this);
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_address_manager);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		user = MyApplication.getInstance().getUserInfo();

		// 从上级界面接收到的标记值
		inFlag = getIntent().getExtras().getBoolean("inFlag");
	}

	@Override
	protected void findViewById() {
		btn_back = (Button) findViewById(R.id.btn_common_back2);
		btn_right = (Button) findViewById(R.id.btn_common_right2);
		btn_add_address = (Button) findViewById(R.id.btn_add_address);
		lv_user_addresses = (ListView) findViewById(R.id.lv_user_addresses);
		tv_title = (TextView) findViewById(R.id.tv_common_title2);
		llyt_network_error = (LinearLayout) findViewById(R.id.llyt_network_error);
		llyt_null_address = (LinearLayout) findViewById(R.id.llyt_null_address);
		loadingdialog = new LoadingDialog(this);
	}

	@Override
	protected void initUI() {
		tv_title.setText("收货地址管理");
		btn_right.setVisibility(View.INVISIBLE);

		// 请求收货地址列表
		initAddress();
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_add_address.setOnClickListener(this);
		llyt_network_error.setOnClickListener(this);

		// 收货地址列表item点击分两种情况，一种查看详细信息，一种选择收货地址，根据接收到的值来确定
		lv_user_addresses.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!inFlag) {// 查看收货地址详情

					Object obj = lv_user_addresses.getItemAtPosition(position);
					if (obj instanceof UserAddress) {
						UserAddress address = (UserAddress) obj;
						Intent intent = new Intent(
								UserAddressManagerActivity.this,
								UserAddressUpdateActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("address", address);
						intent.putExtras(bundle);
						startActivityForResult(intent, AppConfig.REQUEST_CODE);
						overridePendingTransition(
								R.anim.translate_horizontal_start_in,
								R.anim.translate_vertical_start_out);
					}
				} else {// 订单提交界面返回收货地址(并将该地址保存下来作为下一次默认地址)
					Object obj = lv_user_addresses.getItemAtPosition(position);
					if (obj instanceof UserAddress) {
						UserAddress address = (UserAddress) obj;
						Intent intent = new Intent(
								UserAddressManagerActivity.this,
								OrderSubmitActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("address", address);
						intent.putExtras(bundle);
						setResult(AppConfig.ORDER_SUBMIT_ADDRESS, intent);
						//保存改地址作为默认
						MyApplication.getInstance().setDefaultAddress(address);
						finish();
					}
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
		case R.id.btn_common_back2:// 返回
			finish();
			break;
		case R.id.btn_add_address:// 新增收货地址
			Intent intent = new Intent(this, UserAddressCreateActivity.class);
			startActivityForResult(intent, AppConfig.REQUEST_CODE);
			break;
		case R.id.llyt_network_error://重新加载
			initAddress();
			break;
		}
	}

	/**
	 * @描述：请求用户的收货地址信息 2014-8-19
	 */
	private void initAddress() {
		loadingdialog.show(UserAddressManagerActivity.this);
		HttpUrlProvider.getIntance().getUserAddresses(this,
				new TaskUserAddressesBack(handler), URLConfig.USER_ADDRESSES,
				user.getUserId());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == AppConfig.REQUEST_CODE) {
			switch (resultCode) {
			case AppConfig.RESULT_CODE_ADD_ADDRESS:// 添加新地址
				initAddress();
				break;
			case AppConfig.RESULT_CODE_UPDATE_ADDRESS:// 修改收货地址
				initAddress();
				break;
			}
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(UserAddressManagerActivity.this);
		loadingdialog = null;
	}
}
