package com.zt.ssspm.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.sysmanage.dto.TreeDto;
import com.zt.ssspm.sysmanage.entity.Dept;
import com.zt.ssspm.sysmanage.service.IDeptService;
import com.zt.ssspm.util.TreeUtils;
import com.zt.ssspm.util.UserUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 区域管理的action类
 * @ClassName : com.zt.ssspm.sysmanage.action.DeptAction
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月27日
 */
public class DeptAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -2910929060272309033L;

	private IDeptService deptService;

	// 返回页面的参数
	private List<Dept> deptList;
	private String msg;
	private Dept dept;
	private String jsonObj;
	// 接收页面的参数
	private String obj;
	private Integer editFlag;
	private Long deptId;
	private Long parentId;
	
	public void setDeptService(IDeptService deptService) {
		this.deptService = deptService;
	}
	// 删除部门操作
	public String delDept() {
		try {
			boolean operating = false;
			if(!deptService.queryHasChild(deptId)) {
				operating = deptService.deleteDept(deptId);
				if(operating) 
					this.msg = "删除部门成功";
				else
					this.msg = "删除部门失败";
			}else {
				this.msg = "此菜单下面还有子菜单,请确定所有的子菜单被删除以后再进行此操作";
			}
		} catch (Exception e) {
			this.msg = "发生未知错误，请查看日志";
		}
		return SUCCESS;
	}
	public String deptEdit() {
		if(editFlag == 2) {
			// 根据id查询详细信息返回
			dept = deptService.queryDeptById(deptId);
		}else {
			// 直接添加部门页面或者添加下一级部门
			if(parentId == -1) {
				// 直接进入添加页面,清除存在的parentId
				parentId = null;
			}
			if(dept != null) {
				// 清空放到页面上的对象
				dept = null;
			}
			if(parentId != null) {
				// 根据parentId查询信息，将id，name赋值给dept并返回
				Dept parentDept = deptService.queryDeptById(parentId);
				// 重新初始化一下
				dept = new Dept();
				dept.setParentId(parentDept.getId());
				dept.setParentName(parentDept.getName());
			}
		}
		return SUCCESS;
	}
	// 查询所有的部门信息
	public String deptList() {
		// List<Dept> queryList = deptService.queryAllDept();
		List<Dept> queryList = deptService.queryDeptByUserId(UserUtils.getCurrentUserId());
		//因为前台组件需要按顺序显示，才能达到要求，所以必须对menuList进行排序
		List<Dept> realList = new ArrayList<>();
		TreeUtils.sortTreeDtoList(realList, queryList, 0L);
		this.deptList = realList;
		return SUCCESS;
	}
	// 保存对象的信息
	public String saveDept() {
		boolean operating = false;
		// 转化页面传递的字符串
		Dept dept = (Dept) JSONObject.toBean(JSONObject.fromObject(obj),Dept.class);
		if(editFlag == 1) {
			// 添加
			operating = deptService.saveDept(dept);
			if(operating) 
				this.msg = "添加部门成功";
			else
				this.msg = "添加部门失败";
		}else {
			// 更新
			Dept realDept = deptService.queryDeptById(dept.getId());
			dept.setCreateBy(realDept.getCreateBy());
			dept.setCreateDate(realDept.getCreateDate());
			operating = deptService.updateDept(dept);
			if(operating) 
				this.msg = "更新部门成功";
			else
				this.msg = "更新部门失败";
		}
		return SUCCESS;
	}
	// 查询树形部门菜单
	public String getDeptTree() {
		// 1、构建TreeList对象,查询与当前登陆对象有权限访问的部门
		// List<Dept> deptList = this.deptService.queryAllDept();
		List<Dept> deptList = this.deptService.queryDeptByUserId(UserUtils.getCurrentUserId());
		List<TreeDto> treeList = new ArrayList<>();
		for(Dept dept:deptList) {
			TreeDto tree = new TreeDto();
			tree.setId(dept.getId());tree.setName(dept.getName());tree.setParentId(dept.getParentId());
			treeList.add(tree);
		}
		// 2、查询出所有的子节点(修改节点信息时才进行查询)采用key(id) --- value(dto)存储
		if(this.deptId != null) {
			Map<Long,TreeDto> treeMap = new HashMap<>();
			if(treeList!=null && treeList.size()>0) {
				TreeUtils.getAllChildrenMap(treeMap, treeList, deptId);
			}
			// 3、从TreList中移除子节点/传入的节点(不能使用for)
			Iterator<TreeDto> it = treeList.iterator();
			while(it.hasNext()) {
				TreeDto t = it.next();
				// 判断是否存在与map集合中
				if(treeMap.get(t.getId()) != null) {
					// 存在
					it.remove();
				}
				// 移除本身
				if(t.getId().equals(deptId)) {
					it.remove();
				}
			}
		}
		// 4、将TreeList发送到前台页面
		this.jsonObj = JSONArray.fromObject(treeList).toString();
		return SUCCESS;
	}
	public List<Dept> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public Integer getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(Integer editFlag) {
		this.editFlag = editFlag;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getJsonObj() {
		return jsonObj;
	}
	public void setJsonObj(String jsonObj) {
		this.jsonObj = jsonObj;
	}
	
}
