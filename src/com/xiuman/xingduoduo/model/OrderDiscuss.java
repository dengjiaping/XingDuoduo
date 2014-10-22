package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：OrderDiscuss.java
 * @描述：评价订单
 * @author danding
 * @时间 2014-10-20
 */
public class OrderDiscuss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8520796644379243461L;
	// 要评价的商品的id
	private String id;
	// 评价内容
	private String content;
	// 质量
	private float goodsQuality;
	// 服务
	private float serviceAttitude;
	// 发货
	private float deliverySpeed;

	
	public OrderDiscuss() {
		super();
	}

	public OrderDiscuss(String id, String content, float goodsQuality,
			float serviceAttitude, float deliverySpeed) {
		super();
		this.id = id;
		this.content = content;
		this.goodsQuality = goodsQuality;
		this.serviceAttitude = serviceAttitude;
		this.deliverySpeed = deliverySpeed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Override
	public String toString() {
		return id+content+goodsQuality+serviceAttitude+deliverySpeed;
	}
}
