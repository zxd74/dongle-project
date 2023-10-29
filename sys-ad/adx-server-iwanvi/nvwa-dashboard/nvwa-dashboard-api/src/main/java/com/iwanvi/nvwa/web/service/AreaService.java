package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Area;

import java.util.List;

public interface AreaService {

	List<Area> selectForList(Area area);

	List<Area> selectListByNameAndType(Integer findType, String areaName);

	List<Integer> getAllAreaCityId();

	List<Area> getDicByIds(List<Integer> ids);

}
