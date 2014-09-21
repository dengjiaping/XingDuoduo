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
import com.google.gson.reflect.TypeToken;
import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.model.ActionValue;
import com.xiuman.xingduoduo.model.AliPayStatus;
import com.xiuman.xingduoduo.net.HttpTaskListener;

/**
 * @名称：TaskSendAliPayStatusCodeBack.java
 * @描述：传递支付宝支付结果参数到服务端返回的结果回调
 * @author Andrew Lee
 * 2014-9-13下午4:04:57
 */
public class TaskSendAliPayStatusCodeBack implements HttpTaskListener {

	private Handler handler;

	public TaskSendAliPayStatusCodeBack(Handler handler) {
		super();
		this.handler = handler;
	}

	@Override
	public void dataSucced(String result) {
		ActionValue<AliPayStatus> value = new Gson().fromJson(result,
				new TypeToken<ActionValue<AliPayStatus>>() {
				}.getType());

		Message msg = Message.obtain();
		msg.what = AppConfig.SEND_STATUS_CODE;
		msg.obj = value;
		handler.sendMessage(msg);
	}

	@Override
	public void dataError(String result) {
	}

}