package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.GoodsCart;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskShoppingCartBack.java
 * @描述：购物车列表
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskShoppingCartBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskShoppingCartBack(Handler handler) {
		super();
		this.handler = handler;
	}

	/**
	 * 获取数据成功
	 */
	@Override
	public void dataSucced(String result) {
		System.out.println("购物车"+result);
		ActionValue<GoodsCart> goods = new Gson().fromJson(result,
				new TypeToken<ActionValue<GoodsCart>>() {
				}.getType());
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_SUCCED;
		msg.obj = goods;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_ERROR_NOTNET;//无网络
		handler.sendMessage(msg);
	}

}
