package com.iwanvi.nvwa.web.util;

import java.net.URLEncoder;
import java.util.Map;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.HttpClientUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;

public class HttpClients {

	public static Map<String, Object> authorize(String uname, String password, Integer paltform) {
		try {
			String url = WebConstants.AUTH_HOST + StringUtils.buildString(WebConstants.AUTH_LOGIN,
					StringUtils.isBlank(uname) ? null : URLEncoder.encode(uname, "UTF-8"), password,
					paltform == null ? 1 : paltform);
			String resp = HttpClientUtils.get(url);
			if (StringUtils.isNotBlank(resp)) {
				Map<String, Object> respMap = JsonUtils.parse2Map(resp);
				return respMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> logout(String token) {
		try {
			String url = WebConstants.AUTH_HOST + StringUtils.buildString(WebConstants.AUTH_LOGOUT, token);
			String resp = HttpClientUtils.get(url);
			if (StringUtils.isNotBlank(resp)) {
				Map<String, Object> respMap = JsonUtils.parse2Map(resp);
				return respMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
