package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.OrderPutService;
import com.iwanvi.nvwa.crontab.service.OrderService;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.OrdersMapper;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.OrdersExample;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPlan;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrdersMapper ordersMapper;
	
	@Autowired
	private ByteArrayRedisDao byteArrayRedisDao;

	@Autowired
	private AuditMessageProducer auditMessageProducer;
	
	@Autowired
	private OrderPutMapper orderPutMapper;
	
	@Autowired
	private OrderPutService orderPutService;

	@Override
	public Orders getOrder(Integer objectId) throws ServiceException {
		return ordersMapper.selectByPrimaryKey(objectId);
	}

	@Override
	public void buildOrderSend(Integer id, OpType opType) {
		Orders orders = ordersMapper.selectByPrimaryKey(id);
		AdModelsProto.AdPlan order = buildOrder(orders, opType);
		if (order != null) {
			byteArrayRedisDao.set(Constants.REDIS_KEY_PREFIX_PLAN + id, order.toByteArray());
			logger.info("order cache to redis, id : {}", id);
			auditMessageProducer.send(EntityType.kAdPlan, id, opType);
			logger.info("crontab ampPlan message is send, id : {}, opType : {}", id, opType);
		}
		OrderPutExample orderPutExample = new OrderPutExample();
		orderPutExample.createCriteria().andOidEqualTo(orders.getId());
		List<OrderPut> orderList = orderPutMapper.selectByExample(orderPutExample);
		if(orderList.size() > 0){
			for(OrderPut o : orderList){
				orderPutService.buildOrderPutSend(o.getId(), opType);
			}
		}
	}

	private AdPlan buildOrder(Orders orders, OpType opType) {
		try {
			AdPlan.Builder ampPlanBuilder = AdPlan.newBuilder();
			ampPlanBuilder.setPlanId(orders.getId());
			if (!OpType.kDelete.equals(opType)) {
				if (Constants.STATE_VALID.equals(orders.getOrdersState())
						&& Constants.STATE_VALID.equals(orders.getRunState())) {
					ampPlanBuilder.setStatus(Constants.STATE_VALID);
					ampPlanBuilder.setAdvertiserId(orders.getCustId());
				} else {
					ampPlanBuilder.setStatus(Constants.STATE_INVALID);
				}
			}
			return ampPlanBuilder.build();
		} catch (Exception e) {
			logger.error("build order error! id: {}", orders.getId(), e);
			return null;
		}
	}

	@Override
	public List<Orders> getValidOrderByCustId(Integer id) {
		OrdersExample example = new OrdersExample();
		// 包含限额无效状态
		example.createCriteria().andCustIdEqualTo(id).andOrdersStateEqualTo(Constants.STATE_VALID)
				.andRunStateEqualTo(Constants.STATE_VALID);
		return ordersMapper.selectByExample(example);
	}

	@Override
	public PubRecord buildPubRecord(Orders order, OpType kadd) {
		AdModelsProto.AdPlan o = buildOrder(order, kadd);
		if (o != null) {
			AdModelsProto.PubRecord.Builder pubRecord = AdModelsProto.PubRecord.newBuilder();
			pubRecord.setData(o.toByteString());
			pubRecord.setEntityType(EntityType.kAdPlan);
			return pubRecord.build();
		}
		return null;
	}


}
