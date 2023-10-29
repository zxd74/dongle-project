package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface AppService {

	Page<Apps> getAppList(Apps apps, Integer currentPage, Integer pageSize);

	void insert(Apps app);

	void update(Apps app);

	void deleteApp(Apps app);

	Apps getAppById(Integer id);

	void deleteByExample(Apps apps);
	
	List<Apps> getApps(Apps apps);

	void updateStaus(Integer id, Integer status);

	List<Apps> getAll();

	Apps getOne(Integer id);

	List<Apps> getAllByFc();

	Map<String, Object> getAllByFc(Integer cid);

	List<Apps> getAppByConsumer(String uuids);

	Apps getSdkOne(Integer id);

	void doAdpositionService(Integer id);
}
