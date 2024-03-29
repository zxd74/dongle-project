package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.ConsumerPositionPrice;
import com.iwanvi.nvwa.dao.model.ConsumerPositionPriceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConsumerPositionPriceMapper {
    long countByExample(ConsumerPositionPriceExample example);

    int deleteByExample(ConsumerPositionPriceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ConsumerPositionPrice record);

    int insertSelective(ConsumerPositionPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer_position_price
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    ConsumerPositionPrice selectOneByExample(ConsumerPositionPriceExample example);

    List<ConsumerPositionPrice> selectByExample(ConsumerPositionPriceExample example);

    ConsumerPositionPrice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ConsumerPositionPrice record, @Param("example") ConsumerPositionPriceExample example);

    int updateByExample(@Param("record") ConsumerPositionPrice record, @Param("example") ConsumerPositionPriceExample example);

    int updateByPrimaryKeySelective(ConsumerPositionPrice record);

    int updateByPrimaryKey(ConsumerPositionPrice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer_position_price
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<ConsumerPositionPrice> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table consumer_position_price
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<ConsumerPositionPrice> list, @Param("selective") ConsumerPositionPrice.Column ... selective);
}