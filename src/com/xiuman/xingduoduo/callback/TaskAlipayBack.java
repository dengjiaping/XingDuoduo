/**
 * @ClassName: TaskAlipayBack
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-13 下午4:00:00
 */

package com.xiuman.xingduoduo.callback;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValuePay;
import com.xiuman.xingduoduo.net.HttpTaskListener;



/**
 * @名称：TaskAlipayBack.java
 * @描述：服务器返回支付宝签名字串
 * @author Andrew Lee
 * 2014-9-13下午4:04:57
 */
public class TaskAlipayBack implements HttpTaskListener {

	private Handler handler;

	public TaskAlipayBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		System.out.println("签名字串返回"+result);
		ActionValuePay value = new Gson().fromJson(result,ActionValuePay.class);

		Message msg = Message.obtain();
		msg.what = AppConfig.ALIPAY_BACK;
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