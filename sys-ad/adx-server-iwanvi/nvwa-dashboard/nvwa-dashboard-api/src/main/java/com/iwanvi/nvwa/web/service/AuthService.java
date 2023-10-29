package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.web.vo.AuthInfo;
import com.iwanvi.nvwa.web.vo.LoginInfo;

/**
 * 
 * @author wangwp
 */
public interface AuthService {
	AuthInfo auth(LoginInfo loginInfo);
}
