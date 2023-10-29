package com.iwanvi.nvwa.dao.model;

import java.util.Date;

public class QuotaDsp {
    private Integer id;

    private Integer creDay;

    private Integer creHour;

    private Integer aderId;

    private Integer entId;

    private Integer bid;

    private Integer win;

    private Integer exp;

    private Integer clk;

    private Long investment;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreDay() {
        return creDay;
    }

    public void setCreDay(Integer creDay) {
        this.creDay = creDay;
    }

    public Integer getCreHour() {
        return creHour;
    }

    public void setCreHour(Integer creHour) {
        this.creHour = creHour;
    }

    public Integer getAderId() {
        return aderId;
    }

    public void setAderId(Integer aderId) {
        this.aderId = aderId;
    }

    public Integer getEntId() {
        return entId;
    }

    public void setEntId(Integer entId) {
        this.entId = entId;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getClk() {
        return clk;
    }

    public void setClk(Integer clk) {
        this.clk = clk;
    }

    public Long getInvestment() {
        return investment;
    }

    public void setInvestment(Long investment) {
        this.investment = investment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}