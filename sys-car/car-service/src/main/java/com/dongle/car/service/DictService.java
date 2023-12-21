package com.dongle.car.service;

import java.util.List;

import com.dongle.car.dao.model.CarDict;
import com.dongle.car.dao.model.CarDictType;

public interface DictService {

    List<CarDictType> getAllDictType();
    List<CarDict> getAllDict();
    
    List<CarDictType> getDictTypeList(String type);
    List<CarDict> getDicts(String type);

    void addDictType(CarDictType dictType);
    void addDict(CarDict carDict);
    void addDictTypes(List<CarDictType> carDictTypes);
    void addDicts(List<CarDict> carDicts);

    void updateDictType(CarDictType dictType);
    void updateDict(CarDict dict);

    void disableDictType(CarDictType dictType);
    void disableDict(CarDict dict);
}
