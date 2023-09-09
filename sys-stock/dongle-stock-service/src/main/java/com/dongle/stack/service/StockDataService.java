package com.dongle.stack.service;

import com.dongle.stack.model.StockModel;

import java.util.List;

/**
 * @author Dongle
 * @desc 股票数据服务，负责统计和计算股票数据
 * @since 2023/9/9 009 10:18
 */
public interface StockDataService {

    /**
     * 查询股票指定天数范围内数据，
     * @param code
     * @param day 天数范围，7,30，60
     * @return
     */
    List<StockModel> queryStockData(String code,int day);

    /**
     * 获取最新一天的单股票数据
     * @param code
     * @return
     */
    StockModel queryNewDayStockData(String code);

}
