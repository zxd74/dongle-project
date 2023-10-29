/**
 * 
 */
package com.iwanvi.adserv.ngx.router.broker.adview;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wangweiping
 *
 */
public class BidResponse {
	private int res;

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public String getMg() {
		return mg;
	}

	public void setMg(String mg) {
		this.mg = mg;
	}

	public int getCo() {
		return co;
	}

	public void setCo(int co) {
		this.co = co;
	}

	public List<Ad> getAd() {
		return ad;
	}

	public void setAd(List<Ad> ad) {
		this.ad = ad;
	}

	private String mg;
	private int co; // 返回广告条数
	private List<Ad> ad;

	public static final class Ad {
		public String getPosId() {
			return posId;
		}

		public void setPosId(String posId) {
			this.posId = posId;
		}

		public String getAdi() {
			return adi;
		}

		public void setAdi(String adi) {
			this.adi = adi;
		}

		public Integer getAct() {
			return act;
		}

		public void setAct(Integer act) {
			this.act = act;
		}

		public int getAt() {
			return at;
		}

		public void setAt(int at) {
			this.at = at;
		}

		public String getAs() {
			return as;
		}

		public void setAs(String as) {
			this.as = as;
		}

		public String getDan() {
			return dan;
		}

		public void setDan(String dan) {
			this.dan = dan;
		}

		public String getDai() {
			return dai;
		}

		public void setDai(String dai) {
			this.dai = dai;
		}

		public String getDpn() {
			return dpn;
		}

		public void setDpn(String dpn) {
			this.dpn = dpn;
		}

		public int getDas() {
			return das;
		}

		public void setDas(int das) {
			this.das = das;
		}

		public int getAdWinPrice() {
			return adWinPrice;
		}

		public void setAdWinPrice(int adWinPrice) {
			this.adWinPrice = adWinPrice;
		}

		public String getXs() {
			return xs;
		}

		public void setXs(String xs) {
			this.xs = xs;
		}

		public Video getVideo() {
			return video;
		}

		public void setVideo(Video video) {
			this.video = video;
		}

		public List<String> getApi() {
			return api;
		}

		public void setApi(List<String> api) {
			this.api = api;
		}

		public String getAic() {
			return aic;
		}

		public void setAic(String aic) {
			this.aic = aic;
		}

		public String getAte() {
			return ate;
		}

		public void setAte(String ate) {
			this.ate = ate;
		}

		public String getAti() {
			return ati;
		}

		public void setAti(String ati) {
			this.ati = ati;
		}

		public String getAst() {
			return ast;
		}

		public void setAst(String ast) {
			this.ast = ast;
		}

		public String getAbi() {
			return abi;
		}

		public void setAbi(String abi) {
			this.abi = abi;
		}

		public String getAdLogo() {
			return adLogo;
		}

		public void setAdLogo(String adLogo) {
			this.adLogo = adLogo;
		}

		public String getAdIcon() {
			return adIcon;
		}

		public void setAdIcon(String adIcon) {
			this.adIcon = adIcon;
		}

		public Native getNativeAd() {
			return nativeAd;
		}

		public void setNativeAd(Native nativeAd) {
			this.nativeAd = nativeAd;
		}

		public List<String> getEc() {
			return ec;
		}

		public void setEc(List<String> ec) {
			this.ec = ec;
		}

		public String getAl() {
			return al;
		}

		public void setAl(String al) {
			this.al = al;
		}

		public int getAltype() {
			return altype;
		}

		public void setAltype(int altype) {
			this.altype = altype;
		}

		public String getGdt_conversion_link() {
			return gdt_conversion_link;
		}

		public void setGdt_conversion_link(String gdt_conversion_link) {
			this.gdt_conversion_link = gdt_conversion_link;
		}

		public String getDl() {
			return dl;
		}

		public void setDl(String dl) {
			this.dl = dl;
		}

		public String getFallback() {
			return fallback;
		}

		public void setFallback(String fallback) {
			this.fallback = fallback;
		}

		public List<String> getSurl() {
			return surl;
		}

		public void setSurl(List<String> surl) {
			this.surl = surl;
		}

		public List<String> getFurl() {
			return furl;
		}

		public void setFurl(List<String> furl) {
			this.furl = furl;
		}

		public List<String> getIurl() {
			return iurl;
		}

		public void setIurl(List<String> iurl) {
			this.iurl = iurl;
		}

		public List<String> getOurl() {
			return ourl;
		}

		public void setOurl(List<String> ourl) {
			this.ourl = ourl;
		}

		private String posId;
		private String adi; // 广告交易id
		private Integer act; // 广告动作类型，1-广告页；2-下载
		private int at; // 返回广告素材类型
		private String as; // 广告尺寸
		private String dan; // 被下载应用名称
		private String dai; // 被下载的应用图标
		private String dpn; // 被下载应用包名
		private int das; // 被下载应用大小
		/*
		 * 广告 CPM 价格。编码格式的 CPM 价格*10000，如价格为 CPM 价格 0.6 元， 则取值 0.6*10000=6000。在这种情况下，
		 * adWinPrice 的解码值
		 */
		private int adWinPrice;
		private String xs; // 广告物料，xs是html代码
		private Video video; // 视频广告物料内容
		private List<String> api; // 广告大图（Image）的 url 地址
		private String aic; // 广告图标url地址
		private String ate; // 广告文字内容
		private String ati; // 广告标题
		private String ast; // 广告副标题
		private String abi; // 广告行为转化图标url
		private String adLogo; // 广告来源logo
		private String adIcon; // 广告字样
		@JSONField(name = "native")
		private Native nativeAd;

		public Map<String, List<String>> getEs() {
			return es;
		}

		public void setEs(Map<String, List<String>> es) {
			this.es = es;
		}

		private List<String> ec; // 点击监控地址
		private Map<String,List<String>> es; // 曝光监控地址
		private String al; // 点击跳转地址，广告落地页
		private int altype; // 0-普通落地页面,1-广点通专用落地页
		private String gdt_conversion_link;
		private String dl; // deeplink地址
		private String fallback; // 跳转替换地址, 如果al链接不支持使用本地址
		private List<String> surl; // 下载监测地址
		private List<String> furl; // 下载完成监测地址
		private List<String> iurl; // 应用安装完成监测
		private List<String> ourl; // 打开应用监测
	}

	public static final class Video {
		private Integer xmltype;
		private String vastxml;
		private String videoUrl;
		private Integer jumpstarttime;
		private String iconurl;
		private String desc;
		private String title;
		private Integer duration;

		public Integer getXmltype() {
			return xmltype;
		}

		public void setXmltype(Integer xmltype) {
			this.xmltype = xmltype;
		}

		public String getVastxml() {
			return vastxml;
		}

		public void setVastxml(String vastxml) {
			this.vastxml = vastxml;
		}

		public String getVideoUrl() {
			return videoUrl;
		}

		public void setVideoUrl(String videoUrl) {
			this.videoUrl = videoUrl;
		}

		public Integer getJumpstarttime() {
			return jumpstarttime;
		}

		public void setJumpstarttime(Integer jumpstarttime) {
			this.jumpstarttime = jumpstarttime;
		}

		public String getIconurl() {
			return iconurl;
		}

		public void setIconurl(String iconurl) {
			this.iconurl = iconurl;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
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

		public List<String> getPlaymonurls() {
			return playmonurls;
		}

		public void setPlaymonurls(List<String> playmonurls) {
			this.playmonurls = playmonurls;
		}

		public List<String> getSptrackers() {
			return sptrackers;
		}

		public void setSptrackers(List<String> sptrackers) {
			this.sptrackers = sptrackers;
		}

		public List<String> getMptrackers() {
			return mptrackers;
		}

		public void setMptrackers(List<String> mptrackers) {
			this.mptrackers = mptrackers;
		}

		public List<String> getCptrackers() {
			return cptrackers;
		}

		public void setCptrackers(List<String> cptrackers) {
			this.cptrackers = cptrackers;
		}

		public Integer getIconstarttime() {
			return iconstarttime;
		}

		public void setIconstarttime(Integer iconstarttime) {
			this.iconstarttime = iconstarttime;
		}

		public Integer getIconcoffsettime() {
			return iconcoffsettime;
		}

		public void setIconcoffsettime(Integer iconcoffsettime) {
			this.iconcoffsettime = iconcoffsettime;
		}

		public String getIconbuttontext() {
			return iconbuttontext;
		}

		public void setIconbuttontext(String iconbuttontext) {
			this.iconbuttontext = iconbuttontext;
		}

		public Ext getExt() {
			return ext;
		}

		public void setExt(Ext ext) {
			this.ext = ext;
		}

		private Integer width;
		private Integer height;
		private List<String> playmonurls; // 视频播放监控
		private List<String> sptrackers; // 视频播放开始
		private List<String> mptrackers; // 视频播放一半监测
		private List<String> cptrackers; // 视频播放完成监测
		private Integer iconstarttime;
		private Integer iconcoffsettime;
		private String iconbuttontext;
		private Ext ext;

		static final class Ext {
			private String endhtml; // 视频播放完成加载该html片段
			private String endhtmlcharset;
			private String endimgurl;
			private String endiconurl;

			public String getEndhtml() {
				return endhtml;
			}

			public void setEndhtml(String endhtml) {
				this.endhtml = endhtml;
			}

			public String getEndhtmlcharset() {
				return endhtmlcharset;
			}

			public void setEndhtmlcharset(String endhtmlcharset) {
				this.endhtmlcharset = endhtmlcharset;
			}

			public String getEndimgurl() {
				return endimgurl;
			}

			public void setEndimgurl(String endimgurl) {
				this.endimgurl = endimgurl;
			}

			public String getEndiconurl() {
				return endiconurl;
			}

			public void setEndiconurl(String endiconurl) {
				this.endiconurl = endiconurl;
			}

			public String getEnddesc() {
				return enddesc;
			}

			public void setEnddesc(String enddesc) {
				this.enddesc = enddesc;
			}

			public String getEndtitle() {
				return endtitle;
			}

			public void setEndtitle(String endtitle) {
				this.endtitle = endtitle;
			}

			public String getEndcomments() {
				return endcomments;
			}

			public void setEndcomments(String endcomments) {
				this.endcomments = endcomments;
			}

			public String getEndbutton() {
				return endbutton;
			}

			public void setEndbutton(String endbutton) {
				this.endbutton = endbutton;
			}

			public String getEndrating() {
				return endrating;
			}

			public void setEndrating(String endrating) {
				this.endrating = endrating;
			}

			public String getEndbuttonurl() {
				return endbuttonurl;
			}

			public void setEndbuttonurl(String endbuttonurl) {
				this.endbuttonurl = endbuttonurl;
			}

			private String enddesc;
			private String endtitle;
			private String endcomments;
			private String endbutton;
			private String endrating;
			private String endbuttonurl;
		}
	}

	public static final class Native {
		public String getVer() {
			return ver;
		}

		public void setVer(String ver) {
			this.ver = ver;
		}

		public Image getIcon() {
			return icon;
		}

		public void setIcon(Image icon) {
			this.icon = icon;
		}

		public Image getLogo() {
			return logo;
		}

		public void setLogo(Image logo) {
			this.logo = logo;
		}

		public List<Image> getImages() {
			return images;
		}

		public void setImages(List<Image> images) {
			this.images = images;
		}

		public Video getVideo() {
			return video;
		}

		public void setVideo(Video video) {
			this.video = video;
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

		public String getDesc2() {
			return desc2;
		}

		public void setDesc2(String desc2) {
			this.desc2 = desc2;
		}

		private String ver;
		private Image icon;
		private Image logo;
		private List<Image> images;
		private Video video;
		private String title;
		private String desc;
		private String desc2;
	}

	public static final class Image {
		private String url;
		private int w;
		private int h;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}
	}
}
