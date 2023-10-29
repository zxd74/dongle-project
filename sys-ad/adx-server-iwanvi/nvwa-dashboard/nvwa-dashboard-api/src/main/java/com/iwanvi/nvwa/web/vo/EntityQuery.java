package com.iwanvi.nvwa.web.vo;

import java.util.List;

import com.google.common.collect.Lists;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.EntityExample;
import com.iwanvi.nvwa.dao.model.EntityExample.Criteria;

public class EntityQuery {
	private String entityName;

	private Integer custId;

	private Integer putAllId;

	private Integer runState;

	private Integer entityState;

	private Integer cp;

	private Integer ps;

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Integer getPutAllId() {
		return putAllId;
	}

	public void setPutAllId(Integer putAllId) {
		this.putAllId = putAllId;
	}

	public Integer getRunState() {
		return runState;
	}

	public void setRunState(Integer runState) {
		this.runState = runState;
	}

	public Integer getEntityState() {
		return entityState;
	}

	public void setEntityState(Integer entityState) {
		this.entityState = entityState;
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

	public EntityExample toExample(List<Integer> putIds) {
		EntityExample entityExample = new EntityExample();
		Criteria criteria = entityExample.createCriteria();
		if (StringUtils.isNotBlank(this.entityName)) {
			criteria.andEntityNameLike(
					StringUtils.concat(Constants.SIGN_PERCENT, this.entityName, Constants.SIGN_PERCENT));
		} else {
			entityExample.setOffset(0);
			entityExample.setRows(20);
		}
		if (this.putAllId != null) {
			criteria.andPutAllIdEqualTo(this.putAllId);
		} else if (putIds != null && putIds.size() > 0) {
			criteria.andPidIn(putIds);
		}
		if (this.entityState != null) {
			criteria.andEntityStateEqualTo(this.entityState);
		} else {
			List<Integer> hideStates = Lists.newArrayList();
			hideStates.add(Constants.STATE_INVALID);
//			hideStates.add(Constants.STATE_FAILURE_AUDIT_BLACKLIST);
//			hideStates.add(Constants.STATE_WAIT_UPDATE);
			criteria.andEntityStateNotIn(hideStates);
		}
		if (this.runState != null) {
			criteria.andRunStateEqualTo(this.runState);
		}
		return entityExample;
	}

}
