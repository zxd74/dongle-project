package com.iwanvi.nvwa.openapi.dsp.bootstrap;

import org.apache.commons.lang3.math.NumberUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import ai.houyi.dorado.rest.server.DoradoServer;
import ai.houyi.dorado.rest.server.DoradoServerBuilder;

@SpringBootApplication
@MapperScan("com.iwanvi.nvwa.dao")
@ImportResource({ "spring/applicationContext.xml" })
public class HttpServer {

	private static final int DEFAULT_PORT = 9300;

	public static void main(String[] args) {
		try {
			int port = DEFAULT_PORT;
			if (args.length > 0) {
				port = NumberUtils.toInt(args[0], DEFAULT_PORT);
			}

			String[] packages = { "com.nvwa", "com.iwanvi" };
			//优化http server配置，设置backlog以及连接最大空闲时间
			DoradoServer server = DoradoServerBuilder.forPort(port)
					.scanPackages(packages).springOn().backlog(10000)
					.maxIdleTime(60).minWorkers(200).maxWorkers(200).build();

			server.start();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
