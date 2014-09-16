package com.xiuman.xingduoduo.net;

/**
 * @名称：HttpDataTask.java
 * @描述：HttpTask回调监听
 * @author danding
 * @version
 * @date：2014-7-29
 */
public interface HttpTaskListener {
	/**
	 * @描述：HttpTask请求成功之后进行数据处理
	 * @date：2014-7-29
	 * @param result
	 */
	void dataSucced(String result);

	/**
	 * @描述：HttpTask请求失败之后进行数据处理
	 * @date：2014-7-29
	 * @param result
	 */
	void dataError(String result);
}