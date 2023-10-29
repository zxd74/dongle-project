package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.CompanyMapper;
import com.iwanvi.nvwa.dao.QuotaCustMapper;
import com.iwanvi.nvwa.dao.QuotaDidMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.CompanyExample;
import com.iwanvi.nvwa.dao.model.QuotaCust;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.web.service.QuotaCustService;
import com.iwanvi.nvwa.web.service.UserGrandService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;

@Service
public class QuotaCustServiceImpl implements QuotaCustService {

	@Autowired
	private QuotaCustMapper quotaCustMapper;
	@Autowired
	private UserGrandService userGrandService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private QuotaDidMapper quotaDidMapper;
	@Autowired
	private RedisDao redisDao;

	private static final String COM_DISPLAY_KEY = "cust:hour:display:{0}:{1}:{2}";
	private static final String COM_CLICK_KEY = "cust:hour:click:{0}:{1}:{2}";
	private static final String COM_COST_KEY = "cust:hour:cost:{0}:{1}:{2}";

	@Override
	public List<QuotaCust> selectQuotaCustByTypeAndDay(Integer type, String sDay, String eDay, Integer uid) {
		if (type != null && StringUtils.isNotBlank(sDay) && StringUtils.isNotBlank(eDay)) {
			List<String> uuids = Lists.newArrayList();
			if (uid != null) {
				if (WebConstants.COMPANY_TYPE_ADVER == type) {

					List<Integer> custIds = null;
					if (!userService.isAdmin(uid)) {
						custIds = userGrandService.getIdListByUidAndType(uid, WebConstants.AGENT_OBJECT_TYPE_ADVER);
						if (!CollectionUtils.isEmpty(custIds)) {
							CompanyExample example = new CompanyExample();
							example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andIdIn(custIds);
							List<Company> companies = companyMapper.selectByExample(example);
							uuids = Lists.transform(companies, Company::getUuid);
						} else {
//							throw new ServiceException("没有找到授权客户");
							return null;
						}
					}
				} else if (WebConstants.COMPANY_TYPE_AGENT == type) {
					if (!userService.isAdmin(uid)) {
						User user = userMapper.selectByPrimaryKey(uid);
						Company company = companyMapper.selectByPrimaryKey(user.getCompany());
						uuids.add(company.getUuid());
					}
				}
			} else {
				throw new ServiceException("用户未登录");
			}
			List<QuotaCust> quotaCustList = quotaCustMapper.selectByTypeAndDay(type, sDay, eDay, uuids);
            Map<Integer, QuotaCust> quotaCustMap = Maps.newHashMap();
			quotaCustList.forEach(quota -> {
			    quotaCustMap.put(quota.getCreDay(), quota);
            });

            int endDay = NumberUtils.toInt(eDay);
            int today = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));

            if (today <= endDay) {
                CompanyExample example = new CompanyExample();
                example.createCriteria().andTypeEqualTo(type);
                List<Company> companyList = companyMapper.selectByExample(example);
                String hour = DateUtils.format(new Date(), DateUtils.FORMAT_HOUR);
                Integer display = 0;
                Integer click = 0;
                Long cost = 0l;
                for (Company company : companyList) {
                    String impkey = StringUtils.buildString(COM_DISPLAY_KEY, company.getUuid(), today, hour);
                    String clkkey = StringUtils.buildString(COM_CLICK_KEY, company.getUuid(), today, hour);
                    String costkey = StringUtils.buildString(COM_COST_KEY, company.getUuid(), today, hour);
                    display += NumberUtils.toInt(redisDao.get(impkey));
                    click += NumberUtils.toInt(redisDao.get(clkkey));
                    cost += NumberUtils.toLong(redisDao.get(costkey));
                }
                if (quotaCustMap.get(today) == null) {
                    quotaCustMap.put(today, new QuotaCust(today, display, click, cost));
                } else {
                    QuotaCust cust = quotaCustMap.get(today);
                    cust.setExp(cust.getExp() + display);
                    cust.setClk(cust.getClk() + click);
                    cust.setCost(cust.getCost() + cost);
                }
            }

            List<QuotaCust> resultList = Lists.newArrayList();
            for (Date curDate = DateUtils.parse(sDay, DateUtils.SHORT_FORMAT);
                 curDate.compareTo(DateUtils.parse(eDay, DateUtils.SHORT_FORMAT)) <= 0;
                 curDate = DateUtils.getNextDaysDate(curDate, 1)) {
                int i = Integer.parseInt(DateUtils.format(curDate, DateUtils.SHORT_FORMAT));
                if (quotaCustMap.containsKey(i)) {
                    resultList.add(quotaCustMap.get(i));
                } else {
                    resultList.add(new QuotaCust(i));
                }
            }
			return resultList;
		}
		return null;
	}

	@Override
	public List<QuotaCust> selectQuotaCustByDayAndCid(String sDay, String eDay, Integer cid) {
		if (StringUtils.isBlank(sDay) || StringUtils.isBlank(eDay) || cid == null) {
			return Collections.emptyList();
		}
		Company company = companyMapper.selectByPrimaryKey(cid);
		List<QuotaCust> quotaList = quotaCustMapper.selectByTypeAndDayAndUuid(sDay, eDay, company.getUuid());

		Map<Integer, QuotaCust> custQuotaMapper = new HashMap<>();
		quotaList.forEach(a -> {
			a.setCost(a.getCost());
			custQuotaMapper.put(a.getCreDay(), a);
		});

		int startDay = NumberUtils.toInt(sDay);
		int endDay = NumberUtils.toInt(eDay);
		int today = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));

		List<QuotaCust> resultQuotaList = new ArrayList<>();
		for (Date curDate = DateUtils.parse(sDay, DateUtils.SHORT_FORMAT);
             curDate.compareTo(DateUtils.parse(eDay, DateUtils.SHORT_FORMAT)) <= 0;
             curDate = DateUtils.getNextDaysDate(curDate, 1)) {
            int i = Integer.parseInt(DateUtils.format(curDate, DateUtils.SHORT_FORMAT));
			if (!custQuotaMapper.containsKey(i)) {
				resultQuotaList.add(new QuotaCust(i));
			}else {
				resultQuotaList.add(custQuotaMapper.get(i));
			}
		}

		if (endDay < today)
			return resultQuotaList;

		// 获取redis数据
		String hour = DateUtils.format(new Date(), DateUtils.FORMAT_HOUR);
        String impkey = StringUtils.buildString(COM_DISPLAY_KEY, company.getUuid(), today, hour);
        String clkkey = StringUtils.buildString(COM_CLICK_KEY, company.getUuid(), today, hour);
        String costkey = StringUtils.buildString(COM_COST_KEY, company.getUuid(), today, hour);

		List<String> results = redisDao.multiGet(Arrays.asList(impkey, clkkey, costkey));

		resultQuotaList.stream().filter(q -> q.getCreDay() == today).forEach(q -> {
			q.setExp(q.getExp() + NumberUtils.toInt(results.get(0)));
			q.setClk(q.getClk() + NumberUtils.toInt(results.get(1)));
			q.setCost(q.getCost() + NumberUtils.toLong(results.get(2)));
		});
		return resultQuotaList;
	}
}
