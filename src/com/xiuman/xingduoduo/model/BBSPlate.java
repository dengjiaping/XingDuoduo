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
	private String plate_id;
	// 板块名称
	private String plate_name;
	// 板块介绍
	private String plate_description;
	// 板块标签(HOT,NEW)
	private String plate_tag;
	// 板块图标
	private int plate_icon;

	public BBSPlate() {
		super();
	}

	public BBSPlate(String plate_id, String plate_name,
			String plate_description, String plate_tag, int plate_icon) {
		super();
		this.plate_id = plate_id;
		this.plate_name = plate_name;
		this.plate_description = plate_description;
		this.plate_tag = plate_tag;
		this.plate_icon = plate_icon;
	}

	public String getPlate_id() {
		return plate_id;
	}

	public void setPlate_id(String plate_id) {
		this.plate_id = plate_id;
	}

	public String getPlate_name() {
		return plate_name;
	}

	public void setPlate_name(String plate_name) {
		this.plate_name = plate_name;
	}

	public String getPlate_description() {
		return plate_description;
	}

	public void setPlate_description(String plate_description) {
		this.plate_description = plate_description;
	}

	public String getPlate_tag() {
		return plate_tag;
	}

	public void setPlate_tag(String plate_tag) {
		this.plate_tag = plate_tag;
	}

	public int getPlate_icon() {
		return plate_icon;
	}

	public void setPlate_icon(int plate_icon) {
		this.plate_icon = plate_icon;
	}

}
