/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.helper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample;

import ai.houyi.dorado.rest.annotation.FilterPath;
import ai.houyi.dorado.rest.http.Filter;
import ai.houyi.dorado.rest.http.HttpRequest;
import ai.houyi.dorado.rest.http.HttpResponse;
import ai.houyi.dorado.rest.util.StringUtils;

/**
 * API鉴权过滤器
 * 
 * @author wangwp
 */
@FilterPath(include = "/adx/**")
@Component
public class AuthFilter implements Filter {
	@Autowired
	private FlowConsumerMapper dspMapper;

	@Override
	public boolean preFilter(HttpRequest request, HttpResponse response) {
		String dspToken = request.getParameter("token");
		if (StringUtils.isBlank(dspToken)) {
			dspToken = request.getHeader("Authentication");
		}
		if(StringUtils.isBlank(dspToken)) {
			dspToken = request.getHeader("Authorization");
		}
		
		if(StringUtils.isBlank(dspToken)) {
			dspToken = request.getHeader("authorization");
		}
		
		if (StringUtils.isBlank(dspToken)) {
			response.setStatus(HttpResponse.SC_FORBIDDEN);
			return false;
		}

		FlowConsumerExample example = new FlowConsumerExample();
		example.createCriteria().andTokenEqualTo(dspToken);

		long dspCount = dspMapper.countByExample(example);
		if (dspCount == 0) {
			response.setStatus(HttpResponse.SC_FORBIDDEN);
			return false;
		}

		List<FlowConsumer> dspList = dspMapper.selectByExample(example);
		FlowConsumer dsp = dspList.get(0);
		
		AuthContext.get().setDsp(dsp);
		return true;
	}
}
