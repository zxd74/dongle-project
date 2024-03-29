/*
 * Copyright 2018 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.adx.connector.helper;

/**
 * @author weiping wang
 *
 */
public class AdxResponse {
	private int code;
	private String msg;
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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
}
