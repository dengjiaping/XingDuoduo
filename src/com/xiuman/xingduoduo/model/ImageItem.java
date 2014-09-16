package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 一个图片对象
 * 
 * @author Administrator
 * 
 */
public class ImageItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6908958817431058749L;
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	public boolean isSelected = false;
}
