package com.iwanvi.nvwa.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.QuotaDidMapper;
import com.iwanvi.nvwa.dao.model.QuotaDid;
import com.iwanvi.nvwa.dao.model.QuotaDidExample;
import com.iwanvi.nvwa.dao.model.QuotaDidExample.Criteria;
import com.iwanvi.nvwa.web.service.QuotaDidService;

@Service
public class QuotaDidServiceImpl implements QuotaDidService {
	@Autowired
	private QuotaDidMapper quotaDidMapper;

	public List<QuotaDid> listByEntId(Integer entId, List<Integer> entIds, Long quotaDidStartDate,
			Long quotaDidEndDate) {
		QuotaDidExample didExample = new QuotaDidExample();
		Criteria criteria = didExample.createCriteria();
		if (entId != null) {
			criteria.andCreIdEqualTo(entId);
		}
		if (entIds != null && entIds.size() > 0) {
			criteria.andCreIdIn(entIds);
		}
		criteria.andActTimeBetween(quotaDidStartDate, quotaDidEndDate).andIsActiveEqualTo(Constants.STATE_VALID);
		List<QuotaDid> quotaDids = quotaDidMapper.selectByExample(didExample);
		return quotaDidMapper.selectByExample(didExample);
	}
}
