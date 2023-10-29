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
import com.iwanvi.nvwa.dao.model.BookCategory;
import com.iwanvi.nvwa.web.service.BookCategoryService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/book-categorys")
@Api(tags = "图书分级管理")
public class BookCategoryController {
	@Autowired
	private BookCategoryService bookCategoryService;

	@PostMapping
	@NvwaRespBody
	public void insertOrUpdate(@RequestBody BookCategory record) {
		if (StringUtils.isBlank(record.getName())) {
			throw new ServiceException("图书名称不能为空");
		}
		
		if (record.getId() == null) {
			bookCategoryService.insert(record);
		} else {
			bookCategoryService.update(record);
		}
	}

	@PostMapping("/{id}")
	@NvwaRespBody
	public void delete(@PathVariable("id") Integer id) {
		bookCategoryService.delete(id);
	}

	@GetMapping("/{id}")
	@NvwaRespBody
	public BookCategory loadById(@PathVariable("id") Integer id) {
		return bookCategoryService.loadById(id);
	}

	@GetMapping("/select")
	@NvwaRespBody
	public List<BookCategory> select(@RequestParam(value = "name", required = false) String name) {
		return bookCategoryService.select(name);
	}

	@GetMapping("/selectOne")
	@NvwaRespBody
	public List<BookCategory> selectOne() {
		return bookCategoryService.selectOneLevel();
	}

	@GetMapping("/selectTwo")
	@NvwaRespBody
	public List<BookCategory> selectTow() {
		return bookCategoryService.selectTwoLevel();
	}

	@GetMapping("/selectThree")
	@NvwaRespBody
	public List<BookCategory> selectThree() {
		return bookCategoryService.selectThreeLevel();
	}

}
