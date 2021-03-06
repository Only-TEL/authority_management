<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<!-- saved from url=(0036)http://localhost:8080/sys/user/index -->
<html style="overflow: hidden;">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>用户管理</title>
	<%@ include file="/WEB-INF/pages/include/head.jsp"%>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
    <!-- <script type="text/javascript">top.$.jBox.closeTip();</script> -->
    <div id="content" class="row-fluid">
        <div id="left" class="accordion-group" style="width: 180px; height: 742px;">
            <div class="accordion-heading">
                <a class="accordion-toggle">组织机构<i class="icon-refresh pull-right" onclick="refreshTree();"></i></a>
            </div>
            <div id="ztree" class="ztree" style="width: 170px; height: 696px;">
                <li id="ztree_1" class="level0" tabindex="0" hidefocus="true" treenode=""><span id="ztree_1_switch" title="" class="button level0 switch root_open" treenode_switch=""></span><a id="ztree_1_a" class="level0" treenode_a="" onclick="" target="_blank" style="" title="湖南省总公司"><span id="ztree_1_ico" title="" treenode_ico="" class="button ico_open" style=""></span><span id="ztree_1_span">湖南省总公司</span></a>
                    <ul id="ztree_1_ul" class="level0 ">
                        <li id="ztree_2" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="ztree_2_switch" title="" class="button level1 switch center_docu" treenode_switch=""></span><a id="ztree_2_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="公司领导"><span id="ztree_2_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_2_span">公司领导</span></a></li>
                        <li id="ztree_3" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="ztree_3_switch" title="" class="button level1 switch center_docu" treenode_switch=""></span><a id="ztree_3_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="综合部"><span id="ztree_3_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_3_span">综合部</span></a></li>
                        <li id="ztree_4" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="ztree_4_switch" title="" class="button level1 switch center_docu" treenode_switch=""></span><a id="ztree_4_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="市场部"><span id="ztree_4_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_4_span">市场部</span></a></li>
                        <li id="ztree_5" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="ztree_5_switch" title="" class="button level1 switch center_docu" treenode_switch=""></span><a id="ztree_5_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="技术部"><span id="ztree_5_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_5_span">技术部</span></a></li>
                        <li id="ztree_6" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="ztree_6_switch" title="" class="button level1 switch center_docu" treenode_switch=""></span><a id="ztree_6_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="研发部"><span id="ztree_6_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_6_span">研发部</span></a></li>
                        <li id="ztree_7" class="level1" tabindex="0" hidefocus="true" treenode=""><span id="ztree_7_switch" title="" class="button level1 switch bottom_open" treenode_switch=""></span><a id="ztree_7_a" class="level1" treenode_a="" onclick="" target="_blank" style="" title="长沙市分公司"><span id="ztree_7_ico" title="" treenode_ico="" class="button ico_open" style=""></span><span id="ztree_7_span">长沙市分公司</span></a>
                            <ul id="ztree_7_ul" class="level1 " style="">
                                <li id="ztree_8" class="level2" tabindex="0" hidefocus="true" treenode=""><span id="ztree_8_switch" title="" class="button level2 switch center_docu" treenode_switch=""></span><a id="ztree_8_a" class="level2" treenode_a="" onclick="" target="_blank" style="" title="公司领导"><span id="ztree_8_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_8_span">公司领导</span></a></li>
                                <li id="ztree_9" class="level2" tabindex="0" hidefocus="true" treenode=""><span id="ztree_9_switch" title="" class="button level2 switch center_docu" treenode_switch=""></span><a id="ztree_9_a" class="level2" treenode_a="" onclick="" target="_blank" style="" title="综合部"><span id="ztree_9_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_9_span">综合部</span></a></li>
                                <li id="ztree_10" class="level2" tabindex="0" hidefocus="true" treenode=""><span id="ztree_10_switch" title="" class="button level2 switch center_docu" treenode_switch=""></span><a id="ztree_10_a" class="level2" treenode_a="" onclick="" target="_blank" style="" title="市场部"><span id="ztree_10_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_10_span">市场部</span></a></li>
                                <li id="ztree_11" class="level2" tabindex="0" hidefocus="true" treenode=""><span id="ztree_11_switch" title="" class="button level2 switch center_docu" treenode_switch=""></span><a id="ztree_11_a" class="level2" treenode_a="" onclick="" target="_blank" style="" title="技术部"><span id="ztree_11_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_11_span">技术部</span></a></li>
                                <li id="ztree_12" class="level2" tabindex="0" hidefocus="true" treenode=""><span id="ztree_12_switch" title="" class="button level2 switch bottom_open" treenode_switch=""></span><a id="ztree_12_a" class="level2" treenode_a="" onclick="" target="_blank" style="" title="芙蓉区分公司"><span id="ztree_12_ico" title="" treenode_ico="" class="button ico_open" style=""></span><span id="ztree_12_span">芙蓉区分公司</span></a>
                                    <ul id="ztree_12_ul" class="level2 " style="">
                                        <li id="ztree_13" class="level3" tabindex="0" hidefocus="true" treenode=""><span id="ztree_13_switch" title="" class="button level3 switch center_docu" treenode_switch=""></span><a id="ztree_13_a" class="level3" treenode_a="" onclick="" target="_blank" style="" title="公司领导"><span id="ztree_13_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_13_span">公司领导</span></a></li>
                                        <li id="ztree_14" class="level3" tabindex="0" hidefocus="true" treenode=""><span id="ztree_14_switch" title="" class="button level3 switch center_docu" treenode_switch=""></span><a id="ztree_14_a" class="level3" treenode_a="" onclick="" target="_blank" style="" title="综合部"><span id="ztree_14_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_14_span">综合部</span></a></li>
                                        <li id="ztree_15" class="level3" tabindex="0" hidefocus="true" treenode=""><span id="ztree_15_switch" title="" class="button level3 switch center_docu" treenode_switch=""></span><a id="ztree_15_a" class="level3" treenode_a="" onclick="" target="_blank" style="" title="市场部"><span id="ztree_15_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_15_span">市场部</span></a></li>
                                        <li id="ztree_16" class="level3" tabindex="0" hidefocus="true" treenode=""><span id="ztree_16_switch" title="" class="button level3 switch bottom_docu" treenode_switch=""></span><a id="ztree_16_a" class="level3" treenode_a="" onclick="" target="_blank" style="" title="技术部"><span id="ztree_16_ico" title="" treenode_ico="" class="button ico_docu" style=""></span><span id="ztree_16_span">技术部</span></a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </div>
        </div>
        <div id="openClose" class="close" style="height: 742px;">&nbsp;</div>
        <div id="right" style="height: 742px; width: 1222px;">
            <iframe id="officeContent" src="userList.html" width="100%" height="91%" frameborder="0" style="height: 742px;"></iframe>
        </div>
    </div>
    <script type="text/javascript">
    var setting = {
        data: { simpleData: { enable: true, idKey: "id", pIdKey: "pId", rootPId: '0' } },
        callback: {
            onClick: function(event, treeId, treeNode) {
                var id = treeNode.id == '0' ? '' : treeNode.id;
            }
        }
    };
    var leftWidth = 180; // 左侧窗口大小
    var htmlObj = $("html"),
        mainObj = $("#main");
    var frameObj = $("#left, #openClose, #right, #right iframe");

    function wSize() {
        var strs = getWindowSize().toString().split(",");
        htmlObj.css({ "overflow-x": "hidden", "overflow-y": "hidden" });
        mainObj.css("width", "auto");
        frameObj.height(strs[0] - 5);
        var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        $("#right").width($("#content").width() - leftWidth - $("#openClose").width() - 5);
        $(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
    }
    </script>
   <!--  <script src="static/common/wsize.min.js" type="text/javascript"></script> -->
    <div id="haloword-lookup" class="ui-widget-content ui-draggable">
        <div id="haloword-title"><span id="haloword-word"></span><a herf="#" id="haloword-pron" class="haloword-button" title="发音"></a>
            <audio id="haloword-audio"></audio>
            <div id="haloword-control-container"><a herf="#" id="haloword-add" class="haloword-button" title="加入单词表"></a><a herf="#" id="haloword-remove" class="haloword-button" title="移出单词表"></a><a href="http://localhost:8080/sys/user/index#" id="haloword-open" class="haloword-button" title="查看单词详细释义" target="_blank"></a><a herf="#" id="haloword-close" class="haloword-button" title="关闭查询窗"></a></div>
            <br style="clear: both;">
        </div>
        <div id="haloword-content"></div>
    </div>
</body>
</html>