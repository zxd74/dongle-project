package com.iwanvi.nvwa.crontab.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.mq.CrontabProducer;
import com.iwanvi.nvwa.crontab.service.SysCrontabService;
import com.iwanvi.nvwa.dao.SysCrontabMapper;
import com.iwanvi.nvwa.dao.model.SysCrontab;
import com.iwanvi.nvwa.dao.model.SysCrontabExample;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Service
public class SysCrontabServiceImpl implements SysCrontabService {
	
	private static final Logger logger = LoggerFactory.getLogger(SysCrontabServiceImpl.class);

	@Autowired
	private SysCrontabMapper sysCrontabMapper;
	
	@Autowired
	private CrontabProducer crontabProducer;
	
	@Override
	public SysCrontab getByIdAndStatus(Integer id, Integer status) {
		SysCrontabExample example = new SysCrontabExample();
		example.createCriteria().andOpStatusEqualTo(status).andIdEqualTo(id);
		List<SysCrontab> crontabs = sysCrontabMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(crontabs)){
			return crontabs.get(0);
		}
		return null;
	}

	@Override
	public void updateStatus2Handled(SysCrontab sysCrontab) {
		sysCrontab.setOpStatus(Constants.STATUS_HANDLED);
		sysCrontabMapper.updateByPrimaryKeySelective(sysCrontab);	
	}

	@Override
	public void insertSysCrontab(Integer objectId, Integer objectType, Integer opType, Integer createUser) {
		if(sysCrontabExist(objectId, objectType)){
			SysCrontab sysCrontab = new SysCrontab();
			sysCrontab.setOpType(opType);
			sysCrontab.setObjectId(objectId);
			sysCrontab.setObjectType(objectType);
			sysCrontab.setParentId(createUser);
			//sysCrontab.setOpStatus(Constants.STATUS_UNHANDLE);
			sysCrontab.setOpStatus(Constants.STATUS_HANDLED);
			sysCrontab.setOpTime(new Date());
			sysCrontabMapper.insert(sysCrontab);
			logger.info("insert crontab success! objId: {}, objType: {}, opType: {}", sysCrontab.getObjectId(),
					sysCrontab.getObjectType(), opType);
			 //sysCrontabMapper.countByExample(null);
			 crontabProducer.send( sysCrontab.getId() );
		}
	}

	private boolean sysCrontabExist(Integer objectId, Integer objectType) {
		SysCrontabExample crontabExample = new SysCrontabExample();
		crontabExample.createCriteria().andObjectTypeEqualTo(objectType).andObjectIdEqualTo(objectId);
		List<SysCrontab> crontabList = sysCrontabMapper.selectByExample(crontabExample);
		if (!CollectionUtils.isEmpty(crontabList)) {
			return true;
		}
		return false;
	}

	@Override
	public List<SysCrontab> getAllByStatus(Integer statusUnhandle) {
		SysCrontabExample example = new SysCrontabExample();
		example.setOrderByClause("op_time asc");
		example.createCriteria().andOpStatusEqualTo(statusUnhandle);
		return sysCrontabMapper.selectByExample(example);
	}

	@Override
	public void updateStatus2Handled(List<SysCrontab> sysCrontabs) {
		if (!CollectionUtils.isEmpty(sysCrontabs)) {
			List<Integer> ids = FluentIterable.from(sysCrontabs).transform(new Function<SysCrontab, Integer>() {
				public Integer apply(SysCrontab p) {
					return p.getId();
				}
			}).toList();
			sysCrontabMapper.updateStatusByIds(ids);
		}
	}

}
