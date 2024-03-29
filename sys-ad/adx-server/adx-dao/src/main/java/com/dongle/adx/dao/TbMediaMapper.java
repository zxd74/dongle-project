package com.dongle.adx.dao;

import com.dongle.adx.dao.model.TbMedia;
import com.dongle.adx.dao.model.TbMediaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbMediaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    long countByExample(TbMediaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int deleteByExample(TbMediaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int insert(TbMedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int insertSelective(TbMedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    List<TbMedia> selectByExample(TbMediaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    TbMedia selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbMedia record, @Param("example") TbMediaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbMedia record, @Param("example") TbMediaExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbMedia record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_media
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbMedia record);
}