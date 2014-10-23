package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：BClassify.java
 * @描述：二级分类
 * @author danding
 * @时间 2014-10-23
 */
public class BClassify implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7025255363417276011L;
	// 分类名
	private String categoryName;
	// 父类id
	private String parentId;
	// 二级分类id
	private String categoryId;
	private String goodTypeId;
	// 图片地址
	private String logoPath;
	//
	private String adlogoPath;

	public BClassify(String categoryName, String parentId, String categoryId,
			String goodTypeId, String logoPath, String adlogoPath) {
		super();
		this.categoryName = categoryName;
		this.parentId = parentId;
		this.categoryId = categoryId;
		this.goodTypeId = goodTypeId;
		this.logoPath = logoPath;
		this.adlogoPath = adlogoPath;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getAdlogoPath() {
		return adlogoPath;
	}

	public void setAdlogoPath(String adlogoPath) {
		this.adlogoPath = adlogoPath;
	}

}
