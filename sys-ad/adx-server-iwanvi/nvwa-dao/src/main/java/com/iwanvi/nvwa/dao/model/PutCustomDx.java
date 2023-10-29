package com.iwanvi.nvwa.dao.model;

public class PutCustomDx {
    private Integer id;

    private Integer putId;

    private Integer tagId;

    private Integer tagValue;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPutId() {
        return putId;
    }

    public void setPutId(Integer putId) {
        this.putId = putId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagValue() {
        return tagValue;
    }

    public void setTagValue(Integer tagValue) {
        this.tagValue = tagValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}