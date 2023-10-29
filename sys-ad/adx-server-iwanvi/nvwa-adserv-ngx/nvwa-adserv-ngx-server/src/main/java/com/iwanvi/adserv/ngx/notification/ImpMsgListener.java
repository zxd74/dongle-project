/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.notification;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.repo.ImpRepositoryFactory;
import com.iwanvi.adserv.ngx.util.Constants;
import com.iwanvi.adserv.ngx.util.MurmurHash;
import com.iwanvi.nvwa.proto.CommonProto.FrequencyPeriod;

import redis.clients.jedis.JedisPubSub;

/**
 * 曝光消息消费者
 * 
 * @author wangwp
 *
 */
public class ImpMsgListener extends JedisPubSub {
	static final Logger LOG = LoggerFactory.getLogger("MinervaNotifyConsumer");
	static final AtomicInteger index = new AtomicInteger();

	@Override
	public void onMessage(String channel, String message) {
		if (StringUtils.isBlank(message)) {
			return;
		}

		if (Math.abs(index.getAndIncrement() % 200) == 0) {
			LOG.info("Received impression notify message: {}", message);
		}
		LOG.debug("==received impression notify message: {}==",message);
		// 消息格式：unitId:deviceId_impressions_period
		String[] msgFields = message.split(Constants.SIGN_UNDERLINE);
		if (msgFields.length != 3) {
			LOG.warn("Invalid notify message for impression, {}", message);
			return;
		}

		int key = MurmurHash.hash32(msgFields[0], (int) Constants.HASHING_SEED);
		int impressions = NumberUtils.toInt(msgFields[1], 0);
		int period = NumberUtils.toInt(msgFields[2], 0);

		FrequencyPeriod frequencyPeriod = FrequencyPeriod.forNumber(period);
		switch (frequencyPeriod) {
		case kFrequencyPeriodDay:
			ImpRepositoryFactory.getRepository().putDaily(key, impressions);
			break;
		case kFrequencyPeriodWeek:
			ImpRepositoryFactory.getRepository().putWeekly(key, impressions);
			break;
		case kFrequencyPeriodMonth:
			ImpRepositoryFactory.getRepository().putMonthly(key, impressions);
			break;
		default:
			break;
		}
	}
}
