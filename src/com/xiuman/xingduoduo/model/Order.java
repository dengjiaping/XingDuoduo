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
	private String order_state;
	// 订单号
	private String order_id;
	// 订单生成时间
	private String order_time;
	// 用户id
	private String order_user_id;
	// 用户地址
	private UserAddress order_user_address;
	// 支付方式
	private String order_pay_way;
	// 商品列表
	private ArrayList<GoodsCart> goods_list;
	// 邮费
	private String order_trans_pay;
	// 优惠
	private String order_preferential;
	// 订单总价
	private double order_total;
	// 卖家留言
	private String order_user_words;

	public Order() {
		super();
	}

	public Order(String order_state, String order_id, String order_time,
			String order_user_id, UserAddress order_user_address,
			String order_pay_way, ArrayList<GoodsCart> goods_list,
			String order_trans_pay, String order_preferential,
			double order_total, String order_user_words) {
		super();
		this.order_state = order_state;
		this.order_id = order_id;
		this.order_time = order_time;
		this.order_user_id = order_user_id;
		this.order_user_address = order_user_address;
		this.order_pay_way = order_pay_way;
		this.goods_list = goods_list;
		this.order_trans_pay = order_trans_pay;
		this.order_preferential = order_preferential;
		this.order_total = order_total;
		this.order_user_words = order_user_words;
	}

	public String getOrder_user_words() {
		return order_user_words;
	}

	public void setOrder_user_words(String order_user_words) {
		this.order_user_words = order_user_words;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getOrder_user_id() {
		return order_user_id;
	}

	public void setOrder_user_id(String order_user_id) {
		this.order_user_id = order_user_id;
	}

	public UserAddress getOrder_user_address() {
		return order_user_address;
	}

	public void setOrder_user_address(UserAddress order_user_address) {
		this.order_user_address = order_user_address;
	}

	public String getOrder_pay_way() {
		return order_pay_way;
	}

	public void setOrder_pay_way(String order_pay_way) {
		this.order_pay_way = order_pay_way;
	}

	public ArrayList<GoodsCart> getGoods_list() {
		return goods_list;
	}

	public void setGoods_list(ArrayList<GoodsCart> goods_list) {
		this.goods_list = goods_list;
	}

	public String getOrder_trans_pay() {
		return order_trans_pay;
	}

	public void setOrder_trans_pay(String order_trans_pay) {
		this.order_trans_pay = order_trans_pay;
	}

	public String getOrder_preferential() {
		return order_preferential;
	}

	public void setOrder_preferential(String order_preferential) {
		this.order_preferential = order_preferential;
	}

	public double getOrder_total() {
		return order_total;
	}

	public void setOrder_total(double order_total) {
		this.order_total = order_total;
	}

}
