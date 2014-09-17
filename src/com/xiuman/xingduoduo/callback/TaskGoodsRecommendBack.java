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
 * @名称：TaskGoodsRecommendBack.java
 * @描述：相关推荐
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class TaskGoodsRecommendBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	/**
	 * @param handler
	 */
	public TaskGoodsRecommendBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<GoodsOne> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<GoodsOne>>() {
				}.getType());
		Message msg = Message.obtain();
		msg.what = AppConfig.GOODS_RECOMMEND_SUCCESS;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.GOODS_RECOMMEND_FAILD;// 无网络
		handler.sendMessage(msg);
	}

}
