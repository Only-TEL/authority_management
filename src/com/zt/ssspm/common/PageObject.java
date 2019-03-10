package com.zt.ssspm.common;

import java.io.Serializable;

/**
 * 定义分页查询使用的类
 * @ClassName : com.zt.ssspm.common.PageObject
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月23日
 */
public class PageObject implements Serializable{

	 /**   
	  * @Fields serialVersionUID : TODO
	  */
	private static final long serialVersionUID = -7587278673016966413L;
	
	// 总记录数
	private Integer totalRow;
	// 当前页数
	private Integer currentPage;
	// 每页显示行数
	private Integer pageSize;
	// 总页数
	private Integer totalPage;
	// 开始和结束的行号
	private Integer beginRow;
	private Integer endRow;
	// 定义最大的总页数和默认的每页显示行数
	private final Integer DEFAULT_PAGESIZE = 10;
	private final Integer MAX_PAGESIZE = 100;
	// 构造
	public PageObject() {
		
	}
	public PageObject(Integer currentPage) {
		this.currentPage = currentPage;
		this.init();
	}
	public PageObject(Integer currentPage,Integer pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.init();
	}
	// 初始化数据 
	private void init() {
		if(this.currentPage <= 0) {
			this.currentPage = 1;
		}
		if(this.pageSize == null) {
			this.pageSize = DEFAULT_PAGESIZE;
		}		
		if(this.pageSize > MAX_PAGESIZE) {
			this.pageSize = MAX_PAGESIZE;
		}
		// 计算起始结束行数
		if(this.pageSize > 0) {
			this.beginRow = (currentPage-1)*pageSize + 1;
			this.endRow = currentPage * pageSize;
		}else {
			this.beginRow = 1;
			this.endRow = MAX_PAGESIZE;
		}
	}
	// 获取上一页
	public Integer getPrePage() {
		return this.currentPage-1;
	}
	// 获取下一页
	public Integer getNextPage() {
		return this.currentPage+1;
	}
	// 判断是否是第一页
	public boolean isFirstPage() {
		if(this.currentPage==1)
			return true;				
		else
			return false;
	}
	//判断是否是最后一页
	public boolean isLastPage(){
		if(this.currentPage==this.totalPage)
			return true;
		else
			return false;
	}
	public Integer getTotalRow() {
		return totalRow;
	}
	// 设置总记录数的时候计算总页数
	public void setTotalRow(Integer totalRow) {
		this.totalRow = totalRow;
		this.totalPage = (this.totalRow+this.pageSize-1)/this.pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getBeginRow() {
		return beginRow;
	}
	public void setBeginRow(Integer beginRow) {
		this.beginRow = beginRow;
	}
	public Integer getEndRow() {
		return endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getDEFAULT_PAGESIZE() {
		return DEFAULT_PAGESIZE;
	}
	public Integer getMAX_PAGESIZE() {
		return MAX_PAGESIZE;
	}
		
}
