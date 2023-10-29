package com.iwanvi.nvwa.web.vo;

import org.apache.commons.lang3.StringUtils;

import com.iwanvi.nvwa.dao.model.AppChannelExample;
import com.iwanvi.nvwa.dao.model.support.QueryReq;

public class AppChannelQueryReq extends QueryReq<AppChannelExample> {
	private String name; //渠道号
	private String cname; //渠道名称
	private String parentName; //一级渠道号
	private Integer type; //查询类型, 0-不限;1-一级;2-二级

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCname() {
		return cname;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public AppChannelExample buildExample() {
		AppChannelExample example = new AppChannelExample();
		example.page(pageNo, pageSize);
		example.setOrderByClause("id desc");

		AppChannelExample.Criteria criteria = example.createCriteria();
		criteria.andIsDeletedNotEqualTo(true);
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
		}
		if (StringUtils.isNotBlank(cname)) {
			criteria.andCnameLike("%" + cname + "%");
		}
		return example;
	}

}
