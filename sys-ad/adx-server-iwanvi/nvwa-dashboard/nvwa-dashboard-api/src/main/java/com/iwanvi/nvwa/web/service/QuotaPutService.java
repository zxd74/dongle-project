package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.QuotaPut;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;

public interface QuotaPutService {

	List<QuotaPut> listByDay(Map<String, Object> paramMap);

	SwaggerPage<List<QuotaPut>> listByPidOrOid(Map<String, Object> paramMap);

}
