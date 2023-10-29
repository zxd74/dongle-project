package com.iwanvi.nvwa.openapi.dsp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.iwanvi.nvwa.common.utils.StringUtils;

public class BrokerUtils {

	private static final int RESPONSE_SUCCESS = 0; // 成功
	private static final int EXCEPTION_ERROR = -1; // 服务器程序异常
	private static final int PARAMETER_ERROR = 1; // 参数错误
	private static final int DATA_NOT_FIND = 4; // 没有数据
	private static final int ILLEGAL_REPORT = 14; // 非法上报

	public static final String KEY_REDIS_OVER_NOTIFY_TEMP = "over:notify:temp";
	public static final String KEY_REDIS_OVER_NOTIFY = "over:notify";
	
	public static final String VALUE_REDIS_OVER_BALANCE_AGENT = "agent_{0}_{1}_{2}";
	public static final String VALUE_REDIS_OVER_BALANCE_USER = "user_{0}_{1}_{2}";
	public static final String VALUE_REDIS_OVER_LIMIT_PLAN = "plan_{0}_{1}_{2}_{3}";
	public static final String VALUE_REDIS_OVER_LIMIT_UNIT = "unit_{0}_{1}_{2}_{3}";
	
	public static final String URI_ADIS_DISPLAY = "/sdk/rpt?did={0}&appId={1}&prc={2}&p={3}";
	public static final String URI_ADIS_CLICK = "/app/ad/click?did={0}&appId={1}&ext={2}&p={3}";
	public static final String URI_ADIS_DISPLAY_DSP = "/sdk/dsp/exp?dspId={0}&prc={1}&p={2}";
	public static final String URI_ADIS_CLICK_DSP = "/app/dsp/clk?dspId={0}&p={1}";
	
	public static final String JSON_MAP_RET = "ret";
	public static final String JSON_MAP_MSG = "msg";
	public static final String JSON_MAP_DATA = "data";
	public static final String JSON_MAP_LIST = "list";
	
	protected static final SerializerFeature[] features = { SerializerFeature.SortField,
			SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty,
			SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty,
			SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteDateUseDateFormat };

	public static String STATUS_OK() {
		return STATUS_OK(null);
	}
	
	public static String STATUS_OK(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(JSON_MAP_RET, RESPONSE_SUCCESS);
		map.put(JSON_MAP_DATA, obj);
		map.put(JSON_MAP_MSG, StringUtils.EMPTY);
		return JSON.toJSONString(map, SerializerFeature.WriteNullListAsEmpty);
	}

	public static String EXCEPTION_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(JSON_MAP_RET, EXCEPTION_ERROR);
		map.put(JSON_MAP_DATA, null);
		return JSON.toJSONString(map, SerializerFeature.WriteNullListAsEmpty);
	}

	public static String PARAMETER_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(JSON_MAP_RET, PARAMETER_ERROR);
		map.put(JSON_MAP_DATA, null);
		return JSON.toJSONString(map, SerializerFeature.WriteNullListAsEmpty);
	}
	
	public static String ILLEGAL_REPORT() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(JSON_MAP_RET, ILLEGAL_REPORT);
		map.put(JSON_MAP_DATA, null);
		return JSON.toJSONString(map, SerializerFeature.WriteNullListAsEmpty);
	}

	public static String DATA_NOT_FIND() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(JSON_MAP_RET, DATA_NOT_FIND);
		map.put(JSON_MAP_DATA, null);
		return JSON.toJSONString(map, SerializerFeature.WriteNullListAsEmpty);
	}

	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		Map<String, Object> childMap = new HashMap<String, Object>();
		childMap.put("ret", 0);
		childMap.put("msg", "");
		
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> childListMap = new HashMap<String, Object>();
		childListMap.put("disurl", 0);
		childListMap.put("clkurl", "");
		childListMap.put("cid", "");
		list.add(childListMap);
		childMap.put("list", list);

		map.put("20012212", childMap);
		String string = STATUS_OK(map);
		System.out.println(string);
	}
}
