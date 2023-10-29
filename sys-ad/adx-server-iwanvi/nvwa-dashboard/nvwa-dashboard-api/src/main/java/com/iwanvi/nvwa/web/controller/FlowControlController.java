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
import com.iwanvi.nvwa.dao.model.FlowControl;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.FlowControlServicce;

@RequestMapping("flowcontrol")
@RestController
public class FlowControlController {

	private static final Logger logger = LoggerFactory.getLogger(FlowControlController.class);

	@Autowired
	private FlowControlServicce controlServicce;

	@PostMapping("batch-insert")
	public String batchInsert(@RequestBody FlowControl flowControl) {
		String result = StringUtils.EMPTY;
		try {
			controlServicce.batchInsert(flowControl);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.info("batchInsert error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("page")
	public String getPage(@RequestBody FlowControl flowControl) {
		String result = StringUtils.EMPTY;
		try {
			Page<FlowControl> page = controlServicce.getPage(flowControl);
			result = JsonUtils.STATUS_OK(page);
		} catch (Exception e) {
			logger.info("getPage error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@GetMapping("flow-switch")
	public String flowSwitch(Integer status, Integer id) {
		String result = StringUtils.EMPTY;
		try {
			controlServicce.flowSwitch(status, id);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.info("flowSwitch error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@GetMapping("{id}")
	public String getOne(@PathVariable Integer id) {
		String result = StringUtils.EMPTY;
		try {
			FlowControl control = controlServicce.getOne(id);
			result = JsonUtils.STATUS_OK(control);
		} catch (Exception e) {
			logger.info("getOne error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("del/{id}")
	public String getDel(@PathVariable Integer id) {
		String result = StringUtils.EMPTY;
		try {
			controlServicce.getDel(id);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.info("getDel error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("update")
	public String update(@RequestBody FlowControl flowControl) {
		String result = StringUtils.EMPTY;
		try {
			controlServicce.update(flowControl);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.info("update error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("batch-del")
	public String batchUpdate(@RequestBody FlowControl flowControl) {
		String result = StringUtils.EMPTY;
		try {
			controlServicce.batchUpdate(flowControl);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.info("batchUpdate error ! ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

}
