package com.xiuman.xingduoduo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @名称：ActionValue.java
 * @描述：带泛型的商品列表实体类
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class ActionValue<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7623673742716474615L;
	// 商品列表
	private ArrayList<T> datasource;
	// 当前页数
	private int page = 0;
	// 总页数
	private int totalpage = 0;
	// message
	private String message;
	// 结果
	private boolean success;

	public ActionValue() {
		super();
	}

	public ActionValue(ArrayList<T> datasource, int page, int totalpage,
			String message, boolean success) {
		super();
		this.datasource = datasource;
		this.page = page;
		this.totalpage = totalpage;
		this.message = message;
		this.success = success;
	}

	public ArrayList<T> getDatasource() {
		return datasource;
	}

	public void setDatasource(ArrayList<T> datasource) {
		this.datasource = datasource;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
