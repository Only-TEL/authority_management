<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>角色添加</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<script type="text/javascript">
$(function() {
	// 显示菜单资源树
	var menuSetting = {
		check:{enable:true,nocheckInherit:true},
		view:{selectedMulti:false},
		data:{simpleData:{enable:true}},
		callback:{beforeClick:function(id, node){
			menuTree.checkNode(node, !node.checked, true, true);
			return false;
			}
		}
	};
	var menuZNodes=[
		<c:forEach items="${menuList}" var="menu">
			{
				id: "${menu.id}",
				pId: "${menu.parentId}", 
				name: "${menu.name}"
			},
		</c:forEach>
    ];
	// 初始化树结构
	var menuTree = $.fn.zTree.init($("#menuTree"), menuSetting, menuZNodes);
	//进入修改页面的时候 ，选中已经拥有资源的节点	
	<c:forEach items="${roleMenuList}" var="roleMenu">
		var node = menuTree.getNodeByParam("id", ${roleMenu.menuId});
		menuTree.checkNode(node,true,false);
	</c:forEach>
	//默认展开全部节点
	menuTree.expandAll(true);
	
	// 显示部门资源树
	var deptSetting = {
		check:{enable:true,nocheckInherit:true},
		view:{selectedMulti:false},
		data:{simpleData:{enable:true}},
		callback:{beforeClick:function(id, node){
			deptTree.checkNode(node, !node.checked, true, true);
			return false;
			}
		}
	};
	var deptZNodes=[
		<c:forEach items="${deptList}" var="dept">
			{
				id: "${dept.id}",
				pId: "${dept.parentId}", 
				name: "${dept.name}"
			},
		</c:forEach>
    ];
	// 初始化树结构
	var deptTree = $.fn.zTree.init($("#deptTree"), deptSetting, deptZNodes);
	//进入修改页面的时候 ，选中已经拥有资源的节点	
	<c:forEach items="${roleDeptList}" var="deptMenu">
		var node = deptTree.getNodeByParam("id", ${deptMenu.deptId});
		deptTree.checkNode(node,true,false);
	</c:forEach>
	//默认展开全部节点
	deptTree.expandAll(true);
	
	// 显示地区资源树
	var areaSetting = {
		check:{enable:true,nocheckInherit:true},
		view:{selectedMulti:false},
		data:{simpleData:{enable:true}},
		callback:{beforeClick:function(id, node){
			areaTree.checkNode(node, !node.checked, true, true);
			return false;
			}
		}
	};
	var areaZNodes=[
		<c:forEach items="${areaList}" var="area">
			{
				id: "${area.id}",
				pId: "${area.parentId}", 
				name: "${area.name}"
			},
		</c:forEach>
    ];
	// 初始化树结构
	var areaTree = $.fn.zTree.init($("#areaTree"), areaSetting, areaZNodes);
	//进入修改页面的时候 ，选中已经拥有资源的节点	
	<c:forEach items="${roleAreaList}" var="areaMenu">
		var node = areaTree.getNodeByParam("id",${areaMenu.areaId});
		areaTree.checkNode(node,true,false);
	</c:forEach>
	//默认展开全部节点
	areaTree.expandAll(true);
	
	$("#name").focus();
	$("#roleEditForm").validate({
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			var editFlag = ${editFlag};
			// 获取角色的信息
			var json = {};
			var fromData = $("#roleEditForm").serializeArray();
			$.each(fromData, function(index,item){
				json[item.name] = item.value;
			});
			var obj = JSON.stringify(json);// 将json对象转为字符串数据发送到后端
			
			// 获取已选中的菜单信息
			var menuObj = {};
			var menuNodes = menuTree.getCheckedNodes(true);
			$.each(menuNodes, function(index, item){
		 		menuObj[item.id] = item.id;
			});
			var menuIds = JSON.stringify(menuObj);
			// 获取已选中的部门信息
			var deptObj = {};
			var deptNodes = deptTree.getCheckedNodes(true);
			$.each(deptNodes,function(index, item){
				deptObj[item.id] = item.id;
			});
			var deptIds = JSON.stringify(deptObj);
			// 获取已选中的区域信息
			var areaObj = {};
			var areaNodes = areaTree.getCheckedNodes(true);
			$.each(areaNodes,function(index, item){
				areaObj[item.id] = item.id;
			});
			var areaIds = JSON.stringify(areaObj);
			
			
			$.ajax({
				'type' : 'post',
               	'url' : '${ctx}/sysmgr/saveRole.action',
               	'dataType' : 'text',
               	'data' : {"obj":obj,"menuIds":menuIds,"deptIds":deptIds,"areaIds":areaIds,"editFlag":editFlag},
               	'success': function(data){
               		// 后台返回则关掉提示
               		top.$.jBox.closeTip();
               		alert($.parseJSON(data).msg);
					//history.go(-1);// 修改后不会立即体现，需要刷新页面
					window.location.href = "${ctx }/sysmgr/roleList.action";
				}
			});
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
});
</script>
</head>
<body>
   <ul class="nav nav-tabs">
		<li><a href="${ctx}/sysmgr/roleList.action">角色列表</a></li>
		<li class="active">
			<a href="javascript:void(0);">角色
				<c:choose>
					   <c:when test="${editFlag==1}">添加
					   </c:when>
					   <c:otherwise>修改</c:otherwise>
				</c:choose>	
			</a></li>
	</ul><br/>
    <form id="roleEditForm" class="form-horizontal" action="#" method="post">
		<input id="id" name="id" type="hidden" value="${role.id}"/>		 
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<input id="name" name="name" class="required input-xlarge" type="text" value="${role.name}" maxlength="50"/>
				<span class="help-inline"><span style="color:red">*</span> </span>
			</div>
		</div>
			 
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea id="remarks" name="remarks" maxlength="200" class="input-xxlarge" rows="3">${role.remarks}</textarea>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">角色授权:</label>
			<div class="controls">
				<div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
 				<div id="deptTree" class="ztree" style="margin-left:50px;margin-top:3px;float:left;"></div>
 				<div id="areaTree" class="ztree" style="margin-left:50px;margin-top:3px;float:left;"></div>
 			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>