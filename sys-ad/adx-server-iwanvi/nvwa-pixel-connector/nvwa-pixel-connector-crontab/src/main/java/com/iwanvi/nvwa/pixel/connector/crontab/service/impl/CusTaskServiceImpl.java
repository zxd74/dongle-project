package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.QuotaCustMapper;
import com.iwanvi.nvwa.dao.model.QuotaCust;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

import ai.houyi.dorado.rest.server.DoradoServer;
import ai.houyi.dorado.rest.server.DoradoServerBuilder;
import ai.houyi.dorado.spring.SpringContainer;

@Service("cusTaskService")
public class CusTaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("CusTaskService");

	@Resource
	private RedisDao redisDao;
	
	@Autowired
	private QuotaCustMapper quotaCustMapper;
	
	/**
	 * 广告主代理商数据入库
	 * @param paramMap 单元ID string
	 */
	public void quotaTask(Map<String, Object> paramMap) {
		try {
			LOG.info("===================cusTaskService===================");
			List<Map<String, Object>> custList = quotaCustMapper.getCustList(paramMap);
			if(custList == null || custList.isEmpty()) {
				LOG.warn("quotaTask custList is null. paramMap: {}", paramMap.toString());
				return;
			}
			Set<String> uuidAderSet = new HashSet<>();
			Set<String> uuidAgentSet = new HashSet<>();
			for(Map<String, Object> map : custList) {
				uuidAderSet.add(NvwaUtils.obj2Empty(map.get("aderId")));
				uuidAgentSet.add(NvwaUtils.obj2Empty(map.get("agentId")));
			}
			for (String uuid : uuidAderSet) {
				saveCustTask(uuid, 2);
			}
			for (String uuid : uuidAgentSet) {
				saveCustTask(uuid, 1);
			}
			
		} catch (Exception e) {
			LOG.error("quotaTask error. ", e);
		}
	}
	
	/**
	 * 广告主、代理商报表数据入库
	 * @param uuid, type, 1:代理商,2:广告主
	 */
	@Transactional(rollbackFor = Exception.class)
	private void saveCustTask(String uuid, int type) {
		try {
			int exposureCnt, clickCnt = 0;
			long cost = 0;

			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String day = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(preHourDate, DateUtils.FORMAT_HOUR);
			
			List<String> keys = new ArrayList<>();
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, 
					Constants.QUOTA_DISPLAY, uuid, day, hour)); // exposureCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, 
					Constants.QUOTA_CLICK, uuid, day, hour)); // clickCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CUST_QUOTA_HOUR, 
					Constants.QUOTA_COST, uuid, day, hour)); // cost
			
			List<String> values = redisDao.multiGet(keys);
			exposureCnt = NvwaUtils.obj2int(values.get(0));
			clickCnt = NvwaUtils.obj2int(values.get(1));
			cost = NvwaUtils.obj2long(values.get(2));
			
			keys.clear(); keys = null;
			values.clear(); values = null;
			
			long temp = exposureCnt + clickCnt + cost;
			if(temp == Constants.INTEGER_0){
				LOG.warn("saveCustTask zero. uuid: {}, day: {}, hour: {}", uuid, day, hour);
				return;
			}
			
			QuotaCust quotaCust = new QuotaCust();
			quotaCust.setClk(clickCnt);
			quotaCust.setExp(exposureCnt);
			quotaCust.setCost(cost);
			quotaCust.setUuid(uuid);
			quotaCust.setType(type);
			quotaCust.setCreDay(NvwaUtils.obj2int(day));
			quotaCust.setCreHour(NvwaUtils.obj2int(hour, -1));
			
			int result = quotaCustMapper.insertSelective(quotaCust);
			if(result <= 0){
				LOG.warn("saveCustTask exception. uuid: {}, day: {}, hour: {}", uuid, day, hour);
			}
			
			LOG.info("saveCustTask end. uuid: {}, day: {}, hour: {}, quotaEntity: {}", uuid, day, hour, JsonUtils.TO_JSON(quotaCust));
		} catch (Exception e) {
			LOG.error("saveCustTask error. ", e);
		}
	}
	public void quotaTaskForDay() {
		
	}
	
	public static void main(String[] args) {
		DoradoServer server = DoradoServerBuilder.forPort(9320)
				.scanPackages("com.nvwa").springOn().build();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SpringContainer.create(applicationContext);
		
		TaskService cusTaskService = (TaskService) applicationContext.getBean("cusTaskService");
		cusTaskService.quotaTask(null);
		
		server.start();
	}
}
