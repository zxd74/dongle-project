package com.iwanvi.nvwa.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.QuotaPlan;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaPlanService;
import com.iwanvi.nvwa.web.util.ObjectResultUtil;
import com.iwanvi.nvwa.web.util.ResponseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description = "计划报告")
@RestController
@RequestMapping(value = "quotaPlan", method = { RequestMethod.GET, RequestMethod.POST })
public class QuotaPlanController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(QuotaPlanController.class);

	@Autowired
	private QuotaPlanService quotaPlanService;

	@ApiOperation("根据日期获取计划报告接口")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pid", value = "计划id", dataType = "int", required = true),
			@ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true), })
	@RequestMapping("listByDay")
	public ObjectResultUtil<List<QuotaPlan>> listByDay(HttpServletRequest request) {
		ObjectResultUtil<List<QuotaPlan>> resultUtil;
		try {
			Map<String, Object> paramMap = getParamMap(request);
			if (paramMap.get("id") != null && paramMap.get("sTime") != null && paramMap.get("eTime") != null) {
//				getCreatUserByRequest(request, paramMap,Constants.STATE_INVALID);
				List<QuotaPlan> list = quotaPlanService.listByDay(paramMap);
				resultUtil = ResponseResult.STATUS_OK(list);
				logger.info("quotaPlan listByDay success");
			} else {
				logger.error("quotaPlan listByDay error beacuse param have null, paramStr {}",
						JsonUtils.TO_JSON(paramMap));
				resultUtil = ResponseResult.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("quotaPlan listByDay error", e);
			resultUtil = ResponseResult.EXCEPTION_ERROR();
		}
		return resultUtil;
	}

	@ApiOperation("初始获取计划报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true) })
	@RequestMapping("listByInit")
	public ObjectResultUtil<SwaggerPage<List<QuotaPlan>>> listByInit(HttpServletRequest request) {
		ObjectResultUtil<SwaggerPage<List<QuotaPlan>>> result;
		try {
			Map<String, Object> paramMap = getParamMap(request);
			if (paramMap.get("sTime") != null && paramMap.get("eTime") != null) {
				if (paramMap.get("cp") == null) {
					paramMap.put("cp", 1);
				}
				if (paramMap.get("ps") == null) {
					paramMap.put("ps", 15);
				}
				getCreatUserByRequest(request, paramMap);
				SwaggerPage<List<QuotaPlan>> page = quotaPlanService.listByInit(paramMap);
				try {
					result = ResponseResult.STATUS_OK(page);
					logger.info("quotaOrder listByInit success");
				} catch (Exception e) {
					result = ResponseResult.EXCEPTION_ERROR();
					logger.error("quotaOrder listByInit error",e);
				}
			} else {
				result = ResponseResult.PARAMETER_ERROR();
				logger.error("quotaOrder listByInit error paramJson", JsonUtils.TO_JSON(paramMap));
			}
		} catch (Exception e) {
			result = ResponseResult.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
			logger.error("quotaOrder listByInit error", e);
		}
		return result;
	}
}
