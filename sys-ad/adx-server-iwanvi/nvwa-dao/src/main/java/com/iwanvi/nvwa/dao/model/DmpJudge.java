package com.iwanvi.nvwa.dao.model;

import java.util.Date;
import java.util.List;

public class DmpJudge {
    private Integer id;

    private Integer did;

    private Integer tid;

    private Integer status;

    private Integer createUser;

    private Date createTime;

    private Integer relation;

    private List<DmpRules> rulesList;

    private String rules;

    private String judgeRules;

    private String tagsName;

    private String dName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getRelation() {
        return relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public List<DmpRules> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<DmpRules> rulesList) {
        this.rulesList = rulesList;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getJudgeRules() {
        return judgeRules;
    }

    public void setJudgeRules(String judgeRules) {
        this.judgeRules = judgeRules;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }
}