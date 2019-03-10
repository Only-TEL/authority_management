package com.zt.ssspm.test.studyshiro;

import java.util.ArrayList;
import java.util.List;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.zt.ssspm.sysmanage.entity.User;
/**
 * @ClassName : com.zt.ssspm.test.studyshiro.UserRealm
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月25日
 */
public class UserRealm extends AuthorizingRealm{

	@Override
	// 认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 从token中取出身份信息
		String userName = (String) token.getPrincipal();
		// 模拟数据库的user信息
		User user = new User();
		user.setLoginName("admin"); 
		user.setPassword("123456");
		if(userName.equals(user.getLoginName())) {
			// 身份认证通过，开始凭证认证											身份信息		正确的凭证信息			当前类的类名
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, user.getPassword(), this.getName());
			return info;
		}
		return null;
	}
	
	@Override
	// 授权方法,只需将查询到的权限信息返回即可
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principle) {
		String userName = (String) principle.getPrimaryPrincipal();
		System.out.println(userName);
		// 模拟一些权限信息
		List<String> permissions = new ArrayList<String>();
		permissions.add("user:add");
		permissions.add("user:delete");
		permissions.add("user:update");
		// 将得到的权限信息放入simpleAuthorizationInfo对象保存
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (String permission : permissions) {
			info.addStringPermission(permission);
		}
		return info;
	}
}
