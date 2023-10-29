package com.iwanvi.nvwa.common.redis.mq;

public interface RedisQProducer {
    void send(String message);
}
