package com.iwanvi.nvwa.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.web.util.NvwaRespBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppService;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import com.iwanvi.nvwa.web.service.SyslogService;
import com.iwanvi.nvwa.web.service.UserService;

@RestController
@RequestMapping("/app")
public class AppsController {

	private static final Logger logger = LoggerFactory.getLogger(AppsController.class);

	@Autowired
	private AppService appService;

	@Autowired
	private UserService userService;

	@Autowired
	private SysCrontabService sysCrontabService;

	@Autowired
	private AppsMapper appsMapper;

	@RequestMapping("/getlist")
	public String getAppList(Apps apps, Integer currentPage, Integer pageSize, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (!userService.isAdmin(uid)) {
				apps.setCreateUser(uid);
			}
			Page<Apps> page = appService.getAppList(apps, currentPage, pageSize);
			result = JsonUtils.STATUS_OK(page);
			logger.info("getAppList success. ");
		} catch (Exception e) {
			logger.error("getAppList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("/add")
	public String addApp(@RequestBody Apps app, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (app != null) {
				app.setCreateUser(uid);
				appService.insert(app);
				appService.doAdpositionService(app.getId());
				int count = sysCrontabService.countCrontab(app.getId(), Constants.OBJ_APP);
				if (count > 0) {
					sysCrontabService.addSysCrontab(app.getId(), Constants.OBJ_APP, Constants.OP_UPDATE);
				} else {
					sysCrontabService.addSysCrontab(app.getId(), Constants.OBJ_APP, Constants.OP_ADD);
				}
				logger.info("add sysCrontab  success, userId: {}, appId: {}, appName: {}", uid, app.getAppId(),
						app.getAppName());

				result = JsonUtils.STATUS_OK();
			} else {
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
				logger.error("insert app param is null. ");
			}
		} catch (Exception e) {
			logger.error("addApp error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@GetMapping("/getapp")
	public String getAppById(Integer id) {
		String result = null;
		try {
			if (id != null) {
				Apps app = appService.getAppById(id);
				result = JsonUtils.STATUS_OK(app);
			} else {
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
				logger.info("getAppById id is null. ");
			}
		} catch (Exception e) {
			logger.error("getAppById error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@PostMapping("/update")
	public String updateApp(@RequestBody Apps app, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (app != null) {
				app.setUpdateUser(uid);
				app.setUpdateTime(new Date());
				app.setAppStatus(Constants.STATE_VALID);
				appService.update(app);

				int count = sysCrontabService.countCrontab(app.getId(), Constants.OBJ_APP);
				if (count > 0) {
					sysCrontabService.addSysCrontab(app.getId(), Constants.OBJ_APP, Constants.OP_UPDATE);
				} else {
					sysCrontabService.addSysCrontab(app.getId(), Constants.OBJ_APP, Constants.OP_ADD);
				}
				logger.info("add sysCrontab  success, userId: {}, appId: {}, appName: {}", uid, app.getAppId(),
						app.getAppName());

				result = JsonUtils.STATUS_OK();
			} else {
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
				logger.error("update app param is null. ");
			}
		} catch (Exception e) {
			logger.error("updateApp error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/delete")
	public String deleteApp(Apps app, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (app != null) {
				app.setCreateUser(uid);
				appService.deleteApp(app);
				int count = sysCrontabService.countCrontab(app.getId(), Constants.OBJ_APP);
				if (count > 0) {
					sysCrontabService.addSysCrontab(app.getId(), Constants.OBJ_APP, Constants.OP_REMOVE);
				}
				logger.info("add sysCrontab  success, userId: {}, appId: {}, appName: {}", uid, app.getAppId(),
						app.getAppName());
				result = JsonUtils.STATUS_OK();
			} else {
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
				logger.error("delete app param error!");
			}
		} catch (Exception e) {
			logger.error("delete app error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/updateStaus")
	public String updateStaus(Integer id, Integer status, @RequestAttribute Integer uid) {
		String result = StringUtils.EMPTY;
		try {
			if (id != null && status != null) {
				appService.updateStaus(id, status);
				Apps app = appsMapper.selectByPrimaryKey(id);
				int count = sysCrontabService.countCrontab(app.getId(), Constants.OBJ_APP);
				if (count > 0) {
					sysCrontabService.addSysCrontab(app.getId(), Constants.OBJ_APP,  Constants.OP_UPDATE);
				}
				logger.info("add sysCrontab  success, userId: {}, appId: {}, appName: {}", uid, app.getAppId(),
						app.getAppName());

				result = JsonUtils.STATUS_OK();
			} else {
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
				logger.error("updateStaus app param error!");
			}
		} catch (Exception e) {
			logger.error("updateStaus app error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@GetMapping("/getall")
	public String getAll() {
		String result = StringUtils.EMPTY;
		try {
			List<Apps> list = appService.getAll();
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getAll  error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("/getall-fc")
	public String getAllByFc(Integer cid) {
		String result = StringUtils.EMPTY;
		try {
			Map<String, Object> list = appService.getAllByFc(cid);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getAll  error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * sdk轮播策略初始获取广告位逻辑（含默认设置）
	 * @param id
	 * @return
	 */
	@GetMapping("/getone")
	public String getOne(Integer id) {
		String result = StringUtils.EMPTY;
		try {
			Apps apps = appService.getOne(id);
			result = JsonUtils.STATUS_OK(apps);
		} catch (Exception e) {
			logger.error("getOne  error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	/**
	 * sdk轮播策略获取广告位（去除默认设置后）
	 * @param id
	 * @return
	 */
	@GetMapping("/get-sdk-one")
	public String getSdkOne(Integer id) {
		String result = StringUtils.EMPTY;
		try {
			Apps apps = appService.getSdkOne(id);
			result = JsonUtils.STATUS_OK(apps);
		} catch (Exception e) {
			logger.error("getSdkOne  error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	/**
	 * 流量控制中已经添加的App
	 * 
	 * @return
	 */
	@GetMapping("/select-fc")
	public String getAllByFc() {
		String result = StringUtils.EMPTY;
		try {
			List<Apps> list = appService.getAllByFc();
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getAll  error!", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	
	@NvwaRespBody
	@GetMapping("listByConsumer/{id}")
	public List<Apps> getAppByConsumer(@PathVariable("uuid") String uuid) {
		return appService.getAppByConsumer(uuid);
	}

	
}
