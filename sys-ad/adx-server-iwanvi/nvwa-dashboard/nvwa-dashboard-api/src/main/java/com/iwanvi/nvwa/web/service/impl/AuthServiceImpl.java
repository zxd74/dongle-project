package com.iwanvi.nvwa.web.service.impl;

import static com.iwanvi.nvwa.web.util.WebConstants.AUTH_HOST;
import static com.iwanvi.nvwa.web.util.WebConstants.AUTH_LOGIN;

import java.net.URLEncoder;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.HttpClientUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.web.service.AuthService;
import com.iwanvi.nvwa.web.util.ExceptionUtils;
import com.iwanvi.nvwa.web.vo.AuthInfo;
import com.iwanvi.nvwa.web.vo.LoginInfo;

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public AuthInfo auth(LoginInfo loginInfo) {
		String userName = loginInfo.getUserName();
		String password = loginInfo.getPassword();

		if (StringUtils.isBlank(loginInfo.getUserName())) {
			ExceptionUtils.throwException("用户名不能为空");
		}

		if (StringUtils.isBlank(loginInfo.getPassword())) {
			ExceptionUtils.throwException("登录密码不能为空");
		}

		String md5pwd = DigestUtils.md5Hex(password);
		try {
			String authServiceUrl = AUTH_HOST + StringUtils.buildString(AUTH_LOGIN,
					StringUtils.isBlank(userName) ? null : URLEncoder.encode(userName, "UTF-8"), md5pwd, 1);
			String response = HttpClientUtils.get(authServiceUrl);
			if (StringUtils.isBlank(response)) {
				ExceptionUtils.throwException("用户认证失败");
			}
			JSONObject authJSON = JSON.parseObject(response);
			return buildAuthInfo(authJSON);
		} catch (Exception ex) {
			ExceptionUtils.throwException(ex.getMessage());
		}
		return null;
	}

	private AuthInfo buildAuthInfo(JSONObject authJSON) {
		String code = authJSON.getString("code");
		if (code == null || !"A000000".equals(code)) {
			throw new ServiceException(StringUtils.trim(authJSON.getString("message")));
		}
		JSONObject dataJSON = authJSON.getJSONObject("data");
		return JSON.parseObject(dataJSON.toString(), AuthInfo.class);
	}
}
