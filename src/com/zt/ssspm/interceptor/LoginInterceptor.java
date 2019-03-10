package com.zt.ssspm.interceptor;

import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
/**
 * 用户认证拦截器
 * @ClassName : com.zt.ssspm.interceptor.LoginInterceptor
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月24日
 */
public class LoginInterceptor extends AbstractInterceptor{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -265225162736364720L;

	// 日志对象
	private Log logger = LogFactory.getLog(LoginInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String returnCode = "";
		// 获取当前访问的URI
		String uri = ServletActionContext.getRequest().getRequestURI();
		logger.debug("进入用户认证拦截器，当前URI="+uri);
		
		// 判断是否含有匿名访问的路径
		if(uri.contains("toLogin") || uri.contains("login") || uri.contains("main") ) {
			// 直接通过
			logger.debug("验证通过");
			returnCode = invocation.invoke();
		}else {
			// 验证是否登陆
			HttpSession session = ServletActionContext.getRequest().getSession();
			if(session.getAttribute("principle") != null) {
				// 通过
				logger.debug("验证通过");
				returnCode = invocation.invoke();
			}else {
				// 重定向到登陆页面
				logger.debug("返回登录页面");
				returnCode = "toLoginPage";
			}
		}
		logger.debug("退出身份认证拦截器,结果returnCode="+returnCode);
		return returnCode;
	}

}
