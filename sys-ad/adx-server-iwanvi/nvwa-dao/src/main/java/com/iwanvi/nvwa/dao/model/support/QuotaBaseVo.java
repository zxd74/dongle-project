package com.iwanvi.nvwa.dao.model.support;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("基础报告数据实体类")
public class QuotaBaseVo {
	@ApiModelProperty("请求数")
	private Integer req = 0;
	@ApiModelProperty("曝光数")
	private Integer exp = 0;
	@ApiModelProperty("点击数")
	private Integer clk = 0;
	@ApiModelProperty("")
	private Integer act = 0;
	@ApiModelProperty("花费")
	private Long cost = 0L;
	@ApiModelProperty("成本")
	private Long investment = 0L;
	@ApiModelProperty("")
	private Integer bid = 0;
	@ApiModelProperty("")
	private Integer win = 0;
	@ApiModelProperty("")
	private Long agentCost = 0L;
	@ApiModelProperty("每千人成本")
    private float cpm = 0f;
	@ApiModelProperty("每点击成本")
    private float cpc = 0f;
	@ApiModelProperty("每成果成本")
    private Double cpa = 0d;
	@ApiModelProperty("转化率")
    private Double cvr = 0d;
	
	private String fsName;
	
	private Integer fsId;
	
	private float realCost;

	public String getFsName() {
		return fsName;
	}

	public void setFsName(String fsName) {
		this.fsName = fsName;
	}

	public Integer getFsId() {
		return fsId;
	}

	public void setFsId(Integer fsId) {
		this.fsId = fsId;
	}

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

	public Integer getAct() {
		return act;
	}

	public void setAct(Integer act) {
		this.act = act;
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

	public Long getAgentCost() {
		return agentCost;
	}

	public void setAgentCost(Long agentCost) {
		this.agentCost = agentCost;
	}

	public float getCpm() {
		return cpm;
	}

	public void setCpm(float cpm) {
		this.cpm = cpm;
	}

	public float getCpc() {
		return cpc;
	}

	public void setCpc(float cpc) {
		this.cpc = cpc;
	}

	public Double getCpa() {
		return cpa;
	}

	public void setCpa(Double cpa) {
		this.cpa = cpa;
	}

	public Double getCvr() {
		return cvr;
	}

	public void setCvr(Double cvr) {
		this.cvr = cvr;
	}
	
	public float getRealCost() {
		return realCost;
	}

	public void setRealCost(float realCost) {
		this.realCost = realCost;
	}

	public void computeQuotaBase(QuotaBaseVo quotaBaseVo, Long active){
		quotaBaseVo.setRealCost(quotaBaseVo.getCost().floatValue()/100);
		quotaBaseVo
				.setCpm(quotaBaseVo.getExp() == 0 ? 0f : ((quotaBaseVo.getRealCost()) / quotaBaseVo.getExp()) * 1000);
		quotaBaseVo.setCpc(quotaBaseVo.getClk() == 0 ? 0f : (quotaBaseVo.getRealCost()) / quotaBaseVo.getClk());
		//格式化 小数点后两位
		quotaBaseVo.setCpm(new BigDecimal(quotaBaseVo.getCpm()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
		quotaBaseVo.setCpc(new BigDecimal(quotaBaseVo.getCpc()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
		if (active != null) {
			quotaBaseVo.setCpa(active == 0l ? 0D : quotaBaseVo.getCost() / active);
			quotaBaseVo.setCvr(quotaBaseVo.getClk() == 0 ? 0D : active / quotaBaseVo.getClk());
		}
		quotaBaseVo.setInvestment(quotaBaseVo.getInvestment()/100);
		quotaBaseVo.setCost(quotaBaseVo.getCost()/100);
	}
}
