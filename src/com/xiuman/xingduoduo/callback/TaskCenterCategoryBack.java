package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.Ad;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskCenterClassifyBack.java
 * @描述：商城界面专区回调（新）
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskCenterCategoryBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskCenterCategoryBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<Ad> value = new Gson().fromJson(result, new TypeToken<ActionValue<Ad>>() {
		}.getType());
		Message msg = Message.obtain();
		msg.what = AppConfig.CENTER_CATEGORY_SUCCED;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.CENTER_CATEGORY_FAILD;
		handler.sendMessage(msg);
	}

}
