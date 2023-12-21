package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarOrder;
import com.dongle.car.dao.model.CarOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarOrderMapper {
    long countByExample(CarOrderExample example);

    int deleteByExample(CarOrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(CarOrder row);

    int insertSelective(CarOrder row);

    List<CarOrder> selectByExample(CarOrderExample example);

    CarOrder selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") CarOrder row, @Param("example") CarOrderExample example);

    int updateByExample(@Param("row") CarOrder row, @Param("example") CarOrderExample example);

    int updateByPrimaryKeySelective(CarOrder row);

    int updateByPrimaryKey(CarOrder row);

    int batchInsert(@Param("list") List<CarOrder> list);

    int batchInsertSelective(@Param("list") List<CarOrder> list, @Param("selective") CarOrder.Column ... selective);
}