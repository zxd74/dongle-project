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
import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.CompetingProductsService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import com.iwanvi.nvwa.web.vo.CompetingProductsQueryReq;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/competing-products")
@Api(tags = "竞品标签")
public class CompetingProductsController {
	@Autowired
	private CompetingProductsService competingProductsService;

	@PostMapping
	@NvwaRespBody
	public void insert(@RequestBody CompetingProducts record) {
		if (StringUtils.isBlank(record.getProductsName())) {
			throw new ServiceException("竞品标签名称不能为空");
		}
		competingProductsService.insert(record);
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		competingProductsService.delete(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<CompetingProducts> select(@RequestParam(value = "name", required = false) String name) {
		return competingProductsService.selectByExample(name);
	}

	@PostMapping("/page")
	@NvwaRespBody
	public Page<CompetingProducts> selectPage(@RequestBody CompetingProductsQueryReq queryReq) {
		return competingProductsService.selectPage(queryReq);

	}

}
