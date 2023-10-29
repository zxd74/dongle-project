package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import com.iwanvi.nvwa.dao.model.support.QuotaBaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单报告数据实体类")
public class QuotaOrder extends QuotaBaseVo{
	@ApiModelProperty("id")
    private Integer id;
	@ApiModelProperty("订单id")
    private Integer orderId;
	@ApiModelProperty("日期")
    private Integer creDay;
	@ApiModelProperty("时间")
    private Integer creHour;
	@ApiModelProperty("创建时间")
    private Date createTime;
	@ApiModelProperty("订单激活数")
    private Long orderActive = 0l; 
	@ApiModelProperty("订单名称")
	private String orderName;
	@ApiModelProperty("同步订单id")
	private Integer outOrderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

	public Long getOrderActive() {
		return orderActive;
	}

	public void setOrderActive(Long orderActive) {
		this.orderActive = orderActive;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Integer getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(Integer outOrderId) {
		this.outOrderId = outOrderId;
	}
    
}