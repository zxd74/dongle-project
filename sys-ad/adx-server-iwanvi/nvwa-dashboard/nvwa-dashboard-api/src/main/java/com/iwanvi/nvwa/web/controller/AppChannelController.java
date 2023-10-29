package com.iwanvi.nvwa.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AppChannel;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppChannelService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.AppChannelQueryReq;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/app-channels")
@Api(tags = "渠道号")
public class AppChannelController {
	@Autowired
	private AppChannelService appChannelService;

	@PostMapping
	@NvwaRespBody
	public void insert(@RequestBody AppChannel record) {
		if (StringUtils.isBlank(record.getName()) || StringUtils.isBlank(record.getCname())) {
			throw new ServiceException("渠道号或渠道中文名不能为空");
		}
		appChannelService.insert(record);
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		appChannelService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<AppChannel> select(@RequestParam(value = "name", required = false) String name) {
		return appChannelService.select(name);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<AppChannel> selectPage(@RequestBody AppChannelQueryReq queryReq) {
		return appChannelService.selectPage(queryReq);

	}

	@GetMapping("/select-fc")
	@NvwaRespBody
	public List<AppChannel> getAllByFc() {
		return appChannelService.getAllByFc();

	}

	@GetMapping("/selectOne")
	@NvwaRespBody
	public List<AppChannel> selectOne(String name) {
		return appChannelService.selectByLevel(null, 1, name);
	}

	@PostMapping("/selectTwo")
	@NvwaRespBody
	public List<AppChannel> selectTwo(@RequestBody List<String> level1ChannelNames,String name) {
		return appChannelService.selectByLevel(level1ChannelNames, 2, name);
	}

	@PostMapping("/batchImport")
	@NvwaRespBody
	public boolean batchImport(MultipartFile file) throws Exception {

		boolean channel = false;
		String fileName = file.getOriginalFilename();
		channel = appChannelService.batchImport(fileName, file);
		return channel;

	}

}
