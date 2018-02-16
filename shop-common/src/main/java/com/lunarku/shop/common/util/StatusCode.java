package com.lunarku.shop.common.util;

public enum StatusCode {
	
	SUSSESS(200, "susscess"),
	
	FAIL(800, "opration fail"),
	
	PARAMETER_ERROR(900, "parameter error");
	
	private Integer code;
	
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private StatusCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
