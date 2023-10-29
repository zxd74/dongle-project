package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AppPkgBlacklistMapper;
import com.iwanvi.nvwa.dao.model.AppPkgBlacklist;
import com.iwanvi.nvwa.dao.model.AppPkgBlacklist.Column;
import com.iwanvi.nvwa.dao.model.AppPkgBlacklistExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppPkgBlacklistService;
import com.iwanvi.nvwa.web.vo.AppPkgBlacklistQueryReq;

@Service
public class AppPkgBlacklistServiceImpl implements AppPkgBlacklistService {
	@Autowired
	private AppPkgBlacklistMapper appPkgBlacklistMapper;

	@Override
	public void delete(Integer id) {
//		appPkgBlacklistMapper.deleteByPrimaryKey(id);
		AppPkgBlacklist appPkgBlacklist = AppPkgBlacklist.builder().id(id).isDeleted(true).build();
		appPkgBlacklistMapper.updateByPrimaryKeySelective(appPkgBlacklist);
	}

	@Override
	public List<AppPkgBlacklist> select(String name) {
		AppPkgBlacklistExample example = new AppPkgBlacklistExample();
		AppPkgBlacklistExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);

		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
			example.or(example.createCriteria().andAppNameLike("%" + name + "%"));
		}

		return appPkgBlacklistMapper.selectByExample(example);
	}

	@Override
	public Page<AppPkgBlacklist> selectPage(AppPkgBlacklistQueryReq queryReq) {
		AppPkgBlacklistExample example = queryReq.toExample();

		int total = (int) appPkgBlacklistMapper.countByExample(example);
		List<AppPkgBlacklist> dataList = appPkgBlacklistMapper.selectByExample(example);

		return Page.create(total, queryReq.getPageSize(), dataList);
	}

	@Override
	public void insert(List<AppPkgBlacklist> records) {
		List<Integer> appPkgIdList = records.stream().map(apbl -> apbl.getAppPkgId()).collect(Collectors.toList());
		Map<Integer, AppPkgBlacklist> appPkgBlacklistsHolder = new HashMap<Integer, AppPkgBlacklist>();
		records.forEach(r -> appPkgBlacklistsHolder.put(r.getAppPkgId(), r));

		List<AppPkgBlacklist> appPkgBlacklists = appPkgBlacklistMapper
				.selectByExample(AppPkgBlacklistExample.newAndCreateCriteria().andAppPkgIdIn(appPkgIdList).example());

		List<AppPkgBlacklist> insertList = new ArrayList<AppPkgBlacklist>();
		for (AppPkgBlacklist appPkgBlacklist : appPkgBlacklists) {
			if (appPkgBlacklist.getIsDeleted()) {
				AppPkgBlacklist _new = appPkgBlacklistsHolder.get(appPkgBlacklist.getAppPkgId());
				_new.setIsDeleted(false);
				_new.setId(appPkgBlacklist.getId());
				appPkgBlacklistMapper.updateByPrimaryKeySelective(_new);
			}
			appPkgBlacklistsHolder.remove(appPkgBlacklist.getAppPkgId());
		}

		insertList.addAll(appPkgBlacklistsHolder.values());
		if (!insertList.isEmpty()) {
			appPkgBlacklistMapper.batchInsertSelective(insertList, Column.appPkgId, Column.name, Column.appName);
		}
	}

	@Override
	public boolean AppPkgBlacklistPkgName(String name) {
		AppPkgBlacklistExample blacklist = new AppPkgBlacklistExample();
		blacklist.createCriteria().andNameEqualTo(name);
		long count = appPkgBlacklistMapper.countByExample(blacklist);
		return count > 0;
	}
}
