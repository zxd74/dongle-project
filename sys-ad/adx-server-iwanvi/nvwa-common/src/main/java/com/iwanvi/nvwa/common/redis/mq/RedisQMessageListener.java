package com.iwanvi.nvwa.common.redis.mq;

public interface RedisQMessageListener {

    void onMessage(String message);
}
