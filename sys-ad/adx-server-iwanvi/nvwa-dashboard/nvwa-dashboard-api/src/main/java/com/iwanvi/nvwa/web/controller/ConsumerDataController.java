package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.QuotaPlatform;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.ConsumerDataService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/consumerdata")
public class ConsumerDataController {

	private static final Logger logger = LoggerFactory.getLogger(ConsumerDataController.class);

	@Autowired
	private ConsumerDataService consumerDataService;

	/**
	 * 平台概览
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/all")
	public String consumerData(String startDate, String endDate) {
		String result = StringUtils.EMPTY;
		try {
			List<QuotaPlatform> list = consumerDataService.consumerData(startDate, endDate);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("consumerData error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * 平台实时监控
	 * 
	 * @param startDate
	 * @param endDate
	 * @param mediaId
	 * @param isAll
	 * @return
	 */
	@RequestMapping("/getonefcbyhour")
	public String getOneFcByhour(String startDate, String endDate, String mediaId, Integer isAll) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isBlank(mediaId)) {
				throw new ServiceException("请选择平台");
			}
			List<QuotaPlatform> list = consumerDataService.getOneFcByhour(startDate, endDate, mediaId, isAll);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getOneFcByhour error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getplatformsdatabyhour")
	public String getPlatformsDataByHour(String startDate, String endDate, String mediaIds, String type) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isBlank(mediaIds)) {
				throw new ServiceException("请选择平台");
			}
			Map<String, Object> resultMap = consumerDataService.getPlatformsDataByHour(startDate, endDate, mediaIds,
					type);
			result = JsonUtils.STATUS_OK(resultMap);
		} catch (Exception e) {
			logger.error("getPlatformsDataByHour error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getPlatformsdatatoday")
	public String getPlatformsDataToday(String startDate, String endDate, String mediaIds) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isBlank(mediaIds)) {
				throw new ServiceException("请选择平台");
			}
			List<QuotaPlatform> list = consumerDataService.getPlatformsDataToday(startDate, endDate, mediaIds);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getPlatformsDataToday error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * 平台报告
	 */
	@NvwaRespBody
	@RequestMapping("/getoneplatformdatabyday")
	public List<QuotaPlatform> getOnePlatformDataByDay(String startDate, String endDate, String mediaId,
			Integer isAll) {
		if (isAll == null || isAll == 0)
			return summaryDspDataByDay(startDate, endDate, mediaId);
		return summaryDspData(startDate, endDate, mediaId);
	}

	@NvwaRespBody
	@RequestMapping("/summary/all")
	public List<QuotaPlatform> summaryDspData(String startDate, String endDate, @RequestParam("mediaId") String dspId) {
		List<QuotaPlatform> summaryDspDataByDays = consumerDataService.summaryDspDataByDay(startDate, endDate, dspId);
		List<QuotaPlatform> summaryDspDataAll = new ArrayList<>();

		QuotaPlatform summary = new QuotaPlatform();
		summaryDspDataAll.add(summary);

		summaryDspDataByDays.forEach(q -> {
			summary.setReq(summary.getReq() + q.getReq());
			summary.setBid(summary.getBid() + q.getBid());
			summary.setExp(summary.getExp() + q.getExp());
			summary.setClk(summary.getClk() + q.getClk());
			summary.setInvest(summary.getInvest() + q.getInvest());
            summary.setTimeout(summary.getTimeout() + q.getTimeout());
            summary.setNobid(summary.getNobid() + q.getNobid());
            summary.setLower(summary.getLower() + q.getLower());
            summary.setOverqps(summary.getOverqps() + q.getOverqps());
            summary.setError(summary.getError() + q.getError());
            summary.setWin(summary.getWin() + q.getWin());
        });
		return summaryDspDataAll;
	}

	@NvwaRespBody
	@RequestMapping("/summary/days")
	public List<QuotaPlatform> summaryDspDataByDay(String startDate, String endDate,
			@RequestParam("mediaId") String dspId) {
		return consumerDataService.summaryDspDataByDay(startDate, endDate, dspId);
	}

	@NvwaRespBody
	@RequestMapping("/getoneplatformpagedatabyday")
	public Page<QuotaPlatform> getOnePlatformPageDataByDay(String startDate, String endDate, String mediaId,
			Integer currentPage, Integer pageSize) {

		List<QuotaPlatform> summaryDspDataByDays = consumerDataService.summaryDspDataByDay(startDate, endDate, mediaId);
		Page<QuotaPlatform> pageResult = new Page<>(summaryDspDataByDays.size());

		pageResult.bindData(summaryDspDataByDays);
		return pageResult;
	}

	@NvwaRespBody
    @RequestMapping("/sumByDay")
	public List<QuotaPlatform> sumByDay(String pid, String appids, String adpids, Integer sDate, Integer eDate) {
	    return consumerDataService.sumByDay(pid, appids, adpids, sDate, eDate);
    }

	@NvwaRespBody
    @RequestMapping("/sumByDayWithGroup")
	public List<QuotaPlatform> sumByDayWithGroup(String pids, String appids, String adpids, String group,
                                                 Integer sDate, Integer eDate) {
	    return consumerDataService.sumByDayWithGroup(pids, appids, adpids, group, sDate, eDate);
    }

	@NvwaRespBody
    @RequestMapping("/detailReport")
	public List<QuotaPlatform> detailReport(String id, String pids, String appids, String adpids, String group,
                                            Integer sDate, Integer eDate) {
	    return consumerDataService.detailReport(id, pids, appids, adpids, group, sDate, eDate);
    }

	@NvwaRespBody
    @RequestMapping("/sumItemByDay")
	public Map<String, Object> sumItemByDay(String pids, String appids, String adpids, Integer sDate, Integer eDate,
                                            String item, String group) {
	    return consumerDataService.sumItemByDay(pids, appids, adpids, sDate, eDate, item, group);
    }
}
