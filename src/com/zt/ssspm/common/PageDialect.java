package com.zt.ssspm.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 各种数据库的分页查询的SQL语句
 * @ClassName : com.zt.ssspm.common.PageDialect
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月23日
 */
public class PageDialect {

	private static Log logger = LogFactory.getLog(PageDialect.class);
	/**
	 * 根据不同的数据库类型转换成不同的分页sq
	 * @Title: getPageDialectSQL
	 * @Description: TODO
	 * @param sql
	 * @param page
	 * @param dbType
	 * @return
	 */
	public static String getPageDialectSQL(String sql,PageObject page,String dbType) {
		if(dbType.equals("MySQL")){
        	return getMysqlDialect(sql,page);
    	}else if(dbType.equals("Oracle")){
    		return getOracleDialect(sql,page);
    	}else{
    		logger.warn("未知数据库,需要补充方言");
    	}
    	return " ";
	}
	/**
	 * Oracle的分页：SELECT B.* FROM (SELECT A.* ,ROWNUM AS RN FROM TABLE A) B 
	 * WHERE B.RN BETWEEN BEGINROW AND ENDROW 
	 * @Title: getOracleDialect
	 * @Description: TODO
	 * @param sql
	 * @param page
	 * @return
	 */
	private static String getOracleDialect(String sql, PageObject page) {
		StringBuffer oracleSqlBuffer = new StringBuffer();
		oracleSqlBuffer.append(" SELECT Y.* FROM ");
		oracleSqlBuffer.append(" (");
		oracleSqlBuffer.append(" SELECT M.*,ROWNUM AS RN FROM ("+sql+" ) M ");   	 		 
		oracleSqlBuffer.append("  ) Y ");
		oracleSqlBuffer.append(" WHERE Y.RN BETWEEN "+page.getBeginRow()+" AND "+page.getEndRow());
		return oracleSqlBuffer.toString();
	}
	/**
	 * MySQL的分页
	 * select * from table limit beginRow,pageSize
	 * @Title: getMysqlDialect
	 * @Description: TODO
	 * @param sql
	 * @param page
	 * @return
	 */
	private static String getMysqlDialect(String sql, PageObject page) {
		StringBuffer pageSqlBuffer = new StringBuffer();
		int beginRowForMySql= page.getBeginRow()-1;
    	int pageSize = page.getPageSize();
    	pageSqlBuffer.append(sql);
    	pageSqlBuffer.append(" limit "+beginRowForMySql+","+pageSize);
    	return  pageSqlBuffer.toString();
	}
}
