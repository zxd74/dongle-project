package com.iwanvi.nvwa.dao.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class Template {
    private Integer id;

    private String name;

    private Integer type;

    private Integer createUser;

    private Integer updateUser;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer status;

    private Integer aikaTemplateId;

    private Integer materialType; // 素材类型

    private String materialName; // 素材名称串

    private List<TemplateModuleRelation> moduleList; //组件list

    private List<Object> templateModuleRelationList; // 反显的 模版组件关系list

    private Integer positionNum; // 在用广告位数量

    private Integer start;

    private Integer limit;

    private Integer isCopy;

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

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getAikaTemplateId() {
        return aikaTemplateId;
    }

    public void setAikaTemplateId(Integer aikaTemplateId) {
        this.aikaTemplateId = aikaTemplateId;
    }

    public Integer getMaterialType() {
        return materialType;
    }

    public void setMaterialType(Integer materialType) {
        this.materialType = materialType;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public List<Object> getTemplateModuleRelationList() {
        return templateModuleRelationList;
    }

    public void setTemplateModuleRelationList(List<Object> templateModuleRelationList) {
        this.templateModuleRelationList = templateModuleRelationList;
    }

    public Integer getPositionNum() {
        return positionNum;
    }

    public void setPositionNum(Integer positionNum) {
        this.positionNum = positionNum;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(Integer isCopy) {
        this.isCopy = isCopy;
    }

    public List<TemplateModuleRelation> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<TemplateModuleRelation> moduleList) {
        this.moduleList = moduleList;
    }
}