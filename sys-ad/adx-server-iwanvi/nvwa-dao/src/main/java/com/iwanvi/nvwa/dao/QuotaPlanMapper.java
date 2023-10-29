package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaPlan;
import com.iwanvi.nvwa.dao.model.QuotaPlanExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaPlanMapper {
    int countByExample(QuotaPlanExample example);

    int deleteByExample(QuotaPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaPlan record);

    int insertSelective(QuotaPlan record);

    List<QuotaPlan> selectByExample(QuotaPlanExample example);

    QuotaPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaPlan record, @Param("example") QuotaPlanExample example);

    int updateByExample(@Param("record") QuotaPlan record, @Param("example") QuotaPlanExample example);

    int updateByPrimaryKeySelective(QuotaPlan record);

    int updateByPrimaryKey(QuotaPlan record);

	List<QuotaPlan> listByDay(Map<String, Object> paramMap);

	List<QuotaPlan> listByInit(Map<String, Object> paramMap);
	
	Map<String, Object> getQuotaPlanByDayHour(Map<String, Object> paramMap);
}