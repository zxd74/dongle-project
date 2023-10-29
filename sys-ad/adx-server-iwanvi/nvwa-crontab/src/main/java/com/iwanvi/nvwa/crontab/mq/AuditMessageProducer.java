package com.iwanvi.nvwa.crontab.mq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iwanvi.nvwa.common.redis.mq.RedisQProducer;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto;

/**
 * ADX 提交给媒体审核
 * 
 * @author admin
 *
 */
public class AuditMessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(AuditMessageProducer.class);

	private RedisQProducer redisQProducer;

	private RedisPublishMessage redisMessageProducer;

	/**
	 * 消息格式：{"entityId":1,"matchAdx":"60,61","objectType":1}
	 */

	public void send(Integer entityId, String matchAdxs) {
		send(entityId, matchAdxs, Constants.OBJ_ENTITY);
	}

	public void send(Integer entityId, String matchAdxs, Integer objectType) {
		if (entityId != null && StringUtils.isNotBlank(matchAdxs)) {
			try {
				Map<String, Object> messageMap = new HashMap<String, Object>();
				messageMap.put("entityId", entityId);
				messageMap.put("matchAdxs", matchAdxs);
				messageMap.put("objectType", objectType);
				String message = JsonUtils.TO_JSON(messageMap);
				redisQProducer.send(message);
				logger.info("send message to audit success! message: {}", message);
			} catch (Exception e) {
				logger.error("send message to audit error!", e);
			}
		}
	}

	public void send(EntityType entityType, Integer entityId, NotifyMsgProto.OpType opType) {
		send(entityType, entityId, opType, null);
	}

	public void send(EntityType entityType, Integer entityId, NotifyMsgProto.OpType opType, String fileName) {
		if (entityType != null && opType != null) {
			try {
				NotifyMsgProto.NotifyMsg.Builder notifyMsgBuilder = NotifyMsgProto.NotifyMsg.newBuilder();
				if (entityId != null) {
					notifyMsgBuilder.setEntityId(entityId);
				}
				if (fileName != null) {
					notifyMsgBuilder.setFileName(fileName);
				}
				notifyMsgBuilder.setEntityType(entityType);
				notifyMsgBuilder.setOpType(opType);
				redisMessageProducer.sendMessage(notifyMsgBuilder.build().toByteArray());
				logger.info("send message to audit success! message: {}", notifyMsgBuilder.build().toString());
			} catch (Exception e) {
				logger.error("send message to audit error!", e);
			}
		}
	}

	public void setRedisQProducer(RedisQProducer redisQProducer) {
		this.redisQProducer = redisQProducer;
	}

	public void setRedisMessageProducer(RedisPublishMessage redisMessageProducer) {
		this.redisMessageProducer = redisMessageProducer;
	}

}
