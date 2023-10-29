package com.iwanvi.adserv.ngx.router.brokers.iflytek;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import javax.annotation.Generated;
import java.util.Collections;

/**
 * @author wangweiping
 *
 */
public class BidResponse {
	private Integer rc;
	private String id;
	@JSONField(name = "bid_id")
	private String bidId;
	private String info;
	private String cur;
	private List<Ad> ads;

	private Map<String, Object> ext;

	public Integer getRc() {
		return rc;
	}

	public void setRc(Integer rc) {
		this.rc = rc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public List<Ad> getAds() {
		return ads;
	}

	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}

	public Map<String, Object> getExt() {
		return ext;
	}

	public void setExt(Map<String, Object> ext) {
		this.ext = ext;
	}

	@Generated("SparkTools")
	public BidResponse() {
	}

	public static class Ad {
		@JSONField(name = "template_id")
		private Integer templateId;

		@JSONField(name = "creative_id")
		private String creativeId;
		private Double price;
		@JSONField(name = "deal_id")
		private String dealId;
		private String html;
		private Image img;
		private Image img1;
		private Image img2;
		private Image img3;

		private Video video;
		private Audio audio;
		private String title;
		private String desc;
		private Image icon;
		private String content;
		private String ctatext;
		private String app_name;
		private Integer downloads;
		private String rating;
		@JSONField(name = "app_download_url")
		private String appDownloadUrl;
		@JSONField(name = "app_intro_url")
		private String appIntroUrl;
		@JSONField(name = "app_ver")
		private String appVer;
		@JSONField(name = "app_size")
		private Double appSize;
		private String brand;

		private Monitor monitor;
		private String deeplink;
		@JSONField(name = "voice_ad_url")
		private String voiceAdUrl;
		private String landing;
		@JSONField(name = "action_type")
		private Integer actionType;
		@JSONField(name = "ad_source_mark")
		private String adSourceMark;

		private Map<String, Object> ext;

		@Generated("SparkTools")
		private Ad(Builder builder) {
			this.templateId = builder.templateId;
			this.creativeId = builder.creativeId;
			this.price = builder.price;
			this.dealId = builder.dealId;
			this.html = builder.html;
			this.img = builder.img;
			this.img1 = builder.img1;
			this.img2 = builder.img2;
			this.img3 = builder.img3;
			this.video = builder.video;
			this.audio = builder.audio;
			this.title = builder.title;
			this.desc = builder.desc;
			this.icon = builder.icon;
			this.content = builder.content;
			this.ctatext = builder.ctatext;
			this.app_name = builder.app_name;
			this.downloads = builder.downloads;
			this.rating = builder.rating;
			this.appDownloadUrl = builder.appDownloadUrl;
			this.appIntroUrl = builder.appIntroUrl;
			this.appVer = builder.appVer;
			this.appSize = builder.appSize;
			this.brand = builder.brand;
			this.monitor = builder.monitor;
			this.deeplink = builder.deeplink;
			this.voiceAdUrl = builder.voiceAdUrl;
			this.landing = builder.landing;
			this.actionType = builder.actionType;
			this.adSourceMark = builder.adSourceMark;
			this.ext = builder.ext;
		}

		public Integer getTemplateId() {
			return templateId;
		}

		public void setTemplateId(Integer templateId) {
			this.templateId = templateId;
		}

		public String getCreativeId() {
			return creativeId;
		}

		public void setCreativeId(String creativeId) {
			this.creativeId = creativeId;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public String getDealId() {
			return dealId;
		}

		public void setDealId(String dealId) {
			this.dealId = dealId;
		}

		public String getHtml() {
			return html;
		}

		public void setHtml(String html) {
			this.html = html;
		}

		public Image getImg() {
			return img;
		}

		public void setImg(Image img) {
			this.img = img;
		}

		public Image getImg1() {
			return img1;
		}

		public void setImg1(Image img1) {
			this.img1 = img1;
		}

		public Image getImg2() {
			return img2;
		}

		public void setImg2(Image img2) {
			this.img2 = img2;
		}

		public Image getImg3() {
			return img3;
		}

		public void setImg3(Image img3) {
			this.img3 = img3;
		}

		public Video getVideo() {
			return video;
		}

		public void setVideo(Video video) {
			this.video = video;
		}

		public Audio getAudio() {
			return audio;
		}

		public void setAudio(Audio audio) {
			this.audio = audio;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public Image getIcon() {
			return icon;
		}

		public void setIcon(Image icon) {
			this.icon = icon;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getCtatext() {
			return ctatext;
		}

		public void setCtatext(String ctatext) {
			this.ctatext = ctatext;
		}

		public String getApp_name() {
			return app_name;
		}

		public void setApp_name(String app_name) {
			this.app_name = app_name;
		}

		public Integer getDownloads() {
			return downloads;
		}

		public void setDownloads(Integer downloads) {
			this.downloads = downloads;
		}

		public String getRating() {
			return rating;
		}

		public void setRating(String rating) {
			this.rating = rating;
		}

		public String getAppDownloadUrl() {
			return appDownloadUrl;
		}

		public void setAppDownloadUrl(String appDownloadUrl) {
			this.appDownloadUrl = appDownloadUrl;
		}

		public String getAppIntroUrl() {
			return appIntroUrl;
		}

		public void setAppIntroUrl(String appIntroUrl) {
			this.appIntroUrl = appIntroUrl;
		}

		public String getAppVer() {
			return appVer;
		}

		public void setAppVer(String appVer) {
			this.appVer = appVer;
		}

		public Double getAppSize() {
			return appSize;
		}

		public void setAppSize(Double appSize) {
			this.appSize = appSize;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}

		public Monitor getMonitor() {
			return monitor;
		}

		public void setMonitor(Monitor monitor) {
			this.monitor = monitor;
		}

		public String getDeeplink() {
			return deeplink;
		}

		public void setDeeplink(String deeplink) {
			this.deeplink = deeplink;
		}

		public String getVoiceAdUrl() {
			return voiceAdUrl;
		}

		public void setVoiceAdUrl(String voiceAdUrl) {
			this.voiceAdUrl = voiceAdUrl;
		}

		public String getLanding() {
			return landing;
		}

		public void setLanding(String landing) {
			this.landing = landing;
		}

		public Integer getActionType() {
			return actionType;
		}

		public void setActionType(Integer actionType) {
			this.actionType = actionType;
		}

		public String getAdSourceMark() {
			return adSourceMark;
		}

		public void setAdSourceMark(String adSourceMark) {
			this.adSourceMark = adSourceMark;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		@Generated("SparkTools")
		public Ad() {
		}

		/**
		 * Creates builder to build {@link Ad}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Ad}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private Integer templateId;
			private String creativeId;
			private Double price;
			private String dealId;
			private String html;
			private Image img;
			private Image img1;
			private Image img2;
			private Image img3;
			private Video video;
			private Audio audio;
			private String title;
			private String desc;
			private Image icon;
			private String content;
			private String ctatext;
			private String app_name;
			private Integer downloads;
			private String rating;
			private String appDownloadUrl;
			private String appIntroUrl;
			private String appVer;
			private Double appSize;
			private String brand;
			private Monitor monitor;
			private String deeplink;
			private String voiceAdUrl;
			private String landing;
			private Integer actionType;
			private String adSourceMark;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withTemplateId(Integer templateId) {
				this.templateId = templateId;
				return this;
			}

			public Builder withCreativeId(String creativeId) {
				this.creativeId = creativeId;
				return this;
			}

			public Builder withPrice(Double price) {
				this.price = price;
				return this;
			}

			public Builder withDealId(String dealId) {
				this.dealId = dealId;
				return this;
			}

			public Builder withHtml(String html) {
				this.html = html;
				return this;
			}

			public Builder withImg(Image img) {
				this.img = img;
				return this;
			}

			public Builder withImg1(Image img1) {
				this.img1 = img1;
				return this;
			}

			public Builder withImg2(Image img2) {
				this.img2 = img2;
				return this;
			}

			public Builder withImg3(Image img3) {
				this.img3 = img3;
				return this;
			}

			public Builder withVideo(Video video) {
				this.video = video;
				return this;
			}

			public Builder withAudio(Audio audio) {
				this.audio = audio;
				return this;
			}

			public Builder withTitle(String title) {
				this.title = title;
				return this;
			}

			public Builder withDesc(String desc) {
				this.desc = desc;
				return this;
			}

			public Builder withIcon(Image icon) {
				this.icon = icon;
				return this;
			}

			public Builder withContent(String content) {
				this.content = content;
				return this;
			}

			public Builder withCtatext(String ctatext) {
				this.ctatext = ctatext;
				return this;
			}

			public Builder withApp_name(String app_name) {
				this.app_name = app_name;
				return this;
			}

			public Builder withDownloads(Integer downloads) {
				this.downloads = downloads;
				return this;
			}

			public Builder withRating(String rating) {
				this.rating = rating;
				return this;
			}

			public Builder withAppDownloadUrl(String appDownloadUrl) {
				this.appDownloadUrl = appDownloadUrl;
				return this;
			}

			public Builder withAppIntroUrl(String appIntroUrl) {
				this.appIntroUrl = appIntroUrl;
				return this;
			}

			public Builder withAppVer(String appVer) {
				this.appVer = appVer;
				return this;
			}

			public Builder withAppSize(Double appSize) {
				this.appSize = appSize;
				return this;
			}

			public Builder withBrand(String brand) {
				this.brand = brand;
				return this;
			}

			public Builder withMonitor(Monitor monitor) {
				this.monitor = monitor;
				return this;
			}

			public Builder withDeeplink(String deeplink) {
				this.deeplink = deeplink;
				return this;
			}

			public Builder withVoiceAdUrl(String voiceAdUrl) {
				this.voiceAdUrl = voiceAdUrl;
				return this;
			}

			public Builder withLanding(String landing) {
				this.landing = landing;
				return this;
			}

			public Builder withActionType(Integer actionType) {
				this.actionType = actionType;
				return this;
			}

			public Builder withAdSourceMark(String adSourceMark) {
				this.adSourceMark = adSourceMark;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Ad build() {
				return new Ad(this);
			}
		}

	}

	public static class Video {
		private String url;
		private Integer duration;
		private Integer width;
		private Integer height;
		private Integer bitrate;
		private Integer format;
		@JSONField(name = "end_time")
		private Long endTime;
		private Map<String, Object> ext;

		@Generated("SparkTools")
		private Video(Builder builder) {
			this.url = builder.url;
			this.duration = builder.duration;
			this.width = builder.width;
			this.height = builder.height;
			this.bitrate = builder.bitrate;
			this.format = builder.format;
			this.endTime = builder.endTime;
			this.ext = builder.ext;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getDuration() {
			return duration;
		}

		public void setDuration(Integer duration) {
			this.duration = duration;
		}

		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		public Integer getBitrate() {
			return bitrate;
		}

		public void setBitrate(Integer bitrate) {
			this.bitrate = bitrate;
		}

		public Integer getFormat() {
			return format;
		}

		public void setFormat(Integer format) {
			this.format = format;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		@Generated("SparkTools")
		public Video() {
		}

		/**
		 * Creates builder to build {@link Video}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Video}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private String url;
			private Integer duration;
			private Integer width;
			private Integer height;
			private Integer bitrate;
			private Integer format;
			private Long endTime;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withUrl(String url) {
				this.url = url;
				return this;
			}

			public Builder withDuration(Integer duration) {
				this.duration = duration;
				return this;
			}

			public Builder withWidth(Integer width) {
				this.width = width;
				return this;
			}

			public Builder withHeight(Integer height) {
				this.height = height;
				return this;
			}

			public Builder withBitrate(Integer bitrate) {
				this.bitrate = bitrate;
				return this;
			}

			public Builder withFormat(Integer format) {
				this.format = format;
				return this;
			}

			public Builder withEndTime(Long endTime) {
				this.endTime = endTime;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Video build() {
				return new Video(this);
			}
		}

	}

	public static class Audio {
		private String url;
		private Integer duration;
		private Integer bitrate;
		private Integer format;
		@JSONField(name = "end_time")
		private Long endTime;
		private Map<String, Object> ext;

		@Generated("SparkTools")
		private Audio(Builder builder) {
			this.url = builder.url;
			this.duration = builder.duration;
			this.bitrate = builder.bitrate;
			this.format = builder.format;
			this.endTime = builder.endTime;
			this.ext = builder.ext;
		}

		@Generated("SparkTools")
		public Audio() {
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getDuration() {
			return duration;
		}

		public void setDuration(Integer duration) {
			this.duration = duration;
		}

		public Integer getBitrate() {
			return bitrate;
		}

		public void setBitrate(Integer bitrate) {
			this.bitrate = bitrate;
		}

		public Integer getFormat() {
			return format;
		}

		public void setFormat(Integer format) {
			this.format = format;
		}

		public Long getEndTime() {
			return endTime;
		}

		public void setEndTime(Long endTime) {
			this.endTime = endTime;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		/**
		 * Creates builder to build {@link Audio}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Audio}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private String url;
			private Integer duration;
			private Integer bitrate;
			private Integer format;
			private Long endTime;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withUrl(String url) {
				this.url = url;
				return this;
			}

			public Builder withDuration(Integer duration) {
				this.duration = duration;
				return this;
			}

			public Builder withBitrate(Integer bitrate) {
				this.bitrate = bitrate;
				return this;
			}

			public Builder withFormat(Integer format) {
				this.format = format;
				return this;
			}

			public Builder withEndTime(Long endTime) {
				this.endTime = endTime;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Audio build() {
				return new Audio(this);
			}
		}
	}

	public static class Monitor {
		@JSONField(name = "impress_urls")
		private List<String> impressUrls;
		@JSONField(name = "click_urls")
		private List<String> clickUrls;
		@JSONField(name = "download_start_urls")
		private List<String> downloadStartUrls;
		@JSONField(name = "download_complete_urls")
		private List<String> downloadCompleteUrls;
		@JSONField(name = "install_start_urls")
		private List<String> installStartUrls;
		@JSONField(name = "install_complete_urls")
		private List<String> installCompleteUrls;
		@JSONField(name = "start_urls")
		private List<String> startUrls;
		@JSONField(name = "first_quartile_urls")
		private List<String> firstQuartileUrls;
		@JSONField(name = "mid_point_urls")
		private List<String> midPointUrls;
		@JSONField(name = "third_quartile_urls")
		private List<String> thirdQuartileUrls;
		@JSONField(name = "complete_urls")
		private List<String> completeUrls;
		@JSONField(name = "pause_urls")
		private List<String> pauseUrls;
		@JSONField(name = "resume_urls")
		private List<String> resumeUrls;
		@JSONField(name = "skip_urls")
		private List<String> skipUrls;
		@JSONField(name = "mute_urls")
		private List<String> muteUrls;
		@JSONField(name = "unmute_urls")
		private List<String> unmuteUrls;
		@JSONField(name = "replay_urls")
		private List<String> replayUrls;
		@JSONField(name = "close_linear_urls")
		private List<String> closeLinearUrls;
		@JSONField(name = "fullscreen_urls")
		private List<String> fullscreenUrls;
		@JSONField(name = "exit_fullscreen_urls")
		private List<String> exitFullscreenUrls;
		@JSONField(name = "up_scroll_urls")
		private List<String> upScrollUrls;
		@JSONField(name = "down_scroll_urls")
		private List<String> downScrollUrls;
		private Map<String, Object> ext;

		public List<String> getImpressUrls() {
			return impressUrls;
		}

		public void setImpressUrls(List<String> impressUrls) {
			this.impressUrls = impressUrls;
		}

		public List<String> getClickUrls() {
			return clickUrls;
		}

		public void setClickUrls(List<String> clickUrls) {
			this.clickUrls = clickUrls;
		}

		public List<String> getDownloadStartUrls() {
			return downloadStartUrls;
		}

		public void setDownloadStartUrls(List<String> downloadStartUrls) {
			this.downloadStartUrls = downloadStartUrls;
		}

		public List<String> getDownloadCompleteUrls() {
			return downloadCompleteUrls;
		}

		public void setDownloadCompleteUrls(List<String> downloadCompleteUrls) {
			this.downloadCompleteUrls = downloadCompleteUrls;
		}

		public List<String> getInstallStartUrls() {
			return installStartUrls;
		}

		public void setInstallStartUrls(List<String> installStartUrls) {
			this.installStartUrls = installStartUrls;
		}

		public List<String> getInstallCompleteUrls() {
			return installCompleteUrls;
		}

		public void setInstallCompleteUrls(List<String> installCompleteUrls) {
			this.installCompleteUrls = installCompleteUrls;
		}

		public List<String> getStartUrls() {
			return startUrls;
		}

		public void setStartUrls(List<String> startUrls) {
			this.startUrls = startUrls;
		}

		public List<String> getFirstQuartileUrls() {
			return firstQuartileUrls;
		}

		public void setFirstQuartileUrls(List<String> firstQuartileUrls) {
			this.firstQuartileUrls = firstQuartileUrls;
		}

		public List<String> getMidPointUrls() {
			return midPointUrls;
		}

		public void setMidPointUrls(List<String> midPointUrls) {
			this.midPointUrls = midPointUrls;
		}

		public List<String> getThirdQuartileUrls() {
			return thirdQuartileUrls;
		}

		public void setThirdQuartileUrls(List<String> thirdQuartileUrls) {
			this.thirdQuartileUrls = thirdQuartileUrls;
		}

		public List<String> getCompleteUrls() {
			return completeUrls;
		}

		public void setCompleteUrls(List<String> completeUrls) {
			this.completeUrls = completeUrls;
		}

		public List<String> getPauseUrls() {
			return pauseUrls;
		}

		public void setPauseUrls(List<String> pauseUrls) {
			this.pauseUrls = pauseUrls;
		}

		public List<String> getResumeUrls() {
			return resumeUrls;
		}

		public void setResumeUrls(List<String> resumeUrls) {
			this.resumeUrls = resumeUrls;
		}

		public List<String> getSkipUrls() {
			return skipUrls;
		}

		public void setSkipUrls(List<String> skipUrls) {
			this.skipUrls = skipUrls;
		}

		public List<String> getMuteUrls() {
			return muteUrls;
		}

		public void setMuteUrls(List<String> muteUrls) {
			this.muteUrls = muteUrls;
		}

		public List<String> getUnmuteUrls() {
			return unmuteUrls;
		}

		public void setUnmuteUrls(List<String> unmuteUrls) {
			this.unmuteUrls = unmuteUrls;
		}

		public List<String> getReplayUrls() {
			return replayUrls;
		}

		public void setReplayUrls(List<String> replayUrls) {
			this.replayUrls = replayUrls;
		}

		public List<String> getCloseLinearUrls() {
			return closeLinearUrls;
		}

		public void setCloseLinearUrls(List<String> closeLinearUrls) {
			this.closeLinearUrls = closeLinearUrls;
		}

		public List<String> getFullscreenUrls() {
			return fullscreenUrls;
		}

		public void setFullscreenUrls(List<String> fullscreenUrls) {
			this.fullscreenUrls = fullscreenUrls;
		}

		public List<String> getExitFullscreenUrls() {
			return exitFullscreenUrls;
		}

		public void setExitFullscreenUrls(List<String> exitFullscreenUrls) {
			this.exitFullscreenUrls = exitFullscreenUrls;
		}

		public List<String> getUpScrollUrls() {
			return upScrollUrls;
		}

		public void setUpScrollUrls(List<String> upScrollUrls) {
			this.upScrollUrls = upScrollUrls;
		}

		public List<String> getDownScrollUrls() {
			return downScrollUrls;
		}

		public void setDownScrollUrls(List<String> downScrollUrls) {
			this.downScrollUrls = downScrollUrls;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		@Generated("SparkTools")
		private Monitor(Builder builder) {
			this.impressUrls = builder.impressUrls;
			this.clickUrls = builder.clickUrls;
			this.downloadStartUrls = builder.downloadStartUrls;
			this.downloadCompleteUrls = builder.downloadCompleteUrls;
			this.installStartUrls = builder.installStartUrls;
			this.installCompleteUrls = builder.installCompleteUrls;
			this.startUrls = builder.startUrls;
			this.firstQuartileUrls = builder.firstQuartileUrls;
			this.midPointUrls = builder.midPointUrls;
			this.thirdQuartileUrls = builder.thirdQuartileUrls;
			this.completeUrls = builder.completeUrls;
			this.pauseUrls = builder.pauseUrls;
			this.resumeUrls = builder.resumeUrls;
			this.skipUrls = builder.skipUrls;
			this.muteUrls = builder.muteUrls;
			this.unmuteUrls = builder.unmuteUrls;
			this.replayUrls = builder.replayUrls;
			this.closeLinearUrls = builder.closeLinearUrls;
			this.fullscreenUrls = builder.fullscreenUrls;
			this.exitFullscreenUrls = builder.exitFullscreenUrls;
			this.upScrollUrls = builder.upScrollUrls;
			this.downScrollUrls = builder.downScrollUrls;
			this.ext = builder.ext;
		}

		@Generated("SparkTools")
		public Monitor() {
		}

		/**
		 * Creates builder to build {@link Monitor}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Monitor}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private List<String> impressUrls = Collections.emptyList();
			private List<String> clickUrls = Collections.emptyList();
			private List<String> downloadStartUrls = Collections.emptyList();
			private List<String> downloadCompleteUrls = Collections.emptyList();
			private List<String> installStartUrls = Collections.emptyList();
			private List<String> installCompleteUrls = Collections.emptyList();
			private List<String> startUrls = Collections.emptyList();
			private List<String> firstQuartileUrls = Collections.emptyList();
			private List<String> midPointUrls = Collections.emptyList();
			private List<String> thirdQuartileUrls = Collections.emptyList();
			private List<String> completeUrls = Collections.emptyList();
			private List<String> pauseUrls = Collections.emptyList();
			private List<String> resumeUrls = Collections.emptyList();
			private List<String> skipUrls = Collections.emptyList();
			private List<String> muteUrls = Collections.emptyList();
			private List<String> unmuteUrls = Collections.emptyList();
			private List<String> replayUrls = Collections.emptyList();
			private List<String> closeLinearUrls = Collections.emptyList();
			private List<String> fullscreenUrls = Collections.emptyList();
			private List<String> exitFullscreenUrls = Collections.emptyList();
			private List<String> upScrollUrls = Collections.emptyList();
			private List<String> downScrollUrls = Collections.emptyList();
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withImpressUrls(List<String> impressUrls) {
				this.impressUrls = impressUrls;
				return this;
			}

			public Builder withClickUrls(List<String> clickUrls) {
				this.clickUrls = clickUrls;
				return this;
			}

			public Builder withDownloadStartUrls(List<String> downloadStartUrls) {
				this.downloadStartUrls = downloadStartUrls;
				return this;
			}

			public Builder withDownloadCompleteUrls(List<String> downloadCompleteUrls) {
				this.downloadCompleteUrls = downloadCompleteUrls;
				return this;
			}

			public Builder withInstallStartUrls(List<String> installStartUrls) {
				this.installStartUrls = installStartUrls;
				return this;
			}

			public Builder withInstallCompleteUrls(List<String> installCompleteUrls) {
				this.installCompleteUrls = installCompleteUrls;
				return this;
			}

			public Builder withStartUrls(List<String> startUrls) {
				this.startUrls = startUrls;
				return this;
			}

			public Builder withFirstQuartileUrls(List<String> firstQuartileUrls) {
				this.firstQuartileUrls = firstQuartileUrls;
				return this;
			}

			public Builder withMidPointUrls(List<String> midPointUrls) {
				this.midPointUrls = midPointUrls;
				return this;
			}

			public Builder withThirdQuartileUrls(List<String> thirdQuartileUrls) {
				this.thirdQuartileUrls = thirdQuartileUrls;
				return this;
			}

			public Builder withCompleteUrls(List<String> completeUrls) {
				this.completeUrls = completeUrls;
				return this;
			}

			public Builder withPauseUrls(List<String> pauseUrls) {
				this.pauseUrls = pauseUrls;
				return this;
			}

			public Builder withResumeUrls(List<String> resumeUrls) {
				this.resumeUrls = resumeUrls;
				return this;
			}

			public Builder withSkipUrls(List<String> skipUrls) {
				this.skipUrls = skipUrls;
				return this;
			}

			public Builder withMuteUrls(List<String> muteUrls) {
				this.muteUrls = muteUrls;
				return this;
			}

			public Builder withUnmuteUrls(List<String> unmuteUrls) {
				this.unmuteUrls = unmuteUrls;
				return this;
			}

			public Builder withReplayUrls(List<String> replayUrls) {
				this.replayUrls = replayUrls;
				return this;
			}

			public Builder withCloseLinearUrls(List<String> closeLinearUrls) {
				this.closeLinearUrls = closeLinearUrls;
				return this;
			}

			public Builder withFullscreenUrls(List<String> fullscreenUrls) {
				this.fullscreenUrls = fullscreenUrls;
				return this;
			}

			public Builder withExitFullscreenUrls(List<String> exitFullscreenUrls) {
				this.exitFullscreenUrls = exitFullscreenUrls;
				return this;
			}

			public Builder withUpScrollUrls(List<String> upScrollUrls) {
				this.upScrollUrls = upScrollUrls;
				return this;
			}

			public Builder withDownScrollUrls(List<String> downScrollUrls) {
				this.downScrollUrls = downScrollUrls;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Monitor build() {
				return new Monitor(this);
			}
		}

	}

	public static class Image {
		private String url;
		private Integer width;
		private Integer height;
		private Map<String, Object> ext;

		@Generated("SparkTools")
		private Image(Builder builder) {
			this.url = builder.url;
			this.width = builder.width;
			this.height = builder.height;
			this.ext = builder.ext;
		}

		@Generated("SparkTools")
		public Image() {
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getWidth() {
			return width;
		}

		public void setWidth(Integer width) {
			this.width = width;
		}

		public Integer getHeight() {
			return height;
		}

		public void setHeight(Integer height) {
			this.height = height;
		}

		public Map<String, Object> getExt() {
			return ext;
		}

		public void setExt(Map<String, Object> ext) {
			this.ext = ext;
		}

		/**
		 * Creates builder to build {@link Image}.
		 * 
		 * @return created builder
		 */
		@Generated("SparkTools")
		public static Builder builder() {
			return new Builder();
		}

		/**
		 * Builder to build {@link Image}.
		 */
		@Generated("SparkTools")
		public static final class Builder {
			private String url;
			private Integer width;
			private Integer height;
			private Map<String, Object> ext = Collections.emptyMap();

			private Builder() {
			}

			public Builder withUrl(String url) {
				this.url = url;
				return this;
			}

			public Builder withWidth(Integer width) {
				this.width = width;
				return this;
			}

			public Builder withHeight(Integer height) {
				this.height = height;
				return this;
			}

			public Builder withExt(Map<String, Object> ext) {
				this.ext = ext;
				return this;
			}

			public Image build() {
				return new Image(this);
			}
		}
	}

}
