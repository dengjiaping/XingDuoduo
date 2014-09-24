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
 * @author Andrew Lee
 * 2014-9-15下午5:30:57
 */
public class HtmlTag {
	
	public static ArrayList<String> match(String source, String element, String attr) {
		ArrayList<String> result = new ArrayList<String>();
		String reg = "<" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?\\s.*?>";
		Matcher m = Pattern.compile(reg).matcher(source);
		while (m.find()) {
			String r = URLConfig.PRIVATE_IMG_IP+m.group(1);
			Mylog.i("正则图片地址", r);
			result.add(r);
		}
		return result;
	}
	

}
