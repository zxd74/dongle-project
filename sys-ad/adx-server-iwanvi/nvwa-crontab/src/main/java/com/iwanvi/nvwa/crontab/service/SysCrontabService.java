package com.iwanvi.nvwa.crontab.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.SysCrontab;


public interface SysCrontabService {

	SysCrontab getByIdAndStatus(Integer id, Integer statusUnhandle);

	void updateStatus2Handled(SysCrontab sysCrontab);

	void insertSysCrontab(Integer id, Integer objPut, Integer opUpdate, Integer createUser);

	List<SysCrontab> getAllByStatus(Integer statusUnhandle);

	void updateStatus2Handled(List<SysCrontab> sysCrontabs);

}
