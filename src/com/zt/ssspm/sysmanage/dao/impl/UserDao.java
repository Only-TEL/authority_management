package com.zt.ssspm.sysmanage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.zt.ssspm.common.BaseDao;
import com.zt.ssspm.common.PageDialect;
import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dao.IUserDao;
import com.zt.ssspm.sysmanage.dto.UserDto;
import com.zt.ssspm.sysmanage.entity.User;
import com.zt.ssspm.sysmanage.entity.UserToRole;
import com.zt.ssspm.util.StringUtils;

public class UserDao extends BaseDao implements IUserDao{

	@Override
	public User login(String loginName) {
		StringBuffer buffer = new StringBuffer();	
		buffer.append(" SELECT ID,DEPT_ID,LOGIN_NAME,PASSWORD,NO,NAME,EMAIL,PHONE,MOBILE,LOGIN_IP,LOGIN_DATE,LOGIN_FLAG,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,REMARKS ");
		buffer.append(" FROM PM_SYS_USER WHERE LOGIN_NAME= ? AND DEL_FLAG=0 ");
		List<User> userList = this.template.query(buffer.toString(), new Object []{loginName}, new BeanPropertyRowMapper<>(User.class));
		User user = null;
		if(userList!=null && userList.size()>0){
			user = userList.get(0);
		}	
		return user;
	}

	@Override
	public User queryUserById(Long id) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT ID,DEPT_ID,LOGIN_NAME,PASSWORD,NO,NAME,EMAIL,PHONE,MOBILE,LOGIN_IP,LOGIN_DATE,LOGIN_FLAG,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,REMARKS ");
		buffer.append(" FROM PM_SYS_USER WHERE ID=? AND DEL_FLAG=0  ");
		User user = this.template.queryForObject(buffer.toString(), new Object[] {id}, new BeanPropertyRowMapper<>(User.class));
		if(user != null) {
			return user;
		}
		return null;
	}
	
	@Override
	public boolean modifyUserPassword(Long id, String encryptPwd) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE PM_SYS_USER SET PASSWORD=? ");
		buffer.append(" WHERE ID=? AND DEL_FLAG=0  ");
		int rows = this.template.update(buffer.toString(), new Object[] {encryptPwd,id});
		return rows > 0;
	}
	
	@Override// 首页的查询显示部门名称,角色名称
	public UserDto queryUserInfo(Long id) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT u.id,u.login_name,u.password,u.no,u.name,u.email,u.phone,u.mobile,u.login_ip,u.login_date,u.login_flag,u.create_by,u.create_date,u.update_by,u.update_date,u.remarks, ");
		buffer.append(" p.name company_name,d.name dept_name ");
		buffer.append(" FROM pm_sys_user u,pm_sys_dept d,pm_sys_dept p ");
		buffer.append(" WHERE u.dept_id=d.id ");
		buffer.append(" AND d.parent_id=p.id ");
		buffer.append(" AND u.id=? AND u.del_flag=0 ");
		
		UserDto userDto = this.template.queryForObject(buffer.toString(), new Object[] {id}, new BeanPropertyRowMapper<UserDto>(UserDto.class));
		if(userDto != null) {
			return userDto;
		}
		return null;
	}

	@Override
	public boolean updateUser(User user){
    	boolean updateFlag = false;      	
    	StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" UPDATE PM_SYS_USER SET ");
		//sqlBuffer.append(" COMPANY_ID =? , ");
		sqlBuffer.append(" DEPT_ID =? , ");
		sqlBuffer.append(" LOGIN_NAME =? , ");
		sqlBuffer.append(" NO =? , ");
		sqlBuffer.append(" NAME =? , ");
		sqlBuffer.append(" EMAIL =? , ");
		sqlBuffer.append(" PHONE =? , ");
		sqlBuffer.append(" MOBILE =? , ");
		sqlBuffer.append(" LOGIN_IP =? , ");		
		sqlBuffer.append(" LOGIN_DATE =? , ");
		sqlBuffer.append(" LOGIN_FLAG =? , ");
		//sqlBuffer.append(" CREATE_BY =? , ");
		//sqlBuffer.append(" CREATE_DATE =? , ");
		sqlBuffer.append(" UPDATE_BY =? , ");
		sqlBuffer.append(" UPDATE_DATE =? , ");
		sqlBuffer.append(" REMARKS =?  ");
		sqlBuffer.append(" WHERE ID =? AND DEL_FLAG=0 ");
		
		int rows =  this.template.update(sqlBuffer.toString(), //user.getCompanyId(),
		 		user.getDeptId(),	user.getLoginName(),user.getNo(),
				user.getName(),	user.getEmail(),user.getPhone(),user.getMobile(),		
				user.getLoginIp(),user.getLoginDate(),user.getLoginFlag(),
				//user.getCreateBy(),	user.getCreateDate(),
				user.getUpdateBy(),user.getUpdateDate(),user.getRemarks(),	
				user.getId());
		if(rows>0){
			updateFlag = true;
		}
		return updateFlag;
    }

	@Override
	public List<UserDto> queryUserByPage(Long deptId, String userName, PageObject pageObj) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" SELECT distinct(u.id),u.login_name,u.password,u.no,u.name,u.email,u.phone,u.mobile,u.login_ip,u.login_date,u.login_flag,u.create_by,u.create_date,u.update_by,u.update_date,u.remarks, ");
		sqlBuffer.append(" d.name dept_name ");
		sqlBuffer.append(" FROM pm_sys_user u,pm_sys_dept d ");
		sqlBuffer.append(" WHERE u.dept_id=d.id ");
		if(deptId != null) {
			sqlBuffer.append(" AND u.dept_id= "+deptId);
		}
		if(StringUtils.isNotNull(userName)) {
			sqlBuffer.append(" AND u.name like '"+userName+"' ");
		}
		sqlBuffer.append(" AND u.del_flag=0 ");
		// 设置pageObj的查询总数
		int rows = this.getTotalRows(sqlBuffer.toString());
		pageObj.setTotalRow(rows);
		List<UserDto> userDtoList = null;
		if(rows > 0) {
			// 分页查询
			// 获取数据库类型
			String dbType = this.getDbType();
			// 使用对应的SQL分页查询
			String sql = PageDialect.getPageDialectSQL(sqlBuffer.toString(), pageObj, dbType);
			// 查询
			userDtoList = this.template.query(sql, new BeanPropertyRowMapper<UserDto>(UserDto.class));
		}
		return userDtoList;
	}

	@Override
	public boolean deleteUserToRoleByUserId(Long userId) {
		String sql = " DELETE FROM PM_SYS_USER_ROLE WHERE USER_ID=? ";
		int rows = this.template.update(sql,userId);
		return rows > 0;
	}

	@Override
	public boolean addUserRoleBatch(List<UserToRole> userRoleList) {
		boolean flag = true;
		try {
			String sql = " INSERT INTO PM_SYS_USER_ROLE(USER_ID,ROLE_ID) VALUES(?,?) ";
			this.template.batchUpdate(sql, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setObject(1, userRoleList.get(i).getUserId());
					ps.setObject(2, userRoleList.get(i).getRoleId());
				}
				@Override
				public int getBatchSize() {
					return userRoleList.size();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public Long saveUser(User saveUser) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" INSERT INTO pm_sys_user (dept_id,login_name,password,no,name,email,phone,mobile,create_by,create_date,update_by,update_date,remarks) ");
		buffer.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		KeyHolder holder = new GeneratedKeyHolder();
		this.template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				
				PreparedStatement ps = connection.prepareStatement(buffer.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setObject(1, saveUser.getDeptId());
				ps.setObject(2, saveUser.getLoginName());
				ps.setObject(3, saveUser.getPassword());
				ps.setObject(4, saveUser.getNo());
				ps.setObject(5, saveUser.getName());
				ps.setObject(6, saveUser.getEmail());
				ps.setObject(7, saveUser.getPhone());
				ps.setObject(8, saveUser.getMobile());
				ps.setObject(9, saveUser.getCreateBy());
				ps.setObject(10, saveUser.getCreateDate());
				ps.setObject(11, saveUser.getUpdateBy());
				ps.setObject(12, saveUser.getUpdateDate());
				ps.setObject(13, saveUser.getRemarks());
				
				return ps;
			}
		}, holder);
		
		return holder.getKey().longValue();
	}

	@Override
	public UserDto queryUserDtoByUserId(Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT u.id,u.dept_id,u.login_name,u.password,u.no,u.name,u.email,u.phone,u.mobile,u.login_ip,u.login_date,u.login_flag,u.create_by,u.create_date,u.update_by,u.update_date,u.remarks, ");
		buffer.append(" d.name dept_name ");
		buffer.append(" FROM pm_sys_user u,pm_sys_dept d ");
		buffer.append(" WHERE u.dept_id=d.id ");
		buffer.append(" AND u.id=? AND u.del_flag=0 ");
		
		return this.template.queryForObject(buffer.toString(), new Object[] {userId}, new BeanPropertyRowMapper<UserDto>(UserDto.class));
	}

	@Override
	public boolean deleteUser(Long userId) {
		
		String sql = " UPDATE PM_SYS_USER SET DEL_FLAG= 1 WHERE ID = ? ";
		int rows =  this.template.update(sql,userId);
		return rows > 0;
	}

}
