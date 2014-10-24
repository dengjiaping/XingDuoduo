package com.xiuman.xingduoduo.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.util.NetUtil;

/**
 * 数据获取AsyncTask
 * 
 * @author admin
 */
public class HttpDataTask extends AsyncTask<String, Integer, String> {
	private Context mContext = null;
	private HttpTaskListener httpTaskListener;
	private String httpMethor = "post";
	private List<NameValuePair> httpParams = null;
	private String _url;

	/**
	 * 任务初始化
	 * 
	 * @param context
	 * @param listener
	 *            必须设置回调监听
	 */
	public HttpDataTask(Context context, HttpTaskListener listener) {
		mContext = context;
		setHttpTaskListener(listener);
	}

	private void setHttpTaskListener(HttpTaskListener listener) {
		if (listener != null) {
			httpTaskListener = listener;
		} else {
			throw new IllegalArgumentException("Must Set HttpTaskListener");
		}
	}

	/**
	 * 设置Http连接方式
	 * 
	 * @default Post
	 * @param methor
	 */
	public void setHttpMethod(String methor) {
		httpMethor = methor;
	}

	/**
	 * 设置Post参数
	 * 
	 * @param name
	 * @param params
	 */
	public void setParams(String name, String params) {
		if (httpParams == null) {
			httpParams = new ArrayList<NameValuePair>();
		}
		httpParams.add(new NameValuePair(name, params));
	}

	/**
	 * @描述：设置get请求的参数
	 * @date：2014-7-29
	 * @param methor
	 * @param val
	 * @return
	 */
	public String jointToUrl(String methor, String val) {
		String urlGetStr = methor + "=" + val + "&";
		return urlGetStr;
	}

	/**
	 * @描述：设置请求参数
	 * @date：2014-7-29
	 * @param methor
	 * @param val
	 * @return
	 */
	public String jointToUrl(String methor, int val) {
		String urlGetStr = methor + "=" + val + "&";
		return urlGetStr;
	}
	/**
	 * @描述：设置请求参数
	 * @param methor
	 * @param val
	 * @return
	 * @时间 2014-10-20
	 */
	public String jointToUrl(String methor, float val) {
		String urlGetStr = methor + "=" + val + "&";
		return urlGetStr;
	}

	@Override
	protected String doInBackground(String... params) {
		_url = params[0];
		String result = "";
		Log.d("thread", _url);
//		result = MyApplication.getInstance().getJsonCache(_url);
//		if (!TextUtils.isEmpty(result)) {
//			Log.d("HttpDataTask", "来至jsonCache");
//			return result;
//		}
		//判断网络连接是否正常
		if (!NetUtil.isHasNetAvailable(mContext)) {
			return AppConfig.NET_ERROR_NOTNET + "";
		}
		//判断链接是否可用
//		if (NetUtil.isUrlAvailable(_url)) {
//			return AppConfig.NET_ERROR_NOTNET + "";
//		}

		HttpConnWorker httpDataProvide = HttpUrlProvider.getIntance();

		if (httpMethor.equals("post")) {
			result = httpDataProvide.invokePost(_url, httpParams);
		} else {
			result = httpDataProvide.invokeGet(_url);
		}
		return result;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.d("thread", "结束");
		if (result.equals(AppConfig.NET_ERROR_NOTNET + "")
				|| result.equals(AppConfig.NET_ERROR_CONN + "")) {
			httpTaskListener.dataError(result);
		} else {
			httpTaskListener.dataSucced(result);
			MyApplication.getInstance().setJsonCache(_url, result);
		}
		if (httpParams != null) {
			httpParams.clear();
			httpParams = null;
		}
		super.onPostExecute(result);
	}

}
