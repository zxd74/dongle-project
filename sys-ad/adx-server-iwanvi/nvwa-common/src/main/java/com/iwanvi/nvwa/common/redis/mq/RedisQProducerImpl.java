package com.iwanvi.nvwa.common.redis.mq;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisQProducerImpl implements RedisQProducer {

    @Autowired
    private RedisDao redisDao;
    private String queueName;

    public void send(String message){
        redisDao.leftPush(queueName, message);
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
