package com.xiuman.xingduoduo.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import android.util.Log;

import com.xiuman.xingduoduo.app.AppConfig;
import com.xiuman.xingduoduo.app.MyApplication;
import com.xiuman.xingduoduo.app.URLConfig;

/**
 * @名称：HttpConnWorker.java
 * @描述：Http连接
 * @author danding
 * @version
 * @date：2014-7-29
 */
public abstract class HttpConnWorker {
	private static String TAG = "HttpConnWorker";
	public static final String UTF_8 = "UTF-8";
	public static final String DESC = "descend";
	public static final String ASC = "ascend";

	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	/** 重连次数 */
	private final static int RETRY_TIME = 3;

	private static String appCookie;
	private static String appUserAgent;
	public static MyApplication appContext;

	/**
	 * 
	 * @描述：请求消息头
	 * @date：2014-7-29
	 * @return
	 */
	private static String getUserAgent() {
		if (appContext == null) {
			appContext = MyApplication.getInstance();
		}
		if (appUserAgent == null || appUserAgent == "") {
			StringBuilder ua = new StringBuilder("SouTao.com");
			ua.append('/' + appContext.getPackageInfo().versionName);// App版本
			ua.append("/Android");// 手机系统平台
			// ua.append("/"+android.os.Build.VERSION.RELEASE);//手机系统版本
			// ua.append("/"+android.os.Build.MODEL); //手机型号
			appUserAgent = ua.toString();
		}
		return appUserAgent;
	}

	/**
	 * 初始化HttpClient
	 * 
	 * @return
	 */
	private static HttpClient initHttpClient() {
		HttpClient httpClient = new HttpClient();
		// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
		httpClient.getParams().setCookiePolicy(
				CookiePolicy.BROWSER_COMPATIBILITY);
		// 设置 默认的超时重试处理策略
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// 设置 连接超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(TIMEOUT_CONNECTION);
		// 设置 读数据超时时间
		httpClient.getHttpConnectionManager().getParams()
				.setSoTimeout(TIMEOUT_SOCKET);
		// 设置 字符集
		httpClient.getParams().setContentCharset(UTF_8);
		return httpClient;
	}

	/**
	 * 初始化HttpPost
	 * 
	 * @param url
	 * @param cookie
	 * @param userAgent
	 * @return
	 */
	private static PostMethod initHttpPost(String url, String cookie,
			String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		// 设置 请求超时时间
		httpPost.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpPost.setRequestHeader("Host", URLConfig.BASE_IP);
		httpPost.setRequestHeader("Connection", "Keep-Alive");
		// httpPost.setRequestHeader("Cookie", cookie);
		httpPost.setRequestHeader("User-Agent", userAgent);
		return httpPost;
	}

	/**
	 * 初始化HttpGet
	 * 
	 * @param url
	 * @param cookie
	 * @param userAgent
	 * @return
	 */
	private static GetMethod initHttpGet(String url, String cookie,
			String userAgent) {
		GetMethod httpGet = new GetMethod(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		httpGet.setRequestHeader("Host", "");
		httpGet.setRequestHeader("Connection", "Keep-Alive");
		httpGet.setRequestHeader("Cookie", cookie);
		httpGet.setRequestHeader("User-Agent", userAgent);
		return httpGet;
	}

	/**
	 * 
	 * @描述：设置连接Cookie
	 * @date：2014-7-29
	 * @param httpClient
	 */
	private static void setCookie(HttpClient httpClient) {
		Cookie[] cookies = httpClient.getState().getCookies();
		String tmpcookies = "";
		for (Cookie ck : cookies) {
			tmpcookies += ck.toString() + ";";
		}
		// 保存cookie
		if (appContext != null && tmpcookies != "") {
			// appContext.setProperty("cookie", tmpcookies);
			appCookie = tmpcookies;
		}
	}

	/**
	 * HttpGet调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String invokeGet(String url) {
//		Log.e(TAG, "------HttpPostStart请求服务器开始--------" + url);
		String userAgent = getUserAgent();
		HttpClient httpClient = null;
		GetMethod httpGet = null;
		String responseBody = "";
		int time = 0;
		do {
			try {
				httpClient = initHttpClient();
				httpGet = initHttpGet(url, "", userAgent);
				int statusCode = httpClient.executeMethod(httpGet);
				if (statusCode != HttpStatus.SC_OK) {
					return AppConfig.NET_ERROR_CONN + "";
				}
				// responseBody = httpGet.getResponseBodyAsString();
				responseBody = getStreamToString(httpGet
						.getResponseBodyAsStream());
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				return AppConfig.NET_ERROR_CONN + "";
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				return AppConfig.NET_ERROR_CONN + "";
			} finally {
				try {
					// 释放连接
					httpGet.releaseConnection();
					httpClient = null;
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
			}
		} while (time < RETRY_TIME);
		// Log.d(TAG, "--返回信息--"+responseBody);
		return responseBody;
	}

	/**
	 * 公用post方法
	 * 
	 * @param url
	 * @param params
	 * @param files
	 * @throws AppException
	 */
	private String _invokePost(String url, List<NameValuePair> params) {
		Log.e(TAG, "------HttpPostStart请求服务器开始--------" + url);
		String userAgent = getUserAgent();
		HttpClient httpClient = null;
		PostMethod httpPost = null;
		String responseBody = "";
		int time = 0;
		int length = (params == null ? 0 : params.size());
		NameValuePair[] parts = new NameValuePair[length];
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				parts[i] = new NameValuePair(params.get(i).getName(), params
						.get(i).getValue());
			}
		} else {
			throw new IllegalArgumentException("Params is Null");
		}

		do {
			try {
				httpClient = initHttpClient();
				httpPost = initHttpPost(url, "", userAgent);
				httpPost.setRequestBody(parts);
				int statusCode = httpClient.executeMethod(httpPost);
				System.out.println("httpPost连接"+httpPost.getURI()+httpPost.getRequestHeaders());
				if (statusCode != HttpStatus.SC_OK) {
					responseBody = AppConfig.NET_ERROR_CONN + "";
				} else if (statusCode == HttpStatus.SC_OK) {
					// responseBody = httpPost.getResponseBodyAsString();
					responseBody = getStreamToString(httpPost
							.getResponseBodyAsStream());
				}
				break;
			} catch (HttpException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				e.printStackTrace();
				responseBody = AppConfig.NET_ERROR_CONN + "";
				// throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if (time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					continue;
				}
				// 发生网络异常
				e.printStackTrace();
				responseBody = AppConfig.NET_ERROR_CONN + "";
				// throw AppException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		} while (time < RETRY_TIME);

		// Log.d(TAG, "--返回信息--"+responseBody);

		return responseBody;
	}

	/**
	 * 
	 * @描述：将流中的数据转化为字符串
	 * @date：2014-7-29
	 * @param inputStream
	 * @return
	 */
	private String getStreamToString(InputStream inputStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		StringBuffer stringBuffer = new StringBuffer();
		String str = "";
		try {
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	/**
	 * HttpPost方法
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String invokePost(String url, List<NameValuePair> params) {
		return _invokePost(url, params);
	}

	/**
	 * @描述：设置参数
	 * @date：2014-7-29
	 * @param str
	 * @param object
	 */
	public abstract void setParems(String str, Object object);

}
