package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DeviceModel;
import com.iwanvi.nvwa.dao.model.DeviceModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeviceModelMapper {
    long countByExample(DeviceModelExample example);

    int deleteByExample(DeviceModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeviceModel record);

    int insertSelective(DeviceModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device_model
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DeviceModel selectOneByExample(DeviceModelExample example);

    List<DeviceModel> selectByExample(DeviceModelExample example);

    DeviceModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeviceModel record, @Param("example") DeviceModelExample example);

    int updateByExample(@Param("record") DeviceModel record, @Param("example") DeviceModelExample example);

    int updateByPrimaryKeySelective(DeviceModel record);

    int updateByPrimaryKey(DeviceModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device_model
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<DeviceModel> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device_model
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<DeviceModel> list, @Param("selective") DeviceModel.Column ... selective);
}