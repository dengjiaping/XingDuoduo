package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * @名称：BBSPlate.java
 * @描述：论坛板块（可本地，可后台）
 * @author danding
 * @version
 * @date：2014-7-29
 */
public class BBSPlate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1543789994854728589L;
	// 板块Id
	private String id;
	// 板块名称
	private String title;
	// 板块介绍
	private String description;
	// 是否显示
	private int isShow;
	// 板块图标
	private String logo;

	public BBSPlate() {
		super();
	}

	




	public BBSPlate(String id, String title, String description, int isShow,
			String logo) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.isShow = isShow;
		this.logo = logo;
	}






	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	



	public int getIsShow() {
		return isShow;
	}






	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}






	public String getLogo() {
		return logo;
	}



	public void setLogo(String logo) {
		this.logo = logo;
	}

	

}
