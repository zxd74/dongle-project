package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.QuotaPlatform;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface ConsumerDataService {

	List<QuotaPlatform> consumerData(String startDate, String endDate);

	List<QuotaPlatform> getOneFcByhour(String startDate, String endDate, String mediaId, Integer isAll);

	List<QuotaPlatform> getOnePlatformDataByDay(String startDate, String endDate, String mediaId, Integer isAll);

	Page<QuotaPlatform> getOnePlatformPageDataByDay(String startDate, String endDate, String mediaId,
			Integer currentPage, Integer pageSize);

	Map<String, Object> getPlatformsDataByHour(String startDate, String endDate, String mediaIds, String type);

	List<QuotaPlatform> getPlatformsDataToday(String startDate, String endDate, String mediaId);

	List<QuotaPlatform> summaryDspDataByDay(String startDate, String endDate, String dspId);

	List<QuotaPlatform> sumByDay(String pid, String appids, String adpids, Integer sDate, Integer eDate);

	List<QuotaPlatform> sumByDayWithGroup(String pids, String appids, String adpids, String group,
                                          Integer sDate, Integer eDate);

	List<QuotaPlatform> detailReport(String id, String pids, String appids, String adpids, String group,
                                          Integer sDate, Integer eDate);

	Map<String, Object> sumItemByDay(String pids, String appids, String adpids, Integer sDate, Integer eDate,
                                     String item, String group);

}
