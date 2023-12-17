package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarDictType;
import com.dongle.car.dao.model.CarDictTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarDictTypeMapper {
    long countByExample(CarDictTypeExample example);

    int deleteByExample(CarDictTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarDictType row);

    int insertSelective(CarDictType row);

    List<CarDictType> selectByExample(CarDictTypeExample example);

    CarDictType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarDictType row, @Param("example") CarDictTypeExample example);

    int updateByExample(@Param("row") CarDictType row, @Param("example") CarDictTypeExample example);

    int updateByPrimaryKeySelective(CarDictType row);

    int updateByPrimaryKey(CarDictType row);
}