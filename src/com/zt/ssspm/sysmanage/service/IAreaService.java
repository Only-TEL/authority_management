package com.zt.ssspm.sysmanage.service;

import java.util.List;
import com.zt.ssspm.sysmanage.entity.Area;

public interface IAreaService {
	/**
	 * 查询所有的区域信息
	 * @Title: queryAllAreaList
	 * @Description: TODO
	 * @return
	 */
	List<Area> queryAllAreaList();
	/**
	 * 向数据库中插入一条记录
	 * @Title: saveDept
	 * @Description: TODO
	 * @param area
	 * @return
	 */
	boolean saveArea(Area area);
	/**
	 * 根据id查询area对象
	 * @Title: queryDeptById
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	Area queryAreaById(Long areaId);
	/**
	 * 更新
	 * @Title: updateDept
	 * @Description: TODO
	 * @param area
	 * @return
	 */
	boolean updateArea(Area area);
	/**
	 * 判断一个节点是否存在子节点
	 * @Title: queryHasChild
	 * @Description: TODO
	 * @param areaId
	 * @return
	 */
	boolean queryHasChild(Long areaId);
	/**
	 * 删除一个区域信息
	 * @Title: deleteArea
	 * @Description: TODO
	 * @param areaId
	 * @return
	 */
	boolean deleteArea(Long areaId);
	
	/**
	 * 查询当前对象有权限的区域信息
	 * @Title: queryAreaListByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	List<Area> queryAreaListByUserId(Long userId);
}
