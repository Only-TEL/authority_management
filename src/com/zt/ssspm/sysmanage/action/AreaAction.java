package com.zt.ssspm.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.sysmanage.dto.TreeDto;
import com.zt.ssspm.sysmanage.entity.Area;
import com.zt.ssspm.sysmanage.entity.Dict;
import com.zt.ssspm.sysmanage.service.IAreaService;
import com.zt.ssspm.sysmanage.service.IDictService;
import com.zt.ssspm.util.TreeUtils;
import com.zt.ssspm.util.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 区域控制Action
 * @ClassName : com.zt.ssspm.sysmanage.action.AreaAction
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月28日
 */
public class AreaAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = 1206324486089830499L;
	// 接收页面参数
	private Long areaId;
	private Long parentId;
	private String msg;
	private Integer editFlag;
	private String obj;// 前台组装的json格式数据
	// 传递到页面的参数
	private List<Area> areaList;
	private String jsonObj;
	private Area area;
	private List<Dict> areaTypes;
	// Spring注入
	private IAreaService areaService;
	private IDictService dictService;
	
	public void setDictService(IDictService dictService) {
		this.dictService = dictService;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	// 删除区域信息
	public String delArea() {
		try {
			// 查询是否存在子节点
			if(areaService.queryHasChild(areaId)) {
				// 存在子节点
				this.msg = "此区域下面还有子区域,请确定所有的子区域被删除以后再进行此操作" ;
			}else {
				boolean flag = false;
				flag = areaService.deleteArea(areaId);
				if(flag)
	  				this.msg = "删除区域成功";
	  			else 
	  				this.msg = "删除区域失败";
			}
		}catch(Exception e) {
			e.printStackTrace();
			this.msg = "删除失败，请重试";
			
		}
		return SUCCESS;
	}
	// 保存用户
	public String saveArea() {
		boolean operating = false;
		// 将字符串类型的json数据转为对象
		JSONObject jsonArea = JSONObject.fromObject(obj);
		Area area = (Area) JSONObject.toBean(jsonArea, Area.class);
		if(editFlag == 1) {
			// 添加
			operating = areaService.saveArea(area);
			if(operating) 
				this.msg = "添加部门成功";
			else
				this.msg = "添加部门失败";
		}else {
			// 更新
			Area realArea = areaService.queryAreaById(area.getId());
			area.setCreateBy(realArea.getCreateBy());
			area.setCreateDate(realArea.getCreateDate());
			operating = areaService.updateArea(area);
			if(operating) 
				this.msg = "更新部门成功";
			else
				this.msg = "更新部门失败";
		}
		return SUCCESS;
	}
	public String areaEdit() {
		// 从字典中查询区域类型
		this.areaTypes = dictService.queryAreaType("sys_area_type");
		if(editFlag == 2) {
			// 修改
			this.area = areaService.queryAreaById(areaId);
		}else {
			// 直接添加部门页面或者添加下一级部门
			if(parentId == -1) {
				// 直接进入添加页面,清除存在的parentId
				parentId = null;
			}
			if(area != null) {
				// 清空放到页面上的对象
				area = null;
			}
			if(parentId != null) {
				// 将area的父级名称查询出来
				Area parentArea = areaService.queryAreaById(parentId);
				// 初始化
				area = new Area();
				area.setParentId(parentArea.getId());
				area.setParentName(parentArea.getName());
			}
		}
		return SUCCESS;
	}
	public String getAreaTree() {
		//List<Area> realList = areaService.queryAllAreaList();
		List<Area> realList = areaService.queryAreaListByUserId(UserUtils.getCurrentUserId());
		List<TreeDto> treeList = new ArrayList<>();
		for(Area area:realList) {
			TreeDto tree = new TreeDto();
			tree.setId(area.getId());tree.setName(area.getName());tree.setParentId(area.getParentId());
			treeList.add(tree);
		}
		if(this.areaId != null) {
			// 修改页面,不显示自己和子节点
			Map<Long,TreeDto> treeMap = new HashMap<>();
			// 找到所有的子节点Map集合
			TreeUtils.getAllChildrenMap(treeMap, treeList, areaId);
			// 循环比较
			Iterator<TreeDto> it = treeList.iterator();
			while(it.hasNext()) {
				TreeDto t = it.next();
				if(treeMap.get(t.getId()) != null) {
					it.remove();
				}
				// 移除自己
				if(t.getId().equals(areaId)) {
					it.remove();
				}
			}
		}
		// 传递数据
		this.jsonObj = JSONArray.fromObject(treeList).toString();
		return SUCCESS;
	}
	public String areaList() {
		// 查询所有的地区信息    与当前用户关联的区域
		List<Area> realList = new ArrayList<>();
//		List<Area> queryAreaList = areaService.queryAllAreaList();
		List<Area> queryAreaList = areaService.queryAreaListByUserId(UserUtils.getCurrentUserId());
		TreeUtils.sortTreeDtoList(realList, queryAreaList, 0L);
		this.areaList = realList;
		return SUCCESS;
	}
	public List<Area> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Dict> getAreaTypes() {
		return areaTypes;
	}
	public void setAreaTypes(List<Dict> areaTypes) {
		this.areaTypes = areaTypes;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}

	
}
