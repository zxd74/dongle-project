package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AppPkg;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.vo.AppPkgQuertReq;

public interface AppPkgService {

	void insert(AppPkg record);

	void delete(Integer id);

	void update(AppPkg record);

	List<AppPkg> selectByExample(String name);

	AppPkg loadById(Integer id);

	Page<AppPkg> selectPage(AppPkgQuertReq queryReq);

	AppPkg addByIdBlack(Integer id);

	boolean appName(String name);

	boolean pkgName(String name);

}
