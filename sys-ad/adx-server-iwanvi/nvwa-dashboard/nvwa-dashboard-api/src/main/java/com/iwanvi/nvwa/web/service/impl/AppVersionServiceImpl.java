package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.FluentIterable;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.AppVersionMapper;
import com.iwanvi.nvwa.dao.FlowControlMapper;
import com.iwanvi.nvwa.dao.model.AppVersion;
import com.iwanvi.nvwa.dao.model.AppVersion.Column;
import com.iwanvi.nvwa.dao.model.AppVersionExample;
import com.iwanvi.nvwa.dao.model.FlowControl;
import com.iwanvi.nvwa.dao.model.FlowControlExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AppVersionService;
import com.iwanvi.nvwa.web.service.FlowControlServicce;
import com.iwanvi.nvwa.web.vo.AppVersionQueryReq;

@Service
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	private AppVersionMapper appVersionMapper;

	@Autowired
	private FlowControlMapper flowControlMapper;

	@Autowired
	private FlowControlServicce flowControlService;

	@Override
	@Transactional
	public void insert(AppVersion record) {
		String versionName = record.getName();
		if (StringUtils.isBlank(versionName))
			return;

		String[] versionNames = StringUtils.split(versionName, Constants.SIGN_COMMA);
		List<String> versionNameList = new ArrayList<>(Arrays.asList(versionNames));
		List<AppVersion> existsAppVersions = appVersionMapper.selectByExample(
				AppVersionExample.newAndCreateCriteria().andNameIn(Arrays.asList(versionNames)).example());
		
		for (AppVersion existAppVersion : existsAppVersions) {
			if (existAppVersion.getIsDeleted() == true) {
				appVersionMapper.updateByPrimaryKeySelective(
						AppVersion.builder().id(existAppVersion.getId()).isDeleted(false).build());
			}
			versionNameList.remove(existAppVersion.getName());
		}

		List<AppVersion> appVersions = new ArrayList<AppVersion>();
		for (String version : versionNameList) {
			appVersions.add(AppVersion.builder().name(version).build());
		}
		
		if (!appVersions.isEmpty()) {
			appVersionMapper.batchInsertSelective(appVersions, Column.name);
		}
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		AppVersion appVersion = AppVersion.builder().id(id).isDeleted(true).build();
		appVersionMapper.updateByPrimaryKeySelective(appVersion);
		updateFlowControlStatus(id); //删除后将流量控制状态关闭
	}

	private void updateFlowControlStatus(Integer id) {
		FlowControlExample controlExample = new FlowControlExample();
		controlExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andVersionIdEqualTo(id);
		List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		list.forEach(action -> {
			flowControlService.flowSwitch(Constants.STATE_INVALID, action.getId());
		});
	}

	@Override
	public List<AppVersion> select(String name) {
		AppVersionExample example = new AppVersionExample();
		AppVersionExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		return appVersionMapper.selectByExample(example);
	}

	@Override
	public Page<AppVersion> selectPage(AppVersionQueryReq queryReq) {
		AppVersionExample example = queryReq.toExample();
		int total = (int) appVersionMapper.countByExample(example);
		List<AppVersion> dataList = appVersionMapper.selectByExample(example);

		return Page.create(total, queryReq.getPageSize(), dataList);
	}

	@Override
	public List<AppVersion> getAllByFc() {
		FlowControlExample controlExample = new FlowControlExample();
		controlExample.createCriteria()/* .andStatusEqualTo(Constants.STATE_VALID) */;
		List<FlowControl> list = flowControlMapper.selectByExample(controlExample);
		if (CollectionUtils.isEmpty(list))
			return null;
		List<Integer> vids = FluentIterable.from(list).transform((FlowControl flowControl) -> {
			return flowControl.getVersionId();
		}).toList();
		vids = vids.stream().distinct().collect(Collectors.toList());
		AppVersionExample appVersionExample = new AppVersionExample();
		appVersionExample.createCriteria().andIdIn(vids).andIsDeletedEqualTo(false);
		List<AppVersion> result = appVersionMapper.selectByExample(appVersionExample);
		return result;
	}

	@Override
	public boolean AppVersionNameIsExist(String name) {
		AppVersionExample appVersion = new AppVersionExample();
		appVersion.createCriteria().andNameEqualTo(name);
		long count = appVersionMapper.countByExample(appVersion);
		return count > 0;
	}

}
