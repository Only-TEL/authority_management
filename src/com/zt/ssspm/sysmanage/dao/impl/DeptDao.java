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

import com.zt.ssspm.sysmanage.dao.IDeptDao;
import com.zt.ssspm.sysmanage.entity.Dept;

public class DeptDao  implements IDeptDao {

	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Dept> queryAllDept() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT c.id,c.parent_id,c.name,c.sort,c.code,c.address,c.master,c.phone,c.fax,c.email, ");
		buffer.append(" c.create_by,c.create_date,c.update_by,c.update_date,c.remarks ");
		buffer.append(" FROM pm_sys_dept c WHERE c.del_flag=0 AND 1=1 ");
		List<Dept> deptList =  this.template.query(buffer.toString(), new BeanPropertyRowMapper<Dept>(Dept.class));
		return deptList;
	}

	@Override
	public Long saveDept(Dept dept) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" INSERT INTO pm_sys_dept (parent_id,name,sort,code,address,master,phone,fax,email, ");
		buffer.append(" create_by,create_date,update_by,update_date,remarks) ");
		buffer.append(" VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		KeyHolder holder = new GeneratedKeyHolder();
		this.template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				
				PreparedStatement ps = connection.prepareStatement(buffer.toString());
				ps.setObject(1, dept.getParentId());
				ps.setObject(2, dept.getName());
				ps.setObject(3, dept.getSort());
				ps.setObject(4, dept.getCode());
				ps.setObject(5, dept.getAddress());
				ps.setObject(6, dept.getMaster());
				ps.setObject(7, dept.getPhone());
				ps.setObject(9, dept.getFax());
				ps.setObject(10, dept.getEmail());
				ps.setObject(11, dept.getCreateDate());
				ps.setObject(12, dept.getUpdateBy());
				ps.setObject(13, dept.getUpdateDate());
				ps.setObject(13, dept.getRemarks());
				return ps;
			}
		},holder);
		return holder.getKey().longValue();
	}

	@Override
	public Dept queryDeptById(Long deptId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT c.id,d.name AS parent_name,c.parent_id,c.name,c.sort,c.code,c.address,c.master,c.phone,c.fax,c.email, ");
		buffer.append(" c.create_by,c.create_date,c.update_by,c.update_date,c.remarks ");
		buffer.append(" FROM pm_sys_dept c LEFT JOIN pm_sys_dept d ON c.parent_id=d.id ");
		buffer.append(" WHERE c.id=? AND c.del_flag=0 ");
		
		return this.template.queryForObject(buffer.toString(), new Object[] {deptId},  new BeanPropertyRowMapper<Dept>(Dept.class));
	}

	@Override
	public boolean updateDept(Dept dept) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_dept SET parent_id=?,name=?,sort=?,code=?,address=?,master=?,phone=?,fax=?,email=?, ");
		buffer.append(" create_by=?,create_date=?,update_by=?,update_date=?,remarks=? ");
		buffer.append(" WHERE id=? AND del_flag=0 ");
		
		int rows = this.template.update(buffer.toString(), dept.getParentId(), dept.getName(), dept.getSort(), dept.getCode(), dept.getAddress(), dept.getMaster(), dept.getPhone(),
							dept.getFax(), dept.getEmail(), dept.getCreateBy(), dept.getCreateDate(), dept.getUpdateBy(), dept.getUpdateDate(), dept.getRemarks(), dept.getId());
		return rows > 0;
	}

	@Override
	public boolean deleteDept(Long deptId) {
		String sql = " UPDATE pm_sys_dept SET del_flag=1 WHERE id=? AND 1=1 ";
		 int rows = this.template.update(sql,deptId);
		 return rows > 0;
	}

	@Override
	public boolean hasChild(Long deptId) {
		String sql = " select count(id) AS count from pm_sys_dept where parent_id=? and del_flag=0 ";
		Long count = this.template.queryForObject(sql, new Object[] {deptId}, new RowMapper<Long>() {

			@Override
			public Long mapRow(ResultSet rs, int rows) throws SQLException {
				return rs.getLong("count");
			}
		});
		if(count == null) {
			count = 0L;
		}
		return count > 0;
	}

	@Override
	public List<Dept> queryDeptByUserId(Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT C.ID,C.PARENT_ID,C.NAME,C.SORT,C.CODE,C.ADDRESS,C.MASTER,C.PHONE,C.FAX,C.EMAIL ");
		buffer.append(" FROM PM_SYS_USER_ROLE A ,PM_SYS_ROLE_DEPT B,PM_SYS_DEPT C ");
		buffer.append(" WHERE A.USER_ID=? ");
		buffer.append(" AND A.ROLE_ID = B.ROLE_ID AND B.DEPT_ID = C.ID AND C.DEL_FLAG=0 ");
		
		List<Dept> deptList =  this.template.query(buffer.toString(),  new Object[]{userId}, new BeanPropertyRowMapper<Dept>(Dept.class));
		if(deptList!=null && deptList.size()>0) {
			return deptList;
		}else {
			return null;
		}
		
	}
	
}
