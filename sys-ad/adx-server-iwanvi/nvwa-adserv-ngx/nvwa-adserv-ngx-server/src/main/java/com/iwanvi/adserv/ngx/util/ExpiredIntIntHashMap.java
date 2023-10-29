/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.carrotsearch.hppc.IntIntHashMap;

/**
 * 支持元素过期功能的{@link IntIntHashMap}实现
 * 
 * @author wangwp
 *
 */
public class ExpiredIntIntHashMap extends IntIntHashMap {
	private final DelayQueue<ExpiredIntIntHashMapItem> _expiredQueue;
	private ExpiredCallback callback;

	public ExpiredIntIntHashMap() {
		this._expiredQueue = new DelayQueue<>();
		startDaemon();
		callback = null;
	}

	public ExpiredIntIntHashMap(ExpiredCallback callback) {
		this();
		this.callback = callback;
	}

	private void startDaemon() {
		Thread daemon = new Thread(new IntIntHashMapItemExpiredTask(), "Expired-Daemon");
		daemon.start();
	}

	@Override
	public final int put(int key, int value) {
		throw new UnsupportedOperationException();
	}

	public int put(int key, int value, long timeout) {
		int r = super.put(key, value);
		// 这里不调用containKey方法来判断是否第一次put, 而是直接判断put返回值是否为0,
		// 主要是基于在这种使用场景下value必须大于0,所以如果返回
		// 0就可以确认是首次调用
		if (r == Constants.INT_ZERO) {
			_expiredQueue.put(new ExpiredIntIntHashMapItem(key, timeout));
		}
		return r;
	}

	public int put(int key, int value, long timeout, TimeUnit unit) {
		return put(key, value, unit.toMillis(timeout));
	}

	private class IntIntHashMapItemExpiredTask implements Runnable {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					ExpiredIntIntHashMapItem expiredItem = _expiredQueue.take();

					if (expiredItem != null) {
						remove(expiredItem.key);
						if (callback != null)
							callback.expired(expiredItem.key, ExpiredIntIntHashMap.this);
					}
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	private class ExpiredIntIntHashMapItem implements Delayed {
		private final int key;
		private final long triggerTime;

		public ExpiredIntIntHashMapItem(int key, long timeout) {
			this.key = key;
			this.triggerTime = System.currentTimeMillis() + timeout;
		}

		@Override
		public int compareTo(Delayed o) {
			ExpiredIntIntHashMapItem that = (ExpiredIntIntHashMapItem) o;
			if (this.triggerTime < that.triggerTime)
				return -1;
			if (this.triggerTime > that.triggerTime)
				return 1;
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(triggerTime - System.currentTimeMillis(), MILLISECONDS);
		}
	}

	public static void main(String[] args) throws Exception {
		ExpiredIntIntHashMap expiredMap = new ExpiredIntIntHashMap();
		expiredMap.put(1, 1, 3, TimeUnit.SECONDS);

		Thread.sleep(2000);
		System.out.println(expiredMap.get(1));
		Thread.sleep(2000);
		System.out.println(expiredMap.get(1));

		System.exit(0);
	}
}
