package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.QuotaPlan;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;

public interface QuotaPlanService {

	List<QuotaPlan> listByDay(Map<String, Object> paramMap);

	SwaggerPage<List<QuotaPlan>> listByInit(Map<String, Object> paramMap);

}
