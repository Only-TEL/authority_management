package com.zt.ssspm.sysmanage.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.zt.ssspm.common.BaseDao;
import com.zt.ssspm.sysmanage.dao.IMenuDao;
import com.zt.ssspm.sysmanage.entity.Menu;

public class MenuDao extends BaseDao implements IMenuDao{
	
	public void setTemplate(JdbcTemplate template) {
		super.setTemplate(template);
	}
	
	@Override
	public List<Menu> queryMenuListByUserId(Long userId) {
			/*SELECT DISTINCT m.id,m.parent_id,m.name,m.sort,m.href,
			m.target,m.icon,m.is_show,m.permission,m.create_by,
			m.create_date,m.update_by,m.update_date,m.remarks
			FROM pm_sys_user_role u,pm_sys_role_menu r,pm_sys_menu m
			WHERE u.user_id=?
			AND u.role_id=r.role_id
			AND r.menu_id=m.id
			AND del_flag=0
			*/
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT DISTINCT m.id,m.parent_id,m.name,m.sort,m.href,m.target,m.icon,m.is_show,m.permission,m.create_by, ");
		buffer.append(" m.create_date,m.update_by,m.update_date,m.remarks FROM pm_sys_user_role u,pm_sys_role_menu r,pm_sys_menu m ");
		buffer.append(" WHERE u.user_id=? ");
		buffer.append(" AND u.role_id=r.role_id ");
		buffer.append(" AND r.menu_id=m.id ");
		buffer.append(" AND del_flag=0 ");
		List<Menu> list = this.template.query(buffer.toString(), new Object[] {userId}, new BeanPropertyRowMapper<Menu>(Menu.class));
		if(list != null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public List<Menu> queryAllMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id, parent_id, name, sort, href, target, icon, is_show, permission, create_by, ");
		buffer.append(" create_date,update_by,update_date,remarks FROM pm_sys_menu ");
		buffer.append(" WHERE del_flag=0 AND 1=1 ");
		List<Menu> list = this.template.query(buffer.toString(), new BeanPropertyRowMapper<Menu>(Menu.class));
		if(list != null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public Menu queryMenuById(Long menuId) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT c.id, c.parent_id, d.name AS parent_name,c.name, c.sort, c.href, c.target, c.icon, ");
		buffer.append(" c.is_show, c.permission, c.create_by,c.create_date,c.update_by,c.update_date,c.remarks  ");
		buffer.append(" FROM pm_sys_menu c LEFT JOIN pm_sys_menu d  ");
		buffer.append(" ON c.parent_id=d.id WHERE c.id=?  AND c.del_flag=0 ");
		
		return this.template.queryForObject(buffer.toString(), new Object[] {menuId}, new BeanPropertyRowMapper<Menu>(Menu.class));
	}

	@Override
	public boolean updateMenu(Menu m) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_menu SET parent_id=?, name=?, sort=?, href=?, target=?, icon=?, is_show=?, permission=?, create_by=?, ");
		buffer.append(" create_date=?,update_by=?,update_date=?,remarks=? ");
		buffer.append(" WHERE id=? AND del_flag=0  ");

		int rows = this.template.update(buffer.toString(), m.getParentId(),m.getName(),m.getSort(),m.getHref(),m.getTarget(),
										m.getIcon(),m.getIsShow(),m.getPermission(),m.getCreateBy(),
										m.getCreateDate(),m.getUpdateBy(),m.getUpdateDate(),m.getRemarks(),m.getId());
		return rows > 0;
	}

	@Override
	public Long saveMenu(Menu m) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" INSERT INTO pm_sys_menu (parent_id, name, sort, href, target, icon, is_show, permission, create_by,create_date,update_by,update_date,remarks) ");
		buffer.append(" VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?) ");
		
		KeyHolder holder = new GeneratedKeyHolder();
		this.template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				
				PreparedStatement ps = connection.prepareStatement(buffer.toString());
				ps.setObject(1, m.getParentId());
				ps.setObject(2, m.getName());
				ps.setObject(3, m.getSort());
				ps.setObject(4, m.getHref());
				ps.setObject(5, m.getTarget());
				ps.setObject(6, m.getIcon());
				ps.setObject(7, m.getIsShow());
				ps.setObject(8, m.getPermission());
				ps.setObject(9, m.getCreateBy());
				ps.setObject(10, m.getCreateDate());
				ps.setObject(11, m.getUpdateBy());
				ps.setObject(12, m.getUpdateDate());
				ps.setObject(13, m.getRemarks());
				return ps;
			}
		},holder);
		return holder.getKey().longValue();
	}

	@Override
	public boolean delMenu(Long menuId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" UPDATE pm_sys_menu SET del_flag=1 ");
		buffer.append(" WHERE id=? AND 1=1 ");
		int rows = this.template.update(buffer.toString(), menuId);
		return rows > 0;
	}

	@Override
	public boolean hasChildMenu(Long menuId) {
		
		String sql  = " SELECT COUNT(ID) AS count FROM pm_sys_menu WHERE PARENT_ID=? AND DEL_FLAG=0 ";
		Long count = this.template.queryForObject(sql, new RowMapper<Long>(){
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return rs.getLong("count");
			}
			
		}, menuId);
		
		return count > 0;
	}

	@Override
	public List<Long> getChildrenIds(Long menuId) {
		String sql = " SELECT ID FROM pm_sys_menu WHERE parent_id =? AND DEL_FLAG=0 ";
		List<Long> list = this.template.queryForList(sql, Long.class, menuId);
		if(list!=null && list.size()>0) {
			return list;
		}else {
			return null;
		}
	}

	@Override
	public void getChildrenIds(Long menuId, List<Long> listIds) {
		// 查询第一级
		List<Long> list = getChildrenIds(menuId);
		if(list != null && list.size()>0){
			// 有下一及菜单
			listIds.addAll(list);
			// 遍历下一及菜单
			for (Long id : list) {
				if(hasChildMenu(id)){
					// 存在子菜单，迭代
					getChildrenIds(id,listIds);
				}else {
					continue;
				}
			}
		}else{
			return;
		}
	}

	@Override
	public List<Menu> queryOtherMenuById(Long menuId) {
		
		Object param = null;
		
		if(hasChildMenu(menuId)) {
			// 获取所有的子节点id
			List<Long> listIds = new ArrayList<>();
			getChildrenIds(menuId, listIds);
			// 将自己放入集合
			listIds.add(menuId);
			// 组装参数
			String str = "";
			for (Long id : listIds) {
				str += (id+",");
			}
			param = str.substring(0, str.length()-1);
		}else {
			param = menuId;
		}
		// 组装sql语句
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id, parent_id, name, sort, href, target, icon, is_show, permission, create_by, ");
		buffer.append(" create_date,update_by,update_date,remarks FROM pm_sys_menu ");
		buffer.append(" WHERE id NOT IN ("+ param +") AND del_flag=0 ");
		// 查询
		List<Menu> menuList = this.template.query(buffer.toString(),new BeanPropertyRowMapper<Menu>(Menu.class));
		
		if(menuList!=null && menuList.size()>0) {
			return menuList;
		}else {
			return null;
		}
			
	}
	@Override
	public List<Menu> queryChildrenMenu(long parentId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT id, parent_id, name, sort, href, target, icon, is_show, permission, create_by, ");
		buffer.append(" create_date,update_by,update_date,remarks FROM pm_sys_menu ");
		buffer.append(" WHERE parent_id=? AND del_flag=0 ORDER BY parent_id,sort");
		List<Menu> menuList = this.template.query(buffer.toString(), new Object[] {parentId}, new BeanPropertyRowMapper<Menu>(Menu.class));
		if(menuList!=null && menuList.size()>0) {
			return menuList;
		}else {
			return null;
		}
	}
	
}
