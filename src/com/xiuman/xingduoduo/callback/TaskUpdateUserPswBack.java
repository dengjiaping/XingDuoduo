package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskUserInfoBack.java
 * @描述：修改密码
 * @author danding 2014-8-12
 */
public class TaskUpdateUserPswBack implements HttpTaskListener {

	private Handler handler;

	public TaskUpdateUserPswBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<?> value = new Gson().fromJson(result, ActionValue.class);

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
