package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.proto.AdModelsProto.PubRecord;
import com.iwanvi.nvwa.proto.CommonProto.EntityType;
import com.iwanvi.nvwa.proto.NotifyMsgProto.OpType;

public interface CompanyService {

	List<Company> getAllCompanyByType(Integer companyAgentType);

	PubRecord buildPubRecord(Company ampAgent, OpType kadd, EntityType type);

	void buildAdvertiserSend(Integer objectId, OpType opType);

	void buildAgentSend(Integer objectId, OpType opType);

	PubRecord buildPubAderRecord(Company company, OpType kadd, EntityType kadvertiser);


}
