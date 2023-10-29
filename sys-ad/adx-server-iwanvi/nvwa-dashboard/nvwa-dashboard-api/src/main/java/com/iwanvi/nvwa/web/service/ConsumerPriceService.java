package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;


public interface ConsumerPriceService {

	List<Map<String, Object>>  get(Integer id);

	void set(Map<String, Object> params);

}
