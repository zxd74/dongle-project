package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.QuotaBook;
import com.iwanvi.nvwa.web.service.QuotaBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quotaBook")
public class QuotaBookController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(QuotaBookController.class);
    @Autowired
    private QuotaBookService quotaBookService;


    @RequestMapping("sumByDay")
    @ResponseBody
    public String sumByDay(Integer sDate, Integer eDate, String level1s, String level2s,
                            String level3s) {
        String result = null;
        try {
            List<QuotaBook> list = quotaBookService.sumWithDate(sDate, eDate, level1s, level2s, level3s);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum book classify report by day error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("sum")
    @ResponseBody
    public String sumReport(Integer sDate, Integer eDate, String level1s, String level2s,
                            String level3s, String group) {
        String result = null;
        try {
            List<QuotaBook> list = quotaBookService.sumWithGroup(sDate, eDate, level1s, level2s, level3s, group);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("sum book classify report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }

    @RequestMapping("detail")
    @ResponseBody
    public String detailReport(Integer id, Integer sDate, Integer eDate, String level1s, String level2s,
                               String level3s, String group) {
        String result = null;
        try {
            List<QuotaBook> list = quotaBookService.detailReport(id, sDate, eDate, level1s, level2s, level3s, group);
            result = JsonUtils.STATUS_OK(list);
        } catch (Exception e) {
            logger.error("detail book classify report error", e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
        }
        return result;
    }
}
