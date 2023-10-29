/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.web.vo;

import java.util.List;

import com.iwanvi.nvwa.dao.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 
 * @author wangwp
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthInfo {
	private User u;
	private String t;
	private List<MenuVo> a;

	public AuthInfo() {
	}

	public AuthInfo(User user, List<MenuVo> auths, String token) {
		this.u = user;
		this.t = token;
		this.a = auths;
	}

	public User getU() {
		return u;
	}

	public void setU(User user) {
		this.u = user;
	}

	public String getT() {
		return t;
	}

	public void setT(String token) {
		this.t = token;
	}

	public List<MenuVo> getA() {
		if (a == null || a.isEmpty())
			return null;
		return a;
	}

	public void setA(List<MenuVo> auths) {
		if (auths != null && auths.isEmpty()) {
			this.a = null;
		}
		this.a = auths;
	}
}
