package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.WarningSetting;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface WarningSettingService {

	void update(WarningSetting warningSetting);

	void updateStatus(Integer id, Integer status);

	WarningSetting get(Integer id);

	Page<WarningSetting> getPage(WarningSetting warningSetting);

	WarningSetting initPage(Integer aid, Integer cid);

}
