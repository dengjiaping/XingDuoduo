package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

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
	// 分列id
	private String categoryId;
	// 商品类型
	private String goodTypeId;
	// 图片地址
	private String logoPath;
	// 二级分类
	private ArrayList<BClassify> goodsCategoryparams;
	//
	private String adlogoPath;

	public Classify() {
		super();
	}

	public Classify(String categoryName, String categoryId, String goodTypeId,
			String logoPath, ArrayList<BClassify> goodsCategoryparams,
			String adlogoPath) {
		super();
		this.categoryName = categoryName;
		this.categoryId = categoryId;
		this.goodTypeId = goodTypeId;
		this.logoPath = logoPath;
		this.goodsCategoryparams = goodsCategoryparams;
		this.adlogoPath = adlogoPath;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public ArrayList<BClassify> getGoodsCategoryparams() {
		return goodsCategoryparams;
	}

	public void setGoodsCategoryparams(ArrayList<BClassify> goodsCategoryparams) {
		this.goodsCategoryparams = goodsCategoryparams;
	}

	public String getAdlogoPath() {
		return adlogoPath;
	}

	public void setAdlogoPath(String adlogoPath) {
		this.adlogoPath = adlogoPath;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
