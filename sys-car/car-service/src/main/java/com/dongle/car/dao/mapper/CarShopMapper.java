package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarShop;
import com.dongle.car.dao.model.CarShopExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarShopMapper {
    long countByExample(CarShopExample example);

    int deleteByExample(CarShopExample example);

    int deleteByPrimaryKey(String id);

    int insert(CarShop row);

    int insertSelective(CarShop row);

    List<CarShop> selectByExample(CarShopExample example);

    CarShop selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") CarShop row, @Param("example") CarShopExample example);

    int updateByExample(@Param("row") CarShop row, @Param("example") CarShopExample example);

    int updateByPrimaryKeySelective(CarShop row);

    int updateByPrimaryKey(CarShop row);

    int batchInsert(@Param("list") List<CarShop> list);

    int batchInsertSelective(@Param("list") List<CarShop> list, @Param("selective") CarShop.Column ... selective);
}