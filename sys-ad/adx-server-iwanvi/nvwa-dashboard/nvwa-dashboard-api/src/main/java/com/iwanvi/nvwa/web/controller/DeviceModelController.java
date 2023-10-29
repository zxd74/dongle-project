package com.iwanvi.nvwa.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.dao.model.DeviceModel;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DeviceModelService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.DeviceModelQueryReq;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/device-models")
@Api(tags = "机型管理")
public class DeviceModelController {
	@Autowired
	private DeviceModelService deviceModelService;
	
	@PostMapping
	@NvwaRespBody
	public void insert(@RequestBody DeviceModel record) {
		if(StringUtils.isBlank(record.getModelName())) {
			throw new ServiceException("机型名称不能为空");		
		}
		deviceModelService.insert(record);
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		deviceModelService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<DeviceModel> select(@RequestParam(value = "name", required = false) String name) {
		return deviceModelService.select(name);
	}

	@GetMapping("/{id}")
	@NvwaRespBody
	public DeviceModel loadlById(@PathVariable("id") Integer id) {
		return deviceModelService.loadById(id);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<DeviceModel> selectPage(@RequestBody DeviceModelQueryReq queryReq) {
		return deviceModelService.selectPage(queryReq);
	}

}
