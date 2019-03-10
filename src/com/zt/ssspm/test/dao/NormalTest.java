package com.zt.ssspm.test.dao;

import org.junit.Test;

public class NormalTest {

	@Test
	public void fun() {
		int[] params = {1,2,3,5,6,4,74};
		String string ="";
		for(int i=0;i<params.length;i++) {
			string += params[i]+",";
		}
		String substring = string.substring(0,string.length()-1);
		System.out.println(substring);
	}
}
