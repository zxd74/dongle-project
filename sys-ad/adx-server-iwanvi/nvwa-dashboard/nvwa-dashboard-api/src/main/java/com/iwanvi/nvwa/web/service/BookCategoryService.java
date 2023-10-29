package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.BookCategory;

public interface BookCategoryService {

	void insert(BookCategory record);

	void delete(Integer id);

	void update(BookCategory record);

	List<BookCategory> select(String name);

	BookCategory loadById(Integer id);

	List<BookCategory> selectOneLevel();

	List<BookCategory> selectTwoLevel();

	List<BookCategory> selectThreeLevel();

	boolean bookCategoryNameIsExist(String name);

}
