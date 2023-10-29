/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.adserv.ngx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iwanvi.adserv.ngx.util.CollectionUtils;
import com.iwanvi.adserv.ngx.util.Constants;

/**
 * 
 * @author wangwp
 */
public class XcarDmpTag {
	private XcarDmpTag() {
	}

	private List<String> brandInfo;
	private List<String> levelInfo;
	private List<String> seriesInfo;
	private List<String> priceTagInfo;

	public List<String> getBrandInfo() {
		return brandInfo;
	}

	public void setBrandInfo(List<String> brandInfo) {
		this.brandInfo = brandInfo;
	}

	public List<String> getLevelInfo() {
		return levelInfo;
	}

	public void setLevelInfo(List<String> levelInfo) {
		this.levelInfo = levelInfo;
	}

	public List<String> getSeriesInfo() {
		return seriesInfo;
	}

	public void setSeriesInfo(List<String> seriesInfo) {
		this.seriesInfo = seriesInfo;
	}

	public List<String> getPriceTagInfo() {
		return priceTagInfo;
	}

	public void setPriceTagInfo(List<String> priceTagInfo) {
		this.priceTagInfo = priceTagInfo;
	}

	public boolean containsLevelInfo(List<String> levelInfo) {
		return CollectionUtils.containsAny(this.levelInfo, levelInfo);
	}

	public boolean containsPriceTagInfo(List<String> priceTagInfo) {
		return CollectionUtils.containsAny(this.priceTagInfo, priceTagInfo);
	}

	public boolean containsBrandInfo(List<String> brandInfo) {
		return CollectionUtils.containsAny(this.brandInfo, brandInfo);
	}

	public boolean containsSeriesInfo(List<String> seriesInfo) {
		return CollectionUtils.containsAny(this.seriesInfo, seriesInfo);
	}

	public static XcarDmpTag toTag(String str) {
		JSONObject data = JSON.parseObject(str);

		XcarDmpTag tag = new XcarDmpTag();

		JSONObject brandInfo = data.getJSONObject(Constants.XCAR_DMP_TAG_NAME_BRAND);
		JSONObject levelInfo = data.getJSONObject(Constants.XCAR_DMP_TAG_NAME_LEVEL);
		JSONObject seriesInfo = data.getJSONObject(Constants.XCAR_DMP_TAG_NAME_SERIES);
		JSONObject priceTagInfo = data.getJSONObject(Constants.XCAR_DMP_TAG_NAME_PRICE_TAG);

		if (brandInfo != null) {
			tag.brandInfo = toTagList(brandInfo.keySet());
		}

		if (levelInfo != null) {
			tag.levelInfo = toTagList(levelInfo.keySet());
		}

		if (seriesInfo != null) {
			tag.seriesInfo = toTagList(seriesInfo.keySet());
		}

		if (priceTagInfo != null) {
			tag.priceTagInfo = toTagList(priceTagInfo.keySet());
		}
		return tag;
	}

	private static List<String> toTagList(Set<String> tagSet) {
		List<String> tags = new ArrayList<>();
		tags.addAll(tagSet);
		return tags;
	}
}
