package com.iwanvi.nvwa.pixel.connector.crontab.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

import ai.houyi.dorado.rest.annotation.Controller;
import ai.houyi.dorado.rest.annotation.Path;

@Controller
@Path("/cron")
public class CronController {

	private static final Logger LOG = LoggerFactory.getLogger("CronController");
	
	@Resource
	private TaskService flowTaskService;
	
	@Resource
	private TaskService dspPlatformTaskService;
	
	@Path("/flow/{day}")
	public String flow(String day) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("day", day);
			flowTaskService.quotaTask(paramMap);
		} catch (Exception e) {
			LOG.error("flow error. ", e);
		}
		return "ok";
	}
	
	@Path("/dsp")
	public String dsp(String day, String hour) {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("day", day);
			paramMap.put("hour", hour);
			dspPlatformTaskService.quotaTask(paramMap);
		} catch (Exception e) {
			LOG.error("dsp error. ", e);
		}
		return "ok";
	}
}
