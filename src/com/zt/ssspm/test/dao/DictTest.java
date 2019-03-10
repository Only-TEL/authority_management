package com.zt.ssspm.test.dao;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zt.ssspm.sysmanage.entity.Dict;
import com.zt.ssspm.sysmanage.service.IDictService;

public class DictTest {

	private IDictService service;
	
	@SuppressWarnings("resource")
	@Before
	public void fun() {
		ClassPathXmlApplicationContext cs = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.service = (IDictService) cs.getBean("dictService");
	}
	@Test
	public void getAllTypesTest() {
		 List<String> list = service.queryAllType();
		 for (String string : list) {
			System.out.print(string+'\t');
		}
	}
	@Test
	public void queryDetailTest() {
		Dict dict = service.queryDictDetail(1L);
		System.out.println(dict.getLabel());
	}
}
