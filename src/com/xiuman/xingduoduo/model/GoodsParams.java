package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：GoodsParams.java
 * @描述：商品基本参数
 * @author danding 2014-8-15
 */
public class GoodsParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2773020835792146756L;
	// 参数类型
	private String id;
	// 参数值
	private String name;

	public GoodsParams() {
		super();
	}

	public GoodsParams(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		if(name.equals("")){
			return "";
		}
		return id+":"+name;
	}
}
