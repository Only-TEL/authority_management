package com.zt.ssspm.sysmanage.entity;

import java.io.Serializable;
import java.sql.Timestamp;
 /**
  * 角色对象
  * @ClassName : com.zt.ssspm.sysmanage.entity.Role
  * @Description : TODO
  * @author : HeadMaster
  * @date : 2018年7月30日
  */
public class Role implements Serializable{
	
	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -2024498414198758759L;
	
	private Long id;
 	private String name;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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