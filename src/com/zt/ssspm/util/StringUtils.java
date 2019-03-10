package com.zt.ssspm.util;

/**
 * 对于字符串的一些处理和判断
 * 类描述：  
 * 类名称：com.tz.jspstudy.framework.util.StringUtils       
 * 创建人：keven
 * 创建时间：2016年7月3日 下午7:29:46
 * @version   V1.0
 */
public class StringUtils {
 
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return null == str || str.length() == 0 || "".equals(str)
				|| str.matches("\\s*");
	}
	
	/**
	 * 非空判断
	 */
	public static boolean isNotNull(String str) {
		return !isNull(str);
	}
	
	
}

