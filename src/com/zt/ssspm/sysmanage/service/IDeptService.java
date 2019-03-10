package com.zt.ssspm.sysmanage.service;

import java.util.List;

import com.zt.ssspm.sysmanage.entity.Dept;

public interface IDeptService {
	/**
	 * 查询所有的部门信息
	 * @Title: queryAllDept
	 * @Description: TODO
	 * @return
	 */
	List<Dept> queryAllDept();
	/**
	 * 保存dept
	 * @Title: saveDept
	 * @Description: TODO
	 * @param dept
	 * @return
	 */
	boolean saveDept(Dept dept);
	/**
	 * 查询当前用户有权限查询的部门
	 * @Title: queryDeptByUserId
	 * @Description: TODO
	 * @param currentUserId
	 * @return
	 */
	List<Dept> queryDeptByUserId(Long userId);
	/**
	 * 根据deptId查询详细信息
	 * @Title: queryDeptById
	 * @Description: TODO
	 * @param deptId
	 * @return
	 */
	Dept queryDeptById(Long deptId);
	/**
	 * 更新部门信息
	 * @Title: updateDept
	 * @Description: TODO
	 * @param dept
	 * @return
	 */
	boolean updateDept(Dept dept);
	/**
	 * 逻辑删除部门信息
	 * @Title: deleteDept
	 * @Description: TODO
	 * @param deptId
	 * @return
	 */
	boolean deleteDept(Long deptId);
	/**
	 * 查询是否存在有效的子部门
	 * @param deptId 
	 * @Title: queryHasChild
	 * @Description: TODO
	 * @return
	 */
	boolean queryHasChild(Long deptId);

}
