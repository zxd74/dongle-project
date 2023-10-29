package com.iwanvi.nvwa.dao.model.support;

public class QuotaResponseVo {
	private Integer req = 0;
	
	private Integer exp = 0;
	
	private Integer clk = 0;
	
	private Long cost = 0l;
	
	private Long investment = 0l;

	public Integer getReq() {
		return req;
	}

	public void setReq(Integer req) {
		this.req = req;
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

	public Long getInvestment() {
		return investment;
	}

	public void setInvestment(Long investment) {
		this.investment = investment;
	}
	
	
}
