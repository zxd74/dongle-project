package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import com.iwanvi.nvwa.dao.model.support.QuotaBaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("创意报表实体类")
public class QuotaEntity extends QuotaBaseVo {
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("创意id")
	private Integer entId;
	@ApiModelProperty("日期")
	private Integer creDay;
	@ApiModelProperty("小时")
	private Integer creHour;
	@ApiModelProperty("创建时间")
	private Date createTime;
	@ApiModelProperty("创意激活数")
	private Long entityActive = 0l;
	@ApiModelProperty("创意名称")
	private String entityName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEntId() {
		return entId;
	}

	public void setEntId(Integer entId) {
		this.entId = entId;
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

	public Long getEntityActive() {
		return entityActive;
	}

	public void setEntityActive(Long entityActive) {
		this.entityActive = entityActive;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
}