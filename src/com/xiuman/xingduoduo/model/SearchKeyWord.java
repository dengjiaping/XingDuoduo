package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：SearchKeyWord.java
 * @描述：热门搜索关键字
 * @author danding
 * @时间 2014-10-24
 */
public class SearchKeyWord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 393889174676089932L;
	// 关键字
	private String hotsearch;

	public SearchKeyWord(String hotsearch) {
		super();
		this.hotsearch = hotsearch;
	}

	public String getHotsearch() {
		return hotsearch;
	}

	public void setHotsearch(String hotsearch) {
		this.hotsearch = hotsearch;
	}

}
