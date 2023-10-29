package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.DateLimit;

import java.util.List;

public interface DateLimitService {

    void add(DateLimit dateLimit);

    public void update(DateLimit dateLimit);

    List<DateLimit> selectForList(Integer id);
}
