<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<script type="text/javascript">
	var setting = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick: onClick
		}
	};
	// 点击树时返回值给页面
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("menuTree"),
			nodes = zTree.getSelectedNodes();
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
		var menuId = $("#id").val();
		// 从后台请求数据显示
		$.get('${ctx}/sysmgr/getMenuTree.action?id='+menuId,function(data){
			// 初始化后台数据
			var jsonObj = $.parseJSON(data.jsonObj);
			var tree = $.fn.zTree.init($("#menuTree"), setting, jsonObj);
			// 设置一个默认的展开级别
			var nodes = tree.getNodesByParam('level',2);
			for(var i=0,length=nodes.length;i<length;i++){
				tree.expandNode(nodes[i],true,false,false);
			}
			// 如果进入了修改页面，我们需要定位原有的父级菜单
			var selectNodeId  = $("#parentId").val();
			console.info("selectNodeId=="+selectNodeId);
			if(selectNodeId != null){
				// 选中
				// $("#menuTree_"+selectNodeId+"_a").addClass("curSelectedNode");   // 错误，顺序存在问题
				tree.selectNode(tree.getNodeByParam("id",selectNodeId,null));
			}
		});
	});
	// 表单提交事件
    $(function() {
        $("#name").focus();
        $("#menuForm").validate({
            submitHandler: function(form) {
                loading('正在提交，请稍等...');
                var editFlag = ${editFlag};
                var json = {};
                var dataForm = $("#menuForm").serializeArray();
                $.each(dataForm,function(index,item){
                	json[item.name] = item.value;
                });
                var jsonObj = JSON.stringify(json);
                //console.log(jsonObj);
              	$.ajax({
                	'type' : 'post',
                	'url' : '${ctx}/sysmgr/saveMenu.action',
                	'dataType' : 'text',
                	'data' : {"jsonObj":jsonObj,"editFlag":editFlag},
                	'success': function(data){
                		// 后台返回则关掉提示
                		top.$.jBox.closeTip();
                		alert($.parseJSON(data).msg);
						//history.go(-1);// 修改后不会立即体现，需要刷新页面
						window.location.href="${ctx }/sysmgr/menuList.action";
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
        <li><a href="${ctx }/sysmgr/menuList.action">菜单列表</a></li>
        <li class="active"><a href="javascript:void(0);">菜单<c:choose><c:when test="${editFlag == 1 }">添加</c:when><c:otherwise>修改</c:otherwise></c:choose></a></li>
    </ul>
    <br/>
    <form id="menuForm" class="form-horizontal" method="post">
        <input id="id" name="id" type="hidden" value="${not empty menu.id? menu.id:'' }" />
      <!--  <script type="text/javascript">top.$.jBox.closeTip();</script> -->
        <div class="control-group">
            <label class="control-label">上级菜单:</label>
            <div class="controls">
                <!-- <div class="input-append">
	                    <input id="menuId" name="id" class="required" type="hidden" value="" />
	                    <input id="menuName" name="parentName" readonly="readonly" type="text" value="" data-msg-required="" class="required" style="" /><a id="menuButton" href="javascript:" class="btn  " style="">&nbsp;<i class="icon-search"></i>&nbsp;</a>&nbsp;&nbsp;
                	</div>
                 -->
			<%-- <sys:treeSelect 
					url="/sysmgr/getMenuTree.action" id="parent" value="${menu.parentId }" 
					labelName="parentName" labelValue="${menu.parentName}" title="菜单" name="parentId" 
					extId="${not empty menu.id ? menu.id : 0}" cssClass="required" >
			</sys:treeSelect> --%>
               	<div class="input-append">
					<input id="parentId" name="parentId" class="required" type="hidden" value="${menu.parentId}"/>
					<input id="parentName" name="parenName" readonly="readonly" type="text" value="${menu.parentName}" class="required" />
					<a id="menuButton" href='<c:choose>
			        		<c:when test="${parentId != null && editFlag == 1}">javascript:void(0);</c:when>
			        		<c:otherwise>javascript:showMenu();</c:otherwise>
					</c:choose>' class="btn" >&nbsp;<i class="icon-search" ></i>&nbsp;</a>&nbsp;&nbsp;
				</div>
            </div>
        </div>
		<div id="menuContent" class="menuContent" style="display:none; position: absolute;background: #f0f6e4;">
			<ul id="menuTree" class="ztree" style="margin-top:0; width:160px;"></ul>
		</div>
        <div class="control-group">
            <label class="control-label">名称:</label>
            <div class="controls">
                <input id="name" name="name" class="required input-xlarge" type="text" value="${not empty menu.name? menu.name:'' }" maxlength="50" />
                <span class="help-inline"><span style="color:red">*</span> </span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">链接:</label>
            <div class="controls">
                <input id="href" name="href" class="input-xxlarge" type="text" value="${not empty menu.href? menu.href:'' }" maxlength="2000" />
                <span class="help-inline">点击菜单跳转的页面</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">目标:</label>
            <div class="controls">
                <input id="target" name="target" class="input-small" type="text" value="${not empty menu.target? menu.target:'' }" maxlength="10" />
                <span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">图标:</label>
            <div class="controls">
                <!-- 
	                <i id="iconIcon" class="icon- hide"></i>&nbsp;
	                <label id="iconIconLabel">无</label>&nbsp;
	                <input id="icon" name="icon" type="hidden" value="" /><a id="iconButton" href="javascript:" class="btn">选择</a>&nbsp;&nbsp;
             	-->
             	<sys:iconselect name="icon" value="${not empty menu.icon? menu.icon:'' }" id="icon"></sys:iconselect>
             </div>
        </div>
        <div class="control-group">
            <label class="control-label">排序:</label>
            <div class="controls">
                <input id="sort" name="sort" class="required digits input-small" type="text" value="${not empty menu.sort? menu.sort:'' }" maxlength="50" />
                <span class="help-inline">
                <span class="help-inline"><span style="color:red">*</span> </span>排列顺序，升序。</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">可见:</label>
            <div class="controls">
                <span>
	                <input id="isShow1" name="isShow" class="required" type="radio" value="1" <c:if test="${menu.isShow==1}">checked</c:if > />
	                <label for="isShow1">显示</label>
                </span>
                <span>
	                <input id="isShow2" name="isShow" class="required" type="radio" value="0" <c:if test="${menu.isShow==0}">checked</c:if > />
	                <label for="isShow2">隐藏</label>
                </span>
                <span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">权限标识:</label>
            <div class="controls">
                <input id="permission" name="permission" class="input-xxlarge" type="text" value="${not empty menu.permission? menu.permission:'' }" maxlength="100" />
                <span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">备注:</label>
            <div class="controls">
                <textarea id="remarks" name="remarks" maxlength="200" class="input-xxlarge" rows="3">${not empty menu.remarks? menu.remarks:'' }</textarea>
            </div>
        </div>
        <div class="form-actions">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
            <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
        </div>
    </form>
</body>
</html>