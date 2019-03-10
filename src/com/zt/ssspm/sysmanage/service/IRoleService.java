package com.zt.ssspm.sysmanage.service;

import java.util.List;
import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.RoleToArea;
import com.zt.ssspm.sysmanage.entity.RoleToDept;
import com.zt.ssspm.sysmanage.entity.RoleToMenu;

import net.sf.json.JSONObject;

public interface IRoleService {

	/**
	 * 查询所有的角色信息
	 * @Title: queryAllRole
	 * @Description: TODO
	 * @return
	 */
	List<Role> queryAllRole();
	
	/**
	 * 根据用户id查询角色列表
	 * @Title: queryRoleByUserId
	 * @Description: TODO
	 * @param UserId
	 * @return
	 */
	List<Role> queryRoleByUserId(Long UserId);
	
	/**
	 * 角色
	 * @Title: updateRole
	 * @Description: TODO
	 * @param role
	 * @param areaJson 
	 * @param deptJson 
	 * @param menuJson 
	 * @return
	 */
	boolean updateRole(Role role, JSONObject menuJson, JSONObject deptJson, JSONObject areaJson);
	
	/**
	 * 保存角色信息
	 * @Title: saveRole
	 * @Description: TODO
	 * @param role
	 * @param areaJson 
	 * @param deptJson 
	 * @param menuJson 
	 * @return
	 */
	boolean saveRole(Role role, JSONObject menuJson, JSONObject deptJson, JSONObject areaJson);
	
	/**
	 * 删除角色信息
	 * @Title: deleteRole
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	boolean deleteRole(Long roleId);
	
	/**
	 * 查询role的详细信息
	 * @Title: queryRoleById
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	Role queryRoleById(Long roleId);

	/**
	 * queryRoleMenuListByRoleId
	 * @Title: queryRoleMenuListByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	List<RoleToMenu> queryRoleMenuListByRoleId(Long roleId);

	/**
	 * queryRoleDeptListByRoleId
	 * @Title: queryRoleDeptListByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	List<RoleToDept> queryRoleDeptListByRoleId(Long roleId);

	/**
	 * queryRoleAreaListByRoleId
	 * @Title: queryRoleAreaListByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	List<RoleToArea> queryRoleAreaListByRoleId(Long roleId);
	
}
