package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.service.UserService;
import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@Autowired
	private RedisDao redisDao;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private UserService userService;

	/**
	 * 参数map转换 @param parameterMap @return Map<String,String> @throws
	 */
	protected Map<String, Object> convertMap(Map<String, String[]> parameterMap) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (parameterMap != null) {
			for (String key : parameterMap.keySet()) {
				String[] strs = parameterMap.get(key);
				if (strs != null && strs.length > 0) {
					if (strs.length > 1) {
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < strs.length; i++) {
							sb.append(strs[i]);
							if (i != strs.length - 1) {
								sb.append(Constants.SIGN_COMMA);
							}
						}
						map.put(key, sb.toString());
					} else {
						map.put(key, strs[0]);
					}
				}
			}
		}
		return map;
	}

	protected Map<String, Object> getParamMap(HttpServletRequest request) throws IOException {
		// 支持json请求
		if (request.getContentType() != null && request.getContentType().startsWith(MediaType.APPLICATION_JSON_VALUE)) {
			return JsonUtils.parse2Map(request.getInputStream());
		} else {
			return convertMap(request.getParameterMap());
		}
	}

	public static void responseJson(HttpServletRequest request, HttpServletResponse response, String json) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String callback = request.getParameter("callback");
		if (StringUtils.isNotBlank(callback)) {
			json = callback + "(" + json + ")";
		}
		try {
			response.getWriter().print(json);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("response json error!", e);
		}
	}

	protected Map<String, Object> getUserMap(String token) {
		Map<String, Object> userMap = JsonUtils.parse2Map(redisDao.get(token));
		return userMap;
	}

	protected Integer getUserId(HttpServletRequest request) {
		return (Integer) request.getAttribute("uid");
	}
	
	protected Integer getUserType(HttpServletRequest request) {
		return (Integer) request.getAttribute("type");
	}
	//获取虚拟登录用户的id
	protected Integer getVirtualAdverId(HttpServletRequest request) {
		return (Integer) request.getAttribute("adverId");
	}
	//获取虚拟登录用户的type
	protected Integer getVirtualAdverType(HttpServletRequest request) {
		return (Integer) request.getAttribute("adverType");
	}

	//获取虚拟登录用户的信息
	protected User getVirtualAdver(HttpServletRequest request, Integer adverId) {
		String token = (String) request.getAttribute("token");
		Map<String, Object> userMap = getUserMap(token);
		if (userMap.containsKey(Constants.LOGIN_VIRTUAL_ADVER)
				|| ((User) userMap.get(Constants.LOGIN_VIRTUAL_ADVER)).getId().equals(adverId)) {
			return (User) userMap.get(Constants.LOGIN_VIRTUAL_ADVER);
		} else {
			return null;
		}
	}
	
	//获取当前登录人所能查看到得数据的创建人
	protected void getCreatUserByRequest(HttpServletRequest request, Map<String, Object> paramMap){
		Integer uid = getUserId(request);
		Integer type = getUserType(request);
		if(Constants.USER_TYPE_CUST.equals(type)) {
			User user = userService.get(uid);
			Integer adverId = null;
			adverId = user.getCompany();
			paramMap.put("adverId", adverId);
		}else if (!Constants.USER_TYPE_ADMIN.equals(type)) {
			
//			List<Integer> uids = userService.selectUidsByCreateUser(uid, type);
//			uids = getAuthUsersByCreateUser(uids);
//			if (uids != null && uids.size() > 0) {
//				if (uids.size() > 1) {
//					paramMap.put("createUsers", uids);
//				}else {
//					paramMap.put("createUser", uids.get(0));
//				}
//			}
			List<Integer> companyIds = Lists.newArrayList(); 
			companyIds = companyService.getCompanyIdsByWithUid(uid);
			if (companyIds.isEmpty()) {
				throw new ServiceException("无相关数据");
			}
			paramMap.put("advers", companyIds);
		}
	}
	
}
