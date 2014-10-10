/**
 * @ClassName: BBSnormalPost
 * @Description: TODO
 * @author Andrew Lee
 * @date 2014-9-15 下午3:00:36
 */

package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

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
	private ArrayList<String> imgList;
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
	// 所属板块id
	private int forumId;
	private String primeLevel;

	public BBSPost(String content, ArrayList<String> imgList,
			String createTime, String title, String nickname, boolean sex,
			int id, String username, int postTypeId, int replyCount,
			String avatar, int forumId, String primeLevel) {
		super();
		this.content = content;
		this.imgList = imgList;
		this.createTime = createTime;
		this.title = title;
		this.nickname = nickname;
		this.sex = sex;
		this.id = id;
		this.username = username;
		this.postTypeId = postTypeId;
		this.replyCount = replyCount;
		this.avatar = avatar;
		this.forumId = forumId;
		this.primeLevel = primeLevel;
	}

	public ArrayList<String> getImgList() {
		return imgList;
	}

	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
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
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

//	public ArrayList<String> getPostImgs() {
//		ArrayList<String> post_imgs = new ArrayList<String>();
//		post_imgs = HtmlTag.match(contentHtml, "img", "src");
//		if (post_imgs.size() > 0) {
//			for (int i = 0; i < post_imgs.size(); i++) {
//				if (post_imgs.get(i).endsWith(".gif")) {
//					post_imgs.remove(i);
//				}
//			}
//		}
//		return post_imgs;
//	}

}
