package com.xiuman.xingduoduo.model;

import java.io.Serializable;
/**
 * @描述：帖子图片对象
 * @名称：PostImg.java
 * @author CSX
 * @日期：2014-10-12
 */
public class PostImg implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1651256545578529545L;
	//图片地址
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
