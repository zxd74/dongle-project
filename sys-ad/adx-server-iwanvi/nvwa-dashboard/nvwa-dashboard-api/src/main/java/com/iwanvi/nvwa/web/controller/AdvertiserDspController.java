package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdvertiserDsp;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdvertiserDspService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("advertiserDsp")
public class AdvertiserDspController extends  BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AdvertiserDspController.class);

    @Autowired
    private AdvertiserDspService advertiserDspService;

    @ResponseBody
    @RequestMapping("add")
    public String add(AdvertiserDsp advertiserDsp) {
        String result = StringUtils.EMPTY;
        try {
            if (advertiserDsp == null) {
                result = JsonUtils.PARAMETER_ERROR();
            } else {
                boolean checkNameExist = advertiserDspService.checkNameExist(advertiserDsp);
                if (checkNameExist) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("advertiser name already exist ！, name:{}", advertiserDsp.getName());
                } else {
                    advertiserDspService.add(advertiserDsp);
                    result = JsonUtils.STATUS_OK();
                }
            }
        } catch (Exception e) {
            logger.info("insert advertiserDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(AdvertiserDsp advertiserDsp) {
        String result = StringUtils.EMPTY;
        try {
            if (advertiserDsp == null) {
                result = JsonUtils.PARAMETER_ERROR();
            } else {
                boolean checkNameExist = advertiserDspService.checkNameExist(advertiserDsp);
                if (checkNameExist) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("advertiser name already exist ！, name:{}", advertiserDsp.getName());
                } else {
                    advertiserDspService.update(advertiserDsp);
                    result = JsonUtils.STATUS_OK();
                }
            }
        } catch (Exception e) {
            logger.info("update advertiserDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getList")
    public String get(AdvertiserDsp advertiserDsp, Integer cp, Integer ps) {
        String result = StringUtils.EMPTY;
        try {
            if (advertiserDsp != null) {
                Page<AdvertiserDsp> page = advertiserDspService.selectForPage(advertiserDsp,cp,ps);
                result = JsonUtils.STATUS_OK(page);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("getList advertiserDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("get")
    public String get(Integer id) {
        String result = StringUtils.EMPTY;
        try {
            if (id != null) {
                AdvertiserDsp advertiserDsp = advertiserDspService.get(id);
                result = JsonUtils.STATUS_OK(advertiserDsp);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get advertiserDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("examines")
    public String examines(AdvertiserDsp advertiserDsp, @RequestAttribute Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (advertiserDsp != null) {
                advertiserDsp.setExaminesTime(new Date());
                advertiserDsp.setExaminesUser(uid);
                advertiserDspService.update(advertiserDsp);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("examines advertiserDsp error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
