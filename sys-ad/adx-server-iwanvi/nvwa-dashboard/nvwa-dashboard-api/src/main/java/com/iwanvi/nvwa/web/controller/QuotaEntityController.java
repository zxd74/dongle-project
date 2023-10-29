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

import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.QuotaEntity;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.EntityService;
import com.iwanvi.nvwa.web.service.QuotaEntityService;
import com.iwanvi.nvwa.web.util.ObjectResultUtil;
import com.iwanvi.nvwa.web.util.ResponseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "创意报表接口")
@RequestMapping(value = "quotaEntity", method = { RequestMethod.GET, RequestMethod.POST })
public class QuotaEntityController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(QuotaEntityController.class);

	@Autowired
	private QuotaEntityService quotaEntityService;
	@Autowired
	private EntityService entityService;

	@ApiOperation("获取创意时间报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "entId", value = "创意id", dataType = "int", required = true),
			@ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true)
	})
	@RequestMapping("reportByDay")
	public ObjectResultUtil<List<QuotaEntity>> selectReportByDay(HttpServletRequest request, Integer entId,
			String sTime, String eTime) {
		ObjectResultUtil<List<QuotaEntity>> result;
		try {
			Map<String, Object> paramMap = getParamMap(request);
			if (paramMap.get("entId") != null && paramMap.get("sTime") != null && paramMap.get("eTime") != null) {
//				getCreatUserByRequest(request, paramMap);
				Entity entity = entityService.info(Integer.valueOf((String) paramMap.get("entId")));
				List<QuotaEntity> quotaEntities = quotaEntityService.reportByDay(paramMap);
				if (quotaEntities != null) {
					// Map<String, Object> respMap = Maps.newHashMap();
					// respMap.put("entId", paramMap.get("entId"));
					// respMap.put("entName", entity.getEntityName());
					// respMap.put("data", quotaEntities);
					result = ResponseResult.STATUS_OK(quotaEntities);
					logger.info("quotaEntity reportByDay success");
				} else {
					result = ResponseResult.STATUS_FAILED();
					logger.info("quotaEntity reportByDay failed");
				}
			} else {
				result = ResponseResult.PARAMETER_ERROR();
				logger.error("quotaEntity reportByDay error paramJson", JsonUtils.TO_JSON(paramMap));
			}
		} catch (Exception e) {
			result = ResponseResult.EXCEPTION_ERROR();
			logger.error("quotaEntity reportByDay error", e);
		}
		return result;
	}

	@ApiOperation("获取创意时间报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "pid", value = "投放id", dataType = "int", required = true),
			@ApiImplicitParam(name = "putType", value = "投放类型", dataType = "int", required = true),
			@ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true) })
	@RequestMapping("listByPid")
	public ObjectResultUtil<SwaggerPage<List<QuotaEntity>>> listByUid(HttpServletRequest request) {
		ObjectResultUtil<SwaggerPage<List<QuotaEntity>>> result;
		Map<String, Object> paramMap;
		try {
			paramMap = getParamMap(request);
			if (paramMap.get("pid") != null && paramMap.get("putType") != null && paramMap.get("sTime") != null && paramMap.get("eTime") != null) {
				if (paramMap.get("cp") == null) {
					paramMap.put("cp", 1);
				}
				if (paramMap.get("ps") == null) {
					paramMap.put("ps", 15);
				}
//				getCreatUserByRequest(request, paramMap);
				SwaggerPage<List<QuotaEntity>> page = quotaEntityService.listByPid(paramMap);
				result = ResponseResult.STATUS_OK(page);
				logger.info("quotaEntity listByPid success");
			} else {
				result = ResponseResult.PARAMETER_ERROR();
				logger.error("quotaEntity listByPid error paramJson", JsonUtils.TO_JSON(paramMap));
			}
		} catch (Exception e) {
			result = ResponseResult.EXCEPTION_ERROR();
			logger.error("quotaEntity listByPid error", e);
		}
		return result;
	}
}
