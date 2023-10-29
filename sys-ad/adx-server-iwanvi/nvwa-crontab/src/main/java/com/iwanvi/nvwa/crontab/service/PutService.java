package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface PutService {

	Put getPut(Integer objectId);

	void checkUnstartState();

	void resetPutLimitState();

	void checkExpiredState();

	List<Put> getValidPutByPid(Integer id);

	PubRecord buildPubRecord(Put put, List<Entity> entities, OpType kadd);

	void buildPutSend(Integer id, OpType opType);

}
