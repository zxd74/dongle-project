package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarImages;
import com.dongle.car.dao.model.CarImagesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarImagesMapper {
    long countByExample(CarImagesExample example);

    int deleteByExample(CarImagesExample example);

    int deleteByPrimaryKey(String id);

    int insert(CarImages row);

    int insertSelective(CarImages row);

    List<CarImages> selectByExample(CarImagesExample example);

    CarImages selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") CarImages row, @Param("example") CarImagesExample example);

    int updateByExample(@Param("row") CarImages row, @Param("example") CarImagesExample example);

    int updateByPrimaryKeySelective(CarImages row);

    int updateByPrimaryKey(CarImages row);
}