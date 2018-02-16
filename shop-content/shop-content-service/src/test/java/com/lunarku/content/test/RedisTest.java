package com.lunarku.content.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {
	
	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("192.168.25.131",6379);
		jedis.auth("root");
		
		jedis.set("redis-key", "123456");
		String value = jedis.get("redis-key");
		System.out.println(value);
		
		jedis.close();
	}
	
	@Test
	public void testJedisPool() {
		JedisPool pool = new JedisPool("192.168.25.131",6379);
		
		Jedis jedis = pool.getResource();
		jedis.auth("root");
		String value = jedis.get("redis-key");
		System.out.println(value);
		
		jedis.close();
	}
	
}
