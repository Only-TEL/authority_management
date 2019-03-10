package com.zt.ssspm.test.springweb;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TestWeb {

	@Test
	public void fun() {
		
		// web环境下的取值
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		webApplicationContext.getBean("areaAction");
	}
}
