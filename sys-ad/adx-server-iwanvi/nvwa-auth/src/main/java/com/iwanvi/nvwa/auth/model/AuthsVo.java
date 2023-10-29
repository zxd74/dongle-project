package com.iwanvi.nvwa.auth.model;

import com.iwanvi.nvwa.dao.model.Auths;

import java.util.List;

public class AuthsVo extends Auths {

    private List<AuthsVo> childs;

    public List<AuthsVo> getChilds() {
        return childs;
    }

    public void setChilds(List<AuthsVo> childs) {
        this.childs = childs;
    }
}
