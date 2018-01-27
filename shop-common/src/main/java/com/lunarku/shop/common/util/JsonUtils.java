package com.lunarku.shop.common.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 定义json工具类 
 */
public final class JsonUtils {
	
	// 定义jackson对象
	private static final ObjectMapper Mapper = new ObjectMapper();
	
	/**
	 * 对象转为json字符串
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		try {
			String json = Mapper.writeValueAsString(data);
			return json;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * json字符串转成pojo对象
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = Mapper.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * json字符串转成pojo集合
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType){
		JavaType javaType = Mapper.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = Mapper.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
