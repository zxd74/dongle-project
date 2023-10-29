package com.iwanvi.nvwa.crontab.redis.impl;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class ByteArrayRedisSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		// TODO Auto-generated method stub
		return (byte[])t;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		// TODO Auto-generated method stub
		return bytes;
	}

}
