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
import com.iwanvi.nvwa.dao.model.QuotaOrder;
import com.iwanvi.nvwa.dao.model.support.SwaggerPage;
import com.iwanvi.nvwa.web.service.QuotaOrderService;
import com.iwanvi.nvwa.web.util.ObjectResultUtil;
import com.iwanvi.nvwa.web.util.ResponseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "订单报表接口")
@RequestMapping(value = "quotaOrder", method = { RequestMethod.GET, RequestMethod.POST })
public class QuotaOrderController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(QuotaOrderController.class);

	@Autowired
	private QuotaOrderService quotaOrderService;

	@ApiOperation("获取订单时间报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "订单id", dataType = "int", required = true),
			@ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true) })
	@RequestMapping("listByDay")
	public ObjectResultUtil<List<QuotaOrder>> listByDay(HttpServletRequest request) {
		ObjectResultUtil<List<QuotaOrder>> result;
		try {
			Map<String, Object> paramMap = getParamMap(request);
			if (paramMap.get("id") != null && paramMap.get("sTime") != null && paramMap.get("eTime") != null) {
				getCreatUserByRequest(request, paramMap);
				List<QuotaOrder> quotaOrders = quotaOrderService.listByDay(paramMap);
				try {
					result = ResponseResult.STATUS_OK(quotaOrders);
					logger.info("quotaOrder listByDay success");
				} catch (Exception e) {
					result = ResponseResult.STATUS_FAILED();
					logger.error("quotaOrder listByDay error");
				}
			} else {
				result = ResponseResult.PARAMETER_ERROR();
				logger.error("quotaOrder listByDay error paramJson", JsonUtils.TO_JSON(paramMap));
			}
		} catch (Exception e) {
			result = ResponseResult.EXCEPTION_ERROR();
			logger.error("quotaOrder listByDay error", e);
		}
		return result;
	}

	@ApiOperation("获取订单id报告列表")
	@ApiImplicitParams({ @ApiImplicitParam(name = "sTime", value = "开始时间", dataType = "string", required = true),
			@ApiImplicitParam(name = "eTime", value = "结束时间", dataType = "string", required = true) })
	@RequestMapping("listByInit")
	public ObjectResultUtil<SwaggerPage<List<QuotaOrder>>> listByInit(HttpServletRequest request) {
		ObjectResultUtil<SwaggerPage<List<QuotaOrder>>> result;
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
				SwaggerPage<List<QuotaOrder>> page = quotaOrderService.listByInit(paramMap);
				try {
					result = ResponseResult.STATUS_OK(page);
					logger.info("quotaOrder listByInit success");
				} catch (Exception e) {
					result = ResponseResult.STATUS_FAILED();
					logger.error("quotaOrder listByInit error");
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
