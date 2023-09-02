package com.dongle.stack.service.impl;

import com.dongle.stack.dao.StockGroupDao;
import com.dongle.stack.dao.StockGroupInfoDao;
import com.dongle.stack.dao.StockInfoDao;
import com.dongle.stack.dao.entity.StockGroup;
import com.dongle.stack.model.StockGroupModel;
import com.dongle.stack.model.StockModel;
import com.dongle.stack.service.StockGroupService;
import com.dongle.stack.utils.StockConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:20
 */
@Service
public class StockGroupServiceImpl implements StockGroupService {

    @Autowired
    private StockGroupDao stockGroupDao;
    @Autowired
    private StockGroupInfoDao stockGroupInfoDao;
    @Autowired
    private StockInfoDao stockInfoDao;

    @Override
    public List<StockGroupModel> all() {
        List<StockGroupModel> modelList = new ArrayList<>();
        stockGroupDao.findAll().forEach(group -> modelList.add(StockConvertUtils.convert(group)));
        return modelList;
    }

    @Override
    @Transactional
    public boolean save(StockGroupModel model) {
        stockGroupDao.save(StockConvertUtils.convert(model));
        if (model.getStocks()!=null){
            addGroupStock(model);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean delete(StockGroupModel model) {
        if (model.getGroupId() < 2){
            // 不可删除
            return false;
        }
        stockGroupInfoDao.deleteByGroupId(model.getGroupId());
        stockGroupDao.deleteByGroupId(model.getGroupId());
        return true;
    }

    @Override
    public boolean addGroupStock(StockGroupModel model) {
        stockGroupInfoDao.saveAll(StockConvertUtils.convertGroupInfo(model));
        return true;
    }

    @Override
    public boolean deleteGroupStock(StockGroupModel model) {
        stockGroupInfoDao.deleteAll(StockConvertUtils.convertGroupInfo(model));
        return true;
    }

    @Override
    public StockGroupModel groupAll(int groupId) {
        Optional<StockGroup> group = stockGroupDao.findById(groupId);
        if (!group.isPresent()) return null;
        StockGroupModel model = StockConvertUtils.convert(group.get());
        List<String> codes = stockGroupInfoDao.findAllCodeByGroupId(groupId);
        if (!codes.isEmpty()){
            List<StockModel> models = new ArrayList<>(codes.size());
            stockInfoDao.findAllById(codes).forEach(stockInfo -> models.add(StockConvertUtils.convert(stockInfo)));
            model.setStocks(models);
        }
        return model;
    }
}
