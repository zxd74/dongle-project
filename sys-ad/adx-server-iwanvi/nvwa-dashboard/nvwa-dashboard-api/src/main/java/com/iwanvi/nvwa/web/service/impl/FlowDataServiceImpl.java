package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.FlowDataService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

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
public class FlowDataServiceImpl implements FlowDataService {
    private static final Logger logger = LoggerFactory.getLogger(FlowDataServiceImpl.class);

	@Autowired
	private FlowSourceMapper flowSourceMapper;
	@Autowired
	private QuotaFlowMapper quotaFlowMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private AppsMapper appsMapper;
	@Autowired
	private AdPositionMapper adPositionMapper;
    @Autowired
    private AppChannelMapper appChannelMapper;
    @Autowired
    private AppVersionMapper appVersionMapper;

	@Override
	public List<QuotaFlow> getAllData(List<FlowSource> fsList, String startDate, String endDate) {

		List<QuotaFlow> result = Lists.newArrayList();
		List<Integer> ids = FluentIterable.from(fsList).transform((FlowSource flowSource) -> {
			return flowSource.getId();
		}).toList();
		if (CollectionUtils.isEmpty(ids)) {
			return result;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("list", ids);
		paramMap.put("startday", Integer.parseInt(startDate));
		paramMap.put("endday", Integer.parseInt(endDate));
		// paramMap.put("all", "all");
		// sql 问题 W
		result = quotaFlowMapper.getAllFlowDataByUser(paramMap);

		//不要实时数据，太慢了
//		// 取redis当前小时数据
//		int req = 0;
//		int exp = 0;
//		int clk = 0;
//		int invest = 0;
//		for (FlowSource flowSource : fsList) {
//			QuotaFlow quotaFlow = getQuotaByRedis(flowSource.getId().toString(), new Date());
//			req += quotaFlow.getReq();
//			exp += quotaFlow.getExp();
//			clk += quotaFlow.getClk();
//			invest += quotaFlow.getInvestment();
//		}
////		if (result.size() > 0) {
////			QuotaFlow oldQuota = result.get(result.size() - 1);
////			oldQuota.setExp(oldQuota.getExp() + exp);
////			oldQuota.setReq(oldQuota.getReq() + req);
////			oldQuota.setClk(oldQuota.getClk() + clk);
////			oldQuota.setInvestment(oldQuota.getInvestment() + invest);
////			result.set(result.size() - 1, oldQuota);
////		} else {
////		}
//        if (CollectionUtils.isEmpty(result)) {
//		    result = Lists.newArrayList();
//        }
//        QuotaFlow oldQuota = new QuotaFlow();
//        oldQuota.setReq(req);
//        oldQuota.setExp(exp);
//        oldQuota.setClk(clk);
//        oldQuota.setInvestment(invest);
//        oldQuota.setCreDay(Integer.parseInt(endDate));
//        result.add(oldQuota);
        List<QuotaFlow> resultList = formatQuotaFlow(startDate, endDate, result);
		return resultList;
	}

	private List<QuotaFlow> bulidResult(List<QuotaFlow> result, String startDate, String endDate) {
		Map<Integer, QuotaFlow> custQuotaMapper = new HashMap<>();
		result.forEach(a -> {
			custQuotaMapper.put(a.getCreDay(), a);
		});
		int startDay = NumberUtils.toInt(startDate);
		int endDay = NumberUtils.toInt(endDate);
		List<QuotaFlow> resultQuotaList = new ArrayList<>();
		for (int i = startDay; i <= endDay; i++) {
			if (!custQuotaMapper.containsKey(i)) {
				resultQuotaList.add(new QuotaFlow(i));
			} else {
				resultQuotaList.add(custQuotaMapper.get(i));
			}
		}
		return resultQuotaList;
	}

	@Override
	public Page<QuotaFlow> getFlowDataByFlowId(List<FlowSource> fsList, String startDate, String endDate,
			Integer currentPage, Integer pageSize) {
		List<Integer> ids = FluentIterable.from(fsList).transform((FlowSource flowSource) -> {
			return flowSource.getId();
		}).toList();
		if (CollectionUtils.isEmpty(ids)) {
			return new Page<QuotaFlow>(0);
		}
		Page<QuotaFlow> page = null;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("list", ids);
		paramMap.put("startday", Integer.parseInt(startDate));
		paramMap.put("endday", Integer.parseInt(endDate));
		paramMap.put("all", "all");
		// sql 问题 W
		List<QuotaFlow> list = quotaFlowMapper.getAllFlowDataByUser(paramMap);
		List<QuotaFlow> bulidResult = bulidResult(list, startDate, endDate);
		if (CollectionUtils.isEmpty(list)) {
			return new Page<QuotaFlow>(0);
		}
		int count = bulidResult.size();
		if (currentPage != null && pageSize != null) {
			page = new Page<QuotaFlow>(count, currentPage, pageSize);
		} else {
			page = new Page<QuotaFlow>(count);
		}
		paramMap.put("start", page.getStartPosition());
		paramMap.put("limit", page.getPageSize());
		List<QuotaFlow> result = quotaFlowMapper.getAllFlowDataByUser(paramMap);
		// TODO redis取值
		page.bindData(bulidResult(result, startDate, endDate));
		return page;
	}

	@Override
	public List<QuotaFlow> getOneFlowDataByHour(String startDate, String endDate, Integer mediaId, Integer isAll) {
		List<QuotaFlow> result = Lists.newArrayList();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("dx_media", mediaId);
		paramMap.put("start", Integer.parseInt(startDate));
		paramMap.put("end", Integer.parseInt(endDate));
//		if (isAll == 1) {
			paramMap.put("all", "all");
//		}
		result = quotaFlowMapper.getOneFlowDataByHour(paramMap);
		QuotaFlow quotaFlow = getQuotaByRedis(mediaId.toString(), new Date());
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (CollectionUtils.isEmpty(result)) {
		    quotaFlow.setCreHour(hour);
			result.add(quotaFlow);
        } else {
            QuotaFlow oldQuota = result.get(0);
            oldQuota.setCreHour(hour);
			oldQuota.setReq(oldQuota.getReq() == null ? 0 : oldQuota.getReq() + quotaFlow.getReq());
			oldQuota.setExp(oldQuota.getExp() == null ? 0 : oldQuota.getExp() + quotaFlow.getExp());
			oldQuota.setClk(oldQuota.getClk() == null ? 0 : oldQuota.getClk() + quotaFlow.getClk());
			oldQuota.setInvestment(
					oldQuota.getInvestment() > 0 ? 0 : oldQuota.getInvestment() + quotaFlow.getInvestment());
			result.set(0, oldQuota);
        }
		// 增加redis当前小时数据
//		if (isAll == 1) {
//			// 全部
//			QuotaFlow oldQuota = result.get(0);
//			oldQuota.setReq(oldQuota.getReq() == null ? 0 : oldQuota.getReq() + quotaFlow.getReq());
//			oldQuota.setExp(oldQuota.getExp() == null ? 0 : oldQuota.getExp() + quotaFlow.getExp());
//			oldQuota.setClk(oldQuota.getClk() == null ? 0 : oldQuota.getClk() + quotaFlow.getClk());
//			oldQuota.setInvestment(
//					oldQuota.getInvestment() > 0 ? 0 : oldQuota.getInvestment() + quotaFlow.getInvestment());
//			result.set(0, oldQuota);
//		} else {
//			// 分小时数据
//			result.add(quotaFlow);
//
//			Date startDay = DateUtils.parse(startDate, DateUtils.SHORT_FORMAT);
//			Date endDay = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
//			if (DateUtils.getDValue2Day(startDay, endDay) < 0) {
//				return null;
//			}
//			List<QuotaFlow> resultList = Lists.newArrayList();
//			for (int i = 0; i <= 23; i++) {
//				QuotaFlow quotaFlowTemp = new QuotaFlow();
//				quotaFlowTemp.setCreHour(i);
//				resultList.add(quotaFlowTemp);
//			}
//			// 把数据放入对应小时索引位置
//			if (!CollectionUtils.isEmpty(result)) {
//				for (QuotaFlow quotaFlowTemp : result) {
//					int index = quotaFlowTemp.getCreHour();
//					resultList.set(index, quotaFlowTemp);
//				}
//			}
//			return resultList;
//		}

		return result;
	}


    @Override
    public List<QuotaFlow> getOneFlowDataByHour(Integer isAll) {
	    List<QuotaFlow> list = Lists.newArrayList();
	    String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        QuotaFlow quota;
        for (int i = 0; i <= hour; i++) {
            String hourString = i < 10 ? "0" + i : Integer.toString(i);
            String reqKey = StringUtils.buildString(Constants.KEY_REDIS_MEDIA_QUOTA_HOUR, Constants.QUOTA_REQ,
                    Constants.IWANVI_UUID, today, hourString);
            String expKey = StringUtils.buildString(Constants.KEY_REDIS_MEDIA_QUOTA_HOUR, Constants.QUOTA_DISPLAY,
                    Constants.IWANVI_UUID, today, hourString);
            String clkKey = StringUtils.buildString(Constants.KEY_REDIS_MEDIA_QUOTA_HOUR, Constants.QUOTA_CLICK,
                    Constants.IWANVI_UUID, today, hourString);
            String costKey = StringUtils.buildString(Constants.KEY_REDIS_MEDIA_QUOTA_HOUR, Constants.QUOTA_COST,
                    Constants.IWANVI_UUID, today, hourString);
            List<String> keys = Arrays.asList(reqKey, expKey, clkKey, costKey);
            List<String> values = redisDao.multiGet(keys);
            quota = new QuotaFlow();
            String reqValue = values.get(Constants.INTEGER_0);
            String expValue = values.get(Constants.INTEGER_1);
            String clkValue = values.get(Constants.INTEGER_2);
            String costValue = values.get(Constants.INTEGER_3);
            logger.info("key {}, value {}", reqKey, reqValue);
            logger.info("key {}, value {}", expKey, expValue);
            logger.info("key {}, value {}", clkKey, clkValue);
            logger.info("key {}, value {}", costKey, costValue);
            if (StringUtils.isNotBlank(reqValue)) {
                quota.setReq(Long.parseLong(reqValue));
            }
            if (StringUtils.isNotBlank(expValue)) {
                quota.setExp(Integer.parseInt(expValue));
            }
            if (StringUtils.isNotBlank(clkValue)) {
                quota.setClk(Integer.parseInt(clkValue));
            }
            if (StringUtils.isNotBlank(costValue)) {
                quota.setInvest(Long.parseLong(costValue));
            }
            quota.setCreHour(i);
            list.add(quota);
        }
        if (Constants.INTEGER_1 == isAll) {
            quota = new QuotaFlow();
            for (QuotaFlow item : list) {
                quota.setReq(quota.getReq() + item.getReq());
                quota.setExp(quota.getExp() + item.getExp());
                quota.setClk(quota.getClk() + item.getClk());
                quota.setInvest(quota.getInvest() + item.getInvest());
            }
            list = Lists.newArrayList();
            list.add(quota);
        }
	    return list;
    }

    @Override
    public List<QuotaFlow> getOneFlowDataByHour(Integer isAll, String posUuid) {
        List<QuotaFlow> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(posUuid)) {
            String today = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT);
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
            QuotaFlow quota;
            for (int i = 0; i <= hour; i++) {
                String hourString = i < 10 ? "0" + i : Integer.toString(i);
                String reqKey = StringUtils.buildString(Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR, Constants.QUOTA_REQ,
                        posUuid, today, hourString);
                String expKey = StringUtils.buildString(Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR, Constants.QUOTA_DISPLAY,
                        posUuid, today, hourString);
                String clkKey = StringUtils.buildString(Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR, Constants.QUOTA_CLICK,
                        posUuid, today, hourString);
                String costKey = StringUtils.buildString(Constants.KEY_REDIS_ADPOSITION_QUOTA_HOUR, Constants.QUOTA_COST,
                        posUuid, today, hourString);
                List<String> keys = Arrays.asList(reqKey, expKey, clkKey, costKey);
                List<String> values = redisDao.multiGet(keys);
                quota = new QuotaFlow();
                String reqValue = values.get(Constants.INTEGER_0);
                String expValue = values.get(Constants.INTEGER_1);
                String clkValue = values.get(Constants.INTEGER_2);
                String costValue = values.get(Constants.INTEGER_3);
                logger.info("key {}, value {}", reqKey, reqValue);
                logger.info("key {}, value {}", expKey, expValue);
                logger.info("key {}, value {}", clkKey, clkValue);
                logger.info("key {}, value {}", costKey, costValue);
                if (StringUtils.isNotBlank(reqValue)) {
                    quota.setReq(Long.parseLong(reqValue));
                }
                if (StringUtils.isNotBlank(expValue)) {
                    quota.setExp(Integer.parseInt(expValue));
                }
                if (StringUtils.isNotBlank(clkValue)) {
                    quota.setClk(Integer.parseInt(clkValue));
                }
                if (StringUtils.isNotBlank(costValue)) {
                    quota.setInvest(Long.parseLong(costValue));
                }
                quota.setCreHour(i);
                list.add(quota);
            }
            if (Constants.INTEGER_1 == isAll) {
                quota = new QuotaFlow();
                for (QuotaFlow item : list) {
                    quota.setReq(quota.getReq() + item.getReq());
                    quota.setExp(quota.getExp() + item.getExp());
                    quota.setClk(quota.getClk() + item.getClk());
                    quota.setInvest(quota.getInvest() + item.getInvest());
                }
                list = Lists.newArrayList();
                list.add(quota);
            }
        } else {
            list = getOneFlowDataByHour(isAll);
        }
        return list;
    }

	@Override
	public Map<String, Object> getFlowDataByHourAndFlow(String startDate, String endDate, String mediaIds, Integer uid,
			String type) {
		List<QuotaFlow> result = Lists.newArrayList();
		List<Integer> ids = Arrays.asList(mediaIds.split(",")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());

		Map<String, Object> params = new HashMap<>();
		params.put("list", ids);
		params.put("start", startDate);
		params.put("end", endDate);
		result = quotaFlowMapper.getFlowDataByHourAndFlow(params);

		FlowSourceExample example = new FlowSourceExample();
		example.createCriteria().andIdIn(ids);
		List<FlowSource> selectByExample = flowSourceMapper.selectByExample(example);
		List<String> names = FluentIterable.from(selectByExample).transform((FlowSource flowSource) -> {
			return flowSource.getMediaName();
		}).toList();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> series = new ArrayList<Map<String, Object>>();
		List<Integer> categories = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			categories.add(i);
		}
		for (String name : names) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("type", "line");
			List<Object> dataList = new ArrayList<Object>();

			for (Integer hour : categories) {
				Optional<QuotaFlow> Optional = result.stream()
						.filter(item -> item.getFsName().equals(name) && item.getCreHour() == hour).findFirst();
				if (Optional.isPresent()) {
					// 存在
					QuotaFlow quotaFlow = Optional.get();
					switch (type) {
					case "req":
						dataList.add(quotaFlow.getReq());
						break;
					case "exp":
						dataList.add(quotaFlow.getReq());
						break;
					case "clk":
						dataList.add(quotaFlow.getClk());
						break;
					case "bid":
						dataList.add(quotaFlow.getBid());
						break;
					case "clk_rate":
						dataList.add(quotaFlow.getClk_rate());
						break;
					case "bid_rate":
						dataList.add(quotaFlow.getBid_rate());
						break;
					case "exp_rate":
						dataList.add(quotaFlow.getExp_rate());
						break;
					default:
						throw new ServiceException("type 枚举值异常 .");
					}
				} else if (new SimpleDateFormat("HH").format(new Date()).equals(String.valueOf(hour))) {
					FlowSourceExample flowSourceExample = new FlowSourceExample();
					flowSourceExample.createCriteria().andMediaNameEqualTo(name);
					List<FlowSource> list = flowSourceMapper.selectByExample(flowSourceExample);
					QuotaFlow quotaFlow = getQuotaByRedis(String.valueOf(list.get(0).getId()), new Date());
					switch (type) {
					case "req":
						dataList.add(quotaFlow.getReq());
						break;
					case "exp":
						dataList.add(quotaFlow.getReq());
						break;
					case "clk":
						dataList.add(quotaFlow.getClk());
						break;
					case "bid":
						dataList.add(quotaFlow.getBid());
						break;
					case "clk_rate":
						dataList.add(quotaFlow.getClk_rate());
						break;
					case "bid_rate":
						dataList.add(quotaFlow.getBid_rate());
						break;
					case "exp_rate":
						dataList.add(quotaFlow.getExp_rate());
						break;
					default:
						throw new ServiceException("type 枚举值异常 .");
					}
				} else {
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
	public List<QuotaFlow> getFlowDataByDay(String startDate, String endDate, String mediaId, boolean isAll) {
		List<QuotaFlow> result = Lists.newArrayList();
		Map<String, Object> param = new HashMap<>();
		param.put("dx_media", mediaId);
		param.put("startDay", startDate);
		param.put("endDay", endDate);
		if (!isAll) {
			param.put("isGroup", "isGroup");
		}
		result = quotaFlowMapper.getFlowDataByDay(param);
		// 获取redis当前小时数据
		result = addRedisData(endDate, mediaId, result);
		List<QuotaFlow> resultList = formatQuotaFlow(startDate, endDate, result);
		if (isAll) {
			return result;
		} else {
			return resultList;
		}
	}

	@Override
	public Page<QuotaFlow> getFlowDataPageByDay(String startDate, String endDate, String mediaId, Integer currentPage,
			Integer pageSize) {
		Page<QuotaFlow> page = null;
		List<QuotaFlow> flowDataByDay = Lists.newArrayList();
		Map<String, Object> param = new HashMap<>();
		param.put("dx_media", mediaId);
		param.put("startDay", startDate);
		param.put("endDay", endDate);
		param.put("isGroup", "isGroup");
		// redis + 三方 TODO
		flowDataByDay = quotaFlowMapper.getFlowDataByDay(param);
		int count = flowDataByDay.size();
		if (currentPage != null && pageSize != null) {
			page = new Page<QuotaFlow>(count, currentPage, pageSize);
		} else {
			page = new Page<QuotaFlow>(count);
		}
		// param.put("start", page.getStartPosition());
		// param.put("limit", page.getPageSize());
		// flowDataByDay = quotaFlowMapper.getFlowDataByDay(param);
		// 获取redis当前小时数据
		flowDataByDay = addRedisData(endDate, mediaId, flowDataByDay);
		List<QuotaFlow> list = formatQuotaFlow(startDate, endDate, flowDataByDay);
		// 分页
		page = new Page<>(list.size(), currentPage, pageSize);
		List<QuotaFlow> restList = list.subList(page.getStartPosition(),
				page.getStartPosition() + pageSize < list.size() ? page.getStartPosition() + pageSize : list.size());

		page.bindData(restList);

		return page;
	}

	@Override
	public List<QuotaFlow> getFlowDataOneDayByFs(String startDate, String endDate, String mediaId) {
		List<QuotaFlow> result = null;
		Map<String, Object> param = new HashMap<>();
		List<Integer> ids = Arrays.asList(mediaId.split(",")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		param.put("list", ids);
		param.put("start", startDate);
		param.put("end", endDate);
		result = quotaFlowMapper.getFlowDataOneDayByFs(param);
		
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (CollectionUtils.isEmpty(result)) {
			result = Lists.newArrayList();
			for (Integer id : ids) {
				QuotaFlow redisFlow = getQuotaByRedis(String.valueOf(id), today);
				result.add(redisFlow);
			}
		} else {
			List<QuotaFlow> list = Lists.newArrayList();
			for (Integer id : ids) {
				Optional<QuotaFlow> optional = result.stream()
						.filter(item -> Integer.valueOf(item.getFsId()) == id).findFirst();
				if (optional.isPresent()) {
					QuotaFlow quotaFlow = optional.get();
					QuotaFlow quotaRedis = getQuotaByRedis(String.valueOf(id), today);					
					quotaFlow.setReq(quotaFlow.getReq() + quotaRedis.getReq());
					quotaFlow.setBid(quotaFlow.getBid() + quotaRedis.getBid());
					quotaFlow.setExp(quotaFlow.getExp() + quotaRedis.getExp());
					quotaFlow.setClk(quotaFlow.getClk() + quotaRedis.getClk());
					quotaFlow.setInvestment(quotaFlow.getInvestment() + quotaRedis.getInvestment());
					list.add(quotaFlow);
				} else {
					QuotaFlow redisFlow = getQuotaByRedis(String.valueOf(id), today);
					list.add(redisFlow);
				}
			}
			return list;
		}
		return result;
	}

	@Override
	public List<QuotaFlow> getFsByDay(String startDate, String endDate, String mediaIds) {
		List<QuotaFlow> result = Lists.newArrayList();
		Map<String, Object> param = new HashMap<>();
		List<Integer> ids = Arrays.asList(mediaIds.split(",")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		param.put("list", ids);
		param.put("start", startDate);
		param.put("end", endDate);
		result = quotaFlowMapper.getFsByDay(param);
		// 判断是否需要当前小时数据
		result = addRedisData(endDate, mediaIds, result);

		// List<QuotaFlow> resultList = formatQuotaFlow(startDate, endDate,
		// result);

		return bulidResult(result, startDate, endDate);
	}

	@Override
	public Page<QuotaFlow> getListByFs(String startDate, String endDate, String mediaIds, Integer currentPage,
			Integer pageSize) {
		Page<QuotaFlow> page = null;
		List<QuotaFlow> result = Lists.newArrayList();
		Map<String, Object> param = new HashMap<>();
		List<Integer> ids = Arrays.asList(mediaIds.split(",")).stream().map(s -> Integer.parseInt(s))
				.collect(Collectors.toList());
		param.put("list", ids);
		param.put("startDay", startDate);
		param.put("endDay", endDate);
		param.put("table", "table");
		result = quotaFlowMapper.getFsByFs(param);
		int count = 0;
		if (!CollectionUtils.isEmpty(result)) {
			count = result.size();
		}
		if (currentPage != null && pageSize != null) {
			page = new Page<QuotaFlow>(count, currentPage, pageSize);
		} else {
			page = new Page<QuotaFlow>(count);
		}
		param.put("start", page.getStartPosition());
		param.put("limit", page.getPageSize());
		result = quotaFlowMapper.getFsByFs(param);
		// 如果日期为今天获取当前小时数据
		result = addRedisData(endDate, mediaIds, result);

		page.bindData(result);
		return page;
	}

	/**
	 * 格式化QuotaFlow
	 */
	private List<QuotaFlow> formatQuotaFlow(String startDate, String endDate, List<QuotaFlow> result) {
		Date startDay = DateUtils.parse(startDate, DateUtils.SHORT_FORMAT);
		Date endDay = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		if (DateUtils.getDValue2Day(startDay, endDay) < 0) {
			return null;
		}
		List<QuotaFlow> resultList = org.assertj.core.util.Lists.newArrayList();
		for (Date curDate = new Date(startDay.getTime()); curDate.compareTo(endDay) <= 0; curDate = DateUtils
				.getNextDaysDate(curDate, 1)) {
			QuotaFlow quotaFlow = new QuotaFlow();
			quotaFlow.setCreDay(Integer.parseInt(DateUtils.format(curDate, DateUtils.SHORT_FORMAT)));
			resultList.add(quotaFlow);
		}
		// 把数据放入对应日期索引位置
		if (!CollectionUtils.isEmpty(result)) {
			for (QuotaFlow quotaFlow : result) {
				Date curDate = DateUtils.parse(String.valueOf(quotaFlow.getCreDay()), DateUtils.SHORT_FORMAT);
				long index = DateUtils.getDValue2Day(startDay, curDate);
				resultList.set(new Long(index).intValue(), quotaFlow);
			}
		}
		return resultList;
	}

	/**
	 * 获取redis当前小时数据
	 */
	private List<QuotaFlow> addRedisData(String endDate, String mediaIds, List<QuotaFlow> result) {
		Date endDay = DateUtils.parse(endDate, DateUtils.SHORT_FORMAT);
		Date today = DateUtils.getFirstSecondOfDay(new Date());
		if (endDay.compareTo(today) >= 0) {
			// 若查询到今天则获取redis当前小时数据
			QuotaFlow redisFlow = getQuotaByRedis(mediaIds, today);
			if (CollectionUtils.isEmpty(result)) {
//				QuotaFlow quotaFlow = result.get(result.size() - 1);
//				quotaFlow.setReq(quotaFlow.getReq() == null ? 0 : quotaFlow.getReq() + redisFlow.getReq());
//				quotaFlow.setExp(quotaFlow.getExp() == null ? 0 : quotaFlow.getExp() + redisFlow.getExp());
//				quotaFlow.setClk(quotaFlow.getClk() == null ? 0 : quotaFlow.getClk() + redisFlow.getClk());
//				quotaFlow.setInvestment(quotaFlow.getInvestment() + redisFlow.getInvestment());
//				result.set(result.size() - 1, quotaFlow);
//			} else {
				result = Lists.newArrayList();
			}
            redisFlow.setFsId(mediaIds);
            redisFlow.setFsName(flowSourceMapper.selectByPrimaryKey(Integer.parseInt(mediaIds)).getMediaName());
            result.add(redisFlow);
        }
		return result;
	}

	/**
	 * 从redis获取当前小时到quotaFLow
	 */
	private QuotaFlow getQuotaByRedis(String mediaIds, Date today) {
		FlowSource flowSource = flowSourceMapper.selectByPrimaryKey(Integer.parseInt(mediaIds));
		String hour = DateUtils.format(new Date(), DateUtils.FORMAT_HOUR);
		String day = DateUtils.format(today, DateUtils.SHORT_FORMAT);

		AppsExample appsExample = new AppsExample();
		appsExample.createCriteria().andMediasEqualTo(flowSource.getMediaUuid());
		List<Apps> apps = appsMapper.selectByExample(appsExample);
        List<Integer> appidList = Lists.transform(apps, Apps::getId);
        AdPositionExample positionExample = new AdPositionExample();
        positionExample.createCriteria().andAppIdIn(appidList);
        List<AdPosition> adpList = adPositionMapper.selectByExample(positionExample);
		List<AppChannel> channelList = appChannelMapper.selectByExample(null);
		List<AppVersion> versionList = appVersionMapper.selectByExample(null);

		int exp = 0;
		int clk = 0;
		long req = 0;
		int cost = 0;
        List<String> keyList = Lists.newArrayList();
        for (Apps app : apps) {
            for (AdPosition adp : adpList) {
                for (AppChannel channel : channelList) {
                    for (AppVersion ver : versionList) {
                        String reqKey = StringUtils.buildString(WebConstants.QUOTA_MEDIA_DAY_KEY,
                                Constants.QUOTA_REQ, app.getAppId(), adp.getUuid(),
                                channel.getCname(), ver.getName(), day);
                        String expKey = StringUtils.buildString(WebConstants.QUOTA_MEDIA_DAY_KEY,
                                Constants.QUOTA_DISPLAY, app.getAppId(), adp.getUuid(),
                                channel.getCname(), ver.getName(), day);
                        String clkKey = StringUtils.buildString(WebConstants.QUOTA_MEDIA_DAY_KEY,
                                Constants.QUOTA_CLICK, app.getAppId(), adp.getUuid(),
                                channel.getCname(), ver.getName(), day);
                        String costKey = StringUtils.buildString(WebConstants.QUOTA_MEDIA_DAY_KEY,
                                Constants.QUOTA_COST, app.getAppId(), adp.getUuid(),
                                channel.getCname(), ver.getName(), day);
                        keyList.add(reqKey);
                        keyList.add(expKey);
                        keyList.add(clkKey);
                        keyList.add(costKey);
                    }
                }
            }
        }


//		String reqKey = StringUtils.buildString(WebConstants.KEY_REDIS_MEDIA_QUOTA_HOUR, "req",
//				flowSource.getMediaUuid(), day, hour);
//		String impKey = StringUtils.buildString(WebConstants.KEY_REDIS_MEDIA_QUOTA_HOUR, "display",
//				flowSource.getMediaUuid(), day, hour);
//		String clkKey = StringUtils.buildString(WebConstants.KEY_REDIS_MEDIA_QUOTA_HOUR, "click",
//				flowSource.getMediaUuid(), day, hour);
//		String costkey = StringUtils.buildString(WebConstants.KEY_REDIS_MEDIA_QUOTA_HOUR, "cost",
//				flowSource.getMediaUuid(), day, hour);
//
//		List<String> keyList = Arrays.asList(reqKey, impKey, clkKey, costkey);
		List<String> redisResp = redisDao.multiGet(keyList);
        for (int i = 0; i < redisResp.size(); i++) {
            String value = null;
            if (i * 4 < redisResp.size()) {
                value = redisResp.get(i*4);
                if (StringUtils.isNotBlank(value)) {
                    req += Integer.parseInt(value);
                }
            }
            if (i * 4 + 1 < redisResp.size()) {
                value = redisResp.get(i*4 + 1);
                if (StringUtils.isNotBlank(value)) {
                    exp += Integer.parseInt(value);
                }
            }
            if (i * 4 + 2 < redisResp.size()) {
                value = redisResp.get(i*4 + 2);
                if (StringUtils.isNotBlank(value)) {
                    clk += Integer.parseInt(value);
                }
            }
            if (i * 4 + 3 < redisResp.size()) {
                value = redisResp.get(i*4 + 3);
                if (StringUtils.isNotBlank(value)) {
                    cost += Integer.parseInt(value);
                }
            }
        }
		QuotaFlow quotaFlow = new QuotaFlow();
		quotaFlow.setFsName(flowSource.getMediaName());
		quotaFlow.setCreHour(Integer.parseInt(hour));
		quotaFlow.setCreDay(Integer.parseInt(day));
		quotaFlow.setReq(req);
		quotaFlow.setExp(exp);
		quotaFlow.setClk(clk);
		quotaFlow.setInvestment(cost / 100000);
		return quotaFlow;
	}

}
