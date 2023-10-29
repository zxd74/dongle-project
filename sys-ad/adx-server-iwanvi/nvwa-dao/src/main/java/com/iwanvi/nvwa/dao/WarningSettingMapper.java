package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.WarningSetting;
import com.iwanvi.nvwa.dao.model.WarningSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WarningSettingMapper {
    long countByExample(WarningSettingExample example);

    int deleteByExample(WarningSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WarningSetting record);

    int insertSelective(WarningSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_setting
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    WarningSetting selectOneByExample(WarningSettingExample example);

    List<WarningSetting> selectByExample(WarningSettingExample example);

    WarningSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WarningSetting record, @Param("example") WarningSettingExample example);

    int updateByExample(@Param("record") WarningSetting record, @Param("example") WarningSettingExample example);

    int updateByPrimaryKeySelective(WarningSetting record);

    int updateByPrimaryKey(WarningSetting record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_setting
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<WarningSetting> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table warning_setting
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<WarningSetting> list, @Param("selective") WarningSetting.Column ... selective);
}