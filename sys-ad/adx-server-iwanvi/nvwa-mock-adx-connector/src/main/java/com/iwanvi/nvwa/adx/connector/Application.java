/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.adx.connector;

import org.apache.commons.lang3.math.NumberUtils;

import ai.houyi.dorado.rest.server.DoradoServerBuilder;

/**
 * 
 * @author wangwp
 */
public class Application {
	public static void main(String[] args) throws Exception {
		int port = 10000;

		if (args.length > 1)
			port = NumberUtils.toInt(args[0], port);
		DoradoServerBuilder.forPort(port).build().start();
	}
}
