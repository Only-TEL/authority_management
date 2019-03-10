package com.zt.ssspm.sysmanage.dto;

import java.util.List;
import com.zt.ssspm.sysmanage.entity.Role;
import com.zt.ssspm.sysmanage.entity.User;
/**
 * 业务类DTO首页显示个人信息
 * @ClassName : com.zt.ssspm.sysmanage.dto.UserDto
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月22日
 */
public class UserDto extends User {

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -1485003381174028606L;

	private String companyName;
	private String deptName;
	private List<Role> roleList;
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
