package com.iwanvi.nvwa.web.vo;

import java.util.List;

public class SdkAllotmentForm {
    private Integer id;

    private String allotmentName;

    private Integer appId;
    
    private String appName;

    private Integer platformId;
    
    private String plaName;

    private Integer os;

    private String appVersion;

    private String channelId;

    private String posIds;

    private Integer frequency;

    private Integer status;

    private Integer type;

    private Integer chargeType;
    
    private List<ScheduleForm> scheduleList;

	public String getPosIds() {
		return posIds;
	}

	public void setPosIds(String posIds) {
		this.posIds = posIds;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPlaName() {
		return plaName;
	}

	public void setPlaName(String plaName) {
		this.plaName = plaName;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getAllotmentName() {
		return allotmentName;
	}

	public void setAllotmentName(String allotmentName) {
		this.allotmentName = allotmentName;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public Integer getOs() {
		return os;
	}

	public void setOs(Integer os) {
		this.os = os;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getChargeType() {
		return chargeType;
	}

	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}

	public List<ScheduleForm> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<ScheduleForm> scheduleList) {
		this.scheduleList = scheduleList;
	}

}
