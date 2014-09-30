package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.User;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskRegisterBack.java
 * @描述：注册回调接口
 * @author danding 2014-8-12
 */
public class TaskRegisterBack implements HttpTaskListener {

	// 传递请求数据接口获取的数据
	private Handler handler;

	public TaskRegisterBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<User> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<User>>() {
				}.getType());
		String user_info = new Gson().toJson(value.getDatasource().get(0));

		// 保存用户登录信息
		MyApplication.getInstance().saveUserInfo(user_info);
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
