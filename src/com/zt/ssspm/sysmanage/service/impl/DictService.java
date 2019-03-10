package com.zt.ssspm.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.List;
import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dao.IDictDao;
import com.zt.ssspm.sysmanage.entity.Dict;
import com.zt.ssspm.sysmanage.service.IDictService;

public class DictService implements IDictService {

	private IDictDao dictDao;
	
	@Override
	public List<String> queryAllType() {
		
		return dictDao.queryAllType();
	}

	public void setDictDao(IDictDao dictDao) {
		this.dictDao = dictDao;
	}

	@Override
	public List<Dict> queryDictList(String type, String description, PageObject po) {
		return dictDao.queryDictList(type, description,po);
	}

	@Override
	public Dict queryDictDetail(Long id) {
		return dictDao.queryDictDetail(id);
	}

	@Override
	public boolean updateDict(Dict d) {
		return dictDao.updateDict(d);
	}

	@Override
	public boolean saveDict(Dict d) {
		Long currentTime = System.currentTimeMillis();
		// 创建用户名，与更新用户名需要使用....
		if(d.getParentId()==null) d.setParentId("0");
		d.setCreateDate(new Timestamp(currentTime));
		d.setUpdateDate(new Timestamp(currentTime));
		return dictDao.saveDict(d);
	}

	@Override
	public boolean dictDelete(Long id) {
		return dictDao.dictDelete(id);
	}

	@Override
	public List<Dict> queryAreaType(String type) {
		return dictDao.queryAreaType(type);
	}
}
