package com.iwanvi.nvwa.web.service;

import java.util.List;
import java.util.Map;

public interface ProhibitedDateService {

	Map<String, List<Integer>> getProhibitedDateByMonth(String month);

	void setProhibitedDateByMonth(List<Map<String, List<Integer>>> list);

}
