package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.mq.CrontabProducer;
import com.iwanvi.nvwa.web.service.SysCrontabService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class SysCrontabServiceImpl implements SysCrontabService {

	private static final Logger logger = LoggerFactory.getLogger(SysCrontabServiceImpl.class);

	@Autowired
	private SysCrontabMapper sysCrontabMapper;
	@Autowired
	private PutMapper putMapper;
	@Autowired
	private PlanMapper planMapper;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderPutMapper orderPutMapper;
	@Autowired
	private CrontabProducer crontabProducer;

	@Override
	public boolean addSysCrontab(Integer objectId, Integer objectType, Integer opType) throws ServiceException {
		logger.info("add syscrontab objid:{},objtype:{},optype:{}", objectId, objectType, opType);
		SysCrontab sysCrontab = new SysCrontab();
		sysCrontab.setObjectId(objectId);
		sysCrontab.setObjectType(objectType);
		sysCrontab.setOpType(opType);
		sysCrontab.setOpStatus(Constants.STATUS_UNHANDLE);
		Integer crontabId = addSysCrontab(sysCrontab);
		crontabProducer.send(crontabId);
		return true;
	}

	@Override
	public boolean addSysCrontabCheckCount(Integer objectId, Integer objectType, Integer opType)
			throws ServiceException {
		int count = countCrontab(objectId, objectType);
		if (count > 0) {
			logger.info("add syscrontab objid:{},objtype:{},optype:{}", objectId, objectType, opType);
			SysCrontab sysCrontab = new SysCrontab();
			sysCrontab.setObjectId(objectId);
			sysCrontab.setObjectType(objectType);
			sysCrontab.setOpType(opType);
			sysCrontab.setOpStatus(Constants.STATUS_UNHANDLE);
			Integer crontabId = addSysCrontab(sysCrontab);
			crontabProducer.send(crontabId);
		}
		return true;
	}

	private Integer addSysCrontab(SysCrontab sysCrontab) {
		sysCrontab.setOpStatus(Constants.STATUS_UNHANDLE);
		sysCrontab.setOpTime(new Date());
		sysCrontabMapper.insert(sysCrontab);
		logger.info("insert crontab success! objId: {}, objType: {}", sysCrontab.getObjectId(),
				sysCrontab.getObjectType());
		sysCrontabMapper.countByExample(null);
		return sysCrontab.getId();
	}

	public Integer insertSysCrontab(SysCrontab sysCrontab) {
		sysCrontab.setOpStatus(Constants.STATUS_UNHANDLE);
		sysCrontab.setOpTime(new Date());
		sysCrontabMapper.insert(sysCrontab);
		logger.info("insert crontab success! objId: {}, objType: {}", sysCrontab.getObjectId(),
				sysCrontab.getObjectType());
		sysCrontabMapper.countByExample(null);
		crontabProducer.send(sysCrontab.getId());
		return sysCrontab.getId();
	}

	@Override
	public boolean inserSysCrontab(Integer objectId, Integer objectType, Integer opType) {
		logger.info("insert syscrontab objid:{},objtype:{},optype:{}", objectId, objectType, opType);
		SysCrontab sysCrontab = new SysCrontab();
		sysCrontab.setObjectId(objectId);
		sysCrontab.setObjectType(objectType);
		sysCrontab.setOpType(opType);
		insertSysCrontab(sysCrontab);
		return true;
	}

	@Override
	public int countCrontab(Integer objectId, Integer objectType) {
		SysCrontabExample example = new SysCrontabExample();
		example.createCriteria().andObjectIdEqualTo(objectId).andObjectTypeEqualTo(objectType);
		return sysCrontabMapper.countByExample(example);
	}

	@Override
	public boolean updateSysCrontabByEntity(Integer objectId, Integer objectType, Integer opType)
			throws ServiceException {
		SysCrontab sysCrontab = getUniqueCrontab(objectId, objectType);
		SysCrontab putCrontab = new SysCrontab();
		putCrontab.setObjectId(objectId);
		putCrontab.setObjectType(objectType);
		if (sysCrontab != null) {
			putCrontab.setParentId(sysCrontab.getParentId());
			putCrontab.setOpType(Constants.OP_UPDATE);
			Integer crontabId = insertSysCrontab(putCrontab);
		} else {
			// 如果单元的同步数据不存在，说明此时审核通过的创意是该单元下第一个创意，所以需要同步单元和创意，
			// 此时也需要判断广告主和计划的同步状态。
			OrderPut orderPut = orderPutMapper.selectByPrimaryKey(objectId);
			Orders orders = ordersMapper.selectByPrimaryKey(orderPut.getOid());
			Integer fatherId = orders.getId();
			Integer adverId = orders.getCustId();
			Integer fatherObjType = Constants.OBJ_ORDER;
			logger.info(
					"sysCrontab updateSysCrontabByEntity objId: {}, objType: {}, fatherId: {}, adverId: {}, fatherObjType: {}",
					objectId, objectType, fatherId, adverId, fatherObjType);
			SysCrontab fatherCrontab = getUniqueCrontab(fatherId, fatherObjType);
			if (fatherCrontab == null) {
				SysCrontab userCrontab = getUniqueCrontab(adverId, Constants.OBJ_ADVERTISER);
				if (userCrontab == null) {
					userCrontab = new SysCrontab();
					userCrontab.setObjectId(adverId);
					userCrontab.setOpType(Constants.OP_ADD);
					userCrontab.setObjectType(Constants.OBJ_ADVERTISER);
					Integer crontabId = insertSysCrontab(userCrontab);
				}
				fatherCrontab = new SysCrontab();
				fatherCrontab.setObjectId(fatherId);
				fatherCrontab.setObjectType(fatherObjType);
				fatherCrontab.setOpType(Constants.OP_ADD);
				fatherCrontab.setParentId(adverId);
				Integer crontabId = insertSysCrontab(fatherCrontab);
			}
			putCrontab.setParentId(fatherId);
			putCrontab.setOpType(Constants.OP_ADD);
			Integer crontabId = insertSysCrontab(putCrontab);
		}
		return true;
	}

	public SysCrontab getUniqueCrontab(Integer objectId, Integer objectType) throws ServiceException {
		SysCrontabExample sysCrontabExample = new SysCrontabExample();
		sysCrontabExample.createCriteria().andObjectIdEqualTo(objectId).andObjectTypeEqualTo(objectType);
		List<SysCrontab> sysCrontabs = sysCrontabMapper.selectByExample(sysCrontabExample);
		if (!CollectionUtils.isEmpty(sysCrontabs)) {
			return sysCrontabs.get(0);
		}
		return null;
	}
}
