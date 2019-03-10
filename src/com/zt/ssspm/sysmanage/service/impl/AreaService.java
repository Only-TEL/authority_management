package com.zt.ssspm.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.zt.ssspm.sysmanage.dao.IAreaDao;
import com.zt.ssspm.sysmanage.dao.IRoleDao;
import com.zt.ssspm.sysmanage.entity.Area;
import com.zt.ssspm.sysmanage.entity.RoleToArea;
import com.zt.ssspm.sysmanage.service.IAreaService;
import com.zt.ssspm.util.UserUtils;

public class AreaService implements IAreaService {

	private IAreaDao areaDao;
	private IRoleDao roleDao;
	
	public void setAreaDao(IAreaDao areaDao) {
		this.areaDao = areaDao;
	}

	@Override
	public List<Area> queryAllAreaList() {
		return areaDao.queryAllAreaList();
	}

	@Override
	public boolean saveArea(Area area) {
		boolean flag = false;
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String userName = UserUtils.getCurrentPrinciple().getLoginName();
		area.setCreateBy(userName);
		area.setCreateDate(now);
		area.setUpdateBy(userName);
		area.setUpdateDate(now);
		Long areaId = areaDao.saveArea(area);
		// 保存role_area表
		RoleToArea roleArea = new RoleToArea();
		roleArea.setRoleId(1L);
		roleArea.setAreaId(areaId);
		roleDao.addRoleToArea(roleArea);
		return flag;
	}

	@Override
	public Area queryAreaById(Long areaId) {
		return areaDao.queryAreaById(areaId);
	}

	@Override
	public boolean updateArea(Area area) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String userName = UserUtils.getCurrentPrinciple().getLoginName();
		area.setUpdateBy(userName);
		area.setUpdateDate(now);
		return areaDao.updateArea(area);
	}

	@Override
	public boolean queryHasChild(Long areaId) {
		return areaDao.queryHasChild(areaId);
	}

	@Override
	public boolean deleteArea(Long areaId) {
		return areaDao.deleteArea(areaId);
	}

	@Override
	public List<Area> queryAreaListByUserId(Long userId) {
		return areaDao.queryAreaListByUserId(userId);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
}
