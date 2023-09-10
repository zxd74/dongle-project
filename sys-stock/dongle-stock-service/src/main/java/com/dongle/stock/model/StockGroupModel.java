package com.dongle.stock.model;

import lombok.Data;

import java.util.List;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:22
 */
@Data
public class StockGroupModel {
    private int groupId;
    private String groupName;
    private List<StockModel> stocks;
}
