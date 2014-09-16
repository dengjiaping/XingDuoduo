package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：Result.java
 * @描述：
 * @author danding 2014-8-12
 */
public class TaskResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4108847464994657306L;
	// 消息
	private String message;
	// 标记(1成功，0失败)
	private String flg;
	// boolean标记
	private boolean flag;

	public TaskResult(String message, String flg, boolean flag) {
		super();
		this.message = message;
		this.flg = flg;
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
