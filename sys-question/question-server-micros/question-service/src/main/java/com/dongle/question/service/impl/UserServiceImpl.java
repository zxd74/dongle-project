package com.dongle.question.service.impl;

import com.dongle.question.core.service.UserService;
import com.dongle.question.core.model.UserModel;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService(version = "1.0.0",group = "manage")
public class UserServiceImpl implements UserService {
    @Override
    public List<UserModel> getAllQuestions() {
        return null;
    }

    @Override
    public List<UserModel> findQuestions(UserModel user) {
        // TODO
        return null;
    }
}
