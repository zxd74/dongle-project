package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AdxRelation;

public interface AdxRelationService {

	List<AdxRelation> getValidEntityAdxRelations(Integer id);

}
