/**
 * @ClassName: HtmlTag
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-15 下午5:30:57
 */

package com.xiuman.xingduoduo.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xiuman.xingduoduo.app.Mylog;
import com.xiuman.xingduoduo.app.URLConfig;

/**
 * @名称：HtmlTag.java
 * @描述：
 * @author Andrew Lee 2014-9-15下午5:30:57
 */
public class HtmlTag {
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
	private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
	private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
	private static final String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
	private static final String regEx_tag = "<([^>]*)>";
	public static ArrayList<String> match(String source, String element,
			String attr) {
		ArrayList<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr
				+ "=['\"]?(.*?)['\"]?\\s.*?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = URLConfig.PRIVATE_IMG_IP + m.group(1);
			Mylog.i("正则图片地址", r);
			result.add(r);
		}
		return result;
	}

	/**
	 * @param htmlStr
	 * @return 删除Html标签
	 */
	public static String delHTMLTag(String htmlStr) {
		Pattern p_script = Pattern.compile(regEx_script,
				Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern
				.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_space = Pattern
				.compile(regEx_space, Pattern.CASE_INSENSITIVE);
		Matcher m_space = p_space.matcher(htmlStr);
		htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
		return htmlStr.trim(); // 返回文本字符串
	}
	
	/**
	 * @描述：获取清除HTML标签后的str
	 * @param htmlStr
	 * @return
	 * 2014-9-25
	 */
	public static String getTextFromHtml(String htmlStr) {
		htmlStr = delHTMLTag(htmlStr);
		htmlStr = htmlStr.replaceAll("&nbsp;", "");
		htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1);
		return htmlStr;
	}
	/**
	 * @描述：清除1
	 * @param htmlStr
	 * @return
	 * 2014-9-25
	 */
	public static String clearTag(String htmlStr){
		Pattern p_html = Pattern.compile(regEx_tag, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签
		if(htmlStr.contains("<br>")){
			htmlStr.replace("<br>", "\n");
		}
		if(htmlStr.contains("<br/>")){
			htmlStr.replace("<br/>", "\n");
		}
		if(htmlStr.contains("&nbsp;")){
			htmlStr.replace("&nbsp;", " ");
		}
		return htmlStr;
	}
	/**
	 * @描述：清除2
	 * @param htmlStr
	 * @return
	 * 2014-9-25
	 */
	public static String clearTag2(String htmlStr){
		if(htmlStr.contains("<img")){
			htmlStr = htmlStr.replace(htmlStr.substring(htmlStr.indexOf("<img"), htmlStr.lastIndexOf("/>")+2), "");
		}
		return htmlStr;
	}
}
