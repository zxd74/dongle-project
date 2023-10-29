package com.iwanvi.nvwa.openapi.dsp.utils;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 线程分发器
 * 
 */
public class ThreadDistribution {

	protected final Log log = LogFactory.getLog(getClass());

	private static ThreadDistribution instance = null;

	private final ExecutorService executor;

	private final ScheduledExecutorService scheduler;

	private final AtomicBoolean closed = new AtomicBoolean(false);

	private ThreadDistribution(int initCount) {
		executor = new ThreadPoolExecutor(initCount, 100, 5, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(100000), new ThreadPoolExecutor.CallerRunsPolicy());
		scheduler = Executors.newScheduledThreadPool(5);
	}

	synchronized public static ThreadDistribution getInstance(int initCount) {
		if (instance == null) {
			instance = new ThreadDistribution(initCount);
		}
		return instance;
	}

	synchronized public static ThreadDistribution getInstance() {
		if (instance == null) {
			instance = new ThreadDistribution(10);
		}
		return instance;
	}

	public ScheduledExecutorService getScheduler() {
		return this.scheduler;
	}

	/**
	 * 获取新的线程
	 * 
	 * @param work
	 * @return
	 */
	public void doWork(Runnable work) {
		if (closed.get())
			return;

		if (work == null) {
			log.warn("work is empty!");
			throw new NullPointerException();
		}
		try {
			executor.execute(work);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public <T> void submit(Callable<T> task) {
		if (closed.get())
			return;

		if (task == null) {
			log.warn("task is empty!");
			throw new NullPointerException();
		}
		try {
			executor.submit(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Future<String> submit4Futrue(Callable<String> task) {
		Future<String> future = null;
		if (closed.get())
			return null;

		if (task == null) {
			log.warn("task is empty!");
			throw new NullPointerException();
		}
		try {
			future = executor.submit(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return future;
	}

	public Future<Map<String, Object>> submit4sdk(Callable<Map<String, Object>> task) {
		Future<Map<String, Object>> future = null;
		if (closed.get())
			return null;

		if (task == null) {
			log.warn("task is empty!");
			throw new NullPointerException();
		}
		try {
			future = executor.submit(task);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return future;
	}

	public void shutdown() {
		if (closed.compareAndSet(false, true)) {
			executor.shutdownNow();
			scheduler.shutdownNow();
		}
	}
}
