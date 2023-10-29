package com.iwanvi.nvwa.dao.model;

import java.util.Date;
import java.util.List;

public class Tongfa {
    private Integer id;

    private String name;

    private Integer minSellDay;

    private String tongfaIds;

    private Integer updateUser;

    private Date updateTime;

    private Integer status;

    private List<AdPosition> positionList;

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

    public Integer getMinSellDay() {
        return minSellDay;
    }

    public void setMinSellDay(Integer minSellDay) {
        this.minSellDay = minSellDay;
    }

    public String getTongfaIds() {
        return tongfaIds;
    }

    public void setTongfaIds(String tongfaIds) {
        this.tongfaIds = tongfaIds;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<AdPosition> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<AdPosition> positionList) {
        this.positionList = positionList;
    }
}