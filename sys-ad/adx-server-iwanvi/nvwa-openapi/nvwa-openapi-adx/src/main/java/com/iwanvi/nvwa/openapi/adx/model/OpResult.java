/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.model;

/**
 * 数据操作响应结果
 * 
 * @author wangwp
 */
public class OpResult {
	private String code;
	private String msg;
	
	public OpResult() {}
	
	public String getCode() {
		return code;
	}
	
	public OpResult setCode(String code) {
		this.code = code;
		return this;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public OpResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
