package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * @author hp-51nc
 *
 */
@ApiModel("计划实体类")
public class Plan {
	@ApiModelProperty("id")
    private Integer id;
	@ApiModelProperty("计划名称")
    private String planName;	
	@ApiModelProperty("客户id")
    private Integer adverId;
	@ApiModelProperty("计划限额")
    private Integer planLimit;
	@ApiModelProperty("运行状态")
    private Integer runState;
	@ApiModelProperty("限额状态")
    private Integer limitState;
	@ApiModelProperty("计划状态")
    private Integer planState;
	@ApiModelProperty("创建人")
    private Integer createUser;
	@ApiModelProperty("创建时间")
    private Date createTime;
	@ApiModelProperty("修改时间")
    private Date updateTime;
	@ApiModelProperty("曝光上报地址")
    private String impMonitorUrl;
	@ApiModelProperty("点击上报地址")
    private String clkMonitorUrl;
	@ApiModelProperty("关联投放个数")
    private Integer putNumbers;
	@ApiModelProperty("广告客户名称")
	private String custName;
	@ApiModelProperty("创建人名称")
	private String userName;
	
	private Integer createType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getAdverId() {
        return adverId;
    }

    public void setAdverId(Integer adverId) {
        this.adverId = adverId;
    }

    public Integer getPlanLimit() {
        return planLimit;
    }

    public void setPlanLimit(Integer planLimit) {
        this.planLimit = planLimit;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public Integer getLimitState() {
        return limitState;
    }

    public void setLimitState(Integer limitState) {
        this.limitState = limitState;
    }

    public Integer getPlanState() {
        return planState;
    }

    public void setPlanState(Integer planState) {
        this.planState = planState;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImpMonitorUrl() {
        return impMonitorUrl;
    }

    public void setImpMonitorUrl(String impMonitorUrl) {
        this.impMonitorUrl = impMonitorUrl;
    }

    public String getClkMonitorUrl() {
        return clkMonitorUrl;
    }

    public void setClkMonitorUrl(String clkMonitorUrl) {
        this.clkMonitorUrl = clkMonitorUrl;
    }

	public Integer getPutNumbers() {
		return putNumbers;
	}

	public void setPutNumbers(Integer putNumbers) {
		this.putNumbers = putNumbers;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCreateType() {
		return createType;
	}

	public void setCreateType(Integer createType) {
		this.createType = createType;
	}
    
}