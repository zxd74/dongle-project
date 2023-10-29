package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.FlowControl;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface FlowControlServicce {

	void batchInsert(FlowControl flowControl);

	Page<FlowControl> getPage(FlowControl flowControl);

	void flowSwitch(Integer status, Integer id);

	FlowControl getOne(Integer id);

	void batchUpdate(FlowControl flowControl);

	void update(FlowControl flowControl);

	void getDel(Integer id);

}
