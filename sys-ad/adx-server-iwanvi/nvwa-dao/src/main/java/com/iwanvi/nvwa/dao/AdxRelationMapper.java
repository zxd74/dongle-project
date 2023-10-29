package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AdxRelation;
import com.iwanvi.nvwa.dao.model.AdxRelationExample;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.Entity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdxRelationMapper {
	int countByExample(AdxRelationExample example);

	int deleteByExample(AdxRelationExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(AdxRelation record);

	int insertSelective(AdxRelation record);

	List<AdxRelation> selectByExample(AdxRelationExample example);

	AdxRelation selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") AdxRelation record, @Param("example") AdxRelationExample example);

	int updateByExample(@Param("record") AdxRelation record, @Param("example") AdxRelationExample example);

	int updateByPrimaryKeySelective(AdxRelation record);

	int updateByPrimaryKey(AdxRelation record);

	int selectCountAuditEntityByOid(@Param("entity") Entity entity, @Param("adverId") Integer adverId,
			@Param("objType") Integer objType, @Param("adxType") Integer adxType,
			@Param("entityStates") List<Integer> entityStates);

	List<Entity> selectAuditEntityByOid(@Param("entity") Entity entity, @Param("adverId") Integer adverId,
			@Param("objType") Integer objType, @Param("adxType") Integer adxType,
			@Param("entityStates") List<Integer> entityStates, @Param("start") Integer start,
			@Param("limit") Integer limit);

	int selectCountAuditEntityByPid(@Param("entity") Entity entity, @Param("adverId") Integer adverId,
			@Param("objType") Integer objType, @Param("adxType") Integer adxType,
			@Param("entityStates") List<Integer> entityStates);

	List<Entity> selectAuditEntityByPid(@Param("entity") Entity entity, @Param("adverId") Integer adverId,
			@Param("objType") Integer objType, @Param("adxType") Integer adxType,
			@Param("entityStates") List<Integer> entityStates, @Param("start") Integer start,
			@Param("limit") Integer limit);

	int selectCountAuditAdver(@Param("company") Company company, @Param("adxType") Integer adxType,
			@Param("objType") Integer objType);

	List<Company> selectAuditAdverPage(@Param("company") Company company, @Param("adxType") Integer flowMediaAika,
			@Param("objType") Integer objAdvertiser, @Param("start") int startPosition, @Param("limit") Integer ps);

}