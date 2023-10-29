package com.dongle.adx.dao;

import com.dongle.adx.dao.model.TbPlatform;
import com.dongle.adx.dao.model.TbPlatformExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbPlatformMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    long countByExample(TbPlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int deleteByExample(TbPlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int insert(TbPlatform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int insertSelective(TbPlatform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    List<TbPlatform> selectByExample(TbPlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    TbPlatform selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbPlatform record, @Param("example") TbPlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbPlatform record, @Param("example") TbPlatformExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbPlatform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_platform
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbPlatform record);
}