package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @名称：Order.java
 * @描述：订单
 * @author danding 2014-8-4
 */
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6817822517541493663L;
	// 订单状态
	private String OrderStatus;
	// 支付状态
	private String paymentStatus;
	// 订单id
	private String id;
	// 订单号
	private String orderSn;
	// 订单生成时间
	private String create_date;
	// 付款方式
	private String deliveryName;
	// 付款方式2
	private String payment_comnfig_name;
	// 收货人
	private String shipName;
	// 收货人电话
	private String ship_mobile;
	// 收货人地址1
	private String ship_area_store;
	// 收货人地址2
	private String ship_address;
	// 收货人邮编
	private String ship_zip_code;
	// 商品列表
	private ArrayList<GoodsOrderInfo> productDetail;
	// 已支付费用
	private String payment_fee;
	// 邮费
	private String delivery;
	// 订单总价
	private String totalAmount;
	// 已支付
	private String paidAmount;
	// 卖家留言
	private String memo;

	public Order() {
		super();
	}

	public Order(String orderStatus, String paymentStatus, String id,
			String orderSn, String create_date, String deliveryName,
			String payment_comnfig_name, String shipName, String ship_mobile,
			String ship_area_store, String ship_address, String ship_zip_code,
			ArrayList<GoodsOrderInfo> productDetail, String payment_fee,
			String delivery, String totalAmount, String paidAmount, String memo) {
		super();
		OrderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		this.id = id;
		this.orderSn = orderSn;
		this.create_date = create_date;
		this.deliveryName = deliveryName;
		this.payment_comnfig_name = payment_comnfig_name;
		this.shipName = shipName;
		this.ship_mobile = ship_mobile;
		this.ship_area_store = ship_area_store;
		this.ship_address = ship_address;
		this.ship_zip_code = ship_zip_code;
		this.productDetail = productDetail;
		this.payment_fee = payment_fee;
		this.delivery = delivery;
		this.totalAmount = totalAmount;
		this.paidAmount = paidAmount;
		this.memo = memo;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getPayment_comnfig_name() {
		return payment_comnfig_name;
	}

	public void setPayment_comnfig_name(String payment_comnfig_name) {
		this.payment_comnfig_name = payment_comnfig_name;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShip_mobile() {
		return ship_mobile;
	}

	public void setShip_mobile(String ship_mobile) {
		this.ship_mobile = ship_mobile;
	}

	public String getShip_area_store() {
		return ship_area_store;
	}

	public void setShip_area_store(String ship_area_store) {
		this.ship_area_store = ship_area_store;
	}

	public String getShip_address() {
		return ship_address;
	}

	public void setShip_address(String ship_address) {
		this.ship_address = ship_address;
	}

	public String getShip_zip_code() {
		return ship_zip_code;
	}

	public void setShip_zip_code(String ship_zip_code) {
		this.ship_zip_code = ship_zip_code;
	}

	public ArrayList<GoodsOrderInfo> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ArrayList<GoodsOrderInfo> productDetail) {
		this.productDetail = productDetail;
	}

	public String getPayment_fee() {
		return payment_fee;
	}

	public void setPayment_fee(String payment_fee) {
		this.payment_fee = payment_fee;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	

}
