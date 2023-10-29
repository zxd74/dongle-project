package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.DeviceModel;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.DeviceModelQueryReq;

public interface DeviceModelService {

	void update(DeviceModel record);

	void delete(Integer id);

	List<DeviceModel> select(String name);

	DeviceModel loadById(Integer id);

	Page<DeviceModel> selectPage(DeviceModelQueryReq queryReq);
	
	void insert(DeviceModel record);
	
	boolean deviceModelNameIsExist(String name);

}
