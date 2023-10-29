package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class AgentRecharge {
    private Integer id;

    private Integer agentId;

    private Integer createDay;

    private Long price;

    private Integer type;

    private Integer createUser;

    private Date createTime;

    private String comment;

    private String operateUser;

    private String rechargePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Integer createDay) {
        this.createDay = createDay;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }

    public String getRechargePrice() {
        return rechargePrice;
    }

    public void setRechargePrice(String rechargePrice) {
        this.rechargePrice = rechargePrice;
    }
}