package com.zt.ssspm.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.zt.ssspm.sysmanage.dto.Principle;

/**
 * 用户的工具类
 * @ClassName : com.zt.ssspm.util.UserUtils
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月26日
 */
public class UserUtils {

	/**
	 * 获取shiro的session中的主体对象(自己传递的是什么，获取的就是什么)
	 * @Title: getCurrentPrinciple
	 * @Description: TODO
	 * @return
	 */
	public static Principle getCurrentPrinciple() {
		// 从shiro的session里面取出主体
		Subject subject = SecurityUtils.getSubject();
		// 从主体取出身份信息
		return (Principle) subject.getPrincipal();
	}
	
	public static Long getCurrentUserId() {
		// 从shiro的session里面取出主体
		Subject subject = SecurityUtils.getSubject();
		// 从主体取出身份信息
		Principle principle = (Principle) subject.getPrincipal();
		// 获取当前登陆用户的id
		return principle.getUserId();
		
	}
}
