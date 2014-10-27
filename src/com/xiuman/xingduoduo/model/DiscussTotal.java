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
		return (float) ((Math.round(goodsQualityTotal*10))/10.0);
	}

	public void setGoodsQualityTotal(float goodsQualityTotal) {
		this.goodsQualityTotal = goodsQualityTotal;
	}

	public float getServiceAttitudeTotal() {
		return (float) ((Math.round(serviceAttitudeTotal*10))/10.0);
	}

	public void setServiceAttitudeTotal(float serviceAttitudeTotal) {
		this.serviceAttitudeTotal = serviceAttitudeTotal;
	}

	public float getDeliverySpeedTotal() {
		return (float) ((Math.round(deliverySpeedTotal*10))/10.0);
	}

	public void setDeliverySpeedTotal(float deliverySpeedTotal) {
		this.deliverySpeedTotal = deliverySpeedTotal;
	}

	public float getComprScore() {
		return (float) ((Math.round(comprScore*10))/10.0);
	}

	public void setComprScore(float comprScore) {
		this.comprScore = comprScore;
	}

}
