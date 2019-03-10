package com.zt.ssspm.sysmanage.dao;

import java.util.List;

import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.RoleToArea;
import com.zt.ssspm.sysmanage.entity.RoleToDept;
import com.zt.ssspm.sysmanage.entity.RoleToMenu;

public interface IRoleDao {

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
	 * @return
	 */
	boolean updateRole(Role role);
	
	/**
	 * 保存角色信息
	 * @Title: saveRole
	 * @Description: TODO
	 * @param role
	 * @return
	 */
	Long saveRole(Role role);
	
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
	 * 批量插入角色菜单表
	 * @Title: addRoleToMenuBatch
	 * @Description: TODO
	 * @param roleMenuList
	 * @return
	 */
	boolean addRoleToMenuBatch(List<RoleToMenu> roleMenuList);
	
	/**
	 * 添加角色菜单表
	 * @Title: addRoleToMenu
	 * @Description: TODO
	 * @param roleMenu
	 * @return
	 */
	boolean addRoleToMenu(RoleToMenu roleMenu);
	
	/**
	 * 添加角色部门表
	 * @Title: addRoleToDept
	 * @Description: TODO
	 * @param roleDept
	 * @return
	 */
	boolean addRoleToDept(RoleToDept roleDept);
	
	/**
	 * 添加角色区域表
	 * @Title: addRoleToArea
	 * @Description: TODO
	 * @param roleArea
	 * @return
	 */
	boolean addRoleToArea(RoleToArea roleArea);
	
	/**
	 * 批量插入角色部门表
	 * @Title: addRoleToDeptBatch
	 * @Description: TODO
	 * @param roleDeptList
	 * @return
	 */
	boolean addRoleToDeptBatch(List<RoleToDept> roleDeptList);

	/**
	 * 批量插入角色区域表
	 * @Title: addRoleToAreaBatch
	 * @Description: TODO
	 * @param roleAreaList
	 * @return
	 */
	boolean addRoleToAreaBatch(List<RoleToArea> roleAreaList);
	
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

	/**
	 * delRoleMenuByRoleId
	 * @Title: delRoleMenuByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	boolean delRoleMenuByRoleId(Long roleId);

	/**
	 * delRoleDeptByRoleId
	 * @Title: delRoleDeptByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	boolean delRoleDeptByRoleId(Long roleId);

	/**
	 * delRoleAreaByRoleId
	 * @Title: delRoleAreaByRoleId
	 * @Description: TODO
	 * @param roleId
	 * @return
	 */
	boolean delRoleAreaByRoleId(Long roleId);
}
