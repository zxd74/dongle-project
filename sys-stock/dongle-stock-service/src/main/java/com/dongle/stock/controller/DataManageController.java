package com.dongle.stock.controller;

import com.dongle.stock.model.StockGroupModel;
import com.dongle.stock.model.StockModel;
import com.dongle.stock.service.StockDataService;
import com.dongle.stock.service.StockGroupService;
import com.dongle.stock.service.StockHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/data")
public class DataManageController {


    @Autowired
    private StockHistoryService historyService;
    @Autowired
    private StockGroupService groupService;

    @Autowired
    private StockDataService dataService;

    /**
     * 查询素有股票交易信息 TODO 不应考虑提供
     * @return
     */
    @GetMapping("all")
    public List<StockModel> requestAll(){
        return historyService.queryAllStockData();
    }

    @GetMapping("new")
    public List<StockModel> getNewAll(){
        return historyService.queryNewAllStockData();
    }

    @GetMapping("stock")
    public List<StockModel> queryStockAll(String code, @RequestParam(required = false,defaultValue = "30") int day){
        return historyService.queryStockHistory(code,day);
    }

    @GetMapping("group-all")
    public List<StockModel> queryGroupStockAll(@RequestParam(required = false,defaultValue = "0") int groupId){
        StockGroupModel model = groupService.groupAll(groupId);
        if (model==null || model.getStocks()==null){
            return null;
        }
        model.setStocks(model.getStocks().stream().map(stockModel -> historyService.queryStock(stockModel.getCode())).collect(Collectors.toList()));
        historyService.queryStockData(model.getStocks());
        return model.getStocks();
    }
}
