package com.iwanvi.nvwa.crontab.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.crontab.service.AdxRelationService;
import com.iwanvi.nvwa.dao.AdxRelationMapper;
import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.AdxRelationExample;

@Service
public class AdxRelationServiceImpl implements AdxRelationService {

	@Autowired
	private AdxRelationMapper adxRelationMapper;
	
	@Override
	public List<AdxRelation> getValidEntityAdxRelations(Integer id) throws ServiceException {
		AdxRelationExample example = new AdxRelationExample();
		example.createCriteria().andObjIdEqualTo(id).andStatusEqualTo(Constants.STATE_VALID)
				.andAuditStateEqualTo(Constants.STATE_VALID).andObjTypeEqualTo(Constants.OBJ_ENTITY);
		return adxRelationMapper.selectByExample(example);
	}
	

}
