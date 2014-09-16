package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 
 * @名称：GoodsTwo.java
 * @描述：商品信息---详情(二级)
 * @author danding
 * @version
 * @date：2014-6-25
 */
public class GoodsTwo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6291887238278761186L;
	// 商品所属分类id
	private String goodsCategoryId;
	// 商品ID
	private String id;
	// 商品类型id
	private String goodtype_id;
	// 小图地址
	private String SmallGoodsImagePath;
	// 正常图
	private String ThumbnailGoodsImagePath;
	// 商品名
	private String name;
	// 商品SN
	private String goodsSn;
	// 商品图片列表url
	private ArrayList<ImagePath> imagePath;
	// 商品现价
	private String goods_price;
	// 商品规格具体参数
	private ArrayList<GoodsStandard> productDetail;
	// 商品销量
	private String salesVolume;
	// 商品的基本参数
	private ArrayList<GoodsParams> goodsParams;
	// 商品图文详情(html解析)
	private String introduction;
	// Value0
	private String Value0;
	// Value1
	private String Value1;
	// 规格可用
	private boolean IsSpecificationEnabled;

	public GoodsTwo() {
		super();
	}

	public GoodsTwo(String goodsCategoryId, String id, String goodtype_id,
			String smallGoodsImagePath, String thumbnailGoodsImagePath,
			String name, String goodsSn, ArrayList<ImagePath> imagePath,
			String goods_price, ArrayList<GoodsStandard> productDetail,
			String salesVolume, ArrayList<GoodsParams> goodsParams,
			String introduction, String value0, String value1,
			boolean isSpecificationEnabled) {
		super();
		this.goodsCategoryId = goodsCategoryId;
		this.id = id;
		this.goodtype_id = goodtype_id;
		SmallGoodsImagePath = smallGoodsImagePath;
		ThumbnailGoodsImagePath = thumbnailGoodsImagePath;
		this.name = name;
		this.goodsSn = goodsSn;
		this.imagePath = imagePath;
		this.goods_price = goods_price;
		this.productDetail = productDetail;
		this.salesVolume = salesVolume;
		this.goodsParams = goodsParams;
		this.introduction = introduction;
		Value0 = value0;
		Value1 = value1;
		IsSpecificationEnabled = isSpecificationEnabled;
	}

	public boolean isIsSpecificationEnabled() {
		return IsSpecificationEnabled;
	}

	public void setIsSpecificationEnabled(boolean isSpecificationEnabled) {
		IsSpecificationEnabled = isSpecificationEnabled;
	}

	public String getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(String goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodtype_id() {
		return goodtype_id;
	}

	public void setGoodtype_id(String goodtype_id) {
		this.goodtype_id = goodtype_id;
	}

	public String getSmallGoodsImagePath() {
		return SmallGoodsImagePath;
	}

	public void setSmallGoodsImagePath(String smallGoodsImagePath) {
		SmallGoodsImagePath = smallGoodsImagePath;
	}

	public String getThumbnailGoodsImagePath() {
		return ThumbnailGoodsImagePath;
	}

	public void setThumbnailGoodsImagePath(String thumbnailGoodsImagePath) {
		ThumbnailGoodsImagePath = thumbnailGoodsImagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public ArrayList<ImagePath> getImagePath() {
		return imagePath;
	}

	public void setImagePath(ArrayList<ImagePath> imagePath) {
		this.imagePath = imagePath;
	}

	public String getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(String goods_price) {
		this.goods_price = goods_price;
	}

	public ArrayList<GoodsStandard> getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ArrayList<GoodsStandard> productDetail) {
		this.productDetail = productDetail;
	}

	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}

	public ArrayList<GoodsParams> getGoodsParams() {
		return goodsParams;
	}

	public void setGoodsParams(ArrayList<GoodsParams> goodsParams) {
		this.goodsParams = goodsParams;
	}

	/**
	 * @描述：Html解析
	 * @return 2014-8-15
	 */
	public String getIntroduction() {
		if(introduction==null){
			return "";
		}
		Document doc = Jsoup.parse(introduction);
		Element link = doc.select("img").first();
		return link.attr("src");
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getValue0() {
		return Value0;
	}

	public void setValue0(String value0) {
		Value0 = value0;
	}

	public String getValue1() {
		return Value1;
	}

	public void setValue1(String value1) {
		Value1 = value1;
	}

}
