package com.xiuman.xingduoduo.ui.activity;

import android.annotation.SuppressLint;
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
import com.xiuman.xingduoduo.callback.TaskUpdateUserPswBack;
import com.xiuman.xingduoduo.callback.TaskUserInfoBack;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpUrlProvider;
import com.xiuman.xingduoduo.ui.base.Base2Activity;
import com.xiuman.xingduoduo.util.ToastUtil;
import com.xiuman.xingduoduo.view.CustomDialog;
import com.xiuman.xingduoduo.view.CustomDialog2;
import com.xiuman.xingduoduo.view.LoadingDialog;

/**
 * @名称：UserPswUpdateActivity.java
 * @描述：密码修改界面
 * @author danding 2014-8-12
 */
public class UpdateUserPswActivity extends Base2Activity implements
		OnClickListener {

	/*-----------------------------组件----------------------------*/
	// 返回
	private Button btn_back;
	// 提交
	private Button btn_submit;
	// 提交修改
	private Button btn_submit_update;
	// 标题栏
	private TextView tv_title;
	// 原始密码
	private EditText et_update_user_psw_old;
	// 新密码
	private EditText et_update_user_psw_new;
	// 确认密码
	private EditText et_update_user_psw_new2;
	// 加载
	private LoadingDialog loadingdialog;
	// 是否提交修改的Dialog
	private CustomDialog dialog;
	// 取消修改Dialog;
	private CustomDialog2 dialog_cancel;

	/*-----------------------------------------数据-------------------------------*/
	// 从上级界面接收到的用户信息
	private User user;
	// 修改结果
	private ActionValue<?> value;

	// Handler
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case AppConfig.NET_SUCCED:// 请求数据成功(注册成功和注册失败，注册成功则请求个人信息并保存，然后退出界面，失败则留在该界面继续注册)

				value = (ActionValue<?>) msg.obj;
				if (value.isSuccess()) {
					ToastUtil.ToastView(UpdateUserPswActivity.this,
							value.getMessage());
					// 获取用户信息
					getUserInfo();
				} else {
					ToastUtil.ToastView(UpdateUserPswActivity.this,
							value.getMessage());
					loadingdialog.dismiss();
				}

				break;
			case AppConfig.NET_ERROR_NOTNET:// 无网络
				loadingdialog.dismiss();
				ToastUtil.ToastView(UpdateUserPswActivity.this, "网络连接失败，请重试");
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
		setContentView(R.layout.activity_update_user_psw);
		initData();
		findViewById();
		initUI();
		setListener();
	}

	@Override
	protected void initData() {
		user = (User) getIntent().getExtras().getSerializable("user");
	}

	@Override
	protected void findViewById() {
		loadingdialog = new LoadingDialog(this);
		btn_back = (Button) findViewById(R.id.btn_common_back);
		btn_submit = (Button) findViewById(R.id.btn_common_right);
		tv_title = (TextView) findViewById(R.id.tv_common_title);
		et_update_user_psw_old = (EditText) findViewById(R.id.et_update_user_psw_old);
		et_update_user_psw_new = (EditText) findViewById(R.id.et_update_user_psw_new);
		et_update_user_psw_new2 = (EditText) findViewById(R.id.et_update_user_psw_new2);
		btn_submit_update = (Button) findViewById(R.id.btn_submit_update);

	}

	@Override
	protected void initUI() {
		tv_title.setText(R.string.update_psw_title);
	}

	@Override
	protected void setListener() {
		btn_back.setOnClickListener(this);
		btn_submit.setVisibility(View.INVISIBLE);
		btn_submit_update.setOnClickListener(this);
	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_common_back:
			cancelDialog();
			break;
		case R.id.btn_submit_update:// 提交修改
			updatePsw();
			break;
		}
	}

	/**
	 * @描述：修改密码 2014-8-12
	 */
	private void updatePsw() {
		String user_psw_1 = et_update_user_psw_new.getText().toString().trim();
		String user_psw_2 = et_update_user_psw_new2.getText().toString().trim();
		if (user_psw_1.length() < 6 || user_psw_1.length() > 16) {
			ToastUtil.ToastView(this, "请输入6-16位密码");
			return;
		} else if (!user_psw_1.equals(user_psw_2)) {
			ToastUtil.ToastView(this, "两次输入密码不一致，请重试");
			return;
		}
		showUpdateDialog();
	}

	/**
	 * @描述：显示提交密码对话框 2014-8-12
	 */
	private void showUpdateDialog() {

		dialog = new CustomDialog(this, "修改密码", "确定提交新密码？");
		dialog.show();
		dialog.btn_custom_dialog_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String user_name = user.getUserName();
				String user_old = et_update_user_psw_old.getText().toString()
						.trim();
				String user_new = et_update_user_psw_new.getText().toString()
						.trim();
				HttpUrlProvider.getIntance().getUpdateUserPsw(
						UpdateUserPswActivity.this,
						new TaskUpdateUserPswBack(handler),
						URLConfig.UPDATE_PSW, user_name, user_old, user_new);

				dialog.dismiss();
				loadingdialog.show();
			}
		});
		dialog.btn_custom_dialog_cancel
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	}

	/**
	 * @描述：获取用户信息 2014-8-12
	 */
	public void getUserInfo() {
		HttpUrlProvider.getIntance().getUserInfo(UpdateUserPswActivity.this,
				new TaskUserInfoBack(handler), URLConfig.USERINFO,
				user.getUserName());
	}

	/**
	 * @描述：取消修改Dialog 2014-9-23
	 */
	private void cancelDialog() {
		dialog_cancel = new CustomDialog2(this, "确认取消修改？");
		dialog_cancel.show();
		dialog_cancel.btn_custom_dialog_sure
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog_cancel.dismiss();
						finish();
						overridePendingTransition(
								R.anim.translate_horizontal_finish_in,
								R.anim.translate_horizontal_finish_out);
					}
				});
		dialog_cancel.btn_custom_dialog_cancel
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog_cancel.dismiss();
					}
				});
	}

	/**
	 * 按键点击事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 返回键
			cancelDialog();
		}
		return true;
	}
}
