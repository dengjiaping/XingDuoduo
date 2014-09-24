/**
 * @ClassName: BBSnormalPost
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-15 下午3:00:36
 */

package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.text.Html;

import com.xiuman.xingduoduo.util.HtmlTag;

/**
 * @名称：BBSnormalPost.java
 * @描述：主贴内容
 * @author Andrew Lee 2014-9-15下午3:00:36
 */
public class BBSPost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7671161462929406109L;
	// 内容
	private String content;
	// 图片地址
	private String contentHtml;
	// 发帖时间
	private String createTime;
	// 帖子标题
	private String title;
	// 昵称
	private String nickname;
	// 性别
	private boolean sex;
	// id
	private int id;
	// 用户名
	private String username;

	private int postTypeId;
	// 回复数
	private int replyCount;
	// 头像地址
	private String avatar;
	//
	private String primeLevel;

	public BBSPost(String content, String contentHtml, String createTime,
			String title, String nickname, boolean sex, int id,
			String username, int postTypeId, int replyCount, String avatar,
			String primeLevel) {
		super();
		this.content = content;
		this.contentHtml = contentHtml;
		this.createTime = createTime;
		this.title = title;
		this.nickname = nickname;
		this.sex = sex;
		this.id = id;
		this.username = username;
		this.postTypeId = postTypeId;
		this.replyCount = replyCount;
		this.avatar = avatar;
		this.primeLevel = primeLevel;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPrimeLevel() {
		return primeLevel;
	}

	public void setPrimeLevel(String primeLevel) {
		this.primeLevel = primeLevel;
	}

	public String getContent() {
		System.out.println("忒自内容" + content);
		if (content.contains("[att")) {
			content = content.substring(0, content.indexOf("[att"));
			if (content.contains("\r\n\r\n"))
				content.replace("\r\n\r\n", "\r\n");
		} else {
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentHtml() {
		return contentHtml;
	}

	public void setContentHtml(String contentHtml) {
		this.contentHtml = contentHtml;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(int postTypeId) {
		this.postTypeId = postTypeId;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

	public ArrayList<String> getPostImgs() {
		ArrayList<String> post_imgs = new ArrayList<String>();
		post_imgs = HtmlTag.match(contentHtml, "img", "src");

		return post_imgs;
	}

}
