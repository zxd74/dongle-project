/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.web.service.AuthService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.ExceptionUtils;
import com.iwanvi.nvwa.web.util.HttpClients;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.AuthInfo;
import com.iwanvi.nvwa.web.vo.LoginInfo;
import com.google.common.base.Preconditions;

/**
 * 
 * @author wangwp
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/signin")
	@NvwaRespBody
	public AuthInfo signin(@RequestBody LoginInfo loginInfo) throws Exception {
		Preconditions.checkNotNull(loginInfo);
		AuthInfo authInfo = authService.auth(loginInfo);
		if (authInfo == null) {
			ExceptionUtils.throwException("用户认证异常");
		}
		return authInfo;
	}

	// 使用NvwaRespBody会自动构造统一响应格式，controller方法直接返回对象即可
	@NvwaRespBody
	@GetMapping("/signout")
	public void signout(@RequestParam("token") String token) {
		if (StringUtils.isBlank(token))
			ExceptionUtils.throwException("token不能为空");

		try {
			HttpClients.logout(token);
		} catch (Exception ex) {
			throw new ControllerException(ex);
		}
	}
}
