package com.dongle.stock.service.impl;

import com.dongle.stock.service.StockGrabService;
import com.dongle.stock.utils.PythonUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class StockGrabServiceImpl implements StockGrabService {

    @Override
    public boolean grabNewStock(String code, String from) {
        if (!StringUtils.hasLength(from)){
            from="";
        }
        String[] args = new String[]{"python",PythonUtils.STOCK_GRAB_PYTHON,"method=new","code="+code,"day="+from};
        return PythonUtils.execPython(args);
    }

    @Override
    public boolean grabByDay(String day) {
        if (!StringUtils.hasLength(day)) return PythonUtils.execPython(PythonUtils.STOCK_GRAB_PYTHON);
        String[] args = new String[]{"python",PythonUtils.STOCK_GRAB_PYTHON,"day="+day};
        return PythonUtils.execPython(args);
    }
}
