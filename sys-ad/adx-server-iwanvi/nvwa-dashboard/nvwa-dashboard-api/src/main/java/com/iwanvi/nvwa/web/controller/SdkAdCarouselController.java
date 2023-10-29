package com.iwanvi.nvwa.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.SdkAdCarousel;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.SdkAdCarouselService;

@RestController
@RequestMapping("/adcarousel")
public class SdkAdCarouselController {

	private static final Logger logger = LoggerFactory.getLogger(SdkAdCarouselController.class);
	
	@Autowired
	private SdkAdCarouselService sdkAdCarouselService;
	
	@PostMapping("set-defult")
	public String setDefult(@RequestBody List<SdkAdCarousel> list) {
		String result = StringUtils.EMPTY;
		try {
			sdkAdCarouselService.setDefult(list);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.error("setDefult. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("get-defult")
	public String getDefult() {
		String result = StringUtils.EMPTY;
		try {
			List<SdkAdCarousel> list = sdkAdCarouselService.getDefult();
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getDefult. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping
	public String insertOrUpdate(@RequestBody SdkAdCarousel carousel) {
		String result = StringUtils.EMPTY;
		try {
			sdkAdCarouselService.insertOrUpdate(carousel);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.error("insertOrUpdate. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("{id}")
	public String get(@PathVariable Integer id) {
		String result = StringUtils.EMPTY;
		try {
			SdkAdCarousel adCarousel = sdkAdCarouselService.get(id);
			result = JsonUtils.STATUS_OK(adCarousel);
		} catch (Exception e) {
			logger.error("get error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("page")
	public String getPage(@RequestBody SdkAdCarousel adCarousel) {
		String result = StringUtils.EMPTY;
		try {
			Page<SdkAdCarousel> page = sdkAdCarouselService.getPage(adCarousel);
			result = JsonUtils.STATUS_OK(page);
		} catch (Exception e) {
			logger.error("getPage error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
    @GetMapping("get-adposition-list")
    public String getAdpositionList(String name) {
    	String result = StringUtils.EMPTY;
    	try {
    		List<AdPosition> list = sdkAdCarouselService.getAdpositionList(name);
    		result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.info("getAdpositionList  error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
		}
    	return result;
    }
	
}
