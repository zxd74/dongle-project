package com.iwanvi.nvwa.crontab.service;

import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface SysConfigService {

	PubRecord buildPubRecord(OpType kadd);

	void buildConfigSend(OpType opType);

}
