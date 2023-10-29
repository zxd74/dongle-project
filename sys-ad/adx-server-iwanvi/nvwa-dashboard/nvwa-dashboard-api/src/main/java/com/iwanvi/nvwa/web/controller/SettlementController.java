package com.iwanvi.nvwa.web.controller;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.SettlementReport;
import com.iwanvi.nvwa.web.service.SettlementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("settle")
public class SettlementController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SettlementController.class);

    @Autowired
    private SettlementService settlementService;

    @RequestMapping("upload")
    @ResponseBody
    public String uploadReport(MultipartFile file, HttpServletRequest request) {
        String result = null;
        try {
            if (file != null) {
                settlementService.importSettlementReport(file);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("upload settlement report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("sumByDay")
    @ResponseBody
    public String sumByDay(String flowCons, String apps, String pids, String flowPosIds, String channels,
                            Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<SettlementReport> list = settlementService.sumWithDate(flowCons, apps, pids, flowPosIds, channels,
                    sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum settlement report by day error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("sum")
    @ResponseBody
    public String sumReport(String flowCons, String apps, String pids, String flowPosIds, String channels,
                            String group, Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<SettlementReport> list = settlementService.sumWithGroup(flowCons, apps, pids, flowPosIds, channels
                    , group, sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum settlement report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("detail")
    @ResponseBody
    public String detailReport(String id, String flowCons, String apps, String pids, String flowPosIds, String channels,
                               String group, Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<SettlementReport> list = settlementService.detailReport(id, flowCons, apps, pids, flowPosIds, channels,
                    group, sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("detail settlement report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

}
