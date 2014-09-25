/**
 * @ClassName: BBSPostReply
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-16 上午11:27:09
 */

package com.xiuman.xingduoduo.model;

import java.io.Serializable;

import com.xiuman.xingduoduo.util.HtmlTag;

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
	private String contentHtml;
	// 回复时间
	private String createTime;
	// 标题
	private String title;
	// 昵称
	private String nickname;
	// id
	private int String;
	//
	private int postTypeId;

	private int editCount;
	// 性别
	private boolean sex;
	// 头像
	private String avatar;
	// 用户名
	private String username;

	public BBSPostReply(java.lang.String contentHtml,
			java.lang.String createTime, java.lang.String title,
			java.lang.String nickname, int string, int postTypeId,
			int editCount, boolean sex, java.lang.String avatar,
			java.lang.String username) {
		super();
		this.contentHtml = contentHtml;
		this.createTime = createTime;
		this.title = title;
		this.nickname = nickname;
		String = string;
		this.postTypeId = postTypeId;
		this.editCount = editCount;
		this.sex = sex;
		this.avatar = avatar;
		this.username = username;
	}

	public String getContentHtml() {
		return HtmlTag.clearTag(contentHtml);
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getString() {
		return String;
	}

	public void setString(int string) {
		String = string;
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