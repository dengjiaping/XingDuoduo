package com.xiuman.xingduoduo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
/**
 * 
 * 名称：NetUtil.java
 * 描述：工具类，判断网络连接是否可用
 * @author danding
 * @version
 * 2014-6-3
 */
public class NetUtil {
	
	/**
	 * 判断地址可用
	 * @param url
	 * @return
	 */
	public static boolean isUrlAvailable(String url) {
		if ("" != url && url.startsWith("http://")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否有可用网络 无线和3G
	 * @param context
	 * @return
	 */
	public static boolean isHasNetAvailable(Context context) {
		if (isWifiAvailable(context)) {
			return true;
		} else if (isNetAvailable(context)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判读是否Wifi可用
	 * @param context
	 * @return
	 */
	public static boolean isWifiAvailable(Context context) {
		WifiManager manager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();
		int wifiIP;
		if (info == null) {
			wifiIP = 0;
		} else {
			wifiIP = info.getIpAddress();
		}

		if (manager.isWifiEnabled() && wifiIP != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 除wifi外的网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null == manager) {
			return false;
		} else {
			NetworkInfo info = manager.getActiveNetworkInfo();
			if (null == info) {
				return false;
			} else {
				if (info.isAvailable()) {
					return true;
				}
			}
		}
		return false;
	}
}
