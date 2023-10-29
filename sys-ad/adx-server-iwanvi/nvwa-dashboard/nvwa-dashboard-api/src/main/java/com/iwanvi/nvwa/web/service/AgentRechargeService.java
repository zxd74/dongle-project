package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.AgentRecharge;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;
import java.util.Map;

/**
 * class
 *
 * @author hao
 * @date 2018/10/22.
 */
public interface AgentRechargeService {
    void add(AgentRecharge agentRecharge, Integer userType);

    List<AgentRecharge> selectForList(AgentRecharge agentRecharge, String startDay, String endDay);

    Page<AgentRecharge> selectForPage(AgentRecharge agentRecharge, String startDay, String endDay, Integer cp, Integer ps);

    Map<String,Object> sumByDate(AgentRecharge agentRecharge,String startDay, String endDay);

    Map<String, Object> getSumPrice(AgentRecharge agentRecharge, String startDay, String endDay);
}
