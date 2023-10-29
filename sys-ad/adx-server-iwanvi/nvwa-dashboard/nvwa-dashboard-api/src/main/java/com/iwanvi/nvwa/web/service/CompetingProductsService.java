package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.CompetingProductsQueryReq;

public interface CompetingProductsService {

	void insert(CompetingProducts record);

	void delete(Integer id);

	List<CompetingProducts> selectByExample(String name);

	Page<CompetingProducts> selectPage(CompetingProductsQueryReq queryReq);
	
	boolean CompetingProductsProductsName(String name);

}
