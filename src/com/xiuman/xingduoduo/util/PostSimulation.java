/**
 * @ClassName: PostSimulation
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-21 下午12:10:15
 */

package com.xiuman.xingduoduo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.xiuman.xingduoduo.app.Mylog;

/**
 * @名称：PostSimulation.java
 * @描述：
 * @author Andrew Lee 2014-9-21下午12:10:15
 */
public class PostSimulation {
	
	private static PostSimulation instance = null;
	private Vector properties = null;
	private String multipart_form_data = "multipart/form-data";
	private String twoHyphens = "--";
	private String boundary = "****************fD4fH3gL0hK7aI6"; // 数据分隔符
	private String lineEnd = "\r\n" ;// The value is
															// "\r\n" in
															// Windows.
	private PostSimulation() {
	}
	
	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new PostSimulation();
		}
	}
	
	public static PostSimulation getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}
	public Vector getProperties() {
		return properties;
	}

	public void updateProperties() {
		PostSimulation shadow = new PostSimulation();
		properties = shadow.getProperties();
	}
	/*
	 * 上传图片内容，格式请参考HTTP 协议格式。
	 * 人人网Photos.upload中的”程序调用“http://wiki.dev.renren.com/
	 * wiki/Photos.upload#.E7.A8.8B.E5.BA.8F.E8.B0.83.E7.94.A8 对其格式解释的非常清晰。
	 * 格式如下所示： --****************fD4fH3hK7aI6 Content-Disposition: form-data;
	 * name="upload_file"; filename="apple.jpg" Content-Type: image/jpeg
	 * 
	 * 这儿是文件的内容，二进制流的形式
	 */
	private void addImageContent(String fileName, DataOutputStream output) {
			File file=new File(fileName);
			StringBuilder split = new StringBuilder();
			split.append(twoHyphens + boundary + lineEnd);
			split.append("Content-Disposition: form-data; name=\""
					+ "attachment" + "\"; filename=\""
					+ fileName+ "\"" + lineEnd);
			split.append("Content-Type: " + fileName.substring(fileName.lastIndexOf(".")+1) + lineEnd);
			Mylog.i("图片类型", fileName.substring(fileName.lastIndexOf(".")));
			split.append(lineEnd);
			try {
				// 发送图片数据
				output.writeBytes(split.toString());
				FileInputStream fStream = new FileInputStream(file);
				// /* 设置每次写入1024bytes */
				 int bufferSize = 1024;
				 byte[] buffer = new byte[bufferSize];
				 int length = -1;
				// /* 从文件读取数据至缓冲区 */
				 while ((length = fStream.read(buffer)) != -1) {
				// /* 将资料写入DataOutputStream中 */
					 output.write(buffer, 0, length);
				 }
				output.writeBytes(lineEnd);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		
	}

	/*
	 * 构建表单字段内容，格式请参考HTTP 协议格式（用FireBug可以抓取到相关数据）。(以便上传表单相对应的参数值) 格式如下所示：
	 * --****************fD4fH3hK7aI6 Content-Disposition: form-data;
	 * name="action" // 一空行，必须有 upload
	 */
	private void addFormField(String key,String value,
			DataOutputStream output) {
		StringBuilder sb = new StringBuilder();
			sb.append(twoHyphens + boundary + lineEnd);
			sb.append("Content-Disposition: form-data; name=\""
					+ key + "\"" + lineEnd);
			sb.append(lineEnd);
			sb.append(value + lineEnd);
		try {
			output.writeBytes(sb.toString());// 发送表单字段数据
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 直接通过 HTTP 协议提交数据到服务器，实现表单提交功能。
	 * 
	 * @param actionUrl
	 *            上传路径
	 * @param params
	 *            请求参数key为参数名，value为参数值
	 * @param files
	 *            上传文件信息
	 * @return 返回请求结果
	 */
	public String post(String actionUrl,List<String> fileNames,List<String> keys,Map<String ,String> maps) {
		HttpURLConnection conn = null;
		DataOutputStream output = null;
		BufferedReader input = null;
		try {
			URL url = new URL(actionUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(120000);
			conn.setDoInput(true); // 允许输入
			conn.setDoOutput(true); // 允许输出
			conn.setUseCaches(false); // 不使用Cache
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Type", multipart_form_data
					+ "; boundary=" + boundary);

			conn.connect();
			output = new DataOutputStream(conn.getOutputStream());
//			addFormField("scode", "", output);
//			addFormField("userId", "215", output);
//			addFormField("category", "1", output);
//			addFormField("forumId", "1", output);
//			addFormField("postTypeId", "1", output);
//			addFormField("title", "title", output);
//			addFormField("content", "content", output);
//			
			for(String key : keys){
				addFormField(key, maps.get(key), output);
			}
			
			for(String fileName:fileNames){
				addImageContent(fileName, output);
				
			}
			

//			addImageContent(files, output); // 添加图片内容
//
//			addFormField(params, output); // 添加表单字段内容

			output.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);// 数据结束标志
			output.flush();

			int code = conn.getResponseCode();
			if (code != 200) {
				throw new RuntimeException("请求‘" + actionUrl + "’失败！");
			}

			input = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder response = new StringBuilder();
			String oneLine;
			while ((oneLine = input.readLine()) != null) {
				response.append(oneLine + lineEnd);
			}

			return response.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			// 统一释放资源
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			if (conn != null) {
				conn.disconnect();
			}
		}
	}

}