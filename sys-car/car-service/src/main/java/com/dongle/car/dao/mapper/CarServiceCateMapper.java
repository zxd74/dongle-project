package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarServiceCate;
import com.dongle.car.dao.model.CarServiceCateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarServiceCateMapper {
    long countByExample(CarServiceCateExample example);

    int deleteByExample(CarServiceCateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CarServiceCate row);

    int insertSelective(CarServiceCate row);

    List<CarServiceCate> selectByExample(CarServiceCateExample example);

    CarServiceCate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") CarServiceCate row, @Param("example") CarServiceCateExample example);

    int updateByExample(@Param("row") CarServiceCate row, @Param("example") CarServiceCateExample example);

    int updateByPrimaryKeySelective(CarServiceCate row);

    int updateByPrimaryKey(CarServiceCate row);

    int batchInsert(@Param("list") List<CarServiceCate> list);

    int batchInsertSelective(@Param("list") List<CarServiceCate> list, @Param("selective") CarServiceCate.Column ... selective);
}