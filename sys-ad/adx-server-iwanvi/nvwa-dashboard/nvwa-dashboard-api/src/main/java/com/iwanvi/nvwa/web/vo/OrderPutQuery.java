package com.iwanvi.nvwa.web.vo;

import java.util.List;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.OrderPutExample;
import com.iwanvi.nvwa.dao.model.OrderPutExample.Criteria;

public class OrderPutQuery {
	private String putName;

	private Integer putType;

	private Integer putState;

	private Integer createUser;

	private Integer cp;

	private Integer ps;

	public String getPutName() {
		return putName;
	}

	public void setPutName(String putName) {
		this.putName = putName;
	}

	public Integer getPutType() {
		return putType;
	}

	public void setPutType(Integer putType) {
		this.putType = putType;
	}

	public Integer getPutState() {
		return putState;
	}

	public void setPutState(Integer putState) {
		this.putState = putState;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
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

	public OrderPutExample toExample(List<Integer> createUsers) {
		OrderPutExample orderPutExample = new OrderPutExample();
		Criteria criteria = orderPutExample.createCriteria();
		if (StringUtils.isNotBlank(this.putName)) {
			criteria.andPutNameLike(StringUtils.concat(Constants.SIGN_PERCENT, this.putName, Constants.SIGN_PERCENT));
		} else {
			orderPutExample.setOffset(0);
			orderPutExample.setRows(30);
		}
		if (this.getPutType() != null) {
			criteria.andPutTypeEqualTo(this.getPutType());
		}
		if (this.putState != null) {
			criteria.andPutStateEqualTo(this.putState);
		} else {
			criteria.andPutStateNotEqualTo(Constants.STATE_INVALID);
		}
		if (this.createUser != null) {
			criteria.andCreateUserEqualTo(this.createUser);
		} else if (createUsers != null && createUsers.size() > 0) {
			criteria.andCreateUserIn(createUsers);
		}
		return orderPutExample;
	}
}
