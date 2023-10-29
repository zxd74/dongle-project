//package com.iwanvi.nvwa.web.service.impl;
//
//import com.iwanvi.nvwa.common.exception.HttpInvokeException;
//import com.iwanvi.nvwa.common.redis.server.RedisDao;
//import com.iwanvi.nvwa.common.utils.*;
//import com.iwanvi.nvwa.dao.*;
//import com.iwanvi.nvwa.dao.model.*;
//import com.iwanvi.nvwa.dao.model.OrderPutExample.Criteria;
//import com.iwanvi.nvwa.web.service.*;
//import com.iwanvi.nvwa.web.util.WebConstants;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.CollectionUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//@Service
//@Lazy(value = false)
//public class SynchroAiKaServiceImpl implements SynchroAiKaService {
//	private static final Logger logger = LoggerFactory.getLogger(SynchroAiKaServiceImpl.class);
//
//	private static final String CUST_KEY = "customer";
//	private static final String ORDER_KEY = "order";
//	private static final String TEMP_KEY = "template";
//	private static final String CHANGE_TYPE_DEL = "del";
//
//	@Autowired
//	private OrdersMapper ordersMapper;
//	@Autowired
//	private OrderPutService orderPutService;
//	@Autowired
//	private OrderPutMapper orderPutMapper;
//	@Autowired
//	private CompanyMapper companyMapper;
//	@Autowired
//	private UserMapper userMapper;
//	@Autowired
//	private GroupAuthsMapper groupAuthsMapper;
//	@Autowired
//	private UserAuthsMapper userAuthsMapper;
//	@Autowired
//	private AdPositionMapper adPositionMapper;
//	@Autowired
//	private TemplateMapper templateMapper;
//	@Autowired
//	private TemplateModuleRelationMapper templateModuleRelationMapper;
//	@Autowired
//	private AdPositionService adPositionService;
//	@Autowired
//	private RedisDao redisDao;
//	@Autowired
//	private SysCrontabService sysCrontabService;
//	@Autowired
//	private AdxRelationService adxRelationService;
//	@Autowired
//	private UserGrandMapper userGrandMapper;
//	@Autowired
//	private UserService userService;
//	@Autowired
//	private AdxRelationMapper adxRelationMapper;
//	@Autowired
//	private SyslogService syslogService;
//	@Autowired
//	private DictionaryMapper dictionaryMapper;
//
//	@Override
//	@Transactional
//	public void synchroAiKaOrderPutByOrderId(Integer orderId) {
////		File file = new File("C:\\Users\\11499\\Desktop\\对接问题.txt");
////		StringBuffer stringBuffer = new StringBuffer();
////		try {
////			FileInputStream inputStream = new FileInputStream(file);
////			int num = 0;
////			byte[] byteStr = new byte[1024];
////			while ((num = inputStream.read(byteStr)) != -1) {
////				stringBuffer.append(new String(byteStr, 0, num, "gbk"));
////			}
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		 String url = WebConstants.XCAR_URL_HOST +
//		 StringUtils.buildString(WebConstants.XCAR_ORDERSINFO_API, orderId);
//		 String orderPutJson = HttpClientUtils.get(url);
//		 logger.info("sync aika success orderJson: {}",orderPutJson);
////		String orderPutJson = stringBuffer.toString();
//		Map<String, Object> map = JsonUtils.TO_OBJ(orderPutJson, Map.class);
//		if (Constants.INTEGER_0.equals(map.get("code"))) {
//			if (map.get("data") != null) {
//				Map<String, Object> data = (Map<String, Object>) map.get("data");
//				Orders orders = JsonUtils.TO_OBJ(data, Orders.class);
//				if (orders.getOrdersState() != null) {
//					//转订单状态
//					if (!Constants.INTEGER_1.equals(orders.getOrdersState())) {
//						orders.setOrdersState(Constants.STATE_INVALID);
//					}
//					//初始化字段值
//					orders.setId(null);
//					orders.setCreateTime(new Date());
//					orders.setUpdateTime(null);
//					orders.setStartTime(null);
//					orders.setEndTime(null);
//					OrdersExample ordersExample = new OrdersExample();
//					ordersExample.createCriteria().andOrderIdEqualTo(orders.getOrderId());
//					List<Orders> orderList = ordersMapper.selectByExample(ordersExample);
//					Integer oid = null;
//					// 转客户id
//					if (orders.getCustomerId() != null) {
//						CompanyExample companyExample = new CompanyExample();
//						companyExample.createCriteria().andOutCidEqualTo(orders.getCustomerId());
//						List<Company> companies = companyMapper.selectByExample(companyExample);
//						if (companies != null && companies.size() > 0) {
//							orders.setCustId(companies.get(0).getId());
//						}
//					}
//					if (orderList != null && orderList.size() > 0) {
//						// 修改订单
//						orders.setUpdateTime(new Date());
//						logger.info("aiKaOrder update success");
//						//获取订单id 为投放关联订单id做准备
//						ordersMapper.updateByExampleSelective(orders, ordersExample);
//						Orders oldOrder = orderList.get(0);
//						oid = oldOrder.getId();
//						//判断是否需要同步引擎
//						boolean isSyncOrder = false;
//						if (orders.getOrdersState() != null
//								&& !orders.getOrdersState().equals(oldOrder.getOrdersState())) {
//							isSyncOrder = true;
//						}
//						if (isSyncOrder) {
//							//同步引擎
//							sysCrontabService.addSysCrontabCheckCount(oid, Constants.OBJ_ORDER, Constants.OP_UPDATE);
//						}
//						//若订单状态无效则级联修改相关投放状态 并返回
//						if (Constants.STATE_INVALID.equals(orders.getOrdersState())) {
//							OrderPut orderput = new OrderPut();
//							orderput.setPutState(Constants.STATE_INVALID);
//							orderPutService.updateOrderPutByOid(orderput, oid);
//							return;
//						}
//					} else {
//						// 新增订单
//						orders.setRunState(Constants.STATE_VALID);
//						ordersMapper.insertSelective(orders);
//						logger.info("aiKaOrder insert success");
//						ordersMapper.countByExample(null);
//						oid = orders.getId();
//					}
//					List<Integer> PutIdList = Lists.newArrayList();
//					if (data.get("adPushInfoList") != null) {
//						List<Map<String, Object>> orderPutList = (List<Map<String, Object>>) data.get("adPushInfoList");
//						for (Map<String, Object> orderPutMap : orderPutList) {
//							List<Map<String, Object>> limitList = Lists.newArrayList();
//							if (orderPutMap.get("status") != null) {
//								OrderPut orderPut = JsonUtils.TO_OBJ(orderPutMap, OrderPut.class);
//								List<String> dateList = (List<String>) orderPutMap.get("dateList");
//								List<Map<String, Object>> hourList = (List<Map<String, Object>>) orderPutMap
//										.get("hourList");
//								Map<String, List<Integer>> hourMap = Maps.newHashMap();
//								// 转换投放日期和时间段
//								for (Map<String, Object> hMap : hourList) {
//									hourMap.put((String) hMap.get("label"), (List<Integer>) hMap.get("value"));
//								}
//								for (String dateStr : dateList) {
//									Map<String, Object> limitMap = Maps.newHashMap();
//									Date date = DateUtils.parse(dateStr, DateUtils.SHORT_FORMAT_EX);
//									String limitDateStr = StringUtils.concat(
//											DateUtils.format(date, DateUtils.SHORT_FORMAT), Constants.SIGN_MINUS,
//											DateUtils.format(date, DateUtils.SHORT_FORMAT));
//									limitMap.put("date", limitDateStr);
//									// 将日期格式化星期几
//									SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
//									String weekStr = sdf.format(date);
//									String limitHourStr = "";
//									if (hourMap.containsKey(weekStr)) {
//										//若时间都是0 则跳出此次循环
//										List<Integer> hours = hourMap.get(weekStr);
//										if (!hours.contains(Constants.INTEGER_1)) {
//											continue;
//										}
//										limitHourStr = handleHourList(hours);
//									}
//									limitMap.put("times", limitHourStr);
//									// limitMap.put("limit", "");
//									limitList.add(limitMap);
//								}
//								//设置默认媒体为爱卡
//								orderPut.setDxMedia(WebConstants.AIKA_UUID);
//								if (limitList.size() > 0) {
//									orderPut.setLimits(JsonUtils.TO_JSON(limitList));
//								}
//								// 设置开始时间和结束时间
//								if (StringUtils.isNotBlank(orderPut.getLimits())) {
//									openLimits(orderPut);
//								}
//								if (orderPutMap.get("id") != null) {
//									orderPut.setOutId((Integer) orderPutMap.get("id"));
//								}
//								if (orderPutMap.get("adId") != null) {
//									PutIdList.add((Integer) orderPutMap.get("adId"));
//								}
//								//根据状态设置运行状态
//								if (orderPutMap.get("status") != null) {
//									if (Constants.INTEGER_3.equals(orderPutMap.get("status"))) {
//										// orderPut.setPutState(Constants.STATE_VALID);
//										orderPut.setRunState(Constants.STATE_INVALID);
//									} else if (Constants.INTEGER_0.equals(orderPutMap.get("status"))) {
//										orderPut.setRunState(Constants.STATE_INVALID);
//									} else {
//										orderPut.setRunState(Constants.STATE_VALID);
//									}
//								}
//								//设置终端类型 (只支持 天)
//								if (orderPutMap.get("platformId") != null) {
//									Integer zdlx = Constants.getAiKaPutZdlx((Integer) orderPutMap.get("platformId"));
//									orderPut.setDxZdlx(zdlx);
//								}
//								//设置花费类型 以及将kpi放入putLimit
//								if (orderPutMap.get("feeType") != null) {
//									Integer costType = Constants
//											.getAiKaPutCostType((Integer) orderPutMap.get("feeType"));
//									if (Constants.COST_TYPE_ORDER_CPC.equals(costType)) {
//										if (orderPut.getClickKpi() != null) {
//											orderPut.setPutLimit(orderPut.getClickKpi().intValue());
//										}
//									} else if (Constants.COST_TYPE_ORDER_CPM.equals(costType)) {
//										if (orderPut.getPvKpi() != null) {
//											orderPut.setPutLimit(orderPut.getPvKpi().intValue());
//										}
//									} else {
//										orderPut.setPutLimit(Constants.INTEGER_0);
//									}
//									orderPut.setCostType(costType);
//								} else {
//									orderPut.setCostType(Constants.COST_TYPE_CPT);
//								}
//								//设置频次控制 以及相关数据
//								if (orderPut.getIsFrequency() != null) {
//									if (orderPutMap.get("frequencyType") != null) {
//										if (Constants.INTEGER_1.equals(((Integer) orderPutMap.get("frequencyType")))) {
//											Integer period = Constants
//													.getAiKaPutFrePeriod((Integer) orderPutMap.get("frequencyType"));
//											orderPut.setFrequencPeriod(period);
//										}else {
//											orderPut.setFrequencPeriod(null);
//										}
//									} else {
//										// 默认频次周期按天
//										orderPut.setFrequencPeriod(Constants.getAiKaPutFrePeriod(Constants.INTEGER_1));
//									}
//								} else {
//									orderPut.setIsFrequency(Constants.INTEGER_0);
//								}
//								//设置是否匀速
//								if (orderPutMap.get("speedType") != null) {
//									Integer deliveryMode = Constants
//											.getAiKaPutDeliveryMode((Integer) orderPutMap.get("speedType"));
//									orderPut.setDeliveryMode(deliveryMode);
//								}
//								orderPut.setId(null);
//								//关联oid
//								orderPut.setOid(oid);
//								orderPut.setCreateTime(new Date());
//								if (orderPutMap.get("creativeTemplateIds") != null) {
//									Integer orderPutId = null;
//									boolean checkOrderPutFir = false;
//									//获取aika模板id集合
//									String templates = orderPutMap.get("creativeTemplateIds").toString();
//									List<Integer> templateList = StringUtils.str2List4Int(templates,
//											Constants.SIGN_COMMA);
//									List<Integer> adPositionIds = Lists.newArrayList();
//									//获取关联词oid的有效投放数据
//									OrderPutExample orderPutExample = new OrderPutExample();
//									Criteria criteria = orderPutExample.createCriteria();
//									criteria.andOrderBaseTabIdEqualTo(orders.getOrderId())
//											.andAdIdEqualTo(orderPut.getAdId())
//											.andPutStateNotEqualTo(Constants.STATE_INVALID);
//									orderPutExample.setStart(0);
//									orderPutExample.setLimit(1);
//									List<OrderPut> checkOrderPuts = orderPutMapper
//											.selectByExampleWithBLOBs(orderPutExample);
//									if (checkOrderPuts != null && checkOrderPuts.size() > 0) {
//										//检查基本信息是否更改 判断是否同步引擎
//										checkOrderPutFir = orderPutService.checkOrderPutAll(orderPut,
//												checkOrderPuts.get(0));
//									}
//									orderPutExample.setStart(null);
//									orderPutExample.setLimit(null);
//									for (Integer template : templateList) {
//										// 通过aika的模板获取到广告平台的广告位
//										AdPositionExample positionExample = new AdPositionExample();
//										positionExample.createCriteria().andAikaTemplateIdEqualTo(template)
//												.andStatusEqualTo(Constants.STATE_VALID);
//										List<AdPosition> adPositions = adPositionMapper
//												.selectByExample(positionExample);
//										Integer adPositionId = null;
//										if (adPositions != null && adPositions.size() > 0) {
//											AdPosition aPosition = adPositions.get(0);
//											adPositionId = aPosition.getId();
//											orderPut.setAdPosition(adPositionId);
//											//确定订单投放关联的广告位
//											adPositionIds.add(adPositionId);
//											String putName = "";
//											orderPut.setPutName(StringUtils.EMPTY);
//											orderPut.setId(null);
//											//设置投放的名称
//											if (StringUtils.isNotBlank(orderPut.getPutName())) {
//												putName = StringUtils.concat(orderPut.getPutName(),
//														Constants.SIGN_MINUS, aPosition.getName());
//											} else {
//												putName = StringUtils.concat(orders.getName(), Constants.SIGN_MINUS,
//														"投放", Constants.SIGN_MINUS, aPosition.getName());
//											}
//											orderPut.setPutName(putName);
//											// 确定投放状态
//											// 判断是新增还是修改
//											OrderPutExample newOrderPutExample = new OrderPutExample();
//											Criteria newcriteria = newOrderPutExample.createCriteria();
//											newcriteria.andOrderBaseTabIdEqualTo(orders.getOrderId())
//													.andAdIdEqualTo(orderPut.getAdId())
//													.andPutStateNotEqualTo(Constants.STATE_INVALID)
//													.andAdPositionEqualTo(orderPut.getAdPosition());
//											List<OrderPut> orderPuts = orderPutMapper
//													.selectByExampleWithBLOBs(newOrderPutExample);
//											OrderPut oldOrderPut = null;
//											if (orderPuts != null && orderPuts.size() > 0) {
//												boolean checkOrderPutSen = false;
//												boolean checkOrderPutLimit = true;
//												oldOrderPut = orderPuts.get(0);
//												orderPutId = oldOrderPut.getId();
//												orderPut.setId(orderPutId);
//												//设置订单投放的投放状态
//												if (!checkOrderPutFir) {
//													orderPutService.checkPutState(orderPut, oldOrderPut);
//												}
//												//修改订单投放
//												orderPutMapper.updateByPrimaryKeySelective(orderPut);
//												syslogService.addSyslog(0, 0, orderPut, oldOrderPut,
//														this.getClass().getName(), "synchroAiKaOrderPutByOrderId");
//												//针对单个订单投放 检查关键数据是否修改 是否同步引擎
//												checkOrderPutSen = orderPutService.checkOrderPutOne(orderPut,
//														oldOrderPut);
//												if ((orderPut.getPutLimit() != null
//														&& !orderPut.getPutLimit().equals(oldOrderPut.getPutLimit()))
//														|| (orderPut.getCostType() != null && !orderPut.getCostType()
//																.equals(oldOrderPut.getCostType()))) {
//													// 重置限额
//													if (orderPutService.resetPutLimit(orderPut)) {
//														checkOrderPutLimit = true;
//													} else {
//														// 限额失败标记限额失败定时重置
//														OrderPut newOrderPut = new OrderPut();
//														newOrderPut.setId(orderPut.getId());
//														newOrderPut.setResetFaild(Constants.STATE_INVALID);
//														orderPutMapper.updateByPrimaryKeySelective(newOrderPut);
//														syslogService.addSyslog(0, 0, orderPut, oldOrderPut,
//																this.getClass().getName(), "delete");
//														checkOrderPutLimit = false;
//													}
//												}
//												//判断同步条件是否变动来确定是否同步
//												if (checkOrderPutFir || checkOrderPutSen || checkOrderPutLimit) {
//													// 同步订单投放
//													sysCrontabService.addSysCrontabCheckCount(orderPutId,
//															Constants.OBJ_ORDER_PUT, Constants.OP_UPDATE);
//												}
//											} else {
//												//设置订单投放的投放状态
//												orderPutService.checkPutState(orderPut, null);
//												orderPutMapper.insertSelective(orderPut);
//												orderPutMapper.countByExample(null);
//												orderPutId = orderPut.getId();
//												if (orderPut.getPutLimit() != null) {
//													// 设置限额
//													if (!orderPutService.resetPutLimit(orderPut)) {
//														// 限额失败标记限额失败定时重置
//														OrderPut newOrderPut = new OrderPut();
//														newOrderPut.setId(orderPut.getId());
//														newOrderPut.setResetFaild(Constants.STATE_INVALID);
//														orderPutMapper.updateByPrimaryKeySelective(newOrderPut);
//
//													}
//												}
//											}
//
//										}
//									}
//									//查询数据库 将投放中关联此广告位id(adPosition)集合之外的id的数据全部 置为无效
//									if (adPositionIds.size() > 0) {
//										criteria.andAdPositionNotIn(adPositionIds);
//										OrderPut deletePut = new OrderPut();
//										deletePut.setPutState(Constants.STATE_INVALID);
//										orderPutMapper.updateByExampleSelective(deletePut, orderPutExample);
//									}
//								}
//							}
//						}
//					}
//					//查询数据库 将投放关联此投放id(adId)集合之外的id的数据 全部置为无效
//					if (PutIdList.size() > 0) {
//						OrderPutExample orderPutExample = new OrderPutExample();
//						orderPutExample.createCriteria().andOidEqualTo(oid)
//								.andPutStateNotEqualTo(Constants.STATE_INVALID).andAdIdNotIn(PutIdList);
//						OrderPut orderPut = new OrderPut();
//						orderPut.setPutState(Constants.STATE_INVALID);
//						orderPutService.updateByOrderPutExample(orderPut, orderPutExample);
//					}
//				}
//			}
//		}
//	}
//	
//	private String handleHourList(List<Integer> hours) {
//		boolean flag = false;
//		Integer start = 0;
//		Integer end = 0;
//		String limitHourStr = "";
//		for (int i = 0; i < hours.size() - 1; i++) {
//			if (Constants.STATE_VALID.equals(hours.get(i))) {
//				if (!flag) {
//					start = i;
//					flag = true;
//				}
//			}
//			if (Constants.STATE_INVALID.equals(hours.get(i)) && flag) {
//				end = i;
//				flag = false;
//				limitHourStr += StringUtils.concat(Constants.SIGN_COMMA, start, Constants.SIGN_MINUS, end);
//			}
//		}
//		if (limitHourStr.length() > 0) {
//			limitHourStr = limitHourStr.substring(Constants.INTEGER_1);
//		}
//		return limitHourStr;
//	}
//
//	private void openLimits(OrderPut orderPut) {
//		if (StringUtils.isNotBlank(orderPut.getLimits())) {
//			Date nowDate = DateUtils.getFirstSecondOfDay(new Date());
//			Date startDate;
//			Date endDate;
//			List<Map<String, Object>> limits = JsonUtils.TO_OBJ(orderPut.getLimits(), List.class);
//			if (limits != null) {
//				boolean isNow = false;
//				for (Map<String, Object> map : limits) {
//					String dates = map.get("date").toString();
//					String times = map.get("times").toString();
//					Integer limit = (Integer) map.get("limit");
//					List<String> dateList = StringUtils.str2List(dates, Constants.SIGN_MINUS);
//					if (dateList != null && dateList.size() == 2) {
//						startDate = DateUtils.parse(dateList.get(0), DateUtils.SHORT_FORMAT);
//						endDate = DateUtils.parse(dateList.get(1), DateUtils.SHORT_FORMAT);
//						// 选取符合当前日期的时间段作为投放的投放时间
//						if (nowDate.compareTo(startDate) >= 0 && nowDate.compareTo(endDate) <= 0) {
//							logger.info("openLimits putId: {}, dates: {}, times: {}, limit: {}", orderPut.getId(),
//									dates, times, limit);
//							orderPut.setBeginTime(DateUtils.getFirstSecondOfDay(startDate));
//							orderPut.setEndTime(DateUtils.getLastSecondOfDay(endDate));
//							orderPut.setTimeInterval(times);
//							isNow = true;
//						}
//					}
//				}
//				// 若无符合的时间段则默认选取第一个时间段作为投放的投放时间
//				if (!isNow) {
//					Map<String, Object> map = limits.get(0);
//					String dates = map.get("date").toString();
//					String times = map.get("times").toString();
//					List<String> dateList = StringUtils.str2List(dates, Constants.SIGN_MINUS);
//					startDate = DateUtils.parse(dateList.get(0), DateUtils.SHORT_FORMAT);
//					endDate = DateUtils.parse(dateList.get(1), DateUtils.SHORT_FORMAT);
//					logger.info("openLimits default putId: {}, dates: {}, times: {}, limit: {}", orderPut.getId(),
//							dates, times);
//					orderPut.setBeginTime(DateUtils.getFirstSecondOfDay(startDate));
//					orderPut.setEndTime(DateUtils.getLastSecondOfDay(endDate));
//					orderPut.setTimeInterval(times);
//				}
//			}
//		}
//	}
//
//	@Transactional
//	@Override
//	public void syncCustFormXcar(List<Integer> xcarCustIdList) {
//		if (!CollectionUtils.isEmpty(xcarCustIdList)) {
//			String custUrl = StringUtils.concat(WebConstants.XCAR_URL_HOST, WebConstants.XCAR_CUSTOMER_API);
//			Company company;
//			CompanyExample example;
//			int count;
//			for (Integer xcarCustId : xcarCustIdList) {
//				Map<String, String> paramMap = Maps.newHashMap();
//				paramMap.put("customerId", xcarCustId.toString());
//				String respStr = HttpClientUtils.get(custUrl, paramMap);
//				if (StringUtils.isNotBlank(respStr)) {
//					Map<String, Object> respMap = JsonUtils.TO_OBJ(respStr, Map.class);
//					Integer code = Integer.parseInt(respMap.get("code").toString());
//					if (WebConstants.XCAR_SUCCESS_CODE == code) {
//						Map<String, Object> dataMap = (Map<String, Object>) respMap.get("data");
//						if (dataMap != null) {
//							company = new Company();
//							company.setFullName(dataMap.get("name").toString());
//							company.setShortName(dataMap.get("shortName").toString());
//							company.setStatus(
//									Integer.parseInt(dataMap.get("status").toString()) == Constants.STATE_VALID
//											? Constants.STATE_VALID : Constants.STATE_INVALID);
//							company.setIsDelete(
//									Integer.parseInt(dataMap.get("isDelete").toString()) == Constants.INTEGER_2
//											? Constants.STATE_VALID : Constants.STATE_INVALID);
//							Integer checkStatus = Integer.parseInt(dataMap.get("checkStatus").toString());
//							company.setAuditStatus(auditStatusMap.get(checkStatus));
//							company.setAuditComment(dataMap.get("checkRemark").toString());
//							company.setPhone(dataMap.get("phone").toString());
//							company.setOutCid(xcarCustId);
//							company.setAid(WebConstants.KA_AGENT_ID);
//							company.setType(WebConstants.COMPANY_TYPE_ADVER);
//							company.setReadonly(Constants.STATE_INVALID);
//							company.setIndustryType(27);
//							example = new CompanyExample();
//							example.createCriteria().andOutCidEqualTo(xcarCustId);
//							count = companyMapper.countByExample(example);
//							if (count > 0) {
//								companyMapper.updateByExampleSelective(company, example);
//								List<Company> companyList = companyMapper.selectByExample(example);
//                                Integer cid = companyList.get(Constants.INTEGER_0).getId();
//                                AdxRelationExample example1 = new AdxRelationExample();
//								example1.createCriteria().andAdxTypeEqualTo(Constants.FLOW_MEDIA_AIKA)
//										.andObjIdEqualTo(cid).andObjTypeEqualTo(Constants.OBJ_ADVERTISER)
//										.andStatusEqualTo(Constants.STATE_VALID);
//								AdxRelation adxRelation = new AdxRelation();
//								adxRelation.setAuditState(company.getAuditStatus());
//								adxRelation.setAuditComments(company.getAuditComment());
//								adxRelationMapper.updateByExampleSelective(adxRelation, example1);
//								sysCrontabService.addSysCrontab(cid, Constants.OBJ_ADVERTISER, Constants.OP_UPDATE);
//							} else {
//								boolean success = false;
//								while (!success) {
//									String uuid = UUIDUtils.getUUID().substring(Constants.INTEGER_0,
//											Constants.INTEGER_6);
//									if (!checkUuidIsExistedInDb(uuid)) {
//										company.setUuid(uuid);
//										companyMapper.insertSelective(company);
//										companyMapper.countByExample(null);
//										logger.info("save customer from iwanvi success,{}", JsonUtils.TO_JSON(company));
//										// addAdxRelation(company);
//										redisDao.set(WebConstants.REDIS_COMPANY_KEY_PREFIX + company.getId(),
//												JsonUtils.TO_JSON(company));
//										AdxRelation adxRelation = new AdxRelation();
//										adxRelation.setAuditState(company.getAuditStatus());
//										adxRelation.setAuditComments(company.getAuditComment());
//										adxRelation.setObjType(Constants.OBJ_ADVERTISER);
//										adxRelation.setObjId(company.getId());
//										adxRelation.setAdxType(Constants.FLOW_MEDIA_AIKA);
//										adxRelation.setStatus(Constants.STATE_VALID);
//										adxRelationMapper.insertSelective(adxRelation);
//										success = true;
//									}
//								}
//								addUser(company);
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//
//	private void grandCompany(User user) {
//		if (user != null && user.getId() != null) {
//			List<Integer> xcarUserList = userService.getAgentAdminIdByAid(WebConstants.KA_AGENT_ID);
//			if (!CollectionUtils.isEmpty(xcarUserList)) {
//				for (Integer xcarUser : xcarUserList) {
//                    UserGrand userGrand = new UserGrand();
//                    userGrand.setAid(user.getId());
//                    userGrand.setType(WebConstants.AGENT_OBJECT_TYPE_ADVER);
//                    userGrand.setStatus(Constants.STATE_VALID);
//                    userGrand.setUid(xcarUser);
//                    userGrandMapper.insertSelective(userGrand);
//                    logger.info("grand user :{}", JsonUtils.TO_JSON(userGrand));
//                }
//			} else {
//				logger.error("did not found admin of agent iwanvi. please init admin of agent iwanvi");
//			}
//		}
//	}
//
//	private void addAdxRelation(Company company) {
//		if (company != null && company.getId() != null) {
//			AdxRelation adxRelation;
//			adxRelation = new AdxRelation();
//			adxRelation.setAdxType(Constants.FLOW_MEDIA_AIKA);
//			adxRelation.setObjId(company.getId());
//			adxRelation.setObjType(Constants.OBJ_ADVERTISER);
//			adxRelation.setAuditState(company.getAuditStatus());
//			adxRelationService.add(adxRelation);
//			logger.info("add relation :{}", JsonUtils.TO_JSON(adxRelation));
//		}
//	}
//
//	/**
//	 * 给爱卡广告主生成默认登录人 并授权给爱卡代理商所有人员
//	 * 
//	 * @param company
//	 */
//	private void addUser(Company company) {
//		User user = new User();
//		user.setCompany(company.getId());
//		user.setType(Constants.USER_TYPE_CUST);
//		user.setUserName(company.getFullName());
//		user.setPassword(MD5Utils.MD5("123456"));
//		user.setCreateTime(new Date());
//		user.setCreateUser(0);
//		user.setIsDelete(Constants.STATE_INVALID);
//		String uuid = null;
//		boolean existed = true;
//		while (existed) {
//			uuid = UUIDUtils.getUUID().substring(Constants.INTEGER_0, Constants.INTEGER_6);
//			if (!checkUserUuidIsExistedInDb(uuid)) {
//				existed = false;
//			}
//		}
//		user.setUuid(uuid);
//		user.setStatus(Constants.STATE_VALID);
//		userMapper.insertSelective(user);
//		userMapper.countByExample(null);
//		logger.info("save user of customer from iwanvi success,{}", JsonUtils.TO_JSON(user));
//		GroupAuthsExample groupAuthsExample = new GroupAuthsExample();
//		groupAuthsExample.createCriteria().andGidEqualTo(WebConstants.COMPANY_TYPE_ADVER);
//		List<GroupAuths> groupAuths = groupAuthsMapper.selectByExample(groupAuthsExample);
//		UserAuths auth;
//		for (GroupAuths groupAuth : groupAuths) {
//			auth = new UserAuths();
//			auth.setAid(groupAuth.getAid());
//			auth.setUid(user.getId());
//			auth.setReadonly(Constants.STATE_INVALID);
//			auth.setStatus(groupAuth.getStatus());
//			userAuthsMapper.insertSelective(auth);
//			logger.info("save user auth success,{}", JsonUtils.TO_JSON(auth));
//		}
//		grandCompany(user);
//	}
//
//	@Override
//	@Transactional
//	public void synchroAiKaTemplate(Integer templateId) {
//		try {
//			if (templateId != null) {
//				String xcarUrl = WebConstants.XCAR_URL_HOST + WebConstants.XCAR_TEMPLATE_API;
//				Template template = new Template();
//				AdPosition adPosition = new AdPosition();
//				TemplateModuleRelation relation;
//				TemplateExample templateExample;
//				TemplateModuleRelationExample moduleRelationExample;
//				Map<String, String> paramMap = Maps.newHashMap();
//				Map<String, Object> redisMap = Maps.newHashMap();
//				paramMap.put("templateId", templateId.toString());
//				String respData = HttpClientUtils.get(xcarUrl, paramMap);
//				if (StringUtils.isNotBlank(respData)) {
//					Map<String, Object> respMap = JsonUtils.TO_OBJ(respData, Map.class);
//					Integer code = Integer.parseInt(respMap.get("code").toString());
//					if (WebConstants.XCAR_SUCCESS_CODE == code) {
//						Map<String, Object> dataMap = (Map<String, Object>) respMap.get("data");
//						if (dataMap != null && dataMap.get("rule") != null) {
//							Map<String, Object> rule = JsonUtils.parse2Map(dataMap.get("rule").toString());
//							List<Map<String, String>> materList = (List<Map<String, String>>) rule.get("options");
//							template.setName(dataMap.get("name").toString());
//							template.setType(Constants.INTEGER_2);
//							template.setStatus(Integer.parseInt(dataMap.get("status").toString()) == Constants.STATE_VALID
//									? Constants.STATE_VALID : Constants.STATE_INVALID);
//							template.setCreateTime(new Date());
//							template.setAikaTemplateId(templateId);
//							templateExample = new TemplateExample();
//							templateExample.createCriteria().andAikaTemplateIdEqualTo(templateId);
//							List<Template> templateList = templateMapper.selectByExample(templateExample);
//							StringBuilder tempName = new StringBuilder();
//							tempName.append(template.getName()).append(Constants.SIGN_MINUS);
//							AdPositionExample positionExample = new AdPositionExample();
//							if (templateList.size() > 0) {
//								positionExample.createCriteria().andAikaTemplateIdEqualTo(templateId);
//								List<AdPosition> positionList = adPositionMapper.selectByExample(positionExample);
//								// 更新：先删除原来模板的组件，再重新建立组件关系
//								int tid = templateList.get(0).getId();
//								relation = new TemplateModuleRelation();
//								relation.setUpdateTime(new Date());
//								relation.setStatus(Constants.STATE_INVALID);
//								moduleRelationExample = new TemplateModuleRelationExample();
//								moduleRelationExample.createCriteria().andTIdEqualTo(tid)
//										.andStatusEqualTo(Constants.STATE_VALID);
//								List<TemplateModuleRelation> list = templateModuleRelationMapper
//										.selectByExample(moduleRelationExample);
//								for (TemplateModuleRelation templateModuleRelation : list) {
//									templateModuleRelation.setStatus(Constants.STATE_INVALID);
//									templateModuleRelationMapper.updateByPrimaryKeySelective(templateModuleRelation);
//								}
//								redisMap = createModule(materList, tid, redisMap);
//								tempName.append(redisMap.get("tempName"));
//								redisMap.remove("tempName");
//								template = templateList.get(0);
//								template.setName(tempName.toString());
//								templateMapper.updateByPrimaryKeySelective(template);
//								for (AdPosition position : positionList) {
//									String terminalName = dictionaryMapper.selectByPrimaryKey(position.getTerminal())
//											.getDicValue();
//									position.setName(tempName.toString() + Constants.SIGN_MINUS + terminalName);
//									adPositionMapper.updateByPrimaryKey(position);
//									sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_AK_ADP,
//											Constants.OP_UPDATE);
//									logger.info("insert adposition to sysCrontab success ! adposition id :{}",
//											adPosition.getId());
//									String positionKey = StringUtils.buildString(WebConstants.KEY_REDIS_AD_POSITION,
//											position.getUuid());
//									redisDao.set(positionKey, JsonUtils.TO_JSON(redisMap));
//								}
//								logger.info("update template and position by AiKa templateId success ! aika templateId:{}", templateId);
//							} else {
//								templateMapper.insert(template);
//								templateExample = new TemplateExample();
//								logger.info("insert template success !", JsonUtils.TO_JSON(template));
//								templateExample.createCriteria().andAikaTemplateIdEqualTo(templateId);
//								List<Template> templates = templateMapper.selectByExample(templateExample);
//								int tid = templates.get(0).getId();
//								// 创建模板组件关系
//								redisMap = createModule(materList, tid, redisMap);
//								tempName.append(redisMap.get("tempName"));
//								redisMap.remove("tempName");
//								template.setName(tempName.toString());
//								templateMapper.updateByPrimaryKeySelective(template);
//								String[] terminalIds = dataMap.get("platformId").toString().split(Constants.SIGN_COMMA);
//								for (String terminalId : terminalIds) {
//									boolean success = false;
//									// 创建广告位并关联xcar模版id
//									while (!success) {
//										String uuid = ShortUrlUtils.getByUUID();
//										boolean existed = adPositionService.checkUuidIsExisted(uuid);
//										if (!existed) {
//											Integer terminal = convertTerminalMap.get(terminalId);
//											String terminalName = dictionaryMapper.selectByPrimaryKey(terminal)
//													.getDicValue();
//											adPosition.setName(tempName.toString() + Constants.SIGN_MINUS + terminalName);
//											adPosition.setFlowUuid(WebConstants.AIKA_UUID);
//											adPosition.setUuid(uuid);
//											adPosition.setAikaTemplateId(templateId);
//											adPosition.setTemplateId(tid);
//											adPosition.setTerminal(terminal);
//											adPosition.setType(WebConstants.AD_TYPE_NATIVE);
//											adPosition.setStatus(Constants.STATE_VALID);
//											adPosition.setUpdateTime(new Date());
//											adPositionMapper.insert(adPosition);
//											AdPositionExample example = new AdPositionExample();
//											example.createCriteria().andNameEqualTo(adPosition.getName())
//													.andAikaTemplateIdEqualTo(templateId)
//													.andTerminalEqualTo(Integer.parseInt(terminalId));
//											success = true;
//											sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_AK_ADP,
//													Constants.OP_ADD);
//											logger.info("insert adposition to sysCrontab success ! adposition id :{}",
//													adPosition.getId());
//											String positionKey = StringUtils.buildString(WebConstants.KEY_REDIS_AD_POSITION,
//													adPosition.getUuid());
//											redisDao.set(positionKey, JsonUtils.TO_JSON(redisMap));
//											logger.info("insert adPosition success !", JsonUtils.TO_JSON(adPosition));
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.info("synchronize template error !!! templateId:{}", templateId, e);
//		}
//	}
//
//	/**
//	 * 创建模板组件关系
//	 */
//	private Map<String, Object> createModule(List<Map<String, String>> materList, int tid,
//			Map<String, Object> redisMap) {
//		TemplateModuleRelation relation;
//		int mid = 5;
//		for (Map<String, String> map : materList) {
//			// 创建模版组件关系
//			relation = new TemplateModuleRelation();
//			relation.settId(tid);
//			relation.setStatus(Constants.STATE_VALID);
//			relation.setCreateTime(new Date());
//			if ("VIDEO".equals(map.get("type")) || "FLASH".equals(map.get("type"))) {
//				relation.setmId(Constants.INTEGER_4);
//				relation.setDuration(Integer.parseInt(map.get("size")));
//				redisMap.put("duration", map.get("size"));
//				redisMap.put("tempName", redisMap.get("tempName") == null ? map.get("size") + "s"
//						: redisMap.get("tempName") + Constants.SIGN_MINUS + map.get("size") + "s");
//			} else if ("TEXT".equals(map.get("type"))) {
//				relation.setmId(Constants.INTEGER_1);
//				if (map.get("size") != null) {
//					relation.setWordLimit(Integer.parseInt(map.get("size")));
//				}
//				redisMap.put("tempName", redisMap.get("tempName") == null ? map.get("size") + "字标题"
//						: redisMap.get("tempName") + Constants.SIGN_MINUS + map.get("size") + "字标题");
//			} else if ("RICH_TEXT".equals(map.get("type"))) {
//				relation.setmId(Constants.INTEGER_2);
//				if (map.get("size") != null) {
//					relation.setWordLimit(Integer.parseInt(map.get("size")));
//				}
//				redisMap.put("tempName", redisMap.get("tempName") == null ? map.get("size") + "字内容"
//						: redisMap.get("tempName") + Constants.SIGN_MINUS + map.get("size") + "字内容");
//			} else {
//				relation.setmId(mid);
//				String[] size = map.get("size").split(Constants.SIGN_BACKSLASH + Constants.SIGN_ASTERISK);
//				relation.setWidth(Integer.parseInt(size[0]));
//				relation.setHeight(Integer.parseInt(size[1]));
//				redisMap.put("width", size[0]);
//				redisMap.put("height", size[1]);
//				redisMap.put("tempName", redisMap.get("tempName") == null ? map.get("size")
//						: redisMap.get("tempName") + Constants.SIGN_MINUS + map.get("size"));
//				mid++;
//			}
//			redisMap.put("creativeType",
//					dictionaryMapper.selectByPrimaryKey(WebConstants.AD_TYPE_NATIVE).getEnumValue());
//			templateModuleRelationMapper.insert(relation);
//			logger.info("insert templateModuleRealtion success !", JsonUtils.TO_JSON(relation));
//		}
//		return redisMap;
//	}
//
//	@Override
//	public void syncTask(String date) {
//		String changeInfoUrl = StringUtils.concat(WebConstants.XCAR_URL_HOST, WebConstants.XCAR_CHANGEINFO_API);
//		Map<String, String> paramMap = Maps.newHashMap();
//		paramMap.put("date", date);
//		String respStr = HttpClientUtils.get(changeInfoUrl, paramMap);
//		if (StringUtils.isNotBlank(respStr)) {
//			logger.info("task success respStr: {}", respStr);
//			Map<String, Object> respMap = JsonUtils.TO_OBJ(respStr, Map.class);
//			Integer code = Integer.parseInt(respMap.get("code").toString());
//			if (WebConstants.XCAR_SUCCESS_CODE == code) {
//				Map<String, Object> dataMap = (Map<String, Object>) respMap.get("data");
//				if (!CollectionUtils.isEmpty(dataMap)) {
//					for (String key : dataMap.keySet()) {
//						if (CUST_KEY.equals(key)) {
//							Map<String, Object> custMap = (Map<String, Object>) dataMap.get(key);
//							if (!CollectionUtils.isEmpty(custMap)) {
//								Set<Integer> custIdSet = Sets.newHashSet();
//								List<Integer> custIdList;
//								List<Integer> delIdList;
//								for (String innerKey : custMap.keySet()) {
//                                    if (!CHANGE_TYPE_DEL.equals(innerKey)) {
//                                        custIdList = (List<Integer>) custMap.get(innerKey);
//                                        if (!CollectionUtils.isEmpty(custIdList)) {
//                                            custIdSet.addAll(custIdList);
//                                        }
//                                    } else {
//                                        delIdList = (List<Integer>) custMap.get(innerKey);
//                                        if (!CollectionUtils.isEmpty(delIdList)) {
//                                            Company delCom;
//                                            CompanyExample example;
//                                            for (Integer delId : delIdList) {
//                                                delCom = new Company();
//                                                delCom.setIsDelete(Constants.STATE_VALID);
//                                                example = new CompanyExample();
//                                                example.createCriteria().andOutCidEqualTo(delId)
//                                                        .andTypeEqualTo(WebConstants.COMPANY_TYPE_ADVER);
//                                                companyMapper.updateByExampleSelective(delCom, example);
//                                                List<Company> delComList = companyMapper.selectByExample(example);
//                                                if (!CollectionUtils.isEmpty(delComList)) {
//                                                    List<Integer> delComIdList = Lists.transform(delComList, Company::getId);
//                                                    UserExample userExample = new UserExample();
//                                                    userExample.createCriteria().andCompanyIn(delComIdList);
//                                                    User delUser = new User();
//                                                    delUser.setIsDelete(Constants.STATE_VALID);
//                                                    userMapper.updateByExampleSelective(delUser, userExample);
//                                                    for (Integer delCid : delComIdList) {
//                                                        sysCrontabService.addSysCrontab(delCid, Constants.OBJ_ADVERTISER,
//                                                                Constants.OP_UPDATE);
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//								if (!CollectionUtils.isEmpty(custIdSet)) {
//									syncCustFormXcar(Lists.newArrayList(custIdSet));
//								}
//							}
//						} else if (ORDER_KEY.equals(key)) {
//							Map<String, Object> orderMap = (Map<String, Object>) dataMap.get(key);
//							if (!CollectionUtils.isEmpty(orderMap)) {
//								Set<Integer> orderIdSet = Sets.newHashSet();
//								List<Integer> orderIdList = Lists.newArrayList();
//								logger.info("task order orderMap:{}",JsonUtils.TO_JSON(orderMap));
//								for (String innerKey : orderMap.keySet()) {
//									if (CHANGE_TYPE_DEL.equals(innerKey)) {
//										//删除订单
//										orderIdList = (List<Integer>) orderMap.get(innerKey);
//										if (!CollectionUtils.isEmpty(orderIdList)) {
//											OrdersExample ordersExample = new OrdersExample();
//											ordersExample.createCriteria().andOrderIdIn(orderIdList)
//											.andOrdersStateNotEqualTo(Constants.STATE_INVALID);
//											Orders orders = new Orders();
//											orders.setOrdersState(Constants.STATE_INVALID);
//											ordersMapper.updateByExampleSelective(orders, ordersExample);
//											//遍历删除订单关联投放
//											for (Integer orderId : orderIdList) {
//												OrderPut orderput = new OrderPut();
//												orderput.setPutState(Constants.STATE_INVALID);
//												orderPutService.updateOrderPutByOid(orderput, orderId);
//												//同步订单
//												sysCrontabService.addSysCrontabCheckCount(orderId, Constants.OBJ_ORDER, Constants.OP_UPDATE);
//											}
//										}
//									} else {
//										orderIdList = (List<Integer>) orderMap.get(innerKey);
//										if (!CollectionUtils.isEmpty(orderIdList)) {
//											orderIdSet.addAll(orderIdList);
//										}
//									}
//								}
//								if (!CollectionUtils.isEmpty(orderIdSet)) {
//									logger.info("task order orderIdSet",JsonUtils.TO_JSON(orderIdList));
//									for (Integer orderId : orderIdSet) {
//										synchroAiKaOrderPutByOrderId(orderId);
//									}
//								}
//							}
//
//						} else if (TEMP_KEY.equals(key)) {
//							Map<String, Object> templateMap = (Map<String, Object>) dataMap.get(key);
//							List<Integer> isDeleteList = Lists.newArrayList();
//							if (!CollectionUtils.isEmpty(templateMap)) {
//								Set<Integer> templateSet = Sets.newHashSet();
//								List<Integer> templateList;
//								for (String innerKey : templateMap.keySet()) {
//									if (!CHANGE_TYPE_DEL.equals(innerKey)) {
//										templateList = (List<Integer>) templateMap.get(innerKey);
//										if (!CollectionUtils.isEmpty(templateList)) {
//											templateSet.addAll(templateList);
//										}
//									} else {
//										isDeleteList = (List<Integer>) templateMap.get(innerKey);
//										if (!CollectionUtils.isEmpty(isDeleteList)) {
//											deleteTemplateAndPosition(isDeleteList);
//										}
//									}
//								}
//								if (!CollectionUtils.isEmpty(templateSet)) {
//									for (Integer templateId : templateSet) {
//										synchroAiKaTemplate(templateId);
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//	}
//	@Override
//	public void deleteTemplateAndPosition(List<Integer> list) {
//		if (!CollectionUtils.isEmpty(list)) {
//			for (Integer tid : list) {
//				List<Template> templateList ;
//				List<AdPosition> positionList ;
//				TemplateExample templateExample = new TemplateExample();
//				templateExample.createCriteria().andAikaTemplateIdEqualTo(tid);
//				templateList = templateMapper.selectByExample(templateExample);
//
//				AdPositionExample positionExample = new AdPositionExample();
//				positionExample.createCriteria().andAikaTemplateIdEqualTo(tid);
//				positionList = adPositionMapper.selectByExample(positionExample);
//				//删除爱卡模版
//				if (templateList.size() > 0) {
//					for (Template template : templateList) {
//						template.setStatus(Constants.STATE_INVALID);
//						templateMapper.updateByPrimaryKeySelective(template);
//					}
//				}
//				//删除爱卡广告位
//				if (positionList.size() > 0) {
//					for (AdPosition adPosition : positionList) {
//						adPosition.setStatus(Constants.STATE_INVALID);
//						adPositionMapper.updateByPrimaryKey(adPosition);
//						sysCrontabService.addSysCrontab(adPosition.getId(), Constants.OBJ_AK_ADP,
//								Constants.OP_UPDATE);
//						logger.info("insert adposition to sysCrontab success ! adposition id :{}",
//								adPosition.getId());
//						String positionKey = StringUtils.buildString(WebConstants.KEY_REDIS_AD_POSITION,
//								adPosition.getUuid());
//						redisDao.del(positionKey);
//						logger.info("delete aika adPosition success ! positionId :{]", adPosition.getId());
//					}
//				}
//			}
//		}
//	}
//
//	@Override
//	public void syncTask() {
//		String date = DateUtils.format(new Date(), DateUtils.SHORT_FORMAT_MINUTE);
//		Long dateM = Long.parseLong(date);
//		syncTask(Long.toString(dateM - 1));
//	}
//
//	private static Map<Integer, Integer> auditStatusMap = Maps.newHashMap();
//	static {
//		auditStatusMap.put(Constants.STATE_DEFAULT, Constants.STATE_FAILURE_AUDIT);
//		auditStatusMap.put(Constants.STATE_VALID, Constants.STATE_WAIT_AUDIT);
//		auditStatusMap.put(Constants.STATE_FAILURE_AUDIT, Constants.STATE_VALID);
//	}
//
//	private static Map<String, Integer> convertModuleMap = Maps.newHashMap();
//	static {
//		convertModuleMap.put("img1", 5);
//		convertModuleMap.put("img2", 6);
//		convertModuleMap.put("img3", 7);
//		convertModuleMap.put("img4", 8);
//		convertModuleMap.put("img5", 9);
//		convertModuleMap.put("img6", 10);
//		convertModuleMap.put("img7", 11);
//		convertModuleMap.put("img8", 12);
//		convertModuleMap.put("img9", 13);
//	}
//
//	private static Map<String, Integer> convertTerminalMap = Maps.newHashMap();
//	static {
//		convertTerminalMap.put("1", WebConstants.TERMINAL_PC);
//		convertTerminalMap.put("2", WebConstants.TERMINAL_WAP);
//		convertTerminalMap.put("3", WebConstants.AIKA_TERMINAL_APP);
//	}
//
//	private boolean checkUuidIsExistedInDb(String uuid) {
//		boolean isExisted = false;
//		if (StringUtils.isNotBlank(uuid)) {
//			CompanyExample example = new CompanyExample();
//			example.createCriteria().andUuidEqualTo(uuid);
//			int count = companyMapper.countByExample(example);
//			if (count > 0) {
//				isExisted = true;
//			}
//		}
//		return isExisted;
//	}
//
//	private boolean checkUserUuidIsExistedInDb(String uuid) {
//		boolean isExisted = false;
//		if (StringUtils.isNotBlank(uuid)) {
//			UserExample example = new UserExample();
//			example.createCriteria().andUuidEqualTo(uuid);
//			int count = userMapper.countByExample(example);
//			if (count > 0) {
//				isExisted = true;
//			}
//		}
//		return isExisted;
//	}
//}
