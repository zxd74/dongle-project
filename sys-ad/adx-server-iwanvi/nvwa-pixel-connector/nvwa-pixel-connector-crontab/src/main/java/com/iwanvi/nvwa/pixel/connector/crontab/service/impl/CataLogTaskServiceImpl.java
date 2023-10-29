package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.iwanvi.nvwa.dao.QuotaBookMapper;
import com.iwanvi.nvwa.dao.model.QuotaBook;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

@Service("cataLogTaskService")
public class CataLogTaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("cataLogTaskService");

	@Resource
	private RedisDao redisDao;
	
	@Resource
	private QuotaBookMapper quotaBookMapper;

	/**
	 * 书目数据入库
	 * @param paramMap
	 */

	public void quotaTask(Map<String, Object> paramMap) {
		
	}
	public void quotaTaskForDay() {
		try {
			Set<String> partKeySet = redisDao.getKeys(com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_KEYSET);
			if (partKeySet == null || partKeySet.isEmpty()) {
				LOG.warn("cataLogTaskService exception. partKeySet is null.");
				return;
			}
			LOG.info("cataLogTaskService partKeySize: {}", partKeySet.size());
			
			for(String partKey : partKeySet) {
				saveQuotaBookTask(partKey);
			}
		} catch (Exception e) {
			LOG.error("quotaTask error. ", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveQuotaBookTask(String partKey) {
		try {
			String day = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
			
			int exposureCnt, clickCnt, reqCnt = 0;
			long cost = 0;
			
			List<String> keys = new ArrayList<>();
			keys.add(StringUtils.buildString(
					com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA,
					Constants.QUOTA_DISPLAY, partKey, day)); // exposureCnt
			keys.add(StringUtils.buildString(com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA, 
					Constants.QUOTA_CLICK, partKey, day)); // clickCnt
			keys.add(StringUtils.buildString(com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA, 
					Constants.QUOTA_COST, partKey, day)); // cost
			keys.add(StringUtils.buildString(
					com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA, 
					Constants.QUOTA_REQ, partKey, day)); // REQ
			
			List<String> values = redisDao.multiGet(keys);
			exposureCnt = NvwaUtils.obj2int(values.get(0));
			clickCnt = NvwaUtils.obj2int(values.get(1));
			cost = NvwaUtils.obj2long(values.get(2));
			reqCnt = NvwaUtils.obj2int(values.get(3));
			int reqUv = redisDao.pfCont(StringUtils.buildString(
					com.iwanvi.nvwa.common.utils.Constants.KEY_REDIS_CATALOG_QUOTA, 
					Constants.QUOTA_REQUV, partKey, day)).intValue();
			
			keys.clear(); keys = null;
			values.clear(); values = null;
			
			long temp = exposureCnt + clickCnt + cost + reqCnt;
			if(temp == Constants.INTEGER_0){
				LOG.warn("saveFlowSourceTask zero. partKey: {}, day: {}", partKey, day);
				return;
			}
			
			String[] leveArray = partKey.split(":");
			
			QuotaBook entity = new QuotaBook();
			entity.setClk(clickCnt);
			entity.setExp(exposureCnt);
			entity.setInvest(cost);
			entity.setReq(reqCnt);
			entity.setRequv(reqUv);
			entity.setLevel1(Integer.valueOf(leveArray[0]));
			entity.setLevel2(Integer.valueOf(leveArray[1]));
			entity.setLevel3(Integer.valueOf(leveArray[2]));
			entity.setCreDay(NvwaUtils.obj2int(day));
			
			int result = quotaBookMapper.insertSelective(entity);
			if(result <= 0){
				LOG.warn("saveQuotaBookTask exception. partKey: {}, day: {}", partKey, day);
			}
			
			LOG.info("saveQuotaBookTask end. day: {}, entity: {}", day, JsonUtils.TO_JSON(entity));
		} catch (Exception e) {
			LOG.error("saveQuotaBookTask error. ", e);
		}
	}
}
