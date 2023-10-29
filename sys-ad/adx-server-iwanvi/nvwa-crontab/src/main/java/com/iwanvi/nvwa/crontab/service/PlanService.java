package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface PlanService {

	Plan getPlan(Integer objectId);

	void resetPlanLimitState();

	List<Plan> getValidPlanByUid(Integer id);

	PubRecord buildPubRecord(Plan plan, OpType kadd);

	void buildPlanSend(Integer objectId, OpType opType);

}
