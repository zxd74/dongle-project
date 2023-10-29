/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.nvwa.proto.CommonProto.MapEntry;

/**
 * 
 * @author wangwp
 */
public class ProtobufUtils {

	public static Map<String, String> convertListToMap(List<MapEntry> list) {
		Map<String, String> map = new HashMap<>();

		if (list == null || list.isEmpty()) {
			return map;
		}

		for (MapEntry mapEntry : list) {
			map.put(mapEntry.getKey(), mapEntry.getValue());
		}
		return map;
	}

	/**
	 * 判断两个字符串逗号分隔后是否有公共部分
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean ArrayCommonElement(String str1, String str2) {

		if (StringUtils.isBlank(str1) || StringUtils.isBlank(str2)) {
			return false;
		}

		String[] array1 = str1.split(Constants.SIGN_COMMA);
		String[] array2 = str2.split(Constants.SIGN_COMMA);
		for (int i = 0; i < array1.length; i++) {
			for (int j = 0; j < array2.length; j++) {
				if (array1[i].equals(array2[j])) {
					return true;
				}
			}
		}
		return false;
	}
}
