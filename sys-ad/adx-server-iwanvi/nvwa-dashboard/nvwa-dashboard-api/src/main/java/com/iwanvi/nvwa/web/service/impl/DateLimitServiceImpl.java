package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.DateLimitMapper;
import com.iwanvi.nvwa.dao.model.DateLimit;
import com.iwanvi.nvwa.dao.model.DateLimitExample;
import com.iwanvi.nvwa.web.service.DateLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DateLimitServiceImpl implements DateLimitService {
    @Autowired
    private DateLimitMapper dateLimitMapper;

    @Override
    @Transactional
    public void add(DateLimit dateLimit) {
        dateLimit.setUpdateTime(new Date());
        dateLimit.setStatus(Constants.STATE_VALID);
        dateLimitMapper.insert(dateLimit);
    }

    @Override
    @Transactional
    public void update(DateLimit dateLimit) {
        dateLimit.setUpdateTime(new Date());
        dateLimitMapper.updateByPrimaryKeySelective(dateLimit);
    }
    @Override
    public List<DateLimit> selectForList(Integer id) {
        DateLimitExample example = new DateLimitExample();
        example.createCriteria().andPositionIdEqualTo(id);
        return dateLimitMapper.selectByExample(example);
    }
}
