package com.lunarku.shop.pagehelper;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.lunarku.shop.mapper.TbItemMapper;

public class PageHelperTest {
	
	@Test
	public void testPageHelper() {
		//在mybatis中配置分页插件 
		// 执行查询之前配置分页条件
		PageHelper.startPage(1, 10);
		//执行查询
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper= context.getBean(TbItemMapper.class);
		//itemMapper.selectByExample(example);
		
		
	}
	
}
