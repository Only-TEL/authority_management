package com.zt.ssspm.sysmanage.entity;

import java.io.Serializable;

public class UserToRole implements Serializable{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -289500746570207438L;

	private Long userId;
	private Long roleId;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
