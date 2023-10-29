package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.WarningSetting;

public interface WarningSettingService {

	void buildWarningSettingSend(Integer objectId);
	
	List<WarningSetting> getList();

}
