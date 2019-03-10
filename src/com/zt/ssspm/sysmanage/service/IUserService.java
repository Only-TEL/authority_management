package com.zt.ssspm.sysmanage.service;

import java.util.List;

import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dto.UserDto;
import com.zt.ssspm.sysmanage.entity.User;

import net.sf.json.JSONObject;

public interface IUserService {

	/**
	 * 登陆验证方法
	 * @Title: login
	 * @Description: TODO
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User login(String loginName,String password);
	/**
	 * 根据id查询User对象
	 * @Title: queryUserById
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	public User queryUserById(Long id);
	/**
	 * 判断密码相等的方法
	 * @Title: checkPassword
	 * @Description: TODO
	 * @param plainPwd
	 * @param encryptPwd
	 * @return
	 */
	public boolean checkPassword(String plainPwd,String encryptPwd);
	/**
	 * 修改密码的服务方法
	 * @Title: modifyUserPassword
	 * @Description: TODO
	 * @param id
	 * @param newPassword
	 * @return
	 */
	public boolean modifyUserPassword(Long id, String newPassword);
	/**
	 * 查询用户的详细信息
	 * @Title: queryUserInfo
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	public UserDto queryUserInfo(Long id);
	/**
	 * 更新用户
	 * @Title: updateUser
	 * @Description: TODO
	 * @param user
	 * @param roleJson 
	 * @return
	 */
	public boolean updateUser(User user, JSONObject roleJson);
	/**
	 * 根据登陆名查询用户
	 * @Title: loginUser
	 * @Description: TODO
	 * @param loginName
	 * @return
	 */
	public User loginUser(String loginName);
	
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
	 * 保存新增用户
	 * @Title: saveUser
	 * @Description: TODO
	 * @param saveUser
	 * @param roleJson
	 * @return
	 */
	public boolean saveUser(User saveUser, JSONObject roleJson);
	
	/**
	 * 根据用户id查询用户的业务对象
	 * @Title: queryUserDtoByUserId
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	public UserDto queryUserDtoByUserId(Long userId);
	/**
	 * 根据userId删除用户信息
	 * @Title: deleteUser
	 * @Description: TODO
	 * @param userId
	 * @return
	 */
	public boolean deleteUser(Long userId);
}
