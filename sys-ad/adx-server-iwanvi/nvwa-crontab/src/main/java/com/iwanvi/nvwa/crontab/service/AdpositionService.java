package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface AdpositionService {

	List<AdPosition> getAdpositions();

	AdPosition getAdPosition(Integer adPosition);

	void buildPosSend(Integer objectId, OpType opType);

	List<AdPosition> getValidPosList();

	PubRecord buildPubRecord(AdPosition position, OpType kadd);

	PubRecord buildCommonPubRecord(AdPosition adCollection, OpType kadd);

	void refreshCollection(Integer objectId);

	void buildCollectionSend(Integer objectId, OpType opType);

}
