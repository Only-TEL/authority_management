package com.zt.ssspm.sysmanage.dto;

import java.io.Serializable;

public class TreeDto implements Serializable{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -6598786506589850243L;

	private Long id;
	private Long parentId;
	private String name;
	
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

}
