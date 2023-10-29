package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AgentPrice;
import com.iwanvi.nvwa.dao.model.AgentPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AgentPriceMapper {
    int countByExample(AgentPriceExample example);

    int deleteByExample(AgentPriceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AgentPrice record);

    int insertSelective(AgentPrice record);

    List<AgentPrice> selectByExample(AgentPriceExample example);

    AgentPrice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AgentPrice record, @Param("example") AgentPriceExample example);

    int updateByExample(@Param("record") AgentPrice record, @Param("example") AgentPriceExample example);

    int updateByPrimaryKeySelective(AgentPrice record);

    int updateByPrimaryKey(AgentPrice record);

    List<AgentPrice> selectAgentPrice(AgentPriceExample example);

    List<Integer> selectSysPricePids();
}