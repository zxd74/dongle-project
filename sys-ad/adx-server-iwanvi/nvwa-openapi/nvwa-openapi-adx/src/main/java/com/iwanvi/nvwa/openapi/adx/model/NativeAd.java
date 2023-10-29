/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author wangwp
 */
public class NativeAd {
	private String name; //广告名称
	@NotBlank(message = "原生广告title不能为空")
	private String title;
	@NotBlank(message="原生广告icon不能为空")
	private String icon;
	private String desc;
	private String ctatext;
	
	//private String mainImage;
	@NotNull(message="原生广告图片不能为空")
	private String[] images;

	public String getTitle() {
		return title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCtatext() {
		return ctatext;
	}

	public void setCtatext(String ctatext) {
		this.ctatext = ctatext;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}
}
