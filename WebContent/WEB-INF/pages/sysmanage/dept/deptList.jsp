<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>部门列表</title>
   	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<script type="text/javascript">
	$(function() {
		$("#treeTable").treeTable({ expandLevel: 5 });
	});
    var deptMgr = {
    	// 删除方法
    	'delDept':function(deptId){
    		$.ajax({
    			'type':'post',
            	'url':'${ctx}/sysmgr/delDept.action',
            	'dataType':'text',
            	'data':{'deptId':deptId},
            	'success' : function(data){
            		top.$.jBox.closeTip();
            		alert($.parseJSON(data).msg);
            		// 转到查询页面
            		window.location.href="${ctx }/sysmgr/deptList.action";
            	}
    		});
    	}
    }
</script>
</head>

<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">部门列表</a></li>
        <li><a href="${ctx }/sysmgr/deptEdit.action?editFlag=1&parentId=-1">部门添加</a></li>
    </ul>
    <table id="treeTable" class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <th>部门名称</th>
                <th>部门编码</th>
                <th>部门类型</th>
                <th>备注</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="treeTableList">
			<c:forEach items="${deptList}" var="dept">
				<tr id="${dept.id}" pId="${dept.parentId}">										 
					<td><a href="${ctx}/sysmgr/deptEdit.action?editFlag=2&deptId=${dept.id}">${dept.name}</a></td>
					<td>${dept.code}</td>
					<td style="text-align:center;">${dept.sort}</td>
					<td>${dept.master}</td>
					<td>${dept.phone}</td>	
					<td nowrap>
						<a href="${ctx}/sysmgr/deptEdit.action?editFlag=2&deptId=${dept.id}">修改</a>
						<a href="javascript:deptMgr.delDept(${dept.id})">删除</a>
						<a href="${ctx}/sysmgr/deptEdit.action?editFlag=1&parentId=${dept.id}">添加下级部门</a>
					</td>
				</tr>
			</c:forEach>
        </tbody>
    </table>
</body>
</html>