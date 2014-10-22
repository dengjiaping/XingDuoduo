package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * @名称：Ad.java 
 * @描述：商城界面的广告实体 广告的类型不同，属性值有的有，有的没有
 * @author danding
 * @version 2014-6-9
 */
public class Ad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6576297882954831703L;

	// 广告名
	private String name;
	// 是广告还是分类
	private String showAddress;
	// 广告分类(商品，帖子，分类)
	private String gdCategory;
	// 顺序
	private String orderList;
	// 图片地址
	private String logoPath;
	// id
	private String homeId;
	// 板块id（只有是帖子的时候才用到）
	private String introduction;

	/**
	 * 空参数
	 */
	public Ad() {
		super();
	}

	public Ad(String name, String showAddress, String gdCategory,
			String orderList, String logoPath, String homeId,
			String introduction) {
		super();
		this.name = name;
		this.showAddress = showAddress;
		this.gdCategory = gdCategory;
		this.orderList = orderList;
		this.logoPath = logoPath;
		this.homeId = homeId;
		this.introduction = introduction;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowAddress() {
		return showAddress;
	}

	public void setShowAddress(String showAddress) {
		this.showAddress = showAddress;
	}

	public String getGdCategory() {
		return gdCategory;
	}

	public void setGdCategory(String gdCategory) {
		this.gdCategory = gdCategory;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getHomeId() {
		return homeId;
	}

	public void setHomeId(String homeId) {
		this.homeId = homeId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

}
