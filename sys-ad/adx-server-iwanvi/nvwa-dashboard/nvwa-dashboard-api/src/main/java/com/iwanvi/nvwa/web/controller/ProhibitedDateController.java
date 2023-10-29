package com.iwanvi.nvwa.web.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.web.service.ProhibitedDateService;

@RestController
@RequestMapping("prohibite-date")
public class ProhibitedDateController {

	private static final Logger logger = LoggerFactory.getLogger(ProhibitedDateController.class);
	
	@Autowired
	private ProhibitedDateService prohibitedDateService;
	
	@GetMapping("get")
	public String getProhibitedDateByMonth(String month) {
		String result = StringUtils.EMPTY;
		try {
			Map<String, List<Integer>> dates = prohibitedDateService.getProhibitedDateByMonth(month);
			result = JsonUtils.STATUS_OK(dates);
		} catch (Exception e) {
			logger.error("getProhibitedDateByMonth error . ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("set")
	public String setProhibitedDateByMonth(@RequestBody List<Map<String, List<Integer>>> list) {
		String result = StringUtils.EMPTY;
		try {
			prohibitedDateService.setProhibitedDateByMonth(list);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.error("setProhibitedDateByMonth error . ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
}
