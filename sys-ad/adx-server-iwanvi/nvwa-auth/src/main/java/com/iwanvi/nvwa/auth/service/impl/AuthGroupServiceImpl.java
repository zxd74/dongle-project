package com.iwanvi.nvwa.auth.service.impl;

import com.iwanvi.nvwa.auth.service.AuthGroupService;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AuthGroupMapper;
import com.iwanvi.nvwa.dao.model.AuthGroup;
import com.iwanvi.nvwa.dao.model.AuthGroupExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthGroupServiceImpl implements AuthGroupService {
    @Autowired
    private AuthGroupMapper authGroupMapper;


    @Transactional
    @Override
    public void add(AuthGroup authGroup) {
        if (authGroup != null && StringUtils.isNotBlank(authGroup.getName())) {
            authGroup.setStatus(Constants.STATE_VALID);
            authGroupMapper.insertSelective(authGroup);
        }
    }

    @Transactional
    @Override
    public void update(AuthGroup authGroup) {
        if (authGroup != null && authGroup.getId() != null) {
            authGroup.setName(null);
            authGroupMapper.updateByPrimaryKeySelective(authGroup);
        }
    }

    @Override
    public AuthGroup get(Integer id) {
        if (id != null) {
            return authGroupMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public List<AuthGroup> selectForList(AuthGroup authGroup) {
        AuthGroupExample example = bindingExample(authGroup);
        return authGroupMapper.selectByExample(example);
    }

    @Override
    public Page<AuthGroup> selectForPage(AuthGroup authGroup, Integer cp, Integer ps) {
        Page<AuthGroup> page;
        AuthGroupExample example = bindingExample(authGroup);
        int count = authGroupMapper.countByExample(example);
        List<AuthGroup> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<AuthGroup>(count, cp, ps);
        } else {
            page = new Page<AuthGroup>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = authGroupMapper.selectByExample(example);
        page.bindData(list);
        return page;
    }

    private AuthGroupExample bindingExample(AuthGroup authGroup) {
        AuthGroupExample example = new AuthGroupExample();
        if (authGroup != null) {
            AuthGroupExample.Criteria criteria = example.createCriteria();
            if (authGroup.getStatus() != null) {
                criteria.andStatusEqualTo(authGroup.getStatus());
            }
            if (authGroup.getPlatform() != null) {
                criteria.andPlatformEqualTo(authGroup.getPlatform());
            }
            if (StringUtils.isNotBlank(authGroup.getName())) {
                criteria.andNameLike("%" + authGroup.getName() + "%");
            }
        }
        return example;
    }
}
