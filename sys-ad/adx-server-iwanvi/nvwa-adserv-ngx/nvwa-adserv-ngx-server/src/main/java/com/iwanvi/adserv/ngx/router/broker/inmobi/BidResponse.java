/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.inmobi;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wangweiping
 *
 */
public class BidResponse {
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public List<Ad> getAds() {
		return ads;
	}

	public void setAds(List<Ad> ads) {
		this.ads = ads;
	}

	private String requestId;
	private List<Ad> ads;
	
	static final class Ad {
		public Content getContent() {
			return content;
		}

		public void setContent(Content content) {
			this.content = content;
		}

		public String getLandingPage() {
			return landingPage;
		}

		public void setLandingPage(String landingPage) {
			this.landingPage = landingPage;
		}

		public EventTracking getEventTracking() {
			return eventTracking;
		}

		public void setEventTracking(EventTracking eventTracking) {
			this.eventTracking = eventTracking;
		}

		public boolean isApp() {
			return isApp;
		}

		public void setApp(boolean isApp) {
			this.isApp = isApp;
		}

		public boolean isOpenExternal() {
			return openExternal;
		}

		public void setOpenExternal(boolean openExternal) {
			this.openExternal = openExternal;
		}

		@JSONField(name="pubContent")
		private Content content;
		private String landingPage;
		private EventTracking eventTracking;
		private boolean isApp;
		private boolean openExternal;
		
		static final class EventTracking {
			public Tracking getAdLoadedTracking() {
				return adLoadedTracking;
			}

			public void setAdLoadedTracking(Tracking adLoadedTracking) {
				this.adLoadedTracking = adLoadedTracking;
			}

			public Tracking getClkTracking() {
				return clkTracking;
			}

			public void setClkTracking(Tracking clkTracking) {
				this.clkTracking = clkTracking;
			}

			public Tracking getImpTracking() {
				return impTracking;
			}

			public void setImpTracking(Tracking impTracking) {
				this.impTracking = impTracking;
			}

			public Tracking getAdCachedTracking() {
				return adCachedTracking;
			}

			public void setAdCachedTracking(Tracking adCachedTracking) {
				this.adCachedTracking = adCachedTracking;
			}

			@JSONField(name="1")
			private Tracking adLoadedTracking;
			@JSONField(name="8")
			private Tracking clkTracking;
			@JSONField(name="18")
			private Tracking impTracking;
			@JSONField(name="120")
			private Tracking adCachedTracking;
			
			static final class Tracking {
				public List<String> getUrls() {
					return urls;
				}

				public void setUrls(List<String> urls) {
					this.urls = urls;
				}

				private List<String> urls;
			}
		}
		
		static final class Content {
			private String title;
			private String description;
			private String landingURL;
			private String cta;
			private Image icon;
			private Image screenshots;
			
			
			public String getTitle() {
				return title;
			}


			public void setTitle(String title) {
				this.title = title;
			}


			public String getDescription() {
				return description;
			}


			public void setDescription(String description) {
				this.description = description;
			}


			public String getLandingURL() {
				return landingURL;
			}


			public void setLandingURL(String landingURL) {
				this.landingURL = landingURL;
			}


			public String getCta() {
				return cta;
			}


			public void setCta(String cta) {
				this.cta = cta;
			}


			public Image getIcon() {
				return icon;
			}


			public void setIcon(Image icon) {
				this.icon = icon;
			}


			public Image getScreenshots() {
				return screenshots;
			}


			public void setScreenshots(Image screenshots) {
				this.screenshots = screenshots;
			}


			static final class Image {
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
				public String getUrl() {
					return url;
				}
				public void setUrl(String url) {
					this.url = url;
				}
				public Double getAspectRatio() {
					return aspectRatio;
				}
				public void setAspectRatio(Double aspectRatio) {
					this.aspectRatio = aspectRatio;
				}
				private Integer width;
				private Integer height;
				private String url;
				private Double aspectRatio;
			}
		}
	}
}
