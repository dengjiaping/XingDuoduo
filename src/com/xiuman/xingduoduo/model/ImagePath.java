package com.xiuman.xingduoduo.model;

import java.io.Serializable;

import com.xiuman.xingduoduo.app.URLConfig;

/**
 * @名称：ImagePath.java
 * @描述：商品详情
 * @author danding 2014-8-15
 */
public class ImagePath implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3831810806362138691L;
	// 图片id
	private String id;
	// 图片路径
	private String path;
	// 图片格式
	private String sourceImageFormatName;

	public ImagePath() {
		super();
	}

	public ImagePath(String id, String path, String sourceImageFormatName) {
		super();
		this.id = id;
		this.path = path;
		this.sourceImageFormatName = sourceImageFormatName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSourceImageFormatName() {
		return sourceImageFormatName;
	}

	public void setSourceImageFormatName(String sourceImageFormatName) {
		this.sourceImageFormatName = sourceImageFormatName;
	}
	/**
	 * 直接返回图片完整地址
	 */
	@Override
	public String toString() {
		return URLConfig.IMG_IP+path+"/big/"+id+"."+sourceImageFormatName;
	}

}
