package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaConsumer;
import com.iwanvi.nvwa.dao.model.QuotaConsumerExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaConsumerMapper {
    int countByExample(QuotaConsumerExample example);

    int deleteByExample(QuotaConsumerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaConsumer record);

    int insertSelective(QuotaConsumer record);

    List<QuotaConsumer> selectByExample(QuotaConsumerExample example);
    
    List<QuotaConsumer> selecAllByCreday(QuotaConsumerExample example);
    
    List<QuotaConsumer> getOneFcByhour(Map<String, Object> paramMap);
    
    List<QuotaConsumer> getOnePlatformDataByDay(Map<String, Object> paramMap);
    
    List<QuotaConsumer> getPlatformsDataByHour(Map<String, Object> paramMap);
    
    List<QuotaConsumer> getPlatformsDataToday(Map<String, Object> paramMap);
 
    QuotaConsumer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaConsumer record, @Param("example") QuotaConsumerExample example);

    int updateByExample(@Param("record") QuotaConsumer record, @Param("example") QuotaConsumerExample example);

    int updateByPrimaryKeySelective(QuotaConsumer record);

    int updateByPrimaryKey(QuotaConsumer record);
}