package com.zt.ssspm.sysmanage.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Dict implements Serializable{
	/*
	CREATE TABLE `pm_sys_dict` (
	  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
	  `value` varchar(100) NOT NULL COMMENT '数据值',
	  `label` varchar(100) NOT NULL COMMENT '标签名',
	  `type` varchar(100) NOT NULL COMMENT '类型',
	  `description` varchar(100) NOT NULL COMMENT '描述',
	  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
	  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
	  `create_by` varchar(64) NOT NULL COMMENT '创建者',
	  `create_date` datetime NOT NULL COMMENT '创建时间',
	  `update_by` varchar(64) NOT NULL COMMENT '更新者',
	  `update_date` datetime NOT NULL COMMENT '更新时间',
	  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
	  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
	  PRIMARY KEY (`id`),
	  KEY `sys_dict_value` (`value`),
	  KEY `sys_dict_label` (`label`),
	  KEY `sys_dict_del_flag` (`del_flag`)
	) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COMMENT='字典表';
	 * */
	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -8235484567317435952L;
	private Long id;
 	private String value;
 	private String label;
 	private String type;
 	private String description;
 	private Integer sort;
 	private String parentId;
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
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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
