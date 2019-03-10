package com.zt.ssspm.sysmanage.service;

import java.util.List;

import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.entity.Dict;

public interface IDictService {

	/**
	 * 查询所有的类型
	 * @Title: queryAllType
	 * @Description: TODO
	 * @return
	 */
	List<String> queryAllType();
	/**
	 * 查询所有的字典数据
	 * @Title: queryDictList
	 * @Description: TODO
	 * @param type
	 * @param description
	 * @param po 
	 * @return
	 */
	List<Dict> queryDictList(String type, String description, PageObject po);
	/**
	 * 查询字典的详细信息
	 * @Title: queryDictDetail
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	Dict queryDictDetail(Long id);
	/**
	 * 更新字典数据
	 * @Title: updateDict
	 * @Description: TODO
	 * @param d
	 * @return
	 */
	boolean updateDict(Dict d);
	/**
	 * 保存字典
	 * @Title: saveDict
	 * @Description: TODO
	 * @param d
	 * @return
	 */
	boolean saveDict(Dict d);
	/**
	 * 根据id逻辑删除
	 * @Title: dictDelete
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	boolean dictDelete(Long id);
	/**
	 * 根据类型查询字典集合
	 * @Title: queryAreaType
	 * @Description: TODO
	 * @param type
	 * @return
	 */
	List<Dict> queryAreaType(String type);
}
