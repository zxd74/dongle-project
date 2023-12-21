package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarUserCar;
import com.dongle.car.dao.model.CarUserCarExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarUserCarMapper {
    long countByExample(CarUserCarExample example);

    int deleteByExample(CarUserCarExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarUserCar row);

    int insertSelective(CarUserCar row);

    List<CarUserCar> selectByExample(CarUserCarExample example);

    CarUserCar selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarUserCar row, @Param("example") CarUserCarExample example);

    int updateByExample(@Param("row") CarUserCar row, @Param("example") CarUserCarExample example);

    int updateByPrimaryKeySelective(CarUserCar row);

    int updateByPrimaryKey(CarUserCar row);

    int batchInsert(@Param("list") List<CarUserCar> list);

    int batchInsertSelective(@Param("list") List<CarUserCar> list, @Param("selective") CarUserCar.Column ... selective);
}