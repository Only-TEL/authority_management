package com.zt.ssspm.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.sysmanage.entity.Menu;
import com.zt.ssspm.sysmanage.service.IMenuService;
import com.zt.ssspm.util.MenuUtils;
import com.zt.ssspm.util.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 菜单对应的Action
 * @ClassName : com.zt.ssspm.sysmanage.action.MenuAction
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月26日
 */
public class MenuAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = 1521660682865465112L;

	// 接收页面的参数
	private Long id;
	private Long parentId;
	private Integer editFlag;
	// 获取弹出属性菜单页面所需要的当前菜单id进行定位
	private Long extId;
	// 返回页面的参数
	private List<Menu> menuList;
	private String msg;
	private String jsonObj;
	//待修改的对象返回页面赋值
	private Menu menu;
	// Spring注入的对象
	private IMenuService menuService;
	
	// 请求获取菜单
	public String menuNavi(){
		//this.menuList = menuService.queryAllMenu();
		this.menuList = menuService.queryMenuListByUserId(UserUtils.getCurrentUserId());
		return SUCCESS;
	}
	// 删除方法
	public String delMenu() {
		boolean flag = false;
		try {
			flag = menuService.queryHasChildMenu(id);
			if(flag) {
				// 存在子节点
				this.msg = "此菜单下面还有子菜单,请确定所有的子菜单被删除以后再进行此操作";
			}else {// 不存在子节点
				flag = menuService.delMenu(id);
				if(flag)
					this.msg = "删除菜单成功";
				else 
					this.msg = "删除菜单失败";
			}
		} catch (Exception e) {
			this.msg = "操作失败";
		}
		
		return SUCCESS;
	}
	// 进入菜单列表的初始化方法
	public String menuList() {
		// 根据uerId查询对应的菜单列表
		// Long userId = UserUtils.getCurrentUserId();
		// menuList = menuService.queryMenuListByUserId(userId);
		// List<Menu> querList = menuService.queryAllMenu();
		List<Menu> querList = menuService.queryMenuListByUserId(UserUtils.getCurrentUserId());
		List<Menu> returnList = new ArrayList<>();
		MenuUtils.sortMenuList(returnList, querList, 0L);
		this.menuList = returnList;
		return SUCCESS;
	}
	// 保存或者更新后的提交方法
	public String saveMenu() {
		try {
			JSONObject obj = JSONObject.fromObject(jsonObj);
			Menu m = (Menu) JSONObject.toBean(obj,Menu.class);
			boolean operating = false;
			if(editFlag == 1) {
				//增加菜单
				operating = menuService.saveMenu(m);
				if(operating)
					this.msg = "增加菜单成功";
				else 
					this.msg = "增加菜单失败";
			}else{
				// 更新菜单
				operating = menuService.updateMenu(m);
				if(operating)
					this.msg = "修改菜单成功";
				else 
					this.msg = "修改菜单失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "操作菜单信息失败";
		}
		return SUCCESS;
	}
	// 修改或者添加菜单方法
	public String menuEdit() {
		// 根据传递过来的editFlag和parentId查询菜单的详细信息
		if(editFlag == 2) {
			// 修改时，直接根据id查询对象返回到页面
			this.menu = menuService.queryMenuById(id);
		}
		if(editFlag == 1) {
			if(menu != null) {
				menu = null;
			}
			if(parentId == -1) {
				parentId = null;
			}
			// 增加页面
			if(parentId != null) {
				Menu parentMenu = menuService.queryMenuById(parentId);
				this.menu = new Menu();
				this.menu.setParentId(parentMenu.getId());
				this.menu.setParentName(parentMenu.getName());
			}
		}
		return SUCCESS;
	}
	// 获取zTree菜单,
	public String getMenuTree() {
		List<Map<String,Object>> mapList  = new ArrayList<Map<String,Object>>();
		List<Menu> menuList = null;
		// 如果是添加下级菜单我们可以在页面上锁定上级菜单的选择，不允许更改，否则需要在这里查询
		if(id != null) {
			// 进入了修改页面    查询出当前所有不是menuI=id的子菜单    没有与user对象进行关联
			menuList = this.menuService.queryOtherMenuById(id);
			// 关联的做法，可以查询出当前菜单的所有子菜单的id，与权限关联查询出的菜单集合对比，去掉当前菜单的所有子菜单的id
		}else {
			// 进入了添加页面
			menuList = this.menuService.queryMenuListByUserId(UserUtils.getCurrentUserId());
		}
		for(Menu menu:menuList){	
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", menu.getId());
			map.put("pId", menu.getParentId());
			map.put("name", menu.getName());
			mapList.add(map);
		}
		this.jsonObj = JSONArray.fromObject(mapList).toString();
		return SUCCESS;
	}
	
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(Integer editFlag) {
		this.editFlag = editFlag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getJsonObj() {
		return jsonObj;
	}

	public void setJsonObj(String jsonObj) {
		this.jsonObj = jsonObj;
	}

	public Long getExtId() {
		return extId;
	}

	public void setExtId(Long extId) {
		this.extId = extId;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
