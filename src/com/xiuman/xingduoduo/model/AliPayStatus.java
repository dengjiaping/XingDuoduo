package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：AliPayStatus.java
 * @描述：传递支付宝返回参数到服务端返回的结果实体类
 * @author danding 2014-9-19
 */
public class AliPayStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9116750101656714056L;
	// 订单状态
	private String OrderStatus;
	// 支付状态
	private String paymentStatus;
	// 订单id
	private String orderId;

	public AliPayStatus(String orderStatus, String paymentStatus, String orderId) {
		super();
		OrderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.orderId = orderId;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
