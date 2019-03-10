package com.zt.ssspm.sysmanage.action;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zt.ssspm.common.PageObject;
import com.zt.ssspm.sysmanage.entity.Dict;
import com.zt.ssspm.sysmanage.service.IDictService;
import com.zt.ssspm.util.PageUtils;

import net.sf.json.JSONObject;

public class DictAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = 5744540799388667204L;

	// 接收页面的参数
	private String description;
	private String type;
	private Long id;
	private String editFlag;
	private String jsonString;
	// 分页参数
	private Integer currentPage;
	private Integer pageSize;
	// 返回页面参数
	private List<String> types;
	// 分页查询的数据和分页条的代码
	private String info;
	private String msg;
	// 使用的Service对象
	private IDictService dictService;
	
	public String toDictListPage() {
		// 查询字典类型填充页面
		this.types = dictService.queryAllType();
		return SUCCESS;
	}
	public String toDictAddPage() {
		return SUCCESS;
	}
	// 分页查询
	@RequiresPermissions("sys:dict:query")
	public String queryDictList() {
		JSONObject jsonObj = new JSONObject();
		// 查询当前页数据
		PageObject po = new PageObject(currentPage, pageSize);
		List<Dict> dictList = dictService.queryDictList(type, description, po);
		jsonObj.put("dictList", dictList);
		
		// 生成分页条
		if(dictList!=null && dictList.size()>0) {
			String pageStr = PageUtils.getPageStr(po);
			jsonObj.put("pageStr", pageStr);
		}
		// 返回前台
		this.info = jsonObj.toString();
		return SUCCESS;
	}
	// 查询字典的详细信息
	public String dictEdit() {
		if(editFlag.equals("2")) {
			Dict dict = dictService.queryDictDetail(id);
			ActionContext.getContext().put("dictDetail", dict);
		}
		return SUCCESS;
	}
	// 更新或者添加的方法
	public String saveOrUpdateDict() {
		// 定义返回标志
		boolean operationFlag = false;
		// 转换jsonString为JSONObject对象
		JSONObject jsonDict = JSONObject.fromObject(jsonString);
		Dict d = (Dict) JSONObject.toBean(jsonDict, Dict.class);
		if(editFlag.equals("2")) {
			// 修改字典
			// 查询数据库中的字典
			Dict dict = dictService.queryDictDetail(id);
			dict.setValue(d.getValue());
			dict.setLabel(d.getLabel());
			dict.setType(d.getType());
			dict.setDescription(d.getDescription());
			dict.setSort(d.getSort());
			dict.setRemarks(d.getRemarks());
			operationFlag = dictService.updateDict(dict);
		}else if(editFlag.equals("1")){
			d.setCreateBy("admin");
			d.setUpdateBy("admin");
			// 保存新字典
			operationFlag = dictService.saveDict(d);
		}
		// 选择返回值
		if(operationFlag) {
			this.msg = "操作成功";
		}else {
			this.msg = "操作失败";
		}
		return SUCCESS;
	}
	// 根据id逻辑删除
	public String dictDelete() {
		// 定义返回标志
		boolean operationFlag = false;
		operationFlag = dictService.dictDelete(id);
		// 选择返回值
		if(operationFlag) {
			this.msg = "操作成功";
		}else {
			this.msg = "操作失败";
		}
		return SUCCESS;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public void setDictService(IDictService dictService) {
		this.dictService = dictService;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	
}
