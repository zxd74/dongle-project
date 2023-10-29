package com.iwanvi.nvwa.crontab.service;

import com.iwanvi.nvwa.dao.model.Area;
import com.iwanvi.nvwa.dao.model.Dictionary;

public interface AdDicService {

	Dictionary getDic(Integer agType);

	Area getArea(Integer id);

}
