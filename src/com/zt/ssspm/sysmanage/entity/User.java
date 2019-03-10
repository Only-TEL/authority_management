package com.zt.ssspm.sysmanage.entity;

import java.sql.Timestamp;
 
/**
 * @ClassName : com.zt.ssspm.sysmanage.entity.User
 * @Description : 用户对象
 * @author : HeadMaster
 * @date : 2018年7月22日
 */
public class User implements java.io.Serializable{

	 /**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */
	private static final long serialVersionUID = 2598054211690754294L;
	
	private Long id;
	private Long deptId;
	private String loginName;
	private String	password;	 
	private String	no;
	private String name;
	private String	email;
	private String	phone;
	private String	mobile;
	private String	loginIp;
	private Timestamp	loginDate;
	private String	loginFlag;
	private String	createBy;
	private Timestamp createDate;
	private String	updateBy;
	private Timestamp updateDate;
	private String remarks;
 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Timestamp getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Timestamp loginDate) {
		this.loginDate = loginDate;
	}
	public String getLoginFlag() {
		return loginFlag;
	}
	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
 
	
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	 
	
}