package com.iwanvi.nvwa.web.task;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.NvwaUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.SdkAllotScheduleMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.SdkAllotSchedule;
import com.iwanvi.nvwa.dao.model.SdkAllotScheduleExample;

@Component("sdkAllotmentTask")
public class SdkAllotmentTask {
	
    private static final Logger LOG = LoggerFactory.getLogger(SdkAllotmentTask.class);
    
    @Autowired
    private SdkAllotScheduleMapper sdkAllotScheduleMapper;

    @Autowired
    private AdPositionMapper adPositionMapper;
    
    @Autowired
    private RedisDao redisDao;

    @Scheduled(cron = "55 * * * * ?")
    public void updateSdkAllotSechduleStatus() {
        try {
        	Long count = sdkAllotScheduleMapper.getScheduleCount();
			if (count == null || count <= 0) {
				return;
			}
			Long pageTotal = (count % 100 == 0) ? (count / 100) : (count / 100 + 1);

    		Date date = new Date();
    		String currentDay = DateUtils.format(date, DateUtils.SHORT_FORMAT);
			Map<String, Object> parMap = null;
			List<Map<String, Object>> list = null;
			for (int i = 1; i <= pageTotal; i++) {
				parMap = new HashMap<String, Object>();
				parMap.put("start", i - 1);
				parMap.put("limit", 100);
				
				list = sdkAllotScheduleMapper.getScheduleAll(parMap);
				if (list == null || list.isEmpty()) {
					return;
				}
				for (Map<String, Object> tempMap : list) {
					if(NvwaUtils.obj2int(tempMap.get("allotStatus")) == 0) {
						redisDao.hashRemove(StringUtils.buildString(
								Constants.KEY_REDIS_HASH_SDKALLOTMENT, NvwaUtils.obj2Empty(tempMap.get("posId"))), 
								NvwaUtils.obj2Empty(tempMap.get("schId")));
						continue;
					}
					switch (NvwaUtils.obj2int(tempMap.get("alloType"))) {
					case 1: // 包段
						updatePeriodStatus(tempMap, currentDay);
						break;
					case 2: // 包量
						updateFixedStatus(tempMap, currentDay);
						break;

					default:
						break;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("updateSdkAllotSechduleStatus error. ", e);
		}
    }
    
    private void updatePeriodStatus(Map<String, Object> tempMap, String currentDay) {
    	try {
    		if (NvwaUtils.obj2int(tempMap.get("monthPeriod")) < NvwaUtils.obj2int(currentDay.substring(0, 6))) { // 投放完成 status=2
    			updateExe(tempMap, 2);
    			return;
    		}
    		if (NvwaUtils.obj2int(tempMap.get("monthPeriod")) > NvwaUtils.obj2int(currentDay.substring(0, 6))) { // 投放未开始 status=0
    			updateExe(tempMap, 0);
    			return;
			}
			int maxDay = DateUtils.getMaxDayByDate(new Date());
    		String period = NvwaUtils.toBinnryString(NvwaUtils.obj2int(tempMap.get("schedulePeriod")), maxDay);
    		String day = currentDay.substring(6, 8);
    		char[] array = period.toCharArray();
    		if (array[(Integer.valueOf(day) - 1)] == '1') {
    			// 更新数据，status=1
				updateExe(tempMap, 1);
			} else {
				// 更新数据，status=0
				updateExe(tempMap, 0);
			}
    	} catch (Exception e) {
			LOG.error("updatePeriodStatus error. ", e);
    	}
    }
    
    // 包量
    private void updateFixedStatus(Map<String, Object> tempMap, String currentDay) {
    	try {
    		int status = NvwaUtils.obj2int(tempMap.get("status"));
    		if (Integer.valueOf(currentDay) > NvwaUtils.obj2int(tempMap.get("endDay"))) { // 投放完成 status=2
				updateExe(tempMap, 2);
    			return;
    		}
    		if (Integer.valueOf(currentDay) < NvwaUtils.obj2int(tempMap.get("startDay"))){ // 投放未开始 status=0
				updateExe(tempMap, 0);
    			return;
    		}
    		String currentHour = DateUtils.format(new Date(), DateUtils.FORMAT_HOUR);
			String timeSlice = NvwaUtils.obj2Empty(tempMap.get("timeSlice"));
			if (StringUtils.isBlank(timeSlice)) { // 全时段
				updateExe(tempMap, 1);
				return;
			}
			String[] array = timeSlice.split(",");
			int b = 0;
			int e = 0;
			for (int i = 0; i < array.length; i++) {
				b = Integer.valueOf(array[i].split("-")[0]);
				e = Integer.valueOf(array[i].split("-")[1]);
				if (Integer.valueOf(currentHour) > e) { // 投放完成 status=2
					updateExe(tempMap, 2);
					continue;
				}
				if (Integer.valueOf(currentHour) < b) { // 投放未开始 status=0
					updateExe(tempMap, 0);
	    			continue;
				}
				if (status != 1) {
    				updateExe(tempMap, 1);
    			}
			}
			
			// TODO 超限额状态更改
		} catch (Exception e) {
			LOG.error("updateFixedStatus error. ", e);
		}
    }
    
    //执行更新
	@Transactional(rollbackFor = Exception.class)
    private void updateExe(Map<String, Object> tempMap, int status) {
    	try {
    		int id = NvwaUtils.obj2int(tempMap.get("schId"));
    		String posId = NvwaUtils.obj2Empty(tempMap.get("posId"));
    		
    		SdkAllotSchedule record = new SdkAllotSchedule();
    		record.setId(id);
    		record.setStatus(status);
    		
			int r = sdkAllotScheduleMapper.updateByPrimaryKeySelective(record);
			LOG.info("updateExe. record: {}, r: {}", JsonUtils.TO_JSON(record), r);
			
			// TODO 当状态为1的时候写入redis，参与流量分配
			String redisKey = StringUtils.buildString(Constants.KEY_REDIS_HASH_SDKALLOTMENT, posId);
			if (status == 1) {
				redisDao.hashAdd(redisKey, String.valueOf(id), JsonUtils.TO_JSON(tempMap));
			} else {
				redisDao.hashRemove(redisKey, String.valueOf(id));
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.error("updateExe error. ", e);
		}
    }
	
	public void removeRedis(int allotmentId) {
		try {
			SdkAllotScheduleExample sdkAllotScheduleExample = new SdkAllotScheduleExample();
			sdkAllotScheduleExample.createCriteria().andAllotmentIdEqualTo(allotmentId);
			List<SdkAllotSchedule> list = sdkAllotScheduleMapper.selectByExample(sdkAllotScheduleExample);
			if (CollectionUtils.isEmpty(list)) {
				LOG.warn("removeRedis exception. allotmentId: {}", allotmentId);
				return;
			}
			String redisKey = null;
			AdPosition adPosition = null;
			for (SdkAllotSchedule entity : list) {
				adPosition = adPositionMapper.selectByPrimaryKey(entity.getAdPosId());
				if (adPosition == null) {
					continue;
				}
				
				redisKey = StringUtils.buildString(Constants.KEY_REDIS_HASH_SDKALLOTMENT, adPosition.getUuid());
				redisDao.hashRemove(redisKey, String.valueOf(entity.getId()));
			}
			
		} catch (Exception e) {
			LOG.error("removeRedis error. ", e);
		}
	}
}
