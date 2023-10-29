package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaPlatform;
import com.iwanvi.nvwa.dao.model.QuotaPlatformExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaPlatformMapper {
    int countByExample(QuotaPlatformExample example);

    int deleteByExample(QuotaPlatformExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaPlatform record);

    int insertSelective(QuotaPlatform record);

    List<QuotaPlatform> selectByExample(QuotaPlatformExample example);

    QuotaPlatform selectByPrimaryKey(Integer id);

    QuotaPlatform getPlatformByDayHour(Map<String, Object> paramMap);

    int updateByExampleSelective(@Param("record") QuotaPlatform record, @Param("example") QuotaPlatformExample example);

    int updateByExample(@Param("record") QuotaPlatform record, @Param("example") QuotaPlatformExample example);

    int updateByPrimaryKeySelective(QuotaPlatform record);

    int updateByPrimaryKey(QuotaPlatform record);
    
    List<QuotaPlatform> getOneFcByhour(Map<String, Object> paramMap);
    
    List<QuotaPlatform> selecAllByCreday(QuotaPlatformExample example);
    
    List<QuotaPlatform> getPlatformsDataByHour(Map<String, Object> paramMap);
    
    List<QuotaPlatform> getPlatformsDataToday(Map<String, Object> paramMap);
    
    List<QuotaPlatform> getOnePlatformDataByDay(Map<String, Object> paramMap);

	List<QuotaPlatform> getOnePlatformPageDataByDay(Map<String, Object> param);

	List<QuotaPlatform> sumByDay(Map<String, Object> paramMap);

	List<QuotaPlatform> sumByDayWithGroup(Map<String, Object> paramMap);

	List<QuotaPlatform> detailByDay(Map<String, Object> paramMap);

	List<QuotaPlatform> sumItemByDay(Map<String, Object> paramMap);

}