package com.lunarku.shop.redis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RedisClientTest {
	
	@Test
	public void testRedisClient() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisClient jedisClient = context.getBean(JedisClient.class);
		jedisClient.set("manager", "test");
		String value = jedisClient.get("manager");
		System.out.println(value);
	}
}
