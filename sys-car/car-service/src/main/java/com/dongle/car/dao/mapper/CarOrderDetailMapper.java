package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarOrderDetail;
import com.dongle.car.dao.model.CarOrderDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarOrderDetailMapper {
    long countByExample(CarOrderDetailExample example);

    int deleteByExample(CarOrderDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarOrderDetail row);

    int insertSelective(CarOrderDetail row);

    List<CarOrderDetail> selectByExample(CarOrderDetailExample example);

    CarOrderDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarOrderDetail row, @Param("example") CarOrderDetailExample example);

    int updateByExample(@Param("row") CarOrderDetail row, @Param("example") CarOrderDetailExample example);

    int updateByPrimaryKeySelective(CarOrderDetail row);

    int updateByPrimaryKey(CarOrderDetail row);
}