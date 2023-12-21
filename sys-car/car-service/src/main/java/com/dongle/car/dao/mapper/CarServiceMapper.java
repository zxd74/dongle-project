package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarService;
import com.dongle.car.dao.model.CarServiceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarServiceMapper {
    long countByExample(CarServiceExample example);

    int deleteByExample(CarServiceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarService row);

    int insertSelective(CarService row);

    List<CarService> selectByExample(CarServiceExample example);

    CarService selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarService row, @Param("example") CarServiceExample example);

    int updateByExample(@Param("row") CarService row, @Param("example") CarServiceExample example);

    int updateByPrimaryKeySelective(CarService row);

    int updateByPrimaryKey(CarService row);

    int batchInsert(@Param("list") List<CarService> list);

    int batchInsertSelective(@Param("list") List<CarService> list, @Param("selective") CarService.Column ... selective);
}