package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.dao.IndustryBlacklistMapper;
import com.iwanvi.nvwa.dao.IndustryMapper;
import com.iwanvi.nvwa.dao.model.Industry;
import com.iwanvi.nvwa.dao.model.IndustryBlacklist;
import com.iwanvi.nvwa.dao.model.IndustryBlacklistExample;
import com.iwanvi.nvwa.dao.model.IndustryExample;
import com.iwanvi.nvwa.web.service.IndustryService;
import com.iwanvi.nvwa.web.util.ExceptionUtils;

@Service
public class IndustryServiceImpl implements IndustryService {
	@Autowired
	private IndustryMapper industryMapper;
	@Autowired
	private IndustryBlacklistMapper industryBlacklistMapper;

	@Override
	public void insert(Industry record) {
		boolean isExist = industryNameIsExist(record.getName());
		if (isExist) {
			throw new ServiceException("行业关键词名称不能重复");
		}
		industryMapper.insertSelective(record);
		if (record.getBlanklist()) {
			IndustryBlacklist industryBlacklist = new IndustryBlacklist();
			industryBlacklist.setIndustryId(record.getId());
			industryBlacklist.setIndustryName(record.getName());

			industryBlacklistMapper.insertSelective(industryBlacklist);
		}
	}

	@Override
	public void delete(Integer id) {
		industryMapper.deleteByPrimaryKey(id);
		industryBlacklistMapper
				.deleteByExample(IndustryBlacklistExample.newAndCreateCriteria().andIndustryIdEqualTo(id).example());
	}

	@Override
	public void update(Industry record) {
		boolean exists = industryMapper.countByExample(IndustryExample.newAndCreateCriteria()
				.andIdNotEqualTo(record.getId()).andNameEqualTo(record.getName()).example()) > 0;
		if (exists) {
			ExceptionUtils.throwServiceException("行业关键词名称不能重复");
		}
		record.setUpdateTime(new Date());
		if (record.getBlanklist()) {
			IndustryBlacklistExample industryBlacklistExample = new IndustryBlacklistExample();
			industryBlacklistExample.createCriteria().andIndustryIdEqualTo(record.getId());

			int count = (int) industryBlacklistMapper.countByExample(industryBlacklistExample);
			boolean industryExistsBlacklist = count > 0;

			if (industryExistsBlacklist) {
				IndustryBlacklistExample example = new IndustryBlacklistExample();
				example.createCriteria().andIndustryIdEqualTo(record.getId());
				IndustryBlacklist industryBlack = new IndustryBlacklist();
				industryBlack.setIndustryName(record.getName());
				industryBlacklistMapper.updateByExampleSelective(industryBlack, example);
			} else {
				IndustryBlacklist industryBlacklist = new IndustryBlacklist();
				industryBlacklist.setIndustryId(record.getId());
				industryBlacklist.setIndustryName(record.getName());
				industryBlacklistMapper.insertSelective(industryBlacklist);
			}
		} else {
			industryBlacklistMapper.deleteByExample(
					IndustryBlacklistExample.newAndCreateCriteria().andIndustryIdEqualTo(record.getId()).example());
		}
		industryMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Industry> selectByExample(String name) {
		if (StringUtils.isBlank(name)) {
			return selectAll();
		}

		IndustryExample example = new IndustryExample();
		example.createCriteria().andNameLike("%" + name + "%");
		List<Industry> matchResult = industryMapper.selectByExample(example);

		List<Industry> resultList = new ArrayList<Industry>();
		for (Industry industry : matchResult) {
			if (industry.getParentId() == 0) {
				resultList.add(industry);
			} else {
				resultList.add(topLevelIndustry(industry));
			}
		}
		return industryMapper.selectByExample(example);

	}

	private Industry topLevelIndustry(Industry child) {

		Industry result = null;
		if (child.getParentId() == 0) {
			return result;
		}

		result = industryMapper.selectByPrimaryKey(child.getParentId());
		result.setChildren(Arrays.asList(child));

		if (result.getParentId() != 0) {
			result = topLevelIndustry(result);
		}
		return result;
	}

	private List<Industry> selectAll() {
		return selectAll(0);
	}

	private List<Industry> selectAll(Integer id) {

		IndustryExample example = new IndustryExample();
		if (id == 0) {
			example.createCriteria().andParentIdEqualTo(0);
		} else {
			example.createCriteria().andParentIdEqualTo(id);
		}

		List<Industry> parentIndustryList = industryMapper.selectByExample(example);
		for (Industry parent : parentIndustryList) {
			parent.setChildren(selectAll(parent.getId()));
		}
		return parentIndustryList;
	}

	@Override
	public Industry loadById(Integer id) {
		return industryMapper.selectByPrimaryKey(id);
	}

	@Override
	public void addToBlacklist(Integer id) {
		industryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Industry> selectPrimaryIndustry() {
		IndustryExample example = new IndustryExample();
		example.createCriteria().andParentIdEqualTo(0);
		return industryMapper.selectByExample(example);
	}

	@Override
	public boolean industryNameIsExist(String name) {
		IndustryExample industry = new IndustryExample();
		industry.createCriteria().andNameEqualTo(name);
		long count = industryMapper.countByExample(industry);
		return count > 0;

	}

}
