package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AgentRecharge;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AgentRechargeService;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.service.RechargeService;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agentRecharge")
public class AgentRechargeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AgentRechargeController.class);

    @Autowired
    private AgentRechargeService agentRechargeService;
    @Autowired
    private RechargeService rechargeService;
    @Autowired
    private CompanyService companyService;

    @ResponseBody
    @RequestMapping("add")
    public String add(AgentRecharge agentRecharge, HttpServletRequest request) {
        String result = StringUtils.EMPTY;
        boolean bool = true;
        List<String> params = Lists.newArrayList();
        try {
            if (agentRecharge.getAgentId() == null) {
                bool = false;
                params.add("agentId");
            }
            if (agentRecharge.getType() == null) {
                bool = false;
                params.add("type");
            }
//            if (agentRecharge.getPrice() == null) {
//                params.add("price");
//            }
            if (!bool) {
                result = JsonUtils.PARAMETER_ERROR(params);
                logger.info("parameter error:{}",result);
                return result;
            }

            if (StringUtils.isNotBlank(agentRecharge.getRechargePrice())) {
                boolean isPositiveInteger = MatcherUtils.isPositiveInteger(agentRecharge.getRechargePrice());
                if (!isPositiveInteger) {
                    throw new ControllerException("请输入正确的金额");
                }
            }else {
                throw new ControllerException("请输入金额");
            }
            if (agentRecharge.getType().equals(Constants.INTEGER_2)) {
                boolean judgeBalance = rechargeService.judgeBalance(agentRecharge.getAgentId(), Double.parseDouble(agentRecharge.getRechargePrice()));
                if (!judgeBalance) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED, "余额不足");
                    logger.info("take out money error, balance < 0");
                    return result;
                }
            }
            Integer uid = getUserId(request);
            agentRecharge.setCreateUser(uid);
            agentRechargeService.add(agentRecharge,getUserType(request));
            result = JsonUtils.STATUS_OK();
        } catch (Exception e) {
            logger.info("add agent recharge error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getList")
    public String getList(AgentRecharge agentRecharge, String startDay, String endDay, Integer cp, Integer ps) {
        String result = StringUtils.EMPTY;
        boolean bool = true;
        List<String> params = Lists.newArrayList();
        try {
            if (agentRecharge.getAgentId() == null) {
                bool = false;
                params.add("agentId");
            }
            if (agentRecharge.getType() == null) {
                bool = false;
                params.add("type");
            }
            if (!bool) {
                result = JsonUtils.PARAMETER_ERROR(params);
                logger.info("parameter error:{}",result);
                return result;
            }
            Page<AgentRecharge> page = agentRechargeService.selectForPage(agentRecharge, startDay, endDay, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.info("get agent recharge list error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("sumByDate")
    public String sumByDate(AgentRecharge agentRecharge, String startDay, String endDay) {
        String result = StringUtils.EMPTY;
        boolean bool = true;
        List<String> params = Lists.newArrayList();
        try {
            if (agentRecharge.getAgentId() == null) {
                bool = false;
                params.add("agentId");
            }
            if (agentRecharge.getType() == null) {
                bool = false;
                params.add("type");
            }
            if (!bool) {
                result = JsonUtils.PARAMETER_ERROR(params);
                logger.info("parameter error:{}",result);
                return result;
            }
            Map<String,Object> map = agentRechargeService.sumByDate(agentRecharge, startDay, endDay);
            result = JsonUtils.STATUS_OK(map);
        } catch (Exception e) {
            logger.info("sum  agent recharge error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("sumHistory")
    public String sumPrice(AgentRecharge agentRecharge, String startDay, String endDay) {
        String result = StringUtils.EMPTY;
        boolean bool = true;
        List<String> params = Lists.newArrayList();
        Map<String,Object> respMap = Maps.newHashMap();
        try {
            if (agentRecharge.getAgentId() == null) {
                bool = false;
                params.add("advertiserId");
            }
            if (StringUtils.isBlank(startDay)) {
                bool = false;
                params.add("startDay");
            }
            if (StringUtils.isBlank(endDay)) {
                bool = false;
                params.add("endDay");
            }
            if (!bool) {
                result = JsonUtils.PARAMETER_ERROR(params);
                logger.info("parameter error:{}",result);
                return result;
            }
            agentRecharge.setType(WebConstants.MONEY_TYPE_RECHARGE);
            Map<String, Object> rechargeMap = agentRechargeService.getSumPrice(agentRecharge, startDay, endDay);
            Object sumRechargeNow = rechargeMap.get("sumNow");
            Object sumRechargeBefore = rechargeMap.get("sumBefore");
            respMap.put("sumRechargeNow", sumRechargeNow == null || sumRechargeNow instanceof Integer? 0 : ((BigDecimal)sumRechargeNow).longValue() / 100);
            respMap.put("sumRechargeBefore", sumRechargeBefore == null || sumRechargeBefore instanceof Integer? 0 : ((BigDecimal)sumRechargeBefore).longValue() / 100);

            agentRecharge.setType(WebConstants.MONEY_TYPE_REFUND);
            Map<String, Object> refundMap = agentRechargeService.getSumPrice(agentRecharge, startDay, endDay);
            Object sumRefundNow = refundMap.get("sumNow");
            Object sumRefundBefore = refundMap.get("sumBefore");
            respMap.put("sumRefundNow", refundMap == null || sumRefundNow instanceof Integer ? 0 : ((BigDecimal)sumRefundNow).longValue() /100);
            respMap.put("sumRefundBefore", refundMap == null  || sumRefundBefore instanceof Integer ? 0 : ((BigDecimal)sumRefundBefore).longValue() /100);


            Map<String, Object> costMap = companyService.getSumCost(agentRecharge.getAgentId(), startDay, endDay);
            respMap.put("sumCostNow", costMap == null ? 0 : (long)costMap.get("sumNow") /1000 /100);
            respMap.put("sumCostBefore", costMap == null ? 0 : (long)costMap.get("sumBefore") /1000 /100);

            result = JsonUtils.STATUS_OK(respMap);

        } catch (Exception e) {
            logger.info("get agent recharge sum Price error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
