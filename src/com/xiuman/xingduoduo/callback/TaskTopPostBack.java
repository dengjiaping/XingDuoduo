/**
 * @ClassName: TaskTopPostBack
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-15 下午3:56:51
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
 * @名称：TaskTopPostBack.java
 * @描述：
 * @author Andrew Lee
 * 2014-9-15下午3:56:51
 */
public class TaskTopPostBack implements HttpTaskListener {

	private Handler handler;

	public TaskTopPostBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<BBSPost> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<BBSPost>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_TOP_POST_BACK;
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