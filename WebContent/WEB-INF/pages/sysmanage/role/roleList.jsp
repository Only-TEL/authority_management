<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>角色列表</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:void(0);">角色列表</a></li>
        <li><a href="${ctx }/sysmgr/roleEdit.action?editFlag=1">角色添加</a></li>
    </ul>
    <form id="roleListForm" method="post" action="${ctx}/sysmgr/roleList.action">
		<table id="roleListTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>名称</th>
					<th>描述</th>
 					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${roleList}" var="role">
			<tr>										 
				<td><a href="${ctx}/sysmgr/roleEdit.action?editFlag=2&roleId=${role.id}">${role.name}</a></td>
				<td>${role.remarks}</td>					 
				<td nowrap>
					<a href="${ctx}/sysmgr/roleEdit.action?editFlag=2&roleId=${role.id}">修改</a>
					<a href="javascript:roleMgr.delRole(${role.id})">删除</a>
				</td>		 
			</tr>
			</c:forEach>
				 
			</tbody>
		</table>
	 </form>
	 <script type="text/javascript">
	 	var roleMgr = {
		    	'delRole' : function(roleId){
	    			if(confirm("您确定要删除此角色吗?")){
	    				$.ajax({
	    	    			'type' : 'post',
	    	   				'url' : '${ctx}/sysmgr/delRole.action',
	    	               	'dataType' : 'text',
	    	               	'data' : {"roleId":roleId},
	    	               	'success': function(data){
	    	               		// 后台返回则关掉提示
	    	               		top.$.jBox.closeTip();
	    	               		alert($.parseJSON(data).msg);
	    	               		window.location.href="${ctx }/sysmgr/roleList.action";
	    	               	}
	    	    		});
	    			}
		    	}
		    };
	 
	 </script>
</body>
</html>