package com.zt.ssspm.sysmanage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.zt.ssspm.sysmanage.dao.IAreaDao;
import com.zt.ssspm.sysmanage.entity.Area;

public class AreaDao implements IAreaDao {

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Area> queryAllAreaList() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id,parent_id,name,sort,code,type,create_by,create_date,update_by,update_date,remarks ");
		buffer.append(" FROM pm_sys_area WHERE del_flag=0 AND 1=1 ");
		List<Area> list = this.template.query(buffer.toString(), new BeanPropertyRowMapper<Area>(Area.class));
		if(list!=null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public Long saveArea(Area area) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" INSERT INTO pm_sys_area (parent_id,name,sort,code,type,create_by,create_date,update_by,update_date,remarks) ");
		buffer.append(" VALUE(?,?,?,?,?,?,?,?,?,?) ");
		
		KeyHolder holder = new GeneratedKeyHolder();
		this.template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(buffer.toString());
				
				ps.setObject(1, area.getParentId());
				ps.setObject(2, area.getName());
				ps.setObject(3, area.getSort());
				ps.setObject(4, area.getCode());
				ps.setObject(5, area.getType());
				ps.setObject(6, area.getCreateBy());
				ps.setObject(7, area.getCreateDate());
				ps.setObject(8, area.getUpdateBy());
				ps.setObject(9, area.getUpdateDate());
				ps.setObject(10, area.getRemarks());
				return ps;
			}
		},holder);
		
		return holder.getKey().longValue();
	}

	@Override
	public Area queryAreaById(Long areaId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT a.id,a.parent_id,p.name parent_name,a.name,a.sort,a.code,a.type,a.create_by,a.create_date,a.update_by,a.update_date,a.remarks ");
		buffer.append(" FROM pm_sys_area a LEFT JOIN pm_sys_area p ON a.parent_id=p.id WHERE a.id=? AND a.del_flag=0 ");
		return this.template.queryForObject(buffer.toString(), new Object[] {areaId}, new BeanPropertyRowMapper<Area>(Area.class));
	}

	@Override
	public boolean updateArea(Area area) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_area SET parent_id=?,name=?,sort=?,code=?,type=?,create_by=?,create_date=?,update_by=?,update_date=?,remarks=? ");
		buffer.append(" WHERE id=? AND del_flag=0 ");
		int rows = this.template.update(buffer.toString(), area.getParentId(),area.getName(),area.getSort(),area.getCode(),area.getType(),area.getCreateBy(),
							area.getCreateDate(),area.getUpdateBy(),area.getUpdateDate(),area.getRemarks(),area.getId());
		return rows > 0;
	}

	@Override
	public boolean queryHasChild(Long areaId) {
		String sql = " SELECT COUNT(ID) TOTAL FROM pm_sys_area WHERE PARENT_ID=? AND del_flag=0 ";
		Integer total = this.template.queryForObject(sql, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getInt("TOTAL");
			}
		}, areaId);
		return total.intValue() > 0;
	}

	@Override
	public boolean deleteArea(Long areaId) {
		String sql = " UPDATE pm_sys_area SET del_flag=1 WHERE ID=? AND 1=1 ";
		int rows = this.template.update(sql, areaId);
		return rows > 0;
	}

	@Override
	public List<Area> queryAreaListByUserId(Long userId) {
		//根据用户id 获取用户角色   关联角色区域对应表  获取用户能操作的区域的所有 记录
		StringBuffer buffer = new StringBuffer();
	 	
		buffer.append(" SELECT C.ID,C.PARENT_ID,C.NAME,C.SORT,C.CODE  ");
		buffer.append(" FROM PM_SYS_USER_ROLE A ,PM_SYS_ROLE_AREA B ,PM_SYS_AREA C ");
		buffer.append(" WHERE A.USER_ID=? AND A.ROLE_ID = B.ROLE_ID AND B.AREA_ID = C.ID ");
		
		List<Area> areaList =  this.template.query(buffer.toString(),  new Object[]{userId},new BeanPropertyRowMapper<Area>(Area.class));
		if(areaList.size()>0 && areaList!=null)
			return areaList;
		else
			return null;
	}
}
