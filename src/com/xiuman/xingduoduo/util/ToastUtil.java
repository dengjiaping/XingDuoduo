package com.xiuman.xingduoduo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @名称：ToastUtil.java
 * @描述：Toast工具类
 * @author danding
 * @version 
 * @date：2014-7-23
 */
public class ToastUtil {

	/**
	 * 
	 * @描述：Toast
	 * @date：2014-7-23
	 * @param context
	 * @param text
	 */
	public static void ToastView(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
