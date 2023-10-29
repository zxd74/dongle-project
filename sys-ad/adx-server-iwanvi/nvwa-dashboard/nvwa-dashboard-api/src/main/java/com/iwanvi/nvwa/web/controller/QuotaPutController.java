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
import com.iwanvi.nvwa.dao.model.QuotaPut;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaPutService;
import com.iwanvi.nvwa.web.util.ObjectResultUtil;
import com.iwanvi.nvwa.web.util.ResponseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "投放报表接口")
@RequestMapping(value = "quotaPut", method = { RequestMethod.GET, RequestMethod.POST })
public class QuotaPutController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(QuotaPutController.class);

	@Autowired
	private QuotaPutService quotaPutService;

	@ApiOperation("获取投放时间报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "putId", value = "投放id", dataType = "int", required = true),
			@ApiImplicitParam(name = "putType", value = "投放类型", dataType = "int", required = true),
			@ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true) })
	@RequestMapping("listByDay")
	public ObjectResultUtil<List<QuotaPut>> listByDay(HttpServletRequest request) {
		ObjectResultUtil<List<QuotaPut>> result;
		Map<String, Object> paramMap;
		try {
			paramMap = getParamMap(request);
			if (paramMap.get("sTime") != null && paramMap.get("eTime") != null && paramMap.get("putId") != null && paramMap.get("putType") != null) {
//				getCreatUserByRequest(request, paramMap,Constants.STATE_INVALID);
				List<QuotaPut> quotaPuts = quotaPutService.listByDay(paramMap);
				result = ResponseResult.STATUS_OK(quotaPuts);
				logger.info("quotaPut listByDay success");
			} else {
				result = ResponseResult.PARAMETER_ERROR();
				logger.error("quotaPut listByDay error paramJson", JsonUtils.TO_JSON(paramMap));
			}
		} catch (Exception e) {
			result = ResponseResult.EXCEPTION_ERROR();
			logger.error("quotaPut listByDay error", e);
		}
		return result;
	}

	@ApiOperation("通过oid获取投放报告列表")
	@ApiImplicitParams({@ApiImplicitParam(name = "oid", value = "订单id", dataType = "int", required = false),
			@ApiImplicitParam(name = "pid", value = "计划id", dataType = "int", required = false),
			@ApiImplicitParam(name = "putType", value = "投放类型", dataType = "int", required = true),
			@ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true) })
	@RequestMapping("listByOidOrPid")
	public ObjectResultUtil<SwaggerPage<List<QuotaPut>>> listByPid(HttpServletRequest request) {
		ObjectResultUtil<SwaggerPage<List<QuotaPut>>> result;
		try {
			Map<String, Object> paramMap = getParamMap(request);
			if (paramMap.get("pid") != null || paramMap.get("oid") != null ) {
				if (paramMap.get("sTime") != null && paramMap.get("eTime") != null && paramMap.get("putType") != null) {
					if (paramMap.get("cp") == null) {
						paramMap.put("cp", 1);
					}
					if (paramMap.get("ps") == null) {
						paramMap.put("ps", 15);
					}
//					getCreatUserByRequest(request, paramMap,Constants.STATE_INVALID);
					SwaggerPage<List<QuotaPut>> page = quotaPutService.listByPidOrOid(paramMap);
					result = ResponseResult.STATUS_OK(page);
					logger.info("quotaPut listByOid success");
				} else {
					result = ResponseResult.PARAMETER_ERROR();
					logger.error("quotaPut listByOid error beaacuse param is null", JsonUtils.TO_JSON(paramMap));
				}
			} else {
				result = ResponseResult.PARAMETER_ERROR();
				logger.error("quotaPut listByOid error beacuse param is null , oid {}, pid {}",paramMap.get("oid"),paramMap.get("pid"));
			}
		} catch (Exception e) {
			result = ResponseResult.EXCEPTION_ERROR();
			logger.error("quotaPut listByOid error", e);
		}
		return result;
	}
}
