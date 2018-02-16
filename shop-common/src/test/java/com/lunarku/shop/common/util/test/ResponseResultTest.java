package com.lunarku.shop.common.util.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.lunarku.shop.common.util.JsonUtils;
import com.lunarku.shop.common.util.ResponseResult;

public class ResponseResultTest {
	
	public String json = null;
	public String jsonList = null;
	
	@Before
	public void before() {
		Tmp tmp1 = new Tmp("jack", 15);
		Tmp tmp2 = new Tmp("Bob", 20);
		String data = JsonUtils.objectToJson(tmp2);
		
		List<Tmp> list = new ArrayList<Tmp>();
		list.add(tmp1);
		list.add(tmp2);
		
		json = "{\"status\":1,\"msg\":\"ok\",\"data\":"+ data +"}";
		
		String dateList = JsonUtils.objectToJson(list);
		jsonList = "{\"status\":1,\"msg\":\"ok\",\"data\":"+ dateList +"}";
		System.out.println(json);
		System.out.println(jsonList);
	}
	
	@Test
	public void testFormatToPojo() {
		ResponseResult responseResult = ResponseResult.formatToList(jsonList, Tmp.class);
		System.out.println(responseResult.getData());
		System.out.println(responseResult.getMsg());
		System.out.println(responseResult.getStatus());
	}
	
	@Test
	public void testFormatToList() {
		ResponseResult responseResult = ResponseResult.formatToPojo(json, Tmp.class);
		System.out.println(responseResult.getData());
		System.out.println(responseResult.getMsg());
		System.out.println(responseResult.getStatus());
	}
	
	@Test
	public void testJsonToPojo() {
		Tmp tmp1 = new Tmp("jack", 15);
		Tmp tmp = JsonUtils.jsonToPojo(JsonUtils.objectToJson(tmp1), Tmp.class);
		System.out.println(tmp.getName());
	}
	
}


class Tmp implements Serializable{
	
	private String name;
	
	private int age;

	public Tmp(){
		
	}
	public Tmp(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
