package com.zt.ssspm.test.dao;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zt.ssspm.sysmanage.dao.IUserDao;
import com.zt.ssspm.sysmanage.dto.UserDto;
import com.zt.ssspm.sysmanage.entity.User;
import com.zt.ssspm.sysmanage.service.IUserService;

@SuppressWarnings("resource")
public class UserTest {
	
	@Test
	public void loginDaoTest() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserDao dao = (IUserDao) cs.getBean("userDao");
		String userName = "admin";
		User user = dao.login(userName);
		// c58c49e3e4c78df4a71309eab6a830e5f00f85b1c3c7fea586c42d9e
		System.out.println(user.getPassword());
	}

	@Test
	public void loginServiceTest() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserService service = (IUserService) cs.getBean("userService");
		String userName = "admin";
		String password = "123456";
		// c58c49e3e4c78df4a71309eab6a830e5f00f85b1c3c7fea586c42d9e
		User user = service.login(userName,password);
		System.out.println(user);
	}
	@Test
	public void queryUserByIdTest() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserService service = (IUserService) cs.getBean("userService");
		User user = service.queryUserById(1L);
		System.out.println(user);
	}
	@Test
	public void queryUserDtoByIdTest() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserService service = (IUserService) cs.getBean("userService");
		UserDto userDto = service.queryUserInfo(1L);
		System.out.println(userDto.getName());
	}
	@Test
	public void updateUserTest() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserService service = (IUserService) cs.getBean("userService");
		User user = new User();
		user.setId(1L);user.setLoginName("admin");user.setName("系统超级管理员");
		System.out.println(service.updateUser(user,null));
	}
}
