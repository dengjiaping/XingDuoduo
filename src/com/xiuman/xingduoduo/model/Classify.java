package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * 名称：Classify.java 描述：一级分类
 * 
 * @author danding
 * @version 2014-6-5
 */
public class Classify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3485803410598890696L;
	// 分类名
	private String categoryName;
	// flag
	private boolean flag;
	// flg
	private String flg;
	// 分列id
	private String categoryId;
	// 商品类型
	private String goodTypeId;

	public Classify() {
		super();
	}

	public Classify(String categoryName, boolean flag, String flg,
			String categoryId, String goodTypeId) {
		super();
		this.categoryName = categoryName;
		this.flag = flag;
		this.flg = flg;
		this.categoryId = categoryId;
		this.goodTypeId = goodTypeId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getGoodTypeId() {
		return goodTypeId;
	}

	public void setGoodTypeId(String goodTypeId) {
		this.goodTypeId = goodTypeId;
	}

}
