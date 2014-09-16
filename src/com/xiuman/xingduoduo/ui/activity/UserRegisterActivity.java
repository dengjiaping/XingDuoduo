package com.xiuman.xingduoduo.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskRegisterBack;
import com.xiuman.xingduoduo.callback.TaskUserInfoBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.RegexUtil;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * 
 * @名称：UserRegisterActivity.java
 * @描述：用户注册界面
 * @author danding
 * @version 2014-6-18
 */
public class UserRegisterActivity extends Base2Activity implements
		OnClickListener {

	/*---------------------------------组件-----------------------------*/
	// 返回
	private Button btn_back;
	// 邮箱
	private EditText et_register_email;
	// 首次密码
	private EditText et_register_psw_1;
	// 确认密码
	private EditText et_register_psw_2;
	// 用户名
	private EditText et_register_user_name;
	//昵称
	private EditText et_register_nick_name;
	// 查看性多多服务协议
	private LinearLayout llyt_register_read_protocol;
	// 注册
	private Button btn_register_agress2register;
	// 注册进度Dialog
	private LoadingDialog loadingdialog;

	/*---------------------------------数据----------------------------------*/
	// 注册结果
	private ActionValue<?> value;

	// Handler
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功(注册成功和注册失败，注册成功则请求个人信息并保存，然后退出界面，失败则留在该界面继续注册)

				value = (ActionValue<?>) msg.obj;
				if (value.isSuccess()) {
					ToastUtil.ToastView(UserRegisterActivity.this,
							value.getMessage());
					// 获取用户信息
					getUserInfo();
				} else {
					ToastUtil.ToastView(UserRegisterActivity.this,
							value.getMessage());
					loadingdialog.dismiss();
				}

				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				ToastUtil.ToastView(UserRegisterActivity.this, "网络连接失败，请重试");
				break;
			case AppConfig.SUCCESS_USER_INFO:// 获取用户信息成功
				loadingdialog.dismiss();
				finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_register);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	/**
	 * 数据初始化
	 */
	@Override
	protected void initData() {

	}

	/**
	 * 渲染界面
	 */
	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		et_register_email = (EditText) findViewById(R.id.et_register_email);
		et_register_psw_1 = (EditText) findViewById(R.id.et_register_psw_1);
		et_register_psw_2 = (EditText) findViewById(R.id.et_register_psw_2);
		et_register_user_name = (EditText) findViewById(R.id.et_register_user_name);
		et_register_nick_name = (EditText) findViewById(R.id.et_register_nick_name);
		
		
		btn_back = (Button) findViewById(R.id.btn_back_register);
		btn_register_agress2register = (Button) findViewById(R.id.btn_register_agress2register);

		llyt_register_read_protocol = (LinearLayout) findViewById(R.id.llyt_register_read_protocol);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {

	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_register_agress2register.setOnClickListener(this);
		llyt_register_read_protocol.setOnClickListener(this);
	}

	/**
	 * 
	 * 描述：点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back_register:// 返回
			finish();
			overridePendingTransition(R.anim.translate_horizontal_finish_in,
					R.anim.translate_horizontal_finish_out);
			break;
		case R.id.btn_register_agress2register:// 同意注册，并登录
			register();
			break;
		case R.id.llyt_register_read_protocol:// 阅读性多多服务协议
			startActivity(new Intent(UserRegisterActivity.this,
					ProtocolActivity.class));
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		}
	}

	/**
	 * @描述：注册 2014-8-12
	 */
	private void register() {
		String user_name = et_register_user_name.getText().toString().trim();
		String nick_name = et_register_nick_name.getText().toString().trim();
		String user_email = et_register_email.getText().toString().trim();
		String user_psw_1 = et_register_psw_1.getText().toString().trim();
		String user_psw_2 = et_register_psw_2.getText().toString().trim();

		if (user_name.equals("")) {
			ToastUtil.ToastView(this, "请输入您的用户名");
			return;
		}else if(nick_name.equals("")){
			ToastUtil.ToastView(this, "请输入您的昵称");
			return;
		}else if (user_email.equals("")) {
			ToastUtil.ToastView(this, "请输入您的邮箱");
			return;
		} else if (!user_email.matches(RegexUtil.regex_email)) {
			ToastUtil.ToastView(this, "请输入正确的邮箱地址");
			return;
		} else if (user_psw_1.length() < 6 || user_psw_1.length() > 16) {
			ToastUtil.ToastView(this, "请输入6-16位密码");
			return;
		} else if (!user_psw_1.equals(user_psw_2)) {
			ToastUtil.ToastView(this, "两次输入密码不一致，请重试");
			return;
		} 
		// 请求注册
		HttpUrlProvider.getIntance().getRegister(this,
				new TaskRegisterBack(handler), URLConfig.REGISTER, user_name,
				user_psw_1, user_email,nick_name);
		loadingdialog.show();
	}

	/**
	 * @描述：获取用户信息 2014-8-12
	 */
	public void getUserInfo() {
		String user_name = et_register_user_name.getText().toString().trim();
		HttpUrlProvider.getIntance().getUserInfo(UserRegisterActivity.this,
				new TaskUserInfoBack(handler), URLConfig.USERINFO, user_name);
	}
}
