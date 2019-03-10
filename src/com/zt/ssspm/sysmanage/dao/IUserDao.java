package com.zt.ssspm.sysmanage.dao;

import java.util.List;

import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dto.UserDto;
import com.zt.ssspm.sysmanage.entity.User;
import com.zt.ssspm.sysmanage.entity.UserToRole;

public interface IUserDao {
	
	/**
	 * 登陆验证方法
	 * @Title: login
	 * @Description: TODO
	 * @param loginName
	 * @return
	 */
	public User login(String loginName);
	/**
	 * 根据id查询User对象
	 * @Title: queryUserById
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	public User queryUserById(Long id);
	/**
	 * 修改密码
	 * @Title: modifyUserPassword
	 * @Description: TODO
	 * @param id
	 * @param encryptPwd
	 * @return
	 */
	public boolean modifyUserPassword(Long id, String encryptPwd);
	/**
	 * 查询用户的详细信息
	 * @Title: queryUserInfo
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	public UserDto queryUserInfo(Long id);
	
	/**
	 * 更新user对象
	 * @Title: updateUser
	 * @Description: TODO
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);
	
	/**
	 * 分页模糊查询用户详细信息
	 * @Title: queryUserByPage
	 * @Description: TODO
	 * @param deptId
	 * @param userName
	 * @param pageObj
	 * @return
	 */
	public List<UserDto> queryUserByPage(Long deptId, String userName, PageObject pageObj);
	
	/**
	 * 根据用户id删除用户权限对应表中的所有数据
	 * @Title: deleteUserToRoleByUserId
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	public boolean deleteUserToRoleByUserId(Long userId);
	
	/**
	 * 批量插入用户角色对应表的数据
	 * @Title: addUserRoleBatch
	 * @Description: TODO
	 * @param userRoleList
	 * @return
	 */
	public boolean addUserRoleBatch(List<UserToRole> userRoleList);
	/**
	 * 保存用户信息并返回用户的id
	 * @Title: saveUser
	 * @Description: TODO
	 * @param saveUser
	 * @return
	 */
	public Long saveUser(User saveUser);
	/**
	 * 根据用户id查询用户的业务对象
	 * @Title: queryUserDtoByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	public UserDto queryUserDtoByUserId(Long userId);
	/**
	 * 逻辑删除user表的数据
	 * @Title: deleteUser
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(Long userId);
}
