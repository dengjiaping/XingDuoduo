package com.xiuman.xingduoduo.ui.activity;

import u.aly.T;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiuman.xingduoduo.R;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.URLConfig;
import com.xiuman.xingduoduo.callback.TaskUserInfoBack;
import com.xiuman.xingduoduo.callback.TaskUserLoginBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * 
 * 名称：LoginActivity.java 描述：登录界面
 * 
 * @author danding
 * @version 2014-6-5
 */
public class UserLoginActivity extends Base2Activity implements OnClickListener {

	/*--------------------------------组件----------------------------*/
	// 用户名
	private EditText et_login_user_name;
	// 密码
	private EditText et_login_user_psw;
	// 登录
	private Button btn_login_login;
	//注册文字
	private TextView tv_register;
	// 加载进度
	private LoadingDialog loadingdialog;
	// 返回
	private Button btn_login_back;
	// 标题栏
	private TextView tv_title;
	// 右侧
	private Button btn_right;
	/*---------------------------------数据----------------------------------*/
	// 登录结果
	private ActionValue<?> value = new ActionValue<T>();

	// Handler
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功(注册成功和注册失败，注册成功则请求个人信息并保存，然后退出界面，失败则留在该界面继续注册)

				value = (ActionValue<?>) msg.obj;
				if (value.isSuccess()) {// 登录成功
					ToastUtil.ToastView(UserLoginActivity.this,
							value.getMessage());
					// 获取用户信息
					// getUserInfo();
					loadingdialog.dismiss(UserLoginActivity.this);
					finish();
				} else {
					ToastUtil.ToastView(UserLoginActivity.this,
							value.getMessage());
					loadingdialog.dismiss(UserLoginActivity.this);
					et_login_user_name.setFocusable(true);
				}

				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss(UserLoginActivity.this);
				ToastUtil.ToastView(UserLoginActivity.this, "网络连接失败，请重试");
				break;
			case AppConfig.SUCCESS_USER_INFO:// 获取用户信息成功
				loadingdialog.dismiss(UserLoginActivity.this);
				finish();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
		et_login_user_name = (EditText) findViewById(R.id.et_login_user_name);
		et_login_user_psw = (EditText) findViewById(R.id.et_login_user_psw);
		btn_login_login = (Button) findViewById(R.id.btn_login_login);
		btn_login_back = (Button) findViewById(R.id.btn_common_back);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		btn_right = (Button) findViewById(R.id.btn_common_right);
		tv_register = (TextView) findViewById(R.id.tv_register);
	}

	/**
	 * 界面初始化
	 */
	@Override
	protected void initUI() {
		tv_title.setText("登录");
		btn_right.setVisibility(View.INVISIBLE);
		tv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	}

	/**
	 * 设置监听
	 */
	@Override
	protected void setListener() {
		btn_login_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		btn_login_back.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (loadingdialog == null) {
			loadingdialog = new LoadingDialog(this);
		}
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login_login:// 登陆
			login();
			break;
		case R.id.tv_register:// 注册帐号
			startActivity(new Intent(UserLoginActivity.this,
					UserRegisterActivity.class));
			finish();
			overridePendingTransition(R.anim.translate_horizontal_start_in,
					R.anim.translate_horizontal_start_out);
			break;
		case R.id.btn_common_back:
			finish();
			overridePendingTransition(R.anim.translate_vertical_finish_in,
					R.anim.translate_vertical_finish_out);
			break;
		}
	}

	/**
	 * @描述：用户登录 2014-8-12
	 */
	private void login() {
		String user_name = et_login_user_name.getText().toString().trim();
		String user_psw = et_login_user_psw.getText().toString().trim();
		if(user_name.equals("")){
			ToastUtil.ToastView(UserLoginActivity.this, "请输入您的用户名！");
			return;
		}else if(user_psw.equals("")){
			ToastUtil.ToastView(UserLoginActivity.this, "请输入您的密码！");
			return;
		}
		
		loadingdialog.show(UserLoginActivity.this);
		HttpUrlProvider.getIntance().getUserLogin(this,
				new TaskUserLoginBack(handler), URLConfig.USER_LOGION,
				user_name, user_psw);
	}

	/**
	 * @描述：获取用户信息 2014-8-12
	 */
	public void getUserInfo() {
		String user_name = et_login_user_name.getText().toString().trim();
		HttpUrlProvider.getIntance().getUserInfo(UserLoginActivity.this,
				new TaskUserInfoBack(handler), URLConfig.USERINFO, user_name);
		loadingdialog.show(UserLoginActivity.this);
	}

	/**
	 * 自定义返回键的效果
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
			finish();
			overridePendingTransition(R.anim.translate_vertical_finish_in,
					R.anim.translate_vertical_finish_out);
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		loadingdialog.dismiss(UserLoginActivity.this);
		loadingdialog = null;
	}
}
