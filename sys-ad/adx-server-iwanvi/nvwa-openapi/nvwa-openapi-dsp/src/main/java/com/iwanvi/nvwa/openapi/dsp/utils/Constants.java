package com.iwanvi.nvwa.openapi.dsp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.iwanvi.nvwa.openapi.dsp.model.SyncAdPosition;

public class Constants {

	public static final String BLANK = "";
	public static final String MONITOR_TYPE_CLICK = "click";
	public static final String MONITOR_TYPE_EXPOSURE = "exposure";
	
	public static final String KEY_REDIS_CREATIVE_AGENT_COST_HOUR = "creative:agentcost:hour:{0}:{1}:{2}:{3}";  // creative:agentcost:hour:{agent}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";  // creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_QUOTA_DAY = "creative:day:{0}:{1}:{2}";  // creative:day:{quota}:{cid}:{ymd}
	public static final String KEY_REDIS_CREATIVE_QUOTA = "creative:{0}:{1}";  // creative:{quota}:{cid}
	public static final String KEY_REDIS_UNIT_QUOTA_HOUR = "unit:hour:{0}:{1}:{2}:{3}";  // unit:hour:{quota}:{unitId}:{ymd}:{HH}
	public static final String KEY_REDIS_UNIT_QUOTA_DAY = "unit:day:{0}:{1}:{2}";  // unit:day:{quota}:{unitId}:{ymd}
	public static final String KEY_REDIS_PLAN_QUOTA_HOUR = "plan:hour:{0}:{1}:{2}:{3}";  // plan:hour:{quota}:{planId}:{ymd}:{HH}
	public static final String KEY_REDIS_PLAN_QUOTA_DAY = "plan:day:{0}:{1}:{2}";  // plan:day:{quota}:{planId}:{ymd}

	public static final String KEY_REDIS_FREQUENCY = "frequency:platform:{0}:{1}"; // sdk投放频次控制, platformId, did
	public static final String KEY_REDIS_AD_POSITION = "adposition:{0}";  // 广告位信息 adposition:{pid(uuid)}
	public static final Map<String, SyncAdPosition> AD_POSITION_MAP = new HashMap<>(); // 广告位信息
	public static final Set<String> AD_POSITION_UUID_SET = new HashSet<>(); // 广告位uuid
	
//	public static final ConcurrentHashMap<String, Platform> RESPONSE_PLATFORM = new ConcurrentHashMap<>(); // sdk流量分配，posId--->platformId
	public static final ConcurrentHashMap<String, Map<String, Object>> RESPONSE_SDK = new ConcurrentHashMap<>(); // sdk流量分配，posId--->platformId
	public static final ConcurrentHashMap<String, List<String>> SDK_ALLOTMENT = new ConcurrentHashMap<>(); // sdk流量分配，posId--->platformId
	public static final List<String> FORBIDDEN_DATE = new ArrayList<String>(); // 禁投日期
	public static final List<String> PLATFORM_LIMIT = new ArrayList<String>(); // SDK平台流量限制
	
	public static final String QUOTA_REQ = "req"; // 请求量
	public static final String QUOTA_BID = "bid"; // 竞价
	public static final String QUOTA_WIN = "win"; // 竞价成功
	public static final String QUOTA_DISPLAY = "display"; // 曝光
	public static final String QUOTA_CLICK = "click"; // 点击
	public static final String QUOTA_ACTIVE = "active"; // 激活
	public static final String QUOTA_CONSUME = "consume"; // 消费
	public static final String QUOTA_INCOME = "income"; // 收益
	public static final String QUOTA_BID_PRICE = "bidprice";
	
	public static final String QUOTA_BALANCE = "balance"; // 余额 
	
	public static final String OS_IOS = "ios";
	public static final String OS_ANDROID = "android";
	
	public static final ConcurrentHashMap<String, AtomicLong> REQ_CATALOG_COUNT = new ConcurrentHashMap<>();
	public static final ConcurrentHashMap<String, AtomicLong> REQ_COUNT = new ConcurrentHashMap<>();
	public static final ConcurrentHashMap<String, AtomicLong> REQ_WARNNING_COUNT = new ConcurrentHashMap<>(); //预警pv
	public static final ConcurrentHashMap<String, AtomicLong> BID_COUNT = new ConcurrentHashMap<>();

	public static final HashMap<String, Set<String>> REQ_DID_MAP = new HashMap<>();
	public static final HashMap<String, Set<String>> REQ_CATALOG_DID_MAP = new HashMap<>();
	
	public static final Map<Integer, String> OS_MAP = new HashMap<>();
	static {
		OS_MAP.put(0, BLANK);
		OS_MAP.put(1, OS_IOS);
		OS_MAP.put(2, OS_ANDROID);
	}
}
