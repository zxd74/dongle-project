package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.SdkAllotment;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.ScheduleForm;
import com.iwanvi.nvwa.web.vo.SdkAllotmentForm;

public interface SdkAllotmentService {

	/**
	 * 列表
	 * @param name
	 * @param platformId
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	Page<SdkAllotmentForm> listForPage(String name, 
			Integer platformId, Integer type, Integer status, Integer pageNum, Integer pageSize);
	
	/**
	 * 包段排期
	 * @param posIds
	 * @param month
	 * @param scheduleId
	 * @param type
	 * @return
	 */
	String getSchedule(String posIds, Integer month, Integer scheduleId, Integer type);
	
	/**
	 * APP/PLATFORM 列表
	 * @return
	 */
	String prepare();
	
	/**
	 * 广告位列表
	 * @param appId
	 * @param platformId
	 * @return
	 */
	String adPostion(Integer appId, Integer platformId);
	
	/**
	 * 广告位流量分配信息
	 * @param allotId
	 * @return
	 */
	String adpStatus(Integer allotId);

	/**
	 * 更新包段信息
	 * @param sdkAllotmentForm
	 * @return
	 */
	String update(SdkAllotmentForm sdkAllotmentForm);
	
	/**
	 * 更新包量信息
	 * @param paramMap
	 * @return
	 */
	String updateFixed(Map<String, Object> paramMap);

	/**
	 * 新增包段
	 */
	String add(SdkAllotment sdkAllotment, List<ScheduleForm> schedules);
	
	/**
	 * 新增包量
	 * @param paramMap
	 * @param uid
	 * @return
	 */
	String addFixed(Map<String, Object> paramMap, Integer uid);

	/**
	 * 包段信息
	 * @param id
	 * @return
	 */
	SdkAllotmentForm info(Integer id);

	/**
	 * 包量信息
	 * @param id
	 * @return
	 */
	Map<String, Object> get(Integer id);

	/**
	 * 更新状态
	 * @param id
	 * @param status
	 * @return
	 */
	String status(Integer id, Integer status);
	
}
