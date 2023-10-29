package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.QuotaCust;

import java.util.List;

public interface QuotaCustService {

    List<QuotaCust> selectQuotaCustByTypeAndDay(Integer type, String sDay, String eDay, Integer uid);

    List<QuotaCust> selectQuotaCustByDayAndCid(String sDay, String eDay, Integer cid);
}
