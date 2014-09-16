package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：GoodsDiscuss.java
 * @描述：商品评论
 * @author danding 2014-8-7
 */
public class GoodsDiscuss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4509226479453008295L;
	// 评论id
	private String id;
	// 评论者
	private String userName;
	// 商品名
	private String goodsname;
	// 评论时间
	private String createTime;;
	// 评论内容
	private String content;
	// 邮箱
	private String contact;

	public GoodsDiscuss() {
		super();
	}

	public GoodsDiscuss(String id, String userName, String goodsname,
			String createTime, String content, String contact) {
		super();
		this.id = id;
		this.userName = userName;
		this.goodsname = goodsname;
		this.createTime = createTime;
		this.content = content;
		this.contact = contact;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
