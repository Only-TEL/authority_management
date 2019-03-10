package com.zt.ssspm.sysmanage.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dao.IRoleDao;
import com.zt.ssspm.sysmanage.dao.IUserDao;
import com.zt.ssspm.sysmanage.dto.UserDto;
import com.zt.ssspm.sysmanage.entity.User;
import com.zt.ssspm.sysmanage.entity.UserToRole;
import com.zt.ssspm.sysmanage.service.IUserService;
import com.zt.ssspm.util.EncryptUtil;
import com.zt.ssspm.util.UserUtils;

import net.sf.json.JSONObject;

public class UserService implements IUserService {

	// 设置默认的循环数
	private static final int DEFAULT_ITERATIONS = 1024;
	// 设置默认的salt长度
	private static final int SALT_SIZE = 8;
	
	private IUserDao userDao;
	private IRoleDao roleDao;
	@Override
	public User login(String loginName, String password) {
		// 将密码加密后与数据库的密码进行校对
		User user = userDao.login(loginName);
		if(user != null) {
			// 校验密码
			if(this.checkPassword(password, user.getPassword())) {
				return user;
			}else {
				return null;
			}
		}
		return null;
	}
	
	@Override
	public User queryUserById(Long id) {
		return userDao.queryUserById(id);
	}
	
	@Override
	public boolean modifyUserPassword(Long id,String newPassword) {
		String encryptPwd = encryptPwd(newPassword);
		return userDao.modifyUserPassword(id,encryptPwd);
	}
	
	@Override
	public boolean updateUser(User user,JSONObject roleJson) {
		boolean flag = false;
		// 更新时间设置
		Long currentTime = System.currentTimeMillis();
		user.setUpdateDate(new Timestamp(currentTime));
		user.setUpdateBy(user.getLoginName());
		Long userId = user.getId();
		if(roleJson != null) {
			// 先删除用户角色对应表中的数据
			flag = userDao.deleteUserToRoleByUserId(user.getId());
			// 批量添加对应表
			Iterator<Long> keys = roleJson.keys();
			UserToRole userToRole;
			List<UserToRole> userRoleList = new ArrayList<>();
			while(keys.hasNext()) {
				userToRole = new UserToRole();
				userToRole.setUserId(userId);
				userToRole.setRoleId(new Long(roleJson.get(keys.next()).toString()));
				userRoleList.add(userToRole);
			}
			flag = userDao.addUserRoleBatch(userRoleList);
		}
		flag = userDao.updateUser(user);
		return flag;
	}
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	/**
	 * 对密码进行加密的方法  	hex(salt)+hex(sha1(password,salt))
	 * @Title: encryptPwd
	 * @Description: TODO
	 * @param plainPwd
	 * @return
	 */
	private String encryptPwd(String plainPwd) {
		byte[] salt = EncryptUtil.generateSalt(SALT_SIZE);
		String saltEncode = EncryptUtil.encodeHex(salt);
		return saltEncode + EncryptUtil.encodeHex(EncryptUtil.sha1(plainPwd.getBytes(), salt, DEFAULT_ITERATIONS));
	}
	
	/**
	 * 校验密码是否有效的方法
	 * @Title: checkPassword
	 * @Description: TODO
	 * @param plainPwd
	 * @param encryptPwd
	 * @return
	 */
	public boolean checkPassword(String plainPwd,String encryptPwd) {
		// 1、获取已加密密码的salt
		String saltString = encryptPwd.substring(0, SALT_SIZE*2);
		// 2、逆转码salt
		byte [] salt = EncryptUtil.decodeHex(saltString);
		// 3、对plainPwd进行加密
		String newPwd = saltString + EncryptUtil.encodeHex(EncryptUtil.sha1(plainPwd.getBytes(), salt, DEFAULT_ITERATIONS));
		// 4、对比两个已加密的密码
		return newPwd.equals(encryptPwd);
	}
	
	@Override
	public UserDto queryUserInfo(Long id) {
		UserDto userDto = userDao.queryUserInfo(id);
		userDto.setRoleList(roleDao.queryRoleByUserId(id));
		return userDto;
	}

	@Override
	public User loginUser(String loginName) {
		return userDao.login(loginName);
	}

	@Override
	public List<UserDto> queryUserByPage(Long deptId, String userName, PageObject pageObj) {
		List<UserDto> queryUserDto = userDao.queryUserByPage(deptId,userName,pageObj);
		for (UserDto userDto : queryUserDto) {
			userDto.setRoleList(roleDao.queryRoleByUserId(userDto.getId()));
		}
		return queryUserDto;
	}

	@Override
	public boolean saveUser(User saveUser, JSONObject roleJson) {
		boolean flag = false;
		// 设置saveUser的一些参数
		String longinName = UserUtils.getCurrentPrinciple().getLoginName();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		saveUser.setCreateBy(longinName);
		saveUser.setCreateDate(now);
		saveUser.setUpdateBy(longinName);
		saveUser.setUpdateDate(now);
		saveUser.setPassword(encryptPwd("123"));// 初始密码123
		saveUser.setLoginName(saveUser.getName());// 登陆名称与设置名称一致
		Long userId = userDao.saveUser(saveUser);
		// 批量添加映射表数据
		Iterator<Long> keys = roleJson.keys();
		UserToRole userToRole;
		List<UserToRole> userRoleList = new ArrayList<>();
		while(keys.hasNext()) {
			userToRole = new UserToRole();
			userToRole.setUserId(userId);
			userToRole.setRoleId(new Long(roleJson.get(keys.next()).toString()));
			userRoleList.add(userToRole);
		}
		flag = userDao.addUserRoleBatch(userRoleList);
		return flag;
	}

	@Override
	public UserDto queryUserDtoByUserId(Long userId) {
		return userDao.queryUserDtoByUserId(userId);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public boolean deleteUser(Long userId) {
		boolean flag = false;
		// 首先删除用户角色对应表中的数据
		flag = userDao.deleteUserToRoleByUserId(userId);
		// 在逻辑删除user表的数据
		flag = userDao.deleteUser(userId);
		return flag;
	}

}
