package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.QuotaPlatformMapper;
import com.iwanvi.nvwa.dao.model.QuotaPlatform;
import com.iwanvi.nvwa.pixel.connector.common.service.AliyunLogService;
import com.iwanvi.nvwa.pixel.connector.common.service.EmailService;
import com.iwanvi.nvwa.pixel.connector.common.service.PixelService;
import com.iwanvi.nvwa.pixel.connector.common.utils.NvwaUtils;
import com.iwanvi.nvwa.pixel.connector.common.utils.PixelConstants;
import com.iwanvi.nvwa.pixel.connector.common.utils.ThreadDistribution;
import com.iwanvi.nvwa.pixel.connector.crontab.service.TaskService;

import ai.houyi.dorado.rest.server.DoradoServer;
import ai.houyi.dorado.rest.server.DoradoServerBuilder;
import ai.houyi.dorado.spring.SpringContainer;

@Service("dspPlatformTaskService")
public class DspPlatformTaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("DspPlatformTaskService");

	@Resource
	private RedisDao redisDao;
	
	@Resource
	private QuotaPlatformMapper quotaPlatformMapper;
	
	@Resource
	private FlowConsumerMapper flowConsumerMapper;
	
	@Resource
	private EmailService emailService;
	
	@Resource
	private PixelService pixelService;
	
	/**
	 * DSP平台数据入库
	 * @param paramMap 单元ID string
	 * @param day
	 * @param hour
	 */
	public void quotaTask() {
		ThreadDistribution.getInstance().doWork(new Runnable() {
			@Override
			public void run() {
				LOG.info("===================dspPlatformTaskService===================");
				PixelConstants.COST_PLATFORM = 0l;
				quotaTask(null);
			}
		});
	}
	
	@Scheduled(cron = "0 40 */1 * * ?")
	public void taskMonitor() {
		ThreadDistribution.getInstance().doWork(new Runnable() {
			@Override
			public void run() {
				long abs = Math.abs(PixelConstants.COST_PLATFORM - PixelConstants.COST_FLOW);
				LOG.info("===================taskMonitor==================={}, {}, {}", 
						PixelConstants.COST_PLATFORM, PixelConstants.COST_FLOW, abs);
				if(abs > 200000) {
					String content = "Time: " + DateUtils.format(new Date()) 
							+ ", platform: " + PixelConstants.COST_PLATFORM
							+ ", flow: " + PixelConstants.COST_FLOW
							+ ", difference: " + abs;
					String result = emailService.sendEmailBySSL("taskMonitor", content, null);
					LOG.info("===================taskMonitor===================result: {}", result);
				}
			}
		});
	}
	
	public void quotaTask(Map<String, Object> paramMap) {
		try {
			long l1 = System.currentTimeMillis();
			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String day = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(preHourDate, DateUtils.FORMAT_HOUR);
			
			String timeStart = DateUtils.format(DateUtils.getFirstSecondOfHour(preHourDate), DateUtils.FORMAT_YMDHMS);
			String timeEnt = DateUtils.format(DateUtils.getFirstSecondOfHour(new Date()), DateUtils.FORMAT_YMDHMS);
			
			int from = (int)(DateUtils.parse(timeStart, DateUtils.FORMAT_YMDHMS).getTime() / 1000) - 60;
			int to = (int)(DateUtils.parse(timeEnt, DateUtils.FORMAT_YMDHMS).getTime() / 1000) + 60; // 左闭右开

			LOG.info(timeStart + "=========dspPlatformTaskService=========" + timeEnt);

			paramMap = new HashMap<>();
//			paramMap.put("limit", 100);
//			paramMap.put("t", 98); // sdk
//			List<FlowConsumer> tempList = flowConsumerMapper.getIds(paramMap);
//			if(tempList == null || tempList.isEmpty()){
//				LOG.warn("dspPlatformTaskService exception. tempList is null. day: {}, hour: {}", day, hour);
//				return;
//			}
//
//			List<String> dspIdList = new ArrayList<String>();
//			for (FlowConsumer consumer : tempList) {
//				dspIdList.add(consumer.getDspId());
//			}
			
//			List<String> dspIdList = AliyunLogService.getDistinctItem(Constants.ALIYUN_LOG_STORE_DSP, Constants.SIGN_ASTERISK, "dspId", from, to);
//			dspIdList = dspIdList == null ? new ArrayList<String>() : dspIdList;
			List<String> dspIdList = pixelService.getQueryQuotaList(Constants.ALIYUN_LOG_STORE_DSP, "dspId", from, to);
			if (CollectionUtils.isEmpty(dspIdList)) {
				emailService.sendEmail("CountBreak", "Platform getDistinctItem exception: dspId");
				return;
			}
			List<String> appIdList = pixelService.getQueryQuotaList(Constants.ALIYUN_LOG_STORE_DSP, "appId", from, to);
			if (CollectionUtils.isEmpty(appIdList)) {
				emailService.sendEmail("CountBreak", "Platform getDistinctItem exception: appId");
				return;
			}
			List<String> posIdList = pixelService.getQueryQuotaList(Constants.ALIYUN_LOG_STORE_DSP, "posId", from, to);
			if (CollectionUtils.isEmpty(posIdList)) {
				emailService.sendEmail("CountBreak", "Platform getDistinctItem exception: posId");
				return;
			}

			LOG.info("dspIdList======{}", dspIdList);
			LOG.info("appIdList======{}", appIdList);
			LOG.info("posIdList======{}", posIdList);
			
			String query = null;
			QuotaPlatform quotaPlatform = null;
			for (int i = 0; i < appIdList.size(); i++) {
				if (NumberUtils.isCreatable(appIdList.get(i))) {
					continue;
				}
				for (int i1 = 0; i1 < posIdList.size(); i1++) {
					if (NumberUtils.isCreatable(posIdList.get(i1))) {
						continue;
					}
					for (int i2 = 0; i2 < dspIdList.size(); i2++) {
						try {
							quotaPlatform = new QuotaPlatform();
							quotaPlatform.setCreDay(NvwaUtils.obj2int(day));
							quotaPlatform.setCreHour(NvwaUtils.obj2int(hour, -1));
							quotaPlatform.setAppid(appIdList.get(i));
							quotaPlatform.setAdpid(posIdList.get(i1));
							quotaPlatform.setPlatformId(dspIdList.get(i2));
							
							query = StringUtils.buildString(
									Constants.FORMMAT_QUERY_PLATFORM, appIdList.get(i), posIdList.get(i1), 
									dspIdList.get(i2), timeStart, timeEnt);
							
							saveDspPlatformTask(quotaPlatform, query, from, to, dspIdList.get(i2));
						} catch (Exception e) {
							LOG.warn("dspPlatformTaskService saveFlowSourceTask exception.", e);
						}
					}
				}
			}
			long elapsed = (System.currentTimeMillis() - l1);
			LOG.info("dspPlatformTaskService complete=========hour: {}, cost: {}, elapsed: {}, elapsedFormat: {}", 
					hour, PixelConstants.COST_PLATFORM, elapsed, DateUtils.millisecondFormat(elapsed));
		} catch (Exception e) {
			LOG.error("dspPlatformTaskService quotaTask error. ", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveDspPlatformTask(QuotaPlatform quotaPlatform, String query, int from, int to, String dspId) {
		try {
			long exposureCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_EXP, query, from, to);
			long clickCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_CLK, query, from, to);
			String queryCost = StringUtils.concat(query, Constants.SIGN_OR, Constants.FORMMAT_QUERYCOST);
			
			long cost = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCost, from, to);
			cost += AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCost, from, to);
			
			PixelConstants.COST_PLATFORM += cost;
			
			long reqCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, query, from, to);
			long bidCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and bid:1"), from, to);
			long winCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and win:1"), from, to);
			long timeOut = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and timeout:1"), from, to);
			long error = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and error:1"), from, to);
			long nobid = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and nobid:1"), from, to);
			long lower = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and lower:1"), from, to);
			long qps = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_DSP, 
					StringUtils.concat(query, " and qps:1"), from, to);
			
			long temp = reqCnt + exposureCnt + clickCnt + 
					cost + bidCnt + winCnt + timeOut + error + nobid + lower + qps;
			if(temp == Constants.INTEGER_0){
				return;
			}
			
			quotaPlatform.setClk((int)clickCnt);
			quotaPlatform.setExp((int)exposureCnt);
			quotaPlatform.setBid((int)bidCnt);
			quotaPlatform.setInvest(cost);
			quotaPlatform.setReq(reqCnt - qps);
			
			quotaPlatform.setTimeout((int)timeOut);
			quotaPlatform.setError((int)error);
			quotaPlatform.setNobid(nobid);
			quotaPlatform.setLower((int)lower);
			quotaPlatform.setOverqps(qps);
			quotaPlatform.setWin(winCnt);
			
			int result = quotaPlatformMapper.insertSelective(quotaPlatform);
			if(result <= 0){
				LOG.warn("saveDspPlatformTask exception. query: {}", query);
			}
		} catch (Exception e) {
			LOG.error("saveDspPlatformTask error. quotaPlatform: {}", JsonUtils.TO_JSON(quotaPlatform), e);
		}
	}
	
	public void quotaTaskForDay() {
		
	}
	
	public static void main(String[] args) {
		DoradoServer server = DoradoServerBuilder.forPort(9320)
				.scanPackages("com.nvwa").springOn().build();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SpringContainer.create(applicationContext);
		
//		TaskService dspPlatformTaskService = (TaskService) applicationContext.getBean("dspPlatformTaskService");
//		dspPlatformTaskService.quotaTask(null);
//		EmailService emailService = (EmailService)applicationContext.getBean("emailService");
//		String result = emailService.sendEmail("test_subject", "test_content");
		
//		System.out.println(result);
		
		LOG.warn("---------complete-------");
//		System.exit(-1);
		server.start();
//		System.exit(-1);
	}
}
