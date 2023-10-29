package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.QuotaDid;

public interface QuotaDidService {
	List<QuotaDid> listByEntId(Integer entId, List<Integer> entIds, Long quotaDidStartDate, Long quotaDidEndDate);
}
