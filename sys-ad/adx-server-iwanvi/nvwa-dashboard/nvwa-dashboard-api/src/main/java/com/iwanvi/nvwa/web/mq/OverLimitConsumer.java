package com.iwanvi.nvwa.web.mq;

import com.iwanvi.nvwa.common.redis.mq.RedisQMessageListener;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.PlanMapper;
import com.iwanvi.nvwa.dao.PutMapper;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.service.OrderPutService;
import com.iwanvi.nvwa.web.service.OrdersService;
import com.iwanvi.nvwa.web.service.PlanService;
import com.iwanvi.nvwa.web.service.PutService;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OverLimitConsumer implements RedisQMessageListener {
	private static final Logger logger = LoggerFactory.getLogger(OverLimitConsumer.class);
	public static final String MESSAGE_TYPE_USER = "user";
	public static final String MESSAGE_TYPE_PLAN = "plan";
	public static final String MESSAGE_TYPE_UNIT = "unit";
	public static final String MESSAGE_TYPE_ORDER_PUT = "order";
	public static final String COMPANY_KEY = "companyOverBalance";

	@Autowired
	private CompanyService companyService;
	@Autowired
	private PlanService planService;
	@Autowired
	private PlanMapper planMapper;
    @Autowired
    private PutService putService;
    @Autowired
    private PutMapper putMapper;
    @Autowired
    private OrderPutService orderPutService;
    @Autowired
    private OrdersService ordersService;
	@Autowired
	private RedisDao redisDao;

	private static final Long EXPIRED_TIME = 120l;

	@Override
	public void onMessage(String message) {
		logger.debug("Received message from redisMQ is: {}", message);
		if (StringUtils.isBlank(message)) {
			logger.error("Received message from redisMQ is null");
			throw new IllegalArgumentException("invalid message, must not be null");
		}
		List<String> args = StringUtils.str2List(message, Constants.SIGN_UNDERLINE);
		Date nowDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int nowStr = StringUtils.toInt(sdf.format(nowDate));
		if (args.size() > 2) {
			String type = args.get(0);
			int time = StringUtils.toInt(args.get(1));
			if (time > 0 && nowStr == time) {
				if (MESSAGE_TYPE_USER.equals(type)) {
					if (args.size() == 4) {
						String uuid = args.get(2);
						Long balance = StringUtils.toLong(args.get(3));
						companyOverBalance(uuid, balance);
					} else {
						logger.error("Received message from redisMQ is error:{}", message);
						throw new IllegalArgumentException("invalid message struct");
					}
				} else if (MESSAGE_TYPE_PLAN.equals(type)) {
					if (args.size() == 5) {
						String userId = args.get(2);
						Integer planId = StringUtils.toInt(args.get(3));
						Long cost = StringUtils.toLong(args.get(4));
						planOverLimit(userId, planId, cost);
					} else {
						logger.error("Received message from redisMQ is error:{}", message);
						throw new IllegalArgumentException("invalid message struct");
					}
				} else if (MESSAGE_TYPE_UNIT.equals(type)) {
					if (args.size() == 5) {
						String userId = args.get(2);
						Integer unitId = StringUtils.toInt(args.get(3));
						Long cost = StringUtils.toLong(args.get(4));
						putOverLimit(userId, unitId, cost);
					} else {
						logger.error("Received message from redisMQ is error:{}", message);
						throw new IllegalArgumentException("invalid message struct");
					}
				} else if (MESSAGE_TYPE_ORDER_PUT.equals(type)) {
					if (args.size() == 5) {
						String userId = args.get(2);
						Integer orderPutId = StringUtils.toInt(args.get(3));
						Long cost = StringUtils.toLong(args.get(4));
						orderPutOverLimit(userId,orderPutId, cost);
					} else {
						logger.error("Received message from redisMQ is error:{}", message);
						throw new IllegalArgumentException("invalid message struct");
					}
				}
			}
		}
	}

	private void companyOverBalance(String uuid, Long account) {
		try {
			logger.info("company over balance param : [ uid:{},acc:{} ]", uuid, account);
			String time = redisDao.get(COMPANY_KEY + uuid);
			logger.debug("companyOverBalance{},time:{}", uuid, time);
			if (StringUtils.isBlank(time)) {
                Company company = companyService.get(uuid);
                if (company != null) {
                    String BALANCE_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_TOTAL, uuid);
                    Long newBalance = Long.parseLong(redisDao.get(BALANCE_KEY));
                    logger.info("company : {},new balance :{}", uuid, newBalance);
                    if (newBalance == null) {
                        logger.error("error : agent balance is null.");
                        return;
                    }
                    if (company.getBalanceStatus().equals(Constants.STATE_VALID) && newBalance <= account) {
                        time = redisDao.get(COMPANY_KEY + uuid);
                        //再验证一次
                        if ( StringUtils.isBlank(time) ) {
                            redisDao.set(COMPANY_KEY + uuid, new Date().toString(), EXPIRED_TIME);
                            company.setBalanceStatus(Constants.STATE_INVALID);
                            companyService.update(company);
                            logger.info("company over balance success! userId: {}", company.getId());
                        }
                    } else {
                        logger.info("company over balance invalid! userId: {}", company.getId());
                    }
                }
			} else {
				logger.info("company message {} exist in redis", uuid);
			}
		} catch (Exception e) {
			logger.error("company over balance error!", e);
		}
	}

	private void planOverLimit(String userUuid, Integer planId, Long cost) {
		try {
			logger.info("plan over limit param : [ uuid:{},pid:{},cost:{} ]", userUuid, planId, cost);
			String time = redisDao.get("planOverLimit" + planId);
			if ( StringUtils.isBlank(time) ) {
                Company adver = companyService.get(userUuid);
				Plan plan = planMapper.selectByPrimaryKey(planId);
				if (adver != null && plan != null && adver.getId().equals(plan.getAdverId())) {
					if (plan.getLimitState().equals(Constants.STATE_VALID) &&
							plan.getPlanLimit().intValue() <= cost / 1000) {
						time = redisDao.get("planOverLimit" + planId);
						//再验证一次
						if ( StringUtils.isBlank(time) ) {
							redisDao.set("planOverLimit" + plan.getId(), new Date().toString(), EXPIRED_TIME);
							plan.setLimitState(Constants.STATE_INVALID);
							planService.update(plan);
							logger.info("plan over limit success! planId: {}", planId);
						}
					} else {
						logger.info("plan over limit invalid! planId: {}", planId);
					}
				}
			} else {
				logger.info(" plan message {} exist in redis", planId);
			}
		} catch (Exception e) {
			logger.error("plan over limit error!", e);
		}
	}
	
	private void putOverLimit(String userUuid, Integer putId, Long cost) {
		try {
			logger.info("put over limit param : [ uuid:{},uid:{},cost:{} ]", userUuid, putId, cost);
			String time = redisDao.get("unitOverLimit" + putId);
			if ( StringUtils.isBlank(time) ) {
                Company adver = companyService.get(userUuid);
				Put put = putMapper.selectByPrimaryKey(putId);
				Plan plan = planService.info(put.getPid());
				if (adver != null && put != null && adver.getId().equals(plan.getAdverId())) {
					if (put.getPutState().equals(Constants.STATE_VALID) &&
							put.getPutLimit().intValue() <= cost / 1000) {
						time = redisDao.get("unitOverLimit" + putId);
						//再验证一次
						if ( StringUtils.isBlank(time) ) {
							redisDao.set("unitOverLimit" + put.getId(), new Date().toString(), EXPIRED_TIME);
							put.setPutState(Constants.STATE_UNIT_LIMIT);
							putService.update(put);
							logger.info("put over limit success! putId: {}", putId);
						}
					} else {
						logger.info("put over limit invalid! putId: {}", putId);
					}
				}
			} else {
				logger.info("put message {} exist in redis", putId);
			}
		} catch (Exception e) {
			logger.error("put over limit error!", e);
		}
	}
	
	private void orderPutOverLimit(String userUUid, Integer orderPutId, Long kpi) {
		try {
			logger.info("orderPut over limit param : [ uuid:{},pid:{},kpi:{} ]",userUUid, orderPutId, kpi);
			String time = redisDao.get("orderPutOverLimit" + orderPutId);
			if ( StringUtils.isBlank(time) ) {
				Company company = companyService.get(userUUid);
				OrderPut orderPut = orderPutService.info(orderPutId);
				Orders orders = ordersService.info(orderPut.getOid());
				if (company != null && orderPut != null && company.getId().equals(orders.getCustId()) ) {
					if (orderPut.getPutState().equals(Constants.STATE_VALID) &&
							orderPut.getPutLimit().intValue() <= kpi) {
						time = redisDao.get("orderPutOverLimit" + orderPutId);
						//再验证一次
						if ( StringUtils.isBlank(time) ) {
							redisDao.set("orderPutOverLimit" + orderPut.getId(), new Date().toString(), EXPIRED_TIME);
							orderPut.setPutState(Constants.STATE_UNIT_LIMIT);
							orderPutService.update(orderPut);
							logger.info("orderPut over limit success! orderPutId: {}", orderPutId);
						}
					} else {
						logger.info("orderPut over limit invalid! orderPutId: {}", orderPutId);
					}
				}else {
					logger.info("orderPutOverLimit error companyId: {}, orderPutId: {}, ordersId: {}",company.getId(),orderPut.getId(),orderPut.getOid());
				}
			} else {
				logger.info("orderPut message {} exist in redis", orderPutId);
			}
		} catch (Exception e) {
			logger.error("orderPut over limit error!", e);
		}
	}
	

}
