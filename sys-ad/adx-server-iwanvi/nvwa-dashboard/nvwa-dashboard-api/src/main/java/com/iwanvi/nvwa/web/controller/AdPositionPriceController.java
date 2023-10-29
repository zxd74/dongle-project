package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AdPositionPrice;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionPriceService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("adPositionPrice")
public class AdPositionPriceController {
    private static final Logger logger = LoggerFactory.getLogger(AdPositionPriceController.class);

    @Autowired
    private AdPositionPriceService adPositionPriceService;

    @ResponseBody
    @RequestMapping("add")
    public String add(AdPositionPrice adPositionPrice, Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (adPositionPrice != null) {
                boolean priceExist = adPositionPriceService.checkPriceExist(adPositionPrice);
                if (priceExist) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("adPositionPrice  already exist ！, name:{}", adPositionPrice.getPositionId());
                } else {
                    adPositionPrice.setUpdateUser(uid);
                    adPositionPriceService.add(adPositionPrice);
                    result = JsonUtils.STATUS_OK();
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("insert adPositionPrice error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("update")
    public String update(AdPositionPrice adPositionPrice, Integer uid) {
        String result = StringUtils.EMPTY;
        try {
            if (adPositionPrice != null) {
                boolean priceExist = adPositionPriceService.checkPriceExist(adPositionPrice);
                if (priceExist) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("adPositionPrice  already exist ！, name:{}", adPositionPrice.getPositionId());
                } else {
                    adPositionPrice.setUpdateUser(uid);
                    adPositionPriceService.update(adPositionPrice);
                    result = JsonUtils.STATUS_OK();
                }
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("update adPositionPrice error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @NvwaRespBody
    @PostMapping("update-price")
    public void updatePrice(@RequestBody  AdPositionPrice adPositionPrice, Integer uid) {
        adPositionPrice.setUpdateUser(uid);
        adPositionPriceService.update(adPositionPrice);
    }

    @ResponseBody
    @RequestMapping("get")
    public String get(Integer id) {
        String result = StringUtils.EMPTY;
        try {
            if (id != null) {
                AdPositionPrice adPositionPrice = adPositionPriceService.get(id);
                result = JsonUtils.STATUS_OK(adPositionPrice);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("get adPositionPrice error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getList")
    public String get(AdPositionPrice adPositionPrice, Integer cp, Integer ps) {
        String result = StringUtils.EMPTY;
        try {
            if (adPositionPrice != null) {
                Page<AdPositionPrice> page = adPositionPriceService.selectForPage(adPositionPrice,cp,ps);
                result = JsonUtils.STATUS_OK(page);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.info("getList adPositionPrice error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("addIndustry")
    public String addIndustry(String industryName) {
        String result = StringUtils.EMPTY;
        try {
            if (StringUtils.isNotBlank(industryName)) {
                boolean industryExist = adPositionPriceService.checkIndustryExist(industryName);
                if (industryExist) {
                    result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_FAILED_DUPNAME);
                    logger.info("industryName  already exist ！, name:{}", industryName);
                } else {
                    adPositionPriceService.addIndustry(industryName);
                    result = JsonUtils.STATUS_OK();
                }
            }
        } catch (Exception e) {
            logger.info("add industry name  error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("deleteIndustry")
    public String deleteIndustry(Integer industryId) {
        String result = StringUtils.EMPTY;
        try {
            if (industryId != null) {
                adPositionPriceService.deleteIndustry(industryId);
                logger.info("delete industry  success ！, industryId:{}", industryId);
                result = JsonUtils.STATUS_OK();
            }
        } catch (Exception e) {
            logger.info("delete industry   error ! ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
