package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    void add(Company company);

    void update(Company company);

    void delete(Integer id);

    Company get(Integer id);

    Company get(String uuid);

    List<Company> selectForList(Company company);

    Page<Company> selectForPage(Company company, Integer cp, Integer ps);

    Map<String, Object> getBalance(Integer id);

    Map<String, Object> getSumCost(Integer id, String startDay, String endDay);
    
    List<Integer> getCompanyIdsByWithUid(Integer uid);
    
    List<Integer> getAiKaCompanyIdsByWithUid(Integer uid);
    
    Integer getAiKaCompanyIdByWithUid(Integer uid);

	Page<Company> auditPages(Company company, Integer cp, Integer ps);

	Company auditInfo(Integer id);
 }
