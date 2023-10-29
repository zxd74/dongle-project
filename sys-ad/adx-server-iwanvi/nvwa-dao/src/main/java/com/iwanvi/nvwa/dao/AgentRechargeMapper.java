package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AgentRecharge;
import com.iwanvi.nvwa.dao.model.AgentRechargeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AgentRechargeMapper {
    int countByExample(AgentRechargeExample example);

    int deleteByExample(AgentRechargeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AgentRecharge record);

    int insertSelective(AgentRecharge record);

    List<AgentRecharge> selectByExample(AgentRechargeExample example);

    AgentRecharge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AgentRecharge record, @Param("example") AgentRechargeExample example);

    int updateByExample(@Param("record") AgentRecharge record, @Param("example") AgentRechargeExample example);

    int updateByPrimaryKeySelective(AgentRecharge record);

    int updateByPrimaryKey(AgentRecharge record);

    List<AgentRecharge> sumByAgentAndDay(Map<String, Object> map);

    Map<String,Object> sumPriceByDate(Map<String,Object> map);


}