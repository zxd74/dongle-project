package com.iwanvi.nvwa.openapi.dsp.model;

import java.util.List;
import java.util.Map;

public class SyncAdPosition {

	private String posId;
	private Integer creativeType;
	private int width;
	private int height;
	private int duration;
	private List<Map<String, Object>> mapping;
	private Integer creativeStyle;
	
	public List<Map<String, Object>> getMapping() {
		return mapping;
	}
	public void setMapping(List<Map<String, Object>> mapping) {
		this.mapping = mapping;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Integer getCreativeStyle() {
		return creativeStyle;
	}
	public void setCreativeStyle(Integer creativeStyle) {
		this.creativeStyle = creativeStyle;
	}
	public void setCreativeType(Integer creativeType) {
		this.creativeType = creativeType;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public Integer getCreativeType() {
		return creativeType;
	}
	public void setCreativeType(int creativeType) {
		this.creativeType = creativeType;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
