package com.iwanvi.nvwa.web.service;

import java.util.List;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.support.Page;

public interface FlowSourceService {

	Page<FlowSource> getfsPage(FlowSource fs, Integer currentPage, Integer pageSize);

	void insertFS(FlowSource fs);

	void deFsById(int fsid);

	void updateFS(FlowSource fs);

	FlowSource getFlowSourceByUUID(String mediaUuid);

	boolean checkFlowManngerExist(UserGrand userGrand);
	
	List<FlowSource> getFSList(FlowSource fs);

	FlowSource getFlowSourceByID(Integer id);

	List<User> get(String name);


	List<FlowSource> getAllListByType(Integer type);



}
