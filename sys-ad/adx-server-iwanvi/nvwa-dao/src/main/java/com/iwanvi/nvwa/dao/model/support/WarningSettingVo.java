package com.iwanvi.nvwa.dao.model.support;

public class WarningSettingVo {
	
	private Integer id;

	private String channelId;

	private String appId;

	private double pvTb = 0;

	private double pvHb = 0;

	private double clkTb = 0;

	private double clkHb = 0;

	private Integer pvTj = 0;

	private Integer clkTj = 0;

	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public double getPvTb() {
		return pvTb;
	}

	public void setPvTb(double pvTb) {
		this.pvTb = pvTb;
	}

	public double getPvHb() {
		return pvHb;
	}

	public void setPvHb(double pvHb) {
		this.pvHb = pvHb;
	}

	public double getClkTb() {
		return clkTb;
	}

	public void setClkTb(double clkTb) {
		this.clkTb = clkTb;
	}

	public double getClkHb() {
		return clkHb;
	}

	public void setClkHb(double clkHb) {
		this.clkHb = clkHb;
	}

	public Integer getPvTj() {
		return pvTj;
	}

	public void setPvTj(Integer pvTj) {
		this.pvTj = pvTj;
	}

	public Integer getClkTj() {
		return clkTj;
	}

	public void setClkTj(Integer clkTj) {
		this.clkTj = clkTj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
