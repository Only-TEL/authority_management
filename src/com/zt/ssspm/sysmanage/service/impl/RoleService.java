package com.zt.ssspm.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.zt.ssspm.sysmanage.dao.IRoleDao;
import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.RoleToArea;
import com.zt.ssspm.sysmanage.entity.RoleToDept;
import com.zt.ssspm.sysmanage.entity.RoleToMenu;
import com.zt.ssspm.sysmanage.service.IRoleService;
import com.zt.ssspm.util.UserUtils;
import net.sf.json.JSONObject;

public class RoleService implements IRoleService {

	private IRoleDao roleDao;

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public List<Role> queryAllRole() {
		return roleDao.queryAllRole();
	}

	@Override
	public boolean updateRole(Role role, JSONObject menuJson, JSONObject deptJson, JSONObject areaJson) {
		boolean flag = false;
		// 查询数据库中的role对象
		Role realRole = roleDao.queryRoleById(role.getId());
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String loginName = UserUtils.getCurrentPrinciple().getLoginName();
		role.setUpdateBy(loginName);
		role.setUpdateDate(now);
		role.setCreateBy(realRole.getCreateBy());
		role.setCreateDate(realRole.getCreateDate());
		flag = roleDao.updateRole(role);
		// update的步骤
		// 第一：先删除与角色有关的中间表的数据
		flag = this.roleDao.delRoleAreaByRoleId(role.getId());
		flag = this.roleDao.delRoleDeptByRoleId(role.getId());
		flag = this.roleDao.delRoleMenuByRoleId(role.getId());
		
		// 第二：保存时在插入与角色有关的中间表的数据
		// 2.1构造角色菜单表对象
		List<RoleToMenu> roleMenuList = new ArrayList<>();
		Iterator<Long> itMenu = menuJson.keys();
		RoleToMenu roleToMenu;
		while(itMenu.hasNext()) {
			roleToMenu = new RoleToMenu();
			roleToMenu.setRoleId(role.getId());
			roleToMenu.setMenuId(new Long(menuJson.get(itMenu.next()).toString()));
			roleMenuList.add(roleToMenu);
		}
		// 2.2构造角色部门表对象
		List<RoleToDept> roleDeptList = new ArrayList<>();
		Iterator<Long> itDept = deptJson.keys();
		RoleToDept roleToDept;
		while(itDept.hasNext()) {
			roleToDept = new RoleToDept();
			roleToDept.setRoleId(role.getId());
			roleToDept.setDeptId(new Long(deptJson.get(itDept.next()).toString()));
			roleDeptList.add(roleToDept);
		}
		// 2.3构造角色部门表对象
		List<RoleToArea> roleAreaList = new ArrayList<>();
		Iterator<Long> itArea = areaJson.keys();
		RoleToArea roleToArea;
		while(itArea.hasNext()) {
			roleToArea = new RoleToArea();
			roleToArea.setRoleId(role.getId());
			roleToArea.setAreaId(new Long(areaJson.get(itArea.next()).toString()));
			roleAreaList.add(roleToArea);
		}
		// 2.4批量插入中间表数据
		flag = roleDao.addRoleToMenuBatch(roleMenuList);
		flag = roleDao.addRoleToDeptBatch(roleDeptList);
		flag = roleDao.addRoleToAreaBatch(roleAreaList);
		
		return flag;
	}

	@Override
	public boolean saveRole(Role role, JSONObject menuJson, JSONObject deptJson, JSONObject areaJson) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String loginName = UserUtils.getCurrentPrinciple().getLoginName();
		role.setCreateBy(loginName);
		role.setCreateDate(now);
		role.setUpdateBy(loginName);
		role.setUpdateDate(now);
		// 重要：保存角色时，返回保存角色的id
		Long roleId = roleDao.saveRole(role);
		
		// 构造角色菜单表对象
		List<RoleToMenu> roleMenuList = new ArrayList<>();
		Iterator<Long> itMenu = menuJson.keys();
		RoleToMenu roleToMenu;
		while(itMenu.hasNext()) {
			roleToMenu = new RoleToMenu();
			roleToMenu.setRoleId(roleId);
			roleToMenu.setMenuId(new Long(menuJson.get(itMenu.next()).toString()));
			roleMenuList.add(roleToMenu);
		}
		// 构造角色部门表对象
		List<RoleToDept> roleDeptList = new ArrayList<>();
		Iterator<Long> itDept = deptJson.keys();
		RoleToDept roleToDept;
		while(itDept.hasNext()) {
			roleToDept = new RoleToDept();
			roleToDept.setRoleId(roleId);
			roleToDept.setDeptId(new Long(deptJson.get(itDept.next()).toString()));
			roleDeptList.add(roleToDept);
		}
		// 构造角色部门表对象
		List<RoleToArea> roleAreaList = new ArrayList<>();
		Iterator<Long> itArea = areaJson.keys();
		RoleToArea roleToArea;
		while(itArea.hasNext()) {
			roleToArea = new RoleToArea();
			roleToArea.setRoleId(roleId);
			roleToArea.setAreaId(new Long(areaJson.get(itArea.next()).toString()));
			roleAreaList.add(roleToArea);
		}
		// 批量插入中间表数据
		boolean flag = false;
		flag = roleDao.addRoleToMenuBatch(roleMenuList);
		flag = roleDao.addRoleToDeptBatch(roleDeptList);
		flag = roleDao.addRoleToAreaBatch(roleAreaList);
		return flag;
	}

	@Override
	public boolean deleteRole(Long roleId) {
		boolean flag = false;
		// 删除相关表的所有数据     先删除从表，最后删除主表
		flag = roleDao.delRoleMenuByRoleId(roleId);
		flag = roleDao.delRoleDeptByRoleId(roleId);
		flag = roleDao.delRoleAreaByRoleId(roleId);
		
		flag = roleDao.deleteRole(roleId);
		return flag;
	}

	@Override
	public Role queryRoleById(Long roleId) {
		return roleDao.queryRoleById(roleId);
	}

	@Override
	public List<RoleToMenu> queryRoleMenuListByRoleId(Long roleId) {
		return roleDao.queryRoleMenuListByRoleId(roleId);
	}

	@Override
	public List<RoleToDept> queryRoleDeptListByRoleId(Long roleId) {
		return roleDao.queryRoleDeptListByRoleId(roleId);
	}

	@Override
	public List<RoleToArea> queryRoleAreaListByRoleId(Long roleId) {
		return roleDao.queryRoleAreaListByRoleId(roleId);
	}

	@Override
	public List<Role> queryRoleByUserId(Long UserId) {
		return roleDao.queryRoleByUserId(UserId);
	}
	
}
