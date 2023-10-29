package com.iwanvi.adserv.ngx.util;

import java.security.MessageDigest;

public interface Hashing {

	ThreadLocal<MessageDigest> md5_holder = new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("MD5");
			} catch (Throwable ex) {
			}
			return null;
		}
	};

	ThreadLocal<MessageDigest> sha1_holder = new ThreadLocal<MessageDigest>() {
		@Override
		protected MessageDigest initialValue() {
			try {
				return MessageDigest.getInstance("SHA1");
			} catch (Throwable ex) {
			}
			return null;
		}
	};

	Hashing MD5 = (in) -> md5_holder.get().digest(in);

	Hashing SHA1 = (in) -> sha1_holder.get().digest(in);

	byte[] hash(byte[] data);
}
