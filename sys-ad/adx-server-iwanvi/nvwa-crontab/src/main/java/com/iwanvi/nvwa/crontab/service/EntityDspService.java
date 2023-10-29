package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface EntityDspService {

	void buildPosSend(Integer objectId, OpType opType);

	List<DspCreative> buildEntiyDspList(Integer id);

	PubRecord buildDspCreative(DspCreative dspCreative, OpType kadd);
	
	List<AdPositionFloorPrice> buildAdPositionFloorPriceByDspCreative(DspCreative dspCreative);

}
