package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.ProhibitedDate;
import com.iwanvi.nvwa.dao.model.ProhibitedDateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProhibitedDateMapper {
    long countByExample(ProhibitedDateExample example);

    int deleteByExample(ProhibitedDateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ProhibitedDate record);

    int insertSelective(ProhibitedDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prohibited_date
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ProhibitedDate selectOneByExample(ProhibitedDateExample example);

    List<ProhibitedDate> selectByExample(ProhibitedDateExample example);

    ProhibitedDate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ProhibitedDate record, @Param("example") ProhibitedDateExample example);

    int updateByExample(@Param("record") ProhibitedDate record, @Param("example") ProhibitedDateExample example);

    int updateByPrimaryKeySelective(ProhibitedDate record);

    int updateByPrimaryKey(ProhibitedDate record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prohibited_date
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<ProhibitedDate> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prohibited_date
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<ProhibitedDate> list, @Param("selective") ProhibitedDate.Column ... selective);
}