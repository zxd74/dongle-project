package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface AreaGroupService {

	List<AreaGroup> getList();

	PubRecord buildPubRecord(AreaGroup areaGroup, OpType kadd, EntityType karealevel);

	void buildAreaGroupSend(Integer objectId, OpType opType);

}
