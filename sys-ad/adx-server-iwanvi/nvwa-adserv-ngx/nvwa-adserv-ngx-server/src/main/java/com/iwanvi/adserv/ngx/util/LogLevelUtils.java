/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.iwanvi.nvwa.proto.BuiltinServiceProto.GetLoggersRsp;

/**
 * @author wangwp
 *
 */
public final class LogLevelUtils {

	public static void setLogLevel(String name, Level level) {
		Logger logger = LogManager.getLogger(name);
		logger.setLevel(level);
	}

	public static void setRootLogLevel(Level level) {
		LogManager.getRootLogger().setLevel(level);
	}

	@SuppressWarnings("rawtypes")
	public static List<GetLoggersRsp.Logger> getLoggers() {
		List<GetLoggersRsp.Logger> loggers = new ArrayList<>();

		Enumeration enumeration = LogManager.getCurrentLoggers();
		if (enumeration == null)
			return loggers;

		while (enumeration.hasMoreElements()) {
			Logger logger = (Logger) enumeration.nextElement();
			Level logLevel = logger.getEffectiveLevel();
			GetLoggersRsp.Logger _logger = GetLoggersRsp.Logger.newBuilder().setName(logger.getName())
					.setLevel(logLevel.toString()).build();
			loggers.add(_logger);
		}

		return loggers;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(getLoggers());
	}
}
