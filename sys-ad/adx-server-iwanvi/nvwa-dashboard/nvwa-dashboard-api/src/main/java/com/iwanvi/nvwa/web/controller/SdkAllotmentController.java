package com.iwanvi.nvwa.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.NvwaUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.SdkAllotmentMapper;
import com.iwanvi.nvwa.dao.model.SdkAllotment;
import com.iwanvi.nvwa.dao.model.SdkAllotmentExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.SdkAllotmentService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.vo.SdkAllotmentForm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "SDK投放(包段/固定量)接口")
@RequestMapping("sdk/allot")
public class SdkAllotmentController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger("SdkAllotmentController");

	@Autowired
	private SdkAllotmentService sdkAllotmentService;

	@Autowired
	private SdkAllotmentMapper sdkAllotmentMapper;
	
	@ApiOperation("新增分配策略")
	@ResponseBody
	@PostMapping("add")
	public String add(HttpServletRequest request, @RequestBody SdkAllotmentForm sdkAllotmentForm) {
		String result = null;
		try { 
			if (StringUtils.isBlank(sdkAllotmentForm.getAllotmentName())) {
				throw new ControllerException("分配策略名称不能为空");
			}
			SdkAllotment sdkAllotment = new SdkAllotment();
			BeanUtils.copyProperties(sdkAllotmentForm, sdkAllotment);
			SdkAllotmentExample example = new SdkAllotmentExample();
			example.createCriteria().andAllotmentNameEqualTo(sdkAllotment.getAllotmentName());
			
			SdkAllotment temp = sdkAllotmentMapper.selectOneByExample(example);
			if (temp != null) {
				throw new ControllerException("名称已经存在");
			}
			Integer uid = getUserId(request);
			
			sdkAllotment.setCreateUser(uid == null ? 1 : uid);
			
			result = sdkAllotmentService.add(sdkAllotment, sdkAllotmentForm.getScheduleList());
		} catch (Exception e) {
			LOG.error("status error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@ApiOperation("新增分配策略")
	@ResponseBody
	@PostMapping("addFixed")
	public String addFixed(HttpServletRequest request) {
		String result = null;
		try { 
			Map<String, Object> paramMap = getParamMap(request);
			
			if (StringUtils.isBlank(NvwaUtils.obj2Empty(paramMap.get("allotmentName")))) {
				throw new ControllerException("分配策略名称不能为空");
			}
			SdkAllotmentExample example = new SdkAllotmentExample();
			example.createCriteria().andAllotmentNameEqualTo(NvwaUtils.obj2Empty(paramMap.get("allotmentName")));

			SdkAllotment temp = sdkAllotmentMapper.selectOneByExample(example);
			if (temp != null) {
				throw new ControllerException("名称已经存在");
			}
			Integer uid = getUserId(request);

			result = sdkAllotmentService.addFixed(paramMap, uid == null ? 1 : uid);
		} catch (Exception e) {
			LOG.error("status error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@ApiOperation("修改分配策略")
	@ResponseBody
	@PostMapping("updateFixed")
	public String updateFixed(HttpServletRequest request) {
		String result = null;
		try {
			Map<String, Object> paramMap = getParamMap(request);
			if (paramMap.get("id") == null) {
				throw new ServiceException("分配策略id不能为空");
			}
			result = sdkAllotmentService.updateFixed(paramMap);
		} catch (Exception e) {
			LOG.error("update error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@ApiOperation("分配策略更新状态")
	@ResponseBody
	@GetMapping("status")
	public String status(Integer id, Integer status) {
		String result = null;
		try {
			if (id == null || status == null) {
				throw new ServiceException("分配策略id不得为空");
			}
			result = sdkAllotmentService.status(id, status);
		} catch (Exception e) {
			LOG.error("status error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@ApiOperation("修改分配策略")
	@ResponseBody
	@PostMapping("update")
	public String update(HttpServletRequest request, @RequestBody SdkAllotmentForm sdkAllotmentForm) {
		String result = null;
		try {
			if (sdkAllotmentForm.getId() == null) {
				throw new ServiceException("分配策略id不能为空");
			}
			result = sdkAllotmentService.update(sdkAllotmentForm);
		} catch (Exception e) {
			LOG.error("update error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@ApiOperation("查询分配策略列表")
	@RequestMapping("list")
	@ResponseBody
	public String list(String name, Integer platformId, Integer type, Integer status, Integer pageNum,
			Integer pageSize) {

		String result = null;
		try {
			Page<SdkAllotmentForm> list = sdkAllotmentService.listForPage(
					name, platformId, type, status, pageNum, pageSize);
			result = list == null ? JsonUtils.DATA_NOT_FIND() : JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			LOG.error("list error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@ApiOperation("查询包段策略排期")
	@ResponseBody
	@GetMapping("sche")
	public String sche(String posIds, Integer month, Integer allotId, Integer type) {
		String result = null;
		try {
			result = sdkAllotmentService.getSchedule(posIds, month, allotId, type);
			
			result = result == null ? JsonUtils.DATA_NOT_FIND() : result;
		} catch (Exception e) {
			LOG.info("schedule error. ", e);
			result = JsonUtils.STATUS_FAILED();
		}
		return result;
	}
	
	@ApiOperation("查询分配策略详情")
	@GetMapping("get/{id}")
	public Map<String, Object> get(@PathVariable Integer id) {
		if (id == null) {
			throw new ServiceException("分配策略id不得为空");
		}
		return sdkAllotmentService.get(id);
	}

	@ApiOperation("查询分配策略详情")
	@GetMapping("info/{id}")
	public SdkAllotmentForm info(@PathVariable Integer id) {
		if (id == null) {
			throw new ServiceException("分配策略id不得为空");
		}
		SdkAllotmentForm form = sdkAllotmentService.info(id);
		return form;
	}
	
	@ApiOperation("平台、App列表")
	@ResponseBody
	@GetMapping("pre")
	public String prepare() {
		String result = null;
		try {
			result = sdkAllotmentService.prepare();
		} catch (Exception e) {
			LOG.info("prepare error. ", e);
			result = JsonUtils.STATUS_FAILED();
		}
		return result;
	}
	
	@ApiOperation("广告位列表")
	@ResponseBody
	@GetMapping("adp/{appId}/{adPosId}")
	public String adPostion(@PathVariable Integer appId, @PathVariable Integer adPosId) {
		String result = null;
		try {
			result = sdkAllotmentService.adPostion(appId, adPosId);
		} catch (Exception e) {
			LOG.info("adPostion error. ", e);
			result = JsonUtils.STATUS_FAILED();
		}
		return result;
	}

	@ApiOperation("广告位流量分配状态")
	@ResponseBody
	@GetMapping("adp/status/{allotId}")
	public String adpStatus(@PathVariable Integer allotId) {
		String result = null;
		try {
			result = sdkAllotmentService.adpStatus(allotId);
		} catch (Exception e) {
			LOG.info("adPostion error. ", e);
			result = JsonUtils.STATUS_FAILED();
		}
		return result;
	}
}
