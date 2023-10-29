package com.iwanvi.adserv.ngx.router.broker.gdt;

import com.alibaba.fastjson.annotation.JSONField;
import javax.annotation.Generated;

//广点通竞价请求
public class BidRequest {
	private String api_version = "3.2";
	private Pos pos;
	private Media media;
	private Device device;
	private Network network;
	private Geo geo;


	@Generated("SparkTools")
	private BidRequest(Builder builder) {
		this.pos = builder.pos;
		this.media = builder.media;
		this.device = builder.device;
		this.network = builder.network;
		this.geo = builder.geo;
	}

	@Generated("SparkTools")
	public BidRequest() {
	}
	
	
	public String getApi_version() {
		return api_version;
	}

	public Pos getPos() {
		return pos;
	}

	public Media getMedia() {
		return media;
	}

	public Device getDevice() {
		return device;
	}

	public Network getNetwork() {
		return network;
	}

	public Geo getGeo() {
		return geo;
	}

	static class Pos {
		public long getId() {
			return id;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public boolean isSupportFullScreenInterstitial() {
			return supportFullScreenInterstitial;
		}

		public int getDeepLinkVersion() {
			return deepLinkVersion;
		}

		public int getAd_count() {
			return ad_count;
		}
		private long id;
		private int width;
		private int height;
		@JSONField(name="support_full_screen_interstitial")
		private boolean supportFullScreenInterstitial;
		private int ad_count=1;
		@JSONField(name="deep_link_version")
		private int deepLinkVersion=1;
		
		@Generated("SparkTools")
		private Pos(Builder builder) {
			this.id = builder.id;
			this.width = builder.width;
			this.height = builder.height;
			this.supportFullScreenInterstitial = builder.supportFullScreenInterstitial;
		}
		
		@Generated("SparkTools")
		public Pos() {
		}
		/**
		 * Creates builder to build {@link Pos}.
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}
		/**
		 * Builder to build {@link Pos}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private long id;
			private int width;
			private int height;
			private boolean supportFullScreenInterstitial;

			private Builder() {
			}

			public Builder withId(long id) {
				this.id = id;
				return this;
			}

			public Builder withWidth(int width) {
				this.width = width;
				return this;
			}

			public Builder withHeight(int height) {
				this.height = height;
				return this;
			}

			public Builder withSupportFullScreenInterstitial(boolean supportFullScreenInterstitial) {
				this.supportFullScreenInterstitial = supportFullScreenInterstitial;
				return this;
			}

			public Pos build() {
				return new Pos(this);
			}
		}
	}
	
	static class Media {
		public String getAppId() {
			return appId;
		}
		public String getAppBundleId() {
			return appBundleId;
		}
		@JSONField(name="app_id")
		private String appId;
		@JSONField(name="app_bundle_id")
		private String appBundleId;
		@Generated("SparkTools")
		private Media(Builder builder) {
			this.appId = builder.appId;
			this.appBundleId = builder.appBundleId;
		}
		@Generated("SparkTools")
		public Media() {
		}
		/**
		 * Creates builder to build {@link Media}.
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}
		/**
		 * Builder to build {@link Media}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private String appId;
			private String appBundleId;

			private Builder() {
			}

			public Builder withAppId(String appId) {
				this.appId = appId;
				return this;
			}

			public Builder withAppBundleId(String appBundleId) {
				this.appBundleId = appBundleId;
				return this;
			}

			public Media build() {
				return new Media(this);
			}
		}
		
		
	}
	
	static class Device {
		public String getOs() {
			return os;
		}
		public String getOsVersion() {
			return osVersion;
		}
		public String getModel() {
			return model;
		}
		public String getManufacturer() {
			return manufacturer;
		}
		public int getDeviceType() {
			return deviceType;
		}
		public int getScreenWidth() {
			return screenWidth;
		}
		public int getScreenHeight() {
			return screenHeight;
		}
		public int getOrientation() {
			return orientation;
		}
		public String getIdfa() {
			return idfa;
		}
		public String getImei() {
			return imei;
		}
		public String getImeiMd5() {
			return imeiMd5;
		}
		public String getAndroidId() {
			return androidId;
		}
		public String getAndroidIdMd5() {
			return androidIdMd5;
		}
		private String os;
		@JSONField(name="os_version")
		private String osVersion;
		private String model;
		private String manufacturer;
		@JSONField(name="device_type")
		private int deviceType;
		@JSONField(name="screen_width")
		private int screenWidth;
		@JSONField(name="screen_height")
		private int screenHeight;
		private int orientation;
		private String idfa;
		private String imei;
		@JSONField(name="imei_md5")
		private String imeiMd5;
		@JSONField(name="android_id")
		private String androidId;
		@JSONField(name="android_id_md5")
		private String androidIdMd5;
		@Generated("SparkTools")
		private Device(Builder builder) {
			this.os = builder.os;
			this.osVersion = builder.osVersion;
			this.model = builder.model;
			this.manufacturer = builder.manufacturer;
			this.deviceType = builder.deviceType;
			this.screenWidth = builder.screenWidth;
			this.screenHeight = builder.screenHeight;
			this.orientation = builder.orientation;
			this.idfa = builder.idfa;
			this.imei = builder.imei;
			this.imeiMd5 = builder.imeiMd5;
			this.androidId = builder.androidId;
			this.androidIdMd5 = builder.androidIdMd5;
		}
		@Generated("SparkTools")
		public Device() {
		}
		/**
		 * Creates builder to build {@link Device}.
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
			private String os;
			private String osVersion;
			private String model;
			private String manufacturer;
			private int deviceType;
			private int screenWidth;
			private int screenHeight;
			private int orientation;
			private String idfa;
			private String imei;
			private String imeiMd5;
			private String androidId;
			private String androidIdMd5;

			private Builder() {
			}

			public Builder withOs(String os) {
				this.os = os;
				return this;
			}

			public Builder withOsVersion(String osVersion) {
				this.osVersion = osVersion;
				return this;
			}

			public Builder withModel(String model) {
				this.model = model;
				return this;
			}

			public Builder withManufacturer(String manufacturer) {
				this.manufacturer = manufacturer;
				return this;
			}

			public Builder withDeviceType(int deviceType) {
				this.deviceType = deviceType;
				return this;
			}

			public Builder withScreenWidth(int screenWidth) {
				this.screenWidth = screenWidth;
				return this;
			}

			public Builder withScreenHeight(int screenHeight) {
				this.screenHeight = screenHeight;
				return this;
			}

			public Builder withOrientation(int orientation) {
				this.orientation = orientation;
				return this;
			}

			public Builder withIdfa(String idfa) {
				this.idfa = idfa;
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

			public Builder withAndroidId(String androidId) {
				this.androidId = androidId;
				return this;
			}

			public Builder withAndroidIdMd5(String androidIdMd5) {
				this.androidIdMd5 = androidIdMd5;
				return this;
			}

			public Device build() {
				return new Device(this);
			}
		}
	}
	
	static class Network {
		public int getConnectType() {
			return connectType;
		}
		public int getCarrier() {
			return carrier;
		}
		@JSONField(name="connect_type")
		private int connectType;
		private int carrier;
		@Generated("SparkTools")
		private Network(Builder builder) {
			this.connectType = builder.connectType;
			this.carrier = builder.carrier;
		}
		@Generated("SparkTools")
		public Network() {
		}
		/**
		 * Creates builder to build {@link Network}.
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}
		/**
		 * Builder to build {@link Network}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private int connectType;
			private int carrier;

			private Builder() {
			}

			public Builder withConnectType(int connectType) {
				this.connectType = connectType;
				return this;
			}

			public Builder withCarrier(int carrier) {
				this.carrier = carrier;
				return this;
			}

			public Network build() {
				return new Network(this);
			}
		}
	}
	
	static class Geo {
		public int getLat() {
			return lat;
		}
		public int getLng() {
			return lng;
		}
		public Double getLocationAccuracy() {
			return locationAccuracy;
		}
		public Long getCoordTime() {
			return coordTime;
		}
		private int lat;
		private int lng;
		@JSONField(name="location_accuracy")
		private Double locationAccuracy;
		@JSONField(name="coord_time")
		private Long coordTime;
		@Generated("SparkTools")
		private Geo(Builder builder) {
			this.lat = builder.lat;
			this.lng = builder.lng;
			this.locationAccuracy = builder.locationAccuracy;
			this.coordTime = builder.coordTime;
		}
		@Generated("SparkTools")
		public Geo() {
		}
		/**
		 * Creates builder to build {@link Geo}.
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
			private int lat;
			private int lng;
			private Double locationAccuracy;
			private Long coordTime;

			private Builder() {
			}

			public Builder withLat(int lat) {
				this.lat = lat;
				return this;
			}

			public Builder withLng(int lng) {
				this.lng = lng;
				return this;
			}

			public Builder withLocationAccuracy(Double locationAccuracy) {
				this.locationAccuracy = locationAccuracy;
				return this;
			}

			public Builder withCoordTime(Long coordTime) {
				this.coordTime = coordTime;
				return this;
			}

			public Geo build() {
				return new Geo(this);
			}
		};
	}

	/**
	 * Creates builder to build {@link BidRequest}.
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
		private Pos pos;
		private Media media;
		private Device device;
		private Network network;
		private Geo geo;

		private Builder() {
		}

		public Builder withPos(Pos pos) {
			this.pos = pos;
			return this;
		}

		public Builder withMedia(Media media) {
			this.media = media;
			return this;
		}

		public Builder withDevice(Device device) {
			this.device = device;
			return this;
		}

		public Builder withNetwork(Network network) {
			this.network = network;
			return this;
		}

		public Builder withGeo(Geo geo) {
			this.geo = geo;
			return this;
		}

		public BidRequest build() {
			return new BidRequest(this);
		}
	}
}
