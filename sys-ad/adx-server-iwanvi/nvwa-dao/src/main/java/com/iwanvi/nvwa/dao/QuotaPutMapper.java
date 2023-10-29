package com.iwanvi.nvwa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iwanvi.nvwa.dao.model.QuotaPut;
import com.iwanvi.nvwa.dao.model.QuotaPutExample;

public interface QuotaPutMapper {
    int countByExample(QuotaPutExample example);

    int deleteByExample(QuotaPutExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaPut record);

    int insertSelective(QuotaPut record);

    List<QuotaPut> selectByExample(QuotaPutExample example);

    QuotaPut selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaPut record, @Param("example") QuotaPutExample example);

    int updateByExample(@Param("record") QuotaPut record, @Param("example") QuotaPutExample example);

    int updateByPrimaryKeySelective(QuotaPut record);

    int updateByPrimaryKey(QuotaPut record);
    
    List<QuotaPut> selectSumDataByCreday(QuotaPutExample example);
    
    List<QuotaPut> selectSumDataByFlow(QuotaPutExample example);

    List<QuotaPut> getOneFlowDataByHour(Map<String, Object> paramMap);
    
    List<QuotaPut> getFlowDataByHourAndFlow(Map<String, Object> paramMap);

    List<QuotaPut> getFlowDataByDay(Map<String, Object> paramMap);
    
	List<QuotaPut> getFlowDataOneDayByFs(Map<String, Object> paramMap);
    
	List<QuotaPut> listByDay(Map<String, Object> paramMap);

	List<QuotaPut> listByOidOrPid(Map<String, Object> paramMap);

	List<QuotaPut> getFsByDay(Map<String, Object> paramMap);
	
	List<QuotaPut> getFsByFs(Map<String, Object> paramMap);
	
	Map<String, Object> getQuotaPutByDayHour(Map<String, Object> paramMap);
}