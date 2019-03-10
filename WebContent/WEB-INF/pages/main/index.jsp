<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglib.jsp" %>
<!DOCTYPE html>
<!-- saved from url=(0027)http://localhost:8080/index -->
<html style="overflow: hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限管理系统</title>
<%@include file="/WEB-INF/pages/include/head.jsp" %>
    <style type="text/css">
    #main {
        padding: 0;
        margin: 0;
    }

    #main .container-fluid {
        padding: 0 4px 0 6px;
    }

    #header {
        margin: 0 0 8px;
        position: static;
    }

    #header li {
        font-size: 14px;
        _font-size: 12px;
    }

    #header .brand {
        font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
        font-size: 26px;
        padding-left: 33px;
    }

    #footer {
        margin: 8px 0 0 0;
        padding: 3px 0 0 0;
        font-size: 11px;
        text-align: center;
        border-top: 2px solid #0663A2;
    }

    #footer,
    #footer a {
        color: #999;
    }

    #left {
        overflow-x: hidden;
        overflow-y: auto;
    }

    #left .collapse {
        position: static;
    }

    #userControl>li>a {
        /*color:#fff;*/
        text-shadow: none;
    }

    #userControl>li>a:hover,
    #user #userControl>li.open>a {
        background: transparent;
    }
    </style>
    <script type="text/javascript">
	    $(document).ready(function() {
	
	        // 绑定菜单单击事件
	        $("#menu a.menu").click(function() {
	            // 一级菜单焦点
	            $("#menu li.menu").removeClass("active");
	            $(this).parent().addClass("active");
	            // 左侧区域隐藏
	            if ($(this).attr("target") == "mainFrame") {
	                $("#left,#openClose").hide();
	                wSizeWidth();
	                return true;
	            }
	            // 左侧区域显示
	            $("#left,#openClose").show();
	            if (!$("#openClose").hasClass("close")) {
	                $("#openClose").click();
	            }
	            // 显示二级菜单
	            var menuId = "#menu-" + $(this).attr("data-id");
	            if ($(menuId).length > 0) {
	                $("#left .accordion").hide();
	                $(menuId).show();
	                // 初始化点击第一个二级菜单
	                if (!$(menuId + " .accordion-body:first").hasClass('in')) {
	                    $(menuId + " .accordion-heading:first a").click();
	                }
	                if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")) {
	                    $(menuId + " .accordion-body a:first i").click();
	                }
	                // 初始化点击第一个三级菜单
	                $(menuId + " .accordion-body li:first li:first a:first i").click();
	            } else {
	                // 获取二级菜单数据
	                $.get($(this).attr("data-href"), function(data) {
	                    if (data.indexOf("id=\"loginForm\"") != -1) {
	                        alert('未登录或登录超时。请重新登录，谢谢！');
	                        top.location = "";
	                        return false;
	                    }
	                    $("#left .accordion").hide();
	                    $("#left").append(data);
	                    // 链接去掉虚框
	                    $(menuId + " a").bind("focus", function() {
	                        if (this.blur) { this.blur() };
	                    });
	                    // 二级标题
	                    $(menuId + " .accordion-heading a").click(function() {
	                        $(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
	                        if (!$($(this).attr('data-href')).hasClass('in')) {
	                            $(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
	                        }
	                    });
	                    // 二级内容
	                    $(menuId + " .accordion-body a").click(function() {
	                        $(menuId + " li").removeClass("active");
	                        $(menuId + " li i").removeClass("icon-white");
	                        $(this).parent().addClass("active");
	                        $(this).children("i").addClass("icon-white");
	                    });
	                    // 展现三级
	                    $(menuId + " .accordion-inner a").click(function() {
	                        var href = $(this).attr("data-href");
	                        if ($(href).length > 0) {
	                            $(href).toggle().parent().toggle();
	                            return false;
	                        }
	
	                    });
	                    // 默认选中第一个菜单
	                    $(menuId + " .accordion-body a:first i").click();
	                    $(menuId + " .accordion-body li:first li:first a:first i").click();
	                });
	            }
	            // 大小宽度调整
	            wSizeWidth();
	            return false;
	        });
	        // 初始化点击第一个一级菜单
	        $("#menu a.menu:first span").click();
	
	        // 鼠标移动到边界自动弹出左侧菜单
	        $("#openClose").mouseover(function() {
	            if ($(this).hasClass("open")) {
	                $(this).click();
	            }
	        });
	
	    });
    </script>
</head>
<body>
<div id="main" style="width: auto;">
    <div id="header" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="brand"><span id="productName">权限管理系统</span></div>
            <ul id="userControl" class="nav pull-right">
                <li id="userInfo" class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#" title="个人信息">您好, 系统超级管理员&nbsp;</a>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx }/sysmgr/toUserInfo.action" target="mainFrame"><i class="icon-user"></i>&nbsp; 个人信息</a></li>
                        <li><a href="${ctx }/sysmgr/toChangePwd.action" target="mainFrame"><i class="icon-lock"></i>&nbsp;  修改密码</a></li>
                    </ul>
                </li>
                <li><a href="${ctx }/logout.action" title="退出登录">退出</a></li>
                <li>&nbsp;</li>
            </ul>
            <div class="nav-collapse">
                <ul id="menu" class="nav" style="*white-space:nowrap;float:none;">
            <!-- 请求一级菜单 main.action parent_id=1-->
            <c:forEach items="${firstMenuList }" var="menu">
            	<c:if test="${menu.isShow eq '1' }">
            	<li class="menu active">
            		<c:if test="${empty menu.href}">
	                 	<a style="cursor: pointer;"class="menu" data-href="${ctx }/sysmgr/menuNavi?parentId=${menu.id}" data-id="${menu.id }">
	                 		<span>${menu.name}</span>
	                 	</a>
            		</c:if>
             	</li>
            	</c:if>
            </c:forEach>
            <!-- <li class="menu">
                        <a class="menu" href="javascript:" data-href="#" data-id="27"><span>我的面板</span></a>
                    </li>
                    <li class="menu active">
                        <a class="menu" href="javascript:" data-href="#" data-id="2"><span>系统设置</span></a>
                    </li>
            -->
                </ul>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div id="content" class="row-fluid">
            <div id="left" style="width: 160px; height: 604px;">
                <%-- <div class="accordion" id="menu-27" style="display: none;">
                    <div class="accordion-group">
                        <div class="accordion-heading">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#menu-27" data-href="#collapse-28" href="#collapse-28" title=""><i class="icon-chevron-down"></i>&nbsp;个人信息</a>
                        </div>
                        <div id="collapse-28" class="accordion-body in collapse" style="height: auto;">
                            <div class="accordion-inner">
                                <ul class="nav nav-list">
                                    <li class=""><a data-href=".menu3-29" href="${ctx }/sysmgr/toUserInfo.action" target="mainFrame"><i class="icon-user"></i>&nbsp;个人信息</a>
                                        <ul class="nav nav-list hide" style="margin:0;padding-right:0;">
                                        </ul>
                                    </li>
                                    <li class="active"><a data-href=".menu3-30" href="${ctx }/sysmgr/toChangePwd.action" target="mainFrame"><i class="icon-lock icon-white"></i>&nbsp;修改密码</a>
                                        <ul class="nav nav-list hide" style="margin:0;padding-right:0;">
                                        </ul>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div> --%>
            </div>
            <div id="openClose" class="close" style="height: 599px;">&nbsp;</div>
            <div id="right" style="height: 604px; width: 1243px;">
                <iframe id="mainFrame" name="mainFrame" src="" style="overflow: visible; height: 604px;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe>
            </div>
        </div>
        <div id="footer" class="row-fluid">
            Copyright © 2018-2019 <a href="${ctx}/index.jsp" target="_blank">权限管理系统</a> - Powered By ZT V1.0.0
        </div>
    </div>
</div>
<script type="text/javascript">
	var leftWidth = 160; // 左侧窗口大小
	var tabTitleHeight = 33; // 页签的高度
	var htmlObj = $("html"),
	    mainObj = $("#main");
	var headerObj = $("#header"),
	    footerObj = $("#footer");
	var frameObj = $("#left, #openClose, #right, #right iframe");
	
	function wSize() {
	    var minHeight = 500,
	        minWidth = 980;
	    var strs = getWindowSize().toString().split(",");
	    htmlObj.css({ "overflow-x": strs[1] < minWidth ? "auto" : "hidden", "overflow-y": strs[0] < minHeight ? "auto" : "hidden" });
	    mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "auto");
	    frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
	    $("#openClose").height($("#openClose").height() - 5);
	    wSizeWidth();
	}
	
	function wSizeWidth() {
	    if (!$("#openClose").is(":hidden")) {
	        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
	        $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 5);
	    } else {
	        $("#right").width("100%");
	    }
	}
</script>
</body>
</html>