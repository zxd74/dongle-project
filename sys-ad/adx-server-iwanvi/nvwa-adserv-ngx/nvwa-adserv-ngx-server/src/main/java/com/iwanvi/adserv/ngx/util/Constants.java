/*
 * Copyright 2014-2016 f2time.com All right reserved.
 */
package com.iwanvi.adserv.ngx.util;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.nvwa.proto.CommonProto.CostType;
import com.iwanvi.nvwa.proto.CommonProto.CreativeType;

/**
 * 系统常量类
 * 
 * @author wangwp
 */
public class Constants {

	public static final String DEFAULT_TARGET_STRING = "0";

	public static final long HASHING_SEED = 19860319;
	public static final String REVERSE_INDEX_MAP = "reverse_idx_map";

	public static final int DEFAULT_REDIS_POOL_SIZE = 5;

	public static final String DB_ADPLAN = "ad_plan.db";
	public static final String DB_ADUNIT = "ad_unit.db";
	public static final String DB_ADVERTISER = "advertiser.db";
	public static final String DB_CREATIVE = "creative.db";
	public static final String DB_AD_COMMON_CONFIG = "ad_common_cfg.db";
	public static final String DB_AD_POSITION = "ad_pos.db";
	public static final String DB_ADUNIT_INDEX = "ad_unit_index.db";
	public static final String DB_APP_DAY_DATA = "app_day_data.db";
	public static final String DB_DEVICE_EXPOSURE_DAY = "device_exposure_day.db";
	public static final String DB_DEVICE_EXPOSURE_WEEK = "device_exposure_week.db";
	public static final String DB_DEVICE_EXPOSURE_MONTH = "device_exposure_month.db";

	public static final String REDIS_KEY_PREFIX_AD_PLAN = "c:";
	public static final String REDIS_KEY_PREFIX_AD_UNIT = "g:";
	public static final String REDIS_KEY_PREFIX_ADVERTISER = "a:";
	public static final String REDIS_KEY_PREFIX_CREATIVE = "cr:";
	public static final String REDIS_KEY_PREFIX_COMMON = "commonConfig";
	public static final String REDIS_KEY_PREFIX_AD_TYPE_CONFIG = "p:";
	public static final String REDIS_KEY_PREFIX_SSP_APP = "sspapp:";
	public static final String REDIS_KEY_PREFIX_RATE_LIMITER = "minerva:ratelimiter:";

	public static final byte[] MINERVA_CHANNEL = "channel:minerva".getBytes();
	public static final String DISPLAY_CHANNEL = "channel:display";

	public static final int ID_AD_COMMON_CONFIG = MurmurHash.hash32("adCommonConfig", (int) HASHING_SEED);
	public static final int RANK_PRICE_DIFF_UNIT = 1;

	public static final int BIDDING_MAX_AD_NUM = 10;

	public static final String DB_REVERSE_INDEX = "reverse_index.db";
	public static final String REVERSE_KEY_JOIN_SIGN = "^";

	public static final String CACHE_HASH_MAP = "hashMap";
	public static final String CACHE_TREE_MAP = "treeMap";

	public static final int DEFAULT_FETCH_AD_NUM = 1;

	public static final String REDIS_KEY_PREFIX_AGENT = "agent:";
	public static final String REDIS_KEY_PREFIX_AGENT_FLOOR_PRICE_CONFIG = "agentfpc:";

	public static final String DB_AGENT = "agent.db";
	public static final String DB_AGENT_FPC = "agent_floor_price_config.db";

	public static final String SIGN_UNDERLINE = "_";

	public static final String SIGN_COMMA = ",";

	public static final String SIGN_HYPHEN = "-";

	public static final String EMPTY = "";

	public static final String PATH_SEPARATE = "/";

	public static final String PATH_SERVER_DIRECTORY = "/data/pub";

	public static final float COLDSTART_IMP_RATE = 0.9F;

	public static final String DB_SBDSTAT = "sbd_stat.db";

	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd:HH";
	public static final String DATE_FORMAT_DAY = "yyyyMMdd";

	public static final String REDIS_KEY_PREFIX_AD_IMP = "creative:hour:display:%d:%s";
	public static final String REDIS_KEY_PREFIX_AD_CLK = "creative:hour:clk:%d:%s";

	public static final String REDIS_KEY_PREFIX_AD_BID = "creative:hour:bid:%d:%s";

	public static final String REDIS_KEY_PREFIX_AD_CONSUME = "unit:day:consume:%d:%s";

	public static final int MINUTES_HALF_AN_HOURS = 30;

	public static final int SECONDS_ONE_HOUR = 3600;

	public static final int SECONDS_ONE_MINUTE = 60;

	public static final int INT_ZERO = 0;

	public static final double DOUBLE_ZERO = 0d;

	public static final int DEFAULT_RATELIMIT_TIME_INTERVAL = 1;

	public static final int BID_RATE_MIN = 1;

	public static final String APP_CONSUME = "app:consume:%d:%s"; // 日花费

	public static final String APP_ACTIVE = "app:active:%d:%s"; // 日激活数

	public static final String APP_IDS = "app:id:%d";

	public static final String ENTIRE_FILE_PREFIX = "ent";

	public static final String EXT_APP_ID = "dx_app";

	public static final int TIME_MILLIS_30 = 30;

	public static final int TIME_MILLIS_25 = 25;

	public static final int TIME_MILLIS_20 = 20;

	public static final int TIME_MILLIS_15 = 15;

	public static final int TIME_MILLIS_10 = 10;

	public static final int TIME_MILLIS_5 = 5;

	public static final String REDIS_KEY_PREFIX_SSP_AD_POSITION = "sspadpos:";
	public static final String REDIS_KEY_PREFIX_DSP = "dsp:";
	public static final String REDIS_KEY_PREFIX_DSP_CREATIVE = "dspc:";
	public static final String REDIS_KEY_ADNGX_DATA_URL = "adngx:data:url";

	public static List<CostType> COST_TYPES_CPT = Arrays.asList(CostType.kCpt, CostType.kCpfc, CostType.kCpfm);

	public static List<CostType> COST_TYPES_RTB = Arrays.asList(CostType.kCpm, CostType.kCpc);

	public static List<CostType> COST_TYPES_ALL = Arrays.asList(CostType.kCpt, CostType.kCpfc, CostType.kCpfm,
			CostType.kCpm, CostType.kCpc);

	public static List<CreativeType> CREATIVE_TYPES_IMAGE = Arrays.asList(CreativeType.kPic, CreativeType.kFirstScreen,
			CreativeType.kDynamic);

	public static final String PUT_TYPE_EXT_KEY = "putType";

	public static final String XCAR_DMP_TAG_NAME_BRAND = "品牌";

	public static final String XCAR_DMP_TAG_NAME_PRICE_TAG = "价格标签";

	public static final String XCAR_DMP_TAG_NAME_LEVEL = "车型";

	public static final String XCAR_DMP_TAG_NAME_SERIES = "车系";

	public static String XCAR_DMP_SERVICE_URL = MinervaCfg.get().getConfigProperty("iwanvi.dmp.service.url");

	public static boolean XCAR_DMP_ENABLED = BooleanUtils
			.toBoolean(MinervaCfg.get().getConfigProperty("iwanvi.dmp.enabled"));

	public static final String DSP_BID_REQ_KEY = "dsp:hour:req:%s:%s:%s"; // dsp:hour:req:{dspid}:{ymd}:{HH}
	public static final String DSP_BID_BID_KEY = "dsp:hour:bid:%s:%s:%s";
	public static final String DSP_BID_WIN_KEY = "dsp:hour:win:%s:%s:%s";
	public static final String DSP_TIMEOUT_KEY = "dsp:hour:timeout:%s:%s:%s";
	public static final String DSP_ERROR_KEY = "dsp:hour:error:%s:%s:%s";

	public static final String REDIS_KEY_PREFIX_AREALEVEL = "arealevel:";
	public static final String CONFIG_KEY_INTERNAL_MEDIA_UUID = "media.internal.uuid";

	public static final String REDIS_KEY_PREFIX_ADPF = "adpf:";

	public static final String DEFAULT_UA = "Mozilla/5.0 (Linux; Android 6.0.1; Hisense M30T Build/MMB29M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36";
}
