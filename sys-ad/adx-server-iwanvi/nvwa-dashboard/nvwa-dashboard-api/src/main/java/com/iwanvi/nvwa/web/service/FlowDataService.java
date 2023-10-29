package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.QuotaFlow;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface FlowDataService {

	List<QuotaFlow> getAllData(List<FlowSource> fsList, String starDate, String endDate);

	List<QuotaFlow> getOneFlowDataByHour(String startDate, String endDate, Integer mediaId, Integer isAll);

	List<QuotaFlow> getOneFlowDataByHour(Integer isAll);

	List<QuotaFlow> getOneFlowDataByHour(Integer isAll, String posUuid);

	Map<String, Object> getFlowDataByHourAndFlow(String startDate, String endDate, String mediaIds, Integer uid, String type);

	List<QuotaFlow> getFlowDataByDay(String startDate, String endDate, String mediaId, boolean isAll);

	Page<QuotaFlow> getFlowDataByFlowId(List<FlowSource> fsList, String startDate, String endDate, Integer currentPage, Integer pageSize);

	Page<QuotaFlow> getFlowDataPageByDay(String startDate, String endDate, String mediaId, Integer currentPage, Integer pageSize);

	List<QuotaFlow> getFlowDataOneDayByFs(String startDate, String endDate, String mediaId);

	List<QuotaFlow> getFsByDay(String startDate, String endDate, String mediaIds);

	Page<QuotaFlow> getListByFs(String startDate, String endDate, String mediaIds, Integer currentPage, Integer pageSize);


}
