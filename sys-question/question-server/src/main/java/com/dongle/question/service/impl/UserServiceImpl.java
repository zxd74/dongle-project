package com.dongle.question.service.impl;

import com.dongle.question.model.UserModel;
import com.dongle.question.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
