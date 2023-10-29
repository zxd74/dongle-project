package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class DmpRules {
    private Integer id;

    private Integer tid;

    private Integer rid;

    private Integer operation;

    private Double score;

    private Integer jid;

    private Integer status;

    private Integer sumOp;

    private Double sumScL;

    private Integer createUser;

    private Date createTime;

    private Integer relation;

    private Double sumScB;

    private String args;

    private String tName;

    private String rName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSumOp() {
        return sumOp;
    }

    public void setSumOp(Integer sumOp) {
        this.sumOp = sumOp;
    }

    public Double getSumScL() {
        return sumScL;
    }

    public void setSumScL(Double sumScL) {
        this.sumScL = sumScL;
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

    public Double getSumScB() {
        return sumScB;
    }

    public void setSumScB(Double sumScB) {
        this.sumScB = sumScB;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }
}