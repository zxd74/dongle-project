package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.dao.model.Dictionary;

public interface AdDicService {
	
	Dictionary getDic(Integer id) throws ServiceException;

    List<Dictionary> getDicByGroupId(Integer groupId) throws ServiceException;

    List<Dictionary> getDicByGroupId(Integer groupId, Integer depth) throws ServiceException;

    List<Dictionary> getDicByIds(List<Integer> ids) throws ServiceException;

    void addIndustry(Dictionary dictionary);

	Map<String, Integer> getDicByGroupIdWithMap(Integer gid);
}
