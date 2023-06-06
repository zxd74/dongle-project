package com.dongle.stack.service.impl;

import com.dongle.stack.service.StockGrabService;
import com.dongle.stack.utils.PythonUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StockGrabServiceImpl implements StockGrabService {

    @Override
    public boolean grabNewStock(String code, String from) {
        return PythonUtils.execPython(PythonUtils.STOCK_GRAB_PYTHON);
    }

    @Override
    public boolean grabByDay(Date date) {
        return PythonUtils.execPython(PythonUtils.STOCK_GRAB_PYTHON);
    }
}
