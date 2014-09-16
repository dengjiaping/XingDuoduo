package com.xiuman.xingduoduo.util;

/**
 * 
 * @名称：RegexUtil.java
 * @描述：正则表达式验证(手机号码)
 * @author danding
 * @version
 * @date：2014-6-23
 */
public class RegexUtil {
	/**
	 * 电话号码匹配(包括固话和手机号码)
	 */
	public static String regex_phone = "(0\\d{2,3}\\d{7,8})|(1[3458]\\d{9})";
	/**
	 * 密码匹配
	 */
	public static String regex_psw = "\\w{6,16}";
	/**
	 * 邮政编码匹配
	 */
	public static String regex_ems = "\\d{6}";
	/**
	 * 邮箱匹配
	 */
	public static String regex_email = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	/**
	 * 密码匹配
	 */
	public static String regex_user = "^[a-zA-Z]\\w{6,16}$";
}
