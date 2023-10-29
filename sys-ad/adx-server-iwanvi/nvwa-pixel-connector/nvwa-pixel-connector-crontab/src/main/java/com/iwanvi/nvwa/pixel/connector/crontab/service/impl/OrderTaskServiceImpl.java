package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.QuotaOrderMapper;
import com.iwanvi.nvwa.dao.QuotaPutMapper;
import com.iwanvi.nvwa.dao.model.QuotaOrder;
import com.iwanvi.nvwa.dao.model.QuotaPut;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

@Service("orderTaskService")
public class OrderTaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("OrderTaskService");
	
	@Resource
	private RedisDao redisDao;

	@Resource
	private QuotaOrderMapper quotaOrderMapper;
	
	@Resource
	private QuotaPutMapper quotaPutMapper;

	/**
	 * 订单投放
	 * @param paramMap putId
	 */
	@Transactional(rollbackFor = Exception.class)
	public void quotaTask(Map<String, Object> paramMap) {
		try {
			int putId = NvwaUtils.obj2int(paramMap.get("putId"));
			if (putId == 0) {
				LOG.info("exception. putId is null");
				return;
			}
			
			int exposureCnt, clickCnt, bidCnt, winCnt = 0;
			long cost = 0;
			long agentCost = 0;

			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String day = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(preHourDate, DateUtils.FORMAT_HOUR);
			
			List<String> keys = new ArrayList<>();
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, 
					Constants.QUOTA_DISPLAY, putId, day, hour)); // exposureCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, 
					Constants.QUOTA_CLICK, putId, day, hour)); // clickCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, 
					Constants.QUOTA_COST, putId, day, hour)); // cost
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, 
					Constants.QUOTA_BID, putId, day, hour)); // bid
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_ORDER_QUOTA_HOUR, 
					Constants.QUOTA_WIN, putId, day, hour)); // win
			
			List<String> values = redisDao.multiGet(keys);

			exposureCnt = NvwaUtils.obj2int(values.get(0));
			clickCnt = NvwaUtils.obj2int(values.get(1));
			cost = NvwaUtils.obj2long(values.get(2));
			bidCnt = NvwaUtils.obj2int(values.get(3));
			winCnt = NvwaUtils.obj2int(values.get(4));
			
			keys.clear(); keys = null;
			values.clear(); values = null;
			
			long temp = exposureCnt + clickCnt + cost + bidCnt + winCnt + agentCost;
			if(temp == Constants.INTEGER_0){
				LOG.warn("saveQuotaPutTask zero. putId: {}, day: {}, hour: {}", putId, day, hour);
				return;
			}
			
			QuotaPut quotaPut = new QuotaPut();
			quotaPut.setClk(clickCnt);
			quotaPut.setCost(cost);
			quotaPut.setExp(exposureCnt);
			quotaPut.setBid(bidCnt);
			quotaPut.setWin(winCnt);
			quotaPut.setCreDay(NvwaUtils.obj2int(day));
			quotaPut.setCreHour(NvwaUtils.obj2int(hour));
			quotaPut.setPutId(putId);
			quotaPut.setPutType(17);
			
			quotaPutMapper.insertSelective(quotaPut);
			LOG.info("saveQuotaPutTask insert. putId: {}, day: {}, hour: {}, quotaPut: {}", putId, day, hour, JsonUtils.TO_JSON(quotaPut));
			
			saveOrderTask(paramMap, exposureCnt, clickCnt, cost, bidCnt, winCnt, day, hour);
		} catch (Exception e) {
			LOG.error("saveOrderTask error. ", e);
		}
	}
	
	private void saveOrderTask(Map<String, Object> map, int exposureCnt, 
			int clickCnt, long cost, int bidCnt, int winCnt, String day, String hour) throws Exception {
		int orderId = NvwaUtils.obj2int(map.get("orderId"));
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("orderId", orderId);
		paramMap.put("day", day);
		paramMap.put("hour", hour);
		QuotaOrder quotaOrder = quotaOrderMapper.getQuotaOrderByDayHour(paramMap);
		if(quotaOrder != null){
			quotaOrder.setClk(NvwaUtils.obj2int(quotaOrder.getClk() + clickCnt));
			quotaOrder.setCost(NvwaUtils.obj2long(quotaOrder.getCost() + cost));
			quotaOrder.setExp(NvwaUtils.obj2int(quotaOrder.getExp() + exposureCnt));
			quotaOrder.setBid(NvwaUtils.obj2int(quotaOrder.getBid() + bidCnt));
			quotaOrder.setWin(NvwaUtils.obj2int(quotaOrder.getWin() + winCnt));
			
			quotaOrderMapper.updateByPrimaryKeySelective(quotaOrder);
			LOG.info("saveOrderTask update. orderId: {}, day: {}, hour: {}, quotaOrder: {}", orderId, day, hour, JsonUtils.TO_JSON(quotaOrder));
		} else {
			quotaOrder = new QuotaOrder();
			quotaOrder.setBid(bidCnt);
			quotaOrder.setWin(winCnt);
			quotaOrder.setClk(clickCnt);
			quotaOrder.setCost(cost);
			quotaOrder.setExp(exposureCnt);
			quotaOrder.setCreDay(NvwaUtils.obj2int(day));
			quotaOrder.setCreHour(NvwaUtils.obj2int(hour, -1));
			quotaOrder.setOrderId(orderId);
			
			int result = quotaOrderMapper.insertSelective(quotaOrder);
			if(result <= 0){
				LOG.warn("saveOrderTask exception. orderId: {}, day: {}, hour: {}", orderId, day, hour);
			}
			
			LOG.info("saveOrderTask end. orderId: {}, day: {}, hour: {}, quotaOrder: {}", orderId, day, hour, JsonUtils.TO_JSON(quotaOrder));
		}
	}
	public void quotaTaskForDay() {
		
	}
}
