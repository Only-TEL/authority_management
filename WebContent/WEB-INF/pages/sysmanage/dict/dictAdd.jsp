<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>字典管理</title>
    <meta name="decorator" content="default" />
	<%@ include file="/WEB-INF/pages/include/head.jsp" %>
<script type="text/javascript">
 $(document).ready(function() {
     $("#value").focus();
     $("#dictForm").validate({
         submitHandler: function(form) {
             loading('正在提交，请稍等...');
             var jsonObj = {};
             var dataArray = $("#dictForm").serializeArray();
			 $.each(dataArray,function(index,item){
				 jsonObj[item.name] = item.value;
			 });
			 var jsonString = JSON.stringify(jsonObj);
			 var editFlag = ${editFlag};
			/*  var id = ${id==null?null:id}; */
             $.ajax({
            	'type' : 'post',
     			'url' : '${ctx}/sysmgr/saveOrUpdateDict.action', 
     			'dataType' : 'text',
     			'data' : {"jsonString":jsonString,"editFlag":editFlag},
     			'success' : function(data){
		            top.$.jBox.closeTip();
     				alert($.parseJSON(data).msg);
     				// 清空所有的表单
     				$("#dictForm").find(".control-group").find("input").val("");
     				$("#remarks").val("");
     			}
             });
         },
         errorContainer: "#messageBox",
         errorPlacement: function(error, element) {
             $("#messageBox").text("输入有误，请先更正。");
             if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
        <li><a href="${ctx }/sysmgr/toDictListPage.action">字典列表</a></li>
        <li class="active"><a href="#">字典
        	<c:choose>
        		<c:when test="${editFlag==1 }">添加</c:when>
        		<c:otherwise>修改</c:otherwise>
        	</c:choose>
        </a></li>
    </ul>
    <br/>
    <form id="dictForm" class="form-horizontal" action="#" method="post">
<!-- <script type="text/javascript">top.$.jBox.closeTip();</script> -->
        <div class="control-group">
            <label class="control-label">键值:</label>
            <div class="controls">
		        <input id="id" name="id" type="hidden" value='${not empty dictDetail.id ? dictDetail.id:"" }' />
                <input id="value" name="value" class="required" type="text" value='${not empty dictDetail.getValue()?dictDetail.getValue():"" }' maxlength="50" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">标签:</label>
            <div class="controls">
                <input id="label" name="label" class="required" type="text" value="${not empty dictDetail.label?dictDetail.label:'' }" maxlength="50" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">类型:</label>
            <div class="controls">
                <input id="type" name="type" class="required abc" type="text" value="${not empty dictDetail.type?dictDetail.type:'' }" maxlength="50" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">描述:</label>
            <div class="controls">
                <input id="description" name="description" class="required" type="text" value="${not empty dictDetail.description?dictDetail.description:'' }" maxlength="50" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">排序:</label>
            <div class="controls">
                <input id="sort" name="sort" class="required digits" type="text" value="${not empty dictDetail.sort?dictDetail.sort:'' }" maxlength="11" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <textarea id="remarks" name="remarks" maxlength="200" class="input-xlarge" rows="3">${not empty dictDetail.remarks?dictDetail.remarks:'' }</textarea>
            </div>
        </div>
        <div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
        </div>
    </form>
</body>
</html>