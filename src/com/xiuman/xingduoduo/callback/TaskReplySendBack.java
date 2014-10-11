package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.net.HttpTaskListener;
/**
 * @描述：回帖返回
 * @名称：TaskReplySendBack.java
 * @author CSX
 * @日期：2014-10-11
 */
public class TaskReplySendBack implements HttpTaskListener {

	private Handler handler;

	public TaskReplySendBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<?> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<?>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_REPLY_SEND_BACK;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_REPLY_FAILD;
		handler.sendMessage(msg);
	}

}



