package com.zt.ssspm.sysmanage.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Menu implements Serializable{
	/*
		CREATE TABLE `pm_sys_menu` (
		  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
		  `parent_id` int(64) NOT NULL COMMENT '父级编号',
		  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
		  `name` varchar(100) NOT NULL COMMENT '名称',
		  `sort` decimal(10,0) NOT NULL COMMENT '排序',
		  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
		  `target` varchar(20) DEFAULT NULL COMMENT '目标',
		  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
		  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
		  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
		  `create_by` varchar(64) NOT NULL COMMENT '创建者',
		  `create_date` datetime NOT NULL COMMENT '创建时间',
		  `update_by` varchar(64) NOT NULL COMMENT '更新者',
		  `update_date` datetime NOT NULL COMMENT '更新时间',
		  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
		  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
		  PRIMARY KEY (`id`),
		  KEY `sys_menu_parent_id` (`parent_id`),
		  KEY `sys_menu_del_flag` (`del_flag`)
		) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='菜单表';
	 * */
	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -5573000774638720938L;
	
	private Long id;
 	private Long parentId;
 	private String parentName;
 	private String name;
  	private Integer sort;
  	private String href;
  	private String target;
  	private String icon;
  	private String isShow;
  	private String permission;
  	
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
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
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
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
