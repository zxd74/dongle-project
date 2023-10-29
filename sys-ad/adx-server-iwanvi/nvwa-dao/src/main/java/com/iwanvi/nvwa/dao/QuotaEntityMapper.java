package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaEntity;
import com.iwanvi.nvwa.dao.model.QuotaEntityExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaEntityMapper {
    int countByExample(QuotaEntityExample example);

    int deleteByExample(QuotaEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaEntity record);

    int insertSelective(QuotaEntity record);

    List<QuotaEntity> selectByExample(QuotaEntityExample example);

    QuotaEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaEntity record, @Param("example") QuotaEntityExample example);

    int updateByExample(@Param("record") QuotaEntity record, @Param("example") QuotaEntityExample example);

    int updateByPrimaryKeySelective(QuotaEntity record);

    int updateByPrimaryKey(QuotaEntity record);

	List<QuotaEntity> listByDay(Map<String, Object> paramMap);

	List<QuotaEntity> listByUidAndTime(Map<String, Object> paramMap);

	/**
	 * 
	 * @param 创意ID creativeIds
	 * @return
	 */
    List<Map<String, Object>> getIdInfoByCids(Map<String, Object> paramMap);

}