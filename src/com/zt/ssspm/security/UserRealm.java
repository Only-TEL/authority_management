package com.zt.ssspm.security;

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
import org.apache.shiro.util.ByteSource;
import com.zt.ssspm.sysmanage.dto.Principle;
import com.zt.ssspm.sysmanage.entity.Menu;
import com.zt.ssspm.sysmanage.entity.User;
import com.zt.ssspm.sysmanage.service.IMenuService;
import com.zt.ssspm.sysmanage.service.IUserService;
import com.zt.ssspm.util.EncryptUtil;
import com.zt.ssspm.util.StringUtils;

/**
 * 用户验证授权类
 * @ClassName : com.zt.ssspm.security.UserRealm
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月25日
 */
public class UserRealm extends AuthorizingRealm{

	private IUserService userService;
	private IMenuService menuService; 
	
	// 认证方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String loginName = (String) token.getPrincipal();
		// 从数据库查询user对象
		User user = userService.loginUser(loginName);
		// 构建一个Principle对象
		Principle principle = new Principle(user.getId(), user.getLoginName(), user.getName());
		
		if(loginName.equals(user.getLoginName())) {
			// 比较加密的凭证
			// 得到salt
			byte[] salt = EncryptUtil.decodeHex(user.getPassword().substring(0, 16));
			// SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principle, user.getPassword(), this.getName());
			// 在配置文件中告诉shiro我的加密算法和迭代次数
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principle,// 传递到授权方法的对象
											user.getPassword().substring(16),// salt+password一起加密的密码
											ByteSource.Util.bytes(salt),// salt
											this.getName());// realm的name值字符串UserRealm也可以
			return info;
		}
		return null;
	}
	
	// 授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principles) {
		// 获取主体对象,认证方法传递过来的对象
		Principle principle = (Principle) principles.getPrimaryPrincipal();
		// 查询出所有的菜单对象
		List<Menu> menuList = menuService.queryMenuListByUserId(principle.getUserId());
		List<String> permissions = new ArrayList<>();
		if(menuList!=null && menuList.size()>0) {
			for (Menu menu : menuList) {
				if(StringUtils.isNotNull(menu.getPermission())) {
					permissions.add(menu.getPermission());
				}
			}
		}
		// 将得到的权限信息放入simpleAuthorizationInfo对象保存
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);
		
		return info;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

}
