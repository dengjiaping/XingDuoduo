package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * 名称：Ad.java
 * 描述：商城界面的广告实体
 * 广告的类型不同，属性值有的有，有的没有
 * @author danding
 * @version
 * 2014-6-9
 */
public class Ad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6576297882954831703L;

	//广告图片地址
	private String ad_poster_url;
	//广告链接地址
	private String ad_url;
	//广告内容
	private String ad_content;
	
	
	/**
	 * 空参数
	 */
	public Ad() {
		super();
	}
	/**
	 * 构造函数
	 * @param ad_poster_url
	 * @param ad_url
	 * @param ad_context
	 */
	public Ad(String ad_poster_url, String ad_url, String ad_content) {
		super();
		this.ad_poster_url = ad_poster_url;
		this.ad_url = ad_url;
		this.ad_content = ad_content;
	}
	public String getAd_poster_url() {
		return ad_poster_url;
	}
	public void setAd_poster_url(String ad_poster_url) {
		this.ad_poster_url = ad_poster_url;
	}
	public String getAd_url() {
		return ad_url;
	}
	public void setAd_url(String ad_url) {
		this.ad_url = ad_url;
	}
	public String getAd_content() {
		return ad_content;
	}
	public void setAd_context(String ad_content) {
		this.ad_content = ad_content;
	}
	
	
	
}
