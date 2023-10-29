package com.iwanvi.nvwa.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.dao.model.IndustryBlacklist;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.IndustryBlacklistService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.IndustryBlacklistQueryReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/industry-blacklists")
@Api(tags = "行业黑名单")
public class IndustryBlacklisController {
	@Autowired
	private IndustryBlacklistService industryBlacklistService;

	@PostMapping
	@NvwaRespBody
	@ApiOperation("更新行业黑名单")
	public void insertOrUpdate(@RequestBody List<Integer> addToIndustryIdList) {
		industryBlacklistService.addToBlacklist(addToIndustryIdList);
	}

	@GetMapping
	@NvwaRespBody
	public List<Integer> blacklistIndustryIds(){
		return industryBlacklistService.getIndustryBlacklist();
	}
	
	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		industryBlacklistService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<IndustryBlacklist> select(String name) {
		return industryBlacklistService.selectByExample(name);
	}

	@PostMapping("/state/on/{id}")
	@NvwaRespBody
	public void on(@PathVariable("id") Integer id) {
		industryBlacklistService.updateState(id, 1);
	}

	@PostMapping("/state/off/{id}")
	@NvwaRespBody
	public void off(@PathVariable("id") Integer id) {
		industryBlacklistService.updateState(id, 0);
	}

	@GetMapping("/load/{id}")
	@NvwaRespBody
	public IndustryBlacklist loadById(@PathVariable("id") Integer id) {
		return industryBlacklistService.loadById(id);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<IndustryBlacklist> selectPage(@RequestBody IndustryBlacklistQueryReq queryReq) {
		return industryBlacklistService.selectPage(queryReq);
	}
}
