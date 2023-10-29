/**
 * 
 */
package com.iwanvi.adserv.ngx.router.brokers.iflytek;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wangweiping
 *
 */
public class BidRequest {
	private String id;

	@JSONField(name = "api_ver")
	private String apiVer = "1.0.0";
	@JSONField(name = "settle_type")
	private Integer settleType = 1;

	private List<Imp> imps;
	private Device device;
	private App app;
	private String[] cur = { "CNY" };
	private Map<String, Object> ext;

	@Generated("SparkTools")
	private BidRequest(Builder builder) {
		this.id = builder.id;
		this.imps = builder.imps;
		this.device = builder.device;
		this.app = builder.app;
		this.ext = builder.ext;
	}

	public String getId() {
		return id;
	}

	public String getApiVer() {
		return apiVer;
	}

	public Integer getSettleType() {
		return settleType;
	}

	public List<Imp> getImps() {
		return imps;
	}

	public Device getDevice() {
		return device;
	}

	public App getApp() {
		return app;
	}

	public String[] getCur() {
		return cur;
	}

	public Map<String, Object> getExt() {
		return ext;
	}

	@Generated("SparkTools")
	public BidRequest() {
	}

	public static class Imp {
		@JSONField(name = "adunit_id")
		private String adunitId;

		private double bidfloor;
		private Pmp pmp;
		private Integer adw;
		private Integer adh;

		@JSONField(name = "dp_support_status")
		private Integer[] dpSupportStatus = { 1, 2 };
		@JSONField(name = "voice_ad_support_status")
		private Integer[] voiceAdSupportStatus;
		private Integer secure = 3;
		private Debug debug;
		private Map<String, Object> ext;

		public String getAdunitId() {
			return adunitId;
		}

		public void setAdunitId(String adunitId) {
			this.adunitId = adunitId;
		}

		public double getBidfloor() {
			return bidfloor;
		}

		public void setBidfloor(double bidfloor) {
			this.bidfloor = bidfloor;
		}

		public Pmp getPmp() {
			return pmp;
		}

		public void setPmp(Pmp pmp) {
			this.pmp = pmp;
		}

		public Integer getAdw() {
			return adw;
		}

		public void setAdw(Integer adw) {
			this.adw = adw;
		}

		public Integer getAdh() {
			return adh;
		}

		public void setAdh(Integer adh) {
			this.adh = adh;
		}

		public Integer[] getDpSupportStatus() {
			return dpSupportStatus;
		}

		public void setDpSupportStatus(Integer[] dpSupportStatus) {
			this.dpSupportStatus = dpSupportStatus;
		}

		public Integer[] getVoiceAdSupportStatus() {
			return voiceAdSupportStatus;
		}

		public void setVoiceAdSupportStatus(Integer[] voiceAdSupportStatus) {
			this.voiceAdSupportStatus = voiceAdSupportStatus;
		}

		public Integer getSecure() {
			return secure;
		}

		public void setSecure(Integer secure) {
			this.secure = secure;
		}

		public Debug getDebug() {
			return debug;
		}

		public void setDebug(Debug debug) {
			this.debug = debug;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		public Imp() {
		}

		@Generated("SparkTools")
		private Imp(Builder builder) {
			this.adunitId = builder.adunitId;
			this.bidfloor = builder.bidfloor;
			this.pmp = builder.pmp;
			this.adw = builder.adw;
			this.adh = builder.adh;
			this.dpSupportStatus = builder.dpSupportStatus;
			this.voiceAdSupportStatus = builder.voiceAdSupportStatus;
			this.secure = builder.secure;
			this.debug = builder.debug;
			this.ext = builder.ext;
		}

		/**
		 * Creates builder to build {@link Imp}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Imp}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private String adunitId;
			private double bidfloor;
			private Pmp pmp;
			private Integer adw;
			private Integer adh;
			private Integer[] dpSupportStatus;
			private Integer[] voiceAdSupportStatus;
			private Integer secure;
			private Debug debug;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withAdunitId(String adunitId) {
				this.adunitId = adunitId;
				return this;
			}

			public Builder withBidfloor(double bidfloor) {
				this.bidfloor = bidfloor;
				return this;
			}

			public Builder withPmp(Pmp pmp) {
				this.pmp = pmp;
				return this;
			}

			public Builder withAdw(Integer adw) {
				this.adw = adw;
				return this;
			}

			public Builder withAdh(Integer adh) {
				this.adh = adh;
				return this;
			}

			public Builder withDpSupportStatus(Integer[] dpSupportStatus) {
				this.dpSupportStatus = dpSupportStatus;
				return this;
			}

			public Builder withVoiceAdSupportStatus(Integer[] voiceAdSupportStatus) {
				this.voiceAdSupportStatus = voiceAdSupportStatus;
				return this;
			}

			public Builder withSecure(Integer secure) {
				this.secure = secure;
				return this;
			}

			public Builder withDebug(Debug debug) {
				this.debug = debug;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Imp build() {
				return new Imp(this);
			}
		}
	}

	public static class Pmp {
		private List<Deal> deals;
		private Map<String, Object> ext;

		public static class Deal {
			private String id;
			private Double bidfloor;
			private Map<String, Object> ext;

			@Generated("SparkTools")
			private Deal(Builder builder) {
				this.id = builder.id;
				this.bidfloor = builder.bidfloor;
				this.ext = builder.ext;
			}

			@Generated("SparkTools")
			public Deal() {
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public Double getBidfloor() {
				return bidfloor;
			}

			public void setBidfloor(Double bidfloor) {
				this.bidfloor = bidfloor;
			}

			public Map<String, Object> getExt() {
				return ext;
			}

			public void setExt(Map<String, Object> ext) {
				this.ext = ext;
			}

			/**
			 * Creates builder to build {@link Deal}.
			 * 
			 * @return created builder
			 */
			@Generated("SparkTools")
			public static Builder builder() {
				return new Builder();
			}

			/**
			 * Builder to build {@link Deal}.
			 */
			@Generated("SparkTools")
			public static final class Builder {
				private String id;
				private Double bidfloor;
				private Map<String, Object> ext = Collections.emptyMap();

				private Builder() {
				}

				public Builder withId(String id) {
					this.id = id;
					return this;
				}

				public Builder withBidfloor(Double bidfloor) {
					this.bidfloor = bidfloor;
					return this;
				}

				public Builder withExt(Map<String, Object> ext) {
					this.ext = ext;
					return this;
				}

				public Deal build() {
					return new Deal(this);
				}
			}

		}

		public List<Deal> getDeals() {
			return deals;
		}

		public void setDeals(List<Deal> deals) {
			this.deals = deals;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		public Pmp() {
		}

		@Generated("SparkTools")
		private Pmp(Builder builder) {
			this.deals = builder.deals;
			this.ext = builder.ext;
		}

		/**
		 * Creates builder to build {@link Pmp}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Pmp}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private List<Deal> deals = Collections.emptyList();
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withDeals(List<Deal> deals) {
				this.deals = deals;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Pmp build() {
				return new Pmp(this);
			}
		}
	}

	public static class Device {
		@JSONField(name = "device_type")
		private Integer deviceType;
		private Integer os;
		private String osv;
		private String adid;
		@JSONField(name = "adid_md5")
		private String adidMd5;

		private String imei;
		@JSONField(name = "imei_md5")
		private String imeiMd5;
		private String idfa;
		private String openudid;
		@JSONField(name = "openudid_md5")
		private String openudidMd5;
		private String mac;
		@JSONField(name = "mac_md5")
		private String macMd5;
		private Integer density;
		private Integer carrier;
		private Integer net;
		private String ip;
		private String ua;
		private Long ts;
		private Integer dvw;
		private Integer dvh;
		private Integer orientation;
		private String make;
		private String model;
		private String lan;

		public Integer getDeviceType() {
			return deviceType;
		}

		public void setDeviceType(Integer deviceType) {
			this.deviceType = deviceType;
		}

		public Integer getOs() {
			return os;
		}

		public void setOs(Integer os) {
			this.os = os;
		}

		public String getOsv() {
			return osv;
		}

		public void setOsv(String osv) {
			this.osv = osv;
		}

		public String getAdid() {
			return adid;
		}

		public void setAdid(String adid) {
			this.adid = adid;
		}

		public String getAdidMd5() {
			return adidMd5;
		}

		public void setAdidMd5(String adidMd5) {
			this.adidMd5 = adidMd5;
		}

		public String getImei() {
			return imei;
		}

		public void setImei(String imei) {
			this.imei = imei;
		}

		public String getImeiMd5() {
			return imeiMd5;
		}

		public void setImeiMd5(String imeiMd5) {
			this.imeiMd5 = imeiMd5;
		}

		public String getIdfa() {
			return idfa;
		}

		public void setIdfa(String idfa) {
			this.idfa = idfa;
		}

		public String getOpenudid() {
			return openudid;
		}

		public void setOpenudid(String openudid) {
			this.openudid = openudid;
		}

		public String getOpenudidMd5() {
			return openudidMd5;
		}

		public void setOpenudidMd5(String openudidMd5) {
			this.openudidMd5 = openudidMd5;
		}

		public String getMac() {
			return mac;
		}

		public void setMac(String mac) {
			this.mac = mac;
		}

		public String getMacMd5() {
			return macMd5;
		}

		public void setMacMd5(String macMd5) {
			this.macMd5 = macMd5;
		}

		public Integer getDensity() {
			return density;
		}

		public void setDensity(Integer density) {
			this.density = density;
		}

		public Integer getCarrier() {
			return carrier;
		}

		public void setCarrier(Integer carrier) {
			this.carrier = carrier;
		}

		public Integer getNet() {
			return net;
		}

		public void setNet(Integer net) {
			this.net = net;
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public String getUa() {
			return ua;
		}

		public void setUa(String ua) {
			this.ua = ua;
		}

		public Long getTs() {
			return ts;
		}

		public void setTs(Long ts) {
			this.ts = ts;
		}

		public Integer getDvw() {
			return dvw;
		}

		public void setDvw(Integer dvw) {
			this.dvw = dvw;
		}

		public Integer getDvh() {
			return dvh;
		}

		public void setDvh(Integer dvh) {
			this.dvh = dvh;
		}

		public Integer getOrientation() {
			return orientation;
		}

		public void setOrientation(Integer orientation) {
			this.orientation = orientation;
		}

		public String getMake() {
			return make;
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

		public String getLan() {
			return lan;
		}

		public void setLan(String lan) {
			this.lan = lan;
		}

		public Geo getGeo() {
			return geo;
		}

		public void setGeo(Geo geo) {
			this.geo = geo;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		private Geo geo;
		private Map<String, Object> ext;

		@Generated("SparkTools")
		private Device(Builder builder) {
			this.deviceType = builder.deviceType;
			this.os = builder.os;
			this.osv = builder.osv;
			this.adid = builder.adid;
			this.adidMd5 = builder.adidMd5;
			this.imei = builder.imei;
			this.imeiMd5 = builder.imeiMd5;
			this.idfa = builder.idfa;
			this.openudid = builder.openudid;
			this.openudidMd5 = builder.openudidMd5;
			this.mac = builder.mac;
			this.macMd5 = builder.macMd5;
			this.density = builder.density;
			this.carrier = builder.carrier;
			this.net = builder.net;
			this.ip = builder.ip;
			this.ua = builder.ua;
			this.ts = builder.ts;
			this.dvw = builder.dvw;
			this.dvh = builder.dvh;
			this.orientation = builder.orientation;
			this.make = builder.make;
			this.model = builder.model;
			this.lan = builder.lan;
			this.geo = builder.geo;
			this.ext = builder.ext;
		}

		public Device() {
		}

		/**
		 * Creates builder to build {@link Device}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Device}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private Integer deviceType;
			private Integer os;
			private String osv;
			private String adid;
			private String adidMd5;
			private String imei;
			private String imeiMd5;
			private String idfa;
			private String openudid;
			private String openudidMd5;
			private String mac;
			private String macMd5;
			private Integer density;
			private Integer carrier;
			private Integer net;
			private String ip;
			private String ua;
			private Long ts;
			private Integer dvw;
			private Integer dvh;
			private Integer orientation;
			private String make;
			private String model;
			private String lan;
			private Geo geo;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withDeviceType(Integer deviceType) {
				this.deviceType = deviceType;
				return this;
			}

			public Builder withOs(Integer os) {
				this.os = os;
				return this;
			}

			public Builder withOsv(String osv) {
				this.osv = osv;
				return this;
			}

			public Builder withAdid(String adid) {
				this.adid = adid;
				return this;
			}

			public Builder withAdidMd5(String adidMd5) {
				this.adidMd5 = adidMd5;
				return this;
			}

			public Builder withImei(String imei) {
				this.imei = imei;
				return this;
			}

			public Builder withImeiMd5(String imeiMd5) {
				this.imeiMd5 = imeiMd5;
				return this;
			}

			public Builder withIdfa(String idfa) {
				this.idfa = idfa;
				return this;
			}

			public Builder withOpenudid(String openudid) {
				this.openudid = openudid;
				return this;
			}

			public Builder withOpenudidMd5(String openudidMd5) {
				this.openudidMd5 = openudidMd5;
				return this;
			}

			public Builder withMac(String mac) {
				this.mac = mac;
				return this;
			}

			public Builder withMacMd5(String macMd5) {
				this.macMd5 = macMd5;
				return this;
			}

			public Builder withDensity(Integer density) {
				this.density = density;
				return this;
			}

			public Builder withCarrier(Integer carrier) {
				this.carrier = carrier;
				return this;
			}

			public Builder withNet(Integer net) {
				this.net = net;
				return this;
			}

			public Builder withIp(String ip) {
				this.ip = ip;
				return this;
			}

			public Builder withUa(String ua) {
				this.ua = ua;
				return this;
			}

			public Builder withTs(Long ts) {
				this.ts = ts;
				return this;
			}

			public Builder withDvw(Integer dvw) {
				this.dvw = dvw;
				return this;
			}

			public Builder withDvh(Integer dvh) {
				this.dvh = dvh;
				return this;
			}

			public Builder withOrientation(Integer orientation) {
				this.orientation = orientation;
				return this;
			}

			public Builder withMake(String make) {
				this.make = make;
				return this;
			}

			public Builder withModel(String model) {
				this.model = model;
				return this;
			}

			public Builder withLan(String lan) {
				this.lan = lan;
				return this;
			}

			public Builder withGeo(Geo geo) {
				this.geo = geo;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Device build() {
				return new Device(this);
			}
		}
	}

	public static class Geo {
		private Double lat;
		private Double lon;
		private Map<String, Object> ext;

		public Double getLat() {
			return lat;
		}

		public void setLat(Double lat) {
			this.lat = lat;
		}

		public Double getLon() {
			return lon;
		}

		public void setLon(Double lon) {
			this.lon = lon;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		public Geo() {
		}

		@Generated("SparkTools")
		private Geo(Builder builder) {
			this.lat = builder.lat;
			this.lon = builder.lon;
			this.ext = builder.ext;
		}

		/**
		 * Creates builder to build {@link Geo}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Geo}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private Double lat;
			private Double lon;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withLat(Double lat) {
				this.lat = lat;
				return this;
			}

			public Builder withLon(Double lon) {
				this.lon = lon;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Geo build() {
				return new Geo(this);
			}
		}
	}

	public static class App {
		@JSONField(name = "app_ver")
		private String appVer;
		@JSONField(name = "app_name")
		private String appName;
		private Map<String, Object> ext;

		@Generated("SparkTools")
		private App(Builder builder) {
			this.appVer = builder.appVer;
			this.appName = builder.appName;
			this.ext = builder.ext;
		}

		public String getAppVer() {
			return appVer;
		}

		public void setAppVer(String appVer) {
			this.appVer = appVer;
		}

		public String getAppName() {
			return appName;
		}

		public void setAppName(String appName) {
			this.appName = appName;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		@Generated("SparkTools")
		public App() {
		}

		/**
		 * Creates builder to build {@link App}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link App}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private String appVer;
			private String appName;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withAppVer(String appVer) {
				this.appVer = appVer;
				return this;
			}

			public Builder withAppName(String appName) {
				this.appName = appName;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public App build() {
				return new App(this);
			}
		}
	}

	public static class Debug {
		@JSONField(name = "action_type")
		private Integer actionType;
		@JSONField(name = "landing_type")
		private Integer landingType;

		@Generated("SparkTools")
		private Debug(Builder builder) {
			this.actionType = builder.actionType;
			this.landingType = builder.landingType;
		}

		@Generated("SparkTools")
		public Debug() {
		}

		/**
		 * Creates builder to build {@link Debug}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Debug}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private Integer actionType;
			private Integer landingType;

			private Builder() {
			}

			public Builder withActionType(Integer actionType) {
				this.actionType = actionType;
				return this;
			}

			public Builder withLandingType(Integer landingType) {
				this.landingType = landingType;
				return this;
			}

			public Debug build() {
				return new Debug(this);
			}
		}

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
		private String id;
		private List<Imp> imps = Collections.emptyList();
		private Device device;
		private App app;
		private Map<String, Object> ext = Collections.emptyMap();

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withImps(List<Imp> imps) {
			this.imps = imps;
			return this;
		}

		public Builder withDevice(Device device) {
			this.device = device;
			return this;
		}

		public Builder withApp(App app) {
			this.app = app;
			return this;
		}

		public Builder withExt(Map<String, Object> ext) {
			this.ext = ext;
			return this;
		}

		public BidRequest build() {
			return new BidRequest(this);
		}
	}
}
