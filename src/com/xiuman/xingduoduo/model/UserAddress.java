package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * @名称：UserAddress.java
 * @描述：用户收货地址信息实体类
 * @author danding
 * @version
 * @date：2014-6-20
 */
public class UserAddress implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2544451492156848260L;
	// 收货地址id
	private String receiveId;
	// 所在地区
	private String areaId;
	// 详细地址
	private String address;
	// 收货人
	private String receiveName;
	// 收货人手机号码
	private String telephone;
	// 邮编
	private String zipCode;

	public UserAddress() {
		super();
	}

	public UserAddress(String receiveId, String areaId, String address,
			String receiveName, String telephone, String zipCode) {
		super();
		this.receiveId = receiveId;
		this.areaId = areaId;
		this.address = address;
		this.receiveName = receiveName;
		this.telephone = telephone;
		this.zipCode = zipCode;
	}

	public String getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}


	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public void setReceiveName(String receiveName) {
		this.receiveName = receiveName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * toString
	 */
	public String toString() {
		return receiveName + "   " + telephone + "   " + areaId + address
				+ zipCode;
	}
}
