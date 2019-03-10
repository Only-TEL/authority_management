<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户列表</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:void(0);">用户列表</a></li>
        <li><a href="${ctx }/sysmgr/userEdit.action?editFlag=1">用户添加</a></li>
    </ul>
    <form id="dictSearchForm" class="breadcrumb form-search" method="post" action="${ctx}/sysmgr/userList.action">
    	<input id="pageNo" name="pageNo" type="hidden" value="1"/>
    	<!-- <input id="pageSize" name="pageSize" type="hidden" value="5"/> -->
    	<div class="controls">
			<label class="control-label">部门名称:</label>
			<div class="input-append">
				<input id="deptId" name="deptId" class="required" type="hidden" value=""/>
				<input id="deptName" name="deptName" readonly="readonly" type="text" value="" />
				<a id="deptButton" href="javascript:showUserDept();" class="btn" >
					&nbsp;<i class="icon-search" ></i>&nbsp;</a>&nbsp;&nbsp;
			</div>
			&nbsp;&nbsp;<label>用户名称</label>
       		<input id="username" name="name" class="input-medium" type="text" value="" maxlength="50"/>&nbsp;
        	<input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick = "pageSearch(1);"/>
		</div>
		<!-- 部门树容器 -->
        <div id="userDeptContent"  style="display:none; position: absolute; background: #f0f6e4;">
			<ul id="userDeptTree" class="ztree" style="margin-top:0; width:260px;"></ul>
		</div>
	 </form>
	<table id="userListTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
		        <th>用户名称</th>
		        <th>登陆名称</th>
		        <th>所属部门</th>
		        <th>所属角色</th>
		        <th>手机</th>
		        <th>邮箱</th>
		        <th>操作</th>
		    </tr>
		</thead>
		<tbody>
			 
		</tbody>
	</table>
	<div class="pagination" id = "userPageInfo">
		${pageStr}
	</div>
<script type="text/javascript">
	var setting = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentId",
				rootPId: 0
			}
		},
		callback: {
 			onClick: onClick 
		}	
	};
	function onClick(e,treeId,treeNode){
		//树上元素的点击事件我们需要，将点击的元素赋值给我们指定的元素  然后点击完隐藏树结构
		var zTree = $.fn.zTree.getZTreeObj("userDeptTree");
		var node = zTree.getSelectedNodes();
		var nodeName = node[0].name;
		var nodeId = node[0].id;
		$("#deptId").attr("value", nodeId);
		$("#deptName").attr("value", nodeName);
		hideMenu();
	}
	function hideMenu(){
		$("#userDeptContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(){
		if (!(event.target.id == "userDeptBtn" || event.target.id == "userDeptContent" || $(event.target).parents("#userDeptContent").length>0)) {
			hideMenu();
		}
	}
	// 触发显示树形菜单的事件
	function showUserDept(){
		var deptNameObj = $("#deptName");
		var deptNameOffset = $("#deptName").offset();
		$("#userDeptContent").css({left:deptNameOffset.left + "px", top:deptNameOffset.top + deptNameObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	$(document).ready(function(){
		// 展示部门树
		$.get("${ctx}/sysmgr/deptTreeData.action",function(zNodes){
			// 初始化树结构
			var jsonObj = $.parseJSON(zNodes.jsonObj);	        
 			var tree = $.fn.zTree.init($("#userDeptTree"), setting, jsonObj);
			tree.expandAll(true);
		});
		
	});
	function pageSearch(pageNo,pageSize){
		userMgr.getUserListPage(pageNo,pageSize);
	}
	var userMgr = {
		'getUserListPage' : function(pageNo,pageSize){
			// 分页查询
			var deptId = $("#deptId").val();
			var userName = $("#username").val();
			var pageSize = pageSize;
			$.ajax({
				'type' : 'post',
				'url' : '${ctx}/sysmgr/getUserListPage.action',
				'dataType' : 'json',
				'data' : {"deptId":deptId,"userName":userName,"currentPage":pageNo},
				'success' : function(data){
					top.$.jBox.closeTip();
					var jsonObj = $.parseJSON(data.jsonObj);
					var userList = jsonObj.userDtoList;
					//console.log(jsonObj);
					if(userList!=null && userList.length>0){				
						var htmlTable = "";
						for(var i=0;i<userList.length;i++){
							htmlTable = htmlTable+"<tr><td>";							
							htmlTable = htmlTable+userList[i].name;
							htmlTable = htmlTable+"</td><td>";	
							htmlTable = htmlTable+userList[i].loginName;
							htmlTable = htmlTable+"</td><td>";	
							htmlTable = htmlTable+userList[i].deptName;
							htmlTable = htmlTable+"</td><td>";
							//htmlTable = htmlTable+userList[i].roleName; 返回的userDto带有role的集合
							var list = userList[i].roleList;
							$.each(list,function(index,item){
								htmlTable = htmlTable+item.name+"&nbsp;";
							});
							
							htmlTable = htmlTable+"</td><td>";	
							htmlTable = htmlTable+userList[i].phone;
							htmlTable = htmlTable+"</td><td>";	
							htmlTable = htmlTable+userList[i].email;
							htmlTable = htmlTable+"</td><td>";	
 			    			var editButton ="<a href='${ctx}/sysmgr/userEdit.action?editFlag=2&&userId="+userList[i].id+"'>修改</a>";
							var delButton = "<a href='javascript:userMgr.delUser("+userList[i].id+")'>删除</a>";
							htmlTable = htmlTable+editButton+"&nbsp;&nbsp;&nbsp;&nbsp;"+delButton;
							htmlTable = htmlTable+"</td></tr>";					
						}
						$('#userListTable').find('tbody').html(htmlTable);	
						//取出分页条代码
						var pageStr = jsonObj.pageStr;
						$('#userPageInfo').html(pageStr);
					} else{
						$('#userListTable').find('tbody').html("<tr><td colspan='7' align='right'>没有查询到数据</td><tr>");
						$('#userPageInfo').html("");		
	 				}
				}
				
			});
		},
    	'delUser' : function(userId){
   			if(confirm("您确定要删除此角色吗?")){
   				$.ajax({
   	    			'type' : 'post',
   	   				'url' : '${ctx}/sysmgr/delUser.action',
   	               	'dataType' : 'text',
   	               	'data' : {"userId":userId},
   	               	'success': function(data){
   	               		// 后台返回则关掉提示
   	               		top.$.jBox.closeTip();
   	               		alert($.parseJSON(data).msg);
   	               		window.location.href = "${ctx}/sysmgr/userList.action";
   	               	}
   	    		});
   			}
    	}
    };

</script>
</body>
</html>