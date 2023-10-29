package com.iwanvi.nvwa.web.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.Put;
import com.google.common.base.Strings;

public class ResponseResult {
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static <T> ObjectResultUtil<T> STATUS_OK() {
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_SUCCESS);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_OK(T object){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_SUCCESS);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setResult(object);
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_OK(String code,String message,T object) {
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(code);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setResult(object);
		objectResultUtil.setMessage(message);
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_OK(String code,T object) {
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(code);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setResult(object);
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_FAILED(){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_FAILED);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_FAILED, StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_FAILED(String code){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(code);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(code, StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_FAILED(String code,String message){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(code);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(StringUtils.isNotBlank(message)?message:PropertyGetter.getString(code, StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> STATUS_INVALID() {
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_INVALID);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_INVALID,StringUtils.EMPTY));
		return objectResultUtil;
    }

	public static <T> ObjectResultUtil<T> DATA_NOT_FIND() {
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_NOT_FOND);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_NOT_FOND,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> PARAMETER_ERROR(){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.PARAM_ERROR);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.PARAM_ERROR,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> PARAMETER_ERROR( T object){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.PARAM_ERROR);
		objectResultUtil.setResult(object);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.PARAM_ERROR,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> ADVERTISER_REJECT( T object){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.ADVERTISER_REJECT);
		objectResultUtil.setResult(object);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.ADVERTISER_REJECT,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> NO_ADVERTISER( T object){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.NO_ADVERTISER);
		objectResultUtil.setResult(object);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.NO_ADVERTISER,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> NO_ENTITY( T object){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.NO_ENTITY);
		objectResultUtil.setResult(object);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.NO_ENTITY,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> EXCEPTION_ERROR(){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_EXCEPTION);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_EXCEPTION,StringUtils.EMPTY));
		return objectResultUtil;
	}
	
	public static <T> ObjectResultUtil<T> SIGN_ERROR(){
		ObjectResultUtil<T> objectResultUtil = new ObjectResultUtil<>();
		objectResultUtil.setCode(Constants.RESPONSE_FALIED_SIGN_INVALID);
		objectResultUtil.setTimestamp(sdf.format(new Date()));
		objectResultUtil.setMessage(PropertyGetter.getString(Constants.RESPONSE_FALIED_SIGN_INVALID,StringUtils.EMPTY));
		return objectResultUtil;
	}
}
