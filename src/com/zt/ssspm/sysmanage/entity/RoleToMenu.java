package com.zt.ssspm.sysmanage.entity;
/**
 * 角色菜单对应表映射对象
 * @ClassName : com.zt.ssspm.sysmanage.entity.RoleToMenu
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月30日
 */
public class RoleToMenu {
	
	private Long roleId;
	private Long menuId;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	
}
