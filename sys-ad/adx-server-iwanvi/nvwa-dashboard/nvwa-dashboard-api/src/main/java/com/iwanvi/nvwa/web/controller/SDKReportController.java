package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.SdkReport;
import com.iwanvi.nvwa.web.service.SDKReportService;
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
@RequestMapping("sdkReport")
public class SDKReportController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(SDKReportController.class);

    @Autowired
    private SDKReportService sdkReportService;

    @RequestMapping("upload")
    @ResponseBody
    public String uploadReport(MultipartFile file, Integer type, HttpServletRequest request) {
        String result = null;
        try {
            if (file != null && type != null) {
                sdkReportService.importSDKReport(file, type);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("upload sdk report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("sumByDay")
    @ResponseBody
    public String sumByDay(String flowCons, String apps, String pids, String flowPosIds,
                           Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<SdkReport> list = sdkReportService.sumSDKReportWithDate(flowCons, apps, pids, flowPosIds,
                    sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum sdk report by day error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("sum")
    @ResponseBody
    public String sumReport(String flowCons, String apps, String pids, String flowPosIds,
                            String group, Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<SdkReport> list = sdkReportService.sumSDKReportWithGroup(flowCons, apps, pids, flowPosIds,
                    group, sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum sdk report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("detail")
    @ResponseBody
    public String detailReport(String id, String flowCons, String apps, String pids, String flowPosIds,
                               String group, Integer sDate, Integer eDate) {
        String result = null;
        try {
            List<SdkReport> list = sdkReportService.detailSDKReport(id, flowCons, apps, pids, flowPosIds,
                    group, sDate, eDate);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("detail sdk report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }
}
