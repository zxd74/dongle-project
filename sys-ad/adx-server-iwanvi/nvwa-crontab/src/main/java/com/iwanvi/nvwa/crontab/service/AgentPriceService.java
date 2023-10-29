package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AgentPrice;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface AgentPriceService {

	List<AgentPrice> getAgentPrice(Put put, Integer aid, Integer industryType);

	PubRecord buildPubRecord(AgentPrice agentPrice, OpType kadd);

	void buildAgentPriceSend(Integer objectId, OpType opType);

	List<AgentPrice> getOrderAgentPrice(OrderPut orderPut, Integer aid, Integer industryType);

	void buildAgentPriceSend(Put put, OpType opType);

}
