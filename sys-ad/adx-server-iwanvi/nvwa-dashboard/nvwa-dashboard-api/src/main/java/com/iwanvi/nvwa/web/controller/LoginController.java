package com.iwanvi.nvwa.web.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.web.util.HttpClients;

@RestController
@RequestMapping("login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("login")
	@ResponseBody
	public String login(HttpServletResponse response, @RequestBody User user) {
		String result;
		if (StringUtils.isNotBlank(user.getUserName()) && StringUtils.isNotBlank(user.getPassword())) {
			try {
				Map<String, Object> loginMap = HttpClients.authorize(user.getUserName(),
						MD5Utils.MD5(StringUtils.trim(user.getPassword())), 1);
				if (loginMap != null && Constants.RESPONSE_SUCCESS.equals(loginMap.get("code"))) {
					Map<String, Object> userMap = JsonUtils.TO_OBJ(loginMap.get("data"), Map.class);
					User u = JsonUtils.TO_OBJ(userMap.get("u"), User.class);
					Cookie cookie = new Cookie("sysCookie",
							StringUtils.concat(userMap.get("t"), Constants.SIGN_UNDERLINE, user.getId()));
					response.addCookie(cookie);
					logger.info("login login success");
					result = JsonUtils.STATUS_OK(loginMap);
				}else {
					return JsonUtils.TO_JSON(loginMap);
				}
			} catch (Exception e) {
				logger.error("login error ", e);
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
			}
		} else {
			logger.error("login login error, userName {}, password {}", user.getUserName(), user.getPassword());
			result = JsonUtils.PARAMETER_ERROR();
		}
		return result;
	}

	@RequestMapping("logout")
	public String logout(@RequestBody String token) {
		String result;
		if (StringUtils.isNotBlank(token)) {
			try {
				Map<String, Object> respMap = HttpClients.logout(token);
				logger.info("login login success");
				result = JsonUtils.TO_JSON(respMap);
			} catch (Exception e) {
				logger.error("logout error ", e);
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
			}
		} else {
			logger.error("login logout error, token {}", token);
			result = JsonUtils.PARAMETER_ERROR();
		}
		return result;
	}
}
