package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskDeleteOrderBack.java
 * @描述：删除订单
 * @author danding 2014-8-12
 */
public class TaskDeleteOrderBack implements HttpTaskListener {

	private Handler handler;

	public TaskDeleteOrderBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<?> value = new Gson().fromJson(result, ActionValue.class);

		Message msg = Message.obtain();
		msg.what = AppConfig.UPDATE_ORDER;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_ERROR_COMMON;// 无网络
		handler.sendMessage(msg);
	}

}
