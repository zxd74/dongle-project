package com.iwanvi.nvwa.crontab.service;


public interface CrontabService {

	/**
	 * 全量同步
	 */
	//@Scheduled(cron = "0 2 0 * * ?")
	void pub4Entire();

	void pub4Entire(boolean isScheduledTask);

	/**
	 * 增量同步
	 * @param id
	 */
	void pub4Incremental(Integer id);
	
	/**
	 * 扫描单元状态
	 */
	void scanAdPutRunState();
	
	
	/**
	 * 扫描订单投放状态
	 */
	void scanAdOrderPutRunState();

	String getAdpostionPrice(Integer id);
}
