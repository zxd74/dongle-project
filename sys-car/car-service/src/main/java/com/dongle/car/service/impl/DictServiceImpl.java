package com.dongle.car.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.dongle.car.config.ann.DongleModify;
import com.dongle.car.config.ann.DongleRead;
import com.dongle.car.dao.mapper.CarDictMapper;
import com.dongle.car.dao.mapper.CarDictTypeMapper;
import com.dongle.car.dao.model.CarDict;
import com.dongle.car.dao.model.CarDictExample;
import com.dongle.car.dao.model.CarDictType;
import com.dongle.car.dao.model.CarDictTypeExample;
import com.dongle.car.service.DictService;
import com.dongle.commons.utils.DongleException;

import lombok.Data;

@Data
public class DictServiceImpl implements DictService{
    
    @Autowired
    private CarDictTypeMapper dictTypeMapper;
    @Autowired
    private CarDictMapper dictMapper;
    
    @Override
    @DongleRead
    public List<CarDictType> getAllDictType() {
        return dictTypeMapper.selectByExample(new CarDictTypeExample());
    }

    @Override
    @DongleRead
    public List<CarDict> getAllDict() {
        return dictMapper.selectByExample(new CarDictExample());
    }

    @Override
    @DongleRead
    public List<CarDictType> getDictTypeList(String type) {
        CarDictTypeExample example = new CarDictTypeExample();
        example.createCriteria().andTypeEqualTo(type);
        return dictTypeMapper.selectByExample(example);
    }

    @Override
    @DongleRead
    public List<CarDict> getDicts(String type) {
        CarDictExample example = new CarDictExample();
        example.createCriteria().andTypeEqualTo(type);
        return dictMapper.selectByExample(example);
    }

    @Override
    @DongleModify
    public void addDictType(CarDictType dictType) {
        CarDictTypeExample example = new CarDictTypeExample();
        example.createCriteria().andTypeEqualTo(dictType.getType());
        example.or(example.createCriteria().andNameEqualTo(dictType.getName()));
        if (dictTypeMapper.selectByExample(example).size() > 0) {
            throw new DongleException("字典类型或字典类型名称已存在，操作拒绝。");
        }
        dictTypeMapper.insert(dictType);
    }

    @Override
    @DongleModify
    public void addDict(CarDict carDict) {
        CarDictExample example = new CarDictExample();
        example.createCriteria().andTypeEqualTo(carDict.getType()).andNameEqualTo(carDict.getName());
        example.or(example.createCriteria().andTypeEqualTo(carDict.getType()).andValueEqualTo(carDict.getValue()));
        if (dictMapper.selectByExample(example).size() > 0) {
            throw new DongleException("相同字典类型中存在相同字典名称或字典值，操作拒绝。");
        }
        dictMapper.insert(carDict);
    }

    @Override
    @DongleModify
    public void addDictTypes(List<CarDictType> carDictTypes) {
        List<String> types = carDictTypes.stream().map(CarDictType::getType).collect(Collectors.toList());
        List<String> names = carDictTypes.stream().map(CarDictType::getName).collect(Collectors.toList());
        CarDictTypeExample example = new CarDictTypeExample();
        example.createCriteria().andTypeIn(types);
        example.or(example.createCriteria().andNameIn(names));
        if (dictTypeMapper.selectByExample(example).size() > 0) {
            throw new DongleException("存在相同字典类型或字典类型名称，操作拒绝。");
        }
        dictTypeMapper.batchInsert(carDictTypes);
    }

    @Override
    @DongleModify
    public void addDicts(List<CarDict> carDicts) {
        Map<String, List<CarDict>> map = carDicts.stream().collect(Collectors.groupingBy(CarDict::getType));
        for (Map.Entry<String, List<CarDict>> entry : map.entrySet()) {
            String type = entry.getKey();
            List<CarDict> list = entry.getValue();
            List<String> names = list.stream().map(CarDict::getName).collect(Collectors.toList());
            List<String> values = list.stream().map(CarDict::getValue).collect(Collectors.toList());
            CarDictExample example = new CarDictExample();
            // todo AND ( OR )
            example.createCriteria().andTypeEqualTo(type).andNameIn(names);
            example.or(example.createCriteria().andTypeEqualTo(type).andValueIn(values));
            if (dictMapper.selectByExample(example).size() > 0) 
                throw new DongleException("存在相同字典类型中相同的字典值或字典名称，操作拒绝。");
        }
        
        
        dictMapper.batchInsert(carDicts);
        
    }

    @Override
    @DongleModify
    public void updateDictType(CarDictType dictType) {
        if (dictType.getId() == null) {
            throw new DongleException("更新操作，字典类型ID不能为空。");
        }
        dictTypeMapper.updateByPrimaryKey(dictType);
    }

    @Override
    @DongleModify
    public void updateDict(CarDict dict) {
        if (dict.getId() == null) {
            throw new DongleException("更新操作，字典类型ID不能为空。");
        }
        dictMapper.updateByPrimaryKey(dict);
    }

    @Override
    public void disableDictType(CarDictType dictType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableDictType'");
    }

    @Override
    public void disableDict(CarDict dict) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'disableDict'");
    }
    
}
