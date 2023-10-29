package com.iwanvi.nvwa.openapi.dsp.model;

import java.util.List;

public class AdRequest {

	private String id;
	private App app;
	private Device device;
	private Site site;
	private List<Imp> imps;
	private boolean debugOn;
	private boolean isDefault;
	
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isDebugOn() {
		return debugOn;
	}

	public void setDebugOn(boolean debugOn) {
		this.debugOn = debugOn;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void setImps(List<Imp> imps) {
		this.imps = imps;
	}

	public String getId() {
		return id;
	}

	public List<Imp> getImps() {
		return imps;
	}

	public App getApp() {
		return app;
	}

	public Device getDevice() {
		return device;
	}

	public Site getSite() {
		return site;
	}

	public static class Imp {
		private String id;
		private String posId;
		private Object ext;
		private String tmids; //广告位支持模版id, 多个按逗号分隔
		
		public String getTmids() {
			return tmids;
		}

		public void setTmids(String tmids) {
			this.tmids = tmids;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getPosId() {
			return posId;
		}

		public void setPosId(String posId) {
			this.posId = posId;
		}

		public Object getExt() {
			return ext;
		}

		public void setExt(Object ext) {
			this.ext = ext;
		}
	}

	public static class App {

		public String getBundle() {
			return bundle;
		}

		public void setBundle(String bundle) {
			this.bundle = bundle;
		}

		private String appId;
		private String name;
		private String version;
		private String channel;
		private String channel2;
		private String bundle; // 安卓的包名或者ios的appid
		private String origChannel; // 原始渠道号
		private Object ext;

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getOrigChannel() {
			return origChannel;
		}

		public void setOrigChannel(String origChannel) {
			this.origChannel = origChannel;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public String getChannel2() {
			return channel2;
		}

		public void setChannel2(String channel2) {
			this.channel2 = channel2;
		}

		public Object getExt() {
			return ext;
		}

		public void setExt(Object ext) {
			this.ext = ext;
		}
	}

	public static class Device {

		private String did;
		private String ip;
		private String make;
		private String model;
		private String osv;
		private String ua;
		private int os;
		private int carrier;
		private int connectionType;
		private int deviceType;
		private int cw;
		private int ch;
		private String adid;
		private int density; // 屏幕密度
		private double lat; // 纬度
		private double lon; // 经度
		private String mac; // 客户端mac地址

		public int getDensity() {
			return density;
		}

		public void setDensity(int density) {
			this.density = density;
		}

		public String getAdid() {
			return adid;
		}

		public void setAdid(String adid) {
			this.adid = adid;
		}

		public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public int getCw() {
			return cw;
		}

		public void setCw(int cw) {
			this.cw = cw;
		}

		public int getCh() {
			return ch;
		}

		public void setCh(int ch) {
			this.ch = ch;
		}

		public String getUa() {
			return ua;
		}

		public void setUa(String ua) {
			this.ua = ua;
		}

		public String getDid() {
			return did;
		}

		public void setDid(String did) {
			this.did = did;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getMake() {
			return make;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLon() {
			return lon;
		}

		public void setLon(double lon) {
			this.lon = lon;
		}

		public void setMake(String make) {
			this.make = make;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getOsv() {
			return osv;
		}

		public void setOsv(String osv) {
			this.osv = osv;
		}

		public int getOs() {
			return os;
		}

		public void setOs(int os) {
			this.os = os;
		}

		public int getCarrier() {
			return carrier;
		}

		public void setCarrier(int carrier) {
			this.carrier = carrier;
		}

		public int getConnectionType() {
			return connectionType;
		}

		public void setConnectionType(int connectionType) {
			this.connectionType = connectionType;
		}

		public int getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(int deviceType) {
			this.deviceType = deviceType;
		}

	}

	public class Site {
		private String name;
		private String page;
		private String ref;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPage() {
			return page;
		}

		public void setPage(String page) {
			this.page = page;
		}

		public String getRef() {
			return ref;
		}

		public void setRef(String ref) {
			this.ref = ref;
		}

	}
}
