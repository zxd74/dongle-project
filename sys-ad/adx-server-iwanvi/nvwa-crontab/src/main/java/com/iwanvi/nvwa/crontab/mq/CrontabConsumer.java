package com.iwanvi.nvwa.crontab.mq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iwanvi.nvwa.common.redis.mq.RedisQMessageListener;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.service.CrontabService;


public class CrontabConsumer implements RedisQMessageListener {
	private static final Logger logger = LoggerFactory.getLogger(CrontabConsumer.class);

	@Autowired
	private CrontabService crontabService;

	@Override
	public void onMessage(String message) {
		logger.debug("Received message from redisMQ is: {}", message);
		if (StringUtils.isBlank(message)) {
			logger.error("Received message from redisMQ is null");
			throw new IllegalArgumentException("invalid message, must not be null");
		}
		List<String> args = StringUtils.str2List(message, Constants.SIGN_UNDERLINE);
		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMdd");
		if (args != null && args.size() > 1) {
			int id = StringUtils.toInt(args.get(1));
			if (simple.format(date).equals(args.get(0))) {
				crontabService.pub4Incremental(id);
			}
		}

	}
}
