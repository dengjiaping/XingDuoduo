package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.OrderId;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskAddOrderBack.java
 * @描述：生成订单
 * @author danding 2014-8-12
 */
public class TaskAddOrderBack implements HttpTaskListener {

	private Handler handler;

	public TaskAddOrderBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		System.out.println("添加订单"+result);
		ActionValue<OrderId> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<OrderId>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.CREATE_ORDER;
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
