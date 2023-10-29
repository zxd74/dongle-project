package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.Auths;

import java.util.List;

public interface AuthsService {
    List<Auths> selectForList(Auths auths);
}
