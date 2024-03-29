package com.dongle.adx.dao;

import com.dongle.adx.dao.model.TbPosition;
import com.dongle.adx.dao.model.TbPositionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPositionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    long countByExample(TbPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int deleteByExample(TbPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int insert(TbPosition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int insertSelective(TbPosition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    List<TbPosition> selectByExample(TbPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    TbPosition selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbPosition record, @Param("example") TbPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbPosition record, @Param("example") TbPositionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbPosition record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_position
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbPosition record);
}