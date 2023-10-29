package com.iwanvi.nvwa.auth.service;

import com.iwanvi.nvwa.auth.model.AuthsVo;
import com.iwanvi.nvwa.dao.model.Auths;
import com.iwanvi.nvwa.dao.model.support.Page;

import java.util.List;

public interface AuthsService {

    void add(Auths auths);

    void update(Auths auths);

    void delete(Integer id);

    Auths get(Integer id);

    List<AuthsVo> selectForList(Auths auths);

    Page<Auths> selectForPage(Auths auths, Integer cp, Integer ps);

    List<AuthsVo> getUserAuths(Integer userId, Integer platform);

    List<AuthsVo> getGroupAuths(Integer gid, Integer platform);
}
