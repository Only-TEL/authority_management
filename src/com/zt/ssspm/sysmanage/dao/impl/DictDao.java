package com.zt.ssspm.sysmanage.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.zt.ssspm.common.BaseDao;
import com.zt.ssspm.common.PageDialect;
import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dao.IDictDao;
import com.zt.ssspm.sysmanage.entity.Dict;
import com.zt.ssspm.util.StringUtils;

public class DictDao extends BaseDao implements IDictDao{

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	@Override
	public List<Dict> queryAreaType(String type) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id,value,label,type,description,sort,parent_id,create_by,create_date,update_by,update_date,remarks ");
		buffer.append(" FROM pm_sys_dict WHERE type=? AND del_flag=0 ");
		List<Dict> list = this.template.query(buffer.toString(), new Object[]{type}, new BeanPropertyRowMapper<Dict>(Dict.class));
		if(list!=null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}
	@Override
	public List<String> queryAllType() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DISTINCT(type) FROM pm_sys_dict WHERE del_flag=0 AND 1=1");
		return template.query(buffer.toString(), new RowMapper<String>() {

			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		});
	}

	@Override
	public List<Dict> queryDictList(String type, String description, PageObject po) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id,value,label,type,description,sort,parent_id,create_by,create_date,update_by,update_date,remarks ");
		buffer.append(" FROM pm_sys_dict ");
		buffer.append(" WHERE del_flag=0 ");
		if(StringUtils.isNotNull(type)) {
			buffer.append(" AND type='"+type+"' ");
		}
		if(StringUtils.isNotNull(description)) {
			buffer.append(" AND type like '"+description+"' ");
		}
		List<Dict> dictList = new ArrayList<>();
		// 查询总记录数
		Integer rows = this.getTotalRows(buffer.toString());
		po.setTotalRow(rows);
		if(rows > 0) {
			// 获取数据库类型
			String dbType = this.getDbType();
			// 使用对应的SQL分页查询
			String sql = PageDialect.getPageDialectSQL(buffer.toString(), po, dbType);
			// 查询
			dictList = this.template.query(sql, new BeanPropertyRowMapper<Dict>(Dict.class));
		}
		return dictList;
	}

	@Override
	public Dict queryDictDetail(Long id) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id,value,label,type,description,sort,parent_id,create_by,create_date,update_by,update_date,remarks ");
		buffer.append(" FROM  pm_sys_dict WHERE id = ? AND del_flag=0 ");
		return template.queryForObject(buffer.toString(), new Object[] {id} ,new BeanPropertyRowMapper<Dict>(Dict.class));
		
	}
	
	@Override
	public boolean updateDict(Dict d) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_dict SET value=?,label=?,type=?,description=?,sort=?, ");
		buffer.append(" parent_id=?,create_by=?,create_date=?,update_by=?,update_date=?,remarks=? ");
		buffer.append(" WHERE id = ? AND del_flag=0 ");
		Object[] params = new Object[] {
				d.getValue(),d.getLabel(),d.getType(),d.getDescription(),d.getSort(),
				d.getParentId(),d.getCreateBy(),d.getCreateDate(),d.getUpdateBy(),d.getUpdateDate(),
				d.getRemarks(),d.getId()
		};
		int rows = this.template.update(buffer.toString(), params);
		return rows > 0;
	}

	@Override
	public boolean saveDict(Dict d) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" INSERT INTO pm_sys_dict (value,label,type,description,sort,parent_id,create_by,create_date,update_by,update_date,remarks) ");
		buffer.append(" VALUE(?,?,?,?,?,?,?,?,?,?,?) ");
		Object[] params = new Object[] {
				d.getValue(),d.getLabel(),d.getType(),d.getDescription(),d.getSort(),
				d.getParentId(),d.getCreateBy(),d.getCreateDate(),d.getUpdateBy(),d.getUpdateDate(),
				d.getRemarks()
		};
		int rows = this.template.update(buffer.toString(), params);
		return rows > 0;
	}

	@Override
	public boolean dictDelete(Long id) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_dict SET del_flag=1 WHERE id = ? AND 1=1 ");
		int rows = this.template.update(buffer.toString(), new Object[] {id});
		return rows > 0;
	}
}
