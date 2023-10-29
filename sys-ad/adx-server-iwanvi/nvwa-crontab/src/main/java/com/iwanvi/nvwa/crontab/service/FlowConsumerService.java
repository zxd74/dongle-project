package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface FlowConsumerService {

	void buildPosSend(Integer objectId, OpType opType);

	List<FlowConsumer> getAllFc();

	PubRecord buildFlowConsumer(FlowConsumer flowConsumer, OpType kadd);

}
