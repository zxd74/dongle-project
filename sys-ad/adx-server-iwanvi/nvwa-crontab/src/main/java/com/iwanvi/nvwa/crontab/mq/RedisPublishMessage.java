package com.iwanvi.nvwa.crontab.mq;

public interface RedisPublishMessage {

	public void sendMessage(byte[] message);
}
