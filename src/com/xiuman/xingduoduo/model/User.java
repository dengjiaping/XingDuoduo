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

	public User() {
		super();
	}

	public User(String userName, String userId, String email,
			String createDate, double preferntial, String rankNmae) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.email = email;
		this.createDate = createDate;
		this.preferntial = preferntial;
		this.rankNmae = rankNmae;
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
