package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.QuotaEntity;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;

public interface QuotaEntityService {

	List<QuotaEntity> reportByDay(Map<String, Object> paramMap);

	SwaggerPage<List<QuotaEntity>> listByPid(Map<String, Object> paramMap);

}
