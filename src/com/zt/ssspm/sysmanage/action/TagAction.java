package com.zt.ssspm.sysmanage.action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 自定义标签请求的action类
 * @ClassName : com.zt.ssspm.sysmanage.action.TagAction
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月26日
 */
public class TagAction extends ActionSupport{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -9071972900084569735L;

	// 获取标签中的参数
	private String url;
	private String checked;
	private String extId;
	private String selectIds;
	private String value;
	
	public String treeSelect() {
		return SUCCESS;
	}
	
	public String iconSelect() {
		return SUCCESS;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getExtId() {
		return extId;
	}

	public void setExtId(String extId) {
		this.extId = extId;
	}

	public String getSelectIds() {
		return selectIds;
	}

	public void setSelectIds(String selectIds) {
		this.selectIds = selectIds;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
