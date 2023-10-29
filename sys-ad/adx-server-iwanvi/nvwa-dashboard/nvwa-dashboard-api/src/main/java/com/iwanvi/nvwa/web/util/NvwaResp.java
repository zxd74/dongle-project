package com.iwanvi.nvwa.web.util;

public class NvwaResp {
	private String code;
	private String message;
	private long timestamp = System.currentTimeMillis();
	private Object data;

	public NvwaResp() {}
	
	public NvwaResp(String code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public NvwaResp(String message, Object data) {
		this.code = "A000000";
		this.message = message;
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
