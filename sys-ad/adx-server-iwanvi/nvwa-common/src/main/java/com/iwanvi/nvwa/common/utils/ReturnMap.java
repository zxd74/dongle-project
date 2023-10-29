package com.iwanvi.nvwa.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("rawtypes")
public class ReturnMap {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static Map STATUS_OK(String code, String message, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", message);
		return map;
	}

	public static Map STATUS_OK() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_SUCCESS);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return map;
	}

	public static Map STATUS_OK(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_SUCCESS);
		map.put("data", data);
		map.put("timestamp", sdf.format(new Date()));
//		map.put("message", PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return map;
	}

    public static Map STATUS_OK(Object data, boolean isAdmin) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", Constants.RESPONSE_SUCCESS);
        map.put("data", data);
        map.put("timestamp", sdf.format(new Date()));
//		map.put("message", PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
/*        if(isAdmin){
            return mapper.writeValueAsString(map);
        }*/
        return map;
    }

	public static Map STATUS_OK(String code, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(code, StringUtils.EMPTY));
		return map;
	}

    public static Map STATUS_FAILED() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", Constants.RESPONSE_FAILED);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", PropertyGetter.getString(Constants.RESPONSE_FAILED, StringUtils.EMPTY));
        return map;
    }

    public static Map STATUS_FAILED(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", PropertyGetter.getString(code, StringUtils.EMPTY));
        return map;
    }

    public static Map STATUS_FAILED(String code, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", StringUtils.isBlank(msg) ? PropertyGetter.getString(code, StringUtils.EMPTY) : msg);
        return map;
    }

    public static Map STATUS_INVALID() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", Constants.RESPONSE_INVALID);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", PropertyGetter.getString(Constants.RESPONSE_INVALID, StringUtils.EMPTY));
        return map;
    }

	public static Map DATA_NOT_FIND() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_NOT_FOND);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_NOT_FOND, StringUtils.EMPTY));
		return map;
	}

	public static Map PARAMETER_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_PARAM_INVALID);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_PARAM_INVALID, StringUtils.EMPTY));
		return map;
	}
	
	public static Map PARAMETER_ERROR(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.PARAM_ERROR);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.PARAM_ERROR, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return map;
	}
	public static Map ADVERTISER_REJECT(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.ADVERTISER_REJECT);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.ADVERTISER_REJECT, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return map;
	}

	public static Map NO_ADVERTISER(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.NO_ADVERTISER);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.PARAM_ERROR, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return map;
	}
	
	
	public static Map NO_ENTITY(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.NO_ENTITY);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.PARAM_ERROR, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return map;
	}

	public static Map EXCEPTION_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_EXCEPTION);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_EXCEPTION, StringUtils.EMPTY));
		return map;
	}

	public static Map SIGN_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_FALIED_SIGN_INVALID);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_FALIED_SIGN_INVALID, StringUtils.EMPTY));
		return map;
	}

}
