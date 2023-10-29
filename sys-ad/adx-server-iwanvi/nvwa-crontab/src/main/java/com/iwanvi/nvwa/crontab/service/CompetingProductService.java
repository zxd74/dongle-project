package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface CompetingProductService {
	
	List<CompetingProducts> getList();

	PubRecord buildPubRecord(CompetingProducts competingProducts, OpType kadd, EntityType entityType);

	void buildCompetingProductsSend(Integer objectId, OpType opType);

}
