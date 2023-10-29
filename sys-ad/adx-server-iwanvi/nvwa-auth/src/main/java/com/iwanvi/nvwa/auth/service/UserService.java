package com.iwanvi.nvwa.auth.service;

import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;
import java.util.Map;

public interface UserService {

    User get(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);

    List<User> selectForList(User user);

    Page<User> selectForPage(User user, Integer cp, Integer ps);

    Map<String,Object> login(String account, String pwd, Integer platform);
}
