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
 * @author Andrew Lee
 * 2014-9-15下午3:00:36
 */
public class BBSPost implements Serializable{
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
	public String content;
	public String contentHtml;
	public String createTime;
	public String title;
	public String user;
	public int id;
	public int postTypeId;
	public int replyCount;
	

}
