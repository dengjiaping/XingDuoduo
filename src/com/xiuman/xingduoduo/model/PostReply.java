package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：PostReply.java
 * @描述：帖子回复
 * @author danding 2014-8-16
 */
public class PostReply implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 541372811210066620L;
	// id
	private String reply_id;
	// 内容
	private String reply_content;
	// 时间
	private String reply_time;
	// 用户
	private User reply_user;
	// 是否是楼主
	private boolean isStarter;

	public PostReply() {
		super();
	}

	public PostReply(String reply_id, String reply_content, String reply_time,
			User reply_user, boolean isStarter) {
		super();
		this.reply_id = reply_id;
		this.reply_content = reply_content;
		this.reply_time = reply_time;
		this.reply_user = reply_user;
		this.isStarter = isStarter;
	}

	public String getReply_id() {
		return reply_id;
	}

	public void setReply_id(String reply_id) {
		this.reply_id = reply_id;
	}

	public String getReply_content() {
		return reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public String getReply_time() {
		return reply_time;
	}

	public void setReply_time(String reply_time) {
		this.reply_time = reply_time;
	}

	public User getReply_user() {
		return reply_user;
	}

	public void setReply_user(User reply_user) {
		this.reply_user = reply_user;
	}

	public boolean isStarter() {
		return isStarter;
	}

	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}

}
