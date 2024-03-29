/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.model;

import javax.annotation.Generated;

/**
 * 
 * @author wangwp
 */
public class ApiRespBody {
	private int code;
	private String msg;
	private Object data;

	@Generated("SparkTools")
	private ApiRespBody(Builder builder) {
		this.code = builder.code;
		this.msg = builder.msg;
		this.data = builder.data;
	}
	
	public ApiRespBody() {}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
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

	/**
	 * Creates builder to build {@link ApiRespBody}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link ApiRespBody}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private int code;
		private String msg;
		private Object data;

		private Builder() {
		}

		public Builder withCode(int code) {
			this.code = code;
			return this;
		}

		public Builder withMsg(String msg) {
			this.msg = msg;
			return this;
		}

		public Builder withData(Object data) {
			this.data = data;
			return this;
		}

		public ApiRespBody build() {
			return new ApiRespBody(this);
		}
	}
}
