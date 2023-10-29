package com.iwanvi.nvwa.dao.model;

public class SdkAllotSchedule {
    private Integer id;

    private Integer allotmentId;

    private Integer adPosId;

    private Integer startDay;

    private Integer endDay;

    private Integer isSection;

    private String scheduleFixed;

    private Integer exposureLimit;

    private Integer isSmooth;

    private Integer priority;

    private Integer monthPeriod;

    private Integer schedulePeriod;

    private Integer status;

    private Integer straTime;

    private Integer straChapter;

    private Integer straPage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAllotmentId() {
        return allotmentId;
    }

    public void setAllotmentId(Integer allotmentId) {
        this.allotmentId = allotmentId;
    }

    public Integer getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(Integer adPosId) {
        this.adPosId = adPosId == null ? 0 : adPosId;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay == null ? 0 : startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay == null ? 0 :  endDay;
    }

    public Integer getIsSection() {
        return isSection;
    }

    public void setIsSection(Integer isSection) {
        this.isSection = isSection == null ? 0 : isSection;
    }

    public String getScheduleFixed() {
        return scheduleFixed;
    }

    public void setScheduleFixed(String scheduleFixed) {
        this.scheduleFixed = scheduleFixed == null ? "" : scheduleFixed;
    }

    public Integer getExposureLimit() {
        return exposureLimit;
    }

    public void setExposureLimit(Integer exposureLimit) {
        this.exposureLimit = exposureLimit == null ? 0 : exposureLimit;
    }

    public Integer getIsSmooth() {
        return isSmooth;
    }

    public void setIsSmooth(Integer isSmooth) {
        this.isSmooth = isSmooth == null ? 0 : isSmooth;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority == null ? 0 : priority;
    }

    public Integer getMonthPeriod() {
        return monthPeriod;
    }

    public void setMonthPeriod(Integer monthPeriod) {
        this.monthPeriod = monthPeriod == null ? 0 : monthPeriod;
    }

    public Integer getSchedulePeriod() {
        return schedulePeriod;
    }

    public void setSchedulePeriod(Integer schedulePeriod) {
        this.schedulePeriod = schedulePeriod == null ? 0 : schedulePeriod;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status == null ? 0 : status;
    }

    public Integer getStraTime() {
        return straTime;
    }

    public void setStraTime(Integer straTime) {
        this.straTime = straTime == null ? 0 : straTime;
    }

    public Integer getStraChapter() {
        return straChapter;
    }

    public void setStraChapter(Integer straChapter) {
        this.straChapter = straChapter == null ? 0 : straChapter;
    }

    public Integer getStraPage() {
        return straPage;
    }

    public void setStraPage(Integer straPage) {
        this.straPage = straPage == null ? 0 : straPage;
    }
}