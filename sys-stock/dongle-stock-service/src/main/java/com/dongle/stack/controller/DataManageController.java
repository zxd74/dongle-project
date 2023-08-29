package com.dongle.stack.controller;

import com.dongle.stack.model.Stock;
import com.dongle.stack.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/data")
public class DataManageController {


    @Autowired
    private StockHistoryService historyService;

    /**
     * 查询素有股票交易信息
     * @return
     */
    @GetMapping("all")
    public List<Stock> requestAll(){
        return historyService.queryAllStockData();
    }

    @GetMapping("new")
    public List<Stock> getNewAll(){
        return historyService.queryNewAllStockData();
    }

    @GetMapping("stock")
    public List<Stock> queryStockAll(String code,@RequestParam(required = false,defaultValue = "30") int day){
        return historyService.queryStockHistory(code,day);
    }

    @GetMapping("group-all")
    public List<Stock> queryGroupStockAll(String groupId){
        return historyService.queryGroupStockData(groupId);
    }
}
