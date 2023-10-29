package com.iwanvi.nvwa.dao.model;

import java.util.Date;
import java.util.List;

public class DmpPersons {
    private Integer id;

    private String name;

    private String did;

    private String tags;

    private Integer relation;

    private Integer status;

    private Integer num;

    private Date updateTime;

    private Integer times;

    private Integer period;

    private Integer type;

    private Integer createUser;

    private Date createTime;

    private Integer computeStatus;

    private String remark;

    private List<DmpTag> tagList;

    private List<DmpDatas> datasList;

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

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getComputeStatus() {
        return computeStatus;
    }

    public void setComputeStatus(Integer computeStatus) {
        this.computeStatus = computeStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DmpTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<DmpTag> tagList) {
        this.tagList = tagList;
    }

    public List<DmpDatas> getDatasList() {
        return datasList;
    }

    public void setDatasList(List<DmpDatas> datasList) {
        this.datasList = datasList;
    }
}