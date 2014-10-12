package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskCenterClassifyBack.java
 * @描述：商城界面广告接口回调
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskCenterAdBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskCenterAdBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<?> value = new Gson().fromJson(result, ActionValue.class);
		Message msg = Message.obtain();
		msg.what = AppConfig.CANCEL_ORDER;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {

	}

}
