package com.iwanvi.nvwa.web.interceptor;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iwanvi.nvwa.common.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AuthGroupMapper;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.web.util.NvwaResp;
import com.iwanvi.nvwa.web.vo.AuthInfo;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private AuthGroupMapper authGroupMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String method = request.getMethod();
		if ("options".equals(method.toLowerCase())) {
			return true;
		}
		String token = request.getHeader("token");
		if (StringUtils.isBlank(token)) {
			authFailed(response, "token为空");
			return false;
		}

		try {
			String authData = redisDao.get(StringUtils.buildString(Constants.TOKEN_KEY, token));
			AuthInfo authInfo = JSON.parseObject(authData, AuthInfo.class);
			if (authInfo == null) {
				authFailed(response, "获取用户授权信息失败");
				return false;
			}
			User user = authInfo.getU();
			request.setAttribute("uid", user.getId());
			request.setAttribute("type", authGroupMapper.selectByPrimaryKey(user.getType()).getType());
			return true;
		} catch (Exception ex) {
			authFailed(response, "认证失败");
		}
		return false;
	}

	private void authFailed(HttpServletResponse response, String msg) {
		NvwaResp nvwaResp = new NvwaResp("E000000", "未授权的访问", Collections.EMPTY_MAP);
		response.setStatus(403);
		try {
			response.getWriter().write(JSON.toJSONString(nvwaResp));
		} catch (Exception ex) {
			// DO NOTHING
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
