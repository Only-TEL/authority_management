package com.zt.ssspm.test.dao;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.zt.ssspm.sysmanage.dao.IMenuDao;
import com.zt.ssspm.sysmanage.entity.Menu;
import com.zt.ssspm.sysmanage.service.IMenuService;

public class MenuTest {
	
	private IMenuService service;
	private IMenuDao menuDao;
	@Before
	@SuppressWarnings("resource")
	public void fun() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.service = (IMenuService) cs.getBean("menuService");
		this.menuDao = (IMenuDao) cs.getBean("menuDao");
	}
	@Test
	public void queryListTest() {
		List<Menu> list = service.queryMenuListByUserId(1L);
		for (Menu menu : list) {
			System.out.println(menu.getName());
		}
	}
	@Test
	public void queryTest() {
		//boolean b = menuDao.hasChildMenu(3L);
		List<Long> list = menuDao.getChildrenIds(3L);
		for (Long long1 : list) {
			System.out.println(long1);
		}
		
	}
	@Test
	public void queryOtherMenuTest() {
//		boolean b = menuDao.hasChildMenu(4L);
//		System.out.println(b);
		List<Menu> list = menuDao.queryOtherMenuById(3L);
		for (Menu Menu : list) {
			System.out.println(Menu.getName());
		}
		
	}
	public void setService(IMenuService service) {
		this.service = service;
	}
	
}
