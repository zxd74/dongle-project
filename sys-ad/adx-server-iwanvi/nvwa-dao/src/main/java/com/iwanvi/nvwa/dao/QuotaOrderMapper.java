package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.QuotaOrder;
import com.iwanvi.nvwa.dao.model.QuotaOrderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuotaOrderMapper {
    int countByExample(QuotaOrderExample example);

    int deleteByExample(QuotaOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuotaOrder record);

    int insertSelective(QuotaOrder record);

    List<QuotaOrder> selectByExample(QuotaOrderExample example);

    QuotaOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") QuotaOrder record, @Param("example") QuotaOrderExample example);

    int updateByExample(@Param("record") QuotaOrder record, @Param("example") QuotaOrderExample example);

    int updateByPrimaryKeySelective(QuotaOrder record);

    int updateByPrimaryKey(QuotaOrder record);

	List<QuotaOrder> listByDay(Map<String, Object> paramMap);

	List<QuotaOrder> listByInit(Map<String, Object> paramMap);
	
	QuotaOrder getQuotaOrderByDayHour(Map<String, Object> paramMap);
}