package com.iwanvi.nvwa.pixel.connector.common.service;

import java.util.List;
import java.util.Map;

import redis.clients.jedis.Pipeline;

public interface PixelService {

	/**
	 * 上报客户
	 * monitorType 监测类型，1：曝光，2：点击
	 */
	void reportCustomer(Map<String, Object> paramMap, int monitorType);
	
	void buildPipelineIncr(Pipeline pipeline, String templeteKey, 
			String quota, Object id, String day, String hour, Object incrValueTemp);
	/**
	 * monitorType 监测类型，1：曝光，2：点击
	 */
	void count(Map<String, Object> map, int monitorType);
	
	void redisIncr(String key);
	
	List<String> getQueryQuotaList(String logStore, String distinctItem, int from, int to);
}
