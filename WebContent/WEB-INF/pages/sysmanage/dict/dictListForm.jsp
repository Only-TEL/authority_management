<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>字典管理</title>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
    <meta name="decorator" content="default" />
    <script type="text/javascript">
	    function page(n, s) {
	        $("#pageNo").val(n);
	        $("#pageSize").val(s);
	        $("#searchForm").submit();
	        return false;
	    }
    </script>
</head>
<body>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#">字典列表</a></li>
        <li><a href="${ctx }/sysmgr/toDictAddPage.action?editFlag=1">字典添加</a></li>
    </ul>
    <form id="dictSearchForm" action="${ctx}/sysmgr/queryDictList.action" class="breadcrumb form-search" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="1" />
        <input id="pageSize" name="pageSize" type="hidden" value="15" />
        <label>类型：</label>
        <select id="type" name="type" class="input-medium">
            <option value="">所有类型</option>
            <c:forEach items="${types }" var="type">
            	<option value="${type }">${type }</option>
            </c:forEach>
        </select>
        &nbsp;&nbsp;
        <label>描述 ：</label>
        <input id="description" name="description" class="input-medium" type="text" value="" maxlength="50" /> &nbsp;
        <!-- 使用shiro的页面标签完成权限的判断 -->
	    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
    </form>
    <!-- <script type="text/javascript">top.$.jBox.closeTip();</script> -->
    <table id="dictListTable" class="table table-striped table-bordered table-condensed">
        <thead>
            <tr>
                <th>键值</th>
                <th>标签</th>
                <th>类型</th>
                <th>描述</th>
                <th>排序</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <div class="pagination" id="dictPageInfo">
    </div>
</body>
</html>