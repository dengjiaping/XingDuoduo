/**
 * @ClassName: BBSnormalPost
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-15 下午3:00:36
 */

package com.xiuman.xingduoduo.model;

import java.io.Serializable;

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
	public String content;
	public String contentHtml;
	public String createTime;
	public String title;
	public String user;
	public int id;
	public int postTypeId;
	public int replyCount;

	/**
	 * @param content
	 * @param contentHtml
	 * @param createTime
	 * @param title
	 * @param user
	 * @param id
	 * @param postTypeId
	 * @param replyCount
	 */
	public BBSPost(String content, String contentHtml, String createTime,
			String title, String user, int id, int postTypeId, int replyCount) {
		super();
		this.content = content;
		this.contentHtml = contentHtml;
		this.createTime = createTime;
		this.title = title;
		this.user = user;
		this.id = id;
		this.postTypeId = postTypeId;
		this.replyCount = replyCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentHtml() {
		if (content.length() > 0 && content.indexOf("[att") > 0) {

			content = content.substring(0, content.indexOf("[att"));
		} else {
			content = "";
		}
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
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

}
