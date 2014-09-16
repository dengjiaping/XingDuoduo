package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：OrderId.java
 * @描述：订单id(在请求生成订单时返回的数据实体类)
 * @author danding 2014-8-22
 */
public class OrderId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1430659339315608519L;

	// 订单id
	private String orderId;
	//payid
	private String payId;
	//支付方式
	private String paymentName;
	//订单状态
	private String OrderStatus;
	//支付状态
	private String paymentStatus;
	//订单sn
	private String orderSn;
	//快递
	private String deliveryFee;
	public OrderId() {
		super();
	}
	public OrderId(String orderId, String payId, String paymentName,
			String orderStatus, String paymentStatus,
			String orderSn, String deliveryFee) {
		super();
		this.orderId = orderId;
		this.payId = payId;
		this.paymentName = paymentName;
		this.OrderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.orderSn = orderSn;
		this.deliveryFee = deliveryFee;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	

	

}
