package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.IndustryBlacklist;
import com.iwanvi.nvwa.dao.model.IndustryBlacklistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface IndustryBlacklistMapper {
    long countByExample(IndustryBlacklistExample example);

    int deleteByExample(IndustryBlacklistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(IndustryBlacklist record);

    int insertSelective(IndustryBlacklist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table industry_blacklist
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    IndustryBlacklist selectOneByExample(IndustryBlacklistExample example);

    List<IndustryBlacklist> selectByExample(IndustryBlacklistExample example);

    IndustryBlacklist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") IndustryBlacklist record, @Param("example") IndustryBlacklistExample example);

    int updateByExample(@Param("record") IndustryBlacklist record, @Param("example") IndustryBlacklistExample example);

    int updateByPrimaryKeySelective(IndustryBlacklist record);

    int updateByPrimaryKey(IndustryBlacklist record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table industry_blacklist
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<IndustryBlacklist> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table industry_blacklist
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<IndustryBlacklist> list, @Param("selective") IndustryBlacklist.Column ... selective);
}