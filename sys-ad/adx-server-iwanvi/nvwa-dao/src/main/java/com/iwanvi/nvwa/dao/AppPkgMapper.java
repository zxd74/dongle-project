package com.iwanvi.nvwa.dao;

import com.iwanvi.nvwa.dao.model.AppPkg;
import com.iwanvi.nvwa.dao.model.AppPkgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppPkgMapper {
    long countByExample(AppPkgExample example);

    int deleteByExample(AppPkgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AppPkg record);

    int insertSelective(AppPkg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_pkg
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    AppPkg selectOneByExample(AppPkgExample example);

    List<AppPkg> selectByExample(AppPkgExample example);

    AppPkg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AppPkg record, @Param("example") AppPkgExample example);

    int updateByExample(@Param("record") AppPkg record, @Param("example") AppPkgExample example);

    int updateByPrimaryKeySelective(AppPkg record);

    int updateByPrimaryKey(AppPkg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_pkg
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsert(@Param("list") List<AppPkg> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table app_pkg
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int batchInsertSelective(@Param("list") List<AppPkg> list, @Param("selective") AppPkg.Column ... selective);
}