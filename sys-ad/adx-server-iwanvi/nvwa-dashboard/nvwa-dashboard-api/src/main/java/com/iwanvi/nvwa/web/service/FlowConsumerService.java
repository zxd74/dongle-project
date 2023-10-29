package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface FlowConsumerService {

	Page<FlowConsumer> geFcPage(FlowConsumer fc, Integer currentPage, Integer pageSize);

	FlowConsumer getFlowConsumerID(Integer id);

	void insertFc(FlowConsumer fc);

	void updateFC(FlowConsumer fc);

	void deFcById(Integer id);

	Map<String, List<AdPosition>> getAkAdposdbyTerminal(Integer id, Integer terminal);

	List<FlowConsumer> getSdkList();

	Map<String, Object> getFlowDx(Map<String, Object> params);

	void setFlowDx(Map<String, Object> map);

	List<FlowConsumer> getListByType(FlowConsumer fc);

	List<FlowConsumer> getSdkListToApp(String cids);

	List<FlowConsumer> getListByMapped(Integer pid);

	List<FlowConsumer> listByApp(String appIds);

}
