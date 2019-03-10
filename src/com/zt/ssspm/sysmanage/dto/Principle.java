package com.zt.ssspm.sysmanage.dto;

import java.io.Serializable;
import java.util.List;

import com.zt.ssspm.sysmanage.entity.Menu;

/**
 * 存储用户权限信息
 * @ClassName : com.zt.ssspm.sysmanage.dto.Principle
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月24日
 */
public class Principle implements Serializable{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -6492220137080742302L;
	
	private Long userId;
	private String loginName;
	private String name;
	private List<Menu> menuList;
	
	public Principle() {
		
	}
	
	public Principle(Long userId, String loginName, String name) {
		this.userId = userId;
		this.loginName = loginName;
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
