package com.lunarku.search.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lunarku.search.mapper.SearchItemMapper;
import com.lunarku.shop.common.pojo.SearchItem;

public class SearchMapperTest {
	
	@Test
	public void testGetItem() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		SearchItemMapper searchItemMapper = context.getBean(SearchItemMapper.class);
		SearchItem item = searchItemMapper.getItem(1023967335L);
		System.out.println(item.getTitle());
	}
	
}
