package com.iwanvi.nvwa.web.controller;

import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.model.FlowConsumerExample;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.FlowConsumerService;

@RestController
@RequestMapping("/flowconsumer")
public class FlowConsumerController {

	private static final Logger logger = LoggerFactory.getLogger(FlowConsumerController.class);
	
	@Autowired
	private FlowConsumerService flowConsumerService;
	
	@Autowired
	private FlowConsumerMapper flowConsumerMapper;
	

	@RequestMapping("/getadps")
	public String getAkAdposdbyTerminal(Integer id, Integer terminal){
		String result = StringUtils.EMPTY;
		try {
			Map<String, List<AdPosition>> akAdposList = flowConsumerService.getAkAdposdbyTerminal(id, terminal);
			logger.info("getAkAdposdbyTerminal success. ");
			result= JsonUtils.STATUS_OK(akAdposList);
		} catch (Exception e) {
			logger.error("getAkAdposdbyTerminal error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
		
	}
	
	@RequestMapping("/getpage")
	public String geFcPage(FlowConsumer fc, Integer currentPage, 
			Integer pageSize, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				Page<FlowConsumer> page = flowConsumerService.geFcPage(fc, currentPage, pageSize);
				logger.info("geFcPage success. ");
				result = JsonUtils.STATUS_OK(page);
			} else {
				logger.info("geFcPage failed, not login. ");
				result = JsonUtils.SIGN_ERROR();
			}
		} catch (Exception e) {
			logger.error("geFcPage error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@RequestMapping("/get")
	public String getFlowConsumerByID(Integer id) {
		String result = null;
		try {
			FlowConsumer flowConsumer = flowConsumerService.getFlowConsumerID(id);
			logger.info("getFlowConsumerByID success. ");
			result = JsonUtils.STATUS_OK(flowConsumer);
		} catch (Exception e) {
			logger.error("getFlowConsumerByID error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@RequestMapping("/getlist")
	public String getList() {
		String result = StringUtils.EMPTY;
		try {
			FlowConsumerExample example = new FlowConsumerExample();
			List<FlowConsumer> list = flowConsumerMapper.selectByExample(example);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getlist error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@RequestMapping("/add")
	public String insertFc(FlowConsumer fc, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (uid != null) {
				if (fc != null) {
					while (true) {
						String token = UUIDUtils.getUUID();
						if (!checkTokenExistes(token)) {
							fc.setToken(token);
							break;
						}
					}
					fc.setCreateUser(uid);
					flowConsumerService.insertFc(fc);
					result = JsonUtils.STATUS_OK();
				} else {
					logger.info("insertFc failed, fc is null. ");
					result = JsonUtils.PARAMETER_ERROR();
				}
			} else {
				logger.info("insertFc failed, not login. ");
				result = JsonUtils.SIGN_ERROR();
			}

		} catch (Exception e) {
			logger.error("insertFc error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/update")
	public String updateFC(FlowConsumer fc, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (fc != null) {
				flowConsumerService.updateFC(fc);
				result = JsonUtils.STATUS_OK();
			} else {
				logger.info("updateFC failed, fs is null. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("updateFC error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/del")
	public String delFcById(Integer id, @RequestAttribute Integer uid) {
		String result = null;
		try {
			if (id != null) {
				flowConsumerService.deFcById(id);
				result = JsonUtils.STATUS_OK();
			} else {
				logger.info("delFcById failed, fsid is null. ");
				result = JsonUtils.PARAMETER_ERROR();
			}
		} catch (Exception e) {
			logger.error("delFcById error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	private boolean checkTokenExistes(String token){
		boolean isExistes = false;
		if (StringUtils.isNotBlank(token)) {
			FlowConsumerExample example = new FlowConsumerExample();
			example.createCriteria().andTokenEqualTo(token);
			int count = (int)flowConsumerMapper.countByExample(example);
			if ( count > 0 ) {
				isExistes = true;
			}
		}
		return isExistes;
	}
	
	@GetMapping("get-sdkList")
	private String getSdkList() {
		String result = StringUtils.EMPTY;
		try {
			List<FlowConsumer> list = flowConsumerService.getSdkList();
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getSdkList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@GetMapping("get-sdkList-appmapped")
	private String getSdkList(@RequestParam(value = "cids", required = false)String cids) {
		String result = StringUtils.EMPTY;
		try {
			List<FlowConsumer> list = flowConsumerService.getSdkListToApp(cids);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getSdkList error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("get-flow-dx")
	private String getFlowDx(@RequestBody Map<String, Object> params) {
		String result = StringUtils.EMPTY;
		try {
			Map<String, Object> map = flowConsumerService.getFlowDx(params);
			result = JsonUtils.STATUS_OK(map);
		} catch (Exception e) {
			logger.error("getFlowDx error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("set-flow-dx")
	private String setFlowDx(@RequestBody Map<String, Object> map) {
		String result = StringUtils.EMPTY;
		try {
			flowConsumerService.setFlowDx(map);
			result = JsonUtils.STATUS_OK();
		} catch (Exception e) {
			logger.error("setFlowDx error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	@PostMapping("list-by-type")
	private String getListByType(@RequestBody FlowConsumer fc) {
		String result = StringUtils.EMPTY;
		try {
			List<FlowConsumer> list = flowConsumerService.getListByType(fc);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getListByType error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 广告位映射平台列表 需app现存在映射
	 * @param pid
	 * @return
	 */
	@GetMapping("list-by-mapped")
	private String getListByMapped(Integer pid) {
		String result = StringUtils.EMPTY;
		try {
			List<FlowConsumer> list = flowConsumerService.getListByMapped(pid);
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			logger.error("getListByMapped error. ", e);
			result = JsonUtils.STATUS_FAILED(Constants.RESPONSE_EXCEPTION, e.getMessage());
		}
		return result;
	}

	@NvwaRespBody
	@GetMapping("list-by-app")
	public List<FlowConsumer> listByApp(String appIds) {
		return flowConsumerService.listByApp(appIds);
	}
}
