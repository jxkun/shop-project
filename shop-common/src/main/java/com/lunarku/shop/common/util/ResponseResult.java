 package com.lunarku.shop.common.util;

import java.util.List;
import java.io.Serializable;
import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应结构  
 */
public class ResponseResult implements Serializable{
	
	// 定义jsackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();
	// 相应业务状态
	private Integer status;
	// 响应消息
	private String msg;
	// 响应中的数据
	private Object data;
	
	public static ResponseResult build(Integer status, String msg, Object data) {
		return new ResponseResult(status, msg, data);
	}
	
	public static ResponseResult ok(Object data) {
		return new ResponseResult(data);
	}
	
	public static ResponseResult ok() {
		return new ResponseResult(null);
	}
	
	public ResponseResult() {

	}
	
	public ResponseResult(Object data) {
		this.status = 200;
		this.msg = "OK";
		this.data = data;
	}

	public ResponseResult(Integer status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * 将json结果集转化为ResponseResult对象
	 * @param jsonData
	 * @param clazz ResponseResult中的object对象
	 * @return
	 */
	public static ResponseResult formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if(clazz == null) {
				return MAPPER.readValue(jsonData, ResponseResult.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			
			if(data.isObject()) {
				obj = MAPPER.readValue(data.traverse(), clazz);
			}else if(data.isTextual()) {
				obj = MAPPER.readValue(data.asText(), clazz);
			}
			
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 没有Object对象的ResponseResult对象转化
	 * @param json
	 * @return
	 */
	public static ResponseResult format(String json) {
		try {
			return MAPPER.readValue(json, ResponseResult.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Object为集合时的转化
	 * @param jsonData
	 * @param clazz
	 * @return
	 */
	public static ResponseResult formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if(data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType((Class<? extends Collection>) List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ObjectMapper getMapper() {
		return MAPPER;
	}
}
