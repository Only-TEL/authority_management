package com.zt.ssspm.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.dto.TreeDto;
import com.zt.ssspm.sysmanage.dto.UserDto;
import com.zt.ssspm.sysmanage.entity.Dept;
import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.User;
import com.zt.ssspm.sysmanage.service.IDeptService;
import com.zt.ssspm.sysmanage.service.IRoleService;
import com.zt.ssspm.sysmanage.service.IUserService;
import com.zt.ssspm.util.PageUtils;
import com.zt.ssspm.util.TreeUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 
 * @ClassName : com.zt.ssspm.sysmanage.action.UserAction
 * @Description : 用户操作的Action
 * @author : HeadMaster
 * @date : 2018年7月21日
 */
public class UserAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	  */
	private static final long serialVersionUID = -6463043187126077552L;
	// 接收参数
	private String oldPassword;
	private String newPassword;
	private String jsonString;
	private Long userId;
	private Integer editFlag;
	// 保存用户接收的参数
	private String userJson;
	private String roleJson;
	// 分页页面查询参数
	private Integer currentPage;
	private Long deptId;
	private String userName;
	// 角色复选框
	private List<Role> roleList;
	private UserDto userDto;// 返回页面的详细信息
	private Map<Long,Long> roleCheckMap;
	// 返回页面参数
	private String msg;
	private String info;
	private String jsonObj;
	// 服务对象
	private IUserService userService;
	private IDeptService deptService;
	private IRoleService roleService;
	
	public String toUserInfo() {
		return SUCCESS;
	}
	public String toUserMgr() {
		return SUCCESS;
	}
	public String toChangePwd() {
		return SUCCESS;
	}
	// 保存用户的action
	public String saveUser() {
		// 将字符串转换为对象
		User saveUser = (User) JSONObject.toBean(JSONObject.fromObject(userJson),User.class);
		JSONObject userRoleJson = JSONObject.fromObject(roleJson);
		boolean flag = false;
		if(editFlag == 1) {
			flag = userService.saveUser(saveUser,userRoleJson);
			if(flag) {
				this.msg = "保存用户成功";
			}else {
				this.msg = "保存用户失败";
			}
		}else {
			flag = userService.updateUser(saveUser,userRoleJson);
			if(flag) {
				this.msg = "更新用户成功"; 
			}else {
				this.msg = "更新用户失败";
			}
		}
		return SUCCESS;
	}
	// 进入用户详细信息页面
	public String userEdit() {
		// 加载角色复选框
		this.roleList = roleService.queryAllRole();
		// 加载部门菜单树
		if(editFlag == 2) {
			// 根据userId查询详细信息
			this.userDto = userService.queryUserDtoByUserId(userId);
			// 构建checkedMap
			List<Role> checkedRole = roleService.queryRoleByUserId(userId);
			this.roleCheckMap = new HashMap<>();
			for(Role role:checkedRole) {
				roleCheckMap.put(role.getId(), role.getId());
			}
		}
		return SUCCESS;
	}
	// 进入用户列表 
	public String userList() {
		return SUCCESS;
	}
	// 分页查询
	public String getUserListPage() {
		JSONObject json = new JSONObject();
		// 构建分页查询对象
		PageObject pageObj = new PageObject(currentPage);
		// 进行模糊查询
		List<UserDto> userDtoList = userService.queryUserByPage(deptId,userName,pageObj);
		// 构建分页条
		String pageStr = PageUtils.getPageStr(pageObj);
		json.put("userDtoList", userDtoList);
		json.put("pageStr", pageStr);
		this.jsonObj = json.toString();
		return SUCCESS;
	}
	// 删除用户的方法
	public String delUser() {
		boolean flag = userService.deleteUser(userId);
		if(flag) {
			this.msg = "删除用户成功"; 
		}else {
			this.msg = "删除用户失败";
		}
		return SUCCESS;
	}
	// 展示菜单树的方法
	public String deptTreeData() {
		List<Dept> queryDeptList = deptService.queryAllDept();
		List<TreeDto>treeList = new ArrayList<>();
		TreeDto treeDto;
		if(queryDeptList != null) {
			// 构建TreeDto
			for (Dept dept : queryDeptList) {
				treeDto = new TreeDto();
				treeDto.setId(dept.getId());treeDto.setName(dept.getName());treeDto.setParentId(dept.getParentId());
				treeList.add(treeDto);
			}
			// 如果进入修改页面，在树形菜单中不显示下级的所有部门
			if(userId != null) {
				// 查询当前用户的部门编号
				User nowUser = userService.queryUserById(userId);
				Long deptId = nowUser.getDeptId();
				// 构建Map
				Map<Long,TreeDto> treeMap = new HashMap<>();
				// 查询出所有的子部门
				TreeUtils.getAllChildrenMap(treeMap, treeList, deptId);
				// 迭代器从treeList中移除所有的子部门
				Iterator<TreeDto> iterator = treeList.iterator();
				while(iterator.hasNext()) {
					TreeDto tree = iterator.next();
					if(treeMap.get(tree.getId()) != null) {
						// 移除子节点
						iterator.remove();
					}
				}
			}
		}
		// 将treeList返回到前台页面
		this.jsonObj = JSONArray.fromObject(treeList).toString();
		return SUCCESS;
	}
	// 修改密码的方法
	public String saveChangePwd() {
		Long id = 1L;
		User u = userService.queryUserById(id);
		if(userService.checkPassword(oldPassword, u.getPassword())) {
			userService.modifyUserPassword(id,newPassword);
			this.msg = "ok";
		}else {
			this.msg = "请输入正确的密码";
		}
		return SUCCESS;	
	}
	
	// 查询用户的详细信息
	public String userInfo() {
		Long id = 1L;
		UserDto u = userService.queryUserInfo(id);
		this.info = JSONObject.fromObject(u).toString();
		return SUCCESS;
		
	}
	// 保存更新用户
	public String updateUser() {
		// 将前台的json字符串数据转为JSONObject对象
		JSONObject userJson = JSONObject.fromObject(jsonString);
		// 生成uer对象
		User updateUser = (User)JSONObject.toBean(userJson, User.class);
		// 查询出数据库中的对象
		User user = userService.queryUserById(updateUser.getId());
		// 设置修改的属性
		user.setEmail(updateUser.getEmail());
		user.setMobile(updateUser.getMobile());
		user.setPhone(updateUser.getPhone());
		user.setRemarks(updateUser.getRemarks());
		boolean flag = userService.updateUser(user,null);
		if(flag) {
			this.msg = "更新成功";
		}else {
			this.msg = "更新失败";
		}
		return SUCCESS;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(Integer editFlag) {
		this.editFlag = editFlag;
	}
	public String getJsonObj() {
		return jsonObj;
	}
	public void setJsonObj(String jsonObj) {
		this.jsonObj = jsonObj;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public String getUserJson() {
		return userJson;
	}
	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}
	public String getRoleJson() {
		return roleJson;
	}
	public void setRoleJson(String roleJson) {
		this.roleJson = roleJson;
	}
	public UserDto getUserDto() {
		return userDto;
	}
	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}
	public Map<Long, Long> getRoleCheckMap() {
		return roleCheckMap;
	}
	public void setRoleCheckMap(Map<Long, Long> roleCheckMap) {
		this.roleCheckMap = roleCheckMap;
	}
}
