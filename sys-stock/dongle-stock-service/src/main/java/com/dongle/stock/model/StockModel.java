package com.dongle.stock.model;

import lombok.Data;

import java.util.List;

@Data
public class StockModel {

    private String code;
    private String name;
    private String date;
    private double price;
    private double high;
    private double low;
    private double open;
    private int groupId;
    private String groupName;
    private double pctChg; // 涨跌率%
    private StockModel preData; //前日数据
    /**
     * Stock历史数据
     */
    private List<StockModel> data;


}
