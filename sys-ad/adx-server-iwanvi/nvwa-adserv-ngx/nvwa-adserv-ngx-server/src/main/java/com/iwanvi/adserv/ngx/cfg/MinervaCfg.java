/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.cfg;

import java.io.File;
import java.util.Properties;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iwanvi.adserv.ngx.util.Constants;

/**
 * 
 * @author wangwp
 */
public class MinervaCfg {
	private static final Logger LOGGER = LoggerFactory.getLogger(MinervaCfg.class);

	private static final String MINERVA_CFG_FILE = "minerva.properties";

	private static final MinervaCfg INSTANCE = new MinervaCfg();

	private String indexDbPath;
	private String redisServer;
	private int redisPoolSize;
	private String msgTopic;
	private String indexDbFile;
	private String redisAuthPwd;
	private double cpc2cpmCtr;
	private double adxBidWeight;
	private double adxPayDiscount;
	private boolean isDev;
	private int redisDbIndex;
	private String jschHostIp;
	private int jschHostPort;
	private String jschHostPwd;
	private String jschHostUserName;
	private String localFilePath;
	// 广告数据存储文件
	private String dbPath;
	private String impressionDBPath;
	private String ratelimitRedisAuth;
	private String internalMediaUuid;
	private int dspRouterThreadPoolMaxSize;
	private int dspRouterThreadPoolMinSize;
	private int dspTimeout;
	private String dspId;

	public String getDspId() {
		return dspId;
	}

	public int getDspRouterThreadPoolMaxSize() {
		return dspRouterThreadPoolMaxSize;
	}

	public int getDspRouterThreadPoolMinSize() {
		return dspRouterThreadPoolMinSize;
	}

	private final Properties configProperties;

	private MinervaCfg() {
		this.configProperties = new Properties();
		ClassLoader loader = MinervaCfg.class.getClassLoader();
		try {
			configProperties.load(loader.getResourceAsStream(MINERVA_CFG_FILE));
		} catch (Exception ex) {
			LOGGER.error("load minerva.properties error", ex);
		}

		indexDbPath = configProperties.getProperty("index.db.path");
		indexDbFile = configProperties.getProperty("index.db.file");
		redisServer = configProperties.getProperty("redis.server");
		redisPoolSize = NumberUtils.toInt(configProperties.getProperty("redis.pool.size"),
				Constants.DEFAULT_REDIS_POOL_SIZE);
		msgTopic = configProperties.getProperty("notify.msg.topic");
		redisAuthPwd = configProperties.getProperty("redis.auth.pwd");
		ratelimitRedisAuth = configProperties.getProperty("ratelimiter.redis.auth.pwd");
		internalMediaUuid = configProperties.getProperty(Constants.CONFIG_KEY_INTERNAL_MEDIA_UUID);
		if (StringUtils.isBlank(ratelimitRedisAuth)) {
			ratelimitRedisAuth = redisAuthPwd;
		}

		cpc2cpmCtr = NumberUtils.toDouble(configProperties.getProperty("cpc2cpm.ctr"), 0.0001);
		adxBidWeight = NumberUtils.toDouble(configProperties.getProperty("adx.bid.weight"), 1.0);
		adxPayDiscount = NumberUtils.toDouble(configProperties.getProperty("adx.pay.discount"), 1.0);

		isDev = BooleanUtils.toBoolean(NumberUtils.toInt(configProperties.getProperty("env.dev", "0")));
		redisDbIndex = NumberUtils.toInt(configProperties.getProperty("redis.db"), 0);

		jschHostIp = configProperties.getProperty("jsch.host.ip");
		jschHostPort = NumberUtils.toInt(configProperties.getProperty("jsch.host.port"));
		jschHostPwd = configProperties.getProperty("jsch.host.pwd");
		jschHostUserName = configProperties.getProperty("jsch.host.username");

		String module = System.getProperty("module");

		localFilePath = configProperties.getProperty("local.file.path") + File.separator + module;
		dbPath = configProperties.getProperty("db.path") + File.separator + module;
		impressionDBPath = configProperties.getProperty("impression.db.path") + File.separator + module;
		dspRouterThreadPoolMaxSize = NumberUtils.toInt(getConfigProperty("dsp.router.threadpool.max-size"), 10);
		dspRouterThreadPoolMinSize = NumberUtils.toInt(getConfigProperty("dsp.router.threadpool.min-size"), 5);
		dspTimeout = NumberUtils.toInt(getConfigProperty("dsp.timeout"), 200);
		dspId = getConfigProperty("media.dspid");
	}

	public String getConfigProperty(String key) {
		return this.configProperties.getProperty(key);
	}

	public static MinervaCfg get() {
		return INSTANCE;
	}

	public Properties getConfigProperties() {
		return this.configProperties;
	}

	public int getDspTimeout() {
		return dspTimeout;
	}

	public String getIndexDbPath() {
		return indexDbPath;
	}

	/**
	 * @return the redisServer
	 */
	public String getRedisServer() {
		return redisServer;
	}

	/**
	 * @return the redisPoolSize
	 */
	public int getRedisPoolSize() {
		return redisPoolSize;
	}

	/**
	 * @return the msgTopic
	 */
	public String getMsgTopic() {
		return msgTopic;
	}

	/**
	 * @return the indexDbFile
	 */
	public String getIndexDbFile() {
		return indexDbFile;
	}

	public String getRedisAuthPwd() {
		return redisAuthPwd;
	}

	public String getInternalMediaUuid() {
		return internalMediaUuid;
	}

	/**
	 * @return the cpc2cpmCtr
	 */
	public double getCpc2cpmCtr() {
		return cpc2cpmCtr;
	}

	/**
	 * @return the adxBidWeight
	 */
	public double getAdxBidWeight() {
		return adxBidWeight;
	}

	public double getAdxPayDiscount() {
		return this.adxPayDiscount;
	}

	/**
	 * @return the isDev
	 */
	public boolean isDev() {
		return isDev;
	}

	public int getRedisDbIndex() {
		return redisDbIndex;
	}

	public String getJschHostIp() {
		return jschHostIp;
	}

	public int getJschHostPort() {
		return jschHostPort;
	}

	public String getJschHostPwd() {
		return jschHostPwd;
	}

	public String getJschHostUserName() {
		return jschHostUserName;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public String getDBPath() {
		return this.dbPath;
	}

	public String impressionDBPath() {
		return this.impressionDBPath;
	}

	/**
	 * @return the ratelimitRedisAuth
	 */
	public String getRatelimitRedisAuth() {
		return ratelimitRedisAuth;
	}
}
