package com.iwanvi.nvwa.openapi.dsp.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.google.common.collect.Maps;

public class JsonUtils {

	protected static final SerializerFeature[] features = { SerializerFeature.SortField,
			SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat };


	public static String STATUS_OK() {
		return STATUS_OK(null);
	}

	public static String STATUS_OK(String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", com.iwanvi.nvwa.common.utils.Constants.RESPONSE_SUCCESS);
		map.put("timestamp", DateUtils.formatPure(new Date()));
		map.put("message", PropertyGetter.getString(com.iwanvi.nvwa.common.utils.Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return handleResult(map, callback);
	}

	public static String STATUS_OK(Object data, String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", com.iwanvi.nvwa.common.utils.Constants.RESPONSE_SUCCESS);
		map.put("data", data);
		map.put("timestamp", DateUtils.formatPure(new Date()));
		return handleResult(map, callback);
	}
	
	public static String DATA_NOT_FIND() {
		return PARAMETER_ERROR(null);
	}
	
	public static String DATA_NOT_FIND(String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", com.iwanvi.nvwa.common.utils.Constants.RESPONSE_NOT_FOND);
		map.put("timestamp", DateUtils.formatPure(new Date()));
		map.put("message", PropertyGetter.getString(com.iwanvi.nvwa.common.utils.Constants.RESPONSE_NOT_FOND, StringUtils.EMPTY));
		return handleResult(map, callback);
	}
	
	public static String PARAMETER_ERROR() {
		return PARAMETER_ERROR(null);
	}

	public static String PARAMETER_ERROR(String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", com.iwanvi.nvwa.common.utils.Constants.RESPONSE_PARAM_INVALID);
		map.put("timestamp", DateUtils.formatPure(new Date()));
		map.put("message", PropertyGetter.getString(com.iwanvi.nvwa.common.utils.Constants.RESPONSE_PARAM_INVALID, StringUtils.EMPTY));
		return handleResult(map, callback);
	}

	public static String EXCEPTION_ERROR() {
		return EXCEPTION_ERROR(null);
	}

	public static String EXCEPTION_ERROR(String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", com.iwanvi.nvwa.common.utils.Constants.RESPONSE_EXCEPTION);
		map.put("timestamp", DateUtils.formatPure(new Date()));
		map.put("message", "");
		return handleResult(map, callback);
	}


	public static String STATUS_FAILED() {
		return STATUS_FAILED(null);
	}

	public static String STATUS_FAILED(String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", com.iwanvi.nvwa.common.utils.Constants.RESPONSE_FAILED);
		map.put("timestamp", DateUtils.formatPure(new Date()));
		map.put("message", PropertyGetter.getString(com.iwanvi.nvwa.common.utils.Constants.RESPONSE_FAILED, StringUtils.EMPTY));
		return handleResult(map, callback);
	}


	public static String TO_JSON(Object data) {
		return JSON.toJSONString(data, SerializerFeature.WriteNullListAsEmpty);
	}

	public static Object TO_OBJ(String source, Class<?> clazz) {
		return JSON.parseObject(source, clazz);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseMap(String source) {
		Map<String, Object> map = null;
		try {
			map = (Map<String, Object>) TO_OBJ(source, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> parse2StringMap(String source) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			map = (Map<String, String>) TO_OBJ(source, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String handleResult(Object data, String callback) {
		String json = JSON.toJSONString(data, features);
		if (StringUtils.isNotBlank(callback)) {
			return StringUtils.concat(callback, "(", json, ")");
		} else {
			return json;
		}
	}

	public static String gdtOK() {
		Map<String, Object> map = Maps.newHashMap();
		map.put("ret", 0);
		map.put("msg", "OK");
		return JSON.toJSONString(map);
	}

	public static String gdtFailed(){
		Map<String, Object> map = Maps.newHashMap();
		map.put("ret", 1);
		map.put("msg", "OK");
		return JSON.toJSONString(map);
	}
	
	public static void main(String[] args) {
		// Map<String, Object> map = new HashMap<String, Object>();
		//
		// ArrayList<String> list = new ArrayList<String>();
		// list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
		// list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
		// list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
		//
		// map.put("list", list);
		// map.put("pageSize", 3);
		// map.put("pageTotal", 5);
		//
		// String string = STATUS_OK(map);
		// System.out.println("string=" + string);

		boolean isTrue = false;
		isTrue |= true;
		System.out.println(isTrue);
	}
}
