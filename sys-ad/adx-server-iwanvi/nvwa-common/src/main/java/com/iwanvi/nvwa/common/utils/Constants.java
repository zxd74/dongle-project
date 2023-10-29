package com.iwanvi.nvwa.common.utils;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final String STRING_HTTP = "http://";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_GET = "GET";
    
    public static final String CONTENT_TYPE_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

	public static final String RESPONSE_SUCCESS = "A000000"; // 成功
	public static final String RESPONSE_PARAM_INVALID = "A000001"; // 参数无效
	public static final String RESPONSE_INVALID = "A000002"; // 请求无效
	public static final String RESPONSE_NOT_FOND = "A000004"; // 没有数据
	public static final String PARAM_ERROR = "A000005"; // 参数错误
	public static final String ADVERTISER_REJECT = "A000006"; // 广告主未审核通过
	public static final String NO_ADVERTISER = "A000007"; // 无对应广告主
	public static final String NO_ENTITY = "A000008"; // 无对应创意
	public static final String RESPONSE_FAILED = "A000014"; // 失败
    public static final String RESPONSE_FAILED_DUPNAME = "A000015"; // 名称已存在
    public static final String RESPONSE_FAILED_NOT_ACTIVE = "A000016"; // 用户未激活
    public static final String RESPONSE_FALIED_SIGN_INVALID = "A000020";//签名无效

	public static final String RESPONSE_EXCEPTION = "E000000"; // 程序异常
    public static final String RESPONSE_EXCEPTION_NOT_LOGIN = "E000001"; // 用户未登录
    public static final String RESPONSE_EXCEPTION_NO_PERSSION = "E000002"; // 用户无权限

	public static final Integer INTEGER_0=0;
	public static final Integer INTEGER_1=1;
	public static final Integer INTEGER_2=2;
	public static final Integer INTEGER_3=3;
	public static final Integer INTEGER_4=4;
	public static final Integer INTEGER_5=5;
	public static final Integer INTEGER_6=6;
	public static final Integer INTEGER_7=7;
	public static final Integer INTEGER_8=8;
	public static final Integer INTEGER_9=9;
	public static final Double PRICE_DEFAULT_10=10d;
	
	public static final String FILE_SUFFIX_TXT = ".txt";
	public static final String FILE_SUFFIX_XLS = ".xls";
	
	public static final String SIGN_COMMA = ",";
	public static final String SIGN_BLANK = " ";
	public static final String SIGN_SEMICOLON = ";";
	public static final String SIGN_UNDERLINE = "_";
	public static final String SIGN_MINUS = "-";
	public static final String SIGN_PERCENT = "%";
	public static final String SIGN_OBLIQUELINE = "/";
	public static final String SIGN_BACKSLASH = "\\";
	public static final String SIGN_COLON = ":";
	public static final String SIGN_POINT = ".";
	public static final String SIGN_QUERY = "?";
	public static final String SIGN_AT = "@";
	public static final String SIGN_EQUALS = "=";
	public static final String SIGN_LT = "<";
	public static final String SIGN_GT = ">";
	public static final String SIGN_OR = "|";
	public static final String SIGN_BRACKET_L = "[";
	public static final String SIGN_BRACKET_R = "]";
	public static final String SIGN_SPLIT = "___";
	public static final String SIGN_ASTERISK = "*";
	public static final String SIGN_COLON_DOUBLE = "::";

	public static final String TEMP_SUFFIX = "_tmp";
	public static final String IWANVI_UUID = "M7nuyi"; // 唯一流量源UUID

    /**
     * oss服务
     */
    public static final  String OSS_ACCESS_ID =  PropertyGetter.getString("oss.access.id");    //oss验证id
    public static final  String OSS_ACCESS_KEY =  PropertyGetter.getString("oss.access.key");  //oss验证key
    public static final  String OSS_ENDPOINT =  PropertyGetter.getString("oss.endpoint");       //oss服务域
    public static final  String OSS_BUCKETNAME =  PropertyGetter.getString("oss.bucketname");     //oss命名空间

    public static final Integer STATE_ALL = 99;  //全部
    public static final Integer STATE_DEFAULT = -1;  //默认值
    public static final Integer STATE_INVALID = 0;  //状态无效、已删除
    public static final Integer STATE_VALID = 1;  //状态有效、审核通过
    public static final Integer STATE_FAILURE_AUDIT = 2;  //审核未通过(或人工审核未通过)
    public static final Integer STATE_WAIT_AUDIT = 3;  //待审核(人工待审核)
    public static final Integer STATE_NOT_ACTIVE = 4;  //未激活
    public static final Integer STATE_BALANCE_INVALID = 5;  //账户余额不足，用于广告计划
    public static final Integer STATE_EXPIRED = 6;  //已过期或未开始，用于广告单元
    public static final Integer STATE_REJECT_AUDIT = 7;  //审核拒绝,用于ADX
    public static final Integer STATE_UNSTART = 8; // 未启动，用于广告单元
    public static final Integer STATE_UNIT_LIMIT = 9; // 单元超限额
    public static final Integer STATE_WAIT_AUDIT_ADX = 10;  //媒体待审核
    public static final Integer STATE_DISABLE = 12;  //媒体待审核
	public static final Integer STATE_WAIT_AUDIT_MACHINE = 13;  //机审待审核
    public static final Integer STATE_FAILURE_AUDIT_MACHINE = 14;  //机审未通过
	public static final Integer STATE_FAILURE_AUDIT_BLACKLIST = 15;  //黑名单
	public static final Integer STATE_WAIT_UPDATE = 16;  //创意待修改(投放的落地页、媒体、广告位等改变后)

	/**
    *   sys_crontab constants
    */
    public static final Integer OBJ_ADVERTISER = 1 ; //广告主
    public static final Integer OBJ_PLAN = 2 ; //推广计划
    public static final Integer OBJ_PUT = 3 ; //推广单元
    public static final Integer OBJ_AD_TYPE_CONFIG = 4 ; //广告类型配置
    public static final Integer OBJ_COMMON_CONFIG = 5 ; //通用配置
    public static final Integer OBJ_ENTITY = 6 ; //推广创意
    public static final Integer OBJ_PRICE = 7 ; //代理商底价
    public static final Integer OBJ_AGENT = 8;//代理商
    public static final Integer OBJ_APP = 9;//开发者媒体
    public static final Integer OBJ_APP_POSITION = 10;//开发者媒体广告位
    public static final Integer OBJ_ORDER = 11 ; //订单
    public static final Integer OBJ_ORDER_PUT = 12 ; //订单投放
	public static final Integer OBJ_FLOW_CONSUMER = 13; // 广告平台
	public static final Integer OBJ_DSP_ENTITY  = 14; // 第三方创意
	public static final Integer OBJ_AK_ADP  = 15; //爱卡广告位
	public static final Integer OBJ_AK_MEDIA  = 16; // 爱卡id
	public static final Integer OBJ_AREA_GROUP = 17; //地域等级
	public static final Integer OBJ_AD_POSITION_PRICE = 18; //广告位底价
	public static final Integer OBJ_WARN_SETTING = 19; //广告位底价
	public static final Integer OBJ_DSP_ADVERTISER = 20; //第三方广告主

    /**
     * user type
     */
    public static final Integer USER_TYPE = 8;
    public static final Integer USER_TYPE_ADMIN = 9;
    public static final Integer USER_TYPE_ADMIN_FLOW = 10;//流量管理员
	public static final Integer USER_TYPE_ADMIN_DMP = 11;//DMP管理员
	public static final Integer USER_TYPE_AGENT = 12;
	public static final Integer USER_TYPE_DIRECT_OPERATE = 13;//直客运营
	public static final Integer USER_TYPE_AGENT_OPERATE = 14;//代理商运营
	public static final Integer USER_TYPE_AUDIT = 15;//审核员
	public static final Integer USER_TYPE_CUST = 16;
	
    /**
     * put type
     */
    public static final Integer PUT_TYPE_ORDER = 17;//投放类型 订单投放
	public static final Integer PUT_TYPE_BOTTOM = 18;//投放类型 抄底投放
	public static final Integer PUT_TYPE_ACCURATE = 19;//投放类型 精确投放

	public static final String LOGIN_VIRTUAL_AGENT = "anget";
	public static final String LOGIN_VIRTUAL_ADVER = "adver";
	public static final String LOGIN_USER = "u";
	public static final String LOGIN_PERMISSION = "a";
	public static final String LOGIN_TOKEN = "t";

    public static final Integer STATUS_UNHANDLE = 0; //待处理
    public static final Integer STATUS_HANDLED = 1; //已处理
    public static final Integer STATUS_HANDLED_FAILED = 2; //已处理 失败
    public static final Integer STATUS_HANDLING = 3; //处理中
    
    public static final Integer OP_ADD = 1 ; //新增数据
    public static final Integer OP_REMOVE = 2 ; //删除数据
    public static final Integer OP_UPDATE = 3 ; //更新数据
    public static final Integer OP_OFFLINE = 4 ; //下线
    public static final Integer OP_ONLINE = 5 ; //上线
    
    /**
     *   pub constants
     */
    public static final String PUB_DIR = "/data/pub/";
    public static final String PUB_SEQ_FILE = "pub.seq";
    public static final String ENTIRE_FILE_PREFIX = "ent";
    public static final String INCEMENTAL_FILE_PREFIX = "inc";
    public static final String PUB_DATA_FILE_SUFFIX = ".dat";
    public static final String PUB_FILE_TMP_SUFFIX = ".tmp";
    public static final String PRICE_FILE_PREFIX = "afp";
    
    
    /**
     * area type
     */
	
    public static final Integer AREA_TYPE_PROVINCE = 1; //省
    public static final Integer AREA_TYPE_CITY = 2; //市
    public static final Integer AREA_TYPE_COUNTY = 3; //区
    public static final Integer AREA_RULE_INCLUDE = 1; //地区规则包含
    public static final Integer AREA_RULE_EXCLUDE = 2; //地区规则排除
    
	/**
	 * dictionary type
	 */
	public static final Integer ADVERTIDER_INDUSTRY = 26; // 广告主行业
	public static final Integer TERMINAL_TYPE = 21; //终端类型
    public static final Integer TERMINAL_TYPE_APP = 158; //终端类型-app
	public static final Integer COST_TYPE = 110; // 计费类型
	public static final Integer COST_TYPE_CPM = 111;
	public static final Integer COST_TYPE_CPC = 112;
	public static final Integer COST_TYPE_CPT = 113;
	public static final Integer COST_TYPE_ORDER_CPM = 140;
	public static final Integer COST_TYPE_ORDER_CPC = 141;
	public static final Integer PUT_DELIVERY_MODE_STANDARD = 104;//标准投放
	public static final Integer PUT_DELIVERY_MODE_UNIFORM = 105;//匀速投放
	public static final Integer AD_STRATEGY_PAGE = 211;//翻页
	public static final Integer AD_STRATEGY_DURATION = 212;//时长
	
	public static final Integer AD_TYPE_VIDEO = 4;//全屏视频
	public static final Integer AD_TYPE_PIC = 5;//横幅
	public static final Integer AD_TYPE_SCREEN_FIRST = 6;//开屏
	public static final Integer AD_TYPE_NATIVE = 7;//信息流
	public static final Integer AD_TYPE_TEXT = 216;//文字链
	public static final Integer AD_TYPE_INCENTIVE = 217;//激励视频
	public static final Integer AD_TYPE_SCREEN = 218;//插屏
	public static final Integer FC_TYPE_SDK = 97;
	public static final Integer FC_TYPE_DSP = 98;
	
	public static final Integer MODULE_TYPE_CODE = 14;//动态代码组件
	/**
	 * session
	 */
    public static final String USER_SESSION_ID = "id";

    public static final String TOKEN_KEY = "token:{0}";
    
    /**
     * media
     */
    public static final Integer FLOW_MEDIA_AIKA = 1;
    
    public static final Integer SSP_JOINTYPE = 71;

	public static final String KEY = "key";
	
    public static final Long ALL_TIME = Long.parseLong("FFFFFFFFFFFF", 16);

    public static final String REDIS_KEY_PREFIX_ADVERTISER = "a:";
    public static final String REDIS_KEY_PREFIX_PLAN = "c:";
	public static final String REDIS_KEY_PREFIX_PUT = "g:";
	public static final String REDIS_KEY_PREFIX_AGENT = "agent:";
	public static final String REDIS_KEY_PREFIX_AGENT_FLOOR_PRICE_CONFIG = "agentfpc:";
	public static final String REDIS_KEY_PREFIX_SSP_APP = "sspapp:";
	public static final String REDIS_KEY_PREFIX_SSP_POS = "sspadpos:";
	public static final String REDIS_KEY_PREFIX_COMMON_CONFIG = "commonConfig";
	public static final String REDIS_KEY_PREFIX_AD_TYPE_CONFIG = "p:";
	public static final String REDIS_KEY_PREFIX_FLOW_CONSIMER = "dsp:";
	public static final String REDIS_KEY_PREFIX_CONSIMER_ENTITY = "dspc:";
	public static final String REDIS_KEY_PREFIX_ADPFP = "adpfp:";
	public static final String REDIS_KEY_PREFIX_AREA_LEVEL = "arealevel:";

	public static final String QUOTA_REQUV = "requv"; // 请求量UV
	public static final String QUOTA_REQ = "req"; // 请求量
	public static final String QUOTA_BID = "bid"; // 竞价
	public static final String QUOTA_WIN = "win"; // 竞价成功
	public static final String QUOTA_DISPLAY = "display"; // 曝光
	public static final String QUOTA_DISPLAYUV = "expuv"; // 曝光UV
	public static final String QUOTA_CLICK = "click"; // 点击
	public static final String QUOTA_CLICKUV = "clkuv"; // 点击UV
	public static final String QUOTA_ACTIVE = "active"; // 激活
	public static final String QUOTA_COST = "cost"; // 花费
	public static final String QUOTA_BALANCE = "balance"; // 余额

    /**
     * aliyun log conf
     */
    public static final String ALIYUN_LOG_ENDPOINT = PropertyGetter.getString("aliyun.log.endpoint");
    public static final String ALIYUN_LOG_ACCESSKEYID = PropertyGetter.getString("oss.access.id");
    public static final String ALIYUN_LOG_ACCESSKEYSECRET = PropertyGetter.getString("oss.access.key");
    public static final String ALIYUN_LOG_PROJECT = PropertyGetter.getString("aliyun.log.project");
    public static final String ALIYUN_LOG_STORE_REQ = PropertyGetter.getString("aliyun.log.store.req");
    public static final String ALIYUN_LOG_STORE_EXP = PropertyGetter.getString("aliyun.log.store.exp");
    public static final String ALIYUN_LOG_STORE_CLK = PropertyGetter.getString("aliyun.log.store.clk");
    public static final String ALIYUN_LOG_STORE_DSP = PropertyGetter.getString("aliyun.log.store.dsp");

	public static final Integer IS_DEAL = 235;
	public static final Integer WARN_SETTING_ONE = 232;

	//媒体请求
	public static final String FORMMAT_COUNT_KEY = "{0}:{1}:{2}:{3}"; // appId:posId:channel:version
	public static final String FORMMAT_QUERY_FLOW = 
			" appId:{0} and posId:{1} and version:{2} and channel1:{3} and channel2:{4} and time in [{5} {6}) ";
	public static final String FORMMAT_QUERY_PLATFORM = " appId:{0} and posId:{1} and dspId:{2} and time in [{3} {4}) ";
	public static final String FORMMAT_DISTINCT = " | SELECT DISTINCT({0})";
	public static final String FORMMAT_QUERYUV = " SELECT COUNT(DISTINCT(did))";
	public static final String FORMMAT_QUERYCOST = " SELECT SUM(cost)";
	public static final String CHANNEL= "000000"; // 20190505 固定统一渠道号
	
	public static final String FORMMAT_CATALOG_COUNT_KEY = "{0}:{1}:{2}"; // level1:level2:level3
	public static final String KEY_REDIS_MEDIA_QUOTA = "media:day:{0}:{1}:{2}";  // media:day:{quota}:{FORMMAT_COUNT_KEY}:{ymd}
	// media:day:{quota}:{iwanvi_uuid}:{ymd}:{HH}，用于实时监控
	public static final String KEY_REDIS_MEDIA_QUOTA_HOUR = "media:day:{0}:{1}:{2}:{3}";
	public static final String KEY_REDIS_ADPOSITION_QUOTA_HOUR = "pos:hour:{0}:{1}:{2}:{3}";
	public static final String KEY_REDIS_CATALOG_QUOTA = "catalog:day:{0}:{1}:{2}";  //catalog:day:{quota}:{FORMMAT_CATALOG_COUNT_KEY}:{ymd}
	public static final String KEY_REDIS_UV = "uv:hour:{0}:{1}:{2}";  // uv:hour:{quota}:{FORMMAT_COUNT_KEY}:{ymdh}

    public static final String KEY_FLOW_CONTROL = "flow:control";
    public static final String KEY_PROHIBITED_DATE = "prohibite:date";
    public static final String KEY_CAROUSEL = "adcarousel:{}";// 轮播策略 appId
    
	public static final String KEY_REDIS_CATALOG_KEYSET = "catalog:keys:{0}";  // catalog:keys:{ymd}
	
	public static final String KEY_REDIS_HASH_SDKALLOTMENT = "sdk:allotment:{0}";  // sdk:allotment:{posId}, sdkAllotScheduleId, json

	public static final String KEY_ENCODE = "38c257d4734ce59855bc05454674cd16";
	
	//网易盾账户信息
	/** 产品密钥ID，产品标识 */
	public final static String SECRETID = "e2351501c8a1c6da52a9ff8b1abf9c17";
	/** 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露 */
	public final static String SECRETKEY = "0f0e9243f326cd70bbc3b100eafa6a23";
	/** 业务ID，普通图片 */
	public final static String BUSINESSID_IMAGE = "c081bc0bfea72a5e0511f942b2e7bbc0";
	/** 业务ID，视频 */
	public final static String BUSINESSID_VIDEO = "***************";
	/** 业务ID，文本 */
	public final static String BUSINESSID_TEXT = "***************";
	/** 易盾内容安全服务视频点播信息提交接口地址 */
	public final static String VIDEO_UPLOAD_API_URL = "https://as.dun.163yun.com/v3/video/submit";
	/** 易盾内容安全服务视频点播信息检查接口地址 */
	public final static String VIDEO_CHECK_API_URL = "https://as.dun.e163yun.com/v3/video/check";
	/** 易盾内容安全服务图片信息提交接口地址 */
	public final static String IMAGE_CHECK_API_URL = "https://as.dun.163yun.com/v3/image/check";
	/** 易盾内容安全服务文本信息提交接口地址 */
	public final static String TEXT_CHECK_API_URL = "https://as.dun.163yun.com/v3/text/check";

	
	public static final Map<String, Integer> AGENT_PRICE_OS_MAP = new HashMap<>();
	static {
		AGENT_PRICE_OS_MAP.put("74", 174);
		AGENT_PRICE_OS_MAP.put("75", 175);
	}
	
	public static final Map<Integer, Integer> AIKA_PUT_STATUS_MAP = Maps.newHashMap();
	static {
//		AIKA_PUT_STATUS_MAP.put(0, 3);
		AIKA_PUT_STATUS_MAP.put(1, 8);
		AIKA_PUT_STATUS_MAP.put(2, 1);
		AIKA_PUT_STATUS_MAP.put(4, 6);
	}
	public static Integer getAiKaPutStatus(Integer status){
		return AIKA_PUT_STATUS_MAP.get(status);
	};
	
	public static final Map<Integer, Integer> AIKA_DX_ZDLX_MAP = Maps.newHashMap();
	static {
		AIKA_DX_ZDLX_MAP.put(1, 22);
		AIKA_DX_ZDLX_MAP.put(2, 23);
		AIKA_DX_ZDLX_MAP.put(3, 158);
	}
	public static Integer getAiKaPutZdlx(Integer zdlx){
		return AIKA_DX_ZDLX_MAP.get(zdlx);
	};
	
	public static final Map<Integer, Integer> AIKA_PUT_COST_TYPE_MAP = Maps.newHashMap();
	static {
		AIKA_PUT_COST_TYPE_MAP.put(1, 113);
		AIKA_PUT_COST_TYPE_MAP.put(2, 140);
		AIKA_PUT_COST_TYPE_MAP.put(3, 141);
	}
	public static Integer getAiKaPutCostType(Integer status){
		return AIKA_PUT_COST_TYPE_MAP.get(status);
	};
	
	public static final Map<Integer, Integer> AIKA_PUT_DELIVERY_MODE_MAP = Maps.newHashMap();
	static {
		AIKA_PUT_DELIVERY_MODE_MAP.put(0, 104);
		AIKA_PUT_DELIVERY_MODE_MAP.put(1, 105);
	}
	public static Integer getAiKaPutDeliveryMode(Integer status){
		return AIKA_PUT_DELIVERY_MODE_MAP.get(status);
	};
	
	public static final Map<Integer, Integer> AIKA_DX_FRE_PERIOD_MAP = Maps.newHashMap();
	
	static {
		AIKA_DX_FRE_PERIOD_MAP.put(1, 115);
	}
	public static Integer getAiKaPutFrePeriod(Integer period){
		return AIKA_DX_FRE_PERIOD_MAP.get(period);
	}
	
	public static final Map<Integer, Integer> AIKA_ORDER_RUNSTATE_MAP = Maps.newHashMap();
	
	static {
		AIKA_ORDER_RUNSTATE_MAP.put(0, 1);
		AIKA_ORDER_RUNSTATE_MAP.put(1, 0);
	}
	public static Integer getAiKaOrderRunState(Integer runState){
		return AIKA_ORDER_RUNSTATE_MAP.get(runState);
	}


	/**
	 * sdk轮播初始ID
	 */
	public static final List<Integer> DEFULT_IDS = new ArrayList<Integer>();
	public static final int EXPIRED_DAYS_DSP_ENTITY = 180;
	
	static {
		DEFULT_IDS.add(Constants.INTEGER_1);
		DEFULT_IDS.add(Constants.INTEGER_2);
		DEFULT_IDS.add(Constants.INTEGER_3);
	}

}
