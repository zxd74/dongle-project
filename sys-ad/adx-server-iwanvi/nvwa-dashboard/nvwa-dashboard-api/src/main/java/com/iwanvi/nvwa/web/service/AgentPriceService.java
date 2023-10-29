package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.AgentPrice;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface AgentPriceService {

    void add(AgentPrice agentPrice);

    void update(AgentPrice agentPrice);

    void delete(Integer id);

    AgentPrice get(Integer id);

    List<AgentPrice> selectForList(AgentPrice agentPrice);

    Page<AgentPrice> selectForPage(AgentPrice agentPrice, Integer cp, Integer ps);

    void syncPrice(Integer id);

    void syncProfit(Integer id);

    void copyPrice(Integer aid);

    void addPriceWithArea(Integer areaId);

    void addPriceWithIndustry(Integer industry);

    void rmPriceWithArea(Integer areaId);

    void rmPriceWithIndustry(Integer industry);

    void addPriceByAid(Integer aid);
}
