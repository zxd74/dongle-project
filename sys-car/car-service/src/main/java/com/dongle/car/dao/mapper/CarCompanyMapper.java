package com.dongle.car.dao.mapper;

import com.dongle.car.dao.model.CarCompany;
import com.dongle.car.dao.model.CarCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CarCompanyMapper {
    long countByExample(CarCompanyExample example);

    int deleteByExample(CarCompanyExample example);

    int deleteByPrimaryKey(String id);

    int insert(CarCompany row);

    int insertSelective(CarCompany row);

    List<CarCompany> selectByExample(CarCompanyExample example);

    CarCompany selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("row") CarCompany row, @Param("example") CarCompanyExample example);

    int updateByExample(@Param("row") CarCompany row, @Param("example") CarCompanyExample example);

    int updateByPrimaryKeySelective(CarCompany row);

    int updateByPrimaryKey(CarCompany row);

    int batchInsert(@Param("list") List<CarCompany> list);

    int batchInsertSelective(@Param("list") List<CarCompany> list, @Param("selective") CarCompany.Column ... selective);
}