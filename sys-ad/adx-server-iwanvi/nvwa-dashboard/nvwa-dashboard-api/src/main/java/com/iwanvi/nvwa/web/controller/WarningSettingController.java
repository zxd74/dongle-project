package com.iwanvi.nvwa.web.controller;


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
import com.iwanvi.nvwa.dao.model.WarningSetting;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.WarningSettingService;

@RestController
@RequestMapping("warning-setting")
public class WarningSettingController {

	private static final Logger logger = LoggerFactory.getLogger(WarningSettingController.class);
	
	@Autowired
	private WarningSettingService warningSettingService;
	
	
	@PostMapping
	public String update(@RequestBody WarningSetting warningSetting) {
		String result = StringUtils.EMPTY;
		try {
			warningSettingService.update(warningSetting);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.error("update. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	
	@GetMapping("status")
	public String updateStatus(Integer id, Integer status) {
		String result = StringUtils.EMPTY;
		try {
			warningSettingService.updateStatus(id, status);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.error("updateStatus. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("{id}")
	public String get(@PathVariable Integer id) {
		String result = StringUtils.EMPTY;
		try {
			WarningSetting warningSetting = warningSettingService.get(id);
			result = JsonUtils.STATUS_OK(warningSetting);
		} catch (Exception e) {
			logger.error("get error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("page")
	public String getPage(@RequestBody WarningSetting warningSetting) {
		String result = StringUtils.EMPTY;
		try {
			Page<WarningSetting> page = warningSettingService.getPage(warningSetting);
			result = JsonUtils.STATUS_OK(page);
		} catch (Exception e) {
			logger.error("getPage error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("init-page")
	public String initPage(Integer aid, Integer cid) {
		String result = StringUtils.EMPTY;
		try {
			WarningSetting page = warningSettingService.initPage(aid, cid);
			result = JsonUtils.STATUS_OK(page);
		} catch (Exception e) {
			logger.error("initPage error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
}
