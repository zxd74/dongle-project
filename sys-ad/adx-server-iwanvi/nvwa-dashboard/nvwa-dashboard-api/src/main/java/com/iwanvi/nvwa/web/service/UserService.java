package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface UserService {

    User get(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);

    List<User> selectForList(User user);

    Page<User> selectForPage(User user, Integer cp, Integer ps);
    
    List<Integer> selectUidsByAgentId(Integer uid);

    boolean isAdmin(Integer id);

    List<Integer> getAgentAdminIdByAid(Integer id);

    void resetPassword(String oldPwd, String newPwd, Integer uid);

    Integer getAgType(Integer uid);
}
