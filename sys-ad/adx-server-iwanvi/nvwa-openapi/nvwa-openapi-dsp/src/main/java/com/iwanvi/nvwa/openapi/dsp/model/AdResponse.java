package com.iwanvi.nvwa.openapi.dsp.model;

import java.util.List;
import javax.annotation.Generated;
import java.util.Collections;

public class AdResponse {

	private String id;
	private Platform platform;
	private List<Ad> ads;

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public String getId() {
		return id;
	}

	public List<Ad> getAds() {
		return ads;
	}

	@Generated("SparkTools")
	private AdResponse(Builder builder) {
		this.id = builder.id;
		this.platform = builder.platform;
		this.ads = builder.ads;
	}

	public static class Platform {
		private String appId;
		private String posId;
		private String platformId;
		private Integer posType;
		private Integer straTime;
		private Integer straChapter;
		private Integer straPage;

		public Integer getStraTime() {
			return straTime;
		}

		public void setStraTime(Integer straTime) {
			this.straTime = straTime;
		}

		public Integer getStraChapter() {
			return straChapter;
		}

		public void setStraChapter(Integer straChapter) {
			this.straChapter = straChapter;
		}

		public Integer getStraPage() {
			return straPage;
		}

		public void setStraPage(Integer straPage) {
			this.straPage = straPage;
		}

		public int getPosType() {
			return posType;
		}

		public void setPosType(Integer posType) {
			this.posType = posType;
		}

		public String getPlatformId() {
			return platformId;
		}

		public void setPlatformId(String platformId) {
			this.platformId = platformId;
		}

		public String getAppId() {
			return appId;
		}

		public void setAppId(String appId) {
			this.appId = appId;
		}

		public String getPosId() {
			return posId;
		}

		public void setPosId(String posId) {
			this.posId = posId;
		}

		private Platform(Builder builder) {
			this.platformId = builder.platformId;
			this.appId = builder.appId;
			this.posId = builder.posId;
			this.posType = builder.posType;
			this.straTime = builder.straTime;
			this.straChapter = builder.straChapter;
			this.straPage = builder.straPage;
		}

		public static Builder builder() {
			return new Builder();
		}

		public static final class Builder {
			private String platformId;
			private String appId;
			private String posId;
			private Integer posType;
			private Integer straTime;
			private Integer straChapter;
			private Integer straPage;

			private Builder() {
			}

			public Builder straTime(Integer straTime) {
				this.straTime = straTime;
				return this;
			}

			public Builder straChapter(Integer straChapter) {
				this.straChapter = straChapter;
				return this;
			}

			public Builder straPage(Integer straPage) {
				this.straPage = straPage;
				return this;
			}

			public Builder posType(Integer posType) {
				this.posType = posType;
				return this;
			}

			public Builder platformId(String platformId) {
				this.platformId = platformId;
				return this;
			}

			public Builder appId(String appId) {
				this.appId = appId;
				return this;
			}

			public Builder posId(String posId) {
				this.posId = posId;
				return this;
			}

			public Platform build() {
				return new Platform(this);
			}
		}
	}

	public static final class Ad {
		private String impId;
		private String posId;
		private int cid;
		private String img;
		private String video;
		private String ldp;
		private List<String> pm;
		private List<String> cm;
		private Feed feed;
		private String fallback;
		private String source;
		private List<String> dlm;
		private List<String> dpsm;
		private List<String> dpfm;

		public String getImpId() {
			return impId;
		}

		public String getSource() {
			return source;
		}

		public String getPosId() {
			return posId;
		}

		public int getCid() {
			return cid;
		}

		public String getFallback() {
			return fallback;
		}

		public void setFallback(String fallback) {
			this.fallback = fallback;
		}

		public String getImg() {
			return img;
		}

		public String getVideo() {
			return video;
		}

		public List<String> getDlm() {
			return dlm;
		}

		public List<String> getDpsm() {
			return dpsm;
		}

		public List<String> getDpfm() {
			return dpfm;
		}

		public String getLdp() {
			return ldp;
		}

		public List<String> getPm() {
			return pm;
		}

		public List<String> getCm() {
			return cm;
		}

		public Feed getFeed() {
			return feed;
		}

		private Ad(Builder builder) {
			this.impId = builder.impId;
			this.posId = builder.posId;
			this.cid = builder.cid;
			this.img = builder.img;
			this.video = builder.video;
			this.ldp = builder.ldp;
			this.pm = builder.pm;
			this.cm = builder.cm;
			this.feed = builder.feed;
			this.fallback = builder.fallback;
			this.source = builder.source;
			this.dlm = builder.dlm;
			this.dpsm = builder.dpsm;
			this.dpfm = builder.dpfm;
		}

		public static Builder builder() {
			return new Builder();
		}

		public static final class Builder {
			private String impId;
			private String posId;
			private int cid;
			private String img;
			private String video;
			private String ldp;
			private List<String> pm = Collections.emptyList();
			private List<String> cm = Collections.emptyList();
			private Feed feed;
			private String fallback;
			private String source;
			private List<String> dlm = Collections.emptyList();
			private List<String> dpsm = Collections.emptyList();
			private List<String> dpfm = Collections.emptyList();

			private Builder() {
			}

			public Builder source(String source) {
				this.source = source;
				return this;
			}

			public Builder fallback(String fallback) {
				this.fallback = fallback;
				return this;
			}

			public Builder impId(String impId) {
				this.impId = impId;
				return this;
			}

			public Builder posId(String posId) {
				this.posId = posId;
				return this;
			}

			public Builder img(String img) {
				this.img = img;
				return this;
			}

			public Builder video(String video) {
				this.video = video;
				return this;
			}

			public Builder ldp(String ldp) {
				this.ldp = ldp;
				return this;
			}

			public Builder cid(int cid) {
				this.cid = cid;
				return this;
			}

			public Builder pm(List<String> pm) {
				this.pm = pm;
				return this;
			}

			public Builder cm(List<String> cm) {
				this.cm = cm;
				return this;
			}

			public Builder Feed(Feed feed) {
				this.feed = feed;
				return this;
			}

			public Builder dlm(List<String> dlm) {
				this.dlm = dlm;
				return this;
			}

			public Builder dpsm(List<String> dpsm) {
				this.dpsm = dpsm;
				return this;
			}

			public Builder dpfm(List<String> dpfm) {
				this.dpfm = dpfm;
				return this;
			}

			public Ad build() {
				return new Ad(this);
			}
		}

		public static class Feed {
			private String name;
			private String title;
			private String portrait;
			private String button;
			private String desc;
			private List<String> imgs = Collections.emptyList();
			private String tmid; // 原生广告模板id

			public String getName() {
				return name;
			}

			public String getTitle() {
				return title;
			}

			public String getPortrait() {
				return portrait;
			}

			public String getButton() {
				return button;
			}

			public String getDesc() {
				return desc;
			}

			public List<String> getImgs() {
				return imgs;
			}

			public String getTmid() {
				return tmid;
			}

			private Feed(Builder builder) {
				this.name = builder.name;
				this.title = builder.title;
				this.portrait = builder.portrait;
				this.button = builder.button;
				this.desc = builder.desc;
				this.imgs = builder.imgs;
				this.tmid = builder.tmid;
			}

			public static Builder builder() {
				return new Builder();
			}

			public static final class Builder {
				private String name;
				private String title;
				private String portrait;
				private String button;
				private String desc;
				private List<String> imgs = Collections.emptyList();
				private String tmid; // 原生广告模板id

				private Builder() {
				}

				public Builder name(String name) {
					this.name = name;
					return this;
				}

				public Builder title(String title) {
					this.title = title;
					return this;
				}

				public Builder portrait(String portrait) {
					this.portrait = portrait;
					return this;
				}

				public Builder button(String button) {
					this.button = button;
					return this;
				}

				public Builder desc(String desc) {
					this.desc = desc;
					return this;
				}

				public Builder imgs(List<String> imgs) {
					this.imgs = imgs;
					return this;
				}

				public Builder tmid(String tmid) {
					this.tmid = tmid;
					return this;
				}

				public Feed build() {
					return new Feed(this);
				}
			}
		}
	}

	/**
	 * Creates builder to build {@link AdResponse}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link AdResponse}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String id;
		private Platform platform;
		private List<Ad> ads = Collections.emptyList();

		private Builder() {
		}

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder platform(Platform platform) {
			this.platform = platform;
			return this;
		}

		public Builder ads(List<Ad> ads) {
			this.ads = ads;
			return this;
		}

		public AdResponse build() {
			return new AdResponse(this);
		}
	}
}
