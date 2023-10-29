package com.iwanvi.adserv.ngx.router.broker.gdt;

import java.util.List;
import java.util.Map;

//广点通竞价响应
public class BidResponse {
	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Ads> getData() {
		return data;
	}

	public void setData(Map<String, Ads> data) {
		this.data = data;
	}

	private int ret;
	private String msg;
	private Map<String, Ads> data;

	static class Ads {
		public List<Ad> getList() {
			return list;
		}

		public void setList(List<Ad> list) {
			this.list = list;
		}

		private List<Ad> list;
	}

	static class Ad {
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getAd_id() {
			return ad_id;
		}
		public void setAd_id(String ad_id) {
			this.ad_id = ad_id;
		}
		public String getImpression_link() {
			return impression_link;
		}
		public void setImpression_link(String impression_link) {
			this.impression_link = impression_link;
		}
		public String getVideo_view_link() {
			return video_view_link;
		}
		public void setVideo_view_link(String video_view_link) {
			this.video_view_link = video_view_link;
		}
		public String getClick_link() {
			return click_link;
		}
		public void setClick_link(String click_link) {
			this.click_link = click_link;
		}
		public int getInteract_type() {
			return interact_type;
		}
		public void setInteract_type(int interact_type) {
			this.interact_type = interact_type;
		}
		public String getConversion_link() {
			return conversion_link;
		}
		public void setConversion_link(String conversion_link) {
			this.conversion_link = conversion_link;
		}
		public boolean isIs_full_screen_interstitial() {
			return is_full_screen_interstitial;
		}
		public void setIs_full_screen_interstitial(boolean is_full_screen_interstitial) {
			this.is_full_screen_interstitial = is_full_screen_interstitial;
		}
		public String getHtml_snippet() {
			return html_snippet;
		}
		public void setHtml_snippet(String html_snippet) {
			this.html_snippet = html_snippet;
		}
		public Integer getCrt_type() {
			return crt_type;
		}
		public void setCrt_type(Integer crt_type) {
			this.crt_type = crt_type;
		}
		public String getImg_url() {
			return img_url;
		}
		public void setImg_url(String img_url) {
			this.img_url = img_url;
		}
		public String getImg2_url() {
			return img2_url;
		}
		public void setImg2_url(String img2_url) {
			this.img2_url = img2_url;
		}
		public String getImg3_url() {
			return img3_url;
		}
		public void setImg3_url(String img3_url) {
			this.img3_url = img3_url;
		}
		public List<String> getImg_list() {
			return img_list;
		}
		public void setImg_list(List<String> img_list) {
			this.img_list = img_list;
		}
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
		public List<String> getSnapshot_url() {
			return snapshot_url;
		}
		public void setSnapshot_url(List<String> snapshot_url) {
			this.snapshot_url = snapshot_url;
		}
		public String getVideo_url() {
			return video_url;
		}
		public void setVideo_url(String video_url) {
			this.video_url = video_url;
		}
		public String getPackage_name() {
			return package_name;
		}
		public void setPackage_name(String package_name) {
			this.package_name = package_name;
		}
		public int getVideo_duration() {
			return video_duration;
		}
		public void setVideo_duration(int video_duration) {
			this.video_duration = video_duration;
		}
		public int getVideo_file_size() {
			return video_file_size;
		}
		public void setVideo_file_size(int video_file_size) {
			this.video_file_size = video_file_size;
		}
		public String getRelation_target() {
			return relation_target;
		}
		public void setRelation_target(String relation_target) {
			this.relation_target = relation_target;
		}
		public String getCustomized_invoke_url() {
			return customized_invoke_url;
		}
		public void setCustomized_invoke_url(String customized_invoke_url) {
			this.customized_invoke_url = customized_invoke_url;
		}
		public int getEcpm() {
			return ecpm;
		}
		public void setEcpm(int ecpm) {
			this.ecpm = ecpm;
		}
		private String type;
		private String ad_id; // 广告id
		private String impression_link;
		private String video_view_link; // 视频播放信息上报链接
		private String click_link; // 点击上报链接
		private int interact_type; // 交互类型, 0-打开网页, 1-app下载
		private String conversion_link; // 转化上报
		private boolean is_full_screen_interstitial;
		private String html_snippet;
		/**
		 * 可能取值： 1 – 文字 2 – 图片 7 – 图文（1 图 2 文或 3 等比例小图 2 文） 11 – 图文（2 图 2 文） 20 – 视频（1
		 * 横版视频 2 图 2 文或 1 竖版视频 3 图 2 文）
		 */
		private Integer crt_type;
		private String img_url;
		private String img2_url; //icon 
		private String img3_url; //广告图片url
		private List<String> img_list;
		private String title;
		private String description;
		private List<String> snapshot_url;
		private String video_url;
		private String package_name;
		private int video_duration;
		private int video_file_size;
		private String relation_target;
		private String customized_invoke_url;
		private int ecpm;
		
	}
}
