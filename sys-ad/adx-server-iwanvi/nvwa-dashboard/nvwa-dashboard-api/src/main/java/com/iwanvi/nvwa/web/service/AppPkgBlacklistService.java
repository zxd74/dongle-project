package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AppPkgBlacklist;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.AppPkgBlacklistQueryReq;

public interface AppPkgBlacklistService {

	void insert(List<AppPkgBlacklist> records);

	void delete(Integer id);

	List<AppPkgBlacklist> select(String name);

	Page<AppPkgBlacklist> selectPage(AppPkgBlacklistQueryReq queryReq);
	
	boolean AppPkgBlacklistPkgName(String name);

}
