package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.QuotaCust;
import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.service.QuotaCustService;
import com.iwanvi.nvwa.web.service.UserGrandService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("company")
public class CompanyController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGrandService userGrandService;
    @Autowired
    private QuotaCustService quotaCustService;


    @RequestMapping("list")
    @ResponseBody
    public String list(Company company, Integer cp, Integer ps, HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (uid != null) {
                if (!userService.isAdmin(uid)) {
                    company.setCreateUser(uid);
                }
                Page<Company> page = companyService.selectForPage(company, cp, ps);
                result = JsonUtils.STATUS_OK(page);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
            }
        } catch (Exception e) {
            logger.error("list company error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("get")
    @ResponseBody
    public String get(Integer id) {
        String result = null;
        try {
            if (id != null) {
                Company company = companyService.get(id);
                result = JsonUtils.STATUS_OK(company);
            } else {
                result = JsonUtils.PARAMETER_ERROR();
            }
        } catch (Exception e) {
            logger.error("get company error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(@RequestBody Company company, HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (company != null) {
                company.setCreateUser(uid);
                company.setCreateTime(new Date());
                companyService.add(company);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("add company error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("update")
    @ResponseBody
    public String update(@RequestBody Company company) {
        String result = null;
        try {
            if (company != null) {
                companyService.update(company);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("update company error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public String delete(Integer id) {
        String result = null;
        try {
            if (id != null) {
                companyService.delete(id);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("delete company error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("getGrand")
    @ResponseBody
    public String getGrand(UserGrand grand) {
        String result = null;
        try {
            if (grand != null) {
                grand.setType(WebConstants.AGENT_OBJECT_TYPE_ADVER);
                List<UserGrand> list = userGrandService.selectForList(grand);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("get user grand error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("grand")
    @ResponseBody
    public String grand(UserGrand grand, HttpServletRequest request) {
        String result = null;
        try {
            Integer uid = getUserId(request);
            if (grand != null) {
                grand.setType(WebConstants.AGENT_OBJECT_TYPE_ADVER);
                grand.setCreateTime(new Date());
                grand.setCreateUser(uid);
                userGrandService.add(grand);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("get user grand error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("moveGrand")
    @ResponseBody
    public String moveGrand(Integer id) {
        String result = null;
        try {
            if (id != null) {
                userGrandService.deleteById(id);
                result = JsonUtils.STATUS_OK();
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("move user grand error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("quotaByDay")
    @ResponseBody
    public String quotaByDay(Integer type, String sDay, String eDay, HttpServletRequest request) {
        String result = null;
        try {
            if (type != null && StringUtils.isNotBlank(sDay) && StringUtils.isNotBlank(eDay)) {
                Integer uid = getUserId(request);
                List<QuotaCust> list = quotaCustService.selectQuotaCustByTypeAndDay(type, sDay, eDay, uid);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("quota by day error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @RequestMapping("quotaByUser")
    @ResponseBody
    public String quotaByUser(Integer uid, String sDay, String eDay) {
        String result = null;
        try {
            if (uid != null && StringUtils.isNotBlank(sDay) && StringUtils.isNotBlank(eDay)) {
                List<QuotaCust> list = quotaCustService.selectQuotaCustByDayAndCid(sDay, eDay, uid);
                result = JsonUtils.STATUS_OK(list);
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("quota by user error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("getBalance")
    public String getBalance(Integer id) {
        String result = null;
        try {
            if (id != null) {
                Map<String, Object> balanceMap = companyService.getBalance(id);
				if (balanceMap != null && !balanceMap.isEmpty()) {
					result = JsonUtils.STATUS_OK(balanceMap);
					logger.info("getBalance success, id: {}, realMoney: {}, virtualMoney: {}", id,
							JsonUtils.TO_JSON(balanceMap));
				} else {
					result = JsonUtils.STATUS_FAILED();
					logger.error("getBalance failed, balanceMap is null.");
				}
            } else {
                result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
            }
        } catch (Exception e) {
            logger.error("get balance error ",e);
            result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
