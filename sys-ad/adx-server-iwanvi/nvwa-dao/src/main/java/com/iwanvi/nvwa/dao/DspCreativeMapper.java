package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.DspCreative;
import com.iwanvi.nvwa.dao.model.DspCreativeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DspCreativeMapper {
    long countByExample(DspCreativeExample example);

    int deleteByExample(DspCreativeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DspCreative record);

    int insertSelective(DspCreative record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_dsp
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    DspCreative selectOneByExample(DspCreativeExample example);

    List<DspCreative> selectByExample(DspCreativeExample example);

    DspCreative selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DspCreative record, @Param("example") DspCreativeExample example);

    int updateByExample(@Param("record") DspCreative record, @Param("example") DspCreativeExample example);

    int updateByPrimaryKeySelective(DspCreative record);

    int updateByPrimaryKey(DspCreative record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_dsp
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<DspCreative> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table entity_dsp
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<DspCreative> list, @Param("selective") DspCreative.Column ... selective);
}