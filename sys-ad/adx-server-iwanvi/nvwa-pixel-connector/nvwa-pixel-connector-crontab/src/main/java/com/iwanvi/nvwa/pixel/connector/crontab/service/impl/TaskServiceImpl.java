package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.EntityDspMapper;
import com.iwanvi.nvwa.dao.EntityMapper;
import com.iwanvi.nvwa.dao.OrderPutMapper;
import com.iwanvi.nvwa.dao.QuotaEntityMapper;
import com.iwanvi.nvwa.dao.QuotaPlanMapper;
import com.iwanvi.nvwa.dao.QuotaPutMapper;
import com.iwanvi.nvwa.dao.model.QuotaEntity;
import com.iwanvi.nvwa.dao.model.QuotaPlan;
import com.iwanvi.nvwa.dao.model.QuotaPut;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

import ai.houyi.dorado.spring.SpringContainer;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("TaskService");
	
	@Resource
	private RedisDao redisDao;
	
	@Resource
	private QuotaPutMapper quotaPutMapper;
	
	@Resource
	private QuotaPlanMapper quotaPlanMapper;
	
	@Resource
	private QuotaEntityMapper quotaEntityMapper;
	
	@Resource
	private OrderPutMapper orderPutMapper;
	
	@Resource
	private EntityMapper entityMapper;
	
	@Resource
	private EntityDspMapper entityDspMapper;
	
	@Resource
	private TaskService cusTaskService;
	
	@Resource
	private TaskService orderTaskService;
	
	public void quotaTaskForDay() {
		
	}
	public void quotaTask() {
		try {
			Integer adsCnt = entityMapper.getAdsCnt();
			if(adsCnt == null || adsCnt == Constants.INTEGER_0){
				LOG.warn("quotaTask exception. adsCnt is 0.");
				return;
			}
			LOG.info("===================taskService===================");

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("limit", 100);
			Integer pageTotal = (adsCnt % 100 == 0) ? (adsCnt / 100) : (adsCnt / 100 + 1);
			List<Integer> adIds = new ArrayList<>();
			Set<Integer> orderCids = new HashSet<>();
			Set<Integer> putIds = new HashSet<>();
			List<Map<String, Object>> tempList = null;
			for (int i = 0; i < pageTotal; i++) {
				paramMap.put("offset", i);
				tempList = entityMapper.getAdIds(paramMap);
				if(tempList == null || tempList.isEmpty()){
					continue;
				}
				for(Map<String, Object> entityMap : tempList) {
					putIds.add(NvwaUtils.obj2int(entityMap.get("putId")));
					adIds.add(NvwaUtils.obj2int(entityMap.get("id")));
				}
			}
			if(adIds.isEmpty()){
				LOG.warn("saveQuotaTask exception. adIds is null.");
			} else {
				LOG.info("saveQuotaTask adsCnt: {}, adIds size: {}", adsCnt, adIds.size());
				String creativeIdStr = org.apache.commons.lang3.StringUtils.join(adIds, Constants.SIGN_COMMA);
				paramMap.clear();
				paramMap.put("ids", creativeIdStr);
				List<Map<String, Object>> idInfoList = quotaEntityMapper.getIdInfoByCids(paramMap);
				if(idInfoList == null || idInfoList.isEmpty()) {
					LOG.warn("quotaTask idInfoList is null. adsCnt: {}", adsCnt);
				} else {
					for(Map<String, Object> map : idInfoList) {
						saveCreativeTask(map, NvwaUtils.obj2int(map.get("puType")) == 17); // putType=17 订单
						orderCids.add(NvwaUtils.obj2int(map.get("oid")));
					}
				}
			}

			// 订单数据入库
			if (orderCids.isEmpty()) {
				LOG.warn("saveQuotaTask exception. orderCids is null.");
			} else {
				// 广告主代理商数据入库
				String orderIdStr = org.apache.commons.lang3.StringUtils.join(orderCids, Constants.SIGN_COMMA);
				paramMap.clear();
				paramMap.put("ids", orderIdStr);
				
				LOG.info("orderIdStr==============={}", orderIdStr);
				
				cusTaskService.quotaTask(paramMap);
				
				for (Integer orderCid : orderCids) {
					saveOrderCreativeTask(orderCid);
				}
			}
			if(putIds.isEmpty()){
				LOG.warn("saveQuotaTask exception. putIds is null.");
			} else {
				String putIdStr = org.apache.commons.lang3.StringUtils.join(putIds, Constants.SIGN_COMMA);
				paramMap.clear();
				paramMap.put("ids", putIdStr);
				
				LOG.info("putIdStr==============={}", putIdStr);
				List<Map<String, Object>> orderPutInfoList = orderPutMapper.getIdInfoByPutIds(paramMap);
				
				if(orderPutInfoList == null || orderPutInfoList.isEmpty()) {
					LOG.warn("quotaTask orderPutInfoList is null. adsCnt: {}", putIdStr);
				} else {
					for(Map<String, Object> map : orderPutInfoList) {
						orderTaskService.quotaTask(map);
					}
				}
			}
		} catch (Exception e) {
			LOG.error("quotaTask error. ", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveCreativeTask(Map<String, Object> map, boolean isOrder) {
		try {
			int cid = NvwaUtils.obj2int(map.get("cid"));
			if (cid == 0) {
				LOG.warn("saveCreativeTask error. map: {}", map.toString());
				return;
			}
			
			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String dayStr = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			String hourStr = DateUtils.format(preHourDate, DateUtils.FORMAT_HOUR);
			
			int exposureCnt, clickCnt, bidCnt, winCnt = 0;
			long cost = 0;
			long agentCost = 0;
			int day = NvwaUtils.obj2int(dayStr);
			
			List<String> keys = new ArrayList<>();
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_DISPLAY, cid, day, hourStr)); // exposureCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_CLICK, cid, day, hourStr)); // clickCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_COST, cid, day, hourStr)); // cost
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_BID, cid, day, hourStr)); // bid
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_WIN, cid, day, hourStr)); // win
			
			List<String> values = redisDao.multiGet(keys);
			LOG.info("saveCreativeTask values: {}", values);
			exposureCnt = NvwaUtils.obj2int(values.get(0));
			clickCnt = NvwaUtils.obj2int(values.get(1));
			cost = NvwaUtils.obj2long(values.get(2));
			bidCnt = NvwaUtils.obj2int(values.get(3));
			winCnt = NvwaUtils.obj2int(values.get(4));
			
			keys.clear(); keys = null;
			values.clear(); values = null;
			
			long temp = exposureCnt + clickCnt + cost + bidCnt + winCnt + agentCost;
			if(temp == Constants.INTEGER_0){
				LOG.warn("saveCreativeTask zero. cid: {}, day: {}, hour: {}", cid, day, hourStr);
				return;
			}
			
			int hour = NvwaUtils.obj2int(hourStr, -1);
			QuotaEntity quotaEntity = new QuotaEntity();
			quotaEntity.setBid(bidCnt);
			quotaEntity.setWin(winCnt);
			quotaEntity.setClk(clickCnt);
			quotaEntity.setCost(cost);
			quotaEntity.setExp(exposureCnt);
			quotaEntity.setReq(Constants.INTEGER_0);
			quotaEntity.setCreDay(day);
			quotaEntity.setCreHour(hour);
			quotaEntity.setEntId(cid);
			quotaEntity.setAgentCost(agentCost);
			
			int result = quotaEntityMapper.insertSelective(quotaEntity);
			if(result <= 0){
				LOG.warn("saveCreativeTask exception. cid: {}, day: {}, hour: {}", cid, day, hour);
			}
			
			LOG.info("saveCreativeTask end. day: {}, hour: {}, quotaEntity: {}", day, hour, JsonUtils.TO_JSON(quotaEntity));

			if (isOrder) {
				return;
			}
			saveUnitTask(map, exposureCnt, clickCnt, cost, bidCnt, winCnt, agentCost, day, hour);
		} catch (Exception e) {
			LOG.error("saveCreativeTask error. ", e);
		}
	}
	@Transactional(rollbackFor = Exception.class)
	private void saveOrderCreativeTask(int cid) {
		try {
			if (cid == 0) {
				LOG.warn("saveOrderCreativeTask error. cid is null.");
				return;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("cid", cid);
			
			LOG.info("saveOrderCreativeTask cid: {}", cid);
			saveCreativeTask(map, true);
		} catch (Exception e) {
			LOG.error("saveOrderCreativeTask error. ", e);
		}
	}
	
	private void saveUnitTask(Map<String, Object> map, int exposureCnt, 
			int clickCnt, long costNum, int bidCnt, int winCnt, long agentCostNum, int day, int hour) throws Exception {
		int unitId = NvwaUtils.obj2int(map.get("unitId"));
		int puType = NvwaUtils.obj2int(map.get("puType"));
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("unitId", unitId);
		paramMap.put("day", day);
		paramMap.put("hour", hour);
		paramMap.put("puType", puType);
		Map<String, Object> quotaPutMap = quotaPutMapper.getQuotaPutByDayHour(paramMap);
		if(quotaPutMap != null && !quotaPutMap.isEmpty()){
			QuotaPut quotaPut = new QuotaPut();
			quotaPut.setId(NvwaUtils.obj2int(quotaPutMap.get("id")));
			quotaPut.setClk(NvwaUtils.obj2int(quotaPutMap.get("clk")) + clickCnt);
			quotaPut.setCost(NvwaUtils.obj2long(quotaPutMap.get("cost")) + costNum);
			quotaPut.setExp(NvwaUtils.obj2int(quotaPutMap.get("exp")) + exposureCnt);
			quotaPut.setBid(NvwaUtils.obj2int(quotaPutMap.get("bid")) + bidCnt);
			quotaPut.setWin(NvwaUtils.obj2int(quotaPutMap.get("win")) + winCnt);
			quotaPut.setAgentCost(NvwaUtils.obj2long(quotaPutMap.get("agentCost")) + agentCostNum);
			
			quotaPutMapper.updateByPrimaryKeySelective(quotaPut);
			LOG.info("saveUnitTask update. day: {}, hour: {}, quotaPut: {}", day, hour, JsonUtils.TO_JSON(quotaPut));
		} else {
			QuotaPut quotaPut = new QuotaPut();
			quotaPut.setClk(clickCnt);
			quotaPut.setCost(costNum);
			quotaPut.setExp(exposureCnt);
			quotaPut.setBid(bidCnt);
			quotaPut.setWin(winCnt);
			quotaPut.setCreDay(day);
			quotaPut.setCreHour(hour);
			quotaPut.setPutId(unitId);
			quotaPut.setPutType(puType);
			quotaPut.setAgentCost(agentCostNum);
			
			quotaPutMapper.insertSelective(quotaPut);
			LOG.info("saveUnitTask insert. day: {}, hour: {}, quotaPut: {}", day, hour, JsonUtils.TO_JSON(quotaPut));
		}
		
		savePlanTask(map, exposureCnt, clickCnt, costNum, bidCnt, winCnt, agentCostNum, day, hour);
	}
	
	private void savePlanTask(Map<String, Object> map, int exposureCnt, 
			int clickCnt, long costNum, int bidCnt, int winCnt, long agentCostNum, int day, int hour) throws Exception {
		
		int planId = NvwaUtils.obj2int(map.get("planId"));
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("planId", planId);
		paramMap.put("day", day);
		paramMap.put("hour", hour);
		Map<String, Object> quotaPlanMap = quotaPlanMapper.getQuotaPlanByDayHour(paramMap);
		if(quotaPlanMap != null && !quotaPlanMap.isEmpty()){
			long agentCost = NvwaUtils.obj2long(quotaPlanMap.get("agentCost"));
			QuotaPlan quotaPlan = new QuotaPlan();
			quotaPlan.setId(NvwaUtils.obj2int(quotaPlanMap.get("id")));
			quotaPlan.setClk(NvwaUtils.obj2int(quotaPlanMap.get("clk")) + clickCnt);
			quotaPlan.setCost(NvwaUtils.obj2long(quotaPlanMap.get("cost")) + costNum);
			quotaPlan.setExp(NvwaUtils.obj2int(quotaPlanMap.get("exp")) + exposureCnt);
			quotaPlan.setBid(NvwaUtils.obj2int(quotaPlanMap.get("bid")) + bidCnt);
			quotaPlan.setWin(NvwaUtils.obj2int(quotaPlanMap.get("win")) + winCnt);
			quotaPlan.setAgentCost(agentCost + agentCostNum);
			
			quotaPlanMapper.updateByPrimaryKeySelective(quotaPlan);
			LOG.info("savePlanTask update. day: {}, hour: {}, quotaPlan: {}", day, hour, JsonUtils.TO_JSON(quotaPlan));
		} else {
			QuotaPlan quotaPlan = new QuotaPlan();
			quotaPlan.setClk(clickCnt);
			quotaPlan.setCost(costNum);
			quotaPlan.setExp(exposureCnt);
			quotaPlan.setBid(bidCnt);
			quotaPlan.setWin(winCnt);
			quotaPlan.setCreDay(day);
			quotaPlan.setCreHour(hour);
			quotaPlan.setPlanId(planId);
			quotaPlan.setAgentCost(agentCostNum);
			
			quotaPlanMapper.insertSelective(quotaPlan);
			LOG.info("savePlanTask insert. day: {}, hour: {}, quotaPlan: {}", day, hour, JsonUtils.TO_JSON(quotaPlan));
		}
	}
	
	public void quotaTask(Map<String, Object> paramMap) {
		quotaTask();
	}
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SpringContainer.create(applicationContext);
		TaskService flowTaskService = (TaskService) applicationContext.getBean("flowTaskService");
		
		flowTaskService.quotaTask(null);
		
		System.out.println("-------complete------");
	}
}
