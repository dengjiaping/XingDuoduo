package com.xiuman.xingduoduo.model;

import java.io.Serializable;
/**
 * @名称：PostImg.java
 * @描述：帖子图片地址
 * @author danding
 * 2014-10-13
 */
public class PostImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1651256545578529545L;
	//
	private String imgurl;

	public PostImg(String imgurl) {
		super();
		this.imgurl = imgurl;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
