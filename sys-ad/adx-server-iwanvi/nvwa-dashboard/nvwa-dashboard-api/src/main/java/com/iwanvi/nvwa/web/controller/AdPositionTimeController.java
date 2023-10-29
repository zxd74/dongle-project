package com.iwanvi.nvwa.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.web.service.AdPositionTimeService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;

import io.swagger.annotations.Api;

@Api(tags="广告位排期管理")
@RestController
@RequestMapping("positionTime")
public class AdPositionTimeController {
	
	@Autowired
	private AdPositionTimeService adPositionTimeService;
	
	@GetMapping("scheduling")
	@NvwaRespBody
	public List<Map<String, Object>> scheduling(String adIds, String monthStr, Integer putId) {
		if ( StringUtils.isBlank(adIds) || StringUtils.isBlank(monthStr)) {
			throw new ServiceException("缺少必要参数");
		}
		return adPositionTimeService.scheduling(adIds,monthStr,putId);
	}
}
