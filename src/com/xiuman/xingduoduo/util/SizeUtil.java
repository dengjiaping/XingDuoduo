package com.xiuman.xingduoduo.util;

import android.content.Context;

/**
 * 
 * 名称：SizeUtil.java 描述：处理和长度宽度有关的类
 * 
 * @author danding
 * @version 2014-6-6
 */
public class SizeUtil {
	/**
	 * dip换算成px
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
	/**
	 * px换算成dip
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
