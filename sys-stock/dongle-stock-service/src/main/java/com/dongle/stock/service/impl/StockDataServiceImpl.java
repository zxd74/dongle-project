package com.dongle.stock.service.impl;

import com.dongle.stock.config.cache.RedisClient;
import com.dongle.stock.model.StockModel;
import com.dongle.stock.service.StockDataCalcService;
import com.dongle.stock.service.StockDataService;
import com.dongle.stock.service.StockHistoryService;
import com.dongle.stock.utils.Constants;
import com.dongle.stock.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Dongle
 * @desc 股票数据服务
 * @since 2023/9/9 009 10:18
 */
@Service
public class StockDataServiceImpl implements StockDataService {

    /// TODO 股票涨幅情况应该录入时计算

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private StockHistoryService historyService;
    @Autowired
    private StockDataCalcService dataCalcService;

    @Override
    public List<StockModel> queryStockData(String code, int day) {
        String dayStr = day <= Constants.DAY_WEEK ? Constants.STR_DAY_WEEK
                : day <= Constants.DAY_MONTH ? Constants.STR_DAY_MONTH
                : day <= Constants.DAY_THREE_MONTH ? Constants.STR_DAY_THREE_MONTH
                : "all";
        return redisClient.hGetAndSet(String.format(Constants.REDIS_STOCK_PREFIX,code),String.format(Constants.REDIS_STOCK_DATA_PREFIX,dayStr),
            ()-> {
                List<StockModel> models = historyService.queryStockHistory(code, day);
                calc(models);
                return models;
            });
    }
    private void calc(List<StockModel> models){
        StockModel pre = null;
        for (StockModel model:models){
            if (pre == null) {
                pre = model;
                continue;
            }
            model.setPctChg(DataUtils.change(model.getPrice(),pre.getPrice()));
            pre = model;
        }
        pre = null;
    }

    public StockModel queryNewDayStockData(String code){
        // 股票数据汇总计算逻辑
        return redisClient.hGetAndSet(String.format(Constants.REDIS_STOCK_PREFIX,code),
                Constants.REDIS_STOCK_DATA_NEW,
                ()-> {
                    List<StockModel> models = historyService.queryStockHistory(code,2);
                    if (DataUtils.isEmpty(models)){
                        return null;
                    }
                    calc(models);
                    return models.get(models.size()-1);
                });
    }
}
