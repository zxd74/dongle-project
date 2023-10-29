package com.iwanvi.nvwa.crontab.util;

import com.iwanvi.nvwa.common.utils.PropertyGetter;

public class CrontabConstants {

    public static final Integer CONF_ADX_BID_DISCOUNT = 2;

    public static final Integer SSP_JOIN_TYPE = 71;
    
    public static final Integer COMPANY_AGENT_TYPE = 1;
    public static final Integer COMPANY_ADVER_TYPE = 2;

    
	public static final double APP_PAYDISCOUNT = 1;
	public static final double ADP_DEFAULTCTR = 1;
	public static final double ADP_ADXCTR = 0;//TODO
	
	/**
	 * adp type 
	 */
    public static final Integer AD_TYPE_TEXT = 216;//文字链
    public static final Integer AD_TYPE_PIC = 5;//横幅
    public static final Integer AD_TYPE_SCREEN = 218;//插屏
    public static final Integer AD_TYPE_DYNAMIC = 6;//开屏
    public static final Integer AD_TYPE_NATIVE = 7;//信息流
    public static final Integer AD_TYPE_VIDEO_INCENTIVE = 217;//激励视频
    public static final Integer AD_TYPE_VIDEO = 4;//贴片

    
    /**
               * 组件类型
     */
    public static final Integer MODULE_VIDEO = 4;
    public static final Integer MODULE_TITLE = 1;
    public static final Integer MODULE_PIC1 = 5;
    public static final Integer MODULE_PIC2 = 6;
    
    /**
     * FTP
     */
    public static final int FTP_DEFAULT_PORT = 21;
    public static final String UPLOAD_URL_HOST = PropertyGetter.getString("upload.url.host");
    public static final String FTP_PATH = PropertyGetter.getString("ftp.path");
    public static final String FTP_HOST = PropertyGetter.getString("ftp.host");
    public static final Integer FTP_PORT = PropertyGetter.getInt("ftp.port", FTP_DEFAULT_PORT);
    public static final String FTP_USERNAME = PropertyGetter.getString("ftp.username");
    public static final String FTP_PASSWORD = PropertyGetter.getString("ftp.password");

    public static final String FTP_UPLOAD_PATH = PropertyGetter.getString("ftp.upload.path");
    

    public static final Integer COST_TYPE_CPT = 113;
    
    /**
     * put type
     */
    public static final Integer PUT_TYPE_BOTTOM = 18;
    public static final Integer PUT_TYPE_ORDER = 17;
    public static final Integer PUT_TYPE_ACCURATE = 19;

    public static final String AK_UUID = "M7nuyi";
    
    public static final Integer IS_NOT_PRIVATE = 215;
    
    public static final Integer IS_DEAL = 235;

    public static final String DAT = "dat";

    public static final String ENTIRE_FILE_PREFIX = PropertyGetter.getString("entire.fle.prefix");
    
}
