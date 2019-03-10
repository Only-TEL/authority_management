<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门添加</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<meta name="decorator" content="default" />
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
		// 点击树时返回值给页面
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("areaTree");
			var nodes = zTree.getSelectedNodes();
			// 从nodes中获取表单需要的值
			var nodeName = nodes[0].name;
			var nodeId = nodes[0].id;
			$("#parentName").attr("value", nodeName);
			$("#parentId").attr("value", nodeId);
			// 选中节点后，隐藏选框
			hideMenu();
		}
		// 点击搜索框，显示树
		function showMenu() {
			var parentObj = $("#parentName");
			var parentOffset = parentObj.offset();
			// 定位
			$("#menuContent").css({left:parentOffset.left + "px", top:parentOffset.top + parentObj.outerHeight() + "px"}).slideDown("fast");
			$("#menuContent").css("width",parentObj.innerWidth());
			$("body").bind("mousedown", onBodyDown);
		}
		// 隐藏树
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}
		$(document).ready(function(){
			// 过滤掉自己的子菜单
			var areaId = $("#areaId").val();
			// 从后台请求数据显示
			$.get('${ctx}/sysmgr/getAreaTree.action?areaId='+areaId,function(data){
				// 初始化后台数据
				var jsonObj = $.parseJSON(data.jsonObj);
				// console.log(jsonObj);
				var tree = $.fn.zTree.init($("#areaTree"), setting, jsonObj);
				// 设置一个默认的展开级别
				var nodes = tree.getNodesByParam('level',2);
				for(var i=0,length=nodes.length;i<length;i++){
					tree.expandNode(nodes[i],true,false,false);
				}
				// 如果进入了修改页面，我们需要定位原有的父级菜单
				var selectNodeId  = $("#parentId").val();
				//console.info("selectNodeId=="+selectNodeId);
				if(selectNodeId != null){
					// 选中
					tree.selectNode(tree.getNodeByParam("id",selectNodeId,null));
				}
			});
		});		
		$(document).ready(function() {
		    $("#name").focus();
		    $("#areaForm").validate({
		        submitHandler: function(form) {
		            loading('正在提交，请稍等...');
		            var editFlag = ${editFlag};
		            // 组合obj对象
		            var json = {};
		            var formData = $("#areaForm").serializeArray();
		            $.each(formData,function(index,item){
		            	json[item.name] = item.value;
		            });
		            // 将json对象转为字符串发往后台
		            var obj = JSON.stringify(json);
		            // 保存的方法
		            $.ajax({
		            	'type':'post',
		            	'url':'${ctx}/sysmgr/saveArea.action',
		            	'dataType':'text',
		            	'data':{'obj':obj,'editFlag':editFlag},
		            	'success' : function(data){
		            		top.$.jBox.closeTip();
		            		alert($.parseJSON(data).msg);
		            		// 转到查询页面
		            		window.location.href="${ctx }/sysmgr/areaList.action";
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
        <li><a href="${ctx }/sysmgr/areaList.action">区域列表</a></li>
        <li class="active"><a href="javascript:void(0);">区域
        	<c:choose>
			   <c:when test="${editFlag==1}">添加
			   </c:when>
			   <c:otherwise>修改</c:otherwise>
			</c:choose>
		</a></li>
    </ul>
    <br/>
    <form id="areaForm" class="form-horizontal" action="#" method="post">
        <input id="areaId" name="id" type="hidden" value="${not empty area?area.id:'' }" />
        <div class="control-group">
            <label class="control-label">上级区域:</label>
            <div class="controls">
                <div class="input-append">
                   	<input id="parentId" name="parentId" class="required" type="hidden" value="${not empty area?area.parentId:'' }"/>
					<input id="parentName" name="parenName" readonly="readonly" type="text" value="${not empty area?area.parentName:'' }" />
                	
                	<!-- <a id="areaButton" href="javascript:showMenu();" class="btn" >&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp; -->
					
					<a id="areaButton" href='
					<c:choose><c:when test="${parentId!=null && editFlag==1 }">javascript:void(0);</c:when>
                	<c:otherwise>javascript:showMenu();</c:otherwise></c:choose>' 
                class="btn" >&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
                
                </div>
            </div>
        </div>
        <div id="menuContent" class="menuContent" style="display:none; position: absolute;background: #f0f6e4;z-index:9;">
			<ul id="areaTree" class="ztree" style="margin-top:0; width:160px;"></ul>
		</div>
        <div class="control-group">
            <label class="control-label">区域名称:</label>
            <div class="controls">
                <input id="name" name="name" class="required" type="text" value="${not empty area?area.name:'' }" maxlength="50" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">区域编码:</label>
            <div class="controls">
                <input id="code" name="code" type="text" value="${not empty area?area.code:'' }" maxlength="50" />
            </div>
        </div>
        <div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<input id="sort" name="sort" class="required digits input-small" type="text" value="${not empty area?area.sort:'' }" maxlength="50"/>
				<span class="help-inline"><span class="help-inline"><span style="color:red">*</span> </span>排列顺序，升序。</span>
			</div>
		</div>
        <div class="control-group">
            <label class="control-label">区域类型:</label>
            <div class="controls">
                <select id="type" name="type" class="input-medium">
                    <c:forEach items="${areaTypes }" var="dict">
	                    <c:choose>
	                    	<c:when test="${dict.value == area.type }">
	                    		<option value="${dict.value }" selected="selected" >${dict.label}</option>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<option value="${dict.value }">${dict.label}</option>
	                    	</c:otherwise>
	                    </c:choose>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <textarea id="remarks" name="remarks" maxlength="200" class="input-xlarge" rows="3">${not empty area?area.remarks:'' }</textarea>
            </div>
        </div>
        <div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
        </div>
    </form>
</body>
</html>