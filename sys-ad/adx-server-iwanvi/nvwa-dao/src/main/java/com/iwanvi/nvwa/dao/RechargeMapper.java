package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.Recharge;
import com.iwanvi.nvwa.dao.model.RechargeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RechargeMapper {
    int countByExample(RechargeExample example);

    int deleteByExample(RechargeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Recharge record);

    int insertSelective(Recharge record);

    List<Recharge> selectByExample(RechargeExample example);

    Recharge selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Recharge record, @Param("example") RechargeExample example);

    int updateByExample(@Param("record") Recharge record, @Param("example") RechargeExample example);

    int updateByPrimaryKeySelective(Recharge record);

    int updateByPrimaryKey(Recharge record);

    List<Recharge> sumByAdvertiserAndDay(Map<String, Object> map);

    Map<String,Object> sumPriceByDate(Map<String,Object> map);
}