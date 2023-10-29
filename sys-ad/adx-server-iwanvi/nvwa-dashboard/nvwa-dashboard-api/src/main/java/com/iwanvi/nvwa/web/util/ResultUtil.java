package com.iwanvi.nvwa.web.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel(description = "通用响应类")
public class ResultUtil {
	
	@ApiModelProperty(value = "状态码",dataType="string",required = true)
	private String code;
	@ApiModelProperty(value = "时间",dataType = "string")
	private String timestamp;
	@ApiModelProperty(value = "状态信息",dataType = "string")
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
