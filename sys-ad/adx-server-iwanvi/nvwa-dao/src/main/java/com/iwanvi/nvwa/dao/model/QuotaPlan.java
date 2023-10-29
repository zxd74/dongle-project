package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import com.iwanvi.nvwa.dao.model.support.QuotaBaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("计划实体类")
public class QuotaPlan extends QuotaBaseVo{
	@ApiModelProperty("id")
    private Integer id;
	@ApiModelProperty("计划id")
    private Integer planId;
	@ApiModelProperty("日期")
    private Integer creDay;
	@ApiModelProperty("小时")
    private Integer creHour;
	@ApiModelProperty("创建日期")
    private Date createTime;
	@ApiModelProperty("计划激活数")
    private Long planActive = 0l;
	@ApiModelProperty("计划名称")
	private String planName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getPlanActive() {
		return planActive;
	}

	public void setPlanActive(Long planActive) {
		this.planActive = planActive;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}
    
}