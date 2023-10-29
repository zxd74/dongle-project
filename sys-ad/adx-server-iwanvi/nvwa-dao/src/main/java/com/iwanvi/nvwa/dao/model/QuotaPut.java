package com.iwanvi.nvwa.dao.model;

import java.util.Date;

import com.iwanvi.nvwa.dao.model.support.QuotaBaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("投放报表数据实体类")
public class QuotaPut extends QuotaBaseVo{
	@ApiModelProperty("id")
    private Integer id;
	@ApiModelProperty("投放类型")
	private Integer putType;
	@ApiModelProperty("投放id")
    private Integer putId;
	@ApiModelProperty("日期")
    private Integer creDay;
	@ApiModelProperty("时间")
    private Integer creHour;
	@ApiModelProperty("创建时间")
    private Date createTime;
	@ApiModelProperty("")
    private float bid_rate;
	@ApiModelProperty("")
    private float exp_rate;
	@ApiModelProperty("")
    private float clk_rate;
    @ApiModelProperty("投放激活数")
    private Long putActive = 0l;
    @ApiModelProperty("投放名称")
    private String putName;
    @ApiModelProperty("订单同步投放id")
    private Integer outPutId;

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

    public Integer getPutType() {
		return putType;
	}

	public void setPutType(Integer putType) {
		this.putType = putType;
	}

	public Integer getPutId() {
        return putId;
    }

    public void setPutId(Integer putId) {
        this.putId = putId;
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

	public Long getPutActive() {
		return putActive;
	}

	public void setPutActive(Long putActive) {
		this.putActive = putActive;
	}

	public String getPutName() {
		return putName;
	}

	public void setPutName(String putName) {
		this.putName = putName;
	}

	public Integer getOutPutId() {
		return outPutId;
	}

	public void setOutPutId(Integer outPutId) {
		this.outPutId = outPutId;
	}
    
}