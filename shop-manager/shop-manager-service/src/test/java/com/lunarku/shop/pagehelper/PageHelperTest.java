package com.lunarku.shop.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lunarku.shop.mapper.TbItemMapper;
import com.lunarku.shop.pojo.TbItem;

public class PageHelperTest {
	
	@Test
	public void testPageHelper() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		System.out.println(context);
		TbItemMapper itemMapper= context.getBean(TbItemMapper.class);
		//在mybatis中配置分页插件 
		// 执行查询之前配置分页条件
		PageHelper.startPage(1, 10);
		//执行查询
		List<TbItem>list = itemMapper.selectByItem(null);
		PageInfo pageInfo = new PageInfo(list);
		System.out.println(pageInfo.getTotal());
		for(TbItem item: list) {
			System.out.println(item);
		}
	}
}
