package com.zt.ssspm.sysmanage.entity;

import java.sql.Timestamp;
import com.zt.ssspm.sysmanage.dto.TreeDto;
/**
 * 区域对象
 * @ClassName : com.zt.ssspm.sysmanage.entity.Area
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月28日
 */
public class Area extends TreeDto implements java.io.Serializable{
  	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = 5140366627066089474L;
	/*
		CREATE TABLE `pm_sys_area` (
		  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '编号',
		  `parent_id` int(32) NOT NULL COMMENT '父级编号',
		  `name` varchar(100) NOT NULL COMMENT '名称',
		  `sort` decimal(10,0) NOT NULL COMMENT '排序',
		  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
		  `create_by` varchar(64) NOT NULL COMMENT '创建者',
		  `create_date` datetime NOT NULL COMMENT '创建时间',
		  `update_by` varchar(64) NOT NULL COMMENT '更新者',
		  `update_date` datetime NOT NULL COMMENT '更新时间',
		  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
		  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
		  PRIMARY KEY (`id`),
		  KEY `sys_area_parent_id` (`parent_id`),
		  KEY `sys_area_del_flag` (`del_flag`)
		) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='区域表';
	 * */
	private String parentName;
   	private Integer sort;
  	private String code;
  	private String type;
  	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
 
}