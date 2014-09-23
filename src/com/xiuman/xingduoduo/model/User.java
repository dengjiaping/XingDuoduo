package com.xiuman.xingduoduo.model;

import java.io.Serializable;

/**
 * 
 * @名称：User.java
 * @描述：登录用户的信息实体
 * @author danding
 * @version
 * @date：2014-6-20
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3756046945291465479L;

	// 用户名
	private String userName;
	// 用户id
	private String userId;
	// 用户邮箱
	private String email;
	// 创建时间
	private String createDate;
	// 用户折扣
	private double preferntial;
	// 会员信息
	private String rankNmae;
	// 昵称
	private String nickname;
	// 生日
	private String birth;
	// 电话
	private String phone;
	// 地址
	private String address;
	// 地区
	private String areaStore;
	// 邮编
	private String zipCode;
	// 性别
	private String gender;
	// 姓名
	private String name;
	// 论坛id
	private String user_id;
	//头像地址
	private String head_image;

	public User() {
		super();
	}


	public User(String userName, String userId, String email,
			String createDate, double preferntial, String rankNmae,
			String nickname, String birth, String phone, String address,
			String areaStore, String zipCode, String gender, String name,
			String user_id, String head_image) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.email = email;
		this.createDate = createDate;
		this.preferntial = preferntial;
		this.rankNmae = rankNmae;
		this.nickname = nickname;
		this.birth = birth;
		this.phone = phone;
		this.address = address;
		this.areaStore = areaStore;
		this.zipCode = zipCode;
		this.gender = gender;
		this.name = name;
		this.user_id = user_id;
		this.head_image = head_image;
	}


	public String getHead_image() {
		return head_image;
	}


	public void setHead_image(String head_image) {
		this.head_image = head_image;
	}


	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaStore() {
		return areaStore;
	}

	public void setAreaStore(String areaStore) {
		this.areaStore = areaStore;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public double getPreferntial() {
		return preferntial;
	}

	public void setPreferntial(double preferntial) {
		this.preferntial = preferntial;
	}

	public String getRankNmae() {
		return rankNmae;
	}

	public void setRankNmae(String rankNmae) {
		this.rankNmae = rankNmae;
	}

}
