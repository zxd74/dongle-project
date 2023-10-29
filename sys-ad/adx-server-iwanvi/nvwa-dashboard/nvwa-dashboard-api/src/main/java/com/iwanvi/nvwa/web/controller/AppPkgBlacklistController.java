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

import com.iwanvi.nvwa.dao.model.AppPkgBlacklist;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppPkgBlacklistService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.AppPkgBlacklistQueryReq;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/app-pkg-blacklists")
@Api(tags = "应用黑名单")
public class AppPkgBlacklistController {
	@Autowired
	private AppPkgBlacklistService appPkgBlacklistService;

	@PostMapping
	@NvwaRespBody
	public void insert(@RequestBody List<AppPkgBlacklist> records) {
		appPkgBlacklistService.insert(records);
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		appPkgBlacklistService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<AppPkgBlacklist> select(@RequestParam(value = "name", required = false) String name) {
		return appPkgBlacklistService.select(name);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<AppPkgBlacklist> selectPage(@RequestBody AppPkgBlacklistQueryReq queryReq) {
		return appPkgBlacklistService.selectPage(queryReq);
	}
}
