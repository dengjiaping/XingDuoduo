package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPlate;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskBBSPlateBack.java
 * @描述：论坛板块列表回调
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskBBSPlateBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskBBSPlateBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<BBSPlate> value = new Gson().fromJson(result, new TypeToken<ActionValue<BBSPlate>>() {
		}.getType());
		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_PLATE_BACK_SUCCESSED;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_PLATE_BACK_FAILD;
		handler.sendMessage(msg);
	}

}
