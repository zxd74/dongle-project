package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class PositionExclude {
    private Integer id;

    private Integer positionId;

    private Integer tongfaId;

    private Integer excludePosition;

    private Integer excludeTongfa;

    private Integer updateUser;

    private Date updateTime;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getTongfaId() {
        return tongfaId;
    }

    public void setTongfaId(Integer tongfaId) {
        this.tongfaId = tongfaId;
    }

    public Integer getExcludePosition() {
        return excludePosition;
    }

    public void setExcludePosition(Integer excludePosition) {
        this.excludePosition = excludePosition;
    }

    public Integer getExcludeTongfa() {
        return excludeTongfa;
    }

    public void setExcludeTongfa(Integer excludeTongfa) {
        this.excludeTongfa = excludeTongfa;
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
}