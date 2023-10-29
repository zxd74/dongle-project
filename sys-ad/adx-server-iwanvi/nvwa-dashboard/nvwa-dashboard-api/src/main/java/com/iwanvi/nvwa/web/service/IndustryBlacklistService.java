package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.IndustryBlacklist;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.IndustryBlacklistQueryReq;

public interface IndustryBlacklistService {

	void insert(IndustryBlacklist record);

	void delete(Integer id);

	void update(IndustryBlacklist record);

	List<IndustryBlacklist> selectByExample(String name);

	void updateState(Integer id, int state);

	IndustryBlacklist loadById(Integer id);

	Page<IndustryBlacklist> selectPage(IndustryBlacklistQueryReq queryReq);

	void addToBlacklist(List<Integer> addToIndustryIdList);

	List<Integer> getIndustryBlacklist();

}
