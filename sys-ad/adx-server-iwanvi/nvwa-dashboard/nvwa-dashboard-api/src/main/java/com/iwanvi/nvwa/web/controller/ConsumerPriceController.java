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
import com.iwanvi.nvwa.web.service.ConsumerPriceService;

@RequestMapping("consumer-position-price")
@RestController
public class ConsumerPriceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ConsumerPriceController.class);

	@Autowired
	private ConsumerPriceService consumerPriceService;
	
	@GetMapping("get")
	public String get(Integer id) {
		String result = StringUtils.EMPTY;
		try {
			List<Map<String, Object>> map = consumerPriceService.get(id);
			result = JsonUtils.STATUS_OK(map);
		} catch (Exception e) {
			logger.info("get error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("set")
	public String set(@RequestBody Map<String, Object> params) {
		String result = StringUtils.EMPTY;
		try {
			consumerPriceService.set(params);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.info("set error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	

}
