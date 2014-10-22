package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：DiscussTotal.java
 * @描述：商品评价的综合评分
 * @author danding
 * @时间 2014-10-21
 */
public class DiscussTotal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4219738845986883648L;

	// 质量
	private float goodsQualityTotal;
	// 服务
	private float serviceAttitudeTotal;
	// 速度
	private float deliverySpeedTotal;
	// 综合
	private float comprScore;
	

	public DiscussTotal() {
		super();
	}

	public DiscussTotal(float goodsQualityTotal, float serviceAttitudeTotal,
			float deliverySpeedTotal, float comprScore) {
		super();
		this.goodsQualityTotal = goodsQualityTotal;
		this.serviceAttitudeTotal = serviceAttitudeTotal;
		this.deliverySpeedTotal = deliverySpeedTotal;
		this.comprScore = comprScore;
	}

	public float getGoodsQualityTotal() {
		return goodsQualityTotal;
	}

	public void setGoodsQualityTotal(float goodsQualityTotal) {
		this.goodsQualityTotal = goodsQualityTotal;
	}

	public float getServiceAttitudeTotal() {
		return serviceAttitudeTotal;
	}

	public void setServiceAttitudeTotal(float serviceAttitudeTotal) {
		this.serviceAttitudeTotal = serviceAttitudeTotal;
	}

	public float getDeliverySpeedTotal() {
		return deliverySpeedTotal;
	}

	public void setDeliverySpeedTotal(float deliverySpeedTotal) {
		this.deliverySpeedTotal = deliverySpeedTotal;
	}

	public float getComprScore() {
		return comprScore;
	}

	public void setComprScore(float comprScore) {
		this.comprScore = comprScore;
	}

}
