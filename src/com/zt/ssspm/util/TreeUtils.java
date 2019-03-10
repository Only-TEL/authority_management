package com.zt.ssspm.util;

import java.util.List;
import java.util.Map;
import com.zt.ssspm.sysmanage.dto.TreeDto;

/**
 * 树形对象的操作方法
 * @ClassName : com.zt.ssspm.util.TreeUtils
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月28日
 */
public class TreeUtils {

	/**
	 * 获取所有的子节点
	 * @Title: getAllChildrenList
	 * @Description: TODO
	 * @param childrenList	parentId的所有的子节点
	 * @param treeList   所有的树节点
	 * @param parentId	 
	 */
	public static void getAllChildrenList(List<TreeDto> childrenList,List<TreeDto> treeList,long parentId) {
		for(TreeDto tree:treeList){
    		//遍历出父id等于parentId的所有子节点
    		if(tree.getParentId().longValue() == parentId){
    			childrenList.add(tree);
    			getAllChildrenList(childrenList,treeList,tree.getId());
    		}
    	}
	}
	/**
	 * 获取所有的子节点
	 * @Title: getAllChildrenMap
	 * @Description: TODO
	 * @param childrenMap
	 * @param treeList
	 * @param parentId
	 */
	public static void getAllChildrenMap(Map<Long,TreeDto> childrenMap,List<TreeDto> treeList,long parentId) {
		for(TreeDto tree:treeList) {
			if(tree.getParentId().longValue() == parentId) {
				childrenMap.put(tree.getId(), tree);
				getAllChildrenMap(childrenMap,treeList,tree.getId());
			}
		}
	}
	/**
	 * 对数据结构列表根据父节点id进行排序
	 * @Title: sortTreeDtoList
	 * @Description: TODO
	 * @param returnTreeList
	 * @param treeList
	 * @param parentId
	 */
	@SuppressWarnings("unchecked")
	public static <T> void sortTreeDtoList (List<T> returnTreeList,List<T> treeList,Long parentId) {
    	// 轮询所有菜单
        for (int i = 0; i < treeList.size(); i++) {
        	TreeDto m = (TreeDto) treeList.get(i);
            // 找到第一级菜单
            if (m.getParentId() != null&& m.getParentId().equals(parentId)) {
            	returnTreeList.add( (T) m);             
                for (T child : treeList) {
                	// 准备递归
                    if (((TreeDto) child).getParentId() != null&& ((TreeDto) child).getParentId().equals(parentId)) {
                        // 做递归
                    	sortTreeDtoList(returnTreeList, treeList, m.getId());
                        break;
                    }
                }
                
            }
        }
    }
	
	
}
