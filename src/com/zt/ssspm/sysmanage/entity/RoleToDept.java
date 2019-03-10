package com.zt.ssspm.sysmanage.entity;
/**
 * 角色部门对应表映射对象
 * @ClassName : com.zt.ssspm.sysmanage.entity.RoleToDept
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月30日
 */
public class RoleToDept {
	
	private Long roleId;
	private Long deptId;
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
}
