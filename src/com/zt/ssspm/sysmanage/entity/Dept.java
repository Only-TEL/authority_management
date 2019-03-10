package com.zt.ssspm.sysmanage.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.zt.ssspm.sysmanage.dto.TreeDto;
/**
 * 部门对象Dept
 * @ClassName : com.zt.ssspm.sysmanage.entity.Dept
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月27日
 */
public class Dept extends TreeDto implements Serializable{
	
/*
	CREATE TABLE `pm_sys_dept` (
	  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
	  `parent_id` int(32) NOT NULL COMMENT '父级编号',
	  `name` varchar(100) NOT NULL COMMENT '名称',
	  `sort` decimal(10,0) NOT NULL COMMENT '排序',
	  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
	  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
	  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
	  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
	  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
	  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
	  `create_by` varchar(64) NOT NULL COMMENT '创建者',
	  `create_date` datetime NOT NULL COMMENT '创建时间',
	  `update_by` varchar(64) NOT NULL COMMENT '更新者',
	  `update_date` datetime NOT NULL COMMENT '更新时间',
	  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
	  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
	  PRIMARY KEY (`id`),
	  KEY `sys_office_parent_id` (`parent_id`),
	  KEY `sys_office_del_flag` (`del_flag`)
	) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='机构表';
 */
	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = 7492747872008991887L;
	
 	private String parentName;
  	private Integer sort;
  	private String code;
  	private String address;
  	private String master;
  	private String phone;
  	private String fax;
  	private String email;
  	
  	private String	createBy;
	private Timestamp createDate;
	private String	updateBy;
	private Timestamp updateDate;
	private String remarks;


	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMaster() {
		return master;
	}
	public void setMaster(String master) {
		this.master = master;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
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
 
}