package com.iwanvi.nvwa.web.vo;

import java.util.List;

public class ScheduleForm {
	private Integer id;

    private Integer adPosId;

    private Integer startDay;

    private Integer endDay;

    private Integer isSection;

    private String scheduleFixed;

    private Integer exposureLimit;

    private Integer isSmooth;

    private Integer priority;

    private Integer status;

    private Integer monthPeriod;

    private String period;

    private String posName;
    
    private List<Integer> times;
    
    private Integer straTime;

    private Integer straChapter;

    private Integer straPage;

	public List<Integer> getTimes() {
		return times;
	}

	public void setTimes(List<Integer> times) {
		this.times = times;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdPosId() {
		return adPosId;
	}

	public void setAdPosId(Integer adPosId) {
		this.adPosId = adPosId;
	}

	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	public Integer getEndDay() {
		return endDay;
	}

	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	public Integer getIsSection() {
		return isSection;
	}

	public void setIsSection(Integer isSection) {
		this.isSection = isSection;
	}

	public String getScheduleFixed() {
		return scheduleFixed;
	}

	public void setScheduleFixed(String scheduleFixed) {
		this.scheduleFixed = scheduleFixed;
	}

	public Integer getExposureLimit() {
		return exposureLimit;
	}

	public void setExposureLimit(Integer exposureLimit) {
		this.exposureLimit = exposureLimit;
	}

	public Integer getIsSmooth() {
		return isSmooth;
	}

	public void setIsSmooth(Integer isSmooth) {
		this.isSmooth = isSmooth;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getMonthPeriod() {
		return monthPeriod;
	}

	public void setMonthPeriod(Integer monthPeriod) {
		this.monthPeriod = monthPeriod;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

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

}
