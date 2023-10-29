package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class PackagePosition {
    private Integer id;

    private String name;

    private Integer flowId;

    private Integer forecastExposure;

    private Integer forecastClick;

    private Integer priceRtb;

    private Integer priceOther;

    private Integer positionIds;

    private Integer updateUser;

    private Date updateDate;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getForecastExposure() {
        return forecastExposure;
    }

    public void setForecastExposure(Integer forecastExposure) {
        this.forecastExposure = forecastExposure;
    }

    public Integer getForecastClick() {
        return forecastClick;
    }

    public void setForecastClick(Integer forecastClick) {
        this.forecastClick = forecastClick;
    }

    public Integer getPriceRtb() {
        return priceRtb;
    }

    public void setPriceRtb(Integer priceRtb) {
        this.priceRtb = priceRtb;
    }

    public Integer getPriceOther() {
        return priceOther;
    }

    public void setPriceOther(Integer priceOther) {
        this.priceOther = priceOther;
    }

    public Integer getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(Integer positionIds) {
        this.positionIds = positionIds;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}