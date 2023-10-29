package com.iwanvi.nvwa.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AppVersion;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppVersionService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.AppVersionQueryReq;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/app-versions")
@Api(tags = "版本号")
public class AppVersionController {
	@Autowired
	private AppVersionService appVersionService;

	@PostMapping
	@NvwaRespBody
	public void insert(@RequestBody AppVersion record) {
		if(StringUtils.isBlank(record.getName())) {
			throw new ServiceException("版本号不能为空");
		}
		appVersionService.insert(record);
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		appVersionService.delete(id);
	}

	@GetMapping("select")
	@NvwaRespBody
	public List<AppVersion> select(@RequestParam(value = "name", required = false) String name) {
		return appVersionService.select(name);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<AppVersion> selectPage(@RequestBody AppVersionQueryReq queryReq){
		return appVersionService.selectPage(queryReq);
	}
	
	@GetMapping("/select-fc")
	@NvwaRespBody
	public List<AppVersion> getAllByFc() {
		return appVersionService.getAllByFc();

	}
}
