package com.zt.ssspm.sysmanage.dao;

import java.util.List;
import com.zt.ssspm.sysmanage.entity.Menu;

public interface IMenuDao {

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
	 * 更新菜单
	 * @Title: updateMenu
	 * @Description: TODO
	 * @param m
	 * @return
	 */
	boolean updateMenu(Menu m);
	/**
	 * 保存菜单
	 * @Title: saveMenu
	 * @Description: TODO
	 * @param m
	 * @return
	 */
	Long saveMenu(Menu m);
	/**
	 * 根据id删除menu的数据(逻辑删除)
	 * @Title: delMenu
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	boolean delMenu(Long menuId);
	/**
	 * 判断一个菜单是否存在子菜单
	 * @Title: hasChildMenu
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	boolean hasChildMenu(Long menuId);
	/**
	 * 查询一个菜单的所有子菜单的id
	 * @Title: getChildrenIds
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	List<Long> getChildrenIds(Long menuId);
	/**
	 * 查询一个菜单下的所有附属菜单的id集合
	 * @Title: getChildrenIds
	 * @Description: TODO
	 * @param menuId
	 * @param listIds
	 */
	void getChildrenIds(Long menuId,List<Long> listIds);
	/**
	 * 查询除了menuId对应的菜单及其附属菜单之外的所有菜单
	 * @Title: queryOtherMenuById
	 * @Description: TODO
	 * @param menuId
	 * @return
	 */
	List<Menu> queryOtherMenuById(Long menuId);
	/**
	 * 查询子菜单
	 * @Title: queryChildrenMenu
	 * @Description: TODO
	 * @param parentId
	 * @return
	 */
	List<Menu> queryChildrenMenu(long parentId);
}
