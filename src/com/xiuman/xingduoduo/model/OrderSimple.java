package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：OrderSimple.java
 * @描述：订单列表的简略订单信息
 * @author danding 2014-8-4
 */
public class OrderSimple implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1607943306633291013L;
	// 订单id
	private String id;
	// 订单状态
	private String OrderStatus;
	// 订单创建时间
	private String create_date;
	// 订单总价
	private String totalAmount;
	// 支付状态
	private String paymentStatus;
	// 商品数量
	private int quanity;
	// 订单sn
	private String orderSn;
	// 订单图片
	private String SmallGoodsImagePath;

	public OrderSimple() {
		super();
	}

	public OrderSimple(String id, String orderStatus, String create_date,
			String totalAmount, String paymentStatus, int quanity,
			String orderSn, String smallGoodsImagePath) {
		super();
		this.id = id;
		OrderStatus = orderStatus;
		this.create_date = create_date;
		this.totalAmount = totalAmount;
		this.paymentStatus = paymentStatus;
		this.quanity = quanity;
		this.orderSn = orderSn;
		SmallGoodsImagePath = smallGoodsImagePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderStatus() {
		return OrderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getSmallGoodsImagePath() {
		return SmallGoodsImagePath;
	}

	public void setSmallGoodsImagePath(String smallGoodsImagePath) {
		SmallGoodsImagePath = smallGoodsImagePath;
	}

}
