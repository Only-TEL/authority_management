package com.zt.ssspm.sysmanage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.zt.ssspm.sysmanage.dao.IRoleDao;
import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.RoleToArea;
import com.zt.ssspm.sysmanage.entity.RoleToDept;
import com.zt.ssspm.sysmanage.entity.RoleToMenu;

public class RoleDao implements IRoleDao {

	private JdbcTemplate template;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public List<Role> queryAllRole() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id,name,create_by,create_date,update_by,update_date,remarks ");
		buffer.append(" FROM pm_sys_role WHERE del_flag=0 AND 1=1 ");
		
		return this.template.query(buffer.toString(), new BeanPropertyRowMapper<Role>(Role.class));
	}

	@Override
	public boolean updateRole(Role role) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_role SET name=?,create_by=?,create_date=?,update_by=?,update_date=?,remarks=? ");
		buffer.append(" WHERE id=? AND del_flag=0 ");
		int rows = this.template.update(buffer.toString(), role.getName(),role.getCreateBy(),role.getCreateDate(),
							role.getUpdateBy(),role.getUpdateDate(),role.getRemarks(),role.getId());
		return rows > 0;
	}

	@Override
	public Long saveRole(Role role) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" INSERT INTO pm_sys_role(name,create_by,create_date,update_by,update_date,remarks) ");
		buffer.append(" VALUES(?,?,?,?,?,?) ");
		
		KeyHolder holder = new GeneratedKeyHolder();
		this.template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(buffer.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setObject(1, role.getName());
				ps.setObject(2, role.getCreateBy());
				ps.setObject(3, role.getCreateDate());
				ps.setObject(4, role.getUpdateBy());
				ps.setObject(5, role.getUpdateDate());
				ps.setObject(6, role.getRemarks());
				
				return ps;
			}
		}, holder);
		return holder.getKey().longValue();
	}

	@Override
	public boolean deleteRole(Long roleId) {
		String sql = " UPDATE pm_sys_role SET del_flag=1 WHERE id=? AND 1=1 ";
		int rows = this.template.update(sql,roleId);
		return rows > 0;
	}

	@Override
	public Role queryRoleById(Long roleId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id,name,create_by,create_date,update_by,update_date,remarks ");
		buffer.append(" FROM pm_sys_role WHERE id=? AND del_flag=0 ");
		
		return this.template.queryForObject(buffer.toString(),new Object[] {roleId}, new BeanPropertyRowMapper<Role>(Role.class));
	}

	@Override
	public boolean addRoleToMenuBatch(List<RoleToMenu> roleMenuList) {
		boolean flag = true;
		try {
			String sql = "INSERT INTO PM_SYS_ROLE_MENU(ROLE_ID,MENU_ID) VALUES(?,?) ";
			template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, roleMenuList.get(i).getRoleId());
					ps.setObject(2, roleMenuList.get(i).getMenuId());
				}
				
				@Override
				public int getBatchSize() {
					return roleMenuList.size();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean addRoleToDeptBatch(List<RoleToDept> roleDeptList) {
		boolean flag = true;
		try {
			String sql = " INSERT INTO PM_SYS_ROLE_DEPT(ROLE_ID,DEPT_ID) VALUES(?,?) ";
			template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, roleDeptList.get(i).getRoleId());
					ps.setObject(2, roleDeptList.get(i).getDeptId());
				}
				
				@Override
				public int getBatchSize() {
					return roleDeptList.size();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean addRoleToAreaBatch(List<RoleToArea> roleAreaList) {
		boolean flag = true;
		try {
			String sql = "INSERT INTO PM_SYS_ROLE_AREA(ROLE_ID,AREA_ID) VALUES(?,?) ";
			template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, roleAreaList.get(i).getRoleId());
					ps.setObject(2, roleAreaList.get(i).getAreaId());
				}
				
				@Override
				public int getBatchSize() {
					return roleAreaList.size();
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public List<RoleToMenu> queryRoleMenuListByRoleId(Long roleId) {
		String sql = " SELECT ROLE_ID,MENU_ID FROM PM_SYS_ROLE_MENU WHERE ROLE_ID=? ";
		List<RoleToMenu> list = this.template.query(sql, new BeanPropertyRowMapper<RoleToMenu>(RoleToMenu.class), roleId);
		if(list!=null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public List<RoleToDept> queryRoleDeptListByRoleId(Long roleId) {
		String sql = " SELECT ROLE_ID,DEPT_ID FROM PM_SYS_ROLE_DEPT WHERE ROLE_ID=? ";
		List<RoleToDept> list = this.template.query(sql, new BeanPropertyRowMapper<RoleToDept>(RoleToDept.class), roleId);
		if(list!=null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public List<RoleToArea> queryRoleAreaListByRoleId(Long roleId) {
		String sql = " SELECT ROLE_ID,AREA_ID FROM PM_SYS_ROLE_AREA WHERE ROLE_ID=? ";
		List<RoleToArea> list = this.template.query(sql, new BeanPropertyRowMapper<RoleToArea>(RoleToArea.class), roleId);
		if(list!=null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public boolean delRoleMenuByRoleId(Long roleId) {
		String sql = " DELETE FROM PM_SYS_ROLE_MENU WHERE ROLE_ID=?  ";
		int rows =  this.template.update(sql,roleId);
		if(rows>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean delRoleDeptByRoleId(Long roleId) {
		String sql = " DELETE FROM PM_SYS_ROLE_DEPT WHERE ROLE_ID=?  ";
		int rows =  this.template.update(sql,roleId);
		if(rows>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean delRoleAreaByRoleId(Long roleId) {
		String sql = " DELETE FROM PM_SYS_ROLE_AREA WHERE ROLE_ID=?  ";
		int rows =  this.template.update(sql,roleId);
		if(rows>0){
			return true;
		}
		return false;
	}

	@Override
	public List<Role> queryRoleByUserId(Long UserId) {
		String sql = " SELECT R.ID,R.NAME FROM PM_SYS_USER_ROLE UR,PM_SYS_ROLE R WHERE UR.ROLE_ID=R.ID AND UR.USER_ID=? ";
		List<Role> roleList = this.template.query(sql, new BeanPropertyRowMapper<Role>(Role.class), UserId);
		if(roleList!=null && roleList.size()>0) {
			return roleList;
		}else {
			return null;
		}
	}

	@Override
	public boolean addRoleToMenu(RoleToMenu roleMenu) {
		String sql = "INERT INTO PM_SYS_ROLE_MENU(ROLE_ID,MENU_ID) VALUES(?,?) ";
		int rows = this.template.update(sql, roleMenu.getRoleId(),roleMenu.getMenuId());
		return rows > 0;
	}

	@Override
	public boolean addRoleToDept(RoleToDept roleDept) {
		String sql = "INERT INTO PM_SYS_ROLE_DEPT(ROLE_ID,DEPT_ID) VALUES(?,?) ";
		int rows = this.template.update(sql, roleDept.getRoleId(),roleDept.getDeptId());
		return rows > 0;
	}

	@Override
	public boolean addRoleToArea(RoleToArea roleArea) {
		String sql = "INERT INTO PM_SYS_ROLE_AREA(ROLE_ID,AREA_ID) VALUES(?,?) ";
		int rows = this.template.update(sql, roleArea.getRoleId(),roleArea.getAreaId());
		return rows > 0;
	}
}
