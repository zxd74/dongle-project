package com.iwanvi.nvwa.pixel.connector.crontab.service;

import java.util.Map;

public interface TaskService {
	
	void quotaTask(Map<String, Object> paramMap);
	
	void quotaTaskForDay();
}
