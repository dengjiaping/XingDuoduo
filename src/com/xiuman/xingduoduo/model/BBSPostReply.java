package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：BBSPostReply.java
 * @描述：
 * @author Andrew Lee 2014-9-16上午11:27:09
 */
public class BBSPostReply implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4812954585040231855L;
	// 回复内容
	private String content;
	// 回复时间
	private String createTime;
	// 标题
	private String title;
	// 昵称
	private String nickname;
	// id
	private String id;
	//
	private int postTypeId;

	private int editCount;
	// 性别
	private boolean sex;
	// 头像
	private String avatar;
	// 用户名
	private String username;

	public BBSPostReply(String content, String createTime, String title,
			String nickname, String id, int postTypeId, int editCount,
			boolean sex, String avatar, String username) {
		super();
		this.content = content;
		this.createTime = createTime;
		this.title = title;
		this.nickname = nickname;
		this.id = id;
		this.postTypeId = postTypeId;
		this.editCount = editCount;
		this.sex = sex;
		this.avatar = avatar;
		this.username = username;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(int postTypeId) {
		this.postTypeId = postTypeId;
	}

	public int getEditCount() {
		return editCount;
	}

	public void setEditCount(int editCount) {
		this.editCount = editCount;
	}

}