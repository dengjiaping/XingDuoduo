package com.xiuman.xingduoduo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * 名称：MD5Encoder.java
 * 描述：对重要数据进行加密保存
 * @author danding
 * @version
 * 2014-6-18
 */
public class MD5Encoder {
	/**
	 * 
	 * 描述：加密算法(不可逆)
	 * @param psw
	 * @return
	 */
	public static String encode(String message) {

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");

			byte[] bytes = digest.digest(message.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				String s = Integer.toHexString(0xff & bytes[i]);

				if (s.length() == 1) {
					sb.append("0" + s);
				} else {
					sb.append(s);
				}
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("不会发生");
		}

	}
}
