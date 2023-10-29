package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;


public interface AdPositionTimeService {

	List<Map<String, Object>> scheduling(String adIds, String monthStr, Integer putId);

}
