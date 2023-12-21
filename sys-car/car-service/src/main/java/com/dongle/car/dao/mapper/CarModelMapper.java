package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarModel;
import com.dongle.car.dao.model.CarModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarModelMapper {
    long countByExample(CarModelExample example);

    int deleteByExample(CarModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarModel row);

    int insertSelective(CarModel row);

    List<CarModel> selectByExample(CarModelExample example);

    CarModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarModel row, @Param("example") CarModelExample example);

    int updateByExample(@Param("row") CarModel row, @Param("example") CarModelExample example);

    int updateByPrimaryKeySelective(CarModel row);

    int updateByPrimaryKey(CarModel row);

    int batchInsert(@Param("list") List<CarModel> list);

    int batchInsertSelective(@Param("list") List<CarModel> list, @Param("selective") CarModel.Column ... selective);
}