package com.zt.ssspm.interceptor;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zt.ssspm.sysmanage.dto.Principle;
import com.zt.ssspm.sysmanage.entity.Menu;
/**
 * 授权验证拦截器
 * @ClassName : com.zt.ssspm.interceptor.AuthorizationInterceptor
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月24日
 */
public class AuthorizationInterceptor extends AbstractInterceptor {

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -7679141397124826093L;
	// 日志对象
	private Log logger = LogFactory.getLog(AuthorizationInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String returnCode = "";
		// 获取当前访问的URI
		String uri = ServletActionContext.getRequest().getRequestURI();
		logger.debug("进入授权验证拦截器，当前URI="+uri);
		
		// 判断是否含有匿名访问的路径
		if(uri.contains("toLogin") || uri.contains("login") || uri.contains("main") ) {
			// 直接通过
			logger.debug("验证通过");
			returnCode = invocation.invoke();
		}else {
			// 取出用户信息对象,判断当前访问的action是否由权限访问
			Principle principle = (Principle) ServletActionContext.getRequest().getSession().getAttribute("principle");
			// 遍历principle的menuList集合
			List<Menu> list = principle.getMenuList();
			boolean flag = false;
			for (Menu menu : list) {
				String menuHref = menu.getHref();
				if(menuHref != null) {
					if(uri.indexOf(menuHref) >= 0) {
						flag = true;
					}
				}
			}
			if(flag){
				// 授权通过可访问Action
				logger.debug("授权验证已经通过，进行下一步");
				returnCode = invocation.invoke();
			}else {
				logger.debug("授权验证未通过，返回拒绝访问页面");
				returnCode="refusePage";
			}
		}
		logger.debug("退出授权验证拦截器");
		return returnCode;
	}

}
