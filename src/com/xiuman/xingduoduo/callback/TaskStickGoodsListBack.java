package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsOne;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskStickGoodsListBack.java
 * @描述：首页置顶商品列表接口回调
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskStickGoodsListBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskStickGoodsListBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<GoodsOne> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<GoodsOne>>() {
				}.getType());
		Message msg = Message.obtain();
		msg.what = AppConfig.STICK_SUCCED;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.STICK_FAILD;// 无网络
		handler.sendMessage(msg);
	}

}
