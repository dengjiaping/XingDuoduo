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
	// 昵称
	private String nickname;
	// 用户id
	private String userid;
	// 头像地址
	private String head_image;
	// 质量分
	private float goodsQuality;
	// 服务分
	private float serviceAttitude;
	// 发货速度分
	private float deliverySpeed;

	public GoodsDiscuss() {
		super();
	}

	public GoodsDiscuss(String id, String userName, String goodsname,
			String createTime, String content, String contact, String nickname,
			String userid, String head_image, float goodsQuality,
			float serviceAttitude, float deliverySpeed) {
		super();
		this.id = id;
		this.userName = userName;
		this.goodsname = goodsname;
		this.createTime = createTime;
		this.content = content;
		this.contact = contact;
		this.nickname = nickname;
		this.userid = userid;
		this.head_image = head_image;
		this.goodsQuality = goodsQuality;
		this.serviceAttitude = serviceAttitude;
		this.deliverySpeed = deliverySpeed;
	}

	public float getGoodsQuality() {
		return goodsQuality;
	}

	public void setGoodsQuality(float goodsQuality) {
		this.goodsQuality = goodsQuality;
	}

	public float getServiceAttitude() {
		return serviceAttitude;
	}

	public void setServiceAttitude(float serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}

	public float getDeliverySpeed() {
		return deliverySpeed;
	}

	public void setDeliverySpeed(float deliverySpeed) {
		this.deliverySpeed = deliverySpeed;
	}

	public String getHead_image() {
		return head_image;
	}

	public void setHead_image(String head_image) {
		this.head_image = head_image;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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
