package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarDict;
import com.dongle.car.dao.model.CarDictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarDictMapper {
    long countByExample(CarDictExample example);

    int deleteByExample(CarDictExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarDict row);

    int insertSelective(CarDict row);

    List<CarDict> selectByExample(CarDictExample example);

    CarDict selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarDict row, @Param("example") CarDictExample example);

    int updateByExample(@Param("row") CarDict row, @Param("example") CarDictExample example);

    int updateByPrimaryKeySelective(CarDict row);

    int updateByPrimaryKey(CarDict row);

    int batchInsert(@Param("list") List<CarDict> list);

    int batchInsertSelective(@Param("list") List<CarDict> list, @Param("selective") CarDict.Column ... selective);
}