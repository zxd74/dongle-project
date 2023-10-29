package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Recharge;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;
import java.util.Map;

/**
 * class
 *
 * @author hao
 * @date 2018/10/22.
 */
public interface RechargeService {

    void recharge(Recharge recharge, Integer userType);

    List<Recharge> selectForList(Recharge recharge, String startDay, String endDay);

    Page<Recharge> selectForPage(Recharge recharge, String startDay, String endDay,Integer cp, Integer ps);

    Map<String,Object> sumByDate(Recharge recharge,String startDay, String endDay);

    Map<String, Object> getSumPrice(Recharge recharge, String startDay, String endDay);

    boolean judgeBalance(Integer id,Double price);

    Map<String,Object> reportForCost(Integer id,String startDay, String endDay, Integer cp, Integer ps);
}
