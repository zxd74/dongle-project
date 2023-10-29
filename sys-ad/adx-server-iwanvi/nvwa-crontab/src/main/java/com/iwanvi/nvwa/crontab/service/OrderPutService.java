package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface OrderPutService {

	void buildOrderPutSend(Integer id, OpType opType);

	OrderPut getOrderPut(Integer objectId);

	List<OrderPut> getValidOrderPutByOid(Integer id);

	PubRecord buildPubRecord(OrderPut orderPut, List<Entity> entities, OpType kadd);

	void checkExpiredState();

	void resetPutLimitState();

	void checkOrderPutstartState();

}
