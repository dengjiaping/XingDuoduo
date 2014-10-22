package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

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
	// 等级组别
	private String groupName;
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
	private String postTypeId;

	private String editCount;
	// 性别
	private boolean sex;
	// 头像
	private String avatar;
	// 用户名
	private String username;
	// 图片列表
	private ArrayList<PostImg> imgList;

	public BBSPostReply(String groupName, String content, String createTime,
			String title, String nickname, String id, String postTypeId,
			String editCount, boolean sex, String avatar, String username,
			ArrayList<PostImg> imgList) {
		super();
		this.groupName = groupName;
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
		this.imgList = imgList;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public ArrayList<PostImg> getImgList() {
		return imgList;
	}

	public void setImgList(ArrayList<PostImg> imgList) {
		this.imgList = imgList;
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

	public String getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(String postTypeId) {
		this.postTypeId = postTypeId;
	}

	public String getEditCount() {
		return editCount;
	}

	public void setEditCount(String editCount) {
		this.editCount = editCount;
	}

}