package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @名称：OrderSubmit.java
 * @描述：提交订单需要的信息
 * @author danding 2014-8-4
 */
public class OrderSubmit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1607943306633291013L;
	// 用户id
	private String order_user_id;
	// 用户地址
	private UserAddress order_user_address;
	// 支付方式
	private String order_pay_way;
	// 商品清单
	private ArrayList<GoodsCart> order_goods;
	// 运费
	private String order_trans_pay;
	// 优惠
	private String order_preferential;
	// 留言
	private String order_user_words;
	// 商品总价
	private double order_total;

	public OrderSubmit() {
		super();
	}

	public OrderSubmit(String order_user_id, UserAddress order_user_address,
			String order_pay_way, ArrayList<GoodsCart> order_goods,
			String order_trans_pay, String order_preferential,
			String order_user_words, double order_total) {
		super();
		this.order_user_id = order_user_id;
		this.order_user_address = order_user_address;
		this.order_pay_way = order_pay_way;
		this.order_goods = order_goods;
		this.order_trans_pay = order_trans_pay;
		this.order_preferential = order_preferential;
		this.order_user_words = order_user_words;
		this.order_total = order_total;
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

	public ArrayList<GoodsCart> getOrder_goods() {
		return order_goods;
	}

	public void setOrder_goods(ArrayList<GoodsCart> order_goods) {
		this.order_goods = order_goods;
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

	public String getOrder_user_words() {
		return order_user_words;
	}

	public void setOrder_user_words(String order_user_words) {
		this.order_user_words = order_user_words;
	}

	public double getOrder_total() {
		return order_total;
	}

	public void setOrder_total(double order_total) {
		this.order_total = order_total;
	}

}
