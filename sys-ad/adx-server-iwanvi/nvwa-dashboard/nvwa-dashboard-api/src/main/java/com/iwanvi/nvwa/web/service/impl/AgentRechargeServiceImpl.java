package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AgentRechargeMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.AgentRecharge;
import com.iwanvi.nvwa.dao.model.AgentRechargeExample;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.*;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * class 代理商充值
 *
 * @author hao
 * @date 2018/10/22.
 */
@Service
public class AgentRechargeServiceImpl implements AgentRechargeService {

    @Autowired
    private AgentRechargeMapper agentRechargeMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private RedisDao redisDao;
    @Autowired
    private SyslogService syslogService;
    @Autowired
    private SysCrontabService sysCrontabService;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void add(AgentRecharge agentRecharge, Integer userType) {
        if (agentRecharge != null) {
            Company company = companyService.get(agentRecharge.getAgentId());
            String BALANCE_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_TOTAL, company.getUuid());
            long price = Long.parseLong(agentRecharge.getRechargePrice()) * 100 * 1000;
            if (agentRecharge.getType().equals(WebConstants.MONEY_TYPE_REFUND)) {
                price = -price;
            }
            redisDao.incr(BALANCE_KEY,price);
            agentRecharge.setCreateTime(new Date());
            //设置创建日期
            String date = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            agentRecharge.setCreateDay(Integer.parseInt(date));
            agentRecharge.setPrice(Long.parseLong(agentRecharge.getRechargePrice()) * 100);
            agentRechargeMapper.insert(agentRecharge);
            if (Constants.STATE_INVALID.equals(company.getBalanceStatus())) {
                company.setBalanceStatus(Constants.STATE_VALID);
                companyService.update(company);
                sysCrontabService.addSysCrontab(company.getId(), Constants.OBJ_AGENT, Constants.OP_UPDATE);
            }
            syslogService.addSyslog(agentRecharge.getCreateUser(),userType,agentRecharge,this.getClass().getName(),"rechargeAgent");
        }
    }

    @Override
    public List<AgentRecharge> selectForList(AgentRecharge agentRecharge, String startDay, String endDay) {
        AgentRechargeExample example = bindingExample(agentRecharge, startDay, endDay);
        return agentRechargeMapper.selectByExample(example);
    }

    @Override
    public Page<AgentRecharge> selectForPage(AgentRecharge agentRecharge, String startDay, String endDay, Integer cp, Integer ps) {
        Page<AgentRecharge> page;
        AgentRechargeExample example = bindingExample(agentRecharge,startDay,endDay);
        int count = agentRechargeMapper.countByExample(example);
        List<AgentRecharge> list ;
        if (cp != null && ps != null) {
            page = new Page<AgentRecharge>(count, cp, ps);
        } else {
            page = new Page<AgentRecharge>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = agentRechargeMapper.selectByExample(example);
        for (AgentRecharge recharge : list) {
            recharge.setPrice(recharge.getPrice()/100);
            String operateUser = userMapper.selectByPrimaryKey(recharge.getCreateUser()).getUserName();
            if (StringUtils.isNotBlank(operateUser)) {
                recharge.setOperateUser(operateUser);
            }
        }
        page.bindData(list);
        return page;
    }

    @Override
    public Map<String,Object> sumByDate(AgentRecharge agentRecharge, String startDay, String endDay) {

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
        map.put("agentId", agentRecharge.getAgentId());
        map.put("type", agentRecharge.getType());
        map.put("startDay", startDay);
        map.put("endDay", endDay);
        List<AgentRecharge> list = agentRechargeMapper.sumByAgentAndDay(map);
        List<Integer> priceList = Lists.newArrayList();
        for (int i = 0; i < dateList.size(); i++) {
            priceList.add(0);
        }
        if (!CollectionUtils.isEmpty(list)) {
            for (AgentRecharge recharge1 : list) {
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
    public Map<String, Object> getSumPrice(AgentRecharge agentRecharge, String startDay, String endDay) {
        //查询充值总额
        Map<String,Object> paramMap = Maps.newHashMap();
        paramMap.put("agentId", agentRecharge.getAgentId());
        paramMap.put("type", agentRecharge.getType());
        paramMap.put("startDay", startDay);
        paramMap.put("endDay", endDay);
        Map<String, Object> sumNow = agentRechargeMapper.sumPriceByDate(paramMap);

        Date startDate = DateUtils.parse(startDay, DateUtils.SHORT_FORMAT);
        Date endDate = DateUtils.parse(endDay, DateUtils.SHORT_FORMAT);
        Long countDate = DateUtils.getDValue2Day(startDate, endDate);
        Date startDateBefore = DateUtils.getPreDaysDate(startDate, countDate.intValue());
        Date endDateBefore = DateUtils.getPreDaysDate(startDate, Constants.INTEGER_1);

        paramMap.put("startDay", DateUtils.format(startDateBefore, DateUtils.SHORT_FORMAT));
        paramMap.put("endDay", DateUtils.format(endDateBefore, DateUtils.SHORT_FORMAT));
        Map<String, Object> sumBefore = agentRechargeMapper.sumPriceByDate(paramMap);

        Map<String, Object> returnMap = Maps.newHashMap();
        returnMap.put("sumNow", sumNow == null ? 0 : sumNow.get("sumPrice"));
        returnMap.put("sumBefore",sumBefore == null ? 0 :sumBefore.get("sumPrice"));

        return returnMap;
    }

    /**
     * 绑定example
     */
    private AgentRechargeExample bindingExample(AgentRecharge agentRecharge, String startDay, String endDay) {
        AgentRechargeExample example = new AgentRechargeExample();
        AgentRechargeExample.Criteria criteria = example.createCriteria();
        if (agentRecharge.getAgentId() != null) {
            criteria.andAgentIdEqualTo(agentRecharge.getAgentId());
        }
        if (agentRecharge.getType() != null) {
            criteria.andTypeEqualTo(agentRecharge.getType());
        }
        if (startDay != null && endDay != null) {
//            criteria.andCreateDayGreaterThanOrEqualTo(Integer.parseInt(startDay));
//            criteria.andCreateDayLessThanOrEqualTo(Integer.parseInt(endDay));
            criteria.andCreateDayBetween(Integer.parseInt(startDay), Integer.parseInt(endDay));
        }
        return example;
    }
}
