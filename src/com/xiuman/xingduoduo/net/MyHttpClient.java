/**
 * @ClassName: MYHttpClient
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-17 下午4:42:15
 */

package com.xiuman.xingduoduo.net;

/**
 * @名称：MYHttpClient.java
 * @描述：
 * @author Andrew Lee
 * 2014-9-17下午4:42:15
 */
import com.loopj.android.http.*;
import com.xiuman.xingduoduo.app.URLConfig;

public class MyHttpClient {
  private static final String BASE_URL = URLConfig.BASE_IP;

  private static AsyncHttpClient client = new AsyncHttpClient();

  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.get(getAbsoluteUrl(url), params, responseHandler);
  }

  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.post(getAbsoluteUrl(url), params, responseHandler);
  }

  private static String getAbsoluteUrl(String relativeUrl) {
      return BASE_URL + relativeUrl;
  }
}