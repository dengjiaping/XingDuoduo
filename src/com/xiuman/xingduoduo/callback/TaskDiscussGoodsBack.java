package com.xiuman.xingduoduo.callback;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.TaskResult;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskDeleteOrderBack.java
 * @描述：平价商品接口
 * @author danding 2014-8-12
 */
public class TaskDiscussGoodsBack implements HttpTaskListener {

	private Handler handler;

	public TaskDiscussGoodsBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ArrayList<TaskResult> task_results = new Gson().fromJson(result,
				new TypeToken<ArrayList<TaskResult>>() {
				}.getType());
		Message msg = Message.obtain();
		msg.what = AppConfig.UPDATE_ORDER;
		msg.obj = task_results.get(0);
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_ERROR_NOTNET;
		handler.sendMessage(msg);
	}

}
