package com.lunarku.content.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lunarku.content.service.ContentCategoryService;
import com.lunarku.shop.common.pojo.EasyUITreeNode;
import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.pojo.TbContentCategory;

public class ContentCategoryServiceTest {
	
	
	@Test
	public void testContentCategoryService() throws Exception {
		ApplicationContext	context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		ContentCategoryService service = context.getBean(ContentCategoryService.class);
		
		List<EasyUITreeNode> list = service.getContentCatgory(0L);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		System.out.println(json);
	}
	
}
