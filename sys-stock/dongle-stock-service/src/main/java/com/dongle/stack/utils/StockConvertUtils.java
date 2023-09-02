package com.dongle.stack.utils;

import com.dongle.stack.dao.entity.StockGroup;
import com.dongle.stack.dao.entity.StockGroupInfo;
import com.dongle.stack.dao.entity.StockHistoryData;
import com.dongle.stack.dao.entity.StockInfo;
import com.dongle.stack.model.StockGroupModel;
import com.dongle.stack.model.StockModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/2 10:23
 */
public class StockConvertUtils {

    public static StockModel convert(StockInfo stockInfo) {
        StockModel stockModel = new StockModel();
        convert(stockModel,stockInfo);
        return stockModel;
    }

    public static void convert(StockModel stockModel, StockInfo stockInfo) {
        if (stockInfo == null) return;
        stockModel.setCode(stockInfo.getCode());
        stockModel.setName(stockInfo.getName());
    }

    public static StockModel convert(StockHistoryData historyData) {
        StockModel stockModel = new StockModel();
        convert(stockModel,historyData);
        return stockModel;
    }

    public static void convert(StockModel stockModel, StockHistoryData historyData) {
        if (historyData == null) return;
        stockModel.setCode(historyData.getCode());
        stockModel.setDate(historyData.getDate());
        stockModel.setOpen(historyData.getOpen());
        stockModel.setHigh(historyData.getHigh());
        stockModel.setLow(historyData.getLow());
        stockModel.setPrice(historyData.getClose());
    }


    public static StockGroupModel convert(StockGroup group){
        if (group == null) return null;
        StockGroupModel model = new StockGroupModel();
        model.setGroupId(group.getId());
        model.setGroupName(group.getName());
        return model;
    }

    public static StockGroup convert(StockGroupModel model){
        if (model == null) return null;
        StockGroup group = new StockGroup();
        group.setId(model.getGroupId());
        group.setName(model.getGroupName());
        return group;
    }

    public static List<StockGroupInfo> convertGroupInfo(StockGroupModel model){
        if (model == null) return null;
        int id = model.getGroupId();
        List<StockGroupInfo> groupInfos = new ArrayList<>(model.getStocks().size());
        model.getStocks().forEach(stockModel -> groupInfos.add(convertGroupInfo(id,stockModel)));
        return groupInfos;
    }

    public static StockGroupInfo convertGroupInfo(int id,StockModel stockModel) {
        if (stockModel == null) return null;
        StockGroupInfo groupInfo = new StockGroupInfo();
        groupInfo.setGroupId(id);
        groupInfo.setCode(stockModel.getCode());
        return groupInfo;
    }
}
