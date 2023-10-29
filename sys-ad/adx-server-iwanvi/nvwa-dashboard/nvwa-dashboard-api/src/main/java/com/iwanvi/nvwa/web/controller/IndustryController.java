package com.iwanvi.nvwa.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.dao.model.Industry;
import com.iwanvi.nvwa.web.service.IndustryService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/industrys")
@Api(tags = "行业关键词")
public class IndustryController {
	@Autowired
	private IndustryService industryService;

	@PostMapping
	@NvwaRespBody
	public void createOrUpdate(@RequestBody Industry record) {
		if(StringUtils.isBlank(record.getName())) {
			throw new ServiceException("行业关键词不能为空");
		}
		if (record.getId() == null) {
			industryService.insert(record);
		} else {
			industryService.update(record);
		}
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		industryService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<Industry> select(@RequestParam(value = "name", required = false) String name) {
		return industryService.selectByExample(name);
	}

	@GetMapping("/{id}")
	@NvwaRespBody
	public Industry loadById(@PathVariable("id") Integer id) {
		return industryService.loadById(id);
	}

	@PostMapping("/blacklist/{id}")
	@NvwaRespBody
	public void addToBlacklist(@PathVariable("id") Integer id) {
		industryService.addToBlacklist(id);
	}
	
	@GetMapping("/primaryIndustry")
	@NvwaRespBody
	public List<Industry> selectPrimaryIndustry() {
		return industryService.selectPrimaryIndustry();
	}
}
