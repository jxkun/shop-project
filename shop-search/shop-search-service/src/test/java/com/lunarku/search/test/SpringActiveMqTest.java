package com.lunarku.search.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringActiveMqTest {
	
	private String path = "spring/applicationContext-activemq.xml";
	
	@Test
	public void testConsumer() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(path);
		//System.in.read();
		Thread.sleep(10 * 1000);
	}
}
