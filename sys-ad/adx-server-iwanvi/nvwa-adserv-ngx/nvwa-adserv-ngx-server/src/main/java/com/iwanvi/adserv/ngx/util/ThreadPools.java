/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f2time.albatross.commons.util.NamedThreadFactory;
import com.iwanvi.adserv.ngx.cfg.MinervaCfg;

/**
 * 
 * @author wangwp
 */
public class ThreadPools {
	private static final Logger LOG = LoggerFactory.getLogger("AdNgxThreadPools");

	private static MinervaCfg cfg = MinervaCfg.get();

	static class DspRouterExecutorHolder {
		static final ExecutorService dsp_router_pool = new ThreadPoolExecutor(cfg.getDspRouterThreadPoolMinSize(), 1024,
				60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),
				new NamedThreadFactory("dspRouterWorker", true), new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
						LOG.warn("dsprouter pool rejected task, discard it");
					}
				});
	}

	static class AdSearchThreadPoolHolder {
		static final ExecutorService ad_search_threadpool = new ThreadPoolExecutor(2, 5, 120, TimeUnit.SECONDS,
				new SynchronousQueue<>(), new NamedThreadFactory("adsearch", true),
				new ThreadPoolExecutor.CallerRunsPolicy());
	}

	static class SingleThreadPoolHolder {
		static final ExecutorService single_thread_threadpool = Executors.newSingleThreadExecutor();
	}

	static class DefaultThreadPoolHolder {
		static final int nCpu = Runtime.getRuntime().availableProcessors();

		static final ExecutorService default_threadpool = new ThreadPoolExecutor(nCpu * 2, nCpu * 2, 120,
				TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(10000),
				new NamedThreadFactory("default-async-pool"), new ThreadPoolExecutor.CallerRunsPolicy());
	}

	static class ScheduledThreadPoolHolder {
		static final ScheduledExecutorService scheduled_threadpool = Executors.newScheduledThreadPool(5,
				new NamedThreadFactory("adngx-scheduler", true));
	}

	public static void scheduleFixDelayTask(Runnable task, long delay, TimeUnit unit) {
		ScheduledThreadPoolHolder.scheduled_threadpool.schedule(task, delay, unit);
	}

	public static void schedule(Runnable task, long initialDelay, long period, TimeUnit unit) {
		ScheduledThreadPoolHolder.scheduled_threadpool.scheduleAtFixedRate(task, initialDelay, period, unit);
	}

	public static void doTask(Runnable task) {
		SingleThreadPoolHolder.single_thread_threadpool.submit(task);
	}

	public static void concurrentDoTask(Runnable task) {
		DefaultThreadPoolHolder.default_threadpool.submit(task);
	}

	public static <T> List<T> invokeAll(List<Callable<T>> tasks) {
		List<T> invokeResults = new ArrayList<>();
		try {
			List<Future<T>> futures = DefaultThreadPoolHolder.default_threadpool.invokeAll(tasks);
			for (Future<T> future : futures) {
				invokeResults.add(future.get());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		return invokeResults;
	}

	public static <T> List<T> concurrentSearchAds(List<Callable<T>> tasks) {
		List<T> invokeResults = new ArrayList<>();
		try {
			List<Future<T>> futures = AdSearchThreadPoolHolder.ad_search_threadpool.invokeAll(tasks);
			for (Future<T> future : futures) {
				invokeResults.add(future.get());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		}
		return invokeResults;
	}

	public static ExecutorService getDspRouterExecutor() {
		return DspRouterExecutorHolder.dsp_router_pool;
	}
}
