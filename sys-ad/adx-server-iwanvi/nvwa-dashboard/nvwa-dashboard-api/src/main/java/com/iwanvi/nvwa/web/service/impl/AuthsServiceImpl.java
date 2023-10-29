package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.AuthsMapper;
import com.iwanvi.nvwa.dao.model.Auths;
import com.iwanvi.nvwa.dao.model.AuthsExample;
import com.iwanvi.nvwa.web.service.AuthsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthsServiceImpl implements AuthsService {

    @Autowired
    private AuthsMapper authsMapper;

    @Override
    public List<Auths> selectForList(Auths auths) {
        AuthsExample example = bindingExample(auths);
        return authsMapper.selectByExample(example);
    }

    private AuthsExample bindingExample(Auths auths) {
        AuthsExample example = new AuthsExample();
        AuthsExample.Criteria criteria = example.createCriteria();
        if (auths != null) {
            if (auths.getStatus() != null) {
                criteria.andStatusEqualTo(auths.getStatus());
            } else {
                criteria.andStatusEqualTo(Constants.STATE_VALID);
            }
            if (auths.getPlatform() != null) {
                criteria.andPlatformEqualTo(auths.getPlatform());
            }
        }
        return example;
    }
}
