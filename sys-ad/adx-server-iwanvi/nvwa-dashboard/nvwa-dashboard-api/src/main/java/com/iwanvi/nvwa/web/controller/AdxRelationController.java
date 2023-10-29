package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.web.service.AdxRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("relation")
public class AdxRelationController {
    private static final Logger logger = LoggerFactory.getLogger(AdxRelationController.class);

    @Autowired
    private AdxRelationService adxRelationService;


    @RequestMapping("add")
    @ResponseBody
    public String add(AdxRelation adxRelation) {
        String result = null;
        try {
            if (adxRelation != null) {
                adxRelationService.add(adxRelation);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
            }
        } catch (Exception e) {
            logger.error("add adxrelation error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
