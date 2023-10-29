package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.QuotaFlow;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.FlowDataService;
import com.iwanvi.nvwa.web.service.FlowSourceService;
import com.iwanvi.nvwa.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flowdata")
public class FlowDataController {

	private static final Logger logger = LoggerFactory.getLogger(FlowDataController.class);

	@Autowired
	private FlowDataService flowDataService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private FlowSourceService flowSourceService;
	
	/**
	 * 流量数据概览
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/getflowalldata")
	public String getFlowAllData(String startDate, String endDate, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				FlowSource fs = new FlowSource();
				if (!userService.isAdmin(uid)) {
					fs.setCreateUser(uid);
				}
				List<FlowSource> fsList = flowSourceService.getFSList(fs);
				List<QuotaFlow> allData = flowDataService.getAllData(fsList ,startDate, endDate);
				result = JsonUtils.STATUS_OK(allData);		
			}  else {
				logger.info("getFlowAllData failed, not login. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("getFlowAllData error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * group by flowsource
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping("/getallflowdatabyid")
	public String getFlowDataByFlowId(String startDate, String endDate, Integer currentPage, Integer pageSize,
			@RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				FlowSource fs = new FlowSource();
				if (!userService.isAdmin(uid)) {
					fs.setCreateUser(uid);
				}
				List<FlowSource> fsList = flowSourceService.getFSList(fs);
				Page<QuotaFlow> list = flowDataService.getFlowDataByFlowId(fsList, startDate, endDate, currentPage, pageSize);
				result = JsonUtils.STATUS_OK(list);	
			}  else {
				logger.info("getFlowAllData failed, not login. ");
				result = JsonUtils.PARAMETER_ERROR();
			}

		} catch (Exception e) {
			logger.error("getflowdatabyid error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * 实时监控 查询单流量源
	 * 
	 * @param startDate
	 * @param endDate
	 * @param mediaId
	 * @return
	 */
	@RequestMapping("/getoneflowdatabyhour")
	public String getOneFlowDataByHour(String startDate, String endDate, Integer mediaId, Integer isAll, String posUuid) {
		String result = StringUtils.EMPTY;
		try {
			if (mediaId == null) {
				throw new ServiceException("请选择流量源");
			}
			List<QuotaFlow> list = flowDataService.getOneFlowDataByHour(isAll,posUuid);
//			List<QuotaFlow> list = flowDataService.getOneFlowDataByHour(isAll);
//			List<QuotaFlow> list = flowDataService.getOneFlowDataByHour(startDate, endDate, mediaId, isAll);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getOneFlowData error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * 实时监控查询多流量源
	 * @param startDate
	 * @param endDate
	 * @param mediaIds
	 * @param type
	 * @param uid
	 * @return
	 */
	
	@RequestMapping("/getflowdatabyhourandflow")
	public String getFlowDataByHourAndFlow(String startDate, String endDate, String mediaIds,
			String type, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isBlank(mediaIds)) {
				throw new ServiceException("请选择流量源");
			}
			if (StringUtils.isBlank(type)) {
				throw new ServiceException("请选择字段");
			}
			Map<String, Object> resultMap = 
					flowDataService.getFlowDataByHourAndFlow(startDate, endDate, mediaIds, uid, type);
			result = JsonUtils.STATUS_OK(resultMap);
		} catch (Exception e) {
			logger.error("getFlowDataByHourAndFlow error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * 流量源报告 - 详情
	 * @param startDate
	 * @param endDate
	 * @param mediaId
	 * @param isAll 
	 * @return
	 */
	@RequestMapping("/getflowdatabyday")
	public String getFlowDataByDay(String startDate, String endDate, String mediaId, boolean isAll) {
		String result = StringUtils.EMPTY;
		try {

			List<QuotaFlow> list = flowDataService.getFlowDataByDay(startDate, endDate, mediaId, isAll);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getFlowDataByHourAndFlow error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/getflowdataonedaybyfs")
	public String getFlowDataOneDayByFs(String startDate, String endDate, String mediaIds) {
		String result = StringUtils.EMPTY;
		try {
			List<QuotaFlow> list = flowDataService.getFlowDataOneDayByFs(startDate, endDate, mediaIds);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getflowdataonedaybyfs error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 流量源报告 - 详情 - 流量分日数据 激活数激活时
	 * @param startDate
	 * @param endDate
	 * @param mediaId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getflowdatapagebyday")
	public String getFlowDataPageByDay(String startDate, String endDate, String mediaId, Integer currentPage, Integer pageSize) {
		String result = StringUtils.EMPTY;
		try {
			Page<QuotaFlow> page = flowDataService.getFlowDataPageByDay(startDate, endDate, mediaId, currentPage, pageSize);
			result = JsonUtils.STATUS_OK(page);
		} catch (Exception e) {
			logger.error("getFlowDataPageByDay error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 流量源报告 图表
	 */
	@RequestMapping("/getfsbyday")
	public String getFsByDay(String startDate, String endDate, String mediaIds) {
		String result = StringUtils.EMPTY;
		try {
			if (StringUtils.isBlank(mediaIds)) {
				throw new ServiceException("请选择流量源");
			}
			List<QuotaFlow> list = flowDataService.getFsByDay(startDate, endDate, mediaIds);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getFsByDay error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 流量源报告 表格
	 */
	@RequestMapping("/getlistbyfs")
	public String getListByFs(String startDate, String endDate, String mediaIds, Integer currentPage, Integer pageSize) {
		String result = StringUtils.EMPTY;
		try {
			Page<QuotaFlow> list = flowDataService.getListByFs(startDate, endDate, mediaIds, currentPage, pageSize);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getListByFs error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
}