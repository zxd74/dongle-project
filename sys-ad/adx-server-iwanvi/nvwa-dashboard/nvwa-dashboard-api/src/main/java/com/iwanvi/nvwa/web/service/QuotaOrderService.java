package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.QuotaOrder;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;

public interface QuotaOrderService {

	List<QuotaOrder> listByDay(Map<String, Object> paramMap);

	SwaggerPage<List<QuotaOrder>> listByInit(Map<String, Object> paramMap);

}
