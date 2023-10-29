package com.iwanvi.nvwa.dao.model;

import java.util.Date;


public class QuotaConsumer {
	private Integer id;

	private Integer creDay;

	private Integer creHour;

	private String consumerId;

	private Integer entId;

	private Integer bid = 0;

	private Integer win;

	private Integer exp = 0;

	private Integer clk = 0;

	private Long investment = 0L;

	private Date createTime;

	private Long req = 0L;

	private float bid_rate = 0f;

	private float exp_rate = 0f;

	private float clk_rate = 0f;

	private float cpm = 0f;
	
	private float cpc = 0f;	
	
	private Integer cid;
	
	private String consumerName;
	
	
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public float getCpm() {
		cpm = getExp() / 1000;
		return cpm;
	}

	public float getCpc() {
		cpc = getClk() / 1000;
		return cpc;
	}

	public float getBid_rate() {
		bid_rate = getReq() == 0 ? 0 : getBid() / getReq();
		return bid_rate;
	}

	public float getExp_rate() {
		exp_rate = getReq() == 0 ? 0 : getExp() / getReq();
		return exp_rate;
	}

	public float getClk_rate() {
		clk_rate = getReq() == 0 ? 0 : getClk() / getExp();
		return clk_rate;
	}

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

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
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

	public Long getReq() {
		return req;
	}

	public void setReq(Long req) {
		this.req = req;
	}
}