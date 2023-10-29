package com.iwanvi.nvwa.crontab.service;

import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface AkFlowSourceService {

	void buildAKAppSend(Integer objectId, OpType opType);

	PubRecord buildPubRecord(FlowSource flowSource, OpType kadd);

}
