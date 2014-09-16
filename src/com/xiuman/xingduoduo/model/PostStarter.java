package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @名称：PostHost.java
 * @描述：帖子列表实例
 * @author danding 2014-8-8
 */
public class PostStarter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2088420216178871370L;
	// 贴子id
	private String post_id;
	// 发表时间
	private String post_time;
	// 帖子标题
	private String post_title;
	// 帖子内容
	private String post_content;
	// 发表方式(匿名？)
	private String post_style;
	// 帖子标签(推荐、精化等)
	private String post_tag;
	// 楼主信息
	private User post_user;
	// 回复数
	private int post_reply;
	// 图片列表(如果有)
	private ArrayList<String> post_imgs;

	public PostStarter() {
		super();
	}

	public PostStarter(String post_id, String post_time, String post_title,
			String post_content, String post_style, String post_tag,
			User post_user, int post_reply, ArrayList<String> post_imgs) {
		super();
		this.post_id = post_id;
		this.post_time = post_time;
		this.post_title = post_title;
		this.post_content = post_content;
		this.post_style = post_style;
		this.post_tag = post_tag;
		this.post_user = post_user;
		this.post_reply = post_reply;
		this.post_imgs = post_imgs;
	}

	public String getPost_id() {
		return post_id;
	}

	public void setPost_id(String post_id) {
		this.post_id = post_id;
	}

	public String getPost_time() {
		return post_time;
	}

	public void setPost_time(String post_time) {
		this.post_time = post_time;
	}

	public String getPost_title() {
		return post_title;
	}

	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}

	public String getPost_content() {
		return post_content;
	}

	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}

	public String getPost_style() {
		return post_style;
	}

	public void setPost_style(String post_style) {
		this.post_style = post_style;
	}

	public String getPost_tag() {
		return post_tag;
	}

	public void setPost_tag(String post_tag) {
		this.post_tag = post_tag;
	}

	public User getPost_user() {
		return post_user;
	}

	public void setPost_user(User post_user) {
		this.post_user = post_user;
	}

	public int getPost_reply() {
		return post_reply;
	}

	public void setPost_reply(int post_reply) {
		this.post_reply = post_reply;
	}

	public ArrayList<String> getPost_imgs() {
		return post_imgs;
	}

	public void setPost_imgs(ArrayList<String> post_imgs) {
		this.post_imgs = post_imgs;
	}

}
