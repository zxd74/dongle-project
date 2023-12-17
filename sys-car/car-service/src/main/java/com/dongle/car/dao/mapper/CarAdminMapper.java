package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarAdmin;
import com.dongle.car.dao.model.CarAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarAdminMapper {
    long countByExample(CarAdminExample example);

    int deleteByExample(CarAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarAdmin row);

    int insertSelective(CarAdmin row);

    List<CarAdmin> selectByExample(CarAdminExample example);

    CarAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarAdmin row, @Param("example") CarAdminExample example);

    int updateByExample(@Param("row") CarAdmin row, @Param("example") CarAdminExample example);

    int updateByPrimaryKeySelective(CarAdmin row);

    int updateByPrimaryKey(CarAdmin row);
}