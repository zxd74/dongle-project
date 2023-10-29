package com.iwanvi.nvwa.web.controller;


import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.DmpJudge;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.DmpJudgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("judge")
public class DmpJudgeController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DmpJudgeController.class);

    @Autowired
    private DmpJudgeService DmpJudgeService;

    @RequestMapping("list")
    @ResponseBody
    public String list(DmpJudge judge, Integer cp, Integer ps) {
        String result = null;
        try {
            Page<DmpJudge> page = DmpJudgeService.selectForPage(judge, cp, ps);
            result = JsonUtils.STATUS_OK(page);
        } catch (Exception e) {
            logger.error("list judge error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody DmpJudge judge, HttpServletRequest request) {
        String result = null;
        try {
            if (judge != null) {
                Integer uid = getUserId(request);
                judge.setCreateUser(uid);
                judge.setCreateTime(new Date());
                DmpJudgeService.add(judge);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("add judge error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody DmpJudge judge) {
        String result = null;
        try {
            if (judge != null) {
                DmpJudgeService.update(judge);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("update judge error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @RequestMapping("drop")
    @ResponseBody
    public String drop(Integer id) {
        String result = null;
        try {
            if (id != null) {
                DmpJudgeService.dropJudge(id);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("drop judge error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
