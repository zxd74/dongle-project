package com.iwanvi.nvwa.web.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "具体响应类",parent = ResultUtil.class)
public class ObjectResultUtil<T> extends ResultUtil{
	
	@ApiModelProperty(value = "响应实体类",required=true)
	private T result;
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

}
