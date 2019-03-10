package com.zt.ssspm.util;

import java.util.List;

import com.zt.ssspm.sysmanage.entity.Dept;
/**
 * 菜单信息帮助类
 * @ClassName : com.zt.ssspm.util.DeptUtils
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月27日
 */
public class DeptUtils {

    /**
     * 对部门列表进行排序
     * @param menuList
     * @param parentId
     * @param cascade
     * @return
     */
    public static void sortDeptList (List<Dept> returnDepts,List<Dept> deptList,Long parentId) {
    	// 轮询所有菜单
        for (int i = 0; i < deptList.size(); i++) {
        	Dept m = deptList.get(i);
            // 找到第一级菜单
            if (m.getParentId() != null&& m.getParentId().equals(parentId)) {
            	returnDepts.add(m);             
                for (Dept child : deptList) {
                	// 准备递归
                    if (child.getParentId() != null&& child.getParentId().equals(parentId)) {
                        // 做递归
                    	sortDeptList(returnDepts, deptList, m.getId());
                        break;
                    }
                }
                
            }
        }
    }
}
