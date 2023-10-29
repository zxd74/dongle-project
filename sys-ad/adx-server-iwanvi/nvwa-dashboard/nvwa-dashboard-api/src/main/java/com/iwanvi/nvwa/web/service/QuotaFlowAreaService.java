package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.QuotaFlowArea;

import java.util.List;

public interface QuotaFlowAreaService {

    List<QuotaFlowArea> sumByDay(Integer sDay, Integer eDay, String areas);

    List<QuotaFlowArea> detailReport(Integer sDay, Integer eDay, String area);
}
