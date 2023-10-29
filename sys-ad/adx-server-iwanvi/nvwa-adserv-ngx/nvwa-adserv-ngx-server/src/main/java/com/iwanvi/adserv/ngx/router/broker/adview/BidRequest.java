/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.adview;

import javax.annotation.Generated;

/**
 * @author wangweiping
 *
 */
public class BidRequest {
	private String ver = "2.5";
	private int n = 1;
	private String appid;
	// 广告形式：0=>广告条 1=>插屏 4=>开屏 5=>视频 6=>原生 8=>wap
	private int pt;
	private int html5 = 2; // 0-任意广告, 1-html5广告,2-非html5广告
	private int supmacro = 0; // 是否支持宏替换
	private String posId;
	private String pack;
	private int w;
	private int h;
	private int tab; // 设备终端类型: 0=>手机 1=>平板
	private String ip;
	private int os; // 0-安卓, 1-ios
	private String bdr; // 操作系统版本号
	private String tp; // 设备终端型号
	private String brd; // 设备终端品牌
	private String ua;

	public String getVer() {
		return ver;
	}

	public int getN() {
		return n;
	}

	public String getAppid() {
		return appid;
	}

	public int getPt() {
		return pt;
	}

	public int getHtml5() {
		return html5;
	}

	public int getSupmacro() {
		return supmacro;
	}

	public String getPosId() {
		return posId;
	}

	public String getPack() {
		return pack;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getTab() {
		return tab;
	}

	public String getIp() {
		return ip;
	}

	public int getOs() {
		return os;
	}

	public String getBdr() {
		return bdr;
	}

	public String getTp() {
		return tp;
	}

	public String getBrd() {
		return brd;
	}

	public String getUa() {
		return ua;
	}

	public int getSw() {
		return sw;
	}

	public int getSh() {
		return sh;
	}

	public double getDeny() {
		return deny;
	}

	public String getSn() {
		return sn;
	}

	public String getAndid() {
		return andid;
	}

	public String getMac() {
		return mac;
	}

	public String getDidsha1() {
		return didsha1;
	}

	public String getDpidsha1() {
		return dpidsha1;
	}

	public String getMacsha1() {
		return macsha1;
	}

	public String getDidmd5() {
		return didmd5;
	}

	public String getDpidmd5() {
		return dpidmd5;
	}

	public String getMacmd5() {
		return macmd5;
	}

	public String getNt() {
		return nt;
	}

	public String getNop() {
		return nop;
	}

	public String getCountry() {
		return country;
	}

	public String getLanguage() {
		return language;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public Integer getLocType() {
		return locType;
	}

	public String getImsi() {
		return imsi;
	}

	public String getLac() {
		return lac;
	}

	public String getCid() {
		return cid;
	}

	public String getBssid() {
		return bssid;
	}

	public String getSsid() {
		return ssid;
	}

	public String getIccid() {
		return iccid;
	}

	public int getJb() {
		return jb;
	}

	public int getTm() {
		return tm;
	}

	public long getTime() {
		return time;
	}

	public String getToken() {
		return token;
	}

	public int getBaseFloor() {
		return baseFloor;
	}

	public Integer getYob() {
		return yob;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public String getGender() {
		return gender;
	}

	public Integer getGdpr() {
		return gdpr;
	}

	public String getConsent() {
		return consent;
	}

	public Integer getHv() {
		return hv;
	}

	public String getMitoken() {
		return mitoken;
	}

	private int sw; // 设备宽
	private int sh; // 设备高
	private double deny; // 设备屏幕密度
	private String sn; // 设备唯一标识符，安卓imei,ios idfa
	private String andid; // 安卓id
	private String mac; // 可选
	private String didsha1; // 安卓imei的sha1, 可选
	private String dpidsha1; // 安卓id或者idfa的sha1, 可选
	private String macsha1; // 可选
	private String didmd5; // 可选，imei的md5
	private String dpidmd5; // 可选
	private String macmd5; // 可选
	private String nt = ""; // wifi/2g/3g/4g。没有就填""字符串
	private String nop = ""; // 手机运营商代号
	private String country = ""; // 可选
	private String language; // 可选

	private double lat; // 可选，纬度
	private double lon; // 可选，经度
	private Integer locType; // 可选，经纬度信息来源 1=> GPS 经纬度 2=> IP 地址 3=> 用户定制
	private String imsi; // 可选，国际移动用户识别码， 储存在 SIM 卡中
	private String lac; // 可选，位置区号(location area code)，用于识别 GSM 移动通信网中的一个位置区
	private String cid; // 基站小区号（cell identity）。 小区识别， 为了唯一地表示 GSMPLMN 中的每个小区，网络运营者需分配给网络中所有的小区一个代码
	private String bssid; // 可选，WIFI 的 bssid
	private String ssid; // 可选，WIFI的ssid
	private String iccid; // 可选，安卓iccid
	private int jb; // 可选，安卓的越狱情况
	private int tm; // 可选，是否为测试模式
	private long time = System.currentTimeMillis(); // 请求时间戳
	private String token; // 必需，授权使用API的授权码
	private int baseFloor; // 分*100
	private Integer yob;
	private Integer maxAge;
	private Integer minAge;
	private String gender;
	private Integer gdpr;
	private String consent; // Consent string according to IAB's consent string format 1.1
	private Integer hv; // 手机横竖屏标识
	private String mitoken; // 小米设备识别设备参数。

	@Generated("SparkTools")
	private BidRequest(Builder builder) {
		this.appid = builder.appid;
		this.pt = builder.pt;
		this.posId = builder.posId;
		this.pack = builder.pack;
		this.w = builder.w;
		this.h = builder.h;
		this.tab = builder.tab;
		this.ip = builder.ip;
		this.os = builder.os;
		this.bdr = builder.bdr;
		this.tp = builder.tp;
		this.brd = builder.brd;
		this.ua = builder.ua;
		this.sw = builder.sw;
		this.sh = builder.sh;
		this.deny = builder.deny;
		this.sn = builder.sn;
		this.andid = builder.andid;
		this.mac = builder.mac;
		this.didsha1 = builder.didsha1;
		this.dpidsha1 = builder.dpidsha1;
		this.macsha1 = builder.macsha1;
		this.didmd5 = builder.didmd5;
		this.dpidmd5 = builder.dpidmd5;
		this.macmd5 = builder.macmd5;
		this.nt = builder.nt;
		this.nop = builder.nop;
		this.country = builder.country;
		this.language = builder.language;
		this.lat = builder.lat;
		this.lon = builder.lon;
		this.locType = builder.locType;
		this.imsi = builder.imsi;
		this.lac = builder.lac;
		this.cid = builder.cid;
		this.bssid = builder.bssid;
		this.ssid = builder.ssid;
		this.iccid = builder.iccid;
		this.jb = builder.jb;
		this.tm = builder.tm;
		this.time = builder.time;
		this.token = builder.token;
		this.baseFloor = builder.baseFloor;
		this.yob = builder.yob;
		this.maxAge = builder.maxAge;
		this.minAge = builder.minAge;
		this.gender = builder.gender;
		this.gdpr = builder.gdpr;
		this.consent = builder.consent;
		this.hv = builder.hv;
		this.mitoken = builder.mitoken;
	}

	@Generated("SparkTools")
	public BidRequest() {
	}

	/**
	 * Creates builder to build {@link BidRequest}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link BidRequest}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String appid;
		private int pt;
		private String posId;
		private String pack;
		private int w;
		private int h;
		private int tab;
		private String ip;
		private int os;
		private String bdr;
		private String tp;
		private String brd;
		private String ua;
		private int sw;
		private int sh;
		private double deny;
		private String sn;
		private String andid;
		private String mac;
		private String didsha1;
		private String dpidsha1;
		private String macsha1;
		private String didmd5;
		private String dpidmd5;
		private String macmd5;
		private String nt;
		private String nop;
		private String country;
		private String language;
		private double lat;
		private double lon;
		private Integer locType;
		private String imsi;
		private String lac;
		private String cid;
		private String bssid;
		private String ssid;
		private String iccid;
		private int jb;
		private int tm;
		private long time;
		private String token;
		private int baseFloor;
		private Integer yob;
		private Integer maxAge;
		private Integer minAge;
		private String gender;
		private Integer gdpr;
		private String consent;
		private Integer hv;
		private String mitoken;

		private Builder() {
		}

		public Builder withAppid(String appid) {
			this.appid = appid;
			return this;
		}

		public Builder withPt(int pt) {
			this.pt = pt;
			return this;
		}

		public Builder withPosId(String posId) {
			this.posId = posId;
			return this;
		}

		public Builder withPack(String pack) {
			this.pack = pack;
			return this;
		}

		public Builder withw(int w) {
			this.w = w;
			return this;
		}

		public Builder withh(int h) {
			this.h = h;
			return this;
		}

		public Builder withTab(int tab) {
			this.tab = tab;
			return this;
		}

		public Builder withIp(String ip) {
			this.ip = ip;
			return this;
		}

		public Builder withOs(int os) {
			this.os = os;
			return this;
		}

		public Builder withBdr(String bdr) {
			this.bdr = bdr;
			return this;
		}

		public Builder withTp(String tp) {
			this.tp = tp;
			return this;
		}

		public Builder withBrd(String brd) {
			this.brd = brd;
			return this;
		}

		public Builder withUa(String ua) {
			this.ua = ua;
			return this;
		}

		public Builder withSw(int sw) {
			this.sw = sw;
			return this;
		}

		public Builder withSh(int sh) {
			this.sh = sh;
			return this;
		}

		public Builder withDeny(double deny) {
			this.deny = deny;
			return this;
		}

		public Builder withSn(String sn) {
			this.sn = sn;
			return this;
		}

		public Builder withAndid(String andid) {
			this.andid = andid;
			return this;
		}

		public Builder withMac(String mac) {
			this.mac = mac;
			return this;
		}

		public Builder withDidsha1(String didsha1) {
			this.didsha1 = didsha1;
			return this;
		}

		public Builder withDpidsha1(String dpidsha1) {
			this.dpidsha1 = dpidsha1;
			return this;
		}

		public Builder withMacsha1(String macsha1) {
			this.macsha1 = macsha1;
			return this;
		}

		public Builder withDidmd5(String didmd5) {
			this.didmd5 = didmd5;
			return this;
		}

		public Builder withDpidmd5(String dpidmd5) {
			this.dpidmd5 = dpidmd5;
			return this;
		}

		public Builder withMacmd5(String macmd5) {
			this.macmd5 = macmd5;
			return this;
		}

		public Builder withNt(String nt) {
			this.nt = nt;
			return this;
		}

		public Builder withNop(String nop) {
			this.nop = nop;
			return this;
		}

		public Builder withCountry(String country) {
			this.country = country;
			return this;
		}

		public Builder withLanguage(String language) {
			this.language = language;
			return this;
		}

		public Builder withLat(double lat) {
			this.lat = lat;
			return this;
		}

		public Builder withLon(double lon) {
			this.lon = lon;
			return this;
		}

		public Builder withLocType(Integer locType) {
			this.locType = locType;
			return this;
		}

		public Builder withImsi(String imsi) {
			this.imsi = imsi;
			return this;
		}

		public Builder withLac(String lac) {
			this.lac = lac;
			return this;
		}

		public Builder withCid(String cid) {
			this.cid = cid;
			return this;
		}

		public Builder withBssid(String bssid) {
			this.bssid = bssid;
			return this;
		}

		public Builder withSsid(String ssid) {
			this.ssid = ssid;
			return this;
		}

		public Builder withIccid(String iccid) {
			this.iccid = iccid;
			return this;
		}

		public Builder withJb(int jb) {
			this.jb = jb;
			return this;
		}

		public Builder withTm(int tm) {
			this.tm = tm;
			return this;
		}

		public Builder withTime(long time) {
			this.time = time;
			return this;
		}

		public Builder withToken(String token) {
			this.token = token;
			return this;
		}

		public Builder withBaseFloor(int baseFloor) {
			this.baseFloor = baseFloor;
			return this;
		}

		public Builder withYob(Integer yob) {
			this.yob = yob;
			return this;
		}

		public Builder withMaxAge(Integer maxAge) {
			this.maxAge = maxAge;
			return this;
		}

		public Builder withMinAge(Integer minAge) {
			this.minAge = minAge;
			return this;
		}

		public Builder withGender(String gender) {
			this.gender = gender;
			return this;
		}

		public Builder withGdpr(Integer gdpr) {
			this.gdpr = gdpr;
			return this;
		}

		public Builder withConsent(String consent) {
			this.consent = consent;
			return this;
		}

		public Builder withHv(Integer hv) {
			this.hv = hv;
			return this;
		}

		public Builder withMitoken(String mitoken) {
			this.mitoken = mitoken;
			return this;
		}

		public BidRequest build() {
			return new BidRequest(this);
		}
	}
}
