package com.xiuman.xingduoduo.callback;

import java.util.Collections;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Classify;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskClassifyBack.java
 * @描述：商品分类
 * @author danding 2014-8-13
 */
public class TaskClassifyBack implements HttpTaskListener {

	private Handler handler;

	public TaskClassifyBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<Classify> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<Classify>>() {
				}.getType());
		Collections.reverse(value.getDatasource());
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
