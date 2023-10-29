package com.iwanvi.nvwa.web.service;

import java.util.List;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.SdkAdCarousel;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface SdkAdCarouselService {

	void setDefult(List<SdkAdCarousel> list);

	List<SdkAdCarousel> getDefult();

	void insertOrUpdate(SdkAdCarousel carousel);

	SdkAdCarousel get(Integer id);

	Page<SdkAdCarousel> getPage(SdkAdCarousel adCarousel);

	List<AdPosition> getAdpositionList(String name);

}
