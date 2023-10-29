package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.FlowSource;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.FlowSourceService;
import com.iwanvi.nvwa.web.service.UserGrandService;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 
 * Created by Taurus on 2018/9/25.
 *
 */
@RestController
@RequestMapping("/flowsource")
public class FlowSourceController {

	private static final Logger logger = LoggerFactory.getLogger(FlowSourceController.class);

	@Autowired
	private FlowSourceService flowSourceService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserGrandService userGrandService;

	@RequestMapping("/getall")
	public String getFSPage(FlowSource fs, Integer currentPage, Integer pageSize, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				if (fs == null) {
					fs = new FlowSource();
				}
				if (!userService.isAdmin(uid)) {
					fs.setCreateUser(uid);
				}
				Page<FlowSource> page = flowSourceService.getfsPage(fs, currentPage, pageSize);
				if (page == null) {
					logger.info("getFSList failed, page is null. ");
					result = JsonUtils.DATA_NOT_FIND();
				} else {
					logger.info("getFSList success. ");
					result = JsonUtils.STATUS_OK(page);
				}
			} else {
				logger.info("getFSPage failed, not login. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("getFSList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getlist")
	public String getFSList(FlowSource fs, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				if (fs == null) {
					fs = new FlowSource();
				}
				if (!userService.isAdmin(uid)) {
					fs.setCreateUser(uid);
				}
				List<FlowSource> fsList = flowSourceService.getFSList(fs);
				if (fsList == null) {
					logger.info("getFSList failed, fsList is null. ");
					result = JsonUtils.DATA_NOT_FIND();
				} else {
					logger.info("getFSList success. ");
					result = JsonUtils.STATUS_OK(fsList);
				}
			} else {
				logger.info("getFSList failed, not login. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("getFSList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getallbytype")
	public String getAllList(Integer type, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				List<FlowSource> fsList = flowSourceService.getAllListByType(type);
				if (fsList == null) {
					logger.info("getAllList failed, fsList is null. ");
					result = JsonUtils.DATA_NOT_FIND();
				} else {
					logger.info("getAllList success. ");
					result = JsonUtils.STATUS_OK(fsList);
				}
			} else {
				logger.info("getAllList failed, not login. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("getAllList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping("/get")
	public String getFlowSourceByID(Integer id) {
		String result = null;
		try {
			FlowSource flowSource = flowSourceService.getFlowSourceByID(id);
			logger.info("getFlowSourceByUUID success. ");
			result = JsonUtils.STATUS_OK(flowSource);
		} catch (Exception e) {
			logger.error("getFlowSourceByUUID error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/add")
	public String insertFS(FlowSource fs, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				if (fs != null) {
					fs.setCreateUser(uid);
					flowSourceService.insertFS(fs);
					userGrandService.insert(uid, fs.getId(), uid, WebConstants.AGENT_OBJECT_TYPE_FLOW);
					logger.info("insertFS success. ");
					result = JsonUtils.STATUS_OK();
				} else {
					logger.info("insertFS failed, fs is null. ");
					result = JsonUtils.PARAMETER_ERROR();
				}
			} else {
				logger.info("insertFS failed, not login. ");
				result = JsonUtils.PARAMETER_ERROR();
			}

		} catch (Exception e) {
			logger.error("insertFS error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/update")
	public String updateFS(FlowSource fs) {
		String result = null;
		try {
			if (fs != null) {
				flowSourceService.updateFS(fs);
				result = JsonUtils.STATUS_OK();
			} else {
				logger.info("updateFS failed, fs is null. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("updateFS error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/del")
	public String delFsById(Integer id) {
		String result = null;
		try {
			if (id != null) {
				flowSourceService.deFsById(id);
				result = JsonUtils.STATUS_OK();
			} else {
				logger.info("delFsById failed, fsid is null. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("delFsById error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getgrand")
	public String getGrand(UserGrand userGrand) {// {"aid":854}
		String result = null;
		try {
			if (userGrand == null) {
				result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_PARAM_INVALID);
			} else {
				userGrand.setType(WebConstants.AGENT_OBJECT_TYPE_FLOW);
				List<UserGrand> fms = userGrandService.selectForList(userGrand);
				logger.info("getFlowManngersByName success. ");
				result = JsonUtils.STATUS_OK(fms);
			}
		} catch (Exception e) {
			logger.error("getFlowManngersByName error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/grand")
	public String addFlowMannger(UserGrand userGrand, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (userGrand != null) {
				boolean checkFlowManngerExist = flowSourceService.checkFlowManngerExist(userGrand);
				if (checkFlowManngerExist) {
					throw new ServiceException("该用户已存在");
				} else {
					userGrand.setCreateTime(new Date());
					userGrand.setCreateUser(uid);
					userGrand.setType(WebConstants.AGENT_OBJECT_TYPE_FLOW);
					userGrandService.addForFs(userGrand);
					logger.info("addFlowMannger success. ");
					result = JsonUtils.STATUS_OK();
				}
			} else {
				logger.info("addFlowMannger failed, userGrand is null. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("addFlowMannger error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/movegrand")
	public String delFlowMannger(UserGrand userGrand,  @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (userGrand != null) {
				if (userGrand.getUid().equals(WebConstants.USER_ADMIN)) {
					throw new ServiceException("无法清除admin权限");
				}
				if (userGrand.getUid().equals(uid)) {
					throw new ServiceException("无法清除自己的权限");
				}
				userGrand.setType(WebConstants.AGENT_OBJECT_TYPE_FLOW);
				userGrandService.delete(userGrand);
				logger.info("delFlowMannger success. ");
				result = JsonUtils.STATUS_OK();
			} else {
				logger.info("delFlowMannger failed, id is null. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("delFlowMannger error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/getusers")
	public String getFlowManngerList(String uname) {
		String result = StringUtils.EMPTY;
		try {
			List<User> list = flowSourceService.get(uname);
			logger.info("delFlowMannger success. ");
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getFlowManngerList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

}
