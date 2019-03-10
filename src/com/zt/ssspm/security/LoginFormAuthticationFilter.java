package com.zt.ssspm.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

public class LoginFormAuthticationFilter extends FormAuthenticationFilter{
	
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		String msg = "";
		if(e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
			msg = "用户名和密码不正确";
		}else {
			msg = "出现未知错误,请联系管理员";
		}
		request.setAttribute("msg", msg);
		return true;
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		// 读取配置文件的successUrl
		String successUrl = getSuccessUrl();
		WebUtils.issueRedirect(request, response, successUrl, null, true);
	}

}
