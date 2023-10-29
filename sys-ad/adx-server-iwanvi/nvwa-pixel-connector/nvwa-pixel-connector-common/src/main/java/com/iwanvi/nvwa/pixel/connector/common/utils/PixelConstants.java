package com.iwanvi.nvwa.pixel.connector.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;

public class PixelConstants {
	
	public static final Map<String, String> ADX_MAP = new HashMap<String, String>(); // UUID:MARK
	public static final String STR_ADX = PropertyGetter.getString("adxs", StringUtils.EMPTY);
	
	public static final String VALUE_REDIS_OVER_BALANCE_AGENT = "agent_{0}_{1}_{2}";
	public static final String VALUE_REDIS_OVER_BALANCE_USER = "user_{0}_{1}_{2}";
	public static final String VALUE_REDIS_OVER_LIMIT_PLAN = "plan_{0}_{1}_{2}_{3}";
	public static final String VALUE_REDIS_OVER_LIMIT_UNIT = "unit_{0}_{1}_{2}_{3}";
	public static final String VALUE_REDIS_OVER_LIMIT_ORDER = "order_{0}_{1}_{2}_{3}";
	
	public static final String KEY_REDIS_OVER_NOTIFY_TEMP = "over:notify:temp";
	public static final String KEY_REDIS_OVER_NOTIFY = "over:notify";

	public static final String KEY_REDIS_MONEY_LIMIT_PLAN = "money:limit:plan:{0}:{1}";  // 限额   money:limit:plan:{userId}:{planId}
	public static final String KEY_REDIS_MONEY_LIMIT_UNIT = "money:limit:unit:{0}:{1}";  // 限额   money:limit:unit:{userId}:{unitId}
	public static final String KEY_REDIS_MONEY_TOTAL = "money:total:{0}";  // 余额  money:total:{userId}
	public static final String KEY_REDIS_MONEY_COST = "money:cost:{0}:{1}";  // 花费  money:cost:{userId}:{ymd}
	
	public static final String KEY_REDIS_MONEY_COST_ADX_HOUR = "money:cost:hour:{0}:{1}:{2}:{3}";  // 花费  money:cost:hour:{userId}:{adx}:{ymd}:{HH}
	public static final String KEY_REDIS_MONEY_COST_ADX_DAY = "money:cost:day:{0}:{1}:{2}";  // 花费 money:cost:day:{userId}:{adx}:{ymd}
	public static final String KEY_REDIS_MONEY_COST_ADX = "money:cost:total:{0}:{1}";  // 花费  money:cost:total:{userId}:{adx}
	
	public static final String KEY_REDIS_KPI_EXP = "kpi:limit:pv:orderPut:{0}";  // KPI  kpi:limit:pv:orderPut:{putId(unitId}}
	public static final String KEY_REDIS_KPI_CLK = "kpi:limit:clk:orderPut:{0}";  // KPI  kpi:limit:clk:orderPut:{putId(unitId}}

	public static final String KEY_REDIS_CLICK = "clk";
	public static final String KEY_REDIS_ACTIVE = "act";
	public static final String KEY_REDIS_DMP = "dmp:{0}"; // dmp:{quota}
	public static final String VALUE_REDIS_DMP = "{0}|{1}"; // {date}|{VALUE_MEMCACHED_REQ_VALIDATE}
	
	public static final String KEY_REDIS_AD_POSITION = "adposition:{0}";  // 广告位信息 adposition:{pid(uuid)}
	public static final String KEY_REDIS_REQ_HOUR = "req:hour:{0}:{1}";  // 请求量 req:hour:{ymd}:{HH}
	public static final String KEY_REDIS_REQ_DAY = "req:day:{0}";  // 请求量 req:day:{ymd}

	public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";  // creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_QUOTA_DAY = "creative:day:{0}:{1}:{2}";  // creative:day:{quota}:{cid}:{ymd}
	public static final String KEY_REDIS_CREATIVE_QUOTA = "creative:{0}:{1}";  // creative:{quota}:{cid}
	public static final String KEY_REDIS_UNIT_QUOTA_HOUR = "unit:hour:{0}:{1}:{2}:{3}";  // unit:hour:{quota}:{unitId}:{ymd}:{HH}
	public static final String KEY_REDIS_UNIT_QUOTA_DAY = "unit:day:{0}:{1}:{2}";  // unit:day:{quota}:{unitId}:{ymd}
	public static final String KEY_REDIS_PLAN_QUOTA_HOUR = "plan:hour:{0}:{1}:{2}:{3}";  // plan:hour:{quota}:{planId}:{ymd}:{HH}
	public static final String KEY_REDIS_PLAN_QUOTA_DAY = "plan:day:{0}:{1}:{2}";  // plan:day:{quota}:{planId}:{ymd}
	public static final String KEY_REDIS_PLAN_QUOTA = "plan:{0}:{1}";  // plan:day:{quota}:{planId}
	public static final String KEY_REDIS_ORDER_QUOTA_HOUR = "order:hour:{0}:{1}:{2}:{3}";  // order:hour:{quota}:{orderId}:{ymd}:{HH}
	public static final String KEY_REDIS_ORDER_QUOTA_DAY = "order:day:{0}:{1}:{2}";  // order:day:{quota}:{orderId}:{ymd}
	public static final String KEY_REDIS_CUST_QUOTA_HOUR = "cust:hour:{0}:{1}:{2}:{3}";  // cust:hour:{quota}:{uuid}:{ymd}:{HH}
	
	public static final String KEY_REDIS_DSP_QUOTA_HOUR = "dsp:hour:{0}:{1}:{2}:{3}";  // dsp:hour:{quota}:{dspId}:{ymd}:{HH}
	
	public static final String KEY_REDIS_DSP_CREATIVE_QUOTA_HOUR = "dsp:creative:hour:{0}:{1}:{2}:{3}";  // dsp:creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_ADERID_QUOTA_HOUR_DSP = "dsp:ader:hour:{0}:{1}:{2}:{3}";  // dsp:creative:hour:{quota}:{aderId}:{ymd}:{HH}
	public static final String KEY_REDIS_DSPID_QUOTA_HOUR_DSP = "dsp:ader:hour:{0}:{1}:{2}:{3}";  // dsp:creative:hour:{quota}:{dspId}:{ymd}:{HH}
	
	//广告主消费
	public static final String KEY_REDIS_ADER_QUOTA_HOUR = "ader:hour:{0}:{1}";  // ader:hour:{quota}:{appId}:{pid}:{ymd}:{HH}
	public static final String KEY_REDIS_ADER_QUOTA_DAY = "ader:day:{0}:";  // ader:day:{quota}:{appId}:{pid}:{ymd}

	// ApiLog|{dspId}|{cost}
	public static final String LOG_FORMAT_MONITOR = "{0}|{1}|{2}";
	
	public static long COST_PLATFORM = 0L;
	public static long COST_FLOW = 0L;
	
	public static final ConcurrentHashMap<String, AtomicLong> EXP_CATALOG_COUNT = new ConcurrentHashMap<>();
	public static final ConcurrentHashMap<String, AtomicLong> EXP_COUNT = new ConcurrentHashMap<>();
	public static final HashMap<String, Set<String>> EXP_DID_MAP = new HashMap<>();

	public static final ConcurrentHashMap<String, AtomicLong> CLK_CATALOG_COUNT = new ConcurrentHashMap<>();
	public static final ConcurrentHashMap<String, AtomicLong> CLK_COUNT = new ConcurrentHashMap<>();
	public static final HashMap<String, Set<String>> CLK_DID_MAP = new HashMap<>();
	
	public static final Map<Integer, String> OS_MAP = new HashMap<>();
	public static final String OS_IOS = "ios";
	public static final String OS_ANDROID = "android";
	public static final String BLANK = "";
	static {
		OS_MAP.put(0, BLANK);
		OS_MAP.put(1, OS_IOS);
		OS_MAP.put(2, OS_ANDROID);
	}
}
