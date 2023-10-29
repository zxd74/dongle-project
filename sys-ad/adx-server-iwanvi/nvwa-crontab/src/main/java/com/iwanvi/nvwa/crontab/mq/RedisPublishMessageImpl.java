package com.iwanvi.nvwa.crontab.mq;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("redisPublishMessage")
public class RedisPublishMessageImpl implements RedisPublishMessage{
	
	private RedisTemplate<String, byte[]> redisTemplate;
	private String channelName;
	
	@Override
	public void sendMessage(byte[] message){
		redisTemplate.convertAndSend(channelName, message);
	}
	public void setRedisTemplate(RedisTemplate<String, byte[]> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}	
}
