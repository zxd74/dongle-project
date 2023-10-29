package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.AgentPrice;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AgentPriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("price")
public class AgentPriceController {
    private static final Logger logger = LoggerFactory.getLogger(AgentPriceController.class);

    @Autowired
    private AgentPriceService agentPriceService;

    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody AgentPrice agentPrice) {
        String result = null;
        try {
            if (agentPrice != null) {
                agentPriceService.add(agentPrice);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("add agent price error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody AgentPrice agentPrice) {
        String result = null;
        try {
            if (agentPrice != null) {
                agentPriceService.update(agentPrice);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("update agent price error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("list")
    @ResponseBody
    public String list(AgentPrice agentPrice, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<AgentPrice> page = agentPriceService.selectForPage(agentPrice, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list agent price error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("syncPrice")
    @ResponseBody
    public String syncPrice(Integer id) {
        String result = null;
        try {
            agentPriceService.syncPrice(id);
            result = JsonUtils.STATUS_OK();
        } catch (Exception e) {
            logger.error("sync agent price error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("syncProfit")
    @ResponseBody
    public String syncProfit(Integer id) {
        String result = null;
        try {
            agentPriceService.syncProfit(id);
            result = JsonUtils.STATUS_OK();
        } catch (Exception e) {
            logger.error("sync agent profit error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

}
