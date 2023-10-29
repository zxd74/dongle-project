package com.iwanvi.nvwa.dao.model;

public class QuotaCust {
    private Integer id;

    private String uuid;

    private Integer type;

    private Integer creDay;

    private Integer creHour;

    private Integer exp=0;

    private Integer clk=0;

    private Long cost=0L;

    private String name;

    private Integer active=0;

    private Integer custId;

    public QuotaCust() {
	}
    
    public QuotaCust(int creDay) {
    	this.creDay=creDay;
	}

    public QuotaCust(int creDay, Integer exp, Integer clk, Long cost) {
    	this.creDay = creDay;
    	this.exp = exp;
    	this.clk = clk;
    	this.cost = cost;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }
}