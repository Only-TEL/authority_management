package com.zt.ssspm.sysmanage.action;

import java.util.List;
import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.sysmanage.entity.Area;
import com.zt.ssspm.sysmanage.entity.Dept;
import com.zt.ssspm.sysmanage.entity.Menu;
import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.RoleToArea;
import com.zt.ssspm.sysmanage.entity.RoleToDept;
import com.zt.ssspm.sysmanage.entity.RoleToMenu;
import com.zt.ssspm.sysmanage.service.IAreaService;
import com.zt.ssspm.sysmanage.service.IDeptService;
import com.zt.ssspm.sysmanage.service.IMenuService;
import com.zt.ssspm.sysmanage.service.IRoleService;
import net.sf.json.JSONObject;

public class RoleAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -6980878857093501150L;
	// 接收页面的参数
	private Long roleId;
	private Integer editFlag;
	private String obj;
	private String menuIds;
	private String deptIds;
	private String areaIds;
	// 发送页面的参数
	private List<Role> roleList;
	private Role role;
	private String msg;
	// 展示树的参数
	private List<Menu> menuList;
	private List<Dept> deptList;
	private List<Area> areaList;
	// 当前角色对应的权限信息
	private List<RoleToMenu> roleMenuList;
	private List<RoleToDept> roleDeptList;
	private List<RoleToArea> roleAreaList;
	// 参数注入
	private IRoleService roleService;
	private IMenuService menuService;
	private IDeptService deptServcie;
	private IAreaService areaService;
	
	// 点击保存按钮
	public String saveRole() {
		// 把前台的obj字符串数据转为Role对象
		JSONObject roleJson = JSONObject.fromObject(obj);
		JSONObject menuJson = JSONObject.fromObject(menuIds);
		JSONObject deptJson = JSONObject.fromObject(deptIds);
		JSONObject areaJson = JSONObject.fromObject(areaIds);
		Role editRole = (Role) JSONObject.toBean(roleJson,Role.class);
		boolean flag = false;
		if(editFlag == 1) {
			// 新增角色
			flag = roleService.saveRole(editRole,menuJson,deptJson,areaJson);
			if(flag)
				 this.msg = "新增角色信息成功";
			 else 
				 this.msg = "新增角色信息失败";
		}else {
			// 保存已经修改的角色
			flag = roleService.updateRole(editRole,menuJson,deptJson,areaJson);
			if(flag)
				 this.msg = "修改角色信息成功";
			 else 
				 this.msg = "修改角色信息失败";
		}
		return SUCCESS;
	}
	// 进入编辑角色信息页面
	public String roleEdit() {
		// 查询所有的树信息
		menuList = menuService.queryAllMenu();
		deptList = deptServcie.queryAllDept();
		areaList = areaService.queryAllAreaList();
		
		if(editFlag == 2) {
			// 修改页面	从数据库中查询对应的权限信息
			this.role = roleService.queryRoleById(roleId);
			this.roleMenuList = roleService.queryRoleMenuListByRoleId(roleId);
			this.roleDeptList = roleService.queryRoleDeptListByRoleId(roleId);
			this.roleAreaList = roleService.queryRoleAreaListByRoleId(roleId);
		}
		return SUCCESS;
	}
	// 进入角色列表页面
	public String roleList() {
		this.roleList = roleService.queryAllRole();
		return SUCCESS;
	}
	// 删除角色信息方法
	public String delRole() {
		boolean flag = false;
		flag = roleService.deleteRole(roleId);
		 if(flag)
			 this.msg = "删除角色信息成功";
		 else 
			 this.msg = "删除角色信息失败";
		return SUCCESS;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(Integer editFlag) {
		this.editFlag = editFlag;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	public void setDeptServcie(IDeptService deptServcie) {
		this.deptServcie = deptServcie;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public List<Area> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	public String getAreaIds() {
		return areaIds;
	}
	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}
	public List<RoleToMenu> getRoleMenuList() {
		return roleMenuList;
	}
	public void setRoleMenuList(List<RoleToMenu> roleMenuList) {
		this.roleMenuList = roleMenuList;
	}
	public List<RoleToDept> getRoleDeptList() {
		return roleDeptList;
	}
	public void setRoleDeptList(List<RoleToDept> roleDeptList) {
		this.roleDeptList = roleDeptList;
	}
	public List<RoleToArea> getRoleAreaList() {
		return roleAreaList;
	}
	public void setRoleAreaList(List<RoleToArea> roleAreaList) {
		this.roleAreaList = roleAreaList;
	}
	
}