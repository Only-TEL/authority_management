package com.zt.ssspm.test.studyshiro;

import java.util.Arrays;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class UserShiroTest {

	@Test
	// 测试认证
	public void principleAndCredenticalTest(){
		// 1、构建SecurityManager对象SecurityManagerFactory
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:com/zt/ssspm/test/studyshiro/shiro.ini");
		SecurityManager manager = factory.getInstance();
		// 2、将SecurityManager对象设置到运行环境
		SecurityUtils.setSecurityManager(manager);
		//--------------------------------------------------------------
		// 1、获取当前正在执行的主体对象，它基于与当前线程或传入请求相关联的用户数据获取subject。
		Subject subject = SecurityUtils.getSubject();
		// 2、根据用户信息创建一个身份令牌，记录了身份和凭证
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123");
		// 3、进行身份认证(身份仍证+凭证认证)
		try {
			// 在项目集成时subuject.login最终是由SecurityManager通过Authticator调用realm来完成身份认证
			subject.login(token);
			
		} catch (UnknownAccountException e) {
			// 身份信息错误
			System.out.println("用户身份信息错误错误");
		}catch (IncorrectCredentialsException e) {
			// 凭证信息错误
			System.out.println("用户凭证信息错误");
		}catch ( LockedAccountException lae ) {
		    //account for that username is locked - can't login.
			System.out.println("当天登陆用户已被锁定");
		}catch (Exception e) {
			System.out.println("其他异常");
			e.printStackTrace();
		}
		// 4、判断身份认证是否通过
		boolean flag = false;
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
		// 测试登出操作
		subject.logout();
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
	}
	@Test
	// 测试Realm认证
	public void principleAndCredenticalTestByRealm() {
		// 1、构建SecurityManager对象SecurityManagerFactory
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:com/zt/ssspm/test/studyshiro/shiro-realm.ini");
		SecurityManager manager = factory.getInstance();
		// 2、将SecurityManager对象设置到运行环境
		SecurityUtils.setSecurityManager(manager);
		//--------------------------------------------------------------
		// 1、获取当前正在执行的主体对象，它基于与当前线程或传入请求相关联的用户数据获取subject。
		Subject subject = SecurityUtils.getSubject();
		// 2、根据用户信息创建一个身份令牌，记录了身份和凭证
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
		// 3、进行身份认证(身份仍证+凭证认证)
		try {
			// 在项目集成时subuject.login最终是由SecurityManager通过Authticator调用realm来完成身份认证
			subject.login(token);
			
		} catch (UnknownAccountException e) {
			// 身份信息错误
			System.out.println("用户身份信息错误错误");
		}catch (IncorrectCredentialsException e) {
			// 凭证信息错误
			System.out.println("用户凭证信息错误");
		}catch ( LockedAccountException lae ) {
		    //account for that username is locked - can't login.
			System.out.println("当天登陆用户已被锁定");
		}catch (Exception e) {
			System.out.println("其他异常");
			e.printStackTrace();
		}
		// 4、判断身份认证是否通过
		boolean flag = false;
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
		// 测试登出操作
		subject.logout();
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
	}
	@Test
	// 测试配置文件授权
	public void authorizerTest() {
		// 授权必须是在认证之后SimpleAuthorizationInfo完成
		// 1、构建SecurityManager对象SecurityManagerFactory
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:com/zt/ssspm/test/studyshiro/shiro-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		// 2、将SecurityManager对象设置到运行环境
		SecurityUtils.setSecurityManager(securityManager);
		//--------------------------------------------------------------
		// 1、获取当前正在执行的主体对象，它基于与当前线程或传入请求相关联的用户数据获取subject。
		Subject subject = SecurityUtils.getSubject();
		// 2、根据用户信息创建一个身份令牌，记录了身份和凭证
		UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
		// 3、进行身份认证(身份仍证+凭证认证)
		try {
			// 在项目集成时subuject.login最终是由SecurityManager通过Authticator调用realm来完成身份认证
			subject.login(token);
			
		} catch (UnknownAccountException e) {
			// 身份信息错误
			System.out.println("用户身份信息错误错误");
		}catch (IncorrectCredentialsException e) {
			// 凭证信息错误
			System.out.println("用户凭证信息错误");
		}catch ( LockedAccountException lae ) {
		    //account for that username is locked - can't login.
			System.out.println("当天登陆用户已被锁定");
		}catch (Exception e) {
			System.out.println("其他异常");
			e.printStackTrace();
		}
		// 4、判断身份认证是否通过
		boolean flag = false;
		flag = subject.isAuthenticated();
		System.out.println("用户认证状态"+flag);
		// 基于角色的权限判断
		System.out.println("当前用户拥有role1权限======"+subject.hasRole("role1"));
		System.out.println("用户是否拥有role1,role2角色==="+subject.hasAllRoles(Arrays.asList("role1","role2")));
		// 基于资源的权限判断
		System.out.println("用户是否拥有user:add的权限====="+subject.isPermitted("user:add"));
		System.out.println("用户是否拥有user:add,user:update的权限==="+subject.isPermittedAll("user:add","user:update"));
		System.out.println("用户是否拥有user:delete的权限====="+subject.isPermitted("user:delete"));		
	}
}
