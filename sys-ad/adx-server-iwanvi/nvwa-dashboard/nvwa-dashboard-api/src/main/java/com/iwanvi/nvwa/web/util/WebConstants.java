package com.iwanvi.nvwa.web.util;

import com.iwanvi.nvwa.common.utils.PropertyGetter;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.google.common.collect.Maps;

import java.util.Map;

public class WebConstants {

    //redis key prefix
    public static final String REDIS_COMPANY_KEY_PREFIX = "company_";
    public static final String REDIS_FLOWCONSUMER_KEY_PREFIX = "flowconsumer_";
    public static final String AIKA_UUID = "M7nuyi";

    public static final int COMPANY_TYPE_AGENT = 1;
    public static final int COMPANY_TYPE_ADVER = 2;

    public static final int AGENT_OBJECT_TYPE_ADVER = 1;
    public static final int AGENT_OBJECT_TYPE_FLOW = 2;

    public static final int FLOWSOURCE_ADX = 0;
    public static final int FLOWSOURCE_ALLIANCE = 1;

    //抄底初始会计划方式
    public static final Integer PLAN_CREATE_AUTO = 1;

    public static final int PUT_SCHEDULE_TYPE_UNITARY = 1;//单一(投放排期存储对象类型)
    public static final int PUT_SCHEDULE_TYPE_SHARE = 2;//通发(投放排期存储对象类型)


    public static final String USER_LOGIN_URL = "";//TODO
    public static final String NVWA_COMMON_URL = "nvwa.common.url";

    //dictonary value
    public static final Integer POSITIO_NCHANNEL = 77; //频道
    public static final Integer CAR_LEVEL = 78; //车级别
    public static final Integer PRICE_RANGE = 79; //价格范围

    public static final Integer SELL_TYPE = 110;   // 收费类型
    public static final Integer SELL_TYPE_CPM = 111; // 收费类型CPM
    public static final Integer SELL_TYPE_CPC = 112; // 收费类型CPC

    public static final Integer TERMINAL_PC = 22; // 终端类型pc
    public static final Integer TERMINAL_WAP = 23; // 终端类型wap
    public static final Integer AIKA_TERMINAL_APP = 158; //爱卡终端类型APP

    public static final Integer AD_TYPE_PIC = 5; //广告位图片
    public static final Integer AD_TYPE_VIDEO = 4; // 视频
    public static final Integer AD_TYPE_NATIVE = 7; //信息流

    public static final Integer OS_TYPE = 159 ; // 系统

    public static final Integer USER_ADMIN = 1; //管理员id
    public static final Integer USER_XCAR_AGENT = 2; //爱卡代理商用户id
    
    //库存投放类型
    public static final Integer SDK_PUT_TYPE = 1;
    public static final Integer ORDER_PUT_TYPE = 2;

    //组件类型
    public static final Integer MODULE_TYPE_PIC = 5; //图片
    public static final Integer MODULE_TYPE_VIDEO = 4; //视频

    //判定字段
    public static final Integer WARN_FIELD_PV = 226; 
    public static final Integer WARN_FIELD_CLK = 227; 

    //爱卡默认直客代理商ID
    public static final int KA_AGENT_ID = 1;
    public static final String KA_FS_UUID = "M7nuyi";
    //爱卡默认抄底投放关联广告主UUID
    public static final String KA_ADVER_UUID = "qwerdf";

    public static final String KEY_REDIS_MONEY_LIMIT_UNIT = "money:limit:unit:{0}:{1}";  // 精确投放限额   money:limit:unit:{userId}:{unitId}
    public static final String KEY_REDIS_MONEY_LIMIT_PLAN = "money:limit:plan:{0}:{1}";  // 计划限额   money:limit:plan:{userId}:{planId}
    public static final String KEY_REDIS_KPI_LIMIT_ORDER_PUT_PV = "kpi:limit:pv:orderPut:{0}";  // 订单投放曝光kpi   kpi:limit:pv:orderPut:{orderPutId}
    public static final String KEY_REDIS_KPI_LIMIT_ORDER_PUT_CLK = "kpi:limit:clk:orderPut:{0}";  // 订单投放点击kpi   kpi:limit:pv:orderPut:{orderPutId}

    public static final String KEY_REDIS_CREATIVE_QUOTA_HOUR = "creative:hour:{0}:{1}:{2}:{3}";  // creative:hour:{quota}:{cid}:{ymd}:{HH}
	public static final String KEY_REDIS_CREATIVE_CALLBACK_HOUR = "callback:{0}:clk:{1}:{2}";// callback:{cid}:clk:{ymd}:{HH}

    public static final String KEY_REDIS_MONEY_TOTAL = "money:total:{0}";  // 余额  money:total:{uuid}
    public static final String KEY_REDIS_MONEY_COST = "money:cost:{0}:{1}";  // 花费  money:cost:{userId}:{ymd}
    public static final String KEY_REDIS_AD_POSITION = "adposition:{0}";   // 广告位信息 adposition:{pid(uuid)}
    public static final String KEY_REDIS_AD_CAROUSEL = "adcarousel:{0}";   // ad_carousel 信息 adcarousel:{appid}
    public static final String KEY_FLOW_CONTROL = "flow:control";
    public static final String KEY_WARTN_SETTINGS = "warn:settings";  
    public static final String KEY_PROHIBITED_DATE = "prohibite:date"; 

    public static final Integer MONEY_TYPE_RECHARGE = 1; //充值类型：充值
    public static final Integer MONEY_TYPE_REFUND = 2;   //充值类型：退款

    public static final String KEY_REDIS_MEDIA_QUOTA_HOUR = "media:hour:{0}:{1}:{2}:{3}";  // media:hour:{quota}:{appId}:{ymd}:{HH}

    //media:day:{quota}:{appId}:{posId}:{channel}:{version}:{yyyyMMdd}
    public static final String QUOTA_MEDIA_DAY_KEY =  "media:day:{0}:{1}:{2}:{3}:{4}:{5}";
    //catalog:day:{quota}:{level1}:{level2}:{level3}:{ymd}
    public static final String QUOTA_CATALOG_DAY_KEY = "catalog:day:{0}:{1}:{2}:{3}:{4}";

    /**
     * FTP
     */
    public static final int FTP_DEFAULT_PORT = 21;
    public static final String UPLOAD_URL_HOST = PropertyGetter.getString("upload.url.host");
    public static final String FTP_UPLOAD_PATH = PropertyGetter.getString("ftp.upload.path");
    public static final String FTP_PATH = PropertyGetter.getString("ftp.path");
    public static final String FTP_HOST = PropertyGetter.getString("ftp.host");
    public static final Integer FTP_PORT = PropertyGetter.getInt("ftp.port", FTP_DEFAULT_PORT);
    public static final String FTP_USERNAME = PropertyGetter.getString("ftp.username");
    public static final String FTP_PASSWORD = PropertyGetter.getString("ftp.password");

    public static final String UPLOAD_LOCAL_PATH = PropertyGetter.getString("upload.local.path");
    /**
     *权限系统
     */
    public static final String AUTH_HOST = PropertyGetter.getString("auth.host");
    public static final String AUTH_GETALLAUTH = PropertyGetter.getString("auth.getallauth");
    public static final String AUTH_GETAUTH = PropertyGetter.getString("auth.getAuth");
    public static final String AUTH_LOGIN = PropertyGetter.getString("auth.login");
    public static final String AUTH_LOGOUT = PropertyGetter.getString("auth.logout");
    /**
     * 消息任务相关
     */
    public static final String DATASOURCE_DOWNLOAD_MQ = PropertyGetter.getString("datasource.download.mq");
    public static final String DATASOURCE_DOWNLOAD_MQ_KEY = "datasource_";
    public static final String DATASOURCE_DOWNLOAD_FILE_KEY = "ds:{0}:file";
    public static final String DATASOURCE_DOWNLOAD_FILE_OK_KEY = "ds:{0}:done";
    public static final String DATASOURCE_DOWNLOAD_PATH = PropertyGetter.getString("datasource.download.path");
    public static final String RULE_JUDGE_MQ = PropertyGetter.getString("rule.judge.mq");
    public static final String RULE_JUDGE_MQ_KEY = "judge_";
    /**
     * 已判定过的文件
     */
    public static final String DATASOUCE_FILE_JUDGED = "ds:judge:{0}:{1}";
    /**
     * 数据源预览行数
     */
    public static final int DMP_DATA_READ_LINE = 5;
    /**
     * 规则关系
     */
    public static final int DMP_RULE_RELATION_AND = 0;
    public static final int DMP_RULE_RELATION_OR = 1;
    /**
     * 数据操作
     */
    public static final int DMP_RULE_OPERATE_EQ = 1;
    public static final int DMP_RULE_OPERATE_NOTEQ = 2;
    public static final int DMP_RULE_OPERATE_GT = 3;
    public static final int DMP_RULE_OPERATE_GTEQ = 4;
    public static final int DMP_RULE_OPERATE_LT = 5;
    public static final int DMP_RULE_OPERATE_LTEQ = 6;
    public static final int DMP_RULE_OPERATE_CONT = 7;
    public static final int DMP_RULE_OPERATE_NOTCONT = 8;
    /**
     *数据集列数据类型
     */
    public static final int COLUMN_DATATYPE_STRING = 1;
    public static final int COLUMN_DATATYPE_NUM = 2;
    public static final int COLUMN_DATATYPE_DATE = 3;

    public static final String DMP_KEY = "dmp:{0}";

    /**
     * 数据源分隔符
     */
    private static final Map<Integer, String> SIGN_MAP = Maps.newHashMap();
    public static String getSign(Integer signCode){
        return SIGN_MAP.get(signCode);
    }

    static {
        SIGN_MAP.put(1,"\t");
        SIGN_MAP.put(2,";");
        SIGN_MAP.put(3,",");
        SIGN_MAP.put(4," ");
    }

    public static boolean isContainsSpecialChar(String checkStr){
        boolean contains = false;
        String[] specialChars = {"|","(","[","{","}","]",")","?","*","+",".","/","\\","^","-","$"};
        if (StringUtils.isNotBlank(checkStr)) {
            for (int i = 0; i < specialChars.length; i++) {
                if (checkStr.contains(specialChars[i])) {
                    contains = true;
                    break;
                }
            }
        }
        return contains;
    }

}
