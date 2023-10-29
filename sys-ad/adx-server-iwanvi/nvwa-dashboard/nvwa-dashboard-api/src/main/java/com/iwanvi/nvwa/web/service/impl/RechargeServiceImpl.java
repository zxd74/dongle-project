package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.RechargeMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.Recharge;
import com.iwanvi.nvwa.dao.model.RechargeExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.*;
import com.iwanvi.nvwa.web.util.CompanyCostVo;
import com.iwanvi.nvwa.web.util.ControllerException;
import com.iwanvi.nvwa.web.util.MatcherUtils;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * class
 *
 * @author hao
 * @date 2018/10/22.
 */
@Service
public class RechargeServiceImpl implements RechargeService {

    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private SysCrontabService sysCrontabService;
    @Autowired
    private SyslogService syslogService;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void recharge(Recharge recharge,Integer userType) {
        if (recharge != null) {
            Company company = companyService.get(recharge.getAdvertiserId());
            String BALANCE_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_TOTAL, company.getUuid());
            long price = Long.parseLong(recharge.getRechargePrice()) * 100 * 1000;
            if (recharge.getType().equals(WebConstants.MONEY_TYPE_REFUND)) {
                price = -price;
            }
            redisDao.incr(BALANCE_KEY,price);
            recharge.setCreateTime(new Date());
            recharge.setPrice(Long.parseLong(recharge.getRechargePrice()) * 100);
            String date = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            recharge.setCreateDay(Integer.parseInt(date));
            rechargeMapper.insert(recharge);
            if (Constants.STATE_INVALID.equals(company.getBalanceStatus())) {
                company.setBalanceStatus(Constants.STATE_VALID);
                companyService.update(company);
                sysCrontabService.addSysCrontab(company.getId(), Constants.OBJ_ADVERTISER, Constants.OP_UPDATE);
            }
            syslogService.addSyslog(recharge.getCreateUser(),userType,recharge,this.getClass().getName(),"rechargeAdvertiser");
        }
    }

    @Override
    public List<Recharge> selectForList(Recharge recharge, String startDay, String endDay) {
        RechargeExample example = bindingExample(recharge, startDay, endDay);
        return rechargeMapper.selectByExample(example);
    }

    @Override
    public Page<Recharge> selectForPage(Recharge recharge, String startDay, String endDay, Integer cp, Integer ps) {
        Page<Recharge> page;
        RechargeExample example = bindingExample(recharge,startDay,endDay);
        int count = rechargeMapper.countByExample(example);
        List<Recharge> list ;
        if (cp != null && ps != null) {
            page = new Page<Recharge>(count, cp, ps);
        } else {
            page = new Page<Recharge>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = rechargeMapper.selectByExample(example);
        for (Recharge recharge1 : list) {
            String operateUser = userMapper.selectByPrimaryKey(recharge1.getCreateUser()).getUserName();
            recharge1.setPrice(recharge1.getPrice()/100);
            if (StringUtils.isNotBlank(operateUser)) {
                recharge1.setOperateUser(operateUser);
            }
        }
        page.bindData(list);
        return page;

    }

    @Override
    public Map<String,Object> sumByDate(Recharge recharge, String startDay, String endDay) {

        Date startDate = DateUtils.parse(startDay, DateUtils.SHORT_FORMAT);
        Date endDate = DateUtils.parse(endDay, DateUtils.SHORT_FORMAT);
        if(DateUtils.getDValue2Day(startDate, endDate) < 0){
            return null;
        }
        List<String> dateList = Lists.newArrayList();
        for(Date curDate = new Date(startDate.getTime()); curDate.compareTo(endDate) <= 0; curDate = DateUtils.getNextDaysDate(curDate, 1)){
            dateList.add(DateUtils.format(curDate, DateUtils.SHORT_FORMAT));
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("advertiserId", recharge.getAdvertiserId());
        map.put("type", recharge.getType());
        map.put("startDay", startDay);
        map.put("endDay", endDay);
        List<Recharge> list = rechargeMapper.sumByAdvertiserAndDay(map);
        List<Integer> priceList = Lists.newArrayList();
        for (int i = 0; i < dateList.size(); i++) {
            priceList.add(0);
        }
        if (!CollectionUtils.isEmpty(list)) {
            for (Recharge recharge1 : list) {
                Date curDate = DateUtils.parse(String.valueOf(recharge1.getCreateDay()), DateUtils.SHORT_FORMAT);
                long index = DateUtils.getDValue2Day(startDate, curDate);
                priceList.set(new Long(index).intValue(), new Long(recharge1.getPrice()/100).intValue());
            }
        }
        Map<String,Object> respMap = Maps.newHashMap();
        respMap.put("date", dateList);
        respMap.put("money", priceList);
        return respMap;
    }

    @Override
    public Map<String, Object> getSumPrice(Recharge recharge, String startDay, String endDay) {
        //查询充值总额
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("advertiserId", recharge.getAdvertiserId());
        paramMap.put("type", recharge.getType());
        paramMap.put("startDay", startDay);
        paramMap.put("endDay", endDay);
        Map<String, Object> sumNow = rechargeMapper.sumPriceByDate(paramMap);

        Date startDate = DateUtils.parse(startDay, DateUtils.SHORT_FORMAT);
        Date endDate = DateUtils.parse(endDay, DateUtils.SHORT_FORMAT);
        Long countDate = DateUtils.getDValue2Day(startDate, endDate);
        Date startDateBefore = DateUtils.getPreDaysDate(startDate, countDate.intValue());
        Date endDateBefore = DateUtils.getPreDaysDate(startDate, Constants.INTEGER_1);

        paramMap.put("startDay", DateUtils.format(startDateBefore, DateUtils.SHORT_FORMAT));
        paramMap.put("endDay", DateUtils.format(endDateBefore, DateUtils.SHORT_FORMAT));
        Map<String, Object> sumBefore = rechargeMapper.sumPriceByDate(paramMap);

        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("sumNow", sumNow == null ? 0 : sumNow.get("sumPrice"));
        returnMap.put("sumBefore",sumBefore == null ? 0 : sumBefore.get("sumPrice"));

        return returnMap;
    }

    /**
     * 判断退款金额是否小于余额
     */
    @Override
    public boolean judgeBalance(Integer id, Double price) {
        boolean bool = false;
        Map<String,Object> balanceMap = companyService.getBalance(id);
        if (balanceMap != null) {
            Double balance = Double.parseDouble((balanceMap.get("balance") ).toString());
            if (balance - price >= 0) {
                bool = true;
            }
        }
        return bool;
    }

    @Override
    public Map<String, Object> reportForCost(Integer id, String startDay, String endDay,Integer cp, Integer ps) {
        Date startDate = DateUtils.parse(startDay, DateUtils.SHORT_FORMAT);
        Date endDate = DateUtils.parse(endDay, DateUtils.SHORT_FORMAT);
        if(DateUtils.getDValue2Day(startDate, endDate) < 0){
            return null;
        }
        List<String> dateList = Lists.newArrayList();
        List<Double> costList = Lists.newArrayList();
        List<CompanyCostVo>  respList = Lists.newArrayList();
        for(Date curDate = new Date(startDate.getTime()); curDate.compareTo(endDate) <= 0; curDate = DateUtils.getNextDaysDate(curDate, 1)){
            dateList.add(DateUtils.format(curDate, DateUtils.SHORT_FORMAT));
        }
        CompanyCostVo vo ;
        for (String date : dateList) {
            vo = new CompanyCostVo();
            double cost = getCost(id, date);
            costList.add(cost);
            vo.setCreateTime(date);
            vo.setPrice(cost);
            respList.add(vo);
        }
        Collections.reverse(respList);
        Map<String, Object> respMap = Maps.newHashMap();
        Page page;
        int count = respList.size();
        if (cp != null && ps != null) {
            page = new Page(count, cp, ps);
        } else {
            page = new Page(count);
        }
        //分页
        int startPosition = page.getStartPosition();
        if (startPosition + ps < respList.size()) {
            respList = respList.subList(startPosition, startPosition + ps);
        } else {
            respList = respList.subList(startPosition, respList.size());
        }
        page.bindData(respList);

        respMap.put("date", dateList);
        respMap.put("cost", costList);
        respMap.put("page", page);
        return respMap;
    }


    /**
     * 绑定example
     */
    private RechargeExample bindingExample(Recharge recharge, String startDay, String endDay) {
        RechargeExample example = new RechargeExample();
        RechargeExample.Criteria criteria = example.createCriteria();
        if (recharge.getAdvertiserId() != null) {
            criteria.andAdvertiserIdEqualTo(recharge.getAdvertiserId());
        }
        if (recharge.getType() != null) {
            criteria.andTypeEqualTo(recharge.getType());
        }
        if (startDay != null && endDay != null) {
            criteria.andCreateDayBetween(Integer.parseInt(startDay), Integer.parseInt(endDay));
        }
        example.setOrderByClause(" id desc");
        return example;
    }

    private Double getCost(Integer uid, String date) {
        Company company = companyMapper.selectByPrimaryKey(uid);
        double result;
        if (company != null) {
            String uuid = companyMapper.selectByPrimaryKey(uid).getUuid();
            String COST_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_COST, uuid, date);
            double cost = (redisDao.get(COST_KEY) == null ? 0 : Double.parseDouble(redisDao.get(COST_KEY)));
            cost = cost / 1000 / 100;
            result = (double) Math.round(cost * 100) / 100;
        } else {
            result = 0;
        }
        return result;
    }

    /**
     * 解析时间
     */
    /*private Date converStringToDate(String from) {
        if (StringUtils.isNotBlank(from)) {
            return DateUtils.parse(from, DateUtils.SHORT_FORMAT);
        }
        return null;
    }*/
}
