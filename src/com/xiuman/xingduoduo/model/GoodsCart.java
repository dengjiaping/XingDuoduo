package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * @名称：GoodsCart.java
 * @描述：购物车商品实体
 * @author danding
 * @version
 * @date：2014-7-21
 */
public class GoodsCart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1016718857459104448L;

	// 购物车id
	private String cartItemId;
	// 产品id
	private String productId;
	// 商品Id
	private String goodId;
	// 产品名称
	private String productName;
	// 商品价格
	private String productPrice;
	// 商品图片（缩）
	private String ThumbnailGoodsImagePath;
	// 商品图片small
	private String SmallGoodsImagePath;
	// 商品规格
	private String Specifications;
	// 商品数量
	private int quanity;
	// 总价
	private String totalPrice;
	// 是否是活动商品
	private boolean isActivities;

	public GoodsCart() {
		super();
	}

	public GoodsCart(String cartItemId, String productId, String goodId,
			String productName, String productPrice,
			String thumbnailGoodsImagePath, String smallGoodsImagePath,
			String specifications, int quanity, String totalPrice,
			boolean isActivities) {
		super();
		this.cartItemId = cartItemId;
		this.productId = productId;
		this.goodId = goodId;
		this.productName = productName;
		this.productPrice = productPrice;
		ThumbnailGoodsImagePath = thumbnailGoodsImagePath;
		SmallGoodsImagePath = smallGoodsImagePath;
		Specifications = specifications;
		this.quanity = quanity;
		this.totalPrice = totalPrice;
		this.isActivities = isActivities;
	}

	public boolean isActivities() {
		return isActivities;
	}

	public void setActivities(boolean isActivities) {
		this.isActivities = isActivities;
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getThumbnailGoodsImagePath() {
		return ThumbnailGoodsImagePath;
	}

	public void setThumbnailGoodsImagePath(String thumbnailGoodsImagePath) {
		ThumbnailGoodsImagePath = thumbnailGoodsImagePath;
	}

	public String getSmallGoodsImagePath() {
		return SmallGoodsImagePath;
	}

	public void setSmallGoodsImagePath(String smallGoodsImagePath) {
		SmallGoodsImagePath = smallGoodsImagePath;
	}

	public String getSpecifications() {
		return Specifications;
	}

	public void setSpecifications(String specifications) {
		Specifications = specifications;
	}

	public int getQuanity() {
		return quanity;
	}

	public void setQuanity(int quanity) {
		this.quanity = quanity;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

}
