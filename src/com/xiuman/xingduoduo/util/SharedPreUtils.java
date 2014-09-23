package com.xiuman.xingduoduo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * 名称：SharedPreUtils.java
 * 描述：保存信息到SharedPreferences
 * @author danding
 * @version
 * 2014-6-18
 */
public class SharedPreUtils {
	/**
	 * 
	 * 描述：将布尔值写入SharedPreferences文件中
	 * @param context 上下文
	 * @param result boolean
	 * @param filename 文件名
	 * @param keyword boolean的键
	 */
	public static void setBoolean(Context context,boolean result,String filename,String keyword){
		SharedPreferences sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putBoolean(keyword, result);
        editor.commit();
	}
	/**
	 * 
	 * 描述：获取存储的boolean数据
	 * @param context
	 * @param filename
	 * @param keyword
	 * @return
	 */
	public static boolean getBoolean(Context context,boolean default_result,String filename,String keyword){
		SharedPreferences sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		boolean result = sp.getBoolean(keyword, default_result);
		
		return result;
	}
	/**
	 * 
	 * 描述：存储String数据到SharedPreferences中
	 * @param context
	 * @param result
	 * @param filename
	 * @param keyword
	 */
	public static void setString(Context context,String result,String filename,String keyword){
		SharedPreferences sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(keyword, result);
        editor.commit();
	}
	/**
	 * 
	 * 描述：获取存储的String数据
	 * @param context
	 * @param filename 文件名
	 * @param keyword 键
	 * @return
	 */
	public static String getString(Context context,String filename,String keyword){
		SharedPreferences sp = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
		String result = sp.getString(keyword, "");
		return result;
	}
	
}
