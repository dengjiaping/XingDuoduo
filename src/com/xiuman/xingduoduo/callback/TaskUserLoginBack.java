package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskUserLoginBack.java
 * @描述：用户登录
 * @author danding 2014-8-12
 */
public class TaskUserLoginBack implements HttpTaskListener {

	private Handler handler;

	public TaskUserLoginBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<User> value = new Gson()
				.fromJson(result, ActionValue.class);
		String user_info = new Gson().toJson(value.getDatasource().get(0));

		// 保存用户登录信息
		MyApplication.getInstance().saveUserInfo(user_info);
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_SUCCED;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_ERROR_NOTNET;
		handler.sendMessage(msg);
	}

}
