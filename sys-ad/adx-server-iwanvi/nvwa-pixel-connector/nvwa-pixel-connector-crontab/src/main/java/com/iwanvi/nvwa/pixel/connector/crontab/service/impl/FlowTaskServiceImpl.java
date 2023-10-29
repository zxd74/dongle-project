package com.iwanvi.nvwa.pixel.connector.crontab.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AreaMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.QuotaFlowAreaMapper;
import com.iwanvi.nvwa.dao.QuotaFlowMapper;
import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.AreaExample;
import com.iwanvi.nvwa.dao.model.QuotaFlow;
import com.iwanvi.nvwa.dao.model.QuotaFlowArea;
import com.iwanvi.nvwa.dao.model.QuotaFlowAreaExample;
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

@Service("flowTaskService")
public class FlowTaskServiceImpl implements TaskService {

	private static final Logger LOG = LoggerFactory.getLogger("flowTaskService");

	public static final String FORMMAT_QUERY_FLOWAREA = " areacode in [{0} {1}) ";
	@Resource
	private RedisDao redisDao;
	
	@Resource
	private AreaMapper areaMapper;
	
	@Resource
	private QuotaFlowMapper quotaFlowMapper;
	
	@Resource
	private QuotaFlowAreaMapper quotaFlowAreaMapper;
	
	@Resource
	private FlowSourceMapper flowSourceMapper;
	
	@Resource
	private EmailService emailService;
	
	@Resource
	private PixelService pixelService;
	
	/**
	 * 媒体数据入库
	 * @param paramMap
	 */
	public void quotaTask() {
		ThreadDistribution.getInstance().doWork(new Runnable() {
			@Override
			public void run() {
				LOG.info("===================flowTaskService===================");
				PixelConstants.COST_FLOW = 0l;
				quotaTask(null);
				quotaAreaTask();
			}
		});
		
	}
	public void quotaAreaTask() {
		try {
			long l1 = System.currentTimeMillis();
			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String day = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			
			String timeStart = DateUtils.format(DateUtils.getFirstSecondOfHour(preHourDate), DateUtils.FORMAT_YMDHMS);
			String timeEnt = DateUtils.format(DateUtils.getFirstSecondOfHour(new Date()), DateUtils.FORMAT_YMDHMS);
			
			int from = (int)(DateUtils.parse(timeStart, DateUtils.FORMAT_YMDHMS).getTime() / 1000) - 60;
			int to = (int)(DateUtils.parse(timeEnt, DateUtils.FORMAT_YMDHMS).getTime() / 1000) + 60; // 左闭右开
			
			AreaExample areaExample = new AreaExample();
			Integer[] arrays = {1,2};
	        areaExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID)
	        	.andTypeIn(Arrays.asList(arrays)).andIsCityLevelEqualTo(1);
	        List<Area> areaList = areaMapper.selectByExample(areaExample);
			if (CollectionUtils.isEmpty(areaList)) {
				LOG.warn("flowAreaTaskService exception. break. ");
				return;
			}

			String query = null;
			int endCode = 0;
			QuotaFlowArea quotaFlowArea = null;
			QuotaFlowAreaExample example = null;
			for (Area area : areaList) {
				if(area.getAreaCode() % 10000 == 0) {
					endCode = area.getAreaCode() + 10000;
				} else {
					endCode = area.getAreaCode() + 100;
				}
				query = StringUtils.buildString(FORMMAT_QUERY_FLOWAREA, area.getAreaCode(), endCode);
				quotaFlowArea = new QuotaFlowArea();
				quotaFlowArea.setCreDay(NvwaUtils.obj2int(day));
				quotaFlowArea.setArea(NvwaUtils.obj2Empty(area.getAreaCode()));
				
				example = new QuotaFlowAreaExample();
				example.createCriteria().andAreaEqualTo(
						NvwaUtils.obj2Empty(area.getAreaCode())).andCreDayEqualTo(NvwaUtils.obj2int(day));
				saveFlowAreaTask(NvwaUtils.obj2int(day), quotaFlowArea, example, query, from, to);
			}
			LOG.info("flowAreaTaskService complete=========. elapsed: {}", (System.currentTimeMillis() - l1));
		} catch (Exception e) {
			LOG.error("flowAreaTaskService quotaTask error. ", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveFlowAreaTask(int day, QuotaFlowArea quotaFlowArea, QuotaFlowAreaExample example, String query, int from, int to) {
		try {
			long exposureCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_EXP, query, from, to);
			long clickCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_CLK, query, from, to);
			long reqCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_REQ, query, from, to);
			
			String queryUv = StringUtils.concat(query, Constants.SIGN_OR, Constants.FORMMAT_QUERYUV);
			long expuv = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryUv, from, to);
			long clkuv = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryUv, from, to);
			long requv = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_REQ, queryUv, from, to);
			
			String queryCost = StringUtils.concat(query, Constants.SIGN_OR, Constants.FORMMAT_QUERYCOST);
			long cost = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCost, from, to);
			cost += AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCost, from, to);
			
			long temp = exposureCnt + clickCnt + cost + reqCnt;
			if (temp == Constants.INTEGER_0) {
				return;
			}
			
			List<QuotaFlowArea> list = quotaFlowAreaMapper.selectByExample(example);
			QuotaFlowArea entity = CollectionUtils.isEmpty(list) ? null : list.get(0);
			if (entity == null) {
				quotaFlowArea.setClk((int) clickCnt);
				quotaFlowArea.setExp((int) exposureCnt);
				quotaFlowArea.setInvest(cost);
				quotaFlowArea.setReq(reqCnt);
				quotaFlowArea.setExpuv((int) expuv);
				quotaFlowArea.setClkuv((int) clkuv);
				quotaFlowArea.setRequv((int) requv);
				int result = quotaFlowAreaMapper.insertSelective(quotaFlowArea);
				if(result <= 0){
					LOG.warn("saveFlowAreaTask insert exception. query: {}, start: {}, end: {}", query, from, to);
				}
			} else {
				entity.setClk((int) clickCnt + entity.getClk());
				entity.setExp((int) exposureCnt + entity.getExp());
				entity.setInvest(cost + entity.getInvest());
				entity.setReq(reqCnt + entity.getReq());
				entity.setExpuv((int) expuv + entity.getExpuv());
				entity.setClkuv((int) clkuv + entity.getClkuv());
				entity.setRequv((int) requv + entity.getRequv());
				
				int result = quotaFlowAreaMapper.updateByPrimaryKey(entity);
				if(result <= 0){
					LOG.warn("saveFlowAreaTask update exception. query: {}, start: {}, end: {}", query, from, to);
				}
			}
		} catch (Exception e) {
			LOG.error("saveFlowAreaTask error. ", e);
		}
	}
	public void quotaTask(Map<String, Object> paramMap) {
		try {
			long l1 = System.currentTimeMillis();
			
			Date preHourDate = DateUtils.getPreviousHour(new Date(), 1);
			String day = DateUtils.format(preHourDate, DateUtils.SHORT_FORMAT);
			String hour = DateUtils.format(preHourDate, DateUtils.FORMAT_HOUR);
			
			String timeStart = DateUtils.format(DateUtils.getFirstSecondOfHour(preHourDate), DateUtils.FORMAT_YMDHMS);
			String timeEnt = DateUtils.format(DateUtils.getFirstSecondOfHour(new Date()), DateUtils.FORMAT_YMDHMS);
			String timeStartUV = DateUtils.format(DateUtils.getFirstSecondOfDay(new Date()), DateUtils.FORMAT_YMDHMS);
			
			int from = (int)(DateUtils.parse(timeStart, DateUtils.FORMAT_YMDHMS).getTime() / 1000) - 60;
			int to = (int)(DateUtils.parse(timeEnt, DateUtils.FORMAT_YMDHMS).getTime() / 1000) + 60; // 左闭右开
			
			int fromUV = (int)(DateUtils.parse(timeStartUV, DateUtils.FORMAT_YMDHMS).getTime() / 1000) - 60;

			LOG.info("flowTaskService=========timeStart: {}, timeStartUV: {}, timeEnd: {}, from: {}, fromUV: {}, to: {}", 
					timeStart, timeStartUV, timeEnt, from, fromUV, to);

			List<String> versionList = pixelService.getQueryQuotaList(Constants.ALIYUN_LOG_STORE_REQ, "version", from, to);
			if (CollectionUtils.isEmpty(versionList)) {
				emailService.sendEmail("CountBreak", "Flow getDistinctItem exception: version");
				return;
			}
			List<String> appIdList = pixelService.getQueryQuotaList(Constants.ALIYUN_LOG_STORE_REQ, "appId", from, to);
			if (CollectionUtils.isEmpty(appIdList)) {
				emailService.sendEmail("CountBreak", "Flow getDistinctItem exception: appId");
				return;
			}
			List<String> posIdList = pixelService.getQueryQuotaList(Constants.ALIYUN_LOG_STORE_REQ, "posId", from, to);
			if (CollectionUtils.isEmpty(posIdList)) {
				emailService.sendEmail("CountBreak", "Flow getDistinctItem exception: posId");
				return;
			}
			
			LOG.info("appIdList======{}", appIdList);
			LOG.info("posIdList======{}", posIdList);
			LOG.info("versionList======{}", versionList);
			
//			List<String> channel1List = null; //AliyunLogService.getDistinctItem(Constants.ALIYUN_LOG_STORE_REQ, Constants.SIGN_ASTERISK, "channel1", from, to);
//			channel1List = channel1List == null ? new ArrayList<String>() : channel1List;
//			channel1List.add("000000");
//			LOG.info("channel1List======{}", channel1List);
//			List<String> channel2List = null; //AliyunLogService.getDistinctItem(Constants.ALIYUN_LOG_STORE_REQ, Constants.SIGN_ASTERISK, "channel2", from, to);
//			channel2List = channel2List == null ? new ArrayList<String>() : channel2List;
//			LOG.info("channel2List======{}", channel2List);
			
			String query, queryUV, version = null, channel1 = "000000";
			String format = " appId:{0} and posId:{1} and version:{2} and time in [{3} {4}) ";
			QuotaFlow quotaFlow = null;
			for (int i = 0; i < appIdList.size(); i++) {
				if (NumberUtils.isDigits(appIdList.get(i))) {
					continue;
				}
				for (int i1 = 0; i1 < posIdList.size(); i1++) {
					if (NumberUtils.isDigits(posIdList.get(i1))) {
						continue;
					}
					for (int i2 = 0; i2 < versionList.size(); i2++) {
						version = versionList.get(i2);
						if (StringUtils.isBlank(version) || !NumberUtils.isDigits(version.replaceAll("\\.", ""))) {
							continue;
						}
//						for (int i3 = 0; i3 < channel1List.size(); i3++) {
//							LOG.info("4======{}", channel1List.get(i3));
//							for (int i4 = 0; i4 < channel2List.size(); i4++) {
//								LOG.info("5======{}", channel2List.get(i4));
								try {
//									channel2 = "";
//									channel2 = StringUtils.isBlank(channel2) ? "\"\"" : channel2;
									version = StringUtils.isBlank(version) ? "\"\"" : version;
									quotaFlow = new QuotaFlow();
									quotaFlow.setCreDay(NvwaUtils.obj2int(day));
									quotaFlow.setAppid(appIdList.get(i));
									quotaFlow.setAdpid(posIdList.get(i1));
									quotaFlow.setVersions(versionList.get(i2));
									quotaFlow.setChannel1(channel1);
									quotaFlow.setChannel2(StringUtils.EMPTY);
									quotaFlow.setFlowId(Constants.IWANVI_UUID);
									quotaFlow.setCreHour(0);

									paramMap = new HashMap<String, Object>();
									paramMap.put("appId", appIdList.get(i));
									paramMap.put("posId", posIdList.get(i1));
									paramMap.put("channel1", channel1);
									paramMap.put("channel2", StringUtils.EMPTY);
									paramMap.put("version", versionList.get(i2));
									paramMap.put("day", day);
									
									query = StringUtils.buildString(format, appIdList.get(i), posIdList.get(i1), 
											version, timeStart, timeEnt);
									queryUV = StringUtils.buildString(format, appIdList.get(i), posIdList.get(i1), 
											version, timeStartUV, timeEnt);
									saveFlowSourceTask(paramMap, quotaFlow, query, from, to, queryUV, fromUV, hour);
								} catch (Exception e) {
									e.printStackTrace();
									LOG.warn("flowTaskService saveFlowSourceTask exception.", e);
								}

//							}
//						}
					}
				}
			}
			
			LOG.info("flowTaskService complete=========hour: {}, cost: {}, elapsed: {}", 
					hour, PixelConstants.COST_FLOW, (System.currentTimeMillis() - l1));
		} catch (Exception e) {
			LOG.error("flowTaskService quotaTask error. ", e);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void saveFlowSourceTask(Map<String, Object> paramMap, QuotaFlow quotaFlow, 
			String query, int from, int to, String queryUV, int fromUV, String hour) {
		try {
			long l2 = System.currentTimeMillis();
			long exposureCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_EXP, query, from, to);
			long clickCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_CLK, query, from, to);
			long reqCnt = AliyunLogService.getCount(Constants.ALIYUN_LOG_STORE_REQ, query, from, to);

			String tempQueryUV = StringUtils.concat(queryUV, Constants.SIGN_OR, Constants.FORMMAT_QUERYUV);
			long expuv = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_EXP, tempQueryUV, fromUV, to);
			long clkuv = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_CLK, tempQueryUV, fromUV, to);
			long requv = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_REQ, tempQueryUV, fromUV, to);

			String queryCost = StringUtils.concat(query, Constants.SIGN_OR, Constants.FORMMAT_QUERYCOST);
			long cost = AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCost, from, to);
			cost += AliyunLogService.getCountWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCost, from, to);

			PixelConstants.COST_FLOW += cost;
			
			long temp = exposureCnt + clickCnt + cost + reqCnt;
			if (temp == Constants.INTEGER_0) {
				return;
			}
			
			QuotaFlow quotaFlowTemp = quotaFlowMapper.getQuotaFlowByCountKey(paramMap);
			if (quotaFlowTemp == null) {
				quotaFlow.setClk((int) clickCnt);
				quotaFlow.setExp((int) exposureCnt);
				quotaFlow.setInvest(cost);
				quotaFlow.setReq(reqCnt);
				quotaFlow.setExpuv((int) expuv);
				quotaFlow.setClkuv((int) clkuv);
				quotaFlow.setRequv((int) requv);
				
				int result = quotaFlowMapper.insertSelective(quotaFlow);
				if(result <= 0){
					LOG.warn("saveFlowSourceTask insert exception. query: {}, start: {}, end: {}", query, from, to);
				}
			} else {
				quotaFlowTemp.setClk((int) clickCnt + quotaFlowTemp.getClk());
				quotaFlowTemp.setExp((int) exposureCnt + quotaFlowTemp.getExp());
				quotaFlowTemp.setInvest(cost + quotaFlowTemp.getInvest());
				quotaFlowTemp.setReq(reqCnt + quotaFlowTemp.getReq());
				
				expuv = expuv < quotaFlowTemp.getExpuv() ? quotaFlowTemp.getExpuv() : expuv;
				clkuv = clkuv < quotaFlowTemp.getClkuv() ? quotaFlowTemp.getClkuv() : clkuv;
				requv = requv < quotaFlowTemp.getRequv() ? quotaFlowTemp.getRequv() : requv;
				quotaFlowTemp.setExpuv((int) expuv);
				quotaFlowTemp.setClkuv((int) clkuv);
				quotaFlowTemp.setRequv((int) requv);

				int result = quotaFlowMapper.updateByPrimaryKey(quotaFlowTemp);
				if(result <= 0){
					LOG.warn("saveFlowSourceTask update exception. query: {}, start: {}, end: {}", query, from, to);
				} 
			}
			LOG.info("saveFlowSourceTask========hour: {}, clik: {}, exp: {}, cost: {}, req: {}, expuv: {}, clkuv: {}, requv: {}", 
					hour, clickCnt, exposureCnt, cost, reqCnt, expuv, clkuv, requv);

			LOG.debug("saveFlowSourceTask elapsed: {}", (System.currentTimeMillis() - l2), query);
		} catch (Exception e) {
			LOG.error("saveFlowSourceTask error. ", e);
		}
	}
	public void quotaTaskForDay() {
		if (true) {
			quotaTask(null);
			return;
		}
		try {
//			File file = new File("/data/crt/123");
			File file = new File("/data/crt/456");
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			JSONObject jsonObject = null;
			long cost = 0l;
			QuotaFlow quotaFlow = null;
			while ((line = reader.readLine()) != null) {
				line = line.split("===========")[1];
				jsonObject = JSONObject.parseObject(line);
				
				quotaFlow = new QuotaFlow();
				quotaFlow.setCreDay(20190701);
				quotaFlow.setChannel2("");
				quotaFlow.setFlowId("M7nuyi");
				quotaFlow.setChannel1("000000");
				quotaFlow.setCreHour(0);
				
				quotaFlow.setAppid(jsonObject.getString("appid"));
				quotaFlow.setAdpid(jsonObject.getString("adpid"));
				quotaFlow.setVersions(jsonObject.getString("versions"));
				
				quotaFlow.setClk(jsonObject.getIntValue("clk"));
				quotaFlow.setExp(jsonObject.getIntValue("exp"));
				quotaFlow.setInvest(jsonObject.getLongValue("invest"));
				quotaFlow.setReq(jsonObject.getLongValue("req"));
				quotaFlow.setExpuv(jsonObject.getIntValue("expuv"));
				quotaFlow.setClkuv(jsonObject.getIntValue("clkuv"));
				quotaFlow.setRequv(jsonObject.getIntValue("requv"));
				
				int result = quotaFlowMapper.insertSelective(quotaFlow);
				LOG.info("result======={}", result);
			}
			System.out.println(cost);
			reader.close();
		} catch (Exception e) {
			LOG.error("quotaTaskForDay error. ", e);
		}
	}
	
	public static void main(String[] args) {
		DoradoServer server = DoradoServerBuilder.forPort(9320)
				.scanPackages("com.nvwa").springOn().build();
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		SpringContainer.create(applicationContext);
		
		TaskService flowTaskService = (TaskService) applicationContext.getBean("flowTaskService");
//		Map<String, Object> paramMap = null;
//		for (int i = 13; i < 19; i++) {
//			paramMap = new HashMap<String, Object>();
//			paramMap.put("t", i);
//			flowTaskService.quotaTask(paramMap);
//		}

		flowTaskService.quotaTaskForDay();
		LOG.warn("---------complete-------");
		System.exit(-1);
		server.start();
		System.exit(-1);
		
	}
}
