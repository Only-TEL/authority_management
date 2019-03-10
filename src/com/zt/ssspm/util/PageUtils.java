package com.zt.ssspm.util;

import com.zt.ssspm.common.PageObject;

/**
 * 生成页面上的分页条 
 * @ClassName : com.zt.ssspm.util.PageUtils
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月23日
 */
public class PageUtils {
	
    public static String getPageStr (PageObject pageInfo) {

        StringBuilder sb = new StringBuilder("<ul>");
        //判断当前页是不是首页
        if (pageInfo.isFirstPage()
                || pageInfo.getPrePage() == 0) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">&#171; 上一页</a></li>");
        } else {
            sb.append("<li><a href=\"javascript:pageSearch(");
            sb.append(pageInfo.getPrePage()).append(",");
            sb.append(pageInfo.getPageSize()).append(")\">&#171; 上一页</a></li>");
        }

        //判断是否是尾页
        if (pageInfo.isLastPage() || pageInfo.getNextPage() == 0) {
            sb.append("<li class=\"disabled\"><a href=\"javascript:\">下一页 &#187;</a></li>");
        } else {
            sb.append("<li><a href=\"javascript:pageSearch(");
            sb.append(pageInfo.getNextPage()).append(",");
            sb.append(pageInfo.getPageSize()).append(")\">下一页 &#187;</a></li>");
        }
        sb.append("<li class=\"disabled controls\"><a href=\"javascript:void(0);\">当前第 ");
        sb.append(pageInfo.getCurrentPage());
        sb.append(" 页 / 共 ");
        sb.append(pageInfo.getTotalPage());
        sb.append(" 页， 共 ");
        sb.append(pageInfo.getTotalRow());
        sb.append(" 条</a></li></ul>");
        
       /* sb.append("<li class=\"disabled controls\"><a href=\"javascript:void(0);\">当前第 ");
        sb.append("<input type=\"text\" maxLength=\"6\" value=\"");
        sb.append(pageInfo.getCurrentPage());
        sb.append("\" onkeypress=\"var e=window.event||this;var c=e.keyCode||e.which;if(c==13)page(this.value,");
        sb.append(pageInfo.getPageSize()).append(");\" onclick=\"this.select();\"/>");
        sb.append(" 页 / 共 ");
        sb.append(pageInfo.getPages());
        sb.append(" 页， 共 ");
        sb.append(pageInfo.getTotal());
        sb.append(" 条</a></li></ul>");*/

        return sb.toString();

    }


}
