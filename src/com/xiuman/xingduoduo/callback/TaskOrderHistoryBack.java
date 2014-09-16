package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Order;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskOrderHistoryBack.java
 * @描述：历史订单获取回调
 * @author danding 2014-8-4
 */
public class TaskOrderHistoryBack implements HttpTaskListener {
	// 传递数据
	private Handler handler;

	public TaskOrderHistoryBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<Order> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<Order>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.NET_SUCCED;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_ERROR_NOTNET;// 无网络
		handler.sendMessage(msg);
	}

}
