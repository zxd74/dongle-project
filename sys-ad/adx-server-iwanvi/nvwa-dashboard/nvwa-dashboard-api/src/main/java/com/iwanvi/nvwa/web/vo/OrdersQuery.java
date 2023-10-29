package com.iwanvi.nvwa.web.vo;

import java.util.List;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.OrdersExample;
import com.iwanvi.nvwa.dao.model.OrdersExample.Criteria;

public class OrdersQuery {
	private String name;

	private Integer custId;

	private Integer runState;

	private Integer ordersState;

	private Integer cp;

	private Integer ps;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getRunState() {
		return runState;
	}

	public void setRunState(Integer runState) {
		this.runState = runState;
	}

	public Integer getOrdersState() {
		return ordersState;
	}

	public void setOrdersState(Integer ordersState) {
		this.ordersState = ordersState;
	}

	public Integer getCp() {
		return cp;
	}

	public void setCp(Integer cp) {
		this.cp = cp;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(Integer ps) {
		this.ps = ps;
	}

	public OrdersExample toExample(List<Integer> adverIds) {
		OrdersExample ordersExample = new OrdersExample();
		Criteria criteria = ordersExample.createCriteria();
		if (StringUtils.isNotBlank(this.name)) {
			criteria.andNameLike(StringUtils.concat(Constants.SIGN_PERCENT,this.name,Constants.SIGN_PERCENT));
		}
		if (this.custId != null) {
			criteria.andCustIdEqualTo(this.custId);
		}else if (adverIds != null && adverIds.size() > 0) {
			criteria.andCustIdIn(adverIds);
		}
		if (this.runState != null) {
			criteria.andRunStateEqualTo(this.runState);
		}
		if (this.ordersState != null) {
			criteria.andOrdersStateEqualTo(this.ordersState);
		} else {
			criteria.andOrdersStateNotEqualTo(Constants.STATE_INVALID);
		}
		criteria.andCreateTypeEqualTo(Constants.INTEGER_0);
		return ordersExample;
	}
}
