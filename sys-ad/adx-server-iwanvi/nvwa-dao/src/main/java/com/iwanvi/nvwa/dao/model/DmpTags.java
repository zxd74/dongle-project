package com.iwanvi.nvwa.dao.model;

import java.util.Date;
import java.util.List;

public class DmpTags {
    private Integer id;

    private String name;

    private String tags;

    private Integer status;

    private Date updateTime;

    private Date createTime;

    private Integer createUser;

    private Integer isDx;

    private String dxKey;

    private String tagStr;

    private List<DmpTag> tagList;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getIsDx() {
        return isDx;
    }

    public void setIsDx(Integer isDx) {
        this.isDx = isDx;
    }

    public String getDxKey() {
        return dxKey;
    }

    public void setDxKey(String dxKey) {
        this.dxKey = dxKey;
    }

    public String getTagStr() {
        return tagStr;
    }

    public void setTagStr(String tagStr) {
        this.tagStr = tagStr;
    }

    public List<DmpTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<DmpTag> tagList) {
        this.tagList = tagList;
    }
}