package com.lunarku.content.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lunarku.content.redis.JedisClient;

public class JedisClientTest {
	
	@Test
	public void testJedisClient() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("jedisClient", "test");
		String result = jedisClient.get("jedisClient");
		
		System.out.println(result);
	}
}
