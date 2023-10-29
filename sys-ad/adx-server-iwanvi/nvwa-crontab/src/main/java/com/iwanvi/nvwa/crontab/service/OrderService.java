package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface OrderService {

	Orders getOrder(Integer objectId);

	void buildOrderSend(Integer objectId, OpType opType);

	List<Orders> getValidOrderByCustId(Integer id);

	PubRecord buildPubRecord(Orders order, OpType kadd);

}
