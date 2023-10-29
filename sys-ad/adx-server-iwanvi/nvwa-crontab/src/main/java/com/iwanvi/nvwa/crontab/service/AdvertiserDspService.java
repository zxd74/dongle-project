package com.iwanvi.nvwa.crontab.service;

import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface AdvertiserDspService {

	void buildPosSend(Integer objectId, OpType opType);

}
