package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.Put;

public interface AdxRelationService {

	void restAdxRelation(Integer objId, Integer objType);

	void deleteAdxRelation(Integer id, Integer objEntity) throws Exception;

	List<AdxRelation> selectList(AdxRelation conditionRlation);

	void audit(AdxRelation adxRelation, Integer uid);

	void add(AdxRelation adxRelation);
	
	void restAdxRelationByPut(Integer pid, Integer putType);
}
