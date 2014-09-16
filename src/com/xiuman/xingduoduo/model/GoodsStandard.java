package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：GoodsStandard.java
 * @描述：商品规格参数
 * @author danding
 * @version
 * @date：2014-7-31
 */
public class GoodsStandard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6183349916619472171L;

	// 商品规格组合
	private String Specifications;
	// 该规格下的商品价格
	private String price;
	// 库存
	private String store;
	// 产品Id
	private String productId;

	public GoodsStandard() {
		super();
	}

	public GoodsStandard(String specifications, String price, String store,
			String productId) {
		super();
		Specifications = specifications;
		this.price = price;
		this.store = store;
		this.productId = productId;
	}

	public String getSpecifications() {
		return Specifications;
	}

	public void setSpecifications(String specifications) {
		Specifications = specifications;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
