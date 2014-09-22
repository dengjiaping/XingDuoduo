/**
 * @ClassName: TaskPostPublishBack
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-17 下午2:00:31
 */

package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPost;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskPostPublishBack.java
 * @描述：
 * @author Andrew Lee
 * 2014-9-17下午2:00:31
 */
public class TaskPostPublishBack implements HttpTaskListener {

	private Handler handler;

	public TaskPostPublishBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		System.out.println("发帖返回"+result);
		ActionValue<?> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<?>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_PUBLISH_BACK;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
		Message msg = Message.obtain();
		msg.what = AppConfig.NET_ERROR_NOTNET;
		handler.sendMessage(msg);
	}

}
