package com.iwanvi.nvwa.dao.model;

public class Area {
    private Integer id;

    private String areaName;

    private Integer areaCode;

    private Integer superiorArea;

    private Integer type;

    private Integer status;

    private Integer isCityLevel;

    private Integer isDistribution;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getSuperiorArea() {
        return superiorArea;
    }

    public void setSuperiorArea(Integer superiorArea) {
        this.superiorArea = superiorArea;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsCityLevel() {
        return isCityLevel;
    }

    public void setIsCityLevel(Integer isCityLevel) {
        this.isCityLevel = isCityLevel;
    }

    public Integer getIsDistribution() {
        return isDistribution;
    }

    public void setIsDistribution(Integer isDistribution) {
        this.isDistribution = isDistribution;
    }
}