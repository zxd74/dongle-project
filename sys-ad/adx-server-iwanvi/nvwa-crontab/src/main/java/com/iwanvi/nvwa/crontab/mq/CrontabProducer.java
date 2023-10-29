package com.iwanvi.nvwa.crontab.mq;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.nvwa.common.redis.mq.RedisQProducer;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;

public class CrontabProducer {

	private static final Logger logger = LoggerFactory.getLogger(CrontabProducer.class);

	private RedisQProducer redisQProducer;

	// 消息格式：date_id 日期_crontabID

	public void send(Integer crontabId) {
		if (crontabId != null) {
			try {
				String message = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT) + Constants.SIGN_UNDERLINE
						+ crontabId;
				redisQProducer.send(message);
				logger.info("send message to audit success! message: {}", message);
			} catch (Exception e) {
				logger.error("send message to audit error!", e);
			}
		}
	}

	public void setRedisQProducer(RedisQProducer redisQProducer) {
		this.redisQProducer = redisQProducer;
	}
}
