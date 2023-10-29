/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.boot;

import static com.iwanvi.adserv.ngx.util.Constants.DISPLAY_CHANNEL;
import static com.iwanvi.adserv.ngx.util.Constants.MINERVA_CHANNEL;
import static java.lang.String.valueOf;
import static java.lang.System.setProperty;
import static org.apache.commons.lang3.math.NumberUtils.toInt;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.f2time.albatross.commons.util.NamedThreadFactory;
import com.f2time.albatross.rpc.server.AlbatrossRpcServer;
import com.iwanvi.adserv.ngx.notification.DataSyncMsgListener;
import com.iwanvi.adserv.ngx.notification.ImpMsgListener;
import com.iwanvi.adserv.ngx.repo.Repository;
import com.iwanvi.adserv.ngx.repo.RepositoryFactory;
import com.iwanvi.adserv.ngx.service.BiddingServiceImpl;
import com.iwanvi.adserv.ngx.service.BuiltinServiceImpl;
import com.iwanvi.adserv.ngx.util.RedisTemplate;
import com.iwanvi.adserv.ngx.util.RedisUtils;
import com.iwanvi.adserv.ngx.util.ThreadPools;
import com.iwanvi.nvwa.proto.NotifyMsgProto.NotifyMsg;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

import ai.houyi.dorado.rest.server.DoradoServerBuilder;

/**
 * Bidding rpc server实现，对外提供RTB竞价服务
 * 
 * @author wangwp
 */
public class Application {
	private static final Logger LOG = LoggerFactory.getLogger("AdservNgxBoot");

	private static final int DEFAULT_PORT = 13321;

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		if (args.length > 0) {
			port = toInt(args[0], DEFAULT_PORT);
		}
		settingSysProps(port);
		prepareRepoTasks();
		subRedisTopics();
		startRpcServer(port);
	}

	private static void settingSysProps(int port) {
		// 优先使用IPV4
		setProperty("java.net.preferIPv4Stack", "true");
		setProperty("module", valueOf(port));
		setProperty("minerva.listen.port", valueOf(port));
	}

	private static void startRpcServer(final int port) {
		ThreadPoolExecutor pool = (ThreadPoolExecutor) ThreadPools.getDspRouterExecutor();
		pool.prestartAllCoreThreads();
		LOG.info("预启动流量分发线程池...OK");

		new NamedThreadFactory("AdservNgxBoot").newThread(() -> {
			AlbatrossRpcServer server = new AlbatrossRpcServer(port);
			server.registerRpcService(new BiddingServiceImpl());
			server.registerRpcService(new BuiltinServiceImpl());
			server.start();
		}).start();

		new NamedThreadFactory("AdservNgxHttpBoot").newThread(() -> {
			int nCpu = Runtime.getRuntime().availableProcessors();
			DoradoServerBuilder.forPort(port + 5).acceptors(1).ioWorkers(nCpu).backlog(128).minWorkers(nCpu)
					.maxWorkers(nCpu).scanPackages("com.iwanvi.adserv.ngx.controller").build().start();
		}).start();
	}

	private static void subRedisTopics() {
		new Thread(() -> RedisUtils.getRedisClient().subscribe(new DataSyncMsgListener(), MINERVA_CHANNEL)).start();
		new Thread(() -> RedisUtils.getRedisClient().subscribe(new ImpMsgListener(), DISPLAY_CHANNEL)).start();
	}

	private static void prepareRepoTasks() {
		final Repository repository = RepositoryFactory.getRepository();
		// 系统启动的时候从本地数据文件中恢复数据到引擎内存中
		repository.reload();
		// 启动一个定时任务，每5秒持久化引擎数据到磁盘文件
		ThreadPools.schedule(() -> repository.store(), 5, 5, TimeUnit.SECONDS);
		ThreadPools.schedule(() -> {
			RedisTemplate.executeQuietly((r) -> r.publish(MINERVA_CHANNEL,
					NotifyMsg.newBuilder().setOpType(OpType.kPing).build().toByteArray()));
		}, 5, 10, TimeUnit.SECONDS);
	}
}
