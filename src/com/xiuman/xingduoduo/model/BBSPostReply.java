/**
 * @ClassName: BBSPostReply
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-16 上午11:27:09
 */

package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：BBSPostReply.java
 * @描述：
 * @author Andrew Lee
 * 2014-9-16上午11:27:09
 */
public class BBSPostReply implements Serializable{
	public String contentHtml;
	public String createTime;
	public String title;
	public String user;
	public int id;
	public int postTypeId;
	public int editCount;
	/**
	 * @param contentHtml
	 * @param createTime
	 * @param title
	 * @param user
	 * @param id
	 * @param postTypeId
	 * @param editCount
	 */
	public BBSPostReply(String contentHtml, String createTime, String title,
			String user, int id, int postTypeId, int editCount) {
		super();
		this.contentHtml = contentHtml;
		this.createTime = createTime;
		this.title = title;
		this.user = user;
		this.id = id;
		this.postTypeId = postTypeId;
		this.editCount = editCount;
	}
	

}