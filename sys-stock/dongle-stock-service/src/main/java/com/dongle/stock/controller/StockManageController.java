package com.dongle.stock.controller;

import com.dongle.stock.model.StockModel;
import com.dongle.stock.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/stock-manage")
public class StockManageController {

    @Autowired
    private StockHistoryService historyService;

    /**
     * 查询stock基本信息
     * @param code 股票代码
     * @return 股票信息 or null
     */
    @GetMapping("query")
    public StockModel queryStockInfo(String code){
        return historyService.queryStock(code);
    }


    @RequestMapping("all-stock")
    public List<StockModel> allStock(){
        return historyService.queryAllStock();
    }
}
