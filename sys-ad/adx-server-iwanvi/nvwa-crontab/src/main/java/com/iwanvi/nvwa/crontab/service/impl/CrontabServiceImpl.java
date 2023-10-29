package com.iwanvi.nvwa.crontab.service.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.protobuf.TextFormat;
import com.iwanvi.adserv.ngx.util.AdPositionFloorPriceUtils;
import com.iwanvi.adserv.ngx.util.ByteUtils;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.crontab.mq.AuditMessageProducer;
import com.iwanvi.nvwa.crontab.redis.ByteArrayRedisDao;
import com.iwanvi.nvwa.crontab.service.AdPositionPriceService;
import com.iwanvi.nvwa.crontab.service.AdpositionService;
import com.iwanvi.nvwa.crontab.service.AdvertiserDspService;
import com.iwanvi.nvwa.crontab.service.AgentPriceService;
import com.iwanvi.nvwa.crontab.service.AkFlowSourceService;
import com.iwanvi.nvwa.crontab.service.AppService;
import com.iwanvi.nvwa.crontab.service.AreaGroupService;
import com.iwanvi.nvwa.crontab.service.CompanyService;
import com.iwanvi.nvwa.crontab.service.CompetingProductService;
import com.iwanvi.nvwa.crontab.service.CrontabService;
import com.iwanvi.nvwa.crontab.service.EntityDspService;
import com.iwanvi.nvwa.crontab.service.EntityService;
import com.iwanvi.nvwa.crontab.service.FlowConsumerService;
import com.iwanvi.nvwa.crontab.service.OrderPutService;
import com.iwanvi.nvwa.crontab.service.OrderService;
import com.iwanvi.nvwa.crontab.service.PlanService;
import com.iwanvi.nvwa.crontab.service.PutService;
import com.iwanvi.nvwa.crontab.service.SysConfigService;
import com.iwanvi.nvwa.crontab.service.SysCrontabService;
import com.iwanvi.nvwa.crontab.util.CrontabConstants;
import com.iwanvi.nvwa.crontab.util.OssHandler;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.AreaGroup;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.CompetingProducts;
import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.dao.model.Entity;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.OrderPut;
import com.iwanvi.nvwa.dao.model.Orders;
import com.iwanvi.nvwa.dao.model.Plan;
import com.iwanvi.nvwa.dao.model.Put;
import com.iwanvi.nvwa.dao.model.SysCrontab;
import com.iwanvi.nvwa.proto.AdModelsProto;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto;
import com.iwanvi.nvwa.proto.AdModelsProto.AdPositionFloorPrice;
import com.iwanvi.nvwa.proto.AdModelsProto.CompetingProduct;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

@Service
public class CrontabServiceImpl implements CrontabService {

	private static final Logger logger = LoggerFactory.getLogger(CrontabServiceImpl.class);

	private static volatile boolean isPub4Entire = false; // 状态位：正在全量更新
	private static volatile boolean isPub4Incremental = false; // 状态位：正在增量更新
	private static volatile boolean isPub4AgentPrice = false; // 状态位：正在代理商底价全量更新

	@Autowired
	private SysCrontabService sysCrontabService;

	@Autowired
	private PutService putService;

	@Autowired
	private PlanService planService;

	@Autowired
	private AdpositionService adpositionService;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private AppService appService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private EntityService entityService;

	@Autowired
	private AgentPriceService agentPriceService;

	@Autowired
	private AuditMessageProducer auditMessageProducer;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderPutService orderPutService;

	@Autowired
	private FlowConsumerService flowConsumerService;

	@Autowired
	private EntityDspService entityDspService;

	@Autowired
	private AkFlowSourceService akFlowSourceService;

	@Autowired
	private FlowSourceMapper flowSourceMapper;

	@Autowired
	private AreaGroupService areaGroupService;
	
	@Autowired
	private CompetingProductService competingProductService;

	@Autowired
	private AdPositionPriceService adPositionPriceService;

	@Autowired
	private AdPositionMapper adPositionMapper;
	
	@Autowired
	private AdvertiserDspService advertiserDspService;
	
	@Autowired
	private ByteArrayRedisDao redisDao;
	
	@Override
	@Scheduled(cron = "0 2 0 * * ?")
	public void pub4Entire() {
		pub4Entire(true);

	}

	@Override
	public void pub4Entire(boolean isScheduledTask) {
		if (isPub4Entire) {
			logger.error("pub4Entire is runing! pub4Entire canceled...");
			return;
		}
		if (isPub4Incremental) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
			logger.warn("pub4Incremental is runing!");
		}
		logger.info("pub4Entire start...");
		long time = System.currentTimeMillis();
		isPub4Entire = true;
		OutputStream data = null;
		InputStream stream = null;
		try {
			if (isScheduledTask) {
				// 重置订单投放限额
				orderPutService.resetPutLimitState();
				// 检查订单投放过期状态
				orderPutService.checkExpiredState();
			}
			String dataPath = StringUtils.concat(CrontabConstants.FTP_UPLOAD_PATH, CrontabConstants.DAT, Constants.SIGN_OBLIQUELINE,
					DateUtils.format(new Date(), DateUtils.SHORT_FORMAT), Constants.SIGN_OBLIQUELINE,
					CrontabConstants.ENTIRE_FILE_PREFIX, Constants.SIGN_UNDERLINE,
					DateUtils.format(new Date(), DateUtils.FORMAT_YMDHMS), Constants.PUB_DATA_FILE_SUFFIX);

			File dataFile = new File(dataPath);
			if (!dataFile.getParentFile().exists()) {
				dataFile.getParentFile().mkdirs();
			}
			data = new BufferedOutputStream(new FileOutputStream(dataFile));

			// 竞价系数信息
			AdModelsProto.PubRecord adCommonConfigRecord = sysConfigService.buildPubRecord(NotifyMsgProto.OpType.kAdd);
			if (adCommonConfigRecord != null) {
				data.write(ByteUtils.encode(adCommonConfigRecord.toByteArray()));
				logger.info("pub4Entire: {}", adCommonConfigRecord.toString());
			}

			// ssp广告位
			List<AdPosition> positions = adpositionService.getValidPosList();
			if (!CollectionUtils.isEmpty(positions)) {
				for (AdPosition position : positions) {
					AdModelsProto.PubRecord sspPosRecord = adpositionService.buildPubRecord(position,
							NotifyMsgProto.OpType.kAdd);
					if (sspPosRecord != null) {
						data.write(ByteUtils.encode(sspPosRecord.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", sspPosRecord.getEntityType(), position.getId());
					}
				}
			}
			// 同步表中待处理数据 改为已处理
			List<SysCrontab> sysCrontabs = sysCrontabService.getAllByStatus(Constants.STATUS_UNHANDLE);
			sysCrontabService.updateStatus2Handled(sysCrontabs);

			// ssp App
			List<Apps> apps = appService.getAllSspApps();
			if (!CollectionUtils.isEmpty(apps)) {
				for (Apps app : apps) {
					AdModelsProto.PubRecord ap = appService.buildApp(app, NotifyMsgProto.OpType.kAdd);
					if (ap != null) {
						data.write(ByteUtils.encode(ap.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", ap.getEntityType(), app.getId());
					}
				}
			}
			// 爱卡流量源
			FlowSource flowSource = flowSourceMapper.selectByPrimaryKey(Constants.FLOW_MEDIA_AIKA);
			AdModelsProto.PubRecord sspPosRecord = akFlowSourceService.buildPubRecord(flowSource,
					NotifyMsgProto.OpType.kAdd);
			if (sspPosRecord != null) {
				data.write(ByteUtils.encode(sspPosRecord.toByteArray()));
				logger.info("pub4Entire! type: {}, akid: {}", sspPosRecord.getEntityType(), Constants.FLOW_MEDIA_AIKA);
			}

			// 第三方广告平台及创意
			List<FlowConsumer> flowConsumers = flowConsumerService.getAllFc();
			if (!CollectionUtils.isEmpty(flowConsumers)) {
				for (FlowConsumer flowConsumer : flowConsumers) {
					AdModelsProto.PubRecord fc = flowConsumerService.buildFlowConsumer(flowConsumer,
							NotifyMsgProto.OpType.kAdd);
					if (fc != null) {
						data.write(ByteUtils.encode(fc.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", fc.getEntityType(), flowConsumer.getId());
					}
					//全量同步的时候不再同步第三方创意，接入dsp平台越来越多第三方创意会越来越多，全量同步到广告引擎不再是最好的方案
//					List<DspCreative> list = entityDspService.buildEntiyDspList(flowConsumer.getId());
//					if (!CollectionUtils.isEmpty(list)) {
//						for (DspCreative dspCreative : list) {
//							AdModelsProto.PubRecord dc = entityDspService.buildDspCreative(dspCreative,
//									NotifyMsgProto.OpType.kAdd);
//							if (dc != null) {
//								data.write(ByteUtils.encode(dc.toByteArray()));
//								logger.info("pub4Entire! type: {}, id: {}", dc.getEntityType(), dspCreative.getId());
//							}
//						}
//					}
				}
			}

			// 地域
			List<AreaGroup> areaGroups = areaGroupService.getList();
			if (!CollectionUtils.isEmpty(areaGroups)) {
				for (AreaGroup areaGroup : areaGroups) {
					AdModelsProto.PubRecord areaGroupRecord = areaGroupService.buildPubRecord(areaGroup,
							NotifyMsgProto.OpType.kAdd, EntityType.kAreaLevel);
					if (areaGroupRecord != null) {
						data.write(ByteUtils.encode(areaGroupRecord.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", areaGroupRecord.getEntityType(), areaGroup.getId());
					}
				}

			}

			// 竞品信息
			List<CompetingProducts> competingProducts = competingProductService.getList();
			if (!CollectionUtils.isEmpty(competingProducts)) {
				for (CompetingProducts competingProduct : competingProducts) {
					AdModelsProto.PubRecord pubRecord = competingProductService.buildPubRecord(competingProduct,
							NotifyMsgProto.OpType.kAdd, EntityType.kCompetingProduct);
					if (pubRecord != null) {
						data.write(ByteUtils.encode(pubRecord.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", pubRecord.getEntityType(), competingProduct.getProductsName());
					}
				}

			}

			// 代理商
			List<Company> agents = companyService.getAllCompanyByType(CrontabConstants.COMPANY_AGENT_TYPE);
			if (!CollectionUtils.isEmpty(agents)) {
				for (Company company : agents) {
					AdModelsProto.PubRecord agentRecord = companyService.buildPubRecord(company,
							NotifyMsgProto.OpType.kAdd, EntityType.kAgent);
					if (agentRecord != null) {
						data.write(ByteUtils.encode(agentRecord.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", agentRecord.getEntityType(), company.getId());
					}
				}
			}
			// 广告主
			List<Company> advers = companyService.getAllCompanyByType(CrontabConstants.COMPANY_ADVER_TYPE);
			if (!CollectionUtils.isEmpty(advers)) {
				for (Company company : advers) {
					AdModelsProto.PubRecord advertiserRecord = companyService.buildPubAderRecord(company,
							NotifyMsgProto.OpType.kAdd, EntityType.kAdvertiser);
					if (advertiserRecord == null) {
						continue;
					}
					data.write(ByteUtils.encode(advertiserRecord.toByteArray()));
					logger.info("pub4Entire! type: {}, id: {}", advertiserRecord.getEntityType(), company.getId());
					// 订单
					List<Orders> orders = orderService.getValidOrderByCustId(company.getId());
					for (Orders order : orders) {
						AdModelsProto.PubRecord planRecord = orderService.buildPubRecord(order,
								NotifyMsgProto.OpType.kAdd);
						if (planRecord == null) {
							continue;
						}
						data.write(ByteUtils.encode(planRecord.toByteArray()));
						logger.info("pub4Entire! type: {}, id: {}", planRecord.getEntityType(), order.getId());

						List<OrderPut> orderputs = orderPutService.getValidOrderPutByOid(order.getId());
						if (CollectionUtils.isEmpty(orderputs)) {
							continue;
						}
						for (OrderPut orderPut : orderputs) {
							List<Entity> entities = entityService.getValidEntityByPut(orderPut.getId());
							AdModelsProto.PubRecord unitRecord = orderPutService.buildPubRecord(orderPut, entities,
									NotifyMsgProto.OpType.kAdd);
							if (unitRecord == null) {
								continue;
							}
							data.write(ByteUtils.encode(unitRecord.toByteArray()));
							logger.info("pub4Entire! type: {}, id: {}", unitRecord.getEntityType(), orderPut.getId());
						}

					}
				}
			}

			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(data);

			String[] split = dataPath.split(Constants.SIGN_OBLIQUELINE);

			String cdnRelativePath = StringUtils.concat(CrontabConstants.DAT, Constants.SIGN_OBLIQUELINE,
					DateUtils.format(new Date(), DateUtils.SHORT_FORMAT), Constants.SIGN_OBLIQUELINE,
					split[split.length - Constants.INTEGER_1]);
			OssHandler ossHandler = new OssHandler();
			OssHandler.uploadFile(ossHandler.getClient(), Constants.OSS_BUCKETNAME, cdnRelativePath, dataPath);

			auditMessageProducer.send(EntityType.kAdPlan, 0, OpType.kAdd,
					CrontabConstants.UPLOAD_URL_HOST + cdnRelativePath);

			logger.info("pub4Entire message is send, fileName:{}", CrontabConstants.UPLOAD_URL_HOST + cdnRelativePath);
			logger.info("pub4Entire succes!");
		} catch (Throwable e) {
			logger.error("pub4Entire error!", e);
		} finally {
			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(data);
			isPub4Entire = false;
			logger.info("pub4Entire end... elapsed: {}ms", System.currentTimeMillis() - time);
		}
	}

	@Override
	@Transactional
	public void pub4Incremental(Integer id) {
		// 判断同步状态决定等待还是跳过
		if (isPub4Entire || isPub4AgentPrice) {
			logger.warn("pub4Entire is runing! pub4Incremental skiped...");
			return;
		}
		while (isPub4Incremental) {
			logger.error("pub4Incremental is runing! pub4Incremental canceled...");
			try {
				Thread.sleep(3000l);
			} catch (InterruptedException e) {
				logger.info("pub4Incremental sleep error");
			}
		}
		// // 不做增量同步互斥
		// if (isPub4Incremental) {
		// logger.error("pub4Incremental is runing! pub4Incremental
		// canceled...");
		// }
		logger.info("pub4Incremental start...");
		long time = System.currentTimeMillis();
		isPub4Incremental = true;
		try {
			// 得到待处理的数据
			SysCrontab sysCrontab = sysCrontabService.getByIdAndStatus(id, Constants.STATUS_UNHANDLE);
			if (sysCrontab == null) {
				logger.info("no valid sysCrontab data!");
				return;
			}
			// 更改表状态
			sysCrontabService.updateStatus2Handled(sysCrontab);
			// 增量同步
			writeDataToRedis(sysCrontab);

			logger.info("pub4Incremental success");
		} catch (Exception e) {
			logger.error("pub4Incremental error!", e);
		} finally {
			isPub4Incremental = false;
			logger.info("pub4Incremental end... elapsed: {}ms", System.currentTimeMillis() - time);
		}

	}

	private void writeDataToRedis(SysCrontab sysCrontab) {
		if (sysCrontab != null) {
			NotifyMsgProto.OpType opType;
			if (Constants.OP_ADD.equals(sysCrontab.getOpType())) {
				opType = NotifyMsgProto.OpType.kAdd;
			} else if (Constants.OP_REMOVE.equals(sysCrontab.getOpType())) {
				opType = NotifyMsgProto.OpType.kDelete;
			} else {
				opType = NotifyMsgProto.OpType.kModify;
			}

			if (Constants.OBJ_ADVERTISER.equals(sysCrontab.getObjectType())) {// 广告主同步
				companyService.buildAdvertiserSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_ORDER.equals(sysCrontab.getObjectType())) {// 订单同步
				Orders orders = orderService.getOrder(sysCrontab.getObjectId());
				companyService.buildAdvertiserSend(orders.getCustId(), opType);
				orderService.buildOrderSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_ORDER_PUT.equals(sysCrontab.getObjectType())) {// 订单投放同步
				OrderPut orderPut = orderPutService.getOrderPut(sysCrontab.getObjectId());
				Orders order = orderService.getOrder(orderPut.getOid());
				orderService.buildOrderSend(order.getId(), opType);
				companyService.buildAdvertiserSend(order.getCustId(), opType);
				orderPutService.buildOrderPutSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_PLAN.equals(sysCrontab.getObjectType())) {// 计划同步
				Plan plan = planService.getPlan(sysCrontab.getObjectId());
				companyService.buildAdvertiserSend(plan.getAdverId(), opType);
				planService.buildPlanSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_PUT.equals(sysCrontab.getObjectType())) {// 单元同步
				Put put = putService.getPut(sysCrontab.getObjectId());
				Plan plan = planService.getPlan(put.getPid());
				planService.buildPlanSend(plan.getId(), opType);
				companyService.buildAdvertiserSend(plan.getAdverId(), opType);
				putService.buildPutSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_AD_TYPE_CONFIG.equals(sysCrontab.getObjectType())) {// 广告类型
				adpositionService.refreshCollection(sysCrontab.getObjectId());
				adpositionService.buildCollectionSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_COMMON_CONFIG.equals(sysCrontab.getObjectType())) {//// 竞价系数
				sysConfigService.buildConfigSend(opType);
			} else if (Constants.OBJ_AGENT.equals(sysCrontab.getObjectType())) {// 代理商同步
				companyService.buildAgentSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_PRICE.equals(sysCrontab.getObjectType())) {
				agentPriceService.buildAgentPriceSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_APP.equals(sysCrontab.getObjectType())) {// ssp // app同步
				appService.buildAppSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_APP_POSITION.equals(sysCrontab.getObjectType())) {// ssp广告位同步
				adpositionService.buildPosSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_FLOW_CONSUMER.equals(sysCrontab.getObjectType())) {// 第三方平台
				flowConsumerService.buildPosSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_DSP_ADVERTISER.equals(sysCrontab.getObjectType())) {// 第三方广告主
				advertiserDspService.buildPosSend(sysCrontab.getObjectId(), opType);
			}else if (Constants.OBJ_DSP_ENTITY.equals(sysCrontab.getObjectType())) {// 三方平台创意
				entityDspService.buildPosSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_AK_MEDIA.equals(sysCrontab.getObjectType())) {
				akFlowSourceService.buildAKAppSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_AK_ADP.equals(sysCrontab.getObjectType())) {
				adpositionService.buildPosSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_AREA_GROUP.equals(sysCrontab.getObjectType())) {
				areaGroupService.buildAreaGroupSend(sysCrontab.getObjectId(), opType);
			} else if (Constants.OBJ_AD_POSITION_PRICE.equals(sysCrontab.getObjectType())) {
				adPositionPriceService.cacheAdPositionPrice(sysCrontab.getObjectId());
			}
		}
	}

	@Override
//	@Scheduled(cron = "0 1 0 * * ?")
	public void scanAdPutRunState() {
		try {
			logger.info("scanAdPutRunState");
			putService.checkUnstartState();
		} catch (Exception ex) {
			logger.error("scanAdPutRunState error", ex);
		}
	}

	@Override
	@Scheduled(cron = "0 1 0 * * ?")
	public void scanAdOrderPutRunState() {
		try {
			logger.info("scanAdOrderPutRunState");
			orderPutService.checkOrderPutstartState();
		} catch (Exception ex) {
			logger.error("scanAdOrderPutRunState error", ex);
		}
	}

	@Override
	public String getAdpostionPrice(Integer id) {
		try {
			AdPosition adPosition = adPositionMapper.selectByPrimaryKey(id);
			AdPositionFloorPrice adfp = AdPositionFloorPrice.newBuilder().setAdPositionId(adPosition.getUuid())
					.setAreaLevelId(1).setIndustry(1).build();
			
			String cacheKey = org.apache.commons.lang3.StringUtils.join(Constants.REDIS_KEY_PREFIX_ADPFP,
					AdPositionFloorPriceUtils.genHashKey(adfp));
			byte[] bs = redisDao.get(cacheKey);
			AdPositionFloorPrice a = AdPositionFloorPrice.parseFrom(bs);
		    logger.debug("value: ",  a.toString());
		    return TextFormat.printToUnicodeString(a);
		} catch (Exception e) {
			logger.error("getAdpostionPrice error", e);
		}
		return StringUtils.EMPTY;
	}
}
