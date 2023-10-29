package com.iwanvi.nvwa.web.vo;

import java.util.List;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.OrderPutAllExample;
import com.iwanvi.nvwa.dao.model.OrderPutAllExample.Criteria;

public class OrderPutAllQuery {
	private String putName;

	private Integer oid;

	private String adPosition;

	private Integer runState;

	private Integer putState;

	private Integer cp;

	private Integer ps;

	public String getPutName() {
		return putName;
	}

	public void setPutName(String putName) {
		this.putName = putName;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(String adPosition) {
		this.adPosition = adPosition;
	}

	public Integer getRunState() {
		return runState;
	}

	public void setRunState(Integer runState) {
		this.runState = runState;
	}

	public Integer getPutState() {
		return putState;
	}

	public void setPutState(Integer putState) {
		this.putState = putState;
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

	public OrderPutAllExample toExample(List<Integer> orderIds) {
		OrderPutAllExample orderPutAllExample = new OrderPutAllExample();
		Criteria criteria = orderPutAllExample.createCriteria();
		if (StringUtils.isNotBlank(this.putName)) {
			criteria.andPutNameLike(StringUtils.concat(Constants.SIGN_PERCENT, this.putName, Constants.SIGN_PERCENT));
		} else {
			orderPutAllExample.setOffset(0);
			orderPutAllExample.setRows(30);
		}
		if (StringUtils.isNotBlank(this.adPosition)) {
			criteria.andAdPositionLike(StringUtils.concat(Constants.SIGN_PERCENT, Constants.SIGN_COMMA, this.adPosition,
					Constants.SIGN_COMMA, Constants.SIGN_PERCENT));
		}
		if (this.runState != null) {
			criteria.andRunStateEqualTo(this.runState);
		}
		if (this.putState != null) {
			criteria.andPutStateEqualTo(this.putState);
		} else {
			criteria.andPutStateNotEqualTo(Constants.STATE_INVALID);
		}
		if (this.oid != null) {
			criteria.andOidEqualTo(this.oid);
		}
		if (orderIds != null && orderIds.size() > 0) {
			criteria.andOidIn(orderIds);
		}
		return orderPutAllExample;
	}
}
