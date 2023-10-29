package com.iwanvi.nvwa.web.service.impl;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.*;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdPositionService;
import com.iwanvi.nvwa.web.service.AppService;
import com.iwanvi.nvwa.web.service.ConsumerDataService;
import com.iwanvi.nvwa.web.util.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConsumerDataServiceImpl implements ConsumerDataService {

	private static final Logger LOG = LoggerFactory.getLogger("ConsumerDataService");
	
	@Autowired
	private QuotaPlatformMapper quotaPlatformMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private AppService appService;
	@Autowired
	private AdPositionService adPositionService;
	@Autowired
	private AppsMapper appsMapper;
    @Autowired
	private AdPositionMapper adPositionMapper;
    @Autowired
    private QuotaFlowMapper quotaFlowMapper;

	private static final String KEY_REDIS_MEDIA_QUOTA_HOUR = "dsp:hour:{0}:{1}:{2}:{3}";// dsp:hour:{quota}:{dspId}:{ymd}:{HH}

	private List<QuotaPlatform> bulidResult(List<QuotaPlatform> result, String startDate, String endDate) {
		Date startDay = DateUtils.parse(startDate, DateUtils.SHORT_FORMAT);
		Date endDay = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		if (DateUtils.getDValue2Day(startDay, endDay) < 0) {
			return null;
		}
		List<QuotaPlatform> resultList = org.assertj.core.util.Lists.newArrayList();
		for (Date curDate = new Date(startDay.getTime()); curDate.compareTo(endDay) <= 0; curDate = DateUtils
				.getNextDaysDate(curDate, 1)) {
			QuotaPlatform quotaPlatform = new QuotaPlatform();
			quotaPlatform.setCreDay(Integer.parseInt(DateUtils.format(curDate, DateUtils.SHORT_FORMAT)));
			resultList.add(quotaPlatform);
		}
		// 把数据放入对应日期索引位置
		if (!CollectionUtils.isEmpty(result)) {
			for (QuotaPlatform quotaPlatform : result) {
				Date curDate = DateUtils.parse(String.valueOf(quotaPlatform.getCreDay()), DateUtils.SHORT_FORMAT);
				long index = DateUtils.getDValue2Day(startDay, curDate);
				resultList.set(new Long(index).intValue(), quotaPlatform);
			}
		}
		return resultList;
	}

	@Override
	public List<QuotaPlatform> consumerData(String startDate, String endDate) {
		Date eDate = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (eDate.compareTo(today) > 0) {
			eDate = today;
		}
		QuotaPlatformExample consumerExample = new QuotaPlatformExample();
		consumerExample.createCriteria().andCreDayBetween(Integer.parseInt(startDate), Integer.parseInt(endDate));
		List<QuotaPlatform> result = quotaPlatformMapper.selecAllByCreday(consumerExample);
//		List<FlowConsumer> list = flowConsumerMapper.selectByExample(new FlowConsumerExample());
//		if (CollectionUtils.isEmpty(list)) {
//			return null;
//		} else if (eDate.equals(today)) {
//			int day = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
//			int hour = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.FORMAT_HOUR));
//			int req = 0;
//			int exp = 0;
//			int clk = 0;
//			int invest = 0;
//			for (FlowConsumer flowConsumer : list) {
//				QuotaPlatform quotaPlatform = new QuotaPlatform();
//				addRedisDataToQuota(flowConsumer.getDspId(), quotaPlatform, day, hour);
//				req += quotaPlatform.getReq();
//				exp += quotaPlatform.getExp();
//				clk += quotaPlatform.getClk();
//				invest += quotaPlatform.getInvestment();
//			}
//			if (result.size() > 0) {
//				QuotaPlatform oldQuota = result.get(result.size() - 1);
//				oldQuota.setExp(oldQuota.getExp() + exp);
//				oldQuota.setReq(oldQuota.getReq() + req);
//				oldQuota.setClk(oldQuota.getClk() + clk);
//				oldQuota.setInvestment(oldQuota.getInvestment() + invest);
//				result.set(result.size() - 1, oldQuota);
//			} else {
//				QuotaPlatform quota = new QuotaPlatform(Integer.valueOf(endDate), req, exp, clk, invest);
//				result.add(quota);
//			}
//		}

		return bulidResult(result, startDate, endDate);
	}

	@Override
	public List<QuotaPlatform> getOneFcByhour(String startDate, String endDate, String mediaId, Integer isAll) {
		Map<String, Object> param = new HashMap<>();
		param.put("dx_media", mediaId);
		param.put("start", Integer.parseInt(startDate));
		param.put("end", Integer.parseInt(endDate));
		if (isAll == 1) {
			param.put("all", "all");
		}
		List<QuotaPlatform> result = quotaPlatformMapper.getOneFcByhour(param);
		int day = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
		int hour = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.FORMAT_HOUR));
		QuotaPlatform quotaPlatform = new QuotaPlatform();
		FlowConsumerExample flowConsumerExample = new FlowConsumerExample();
		flowConsumerExample.createCriteria().andIdEqualTo(Integer.valueOf(mediaId));
		List<FlowConsumer> list = flowConsumerMapper.selectByExample(flowConsumerExample);
		addRedisDataToQuota(list.get(0).getDspId(), quotaPlatform, day, hour);
		if (isAll == 1) {
			QuotaPlatform oldQuota = result.get(0);
			oldQuota.setReq(oldQuota.getReq() + quotaPlatform.getReq());
			oldQuota.setExp(oldQuota.getExp() + quotaPlatform.getExp());
			oldQuota.setClk(oldQuota.getClk() + quotaPlatform.getClk());
			oldQuota.setBid(oldQuota.getBid() + quotaPlatform.getBid());
			oldQuota.setInvestment(oldQuota.getInvestment() + quotaPlatform.getInvestment());
            oldQuota.setTimeout(oldQuota.getTimeout() + quotaPlatform.getTimeout());
            oldQuota.setNobid(oldQuota.getNobid() + quotaPlatform.getNobid());
            oldQuota.setLower(oldQuota.getLower() + quotaPlatform.getLower());
            oldQuota.setOverqps(oldQuota.getOverqps() + quotaPlatform.getOverqps());
            oldQuota.setError(oldQuota.getError() + quotaPlatform.getError());
            oldQuota.setWin(oldQuota.getWin() + quotaPlatform.getWin());
            result.set(0, oldQuota);
        } else {
			Date startDay = DateUtils.parse(startDate, DateUtils.SHORT_FORMAT);
			Date endDay = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
			if (DateUtils.getDValue2Day(startDay, endDay) < 0) {
				return null;
			}
			List<QuotaPlatform> resultList = org.assertj.core.util.Lists.newArrayList();
			for (int i = 0; i <= hour; i++) {
				if (i == hour) {
					quotaPlatform.setCreHour(i);
					resultList.add(quotaPlatform);
					continue;
				}
				QuotaPlatform quotapTemp = new QuotaPlatform();
				quotapTemp.setCreHour(i);
				resultList.add(quotapTemp);
			}
			if (!CollectionUtils.isEmpty(result)) {
				for (QuotaPlatform quotapTemp : result) {
					int index = quotapTemp.getCreHour();
					resultList.set(index, quotapTemp);
				}
			}
			return resultList;
		}
		return result;
	}

	@Override
	public List<QuotaPlatform> getOnePlatformDataByDay(String startDate, String endDate, String mediaId,
			Integer isAll) {
		Date eDate = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (eDate.compareTo(today) > 0) {
			eDate = today;
		}
		Map<String, Object> param = new HashMap<>();
		param.put("dx_media", mediaId);
		param.put("startDate", Integer.parseInt(startDate));
		param.put("endDate", Integer.parseInt(DateUtils.format(eDate, DateUtils.SHORT_FORMAT)));
		if (isAll == 1) {
			param.put("all", "all");
		}
		List<QuotaPlatform> onePlatformDataByDay = quotaPlatformMapper.getOnePlatformDataByDay(param);
		// 判断若是当天则从redis获取当天实时数据
		if (eDate.equals(today)) {
			String ymd = DateUtils.format(eDate, DateUtils.SHORT_FORMAT);
			Calendar calendar = Calendar.getInstance();
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			for (QuotaPlatform quotaPlatform : onePlatformDataByDay) {
				String hourString = hour < 10 ? "0" + hour : hour.toString();
				String hourString1 = hour < 11 ? "0" + (hour - 1) : (hour - 1) + "";
				computeQuotaPlatform(quotaPlatform, ymd, hourString);
				computeQuotaPlatform(quotaPlatform, ymd, hourString1);
			}
		}
		return onePlatformDataByDay;
	}

	private void computeQuotaPlatform(QuotaPlatform quotaPlatform, String ymd, String hourStr) {
		Integer flowConsumerId = quotaPlatform.getCid();
		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(flowConsumerId);
		String dspUUID = flowConsumer.getDspId();
		String reqKey = StringUtils.buildString(KEY_REDIS_MEDIA_QUOTA_HOUR, "req", dspUUID, ymd, hourStr);
		String clkKey = StringUtils.buildString(KEY_REDIS_MEDIA_QUOTA_HOUR, "click", dspUUID, ymd, hourStr);
		String expKey = StringUtils.buildString(KEY_REDIS_MEDIA_QUOTA_HOUR, "display", dspUUID, ymd, hourStr);
		String costKey = StringUtils.buildString(KEY_REDIS_MEDIA_QUOTA_HOUR, "cost", dspUUID, ymd, hourStr);
		String reqValue = redisDao.get(reqKey);
		String clkValue = redisDao.get(clkKey);
		String expValue = redisDao.get(expKey);
		String costValue = redisDao.get(costKey);
		if (StringUtils.isNotBlank(reqValue)) {
			quotaPlatform.setReq(quotaPlatform.getReq() + Integer.parseInt(reqValue));
		}
		if (StringUtils.isNotBlank(clkValue)) {
			quotaPlatform.setClk(quotaPlatform.getClk() + Integer.parseInt(clkValue));
		}
		if (StringUtils.isNotBlank(expValue)) {
			quotaPlatform.setExp(quotaPlatform.getExp() + Integer.parseInt(expValue));
		}
		if (StringUtils.isNotBlank(costValue)) {
			quotaPlatform
					.setInvestment(quotaPlatform.getInvestment() + ((Integer.parseInt(costValue) / 1000) * 1.0f / 100));
		}
	}

	@Override
	public Page<QuotaPlatform> getOnePlatformPageDataByDay(String startDate, String endDate, String mediaId,
			Integer currentPage, Integer pageSize) {
		Date eDate = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (eDate.compareTo(today) > 0) {
			eDate = today;
		}
		Page<QuotaPlatform> page = null;
		List<QuotaPlatform> result = Lists.newArrayList();
		Map<String, Object> param = new HashMap<>();
		param.put("dx_media", mediaId);
		param.put("startDate", Integer.parseInt(startDate));
		param.put("endDate", Integer.parseInt(DateUtils.format(eDate, DateUtils.SHORT_FORMAT)));
		// param.put("all", "all");
		result = quotaPlatformMapper.getOnePlatformPageDataByDay(param);
		int count = 0;
		if (!CollectionUtils.isEmpty(result)) {
			count = result.size();
		}
		if (currentPage != null && pageSize != null) {
			page = new Page<QuotaPlatform>(count, currentPage, pageSize);
		} else {
			page = new Page<QuotaPlatform>(count);
		}
		param.put("start", page.getStartPosition());
		param.put("limit", page.getPageSize());
		result = quotaPlatformMapper.getOnePlatformPageDataByDay(param);
		if (eDate.equals(today)) {
			if (result.isEmpty()) {
				QuotaPlatform quotaPlatform = new QuotaPlatform();
				quotaPlatform.setCreDay(Integer.valueOf(DateUtils.format(eDate, DateUtils.SHORT_FORMAT)));
				quotaPlatform.setCid(Integer.valueOf(mediaId));
				result.add(quotaPlatform);
			}
			QuotaPlatform quotaPlatform = result.get(result.size() - 1);
			String ymd = DateUtils.format(eDate, DateUtils.SHORT_FORMAT);
			Calendar calendar = Calendar.getInstance();
			Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
			String hourString = hour < 10 ? "0" + hour : hour.toString();
			String hourString1 = hour < 11 ? "0" + (hour - 1) : (hour - 1) + "";
			computeQuotaPlatform(quotaPlatform, ymd, hourString);
			computeQuotaPlatform(quotaPlatform, ymd, hourString1);
			// quotaPlatform.setCpm(quotaPlatform.getCpm());
			// quotaPlatform.setCpc(quotaPlatform.getCpc());
		}
		page.bindData(result);
		return page;
	}

	@Autowired
	private FlowConsumerMapper flowConsumerMapper;

	@Override
	public Map<String, Object> getPlatformsDataByHour(String startDate, String endDate, String mediaIds, String type) {
		List<QuotaPlatform> result = Lists.newArrayList();
		List<Integer> ids = Arrays.asList(mediaIds.split(",")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		Map<String, Object> params = new HashMap<>();
		params.put("list", ids);
		params.put("start", startDate);
		params.put("end", endDate);
		result = quotaPlatformMapper.getPlatformsDataByHour(params);
		FlowConsumerExample flowConsumerExample = new FlowConsumerExample();
		flowConsumerExample.createCriteria().andIdIn(ids);
		List<FlowConsumer> selectByExample = flowConsumerMapper.selectByExample(flowConsumerExample);
		List<String> names = FluentIterable.from(selectByExample).transform((FlowConsumer flowConsumer) -> {
			return flowConsumer.getConsumerName();
		}).toList();
		List<String> dspIds = Lists.transform(selectByExample, FlowConsumer::getDspId);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
		List<Integer> categories = new ArrayList<>();
        int dayHour = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.FORMAT_HOUR));
		for (int i = 0; i <= dayHour; i++) {
			categories.add(i);
		}
        String query = buildQuery(StringUtils.list2str(dspIds), null, null);
        Map<String, QuotaPlatform> multiPlat = getTodayQuotaFromLogWithGroup(query, "dspId");
		for (String name : names) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("type", "line");
			List<Object> dataList = new ArrayList<Object>();

			for (Integer hour : categories) {
				Optional<QuotaPlatform> Optional = result.stream()
						.filter(item -> item.getConsumerName().equals(name) && item.getCreHour() == hour).findFirst();
				if (Optional.isPresent()) {
					// 存在
					QuotaPlatform quotaPlatform = Optional.get();
					switch (type) {
					case "req":
						dataList.add(quotaPlatform.getReq());
                        break;
					case "exp":
						dataList.add(quotaPlatform.getExp());
						break;
					case "clk":
						dataList.add(quotaPlatform.getClk());
						break;
					case "bid":
						dataList.add(quotaPlatform.getBid());
						break;
					case "timeout":
						dataList.add(quotaPlatform.getTimeout());
						break;
					case "nobid":
						dataList.add(quotaPlatform.getNobid());
						break;
					case "lower":
						dataList.add(quotaPlatform.getLower());
						break;
					case "overqps":
						dataList.add(quotaPlatform.getOverqps());
						break;
					case "error":
						dataList.add(quotaPlatform.getError());
						break;
					case "win":
						dataList.add(quotaPlatform.getWin());
						break;
					case "clk_rate":
						dataList.add(quotaPlatform.getClk_rate());
						break;
					case "bid_rate":
						dataList.add(quotaPlatform.getBid_rate());
						break;
					case "exp_rate":
						dataList.add(quotaPlatform.getExp_rate());
						break;
					default:
						throw new ServiceException("请选择字段");
					}
				} else if (new SimpleDateFormat("HH").format(new Date()).equals(String.valueOf(hour))) {
					FlowConsumerExample consumerExample = new FlowConsumerExample();
					consumerExample.createCriteria().andConsumerNameEqualTo(name);
					List<FlowConsumer> list = flowConsumerMapper.selectByExample(consumerExample);
					String dspId = list.get(0).getDspId();
					QuotaPlatform quotaPlatform = multiPlat.get(dspId);
					if (quotaPlatform == null) {
                        quotaPlatform = new QuotaPlatform();
                    }
					switch (type) {
					case "req":
						dataList.add(quotaPlatform.getReq());
						break;
					case "exp":
						dataList.add(quotaPlatform.getExp());
						break;
					case "clk":
						dataList.add(quotaPlatform.getClk());
						break;
					case "bid":
						dataList.add(quotaPlatform.getBid());
						break;
                    case "timeout":
                        dataList.add(quotaPlatform.getTimeout());
                        break;
                    case "nobid":
                        dataList.add(quotaPlatform.getNobid());
                        break;
                    case "lower":
                        dataList.add(quotaPlatform.getLower());
                        break;
                    case "overqps":
                        dataList.add(quotaPlatform.getOverqps());
                        break;
                    case "error":
                        dataList.add(quotaPlatform.getError());
                        break;
                    case "win":
                        dataList.add(quotaPlatform.getWin());
                        break;
					case "clk_rate":
						dataList.add(quotaPlatform.getClk_rate());
						break;
					case "bid_rate":
						dataList.add(quotaPlatform.getBid_rate());
						break;
					case "exp_rate":
						dataList.add(quotaPlatform.getExp_rate());
						break;
					default:
						throw new ServiceException("请选择字段");
					}
				}else {
					dataList.add(0);
				}
			}
			map.put("data", dataList);
			series.add(map);
		}
		resultMap.put("series", series);
		resultMap.put("categories", categories);

		return resultMap;
	}

	@Override
	public List<QuotaPlatform> getPlatformsDataToday(String startDate, String endDate, String mediaId) {
		List<QuotaPlatform> result = null;
		List<Integer> ids = Arrays.asList(mediaId.split(",")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		Map<String, Object> params = new HashMap<>();
		params.put("list", ids);
		params.put("start", startDate);
		params.put("end", endDate);
		result = quotaPlatformMapper.getPlatformsDataToday(params);
		
		int d = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
		int h = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.FORMAT_HOUR));
		if (CollectionUtils.isEmpty(result)) {
			result = Lists.newArrayList();
			for (Integer id : ids) {
				FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(id);
				QuotaPlatform quotaRedis = new QuotaPlatform();	
				quotaRedis.setConsumerName(flowConsumer.getConsumerName());
				addRedisDataToQuota(flowConsumer.getDspId(), quotaRedis, d, h);
				result.add(quotaRedis);
			}
		} else {
			List<QuotaPlatform> list = Lists.newArrayList();
			for (Integer id : ids) {
				Optional<QuotaPlatform> optional = result.stream()
						.filter(item -> item.getCid() == id).findFirst();
				if (optional.isPresent()) {
					QuotaPlatform quotaPlatform = optional.get();
					QuotaPlatform quotaRedis = new QuotaPlatform();					
					addRedisDataToQuota(quotaPlatform.getDspId(), quotaRedis, d, h);
					quotaPlatform.setReq(quotaPlatform.getReq() + quotaRedis.getReq());
					quotaPlatform.setBid(quotaPlatform.getBid() + quotaRedis.getBid());
					quotaPlatform.setExp(quotaPlatform.getExp() + quotaRedis.getExp());
					quotaPlatform.setClk(quotaPlatform.getClk() + quotaRedis.getClk());
					quotaPlatform.setInvestment(quotaPlatform.getInvestment() + quotaRedis.getInvest()/100000);
                    quotaPlatform.setTimeout(quotaPlatform.getTimeout() + quotaRedis.getTimeout());
                    quotaPlatform.setNobid(quotaPlatform.getNobid() + quotaRedis.getNobid());
                    quotaPlatform.setLower(quotaPlatform.getLower() + quotaRedis.getLower());
                    quotaPlatform.setOverqps(quotaPlatform.getOverqps() + quotaRedis.getOverqps());
                    quotaPlatform.setError(quotaPlatform.getError() + quotaRedis.getError());
                    quotaPlatform.setWin(quotaPlatform.getWin() + quotaRedis.getWin());
                    list.add(quotaPlatform);
				} else {
					FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(id);
					QuotaPlatform quotaRedis = new QuotaPlatform();	
					quotaRedis.setConsumerName(flowConsumer.getConsumerName());
					addRedisDataToQuota(flowConsumer.getDspId(), quotaRedis, d, h);
					list.add(quotaRedis);
				}
			}
			return list;
		}
		return result;
	}

	@Override
	public List<QuotaPlatform> summaryDspDataByDay(String startDate, String endDate, String flowConsumerId) {
		if (StringUtils.isBlank(flowConsumerId)) {
			ExceptionUtils.throwServiceException("平台不能为空");
		}
		if (StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)) {
			ExceptionUtils.throwServiceException("起始和结束日期不能为空");
		}

		int startCreDay = NumberUtils.toInt(startDate);
		int endCreDay = NumberUtils.toInt(endDate);

		if (endCreDay < startCreDay) {
			ExceptionUtils.throwServiceException("查询结束日期必须大于起始日期");
		}

		FlowConsumerExample fce = new FlowConsumerExample();
		fce.createCriteria().andIdEqualTo(NumberUtils.toInt(flowConsumerId));

		List<FlowConsumer> dspList = flowConsumerMapper.selectByExample(fce);
		FlowConsumer dsp = dspList != null && !dspList.isEmpty() ? dspList.get(0) : null;

		if (dsp == null)
			ExceptionUtils.throwServiceException("指定dsp平台不存在, id: " + flowConsumerId);

		String dspId = dsp.getDspId();
		QuotaPlatformExample example = new QuotaPlatformExample();
		example.createCriteria().andCreDayBetween(startCreDay, endCreDay).andPlatformIdEqualTo(dspId);
		List<QuotaPlatform> quotaDspList = quotaPlatformMapper.selecAllByCreday(example);

		Map<Integer, QuotaPlatform> quotaDspMapper = new HashMap<>();
		quotaDspList.forEach(q -> quotaDspMapper.put(q.getCreDay(), q));

		int today = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.SHORT_FORMAT));
		int hour = NumberUtils.toInt(DateUtils.format(new Date(), DateUtils.FORMAT_HOUR));

		List<QuotaPlatform> resultQuotaList = new ArrayList<>();
		// 如果查詢截至日期大于等于今天，则需要获取redis中当前小时的数据加到今天数据上
		Date sDay = DateUtils.parse(startDate, DateUtils.SHORT_FORMAT);
		Date eDay = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		for (Date sDate = sDay; sDate.compareTo(eDay) <= 0; sDate = DateUtils.getNextDaysDate(sDate,
				1)) {
			String day = DateUtils.format(sDate, DateUtils.SHORT_FORMAT);
			if (!quotaDspMapper.containsKey(Integer.parseInt(day))) {
				resultQuotaList.add(new QuotaPlatform(Integer.parseInt(day)));
			} else {
				resultQuotaList.add(quotaDspMapper.get(Integer.parseInt(day)));
			}
		}

		if (endCreDay < today)
			return resultQuotaList;

		resultQuotaList.stream().filter(q -> q.getCreDay() == today).forEach(q -> {
			addRedisDataToQuota(dspId, q, today, hour);
		});
		return resultQuotaList;
	}

    @Override
    public List<QuotaPlatform> sumByDay(String pid, String appids, String adpids, Integer sDate, Integer eDate) {
	    List<QuotaPlatform> resultList = Lists.newArrayList();
	    if (sDate != null && eDate != null && StringUtils.isNotBlank(pid)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            paramMap.put("pid", pid);
            if (StringUtils.isNotBlank(appids)) {
                List<String> appList = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("appids", appList);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> adpList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("adpids", adpList);
            }
            List<QuotaPlatform> quotaList = quotaPlatformMapper.sumByDay(paramMap);
            Map<Integer, QuotaPlatform> dayMap = Maps.newHashMap();
            if (!CollectionUtils.isEmpty(quotaList)) {
                for (QuotaPlatform quota : quotaList) {
                    dayMap.put(quota.getCreDay(), quota);
                }
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDay = sDay; startDay.compareTo(eDay) <= 0; startDay = DateUtils.getNextDaysDate(startDay,
                    1)) {
                String day = DateUtils.format(startDay, DateUtils.SHORT_FORMAT);
                if (!dayMap.containsKey(Integer.parseInt(day))) {
                    dayMap.putIfAbsent(Integer.parseInt(day),new QuotaPlatform(Integer.parseInt(day)));
                }
            }
            if (eDate >= Integer.parseInt(today)) {
                QuotaPlatform quota = dayMap.get(Integer.parseInt(today));
                String query = buildQuery(pid, appids, adpids);
                QuotaPlatform todayQuota = getQuotaFromLogForSinglePlat(query);
                quota.setReq(quota.getReq() + todayQuota.getReq());
                quota.setExp(quota.getExp() + todayQuota.getExp());
                quota.setClk(quota.getClk() + todayQuota.getClk());
                quota.setInvest(quota.getInvest() + todayQuota.getInvest());
                quota.setBid(quota.getBid() + todayQuota.getBid());
                quota.setTimeout(quota.getTimeout() + todayQuota.getTimeout());
                quota.setNobid(quota.getNobid() + todayQuota.getNobid());
                quota.setLower(quota.getLower() + todayQuota.getLower());
                quota.setOverqps(quota.getOverqps() + todayQuota.getOverqps());
                quota.setError(quota.getError() + todayQuota.getError());
                quota.setWin(quota.getWin() + todayQuota.getWin());
            }
            resultList = Lists.newArrayList(dayMap.values());
            resultList.sort((o1, o2) -> o1.getCreDay() < o2.getCreDay() ? -1 : 1);
        }
        return resultList;
    }

    @Override
    public List<QuotaPlatform> sumByDayWithGroup(String pids, String appids, String adpids, String group, Integer sDate, Integer eDate) {
        List<QuotaPlatform> resultList = Lists.newArrayList();

        if (sDate != null && eDate != null && StringUtils.isNotBlank(pids)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if (StringUtils.isNotBlank(group)) {
                paramMap.put("groupItem", group);
            } else {
                paramMap.put("groupItem", "platform_id");
            }
            List<String> pList = StringUtils.str2List(pids, Constants.SIGN_COMMA);
            paramMap.put("pids", pList);
            if (StringUtils.isNotBlank(appids)) {
                List<String> appList = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("appids", appList);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> adpList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("adpids", adpList);
            }
            resultList = quotaPlatformMapper.sumByDayWithGroup(paramMap);
//            //多平台请求从流量源表里取
//            if (!CollectionUtils.isEmpty(pList) && pList.size() > 1) {
//                paramMap.put("pids", null);
//                paramMap.put("apps", null);
//                if ("platform_id".equals(paramMap.get("groupItem"))) {
//                    paramMap.put("groupItem", null);
//                }
//                if (StringUtils.isNotBlank(appids)) {
//                    List<String> appList = StringUtils.str2List(appids, Constants.SIGN_COMMA);
//                    paramMap.put("apps", appList);
//                }
//                if (StringUtils.isNotBlank(adpids)) {
//                    List<String> adpList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
//                    paramMap.put("pids", adpList);
//                }
//                List<QuotaFlow> flowList = quotaFlowMapper.sumWithGroup(paramMap);
//                for (QuotaPlatform plat : resultList) {
//                    if ("platform_id".equals(group)) {
//                        plat.setReq(flowList.get(Constants.INTEGER_0).getReq());
//                    } else {
//                        Optional<QuotaFlow> opt = flowList.stream()
//                                .filter(item -> item.getItemId().equals(plat.getItemId())).findFirst();
//                        if (opt.isPresent()) {
//                            plat.setReq(opt.get().getReq());
//                        }
//                    }
//                }
//            }
            List<String> itemList = Lists.transform(resultList, QuotaPlatform::getItemId);
            List<String> list = Lists.newArrayList();
            String logGroup = StringUtils.EMPTY;
            if ("appid".equals(group)) {
                if (StringUtils.isNotBlank(appids)) {
                    list = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                } else {
                    List<Apps> appList = appService.getAll();
                    if (!CollectionUtils.isEmpty(appList)) {
                        list = Lists.transform(appList, Apps::getAppId);
                    }
                }
                logGroup = "appId";
            } else if ("adpid".equals(group)) {
                if (StringUtils.isNotBlank(adpids)) {
                    list = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                } else {
                    List<AdPosition> adpList = Lists.newArrayList();
                    if (StringUtils.isBlank(appids)) {
                        adpList = adPositionService.selectForList(null);
                    } else {
                        adpList = adPositionService.getPositionByApp(appids);
                    }
                    if (!CollectionUtils.isEmpty(adpList)) {
                        list = Lists.transform(adpList, AdPosition::getUuid);
                    }
                }
                logGroup = "posId";
            } else if ("platform_id".equals(group)) {
                if (StringUtils.isNotBlank(pids)) {
                    list = StringUtils.str2List(pids, Constants.SIGN_COMMA);
                } else {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andConsumerTypeEqualTo(Constants.FC_TYPE_DSP);
                    List<FlowConsumer> dsps = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(dsps)) {
                        list = Lists.transform(dsps, FlowConsumer::getDspId);
                    }
                }
                logGroup = "dspId";
            } else {//不分组的是单平台报表
                list = Arrays.asList(pids);
            }
            for (String item : list) {
                if (!itemList.contains(item)) {
                    QuotaPlatform quota = new QuotaPlatform();
                    quota.setItemId(item);
                    resultList.add(quota);
                }
            }
            QuotaPlatform singlePlat = null;
            Map<String, QuotaPlatform> multiPlat = null;
            String query = buildQuery(pids, appids, adpids);
            if (eDate >= Integer.parseInt(today)) {
                if (StringUtils.isNotBlank(logGroup)) {
                    if (!CollectionUtils.isEmpty(pList) && pList.size() > 1) {
                        multiPlat = getTodayQuotaFromLogWithGroup(query,logGroup);
                    } else {
                        multiPlat = getQuotaFromLogWithGroupForSinglePlat(query,logGroup);
                    }
                } else {
                    singlePlat = getQuotaFromLogForSinglePlat(query);
                }
            }
            for (QuotaPlatform quota : resultList) {
                if (StringUtils.isBlank(quota.getItemId())) {
                    continue;
                }
                if ("appid".equals(group)) {
                    AppsExample example = new AppsExample();
                    example.createCriteria().andAppIdEqualTo(quota.getItemId());
                    List<Apps> apps = appsMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(apps)) {
                        quota.setAppName(apps.get(Constants.INTEGER_0).getAppName());
                    }
                    if (eDate >= Integer.parseInt(today) && multiPlat != null) {
                        QuotaPlatform todayQuota = multiPlat.get(quota.getItemId());
                        if (todayQuota != null) {
                            addQuota(quota, todayQuota);
                        }
                    }
                } else if ("adpid".equals(group)) {
                    AdPositionExample example = new AdPositionExample();
                    example.createCriteria().andUuidEqualTo(quota.getItemId());
                    List<AdPosition> adps = adPositionMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(adps)) {
                        AdPosition position = adps.get(Constants.INTEGER_0);
                        Apps app = appsMapper.selectByPrimaryKey(position.getAppId());
                        String name = position.getName();
                        if (app != null) {
                            name += "-" + app.getAppName();
                        }
                        quota.setAdpName(name);
                    }
                    if (eDate >= Integer.parseInt(today) && multiPlat != null) {
                        QuotaPlatform todayQuota = multiPlat.get(quota.getItemId());
                        if (todayQuota != null) {
                            addQuota(quota, todayQuota);
                        }
                    }
                } else if ("platform_id".equals(group)) {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andDspIdEqualTo(quota.getItemId());
                    List<FlowConsumer> platformList = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(platformList)) {
                        quota.setDspName(platformList.get(Constants.INTEGER_0).getConsumerName());
                    }
                    if (eDate >= Integer.parseInt(today) && multiPlat != null) {
                        QuotaPlatform todayQuota = multiPlat.get(quota.getItemId());
                        if (todayQuota != null) {
                            addQuota(quota, todayQuota);
                        }
                    }
                } else {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andDspIdEqualTo(pids);
                    List<FlowConsumer> platformList = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(platformList)) {
                        quota.setItemName(platformList.get(Constants.INTEGER_0).getConsumerName());
                    }
                    if (eDate >= Integer.parseInt(today) && singlePlat != null) {
                        addQuota(quota, singlePlat);
                    }
                }
            }
            resultList = resultList.stream().filter(item -> item.getReq() > 0 || item.getExp() > 0 || item.getClk() > 0)
                    .collect(Collectors.toList());
        }
        return resultList;
    }
    private void addQuota(QuotaPlatform quota, QuotaPlatform todayQuota) {
        quota.setReq(quota.getReq() + todayQuota.getReq());
        quota.setExp(quota.getExp() + todayQuota.getExp());
        quota.setClk(quota.getClk() + todayQuota.getClk());
        quota.setInvest(quota.getInvest() + todayQuota.getInvest());
        quota.setBid(quota.getBid() + todayQuota.getBid());
        quota.setTimeout(quota.getTimeout() + todayQuota.getTimeout());
        quota.setNobid(quota.getNobid() + todayQuota.getNobid());
        quota.setLower(quota.getLower() + todayQuota.getLower());
        quota.setOverqps(quota.getOverqps() + todayQuota.getOverqps());
        quota.setError(quota.getError() + todayQuota.getError());
        quota.setWin(quota.getWin() + todayQuota.getWin());
    }

    @Override
    public List<QuotaPlatform> detailReport(String id, String pids, String appids, String adpids, String group,
                                            Integer sDate, Integer eDate) {
        List<QuotaPlatform> resultList = Lists.newArrayList();
        if (sDate != null && eDate != null) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            if ("appid".equals(group)) {
                appids = id;
            } else if ("adpid".equals(group)) {
                adpids = id;
            } else if ("platform_id".equals(group)) {
                pids = id;
            }
            List<String> pList = StringUtils.str2List(pids, Constants.SIGN_COMMA);
            paramMap.put("pids", pList);
            if (StringUtils.isNotBlank(appids)) {
                List<String> appIds = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("appids", appIds);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> pidList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("adpids", pidList);
            }
            resultList = quotaPlatformMapper.detailByDay(paramMap);
            //多平台请求从流量源表里取
//            if (!CollectionUtils.isEmpty(pList) && pList.size() > 1) {
//                paramMap.put("pids", null);
//                paramMap.put("apps", null);
//                if (StringUtils.isNotBlank(appids)) {
//                    List<String> appList = StringUtils.str2List(appids, Constants.SIGN_COMMA);
//                    paramMap.put("apps", appList);
//                }
//                if (StringUtils.isNotBlank(adpids)) {
//                    List<String> adpList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
//                    paramMap.put("pids", adpList);
//                }
//                List<QuotaFlow> flowList = quotaFlowMapper.detailReport(paramMap);
//                for (QuotaPlatform plat : resultList) {
//                    if ("platform_id".equals(group)) {
//                        plat.setReq(flowList.get(Constants.INTEGER_0).getReq());
//                    } else {
//                        Optional<QuotaFlow> opt = flowList.stream()
//                                .filter(item -> item.getCreDay().equals(plat.getCreDay())).findFirst();
//                        if (opt.isPresent()) {
//                            plat.setReq(opt.get().getReq());
//                        }
//                    }
//                }
//            }
            Map<String, QuotaPlatform> quotaDayMap = Maps.newHashMap();
            for (QuotaPlatform report : resultList) {
                quotaDayMap.put(report.getCreDay().toString(), report);
            }
            Date sDay = DateUtils.parse(sDate.toString(), DateUtils.SHORT_FORMAT);
            Date eDay = DateUtils.parse(eDate.toString(), DateUtils.SHORT_FORMAT);
            for (Date startDate = sDay; startDate.compareTo(eDay) <= 0; startDate = DateUtils.getNextDaysDate(startDate,
                    1)) {
                String day = DateUtils.format(startDate, DateUtils.SHORT_FORMAT);
                if (!quotaDayMap.containsKey(day)) {
                    QuotaPlatform quota = new QuotaPlatform();
                    quota.setCreDay(Integer.parseInt(day));
                    quotaDayMap.put(day, quota);
                }
            }
            if (eDate >= Integer.parseInt(today)) {
                QuotaPlatform quota = quotaDayMap.get(today);
                String query = StringUtils.EMPTY;
                String logGroup = StringUtils.EMPTY;
                if ("appid".equals(group)) {
                    query = buildQuery(pids, id, adpids);
                    logGroup = "appId";
                } else if ("adpid".equals(group)) {
                    query = buildQuery(pids, appids, id);
                    logGroup = "posId";
                } else if ("platform_id".equals(group)) {
                    query = buildQuery(id, appids, adpids);
                    logGroup = "dspId";
                } else {
                    query = buildQuery(pids, null, null);
                    logGroup = "dspId";
                }
                Map<String, QuotaPlatform> todayMap;
                if (!CollectionUtils.isEmpty(pList) && pList.size() > 1) {
                    todayMap = getTodayQuotaFromLogWithGroup(query, logGroup);
                } else {
                    todayMap = getQuotaFromLogWithGroupForSinglePlat(query, logGroup);
                }
                QuotaPlatform todayQuota = todayMap.get(id);
                quota.setReq(quota.getReq() + todayQuota.getReq());
                quota.setExp(quota.getExp() + todayQuota.getExp());
                quota.setClk(quota.getClk() + todayQuota.getClk());
                quota.setInvest(quota.getInvest() + todayQuota.getInvest());
                quota.setBid(quota.getBid() + todayQuota.getBid());
                quota.setTimeout(quota.getTimeout() + todayQuota.getTimeout());
                quota.setNobid(quota.getNobid() + todayQuota.getNobid());
                quota.setLower(quota.getLower() + todayQuota.getLower());
                quota.setOverqps(quota.getOverqps() + todayQuota.getOverqps());
                quota.setError(quota.getError() + todayQuota.getError());
                quota.setWin(quota.getWin() + todayQuota.getWin());
            }
            resultList = Lists.newArrayList(quotaDayMap.values());
            for (QuotaPlatform report : resultList) {
                if ("appid".equals(group)) {
                    AppsExample example = new AppsExample();
                    example.createCriteria().andAppIdEqualTo(id);
                    List<Apps> apps = appsMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(apps)) {
                        report.setItemName(apps.get(Constants.INTEGER_0).getAppName());
                    }
                } else if ("adpid".equals(group)) {
                    AdPositionExample example = new AdPositionExample();
                    example.createCriteria().andUuidEqualTo(id);
                    List<AdPosition> adps = adPositionMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(adps)) {
                        report.setItemName(adps.get(Constants.INTEGER_0).getName());
                    }
                } else if ("platform_id".equals(group)) {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andDspIdEqualTo(id);
                    List<FlowConsumer> platformList = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(platformList)) {
                        report.setItemName(platformList.get(Constants.INTEGER_0).getConsumerName());
                    }
                } else {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andDspIdEqualTo(pids);
                    List<FlowConsumer> platformList = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(platformList)) {
                        report.setItemName(platformList.get(Constants.INTEGER_0).getConsumerName());
                    }
                }
            }
            resultList.sort((o1, o2) -> o1.getCreDay() < o2.getCreDay() ? -1 : 1);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> sumItemByDay(String pids, String appids, String adpids, Integer sDate, Integer eDate,
                                            String item, String group) {
        Map<String, Object> resultMap = Maps.newHashMap();
        if (sDate != null && eDate != null && StringUtils.isNotBlank(item) && StringUtils.isNotBlank(group)) {
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("sDate", sDate);
            paramMap.put("eDate", eDate);
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            paramMap.put("item", item);
            paramMap.put("groupItem", group);
            List<String> pList = StringUtils.str2List(pids, Constants.SIGN_COMMA);
            paramMap.put("pids", pList);
            if (StringUtils.isNotBlank(appids)) {
                List<String> appList = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                paramMap.put("appids", appList);
            }
            if (StringUtils.isNotBlank(adpids)) {
                List<String> adpList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                paramMap.put("adpids", adpList);
            }
            List<QuotaPlatform> quotaList = quotaPlatformMapper.sumItemByDay(paramMap);
            //多平台请求从流量源表里取
//            if (!CollectionUtils.isEmpty(pList) && pList.size() > 1 && "req".equals(item)) {
//				if ("platform_id".equals(paramMap.get("groupItem"))) {
//					paramMap.put("groupItem", null);
//				}
//                paramMap.put("pids", null);
//                paramMap.put("apps", null);
//                if (StringUtils.isNotBlank(appids)) {
//                    List<String> appList = StringUtils.str2List(appids, Constants.SIGN_COMMA);
//                    paramMap.put("apps", appList);
//                }
//                if (StringUtils.isNotBlank(adpids)) {
//                    List<String> adpList = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
//                    paramMap.put("pids", adpList);
//                }
//                List<QuotaFlow> flowList = quotaFlowMapper.sumWithDateAndGroup(paramMap);
//                for (QuotaPlatform plat : quotaList) {
//                    if ("platform_id".equals(group)) {
//                        plat.setReq(flowList.get(Constants.INTEGER_0).getReq());
//                    } else {
//                        Optional<QuotaFlow> opt = flowList.stream()
//                                .filter(it -> it.getCreDay().equals(plat.getCreDay())
//                                        && it.getItemId().equals(plat.getItemId())).findFirst();
//                        if (opt.isPresent()) {
//                            plat.setReq(opt.get().getReq());
//                        }
//                    }
//                }
//            }
            List<String> list = Lists.newArrayList();
            String logGroup = StringUtils.EMPTY;
            if ("platform_id".equals(group)) {
                if (StringUtils.isNotBlank(pids)) {
                    list = StringUtils.str2List(pids, Constants.SIGN_COMMA);
                } else {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andConsumerTypeEqualTo(Constants.FC_TYPE_DSP);
                    List<FlowConsumer> dsps = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(dsps)) {
                        list = Lists.transform(dsps, FlowConsumer::getConsumerUuid);
                    }
                }
                logGroup = "dspId";
            } else if ("appid".equals(group)) {
                if (StringUtils.isNotBlank(appids)) {
                    list = StringUtils.str2List(appids, Constants.SIGN_COMMA);
                } else {
                    List<Apps> appList = appService.getAll();
                    if (!CollectionUtils.isEmpty(appList)) {
                        list = Lists.transform(appList, Apps::getAppId);
                    }
                }
                logGroup = "appId";
            } else if ("adpid".equals(group)) {
                if (StringUtils.isNotBlank(adpids)) {
                    list = StringUtils.str2List(adpids, Constants.SIGN_COMMA);
                } else {
                    List<AdPosition> adpList = Lists.newArrayList();
                    if (StringUtils.isBlank(appids)) {
                        adpList = adPositionService.selectForList(null);
                    } else {
                        adpList = adPositionService.getPositionByApp(appids);
                    }
                    if (!CollectionUtils.isEmpty(adpList)) {
                        list = Lists.transform(adpList, AdPosition::getUuid);
                    }
                }
                logGroup = "posId";
            }
            List<String> dayList = DateUtils.getDatesByInterval(sDate.toString(), eDate.toString(),
                    DateUtils.SHORT_FORMAT);
            String query = buildQuery(pids, appids, adpids);
            Map<String, QuotaPlatform> multiPlat = Maps.newHashMap();
            if (eDate >= Integer.parseInt(today)) {
                multiPlat = getTodayQuotaFromLogWithGroup(query, logGroup);
            }
            List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
            for (String itemId : list) {
                String name = "";
                if ("platform_id".equals(group)) {
                    FlowConsumerExample example = new FlowConsumerExample();
                    example.createCriteria().andDspIdEqualTo(itemId);
                    List<FlowConsumer> plist = flowConsumerMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(plist)) {
                        name = plist.get(Constants.INTEGER_0).getConsumerName();
                    }
                } else if ("appid".equals(group)) {
                    AppsExample example = new AppsExample();
                    example.createCriteria().andAppIdEqualTo(itemId);
                    List<Apps> appsList = appsMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(appsList)) {
                        name = appsList.get(Constants.INTEGER_0).getAppName();
                    }
                } else if ("adpid".equals(group)) {
                    AdPositionExample example = new AdPositionExample();
                    example.createCriteria().andUuidEqualTo(itemId);
                    List<AdPosition> positions = adPositionMapper.selectByExample(example);
                    if (!CollectionUtils.isEmpty(positions)) {
                        name = positions.get(Constants.INTEGER_0).getName();
                    }
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", name);
                map.put("type", "line");
                List<Object> dataList = new ArrayList<Object>();
                for (String day : dayList) {
                    Optional<QuotaPlatform> op = quotaList.stream().filter(itm -> itm.getCreDay() == Integer.parseInt(day)
                            && itemId.equals(itm.getItemId())).findFirst();
                    QuotaPlatform quotaPlatform;
                    if (op.isPresent()) {
                        // 存在
                        quotaPlatform = op.get();
                    } else {
                        quotaPlatform = new QuotaPlatform();
                    }
                    if (day.equals(today)) {
                        QuotaPlatform quotaNow = multiPlat.get(itemId);
                        if (quotaNow != null) {
                            addQuota(quotaPlatform, quotaNow);
                        }
                    }
                    switch (item) {
                        case "req":
                            dataList.add(quotaPlatform.getReq());
                            break;
                        case "exp":
                            dataList.add(quotaPlatform.getExp());
                            break;
                        case "clk":
                            dataList.add(quotaPlatform.getClk());
                            break;
                        case "bid":
                            dataList.add(quotaPlatform.getBid());
                            break;
                        case "timeout":
                            dataList.add(quotaPlatform.getTimeout());
                            break;
                        case "nobid":
                            dataList.add(quotaPlatform.getNobid());
                            break;
                        case "lower":
                            dataList.add(quotaPlatform.getLower());
                            break;
                        case "overqps":
                            dataList.add(quotaPlatform.getOverqps());
                            break;
                        case "error":
                            dataList.add(quotaPlatform.getError());
                            break;
                        case "win":
                            dataList.add(quotaPlatform.getWin());
                            break;
                        case "clk_rate":
                            dataList.add(quotaPlatform.getClk_rate());
                            break;
                        case "bid_rate":
                            dataList.add(quotaPlatform.getBid_rate());
                            break;
                        case "exp_rate":
                            dataList.add(quotaPlatform.getExp_rate());
                            break;
                        default:
                            throw new ServiceException("请选择字段");
                    }
                }
                map.put("data", dataList);
                series.add(map);
            }
            resultMap.put("series", series);
            resultMap.put("categories", dayList);
        }
        return resultMap;
    }
    
    private QuotaPlatform getQuotaFromLogForSinglePlat(String query) {
    	QuotaPlatform quota = new QuotaPlatform();
    	int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
    	int to = (int) (new Date().getTime() / 1000);
    	String queryCost = StringUtils.concat("*| SELECT sum(cost) ", query);
    	String queryDsp = StringUtils.concat("*| select count(*) num, sum (timeout) timeout,sum (nobid) nobid,sum (win) win," +
                "sum (error) error,sum (qps) qps,sum (lower) lower,sum (bid) bid", query, " and timeout in (0,1) " +
                "and win in (0,1) and nobid in (0,1) and error in (0,1) and qps in (0,1) and lower in (0,1) and bid in (0,1)");
    	String queryCommon = StringUtils.concat("*| select count(*) ", query);

    	Long clk = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCommon, from, to);
    	Long exp = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCommon, from, to);
    	Long cost = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCost, from, to);

        List<Map<String, Object>> dspList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_DSP, queryDsp, from, to);

    	quota.setExp(exp.intValue());
    	quota.setClk(clk.intValue());
    	quota.setInvest(cost);
        if (!CollectionUtils.isEmpty(dspList)) {
            Map<String, Object> itemMap = dspList.get(Constants.INTEGER_0);
            quota.setTimeout(NvwaUtils.obj2int(itemMap.get("timeout")));
            quota.setNobid(NvwaUtils.obj2long(itemMap.get("nobid"),0l));
            quota.setError(NvwaUtils.obj2int(itemMap.get("error")));
            quota.setWin(NvwaUtils.obj2long(itemMap.get("win"),0l));
            quota.setOverqps(NvwaUtils.obj2long(itemMap.get("qps"),0l));
            quota.setLower(NvwaUtils.obj2int(itemMap.get("lower")));
            quota.setBid(NvwaUtils.obj2int(itemMap.get("bid")));
            quota.setReq(NvwaUtils.obj2long(itemMap.get("num"),0l));
            quota.setReq(quota.getReq() - quota.getOverqps());
        }
    	return quota;
    }
    private QuotaPlatform getTodayQuotaFromLog(String query) {
    	QuotaPlatform quota = new QuotaPlatform();
    	int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
    	int to = (int) (new Date().getTime() / 1000);
    	String queryCost = StringUtils.concat("*| SELECT sum(cost) ", query);
    	String queryDsp = StringUtils.concat("*| select count(*) num, sum (timeout) timeout,sum (nobid) nobid,sum (win) win," +
                "sum (error) error,sum (qps) qps,sum (lower) lower,sum (bid) bid", query, " and timeout in (0,1) " +
                "and win in (0,1) and nobid in (0,1) and error in (0,1) and qps in (0,1) and lower in (0,1) and bid in (0,1)");
    	String queryCommon = StringUtils.concat("*| select count(*) ", query);

//    	String queryReq = transReqQuery(query);
//    	Long req = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql("ads-req", queryReq, from, to);
    	Long clk = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCommon, from, to);
    	Long exp = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCommon, from, to);
    	Long cost = AliyunLogUtil.getTodayNumByTypeAndParamsWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryCost, from, to);

        List<Map<String, Object>> dspList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_DSP, queryDsp, from, to);

//    	quota.setReq(req.longValue());
    	quota.setExp(exp.intValue());
    	quota.setClk(clk.intValue());
    	quota.setInvest(cost);
        if (!CollectionUtils.isEmpty(dspList)) {
            Map<String, Object> itemMap = dspList.get(Constants.INTEGER_0);
            quota.setReq(NvwaUtils.obj2long(itemMap.get("num"), 0l));
            quota.setTimeout(NvwaUtils.obj2int(itemMap.get("timeout")));
            quota.setNobid(NvwaUtils.obj2long(itemMap.get("nobid"),0l));
            quota.setError(NvwaUtils.obj2int(itemMap.get("error")));
            quota.setWin(NvwaUtils.obj2long(itemMap.get("win"),0l));
            quota.setOverqps(NvwaUtils.obj2long(itemMap.get("qps"),0l));
            quota.setLower(NvwaUtils.obj2int(itemMap.get("lower")));
            quota.setBid(NvwaUtils.obj2int(itemMap.get("bid")));
            quota.setReq(quota.getReq() - quota.getOverqps());
        }
    	return quota;
    }

//    private String transReqQuery(String query) {
//        String queryReq = "*| select count(*) num ";
//        if (StringUtils.isNotBlank(query) && query.indexOf("dspId in") > 0) {
//            List<String> queryStrList = StringUtils.str2List(query,"dspId in \\(");
//            if (queryStrList.get(1).indexOf(")") + 1 != queryStrList.get(1).length()) {
//                if (queryStrList.get(1).indexOf("and") > 0) {
//                    queryReq += StringUtils.concat(queryStrList.get(0), queryStrList.get(1).substring(queryStrList.get(1).indexOf("and") + 3));
//                } else {
//                    queryReq += StringUtils.concat(queryStrList.get(0), queryStrList.get(1).substring(queryStrList.get(1).indexOf(")") + 1));
//                }
//            }
//        }
//        return queryReq;
//    }

    private Map<String, QuotaPlatform> getTodayQuotaFromLogWithGroup(String query, String group) {
        Map<String, QuotaPlatform> groupMap = Maps.newHashMap();
    	int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
    	int to = (int) (new Date().getTime() / 1000);
    	String queryDsp = StringUtils.concat(" count(*) num, sum(timeout) timeout,sum(win) win,sum(nobid) nobid," +
                        "sum(error) error,sum(qps) qps,sum(lower) lower,sum(bid) bid ", query,
                " and timeout in (0,1) and win in (0,1) and nobid in (0,1) and error in (0,1) and qps in (0,1) " +
                        "and lower in (0,1) and bid in (0,1)");
        if (StringUtils.isNotBlank(group)) {
            queryDsp = group + Constants.SIGN_COMMA + queryDsp;
            queryDsp += " group by ";
            queryDsp += group;
        }
        queryDsp = "*| select " +queryDsp;

    	String queryCommon = StringUtils.concat(" count(*) num ", query);
        if (StringUtils.isNotBlank(group)) {
            queryCommon = group + Constants.SIGN_COMMA + queryCommon;
            queryCommon += " group by ";
            queryCommon += group;
        }
        queryCommon = "*| select " +queryCommon;

        String queryExp = StringUtils.concat(" count(*) num, sum(cost) cost ", query);
        if (StringUtils.isNotBlank(group)) {
            queryExp = group + Constants.SIGN_COMMA + queryExp;
            queryExp += " group by ";
            queryExp += group;
        }
        queryExp = "*| select " + queryExp;


//        String queryReq = transReqQuery(query);
//
//        if (StringUtils.isNotBlank(group) && !"dspId".equals(group)) {
//            queryReq += " group by ";
//            queryReq += group;
//            List<String> queryList = StringUtils.str2List(queryReq, "select");
//            queryReq = StringUtils.concat(queryList.get(0),"select ",group,Constants.SIGN_COMMA, queryList.get(1));
//        }
//        List<Map<String, Object>> reqList = AliyunLogUtil.getQuotaWithSql("ads-req", queryReq, from, to);
        List<Map<String, Object>> expList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryExp, from, to);
        List<Map<String, Object>> clkList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCommon, from, to);
    	List<Map<String, Object>> dspList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_DSP, queryDsp, from, to);
        QuotaPlatform quota;
        if (!CollectionUtils.isEmpty(expList)) {
            for (Map<String, Object> itemMap : expList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaPlatform();
                    }
                    quota.setInvest(NvwaUtils.obj2long(itemMap.get("cost"),0l));
                    quota.setExp(NvwaUtils.obj2int(itemMap.get("num")));
                    groupMap.put(index, quota);
                }
            }
        }
        if (!CollectionUtils.isEmpty(clkList)) {
            for (Map<String, Object> itemMap : clkList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaPlatform();
                    }
                    quota.setClk(NvwaUtils.obj2int(itemMap.get("num")));
                    groupMap.put(index, quota);
                }
            }
        }
//        if (!CollectionUtils.isEmpty(reqList)) {
//            for (Map<String, Object> itemMap : reqList) {
//                if (!"dspId".equals(group)) {
//                    String index = itemMap.get(group).toString();
//                    if (StringUtils.isNotBlank(index)) {
//                        if ( groupMap.get(index) != null ) {
//                            quota = groupMap.get(index);
//                        } else {
//                            quota = new QuotaPlatform();
//                        }
//                        quota.setReq(NvwaUtils.obj2long(itemMap.get("num"),0l));
//                        groupMap.put(index, quota);
//                    }
//                } else {
//                    Long req = NvwaUtils.obj2long(reqList.get(Constants.INTEGER_0).get("num"),Constants.INTEGER_0);
//                    Collection<QuotaPlatform> values = groupMap.values();
//                    for (QuotaPlatform quotaItem : values) {
//                        quotaItem.setReq(req.longValue());
//                    }
//                }
//            }
//        }
    	if (!CollectionUtils.isEmpty(dspList)) {
            for (Map<String, Object> itemMap : dspList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaPlatform();
                    }
                    quota.setReq(NvwaUtils.obj2long(itemMap.get("num"),0l));
                    quota.setTimeout(NvwaUtils.obj2int(itemMap.get("timeout")));
                    quota.setNobid(NvwaUtils.obj2long(itemMap.get("nobid"),0l));
                    quota.setError(NvwaUtils.obj2int(itemMap.get("error")));
                    quota.setWin(NvwaUtils.obj2long(itemMap.get("win"),0l));
                    quota.setOverqps(NvwaUtils.obj2long(itemMap.get("qps"),0l));
                    quota.setLower(NvwaUtils.obj2int(itemMap.get("lower")));
                    quota.setBid(NvwaUtils.obj2int(itemMap.get("bid")));
                    quota.setReq(quota.getReq() - quota.getOverqps());
                    groupMap.put(index, quota);
                }
            }
        }
    	return groupMap;
    }
    private Map<String, QuotaPlatform> getQuotaFromLogWithGroupForSinglePlat(String query, String group) {
        Map<String, QuotaPlatform> groupMap = Maps.newHashMap();
    	int from = (int) (DateUtils.getFirstSecondOfHour(new Date()).getTime() / 1000);
    	int to = (int) (new Date().getTime() / 1000);
    	String queryDsp = StringUtils.concat(" count(*) num, sum(timeout) timeout,sum(win) win,sum(nobid) nobid," +
                        "sum(error) error,sum(qps) qps,sum(lower) lower,sum(bid) bid ", query,
                " and timeout in (0,1) and win in (0,1) and nobid in (0,1) and error in (0,1) and qps in (0,1) " +
                        "and lower in (0,1) and bid in (0,1)");
        if (StringUtils.isNotBlank(group)) {
            queryDsp = group + Constants.SIGN_COMMA + queryDsp;
            queryDsp += " group by ";
            queryDsp += group;
        }
        queryDsp = "*| select " +queryDsp;

    	String queryCommon = StringUtils.concat(" count(*) num ", query);
        if (StringUtils.isNotBlank(group)) {
            queryCommon = group + Constants.SIGN_COMMA + queryCommon;
            queryCommon += " group by ";
            queryCommon += group;
        }
        queryCommon = "*| select " +queryCommon;

        String queryExp = StringUtils.concat(" count(*) num, sum(cost) cost ", query);
        if (StringUtils.isNotBlank(group)) {
            queryExp = group + Constants.SIGN_COMMA + queryExp;
            queryExp += " group by ";
            queryExp += group;
        }
        queryExp = "*| select " + queryExp;

        List<Map<String, Object>> expList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_EXP, queryExp, from, to);
        List<Map<String, Object>> clkList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_CLK, queryCommon, from, to);
    	List<Map<String, Object>> dspList = AliyunLogUtil.getQuotaWithSql(Constants.ALIYUN_LOG_STORE_DSP, queryDsp, from, to);
        QuotaPlatform quota;
        if (!CollectionUtils.isEmpty(expList)) {
            for (Map<String, Object> itemMap : expList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaPlatform();
                    }
                    quota.setInvest(NvwaUtils.obj2long(itemMap.get("cost"),0l));
                    quota.setExp(NvwaUtils.obj2int(itemMap.get("num")));
                    groupMap.put(index, quota);
                }
            }
        }
        if (!CollectionUtils.isEmpty(clkList)) {
            for (Map<String, Object> itemMap : clkList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaPlatform();
                    }
                    quota.setClk(NvwaUtils.obj2int(itemMap.get("num")));
                    groupMap.put(index, quota);
                }
            }
        }
    	if (!CollectionUtils.isEmpty(dspList)) {
            for (Map<String, Object> itemMap : dspList) {
                String index = itemMap.get(group).toString();
                if (StringUtils.isNotBlank(index)) {
                    if ( groupMap.get(index) != null ) {
                        quota = groupMap.get(index);
                    } else {
                        quota = new QuotaPlatform();
                    }
                    quota.setTimeout(NvwaUtils.obj2int(itemMap.get("timeout")));
                    quota.setNobid(NvwaUtils.obj2long(itemMap.get("nobid"),0l));
                    quota.setError(NvwaUtils.obj2int(itemMap.get("error")));
                    quota.setWin(NvwaUtils.obj2long(itemMap.get("win"),0l));
                    quota.setOverqps(NvwaUtils.obj2long(itemMap.get("qps"),0l));
                    quota.setLower(NvwaUtils.obj2int(itemMap.get("lower")));
                    quota.setBid(NvwaUtils.obj2int(itemMap.get("bid")));
                    quota.setReq(NvwaUtils.obj2long(itemMap.get("num"),0l));
                    quota.setReq(quota.getReq() - quota.getOverqps());
                    groupMap.put(index, quota);
                }
            }
        }
    	return groupMap;
    }

    private String buildQuery(String pids, String appids, String adpids) {
        StringBuilder query = new StringBuilder();
        String subQuery = null;
        if (StringUtils.isNotBlank(pids)) {
            subQuery = pids.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and dspId in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        if (StringUtils.isNotBlank(appids)) {
            subQuery = appids.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and appId in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        if (StringUtils.isNotBlank(adpids)) {
            subQuery = adpids.replaceAll(Constants.SIGN_COMMA, "','");
            if (StringUtils.isNotBlank(subQuery)) {
                query.append(" and posId in ('");
                query.append(subQuery);
                query.append("')");
            }
        }
        String queryString = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(query)) {
            queryString = " where " + query.substring(5);
        }
        return queryString;
    }

    private void addRedisDataToQuota(String dspId, QuotaPlatform q, int today, int hour) {
        String query = buildQuery(dspId, null, null);
        QuotaPlatform quota = getQuotaFromLogForSinglePlat(query);
		q.setReq(q.getReq() + quota.getReq());
		q.setExp(q.getExp() + quota.getExp());
		q.setClk(q.getClk() + quota.getClk());
		q.setInvest(q.getInvest() + quota.getInvest());
        q.setTimeout(q.getTimeout() + quota.getTimeout());
        q.setNobid(q.getNobid() + quota.getNobid());
        q.setLower(q.getLower() + quota.getLower());
        q.setOverqps(q.getOverqps() + quota.getOverqps());
        q.setError(q.getError() + quota.getError());
        q.setWin(q.getWin() + quota.getWin());
        q.setBid(q.getBid() + quota.getBid());
    }

}
