/**
 * @ClassName: TaskPostReplyBack
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-16 上午11:42:17
 */

package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.BBSPostReply;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskPostReplyBack.java
 * @描述：
 * @author Andrew Lee 2014-9-16上午11:42:17
 */
public class TaskPostReplyBack implements HttpTaskListener {

	private Handler handler;

	public TaskPostReplyBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<BBSPostReply> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<BBSPostReply>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.BBS_REPLY_POST_BACK;
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
