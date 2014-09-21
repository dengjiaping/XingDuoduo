package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * @名称：Goods.java
 * @描述：商品信息-->基本信息(一级)
 * @author danding
 * @version
 * @date：2014-6-25
 */
public class GoodsOne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5521588432304575146L;

	// 商品ID
	private String id;
	// 商品海报图片地址1
	private String ThumbnailGoodsImagePath;
	// 商品海报图片地址3
	private String SmallGoodsImagePath;
	// 源图片
	private String SourceImagePath;
	// 所属分类id
	private String goodsCategoryId;
	// 商品名
	private String name;
	// 商品价格
	private double goods_price;
	// 商品销量
	private int salesVolume;
	// 商品Sn
	private String goodsSn;
	// Value0
	private String bigGoodsImagePath;
	// Value1
	private String goodtype_id;
	// 介绍
	private String metaDescription;

	public GoodsOne() {
		super();
	}

	public GoodsOne(String id, String thumbnailGoodsImagePath,
			String smallGoodsImagePath, String sourceImagePath,
			String goodsCategoryId, String name, double goods_price,
			int salesVolume, String goodsSn, String bigGoodsImagePath,
			String goodtype_id, String metaDescription) {
		super();
		this.id = id;
		ThumbnailGoodsImagePath = thumbnailGoodsImagePath;
		SmallGoodsImagePath = smallGoodsImagePath;
		SourceImagePath = sourceImagePath;
		this.goodsCategoryId = goodsCategoryId;
		this.name = name;
		this.goods_price = goods_price;
		this.salesVolume = salesVolume;
		this.goodsSn = goodsSn;
		this.bigGoodsImagePath = bigGoodsImagePath;
		this.goodtype_id = goodtype_id;
		this.metaDescription = metaDescription;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getSourceImagePath() {
		return SourceImagePath;
	}

	public void setSourceImagePath(String sourceImagePath) {
		SourceImagePath = sourceImagePath;
	}

	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getBigGoodsImagePath() {
		return bigGoodsImagePath;
	}

	public void setBigGoodsImagePath(String bigGoodsImagePath) {
		this.bigGoodsImagePath = bigGoodsImagePath;
	}

	public String getGoodtype_id() {
		return goodtype_id;
	}

	public void setGoodtype_id(String goodtype_id) {
		this.goodtype_id = goodtype_id;
	}

	public String getMetaDescription() {
		return metaDescription;
	}

	public void setMetaDescription(String metaDescription) {
		this.metaDescription = metaDescription;
	}

	/**
	 * 重写以便比较(商品id跟商品型号相同则相同)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GoodsOne) {
			GoodsOne goods = (GoodsOne) obj;
			return this.id.equals(goods.id);
		}
		return super.equals(obj);
	}
}
