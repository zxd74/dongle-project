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

import com.iwanvi.nvwa.dao.model.AppPkg;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppPkgService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.AppPkgQuertReq;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/app-pkgs")
@Api(tags = "应用包名关键词")
public class AppPkgController {
	@Autowired
	private AppPkgService appPkgService;

	@PostMapping
	@NvwaRespBody
	public void inertOrUpdate(@RequestBody AppPkg record) {
		if (record.getId() == null) {
			appPkgService.insert(record);
		} else {
			appPkgService.update(record);
		}
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		appPkgService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<AppPkg> select(@RequestParam(value = "name", required = false) String name) {
		return appPkgService.selectByExample(name);
	}

	@GetMapping("/{id}")
	@NvwaRespBody
	public AppPkg loadById(@PathVariable("id") Integer id) {
		return appPkgService.loadById(id);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<AppPkg> selectPage(@RequestBody AppPkgQuertReq queryReq) {
		return appPkgService.selectPage(queryReq);
	}

	@PostMapping("/blacklist/{id}")
	@NvwaRespBody
	public void addToBlacklist(@PathVariable("id") Integer id) {
		appPkgService.addByIdBlack(id);
	}

}
