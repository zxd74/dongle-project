package com.iwanvi.nvwa.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.UserGrandMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.Auths;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.UserGrandExample;
import com.iwanvi.nvwa.web.service.UserService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.iwanvi.nvwa.web.util.HttpClients;
import com.google.common.collect.Maps;

@RestController
@RequestMapping("index")
public class IndexController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private RedisDao redisdao;
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserGrandMapper userGrandMapper;

	/**
	 * 虚拟登录接口
	 * 
	 * @param request
	 * @param response
	 * @param cid
	 */
//	@RequestMapping("virtual")
//	public String virtualLogin(HttpServletRequest request, HttpServletResponse response, String token, Integer cid,
//			Integer sysType) {
//		String result;
//		if (cid != null && StringUtils.isNotBlank(token)) {
//			User user = userMapper.selectByPrimaryKey(cid);
//			if (user != null && Constants.USER_TYPE_CUST.equals(user.getType())) {
//				List<Auths> auths = HttpClients.authorize(null,null,1, token);
//				if (auths != null) {
//					// 判断当前登录人是否拥有被虚拟登录者的操作权限
//					boolean isDown = false;
//					Map<String, Object> loginMap = JsonUtils.parse2Map(redisdao.get(token));
//					User loginUser = (User) loginMap.get(Constants.LOGIN_USER);
//					Integer loginUid = loginUser.getId();
//					Integer type = loginUser.getType();
//					User virtualUser = userService.get(cid);
//					if (!Constants.USER_TYPE_ADMIN.equals(type)) {
//						List<Integer> createUsers = userService.selectUidsByCreateUser(loginUid, type);
//						if (Constants.USER_TYPE_CUST.equals(virtualUser.getType())) {
//							if (createUsers.contains(cid)) {
//								isDown = true;
//							} else {
//								UserGrandExample grandExample = new UserGrandExample();
//								grandExample.createCriteria().andUidEqualTo(loginUid).andAidEqualTo(cid)
//										.andTypeEqualTo(WebConstants.AGENT_OBJECT_TYPE_ADVER);
//								List<UserGrand> userGrands = userGrandMapper.selectByExample(grandExample);
//								if (userGrands.size() > 0) {
//									isDown = true;
//								}
//							}
//						}
//					}
//					if (isDown) {
//						Map<String, Object> respMap = Maps.newHashMap();
//						if (Constants.USER_TYPE_CUST.equals(virtualUser.getType())) {
//							loginMap.put(Constants.LOGIN_VIRTUAL_ADVER, auths);
//							redisdao.set(token, JsonUtils.TO_JSON(loginMap));
//						} else {
//							loginMap.put(Constants.LOGIN_VIRTUAL_AGENT, auths);
//							redisdao.set(token, JsonUtils.TO_JSON(loginMap));
//						}
//						respMap.put("id", cid);
//						respMap.put("auth", auths);
//						logger.info("index virtual success, loginUid:{}, cid:{}", loginUid, cid);
//						result = JsonUtils.STATUS_OK(respMap);
//					} else {
//						logger.error("index virtual error, no permission, loginUid: {}, cid: {}, cType:{}", loginUid,
//								cid, virtualUser.getType());
//						result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NO_PERSSION);
//					}
//				} else {
//					logger.error("index virtual error not login, token: {}, cid: {}", token, cid);
//					result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION_NOT_LOGIN);
//				}
//			} else {
//				logger.error("index virtual error cid type error, token: {}, cid: {}", token, cid, user.getType());
//				result = JsonUtils.STATUS_FAILED();
//			}
//		} else {
//			logger.error("index virtual error, token:{}, cid:{}, type: {}", cid, token);
//			result = JsonUtils.PARAMETER_ERROR();
//		}
//		return result;
//	}
}
