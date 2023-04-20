package com.dongle.question.core.service;

import com.dongle.question.core.model.UserModel;

import java.util.List;

public interface UserService {

    /**
     * 获取所有用户信息
     * @return 返回用户集合
     */
    List<UserModel> getAllQuestions();

    /**
     * 根据用户信息查询用户
     * @param user 用户信息
     * @return 用户集
     */
    List<UserModel>  findQuestions(UserModel user);
}
