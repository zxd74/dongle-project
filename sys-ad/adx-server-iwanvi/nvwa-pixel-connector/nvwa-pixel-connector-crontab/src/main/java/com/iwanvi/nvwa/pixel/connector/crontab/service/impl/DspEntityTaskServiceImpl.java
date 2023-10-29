package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.iwanvi.nvwa.dao.QuotaDspMapper;
import com.iwanvi.nvwa.dao.model.EntityDsp;
import com.iwanvi.nvwa.dao.model.QuotaDsp;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

import ai.houyi.dorado.rest.server.DoradoServer;
import ai.houyi.dorado.rest.server.DoradoServerBuilder;
import ai.houyi.dorado.spring.SpringContainer;

@Service("dspEntityTaskService")
public class DspEntityTaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("DspEntityTaskService");

	@Resource
	private RedisDao redisDao;
	
	@Resource
	private QuotaDspMapper quotaDspMapper;
	
	@Resource
	private EntityDspMapper entityDspMapper;

	/**
	 * DSP平台创意数据入库
	 * @param paramMap 单元ID string
	 * @param day
	 * @param hour
	 */
	public void quotaTask(Map<String, Object> paramMap) {
		try {
			LOG.info("===================dspEntityTaskService===================");
			Integer adsCnt = entityDspMapper.getAdsCnt();
			if(adsCnt == null || adsCnt == Constants.INTEGER_0){
				LOG.warn("quotaTask exception. adsCnt is 0.");
				return;
			}
			paramMap = new HashMap<>();
			paramMap.put("limit", 100);
			Integer pageTotal = (adsCnt % 100 == 0) ? (adsCnt / 100) : (adsCnt / 100 + 1);
			for (int i = 0; i < pageTotal; i++) {
				paramMap.put("offset", i);
				List<EntityDsp> tempList = entityDspMapper.getAdIds(paramMap);
				if(tempList == null || tempList.isEmpty()){
					continue;
				}
				for (EntityDsp entityDsp : tempList) {
					saveDspEntityTask(entityDsp);
				}
			}
		} catch (Exception e) {
			LOG.error("quotaTask error. ", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveDspEntityTask(EntityDsp entityDsp) {
		try {
			int cid = entityDsp.getId();
			int exposureCnt, clickCnt, bidCnt, winCnt = 0;
			long cost = 0;

			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String day = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(preHourDate, DateUtils.FORMAT_HOUR);
			
			List<String> keys = new ArrayList<>();
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_DISPLAY, cid, day, hour)); // exposureCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_CLICK, cid, day, hour)); // clickCnt
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_COST, cid, day, hour)); // cost
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_BID, cid, day, hour)); // bid
			keys.add(StringUtils.buildString(PixelConstants.KEY_REDIS_CREATIVE_QUOTA_HOUR, 
					Constants.QUOTA_WIN, cid, day, hour)); // win
			
			List<String> values = redisDao.multiGet(keys);
			exposureCnt = NvwaUtils.obj2int(values.get(0));
			clickCnt = NvwaUtils.obj2int(values.get(1));
			cost = NvwaUtils.obj2long(values.get(2));
			bidCnt = NvwaUtils.obj2int(values.get(3));
			winCnt = NvwaUtils.obj2int(values.get(4));
			
			keys.clear(); keys = null;
			values.clear(); values = null;
			
			long temp = exposureCnt + clickCnt + cost + bidCnt + winCnt;
			if(temp == Constants.INTEGER_0){
				LOG.warn("saveDspEntityTask zero. cid: {}, day: {}, hour: {}", cid, day, hour);
				return;
			}
			
			QuotaDsp entity = new QuotaDsp();
			entity.setEntId(cid);
			entity.setClk(clickCnt);
			entity.setExp(exposureCnt);
			entity.setBid(bidCnt);
			entity.setWin(winCnt);
			entity.setCreDay(NvwaUtils.obj2int(day));
			entity.setCreHour(NvwaUtils.obj2int(hour, -1));
			
			int result = quotaDspMapper.insertSelective(entity);
			if(result <= 0){
				LOG.warn("saveDspEntityTask exception. cid: {}, day: {}, hour: {}", cid, day, hour);
			}
			
			LOG.info("saveDspEntityTask end. day: {}, hour: {}, QuotaDsp: {}", day, hour, JsonUtils.TO_JSON(entity));
		} catch (Exception e) {
			LOG.error("saveDspEntityTask error. ", e);
		}
	}
	public void quotaTaskForDay() {
		
	}
	
	public static void main(String[] args) {
		DoradoServer server = DoradoServerBuilder.forPort(9100)
				.scanPackages("com.nvwa").springOn().build();
		
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SpringContainer.create(applicationContext);
		TaskService dspEntityTaskService = (TaskService) applicationContext.getBean("dspEntityTaskService");
		
		dspEntityTaskService.quotaTask(null);
		
		System.out.println("-------complete------");
		
		server.start();
	}
}
