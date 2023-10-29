package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.service.WarningSettingService;
import com.iwanvi.nvwa.dao.WarningSettingMapper;
import com.iwanvi.nvwa.dao.model.WarningSetting;
import com.iwanvi.nvwa.dao.model.WarningSettingExample;

@Service
public class WarningSettingServiceImpl implements WarningSettingService {


	@Autowired
	private WarningSettingMapper warningSettingMapper;
	
	@Override
	public void buildWarningSettingSend(Integer objectId) {
		WarningSetting setting = warningSettingMapper.selectByPrimaryKey(objectId);
		//TODO
	}

	@Override
	public List<WarningSetting> getList() {
		WarningSettingExample example = new WarningSettingExample();
		example.createCriteria().andStatusEqualTo(Constants.STATE_VALID);
		return warningSettingMapper.selectByExample(example);
	}

}
