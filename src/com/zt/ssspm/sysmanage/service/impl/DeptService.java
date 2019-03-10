package com.zt.ssspm.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.zt.ssspm.sysmanage.dao.IDeptDao;
import com.zt.ssspm.sysmanage.dao.IRoleDao;
import com.zt.ssspm.sysmanage.entity.Dept;
import com.zt.ssspm.sysmanage.entity.RoleToDept;
import com.zt.ssspm.sysmanage.service.IDeptService;
import com.zt.ssspm.util.UserUtils;

public class DeptService implements IDeptService {

	private IDeptDao deptDao;
	private IRoleDao roleDao;

	public void setDeptDao(IDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	@Override
	public List<Dept> queryAllDept() {
		return deptDao.queryAllDept();
	}

	@Override
	public boolean saveDept(Dept dept) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String userName = UserUtils.getCurrentPrinciple().getLoginName();
		dept.setCreateDate(now);
		dept.setUpdateDate(now);
		dept.setUpdateBy(userName);
		dept.setCreateBy(userName);
		Long deptId = deptDao.saveDept(dept);
		// 保存角色部门信息
		RoleToDept roleDept = new RoleToDept();
		roleDept.setDeptId(deptId);
		roleDept.setRoleId(1L);
		return roleDao.addRoleToDept(roleDept);
	}

	@Override
	public List<Dept> queryDeptByUserId(Long userId) {
		return deptDao.queryDeptByUserId(userId);
	}

	@Override
	public Dept queryDeptById(Long deptId) {
		return deptDao.queryDeptById(deptId);
	}

	@Override
	public boolean updateDept(Dept dept) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String loginName = UserUtils.getCurrentPrinciple().getLoginName();
		dept.setUpdateBy(loginName);
		dept.setUpdateDate(now);
		return deptDao.updateDept(dept);
	}

	@Override
	public boolean deleteDept(Long deptId) {
		return deptDao.deleteDept(deptId);
	}

	@Override
	public boolean queryHasChild(Long deptId) {
		return deptDao.hasChild(deptId);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
}
