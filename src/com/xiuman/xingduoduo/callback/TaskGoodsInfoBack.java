package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsTwo;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskCenterClassifyBack.java
 * @描述：商品详情界面回调
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskGoodsInfoBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskGoodsInfoBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		System.out.println("数据"+result);
		ActionValue<GoodsTwo> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<GoodsTwo>>() {
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
