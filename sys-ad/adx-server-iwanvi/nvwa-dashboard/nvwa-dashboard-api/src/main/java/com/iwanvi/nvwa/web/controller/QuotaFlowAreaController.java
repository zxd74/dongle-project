package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.QuotaFlowArea;
import com.iwanvi.nvwa.web.service.QuotaFlowAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("qa")
public class QuotaFlowAreaController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(QuotaFlowAreaController.class);
    @Autowired
    private QuotaFlowAreaService quotaFlowAreaService;

    @RequestMapping("sumByDay")
    @ResponseBody
    public String sumByDay(Integer sDate, Integer eDate, String areas) {
        String result = null;
        try {
            Long start = System.currentTimeMillis();
            List<QuotaFlowArea> list = quotaFlowAreaService.sumByDay(sDate, eDate, areas);
            logger.info("sum by day use time: {}", System.currentTimeMillis() - start);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum area report by day error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }


    @RequestMapping("detail")
    @ResponseBody
    public String detailReport(Integer sDate, Integer eDate, String area) {
        String result = null;
        try {
            List<QuotaFlowArea> list = quotaFlowAreaService.detailReport(sDate, eDate, area);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("detail area report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }
}
