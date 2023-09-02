package com.dongle.stack.model;

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
    /**
     * Stock历史数据
     */
    private List<StockModel> data;


}
