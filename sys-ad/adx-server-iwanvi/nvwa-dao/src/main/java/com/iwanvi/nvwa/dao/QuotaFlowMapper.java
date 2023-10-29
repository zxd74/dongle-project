package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaFlow;
import com.iwanvi.nvwa.dao.model.QuotaFlowExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaFlowMapper {
    int countByExample(QuotaFlowExample example);

    int deleteByExample(QuotaFlowExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaFlow record);

    int insertSelective(QuotaFlow record);

    List<QuotaFlow> selectByExample(QuotaFlowExample example);

    QuotaFlow selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaFlow record, @Param("example") QuotaFlowExample example);

    int updateByExample(@Param("record") QuotaFlow record, @Param("example") QuotaFlowExample example);

    int updateByPrimaryKeySelective(QuotaFlow record);

    int updateByPrimaryKey(QuotaFlow record);

    List<QuotaFlow> selectSumDataByCreday(QuotaFlowExample example);

    List<QuotaFlow> selectSumDataByFlow(QuotaFlowExample example);

    List<QuotaFlow> getOneFlowDataByHour(Map<String, Object> paramMap);

    List<QuotaFlow> getFlowDataByHourAndFlow(Map<String, Object> paramMap);

    List<QuotaFlow> getFlowDataByDay(Map<String, Object> paramMap);

    List<QuotaFlow> getFlowDataOneDayByFs(Map<String, Object> paramMap);

    List<QuotaFlow> getFsByDay(Map<String, Object> paramMap);

    List<QuotaFlow> getFsByFs(Map<String, Object> paramMap);

    List<QuotaFlow> getAllFlowDataByUser(Map<String, Object> paramMap);

    List<QuotaFlow> sumWithDate(Map<String, Object> paramMap);

    List<QuotaFlow> sumWithGroup(Map<String, Object> paramMap);

    List<QuotaFlow> sumWithDateAndGroup(Map<String, Object> paramMap);

    List<QuotaFlow> detailReport(Map<String, Object> paramMap);
    
    List<Map<String, Object>> getAppAdPostionUuidList();
    List<Map<String, Object>> getChannelList();
    List<Map<String, Object>> getVersionList();
    QuotaFlow getQuotaFlowByCountKey(Map<String, Object> paramMap);
}