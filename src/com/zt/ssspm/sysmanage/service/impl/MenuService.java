package com.zt.ssspm.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.List;

import com.zt.ssspm.sysmanage.dao.IMenuDao;
import com.zt.ssspm.sysmanage.dao.IRoleDao;
import com.zt.ssspm.sysmanage.dto.Principle;
import com.zt.ssspm.sysmanage.entity.Menu;
import com.zt.ssspm.sysmanage.entity.RoleToMenu;
import com.zt.ssspm.sysmanage.service.IMenuService;
import com.zt.ssspm.util.UserUtils;

public class MenuService implements IMenuService {

	private IMenuDao menuDao;
	private IRoleDao roleDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	@Override
	public List<Menu> queryMenuListByUserId(Long userId) {
		return menuDao.queryMenuListByUserId(userId);
	}

	@Override
	public List<Menu> queryAllMenu() {
		return menuDao.queryAllMenu();
	}

	@Override
	public Menu queryMenuById(Long id) {
		return menuDao.queryMenuById(id);
	}

	@Override
	public boolean saveMenu(Menu m) {
		// 添加菜单时，需要将菜单加入到角色菜单对应表中，不然无法使用这个菜单。同样无法查询到
		String name = UserUtils.getCurrentPrinciple().getLoginName();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		m.setCreateBy(name);
		m.setCreateDate(now);
		m.setUpdateBy(name);
		m.setUpdateDate(now);
		Long menuId = menuDao.saveMenu(m);
		RoleToMenu roleMenu = new RoleToMenu();
		roleMenu.setMenuId(menuId);
		roleMenu.setRoleId(1L);
		return roleDao.addRoleToMenu(roleMenu);
	}

	@Override
	public boolean updateMenu(Menu m) {
		Menu realMenu = menuDao.queryMenuById(m.getId());
		// 设置原始属性
		m.setCreateBy(realMenu.getCreateBy());
		m.setCreateDate(realMenu.getCreateDate());
		// 获取主体对象信息
		Principle principle = UserUtils.getCurrentPrinciple();
		//修改的时候注意填充修改人和修改时间
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		m.setUpdateDate(currentDate);
		m.setUpdateBy(principle.getLoginName());
		return menuDao.updateMenu(m);
	}

	@Override
	public boolean delMenu(Long menuId) {
		// 同时需要删除角色菜单对应表中的信息
		return menuDao.delMenu(menuId);
	}

	@Override
	public List<Menu> queryOtherMenuById(Long menuId) {
		return menuDao.queryOtherMenuById(menuId);
	}

	@Override
	public boolean queryHasChildMenu(Long menuId) {
		return menuDao.hasChildMenu(menuId);
	}

	@Override
	public List<Menu> queryChildrenMenu(long parentId) {
		return menuDao.queryChildrenMenu(parentId);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}


}
