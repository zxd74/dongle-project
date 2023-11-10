package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AdPositionMapping;
import com.iwanvi.nvwa.dao.model.AdPositionMappingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdPositionMappingMapper {
    long countByExample(AdPositionMappingExample example);

    int deleteByExample(AdPositionMappingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AdPositionMapping record);

    int insertSelective(AdPositionMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_position_mapping
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AdPositionMapping selectOneByExample(AdPositionMappingExample example);

    List<AdPositionMapping> selectByExample(AdPositionMappingExample example);

    AdPositionMapping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AdPositionMapping record, @Param("example") AdPositionMappingExample example);

    int updateByExample(@Param("record") AdPositionMapping record, @Param("example") AdPositionMappingExample example);

    int updateByPrimaryKeySelective(AdPositionMapping record);

    int updateByPrimaryKey(AdPositionMapping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_position_mapping
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<AdPositionMapping> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ad_position_mapping
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<AdPositionMapping> list, @Param("selective") AdPositionMapping.Column ... selective);
}