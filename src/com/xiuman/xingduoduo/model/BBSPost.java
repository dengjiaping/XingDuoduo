package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

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
	private ArrayList<PostImg> imgList;
	// 发帖时间
	private String createTime;
	// 最后回复时间
	private String lastTime;
	// 是否精化贴(0非精化，1精化)
	private int jinghua;
	// 是否推荐(非空推荐，空，不推荐)
	private String styleColor;
	//发帖人是否版主
	private boolean moderatorReply;
	// 帖子标题
	private String title;
	// 昵称
	private String nickname;
	// 性别
	private boolean sex;
	// id
	private String id;
	// 用户名
	private String username;
	private String postTypeId;
	// 回复数
	private String replyCount;
	// 头像地址
	private String avatar;
	// 所属板块id
	private int forumId;
	private String primeLevel;

	

	public BBSPost(String content, ArrayList<PostImg> imgList,
			String createTime, String lastTime, int jinghua, String styleColor,
			boolean moderatorReply, String title, String nickname, boolean sex,
			String id, String username, String postTypeId, String replyCount,
			String avatar, int forumId, String primeLevel) {
		super();
		this.content = content;
		this.imgList = imgList;
		this.createTime = createTime;
		this.lastTime = lastTime;
		this.jinghua = jinghua;
		this.styleColor = styleColor;
		this.moderatorReply = moderatorReply;
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

	public boolean isModeratorReply() {
		return moderatorReply;
	}

	public void setModeratorReply(boolean moderatorReply) {
		this.moderatorReply = moderatorReply;
	}

	public String getStyleColor() {
		return styleColor;
	}

	public void setStyleColor(String styleColor) {
		this.styleColor = styleColor;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public int getJinghua() {
		return jinghua;
	}

	public void setJinghua(int jinghua) {
		this.jinghua = jinghua;
	}

	public ArrayList<PostImg> getImgList() {
		return imgList;
	}

	public void setImgList(ArrayList<PostImg> imgList) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(String postTypeId) {
		this.postTypeId = postTypeId;
	}

	public String getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(String replyCount) {
		this.replyCount = replyCount;
	}

	// public ArrayList<String> getPostImgs() {
	// ArrayList<String> post_imgs = new ArrayList<String>();
	// post_imgs = HtmlTag.match(contentHtml, "img", "src");
	// if (post_imgs.size() > 0) {
	// for (int i = 0; i < post_imgs.size(); i++) {
	// if (post_imgs.get(i).endsWith(".gif")) {
	// post_imgs.remove(i);
	// }
	// }
	// }
	// return post_imgs;
	// }

}
