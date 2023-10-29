package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.IndustryBlacklistMapper;
import com.iwanvi.nvwa.dao.IndustryMapper;
import com.iwanvi.nvwa.dao.model.Industry;
import com.iwanvi.nvwa.dao.model.IndustryBlacklist;
import com.iwanvi.nvwa.dao.model.IndustryBlacklistExample;
import com.iwanvi.nvwa.dao.model.IndustryExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.IndustryBlacklistService;
import com.iwanvi.nvwa.web.vo.IndustryBlacklistQueryReq;

@Service
public class IndustryBlacklistServiceImpl implements IndustryBlacklistService {
	@Autowired
	private IndustryBlacklistMapper industryBlacklistMapper;
	@Autowired
	private IndustryMapper industryMapper;

	@Override
	public void insert(IndustryBlacklist record) {
		industryBlacklistMapper.insertSelective(record);
	}

	@Override
	public void delete(Integer id) {
		industryBlacklistMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void update(IndustryBlacklist record) {
		industryBlacklistMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<IndustryBlacklist> selectByExample(String name) {
		IndustryBlacklistExample ibe = new IndustryBlacklistExample();
		if (StringUtils.isNotBlank(name)) {
			ibe.createCriteria().andIndustryNameLike("%" + name + "%");
		}
		return industryBlacklistMapper.selectByExample(ibe);
	}

	@Override
	public void updateState(Integer id, int state) {
		IndustryBlacklist ibl = new IndustryBlacklist();
		ibl.setState(state);
		ibl.setId(id);
		industryBlacklistMapper.updateByPrimaryKeySelective(ibl);
	}

	@Override
	public IndustryBlacklist loadById(Integer id) {
		return industryBlacklistMapper.selectByPrimaryKey(id);
	}

	@Override
	public Page<IndustryBlacklist> selectPage(IndustryBlacklistQueryReq queryReq) {
		IndustryBlacklistExample example = queryReq.toExample();

		int total = (int) industryBlacklistMapper.countByExample(example);
		List<IndustryBlacklist> dataList = industryBlacklistMapper.selectByExample(example);

		return Page.create(total, queryReq.getPageSize(), dataList);
	}

	@Override
	public void addToBlacklist(List<Integer> addToIndustryIdList) {
		industryBlacklistMapper.deleteByExample(new IndustryBlacklistExample());
		if (addToIndustryIdList == null || addToIndustryIdList.isEmpty()) {
			return;
		}
		List<Industry> industryList = industryMapper
				.selectByExample(IndustryExample.newAndCreateCriteria().andIdIn(addToIndustryIdList).example());

		List<IndustryBlacklist> blList = new ArrayList<IndustryBlacklist>();
		for (Industry industry : industryList) {
			IndustryBlacklist bl = new IndustryBlacklist();
			bl.setIndustryId(industry.getId());
			bl.setIndustryName(industry.getName());

			blList.add(bl);
		}

		industryBlacklistMapper.batchInsertSelective(blList, IndustryBlacklist.Column.industryId,
				IndustryBlacklist.Column.industryName);
	}

	public List<Integer> getIndustryBlacklist() {
		return industryBlacklistMapper.selectByExample(new IndustryBlacklistExample()).stream()
				.map(IndustryBlacklist::getIndustryId).collect(Collectors.toList());
	}

}
