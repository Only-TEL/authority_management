<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>字典管理</title>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
    <meta name="decorator" content="default" />
    <script type="text/javascript">
    	$(document).ready(function(){
    		dictMgr.queryDictList(1,10);
    	});
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
    <form id="dictSearchForm" class="breadcrumb form-search" method="post">
        <!-- <input id="pageNo" name="pageNo" type="hidden" value="1" />
        <input id="pageSize" name="pageSize" type="hidden" value="15" /> -->
        <label>类型：</label>
        <select id="type" name="type" class="input-medium">
            <option value="">所有类型</option>
            <c:forEach items="${types }" var="type">
            	<option value="${type }">${type }</option>
            </c:forEach>
          <!--   <option value="del_flag">del_flag</option>
            <option value="sex">sex</option>
            <option value="show_hide">show_hide</option>
            <option value="sys_area_type">sys_area_type</option>
            <option value="sys_data_scope">sys_data_scope</option>
            <option value="sys_log_type">sys_log_type</option>
            <option value="sys_office_grade">sys_office_grade</option>
            <option value="sys_office_type">sys_office_type</option>
            <option value="sys_user_type">sys_user_type</option>
            <option value="yes_no">yes_no</option> -->
        </select>
        &nbsp;&nbsp;
        <label>描述 ：</label>
        <input id="description" name="description" class="input-medium" type="text" value="" maxlength="50" /> &nbsp;
        <!-- 使用shiro的页面标签完成权限的判断 -->
        <shiro:hasPermission name="sys:dict:query">
	        <input id="btnSubmit" onclick="dictMgr.queryDictList(1,10);" class="btn btn-primary" type="button" value="查询" />
        </shiro:hasPermission>
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
           <!--  <tr>
                <td>0</td>
                <td><a href="#">正常</a></td>
                <td><a href="javascript:" onclick="$('#type').val('del_flag');$('#searchForm').submit();return false;">del_flag</a>
                </td>
                <td>删除标记</td>
                <td>10</td>
                <td>
                    <a href="dictAdd.html">修改</a>
                    <a href="#" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
                    <a href="dictAdd.html">添加键值</a>
                </td>
            </tr> -->
        </tbody>
    </table>
    <div class="pagination" id="dictPageInfo">
        <!-- <ul>
            <li class="disabled"><a href="javascript:">&#171; 上一页</a></li>
            <li class="active"><a href="javascript:">1</a></li>
            <li class="disabled"><a href="javascript:">下一页 &#187;</a></li>
            <li class="disabled controls"><a href="javascript:">当前 <input type="text" value="1" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(this.value,15,'');" onclick="this.select();"/> / <input type="text" value="15" onkeypress="var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(1,this.value,'');" onclick="this.select();"/> 条，共 0 条</a></li>
        </ul>
        <div style="clear:both;"></div> -->
    </div>
  <script type="text/javascript">
	function pageSearch(currentPage,pageSize){
		dictMgr.queryDictList(currentPage,pageSize);
	}
	
  	var dictMgr = {
  		'queryDictList': function(currentPage,pageSize){
  			loading('.....');
  			var description = $("#description").val();
  			var type = $("#type").val();
  			$.ajax({
  			'type' : 'post',
			'url' : '${ctx}/sysmgr/queryDictList.action', 
			'dataType' : 'json',
			'data' : {"description":description,"type":type,"currentPage":currentPage,"pageSize":pageSize,},
			'success' : function(data){
				var jsonObj = $.parseJSON(data.info);
				// 字典数据
				var list = jsonObj.dictList;
				// 分页条数据
				var pageStr = jsonObj.pageStr;
				// console.log(jsonObj);
				if(list!=null && list.length>0){
					var htmlTable = "";
					$.each(list, function(index,item){
						htmlTable = htmlTable+"<tr>";								
						htmlTable = htmlTable+"<td>";
						htmlTable = htmlTable+item.value;
						htmlTable = htmlTable+"</td>";	
						htmlTable = htmlTable+"<td>";
						htmlTable = htmlTable+item.label;
						htmlTable = htmlTable+"</td>";	
						htmlTable = htmlTable+"<td>";
						htmlTable = htmlTable+item.type;
						htmlTable = htmlTable+"</td>";	
						htmlTable = htmlTable+"<td>";
						htmlTable = htmlTable+item.description;
						htmlTable = htmlTable+"</td>";	
						htmlTable = htmlTable+"<td>";
						htmlTable = htmlTable+item.sort;
						htmlTable = htmlTable+"</td>";	
						htmlTable = htmlTable+"<td>";
			    			var editButton ="<a href='${ctx}/sysmgr/dictEdit.action?editFlag=2&&id="+item.id+"'>修改</a>";
						var delButton = "<a href='${ctx}/sysmgr/dictDelete.action?id="+item.id+"'>删除</a>";
						var addButton = "<a href='${ctx}/sysmgr/dictEdit.action?editFlag=1'>添加</a>";
						htmlTable = htmlTable+editButton+"&nbsp;&nbsp;&nbsp;&nbsp;"+delButton+"&nbsp;&nbsp;&nbsp;&nbsp;"+addButton;
						htmlTable = htmlTable+"</td>";					
						htmlTable = htmlTable+"</tr>";
					});
					$('#dictListTable').find('tbody').html(htmlTable);
					// 填充分页条代码
					$('#dictPageInfo').html(pageStr);
				}else{
					$('#dictListTable').find('tbody').html("<tr><td colspan='6' align='right'>没有查询到数据</td><tr>");
					$('#dictPageInfo').html("");
				}
				top.$.jBox.closeTip();
			},
			'error' : function(XMLHttpRequest, textStatus, errorThrown){
				top.$.jBox.closeTip();
				alert("你当前没有访问这个功能的权限，请联系管理员");
				console.log(textStatus);
				console.log(errorThrown);
			}
  			});
  		}
  	}
  </script>
</body>
</html>