package com.fftime.ffmob.util;

import com.fftime.ffmob.SdkSettings;

import java.util.regex.Pattern;

public class Constants {
    public static final boolean DEBUG = true;
    public static final String VERSION_CODE = "0.1";

    public static final String SPY_REQ_URL = "http://api.f2time.com/sdk/getApps";
    public static final String SPY_RESP_URL = "http://api.f2time.com/sdk/existedApps";

    public static final String PAGE_HOST = "http://static.f2time.com/h5/";
    public static final String BANNER_PAGE = PAGE_HOST + "banner/banner.html";
    public static final String INTERSTITIAL_PAGE = PAGE_HOST + "interstitial/interstitial.html";

    //public static final String LOADAD_CGI = "http://api.f2time.com/sdk/getAds";
    //public static final String DEFAULT_API_DOMAIN ="123.57.145.7:9300";
    public static final String DEFAULT_API_DOMAIN = "api.iwanvi.loanflashing.com";
    public static final String LOADAD_CGI = "http://" + SdkSettings.getInstance().getApiDomain() + "/api/sdk/ad";

    /**
     * 广告策略
     */
    public static final String FETCH_AD_POLICY = "http://feifanadx.cread.com/api/ad";
    public static final String DEFAULT_CHANNEL = "iwanvi";
    public static final String TAOBAO_IP_SERVICE_URL = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";

    public static final Pattern ACCEPTED_URI_SCHEME = Pattern.compile("(?i)"
            + // switch on case insensitive matching
            '('
            + // begin group for scheme
            "(?:http|https|ftp|file)://" + "|(?:inline|data|about|javascript):" + "|(?:.*:.*@)"
            + ')' + "(.*)");

    public static final String INTENT_VAR_CLK_EVENT_DATA = "sdk_clkEventData";
    public static final String INTENT_VAR_REQ_EXT_PARAMS = "sdk_req_ext";

    public static final String FETCH_AD_PLATFORMS = "http://"+SdkSettings.getInstance().getApiDomain()+"/api/platform";
    //public static final String FETCH_AD_PLATFORMS_CPM = "http://"+SdkSettings.getInstance().getApiDomain()+"/api/platform";

    public static final String INTENT_VAR_AD = "sdk_ad";
    public static final String VOLUME_ON_ICON_ASSETS_URL = "imgs/v_on.png";
    public static final String VOLUME_OFF_ICON_ASSETS_URL = "imgs/v_off.png";
    public static final String CLOSE_ICON_ASSETS_URL = "imgs/v_close.png";
    public static final String VIDEO_DEFAULT_COVER = "imgs/v_default_cover.png";


    public static final String VOLUME_ON_ICON_ASSETS_URL_LARGE = "imgs/v_on_large.png";
    public static final String VOLUME_OFF_ICON_ASSETS_URL_LARGE = "imgs/v_off_large.png";
    public static final String CLOSE_ICON_ASSETS_URL_LARGE = "imgs/v_close_large.png";

    public static final String PATTERN_MACRO = "\\{[^{]*\\}";

    public static final int SDK_ADTYPE_CPT = 1;
    public static final int SDK_ADTYPE_CPM = 2;
}
