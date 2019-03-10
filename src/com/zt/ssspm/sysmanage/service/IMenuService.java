package com.zt.ssspm.sysmanage.service;

import java.util.List;
import com.zt.ssspm.sysmanage.entity.Menu;

public interface IMenuService {


	/**
	 * 根据对象id查询菜单列表
	 * @Title: queryMenuListByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	List<Menu> queryMenuListByUserId(Long userId);
	/**
	 * 查询所有的菜单信息
	 * @Title: queryAllMenu
	 * @Description: TODO
	 * @return
	 */
	List<Menu> queryAllMenu();
	/**
	 * 根据id查询menu的详细信息
	 * @Title: queryMenuById
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	Menu queryMenuById(Long id);
	/**
	 * 保存新菜单
	 * @Title: saveMenu
	 * @Description: TODO
	 * @param m
	 * @return
	 */
	boolean saveMenu(Menu m);
	/**
	 * 更新菜单信息
	 * @Title: updateMenu
	 * @Description: TODO
	 * @param m
	 * @return
	 */
	boolean updateMenu(Menu m);
	/**
	 * 删除角色信息
	 * @Title: delMenu
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	boolean delMenu(Long menuId);
	/**
	 * 查询除了menuId对应的菜单及其附属菜单之外的所有菜单
	 * @Title: queryOtherMenuById
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	List<Menu> queryOtherMenuById(Long menuId);
	/**
	 * 判断一个菜单是否存在子菜单
	 * @Title: hasChildMenu
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	boolean queryHasChildMenu(Long menuId);
	/**
	 * 查询子菜单
	 * @Title: queryChildrenMenu
	 * @Description: TODO
	 * @param parentId
	 * @return
	 */
	List<Menu> queryChildrenMenu(long parentId);
}
