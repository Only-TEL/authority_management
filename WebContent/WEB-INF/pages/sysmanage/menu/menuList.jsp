<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>菜单管理</title>
   	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="javascript:void(0);">菜单列表</a></li>
        <li><a href="${ctx }/sysmgr/menuEdit.action?editFlag=1&parentId=-1">菜单添加</a></li>
    </ul>
    <!-- <script type="text/javascript">top.$.jBox.closeTip();</script> -->
    <form id="menuListForm" method="post">
        <table id="treeTable" class="table table-striped table-bordered table-condensed hide">
            <thead>
                <tr>
                    <th>名称</th>
                    <th>链接</th>
                    <th style="text-align:center;">排序</th>
                    <th>可见</th>
                    <th>权限标识</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
	            <c:forEach items="${ menuList}" var="menu">
	            	<tr id="${menu.id }" pId="${menu.parentId }">
	                    <td nowrap><i class="icon-${not empty menu.icon?menu.icon:' hide'}"></i>
	                    <a href="${ctx}/sys/menu/toEdit?id=${menu.id}">${menu.name }</a></td>
	                    <td title="${menu.href}">${menu.href}</td>
	                    <td style="text-align:center;">
	                        ${menu.sort}
	                    </td>
	                    <td>
	                    	<c:choose>
	                    		<c:when test="${menu.isShow==1 }">显示</c:when>
	                    		<c:otherwise>不显示</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    <td title="${menu.permission}">${menu.permission}</td>
	                    <td nowrap>
	                        <a href="${ctx }/sysmgr/menuEdit.action?id=${menu.id }&&editFlag=2">修改</a>
	                        <a href="javascript:menuMgr.delMenu(${menu.id})">删除</a>
	                        <a href="${ctx }/sysmgr/menuEdit.action?parentId=${menu.parentId }&&editFlag=1">添加下级菜单</a>
	                    </td>
	                </tr>
	            </c:forEach>
            </tbody>
        </table>
        <!-- <div class="form-actions pagination-left">
            <input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();" />
        </div> -->
    </form>
<script type="text/javascript">
    $(function() {
        $("#treeTable").treeTable({ expandLevel: 3 }).show();
    });
    
    var menuMgr = {
    	'delMenu' : function(menuId){
    		// 首先进行判断当前节点是否存在子节点(子菜单)
    		/* if(document.getElementById(menuId).style.display=="table-row"){ */
    			if(confirm("您确定要删除此菜单吗?")){
    				$.ajax({
    	    			'type' : 'post',
    	   				'url' : '${ctx}/sysmgr/delMenu.action',
    	               	'dataType' : 'text',
    	               	'data' : {"id":menuId},
    	               	'success': function(data){
    	               		// 后台返回则关掉提示
    	               		top.$.jBox.closeTip();
    	               		alert($.parseJSON(data).msg);
    	               		window.location.href="${ctx }/sysmgr/menuList.action";
    	               	}
    	    		});
    			}
    		/* }else{
    			alert("此菜单下面还有子菜单,请确定所有的子菜单被删除以后再进行此操作");
    		} */
    	}
    };
    
    /* function updateSort() {
        loading('正在提交，请稍等...');
        $("#menuListForm").attr("action", "#");
        $("#menuListForm").submit();
    } */
</script>
</body>
</html>