package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Industry;

public interface IndustryService {

	void insert(Industry record);

	void delete(Integer id);

	void update(Industry record);

	List<Industry> selectByExample(String t);

	Industry loadById(Integer id);

	void addToBlacklist(Integer id);

	List<Industry> selectPrimaryIndustry();

	boolean industryNameIsExist(String name);

}
