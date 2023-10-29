package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.dao.BookCategoryMapper;
import com.iwanvi.nvwa.dao.model.BookCategory;
import com.iwanvi.nvwa.dao.model.BookCategoryExample;
import com.iwanvi.nvwa.web.service.BookCategoryService;
import com.iwanvi.nvwa.web.util.ExceptionUtils;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {
	@Autowired
	private BookCategoryMapper bookCategoryMapper;

	@Override
	public void insert(BookCategory record) {
		BookCategory cat = bookCategoryMapper.selectOneByExample(
				BookCategoryExample.newAndCreateCriteria().andNameEqualTo(record.getName()).example());
		if (cat == null) {
			bookCategoryMapper.insertSelective(record);
			return;
		}

		if (!cat.getIsDeleted())
			ExceptionUtils.throwException("图书分类名称重复");
		bookCategoryMapper.updateByPrimaryKeySelective(
				BookCategory.builder().id(cat.getId()).parentId(record.getParentId()).isDeleted(false).build());
	}

	@Override
	public void delete(Integer id) {
		BookCategory book = BookCategory.builder().id(id).isDeleted(true).build();
		bookCategoryMapper.updateByPrimaryKeySelective(book);
	}

	@Override
	public void update(BookCategory record) {
		BookCategoryExample example = BookCategoryExample.newAndCreateCriteria().andNameEqualTo(record.getName())
				.andIdNotEqualTo(record.getId()).example();
		BookCategory bookCategory = bookCategoryMapper.selectOneByExample(example);
		if (bookCategory == null) {
			bookCategoryMapper.updateByPrimaryKeySelective(record);
			return;
		}
		ExceptionUtils.throwException("图书分类名称重复");
	}

	@Override
	public List<BookCategory> select(String name) {
		if (StringUtils.isBlank(name)) {
			return selectAll();
		}

		BookCategoryExample example = new BookCategoryExample();
		BookCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		criteria.andNameLike("%" + name + "%");
//		example.createCriteria().andNameLike("%" + name + "%").andIsDeletedEqualTo(true);
		List<BookCategory> matchResult = bookCategoryMapper.selectByExample(example);

		List<BookCategory> resultList = new ArrayList<BookCategory>();
		for (BookCategory bookcategory : matchResult) {
			if (bookcategory.getParentId() == null) {
				resultList.add(bookcategory);
			} else {
				resultList.add(topLevelBookCategory(bookcategory));
			}
		}
		return bookCategoryMapper.selectByExample(example);
	}

	private BookCategory topLevelBookCategory(BookCategory child) {

		BookCategory result = null;
		if (child.getParentId() == null) {
			return result;
		}
		result = bookCategoryMapper.selectByPrimaryKey(child.getParentId());
		result.setChildren(Arrays.asList(child));

		if (result.getParentId() != null) {
			result = topLevelBookCategory(result);
		}
		return result;
	}

	private List<BookCategory> selectAll() {
		return selectAll(null);
	}

	private List<BookCategory> selectAll(Integer id) {

		BookCategoryExample example = new BookCategoryExample();
		BookCategoryExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);

		if (id == null) {
			criteria.andParentIdIsNull();
		} else {
			criteria.andParentIdEqualTo(id);
		}
		List<BookCategory> parentList = bookCategoryMapper.selectByExample(example);
		for (BookCategory parent : parentList) {
			parent.setChildren(selectAll(parent.getId()));
		}
		return parentList;
	}

	@Override
	public BookCategory loadById(Integer id) {
		return bookCategoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<BookCategory> selectOneLevel() {
		BookCategoryExample bookOne = new BookCategoryExample();
		BookCategoryExample.Criteria criteria = bookOne.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		criteria.andParentIdIsNull();

		return bookCategoryMapper.selectByExample(bookOne);
	}

	@Override
	public List<BookCategory> selectTwoLevel() {
		BookCategoryExample bookTow = new BookCategoryExample();
		BookCategoryExample.Criteria books = bookTow.createCriteria();
		books.andIsDeletedNotEqualTo(true);
		List<Integer> list = new ArrayList<Integer>();
		List<BookCategory> levelList = selectOneLevel();
		if (list.isEmpty())
			return Collections.emptyList();
		for (BookCategory book : levelList) {

			list.add(book.getId());
		}
		books.andParentIdIn(list);
		return bookCategoryMapper.selectByExample(bookTow);
	}

	@Override
	public List<BookCategory> selectThreeLevel() {
		BookCategoryExample bookThree = new BookCategoryExample();
		BookCategoryExample.Criteria bookCriteria = bookThree.createCriteria();
		bookCriteria.andIsDeletedNotEqualTo(true);
		List<Integer> listThree = new ArrayList<Integer>();
		List<BookCategory> threeList = selectTwoLevel();
		if (threeList.isEmpty())
			return Collections.emptyList();
		for (BookCategory bookcategory : threeList) {
			listThree.add(bookcategory.getId());
		}
		bookCriteria.andParentIdIn(listThree);
		return bookCategoryMapper.selectByExample(bookThree);
	}

	@Override
	public boolean bookCategoryNameIsExist(String name) {
		BookCategoryExample bookExample = new BookCategoryExample();
		bookExample.createCriteria().andNameEqualTo(name);
		long count = bookCategoryMapper.countByExample(bookExample);
		return count > 0;

	}

}
