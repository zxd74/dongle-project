package com.iwanvi.nvwa.auth.service;

import com.iwanvi.nvwa.dao.model.AuthGroup;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface AuthGroupService {

    void add(AuthGroup authGroup);

    void update(AuthGroup authGroup);

    AuthGroup get(Integer id);

    List<AuthGroup> selectForList(AuthGroup authGroup);

    Page<AuthGroup> selectForPage(AuthGroup authGroup, Integer cp, Integer ps);
}
