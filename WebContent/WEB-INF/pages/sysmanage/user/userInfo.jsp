<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户编辑页面</title>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
	<script type="text/javascript">
		$(function() {
			$("#userInfoForm").validate({
				submitHandler: function(form){
					loading('.....');
					// 拼凑json对象数据
					var jsonForm = {};
					var formArray = $("#userInfoForm").serializeArray();
					$.each(formArray,function(index,item){
						// item ---> name="",value=""
						jsonForm[item.name] = item.value;
					});
					// json格式的字符串数据    传递到后台
					var jsonString = JSON.stringify(jsonForm);
					$.ajax({
						'type' : 'post',
						'url' : '${ctx}/sysmgr/saveUserById.action', 
						'dataType' : 'text',
						'data' : {"jsonString":jsonString},
						'success' : function(data){
							top.$.jBox.closeTip();
						}
					}); 
					/* $.ajax({
						'type' : 'post',
						'url' : '${ctx}/sysmgr/saveUserById.action', 
						'dataType' : 'text',
						'data' : $("#userInfoForm").serialize(),
						'success' : function(data){
							top.$.jBox.closeTip();
						}
					}); */
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("....");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
var userMgr = {
	'init' : function(){
		$.ajax({
			'type' : 'post',
			'url' : '${ctx}/sysmgr/getUserInfoById.action', 
			'dataType' : 'json',
			'data' : {},
			'success' : function(data){
				var info = $.parseJSON(data.info);
				var roles = info.roleList;
				var str = "";
				//console.log(info);
				$("#companyName").html(info.companyName);
				$("#officeName").html(info.deptName);
				//$("#roleName").html(info.roleName);
				$.each(roles,function(index,item){
					str += item.name+"&nbsp;&nbsp;&nbsp";
					
				});
				$("#roleName").html(str)
				$("#loginIp").html(info.loginIp);
				if(isNotEmpty(info.loginDate)){
					$("#loginDate").html(getFormatDateByLong(info.loginDate.time,null));
				}
				//表单元素赋值
				$("#name").val(info.name);
				$("#email").val(info.email);
				$("#phone").val(info.phone);
				$("#mobile").val(info.mobile);
				$("#remarks").val(info.remarks);
				$("#id").val(info.id);
			}
		});
	}
}
	</script>
</head>

<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:void(0)">个人信息</a></li>
		<li><a href="${ctx}/sysmgr/toChangePwd.action">修改密码</a></li>
	</ul><br/>
	<form id="userInfoForm" class="form-horizontal" method="post">
	<!-- <script type="text/javascript">top.$.jBox.closeTip();</script> -->

		<div class="control-group">
			<label class="control-label">所属公司:</label>
			<div class="controls">
				<label class="lbl"><span id="companyName"></span></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属部门:</label>
			<div class="controls">
				<label class="lbl"><span id="officeName"></span></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<input name="id" id="id" type="hidden"/>
				<input id="name" name="name" class="required" readonly="readonly" type="text" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<input id="email" name="email" class="email" type="text" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<input id="phone" name="phone" type="text" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<input id="mobile" name="mobile" type="text" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea id="remarks" name="remarks" maxlength="200" class="input-xlarge" rows="3"></textarea>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">用户角色:</label>
			<div class="controls">
				<label class="lbl"><span id="roleName"></span></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上次登录:</label>
			<div class="controls">
				<label class="lbl">IP: <span id="loginIp"></span>&nbsp;&nbsp;&nbsp;&nbsp;时间：<span id="loginDate"></span></label>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
		</div>
	</form>
</body>
		<script type="text/javascript">
			userMgr.init();
		</script>
</html>