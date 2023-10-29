package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.TongfaMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionExample;
import com.iwanvi.nvwa.dao.model.Tongfa;
import com.iwanvi.nvwa.dao.model.TongfaExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.TongfaService;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TongfaServiceImpl implements TongfaService {
    @Autowired
    private TongfaMapper tongfaMapper;
    @Autowired
    private AdPositionMapper adPositionMapper;

    @Override
    @Transactional
    public void add(Tongfa tongfa) {
        if (tongfa != null) {
            tongfa.setStatus(Constants.STATE_VALID);
            tongfa.setUpdateTime(new Date());
            tongfaMapper.insert(tongfa);
        }
    }

    @Override
    @Transactional
    public void update(Tongfa tongfa) {
        if (tongfa != null) {
            tongfa.setUpdateTime(new Date());
            tongfaMapper.updateByPrimaryKeySelective(tongfa);
        }
    }

    @Override
    public Tongfa get(Integer id) {
        if (id != null) {
            Tongfa tongfa = tongfaMapper.selectByPrimaryKey(id);
            if (StringUtils.isNotBlank(tongfa.getTongfaIds())) {
                String[] ids = tongfa.getTongfaIds().split(Constants.SIGN_COMMA);
                List<String> list = Arrays.asList(ids);
                List<Integer> idsList = FluentIterable.from(list).transform(s -> {
                    return Integer.parseInt(s);
                }).toList();
                AdPositionExample example = new AdPositionExample();
                example.createCriteria().andIdIn(idsList);
                List<AdPosition> positionList = adPositionMapper.selectByExample(example);
                tongfa.setPositionList(positionList);
            }
            return tongfa;
        }
        return null;
    }

    @Override
    public Page<Tongfa> selectForPage(Tongfa tongfa, Integer cp, Integer ps) {
        Page<Tongfa> page;
        TongfaExample example = bindingExample(tongfa);
        int count = tongfaMapper.countByExample(example);
        List<Tongfa> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<Tongfa>(count, cp, ps);
        } else {
            page = new Page<Tongfa>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = tongfaMapper.selectByExample(example);
        page.bindData(list);
        return page;
    }

    private TongfaExample bindingExample(Tongfa tongfa) {
        TongfaExample example = new TongfaExample();
        if (tongfa != null) {
            TongfaExample.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(tongfa.getName())) {
                criteria.andNameLike("%" + tongfa.getName() + "%");
            }
            if (tongfa.getStatus() != null) {
                criteria.andStatusEqualTo(tongfa.getStatus());
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }
}
