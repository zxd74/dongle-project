package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class SysConfig {
    private Integer id;

    private String name;

    private String configKey;

    private String configValue;

    private Integer editState;

    private Integer pubState;

    private Integer status;

    private Date updateTime;

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

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Integer getEditState() {
        return editState;
    }

    public void setEditState(Integer editState) {
        this.editState = editState;
    }

    public Integer getPubState() {
        return pubState;
    }

    public void setPubState(Integer pubState) {
        this.pubState = pubState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}