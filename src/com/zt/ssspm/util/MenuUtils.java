package com.zt.ssspm.util;

import java.util.List;

import com.zt.ssspm.sysmanage.entity.Menu;

/**
 * 菜单处理工具类
 * @ClassName : com.zt.ssspm.util.MenuUtils
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月26日
 */
public class MenuUtils {

	/**
	 * 将menuList排序，按树枝结构排序
	 * @Title: sortMenuList
	 * @Description: TODO
	 * @param returnList
	 * @param menuList
	 * @param parentId
	 */
	public static void sortMenuList(List<Menu> returnList,List<Menu> menuList,Long parentId){
		int size = menuList.size();
		
		for(int i=0;i<size;i++) {
			Menu m = menuList.get(i);
			if(m.getParentId() != null && m.getParentId().equals(parentId)) {
				returnList.add(m);
				for (Menu child : menuList) {
                	//准备递归
                    if (child.getParentId() != null && child.getParentId().equals(parentId)) {
                        //做递归
                        sortMenuList(returnList, menuList, m.getId());
                        break;
                    }
                }
			}
		}
	}
	
}
