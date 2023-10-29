package com.iwanvi.nvwa.pixel.connector.crontab.bootstrap;

import org.apache.commons.lang3.math.NumberUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import ai.houyi.dorado.rest.server.DoradoServer;
import ai.houyi.dorado.rest.server.DoradoServerBuilder;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.iwanvi.nvwa.dao")
@ImportResource({"spring/applicationContext.xml"})
public class HttpServer {

	private static final int DEFAULT_PORT = 9320;

	public static void main(String[] args) {
		try {
			int port = DEFAULT_PORT;
			if (args.length > 0) {
				port = NumberUtils.toInt(args[0], DEFAULT_PORT);
			}

			String[] packages = {"com.iwanvi"};
			DoradoServer server = DoradoServerBuilder.forPort(port)
					.scanPackages(packages).springOn().build();

			server.start();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
