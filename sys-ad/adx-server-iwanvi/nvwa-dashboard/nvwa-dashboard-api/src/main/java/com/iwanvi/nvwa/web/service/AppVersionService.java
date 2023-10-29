package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AppVersion;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.AppVersionQueryReq;

public interface AppVersionService {
	
	void insert(AppVersion record);
	
	void delete(Integer id);
	
	List<AppVersion> select(String name);

	Page<AppVersion> selectPage(AppVersionQueryReq queryReq);

	List<AppVersion> getAllByFc();
	
	boolean AppVersionNameIsExist(String name);

}
