package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.QuotaBook;
import com.iwanvi.nvwa.dao.model.QuotaFlow;
import com.iwanvi.nvwa.web.service.QuotaBookService;
import com.iwanvi.nvwa.web.service.QuotaFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quotaFlow")
public class QuotaFlowController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(QuotaFlowController.class);
    @Autowired
    private QuotaFlowService quotaFlowService;


    @RequestMapping("sumByDay")
    @ResponseBody
    public String sumByDay(String flowId, String appids, String adpids, String channels1, String channels2,
                           String versions, Integer sDate, Integer eDate) {
        String result = null;
        try {
            Long start = System.currentTimeMillis();
            List<QuotaFlow> list = quotaFlowService.sumWithDate(flowId, appids, adpids, channels1, channels2, versions,
                    sDate, eDate);
            logger.info("sum by day use time: {}", System.currentTimeMillis() - start);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum flow report by day error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("sum")
    @ResponseBody
    public String sumReport(String flowId, String appids, String adpids, String channels1, String channels2,
                            String versions, String group, Integer sDate, Integer eDate) {
        String result = null;
        try {
            Long start = System.currentTimeMillis();
            List<QuotaFlow> list = quotaFlowService.sumWithGroup(flowId, appids, adpids, channels1, channels2, versions,
                    group, sDate, eDate);
            logger.info("sum use time: {}", System.currentTimeMillis() - start);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum flow report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("detail")
    @ResponseBody
    public String detailReport(String id, String flowId, String appids, String adpids, String channels1,
                               String channels2, String versions, String group, Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<QuotaFlow> list = quotaFlowService.detailReport(id, flowId, appids, adpids, channels1, channels2,
                    versions, group, sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("detail flow report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }
}
