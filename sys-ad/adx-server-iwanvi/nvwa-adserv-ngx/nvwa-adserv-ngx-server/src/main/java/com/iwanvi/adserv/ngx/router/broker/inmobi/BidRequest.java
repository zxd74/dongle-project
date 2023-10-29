/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.inmobi;

import javax.annotation.Generated;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wangweiping
 *
 */
public class BidRequest {
	private App app;
	private Imp imp;
	private Device device;
	private Ext ext = new Ext();

	@Generated("SparkTools")
	private BidRequest(Builder builder) {
		this.app = builder.app;
		this.imp = builder.imp;
		this.device = builder.device;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public Imp getImp() {
		return imp;
	}

	public void setImp(Imp imp) {
		this.imp = imp;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Ext getExt() {
		return ext;
	}

	public void setExt(Ext ext) {
		this.ext = ext;
	}

	@Generated("SparkTools")
	public BidRequest() {
	}

	final static class App {
		public String getId() {
			return id;
		}

		public String getBundle() {
			return bundle;
		}

		private String id;
		private String bundle;

		@Generated("SparkTools")
		private App(Builder builder) {
			this.id = builder.id;
			this.bundle = builder.bundle;
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
			private String id;
			private String bundle;

			private Builder() {
			}

			public Builder withId(String id) {
				this.id = id;
				return this;
			}

			public Builder withBundle(String bundle) {
				this.bundle = bundle;
				return this;
			}

			public App build() {
				return new App(this);
			}
		}

	}

	/**
	 * @author wangweiping
	 *
	 */
	final static class Geo {
		public float getLat() {
			return lat;
		}

		public float getLon() {
			return lon;
		}

		public float getAccu() {
			return accu;
		}

		private float lat;
		private float lon;
		private float accu;

		@Generated("SparkTools")
		private Geo(Builder builder) {
			this.lat = builder.lat;
			this.lon = builder.lon;
			this.accu = builder.accu;
		}

		@Generated("SparkTools")
		public Geo() {
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
			private float lat;
			private float lon;
			private float accu;

			private Builder() {
			}

			public Builder withLat(float lat) {
				this.lat = lat;
				return this;
			}

			public Builder withLon(float lon) {
				this.lon = lon;
				return this;
			}

			public Builder withAccu(float accu) {
				this.accu = accu;
				return this;
			}

			public Geo build() {
				return new Geo(this);
			}
		}

	}

	final static class Device {
		public String getIfa() {
			return ifa;
		}

		public String getGpid() {
			return gpid;
		}

		public String getMd5Imei() {
			return md5Imei;
		}

		public String getSha1Imei() {
			return sha1Imei;
		}

		public String getO1() {
			return o1;
		}

		public String getUm5() {
			return um5;
		}

		public String getUa() {
			return ua;
		}

		public String getIp() {
			return ip;
		}

		public String getOs() {
			return os;
		}

		public String getOsv() {
			return osv;
		}

		public Geo getGeo() {
			return geo;
		}

		public String getModel() {
			return model;
		}

		private String ifa; // ios的idfa
		private String gpid;
		@JSONField(name = "md5_imei")
		private String md5Imei;
		@JSONField(name = "sha1_imei")
		private String sha1Imei;
		private String o1; // sha1(adid)
		// md5(lowercase(adid))
		private String um5;
		private String ua;
		private String ip;
		private String os;
		private String osv;
		private String model;
		private int connectiontype;
		private Geo geo;

		@Generated("SparkTools")
		private Device(Builder builder) {
			this.ifa = builder.ifa;
			this.gpid = builder.gpid;
			this.md5Imei = builder.md5Imei;
			this.sha1Imei = builder.sha1Imei;
			this.o1 = builder.o1;
			this.um5 = builder.um5;
			this.ua = builder.ua;
			this.ip = builder.ip;
			this.os = builder.os;
			this.osv = builder.osv;
			this.model = builder.model;
			this.connectiontype = builder.connectiontype;
			this.geo = builder.geo;
		}

		public int getConnectiontype() {
			return connectiontype;
		}

		@Generated("SparkTools")
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
			private String ifa;
			private String gpid;
			private String md5Imei;
			private String sha1Imei;
			private String o1;
			private String um5;
			private String ua;
			private String ip;
			private String os;
			private String osv;
			private String model;
			private int connectiontype;
			private Geo geo;

			private Builder() {
			}

			public Builder withIfa(String ifa) {
				this.ifa = ifa;
				return this;
			}

			public Builder withGpid(String gpid) {
				this.gpid = gpid;
				return this;
			}

			public Builder withMd5Imei(String md5Imei) {
				this.md5Imei = md5Imei;
				return this;
			}

			public Builder withSha1Imei(String sha1Imei) {
				this.sha1Imei = sha1Imei;
				return this;
			}

			public Builder withO1(String o1) {
				this.o1 = o1;
				return this;
			}

			public Builder withUm5(String um5) {
				this.um5 = um5;
				return this;
			}

			public Builder withUa(String ua) {
				this.ua = ua;
				return this;
			}

			public Builder withIp(String ip) {
				this.ip = ip;
				return this;
			}

			public Builder withOs(String os) {
				this.os = os;
				return this;
			}

			public Builder withOsv(String osv) {
				this.osv = osv;
				return this;
			}

			public Builder withModel(String model) {
				this.model = model;
				return this;
			}

			public Builder withConnectiontype(int connectiontype) {
				this.connectiontype = connectiontype;
				return this;
			}

			public Builder withGeo(Geo geo) {
				this.geo = geo;
				return this;
			}

			public Device build() {
				return new Device(this);
			}
		}

	}

	final static class Imp {
		@JSONField(name = "native")
		private Native nativeAd;
		private Banner banner;

		private Integer secure = 0;
		private String trackertype = "url_ping";

		private Ext ext = new Ext();

		@Generated("SparkTools")
		private Imp(Builder builder) {
			this.nativeAd = builder.nativeAd;
			this.banner = builder.banner;
		}

		public Native getNativeAd() {
			return nativeAd;
		}

		public Banner getBanner() {
			return banner;
		}

		public Integer getSecure() {
			return secure;
		}

		public String getTrackertype() {
			return trackertype;
		}

		public Ext getExt() {
			return ext;
		}

		@Generated("SparkTools")
		public Imp() {
		}

		final static class Ext {
			private Integer ads = 1;

			public Integer getAds() {
				return ads;
			}
		}

		final static class Native {
			private Integer layout = 0;

			public Integer getLayout() {
				return layout;
			}
		}

		final static class Banner {
			private Integer h;
			private Integer w;

			@Generated("SparkTools")
			private Banner(Builder builder) {
				this.h = builder.h;
				this.w = builder.w;
			}

			@Generated("SparkTools")
			public Banner() {
			}

			public Integer getH() {
				return h;
			}

			public Integer getW() {
				return w;
			}

			/**
			 * Creates builder to build {@link Banner}.
			 * 
			 * @return created builder
			 */
			@Generated("SparkTools")
			public static Builder builder() {
				return new Builder();
			}

			/**
			 * Builder to build {@link Banner}.
			 */
			@Generated("SparkTools")
			public static final class Builder {
				private Integer h;
				private Integer w;

				private Builder() {
				}

				public Builder withh(Integer h) {
					this.h = h;
					return this;
				}

				public Builder withw(Integer w) {
					this.w = w;
					return this;
				}

				public Banner build() {
					return new Banner(this);
				}
			}
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
			private Native nativeAd;
			private Banner banner;

			private Builder() {
			}

			public Builder withNativeAd(Native nativeAd) {
				this.nativeAd = nativeAd;
				return this;
			}

			public Builder withBanner(Banner banner) {
				this.banner = banner;
				return this;
			}

			public Imp build() {
				return new Imp(this);
			}
		}
	}

	final static class Ext {
		private String responseformat = "json";
		private boolean externalSupported = true;

		public String getResponseformat() {
			return responseformat;
		}

		public boolean isExternalSupported() {
			return externalSupported;
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
		private App app;
		private Imp imp;
		private Device device;

		private Builder() {
		}

		public Builder withApp(App app) {
			this.app = app;
			return this;
		}

		public Builder withImp(Imp imp) {
			this.imp = imp;
			return this;
		}

		public Builder withDevice(Device device) {
			this.device = device;
			return this;
		}

		public BidRequest build() {
			return new BidRequest(this);
		}
	}
}
