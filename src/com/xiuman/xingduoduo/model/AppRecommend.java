package com.xiuman.xingduoduo.model;

import java.io.Serializable;

import com.xiuman.xingduoduo.app.URLConfig;
/**
 * @名称：AppRecommend.java
 * @描述：应用推荐
 * @author danding
 * 2014-9-22
 */
public class AppRecommend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1879210941165054450L;
	//app地址
	private String appUrl;
	//appname
	private String appName;
	//app介绍
	private String appIntroduct;
	//图标
	private String appImageUrl;
	public AppRecommend(String appUrl, String appName, String appIntroduct,
			String appImageUrl) {
		super();
		this.appUrl = appUrl;
		this.appName = appName;
		this.appIntroduct = appIntroduct;
		this.appImageUrl = appImageUrl;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppIntroduct() {
		return appIntroduct;
	}
	public void setAppIntroduct(String appIntroduct) {
		this.appIntroduct = appIntroduct;
	}
	public String getAppImageUrl() {
		return URLConfig.IMG_IP+appImageUrl;
	}
	public void setAppImageUrl(String appImageUrl) {
		this.appImageUrl = appImageUrl;
	}
	
	

}
