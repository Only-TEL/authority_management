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
		var areaMgr = {
			'delArea' : function(areaId){
				//if(document.getElementById(areaId).style.display=="table-row"){
	   	 		if(confirm("您确定要删除此区域吗?")){  	 			 
	   		 		  $.ajax({
	   						type:'post',
	   						url:'${ctx}/sysmgr/delArea.action', 
	   						dataType:'json', // 有几种格式 xml html json text 等常用
	   						data: {"areaId":areaId},
	   						// 与服务器交互成功调用的回调函数
	   						success:function(data){
	   							// data就是out.print输出的内容
	   							alert(data.msg);
	   							window.location.href="${ctx }/sysmgr/areaList.action";			
	   						}
	   					});
   					}
	   			/* }else{
	   				alert("此菜单下面还有子区域,请确定所有的子区域被删除以后再进行此操作");
	   			} */
			}	
		};
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:void(0);">区域列表</a></li>
        <li><a href="${ctx }/sysmgr/areaEdit.action?editFlag=1&parentId=-1">区域添加</a></li>
    </ul>
    <table id="treeTable" class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <th>区域名称</th>
                <th>区域编码</th>
                <th style="text-align:center;">排序</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody id="treeTableList">
	        <c:forEach items="${areaList }" var="area">
	        	<tr id="${area.id }" pId="${area.parentId }">
	                <td><a href="${ctx }/sysmgr/areaEdit.action?editFlag=2&areaId=${area.id }">${area.name }</a></td>
	                <td>${area.code }</td>
	                <td style="text-align:center;">${area.sort}</td>
	                <td nowrap="nowrap">
	                    <a href="${ctx }/sysmgr/areaEdit.action?editFlag=2&areaId=${area.id }">修改</a>
	                    <a href="javascript:areaMgr.delArea(${area.id})" >删除</a>
	                    <a href="${ctx }/sysmgr/areaEdit.action?editFlag=1&parentId=${area.id }">添加下级区域</a>
	                </td>
	            </tr>
	        </c:forEach> 
        </tbody>
    </table>
</body>

</html>