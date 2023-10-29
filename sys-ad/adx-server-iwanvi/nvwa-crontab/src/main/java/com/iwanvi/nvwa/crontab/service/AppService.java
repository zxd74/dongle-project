package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface AppService {

	PubRecord buildApp(Apps app, OpType kadd);

	List<Apps> getAllSspApps();

	void buildAppSend(Integer objectId, OpType opType);


}
