package com.zt.ssspm.sysmanage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.sysmanage.entity.Menu;
import com.zt.ssspm.sysmanage.service.IMenuService;
import com.zt.ssspm.sysmanage.service.IUserService;

/**
 * 
 * @ClassName : com.zt.ssspm.sysmanage.action.LoginAction
 * @Description : 登陆的Action
 * @author : HeadMaster
 * @date : 2018年7月21日
 */
public class LoginAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */
	private static final long serialVersionUID = 1L;
	
	// 页面参数
	private String username;
	private String password;
	private String msg;
	private List<Menu> firstMenuList;
	// Spring注入服务对象
	private IUserService userService;
	private IMenuService menuService;
	
	public String loginPage() {
		return SUCCESS;
	}
	public String main() {
		// 查询一级菜单
		this.firstMenuList = menuService.queryChildrenMenu(1L);
		return SUCCESS;
	}
	/*public String login(){
		if(StringUtils.isNotNull(username) && StringUtils.isNotNull(password)){
			User user = userService.login(username, password);
			if(user != null) {
				// 1、构建身份信息对象
				Principle principle = new Principle();
				principle.setId(user.getId());principle.setName(user.getName());principle.setLoginName(user.getLoginName());
				principle.setMenuList(menuService.queryMenuListByUserId(user.getId()));
				// 2、将对象存入session域中
				ActionContext.getContext().getSession().put("principle", principle);
				this.msg = "登陆成功";
				return SUCCESS;
			}else {
				this.msg = "用户名或密码输入不正确";
				return ERROR;
			}		
		}else {
			this.msg = "清输入正确的用户名和密码";
			return ERROR;
		}
	}*/
	//登陆验证
	public String login() throws Exception{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		this.msg = (String)request.getAttribute("msg");		
		return "loginPage";
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public IMenuService getMenuService() {
		return menuService;
	}
	public List<Menu> getFirstMenuList() {
		return firstMenuList;
	}
	public void setFirstMenuList(List<Menu> firstMenuList) {
		this.firstMenuList = firstMenuList;
	}
	
}
