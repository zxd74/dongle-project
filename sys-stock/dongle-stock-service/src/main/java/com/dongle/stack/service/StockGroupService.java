package com.dongle.stack.service;

import com.dongle.stack.model.StockGroupModel;

import java.util.List;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:20
 */
public interface StockGroupService {
    /**
     * 查询所有分组
     * @return
     */
    List<StockGroupModel> all();

    /**
     * 添加分组
     * @param model
     * @return
     */
    boolean save(StockGroupModel model);

    /**
     * 删除分组
     * @param model
     * @return
     */
    boolean delete(StockGroupModel model);

    /**
     * 添加股票分组关联
     * @param model
     * @return
     */
    boolean addGroupStock(StockGroupModel model);
    /**
     * 删除股票分组关联项
     * @param model
     * @return
     */
    boolean deleteGroupStock(StockGroupModel model);

    /**
     * 查询分组全部：包含股票列表
     * @param groupId
     * @return
     */
    StockGroupModel groupAll(int groupId);
}
