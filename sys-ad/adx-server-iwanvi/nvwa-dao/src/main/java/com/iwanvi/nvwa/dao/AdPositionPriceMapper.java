package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AdPositionPrice;
import com.iwanvi.nvwa.dao.model.AdPositionPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdPositionPriceMapper {
    int countByExample(AdPositionPriceExample example);

    int deleteByExample(AdPositionPriceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdPositionPrice record);

    int insertSelective(AdPositionPrice record);

    List<AdPositionPrice> selectByExample(AdPositionPriceExample example);

    AdPositionPrice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdPositionPrice record, @Param("example") AdPositionPriceExample example);

    int updateByExample(@Param("record") AdPositionPrice record, @Param("example") AdPositionPriceExample example);

    int updateByPrimaryKeySelective(AdPositionPrice record);

    int updateByPrimaryKey(AdPositionPrice record);

    List<AdPositionPrice> getPositionByPrice();
}