package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.QuotaBook;

import java.util.List;

public interface QuotaBookService {

    List<QuotaBook> sumWithDate(Integer sDate, Integer eDate, String level1s, String level2s,
                                 String level3s);

    List<QuotaBook> sumWithGroup(Integer sDate, Integer eDate, String level1s, String level2s,
                                 String level3s, String group);

    List<QuotaBook> detailReport(Integer id, Integer sDate, Integer eDate, String level1s, String level2s,
                                 String level3s, String group);
}
