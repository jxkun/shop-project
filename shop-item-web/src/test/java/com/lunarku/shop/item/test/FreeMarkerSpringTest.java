package com.lunarku.shop.item.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lunarku.item.listener.ItemAddMessageListener;

public class FreeMarkerSpringTest {

	private ApplicationContext context = null;
	private String classpath = "classpath:spring/*.xml";
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext(classpath);
	}
	
	@Test
	public void testItemAddMessageListener() {
		ItemAddMessageListener itemAddMessageListener = context.getBean(ItemAddMessageListener.class);
	}
}
