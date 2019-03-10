package com.zt.ssspm.common;

import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 分页查询的基类
 * @ClassName : com.zt.ssspm.common.BaseDao
 * @Description : TODO
 * @author : HeadMaster
 * @date : 2018年7月23日
 */
public class BaseDao {
	
	protected JdbcTemplate template;
		
	protected Integer getTotalRows(String sql){
		Integer count = 0;
		String countSql = " SELECT COUNT(*) FROM ("+ sql +") Y" ;
		try {
			count = this.template.queryForObject(countSql, Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	protected String getDbType(){
		String dbType = "MySQL"; 
		try {
    		dbType= this.template.getDataSource().getConnection().getMetaData().getDatabaseProductName();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return dbType;
	}

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	 
}
