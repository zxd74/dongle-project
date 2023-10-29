package com.iwanvi.nvwa.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.NvwaUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AppsMapper;
import com.iwanvi.nvwa.dao.FlowConsumerMapper;
import com.iwanvi.nvwa.dao.SdkAllotScheduleMapper;
import com.iwanvi.nvwa.dao.SdkAllotmentMapper;
import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.Apps;
import com.iwanvi.nvwa.dao.model.FlowConsumer;
import com.iwanvi.nvwa.dao.model.SdkAllotSchedule;
import com.iwanvi.nvwa.dao.model.SdkAllotScheduleExample;
import com.iwanvi.nvwa.dao.model.SdkAllotment;
import com.iwanvi.nvwa.dao.model.SdkAllotmentExample;
import com.iwanvi.nvwa.dao.model.SdkAllotmentExample.Criteria;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.SdkAllotmentService;
import com.iwanvi.nvwa.web.task.SdkAllotmentTask;
import com.iwanvi.nvwa.web.vo.ScheduleForm;
import com.iwanvi.nvwa.web.vo.SdkAllotmentForm;

@Service
public class SdkAllotmentServiceImpl implements SdkAllotmentService {
	
	private static final Logger LOG = LoggerFactory.getLogger("SdkAllotmentService");
	
	@Autowired
	private AppsMapper appsMapper;
	
	@Autowired
	private AdPositionMapper adPositionMapper;
	
	@Autowired
	private FlowConsumerMapper flowConsumerMapper;
	
	@Autowired
	private SdkAllotmentMapper sdkAllotmentMapper;
	
	@Autowired
	private SdkAllotScheduleMapper sdkAllotScheduleMapper;
	
	@Autowired
	private SdkAllotmentTask sdkAllotmentTask;

	@Override
	public Page<SdkAllotmentForm> listForPage(String name, 
			Integer platformId, Integer type, Integer status, Integer pageNum, Integer pageSize) {
		Page<SdkAllotmentForm> page = null;
		try {
			SdkAllotmentExample example = new SdkAllotmentExample();
			
			Criteria criteria = example.createCriteria();
			criteria.andStatusNotEqualTo(-1);
			if (StringUtils.isNotBlank(name)) {
				criteria.andAllotmentNameLike("%" + name + "%");
			}
			if (platformId != null) {
				criteria.andPlatformIdEqualTo(platformId);
			}
			if (type != null) {
				criteria.andTypeEqualTo(type);
			}
			if (status != null) {
				criteria.andStatusEqualTo(status);
			}
			
			Long count = sdkAllotmentMapper.countByExample(criteria.example());
			if (count == null || count <= 0) {
				return page;
			}
			page = new Page<SdkAllotmentForm>(count.intValue(), pageNum, pageSize);
			example.setOrderByClause("id desc");
			example.setStart(page.getStartPosition());
			example.setLimit(page.getPageSize());
			
			List<SdkAllotment> sdkAllotmentList = sdkAllotmentMapper.selectByExample(example);
			if (sdkAllotmentList == null || sdkAllotmentList.isEmpty()) {
				return page;
			}
			List<SdkAllotmentForm> sdkAllotmentFormList = new ArrayList<>();
			SdkAllotmentForm form = null;
			SdkAllotScheduleExample tempExample = null;
			List<SdkAllotSchedule> sdkAllotScheduleList = null;
			List<ScheduleForm> scheduleForms = null;
			Apps apps = null;
			FlowConsumer flowConsumer = null;
			for(SdkAllotment tempSdkAllotment : sdkAllotmentList) {
				form = new SdkAllotmentForm();
				BeanUtils.copyProperties(tempSdkAllotment, form);
				
				apps = appsMapper.selectByPrimaryKey(tempSdkAllotment.getAppId());
				flowConsumer = flowConsumerMapper.selectByPrimaryKey(tempSdkAllotment.getPlatformId());
				
				form.setAppName(apps == null ? "" : apps.getAppName());
				form.setPlaName(flowConsumer == null ? "" : flowConsumer.getConsumerName());
				
				tempExample = new SdkAllotScheduleExample();
				tempExample.createCriteria().andAllotmentIdEqualTo(tempSdkAllotment.getId());
				sdkAllotScheduleList = sdkAllotScheduleMapper.selectByExample(tempExample);
				
				AdPosition adPosition = null;
				
				if (sdkAllotScheduleList != null && !sdkAllotScheduleList.isEmpty()) {
					scheduleForms = new ArrayList<ScheduleForm>();
					Set<Integer> posIdSet = new HashSet<Integer>();
					for (SdkAllotSchedule sdkAllotSchedule : sdkAllotScheduleList) {
						
						ScheduleForm scheduleForm = new ScheduleForm();
						BeanUtils.copyProperties(sdkAllotSchedule, scheduleForm);

						adPosition = adPositionMapper.selectByPrimaryKey(scheduleForm.getAdPosId());
						scheduleForm.setPosName(adPosition == null ? "" : adPosition.getName());
						
						posIdSet.add(sdkAllotSchedule.getAdPosId());
					}
					form.setPosIds(org.apache.commons.lang3.StringUtils.join(posIdSet.toArray(), ","));
					form.setScheduleList(scheduleForms);
				}
				sdkAllotmentFormList.add(form);
			}
			page.setData(sdkAllotmentFormList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	@Transactional
	public String status(Integer id, Integer status) {
		String result = null;
		try {
			SdkAllotment sdkAllotment = new SdkAllotment();
			sdkAllotment.setId(id);
			sdkAllotment.setStatus(status);
			sdkAllotment.setUpdateTime(new Date());
			int r = sdkAllotmentMapper.updateByPrimaryKeySelective(sdkAllotment);
			if (r > 0) {
				if (status == 0 || status == -1) { // 无效
					sdkAllotmentTask.removeRedis(id);
				}
				result = JsonUtils.STATUS_OK();
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.error("", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public String updateFixed(Map<String, Object> paramMap) {
		String result = null;
		try {
			SdkAllotment sdkAllotment = new SdkAllotment();
			
			sdkAllotment.setId(NvwaUtils.obj2int(paramMap.get("id")));
			sdkAllotment.setType(2);
			sdkAllotment.setChannelId(NvwaUtils.obj2Empty(paramMap.get("channelId")));
			sdkAllotment.setAppVersion(NvwaUtils.obj2Empty(paramMap.get("appVersion")));
			sdkAllotment.setAllotmentName(NvwaUtils.obj2Empty(paramMap.get("allotmentName")));
			sdkAllotment.setAppId(NvwaUtils.obj2int(paramMap.get("appId")));
			sdkAllotment.setPlatformId(NvwaUtils.obj2int(paramMap.get("platformId")));
			sdkAllotment.setFrequency(NvwaUtils.obj2int(paramMap.get("frequency")));
			sdkAllotment.setChargeType(NvwaUtils.obj2int(paramMap.get("chargeType")));
			sdkAllotment.setCreateTime(new Date());
			sdkAllotment.setUpdateTime(new Date());
			
			int r1 = sdkAllotmentMapper.updateByPrimaryKeySelective(sdkAllotment);
			
			List<Map<String, Object>> schedules = (List<Map<String, Object>>)paramMap.get("scheduleList");
			int r2 = 0;
			if (schedules != null && !schedules.isEmpty()) {

				// 删除sdkAllotSchedule数据
				SdkAllotScheduleExample example = new SdkAllotScheduleExample();
				example.createCriteria().andAllotmentIdEqualTo(sdkAllotment.getId());
				int delResult = sdkAllotScheduleMapper.deleteByExample(example);
				
				LOG.info("deleteSdkAllotScehdule. delResult: {}", delResult);
				
				List<SdkAllotSchedule> sdkAllotSchedules = new ArrayList<SdkAllotSchedule>();
				for (Map<String, Object> scheduleForm : schedules) {
					List<Map<String, Object>> limitList = (List<Map<String, Object>>)scheduleForm.get("limits");
					for (Map<String, Object> limitMap : limitList) {
						String date = NvwaUtils.obj2Empty(limitMap.get("date"));
						SdkAllotSchedule sdkAllotSchedule = new SdkAllotSchedule();
						sdkAllotSchedule.setStartDay(NvwaUtils.obj2int(date.split("-")[0]));
						sdkAllotSchedule.setEndDay(NvwaUtils.obj2int(date.split("-")[1]));
						sdkAllotSchedule.setExposureLimit(NvwaUtils.obj2int(limitMap.get("limit")));
						sdkAllotSchedule.setScheduleFixed(NvwaUtils.obj2Empty(limitMap.get("times")));
						sdkAllotSchedule.setAllotmentId(sdkAllotment.getId());
						sdkAllotSchedule.setAdPosId(NvwaUtils.obj2int(scheduleForm.get("id")));
						sdkAllotSchedule.setIsSmooth(NvwaUtils.obj2int(scheduleForm.get("deliveryMode")));
						sdkAllotSchedule.setPriority(NvwaUtils.obj2int(scheduleForm.get("priority")));
						
						sdkAllotSchedule.setStraChapter(NvwaUtils.obj2int(scheduleForm.get("straChapter")));
						sdkAllotSchedule.setStraPage(NvwaUtils.obj2int(scheduleForm.get("straPage")));
						sdkAllotSchedule.setStraTime(NvwaUtils.obj2int(scheduleForm.get("straTime")));

						sdkAllotSchedules.add(sdkAllotSchedule);
					}
				}
				r2 = sdkAllotScheduleMapper.batchInsert(sdkAllotSchedules);
			}
			LOG.info("sdkAllotment update. updateSdkAllotment: r1: {}, r2: {}", r1, r2);
			if (r1 + r2 > 0) {
				return JsonUtils.STATUS_OK();
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.error("", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}

	@Override
	@Transactional
	public String update(SdkAllotmentForm sdkAllotmentForm) {
		String result = null;
		try {
			SdkAllotment sdkAllotment = new SdkAllotment();
			BeanUtils.copyProperties(sdkAllotmentForm, sdkAllotment);
			
			List<ScheduleForm> schedules = sdkAllotmentForm.getScheduleList();
			
			if (CollectionUtils.isNotEmpty(schedules)) {
				SdkAllotSchedule sdkAllotSchedule = null;
				SdkAllotScheduleExample example = null;
				for (ScheduleForm scheduleForm : schedules) {
					
					sdkAllotSchedule = new SdkAllotSchedule();
					BeanUtils.copyProperties(scheduleForm, sdkAllotSchedule);
					
					sdkAllotSchedule.setSchedulePeriod(Integer.parseInt(scheduleForm.getPeriod().replaceAll("2", "1"), 2));
					sdkAllotSchedule.setAllotmentId(sdkAllotment.getId());
					sdkAllotSchedule.setStraChapter(scheduleForm.getStraChapter());
					sdkAllotSchedule.setStraPage(scheduleForm.getStraPage());
					sdkAllotSchedule.setStraTime(scheduleForm.getStraTime());
					
					if(sdkAllotmentForm.getType() == 1) { //包段
						example = new SdkAllotScheduleExample();
						example.createCriteria().andAllotmentIdEqualTo(sdkAllotmentForm.getId())
							.andMonthPeriodEqualTo(scheduleForm.getMonthPeriod())
							.andAdPosIdEqualTo(scheduleForm.getAdPosId());
						List<SdkAllotSchedule> temp = sdkAllotScheduleMapper.selectByExample(example);
						
						if (CollectionUtils.isEmpty(temp)) {
							sdkAllotSchedule.setId(null);
							sdkAllotScheduleMapper.insert(sdkAllotSchedule);
						} else {
							sdkAllotScheduleMapper.updateByExampleSelective(sdkAllotSchedule, example);
						}
						
						// 更新stra
						sdkAllotSchedule = new SdkAllotSchedule();
						sdkAllotSchedule.setStraChapter(scheduleForm.getStraChapter());
						sdkAllotSchedule.setStraPage(scheduleForm.getStraPage());
						sdkAllotSchedule.setStraTime(scheduleForm.getStraTime());
						
						example = new SdkAllotScheduleExample();
						example.createCriteria().andAllotmentIdEqualTo(sdkAllotmentForm.getId())
							.andAdPosIdEqualTo(scheduleForm.getAdPosId());
						sdkAllotScheduleMapper.updateByExampleSelective(sdkAllotSchedule, example);
					}
				}
			}
			
			int r1 = sdkAllotmentMapper.updateByPrimaryKeySelective(sdkAllotment);
			LOG.info("sdkAllotment update. updateByPrimaryKeySelective: {}", r1);
			if (r1 > 0) {
				return JsonUtils.STATUS_OK();
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.error("", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String add(SdkAllotment sdkAllotment, List<ScheduleForm> schedules) {
		String result = null;
		try {
			LOG.info("add sdkAllotment: {}", JsonUtils.TO_JSON(sdkAllotment));
			
			sdkAllotment.setCreateTime(new Date());
			sdkAllotment.setUpdateTime(new Date());
			sdkAllotment.setStatus(Constants.STATE_VALID);
			int r1 = sdkAllotmentMapper.insertSelective(sdkAllotment);
			int r2 = 0;
			if (CollectionUtils.isNotEmpty(schedules)) {
				List<SdkAllotSchedule> sdkAllotSchedules = new ArrayList<SdkAllotSchedule>();
				for (ScheduleForm scheduleForm : schedules) {
					SdkAllotSchedule sdkAllotSchedule = new SdkAllotSchedule();
					BeanUtils.copyProperties(scheduleForm, sdkAllotSchedule);
					sdkAllotSchedule.setAllotmentId(sdkAllotment.getId());
					
					sdkAllotSchedule.setSchedulePeriod(Integer.parseInt(scheduleForm.getPeriod().replaceAll("2", "1"), 2));
					sdkAllotSchedules.add(sdkAllotSchedule);
				}
				
				r2 = sdkAllotScheduleMapper.batchInsert(sdkAllotSchedules);
			}
			
			LOG.info("sdkAllotment add. insertSdkAllotment: {}, insertSdkAllotSchedule: {}", r1, r2);
			if (r1 + r1 > 0) {
				return JsonUtils.STATUS_OK();
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.error("", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String addFixed(Map<String, Object> paramMap, Integer uid) {
		String result = null;
		try {
			LOG.info("add sdkAllotment: {}", JsonUtils.TO_JSON(paramMap));
			
			SdkAllotment sdkAllotment = new SdkAllotment();
			
			sdkAllotment.setType(2);
			sdkAllotment.setChannelId(NvwaUtils.obj2Empty(paramMap.get("channelId")));
			sdkAllotment.setAppVersion(NvwaUtils.obj2Empty(paramMap.get("appVersion")));
			sdkAllotment.setAllotmentName(NvwaUtils.obj2Empty(paramMap.get("allotmentName")));
			sdkAllotment.setAppId(NvwaUtils.obj2int(paramMap.get("appId")));
			sdkAllotment.setPlatformId(NvwaUtils.obj2int(paramMap.get("platformId")));
			sdkAllotment.setFrequency(NvwaUtils.obj2int(paramMap.get("frequency")));
			sdkAllotment.setChargeType(NvwaUtils.obj2int(paramMap.get("chargeType")));
			sdkAllotment.setCreateTime(new Date());
			sdkAllotment.setUpdateTime(new Date());
			sdkAllotment.setStatus(Constants.STATE_VALID);
			sdkAllotment.setCreateUser(uid);
			
			int r1 = sdkAllotmentMapper.insertSelective(sdkAllotment);
			int r2 = 0;
			
			List<Map<String, Object>> schedules = (List<Map<String, Object>>)paramMap.get("scheduleList");
			
			if (CollectionUtils.isNotEmpty(schedules)) {
				List<SdkAllotSchedule> sdkAllotSchedules = new ArrayList<SdkAllotSchedule>();
				for (Map<String, Object> scheduleForm : schedules) {
					List<Map<String, Object>> limitList = (List<Map<String, Object>>)scheduleForm.get("limits");
					for (Map<String, Object> limitMap : limitList) {
						String date = NvwaUtils.obj2Empty(limitMap.get("date"));
						SdkAllotSchedule sdkAllotSchedule = new SdkAllotSchedule();
						sdkAllotSchedule.setStartDay(NvwaUtils.obj2int(date.split("-")[0]));
						sdkAllotSchedule.setEndDay(NvwaUtils.obj2int(date.split("-")[1]));
						sdkAllotSchedule.setExposureLimit(NvwaUtils.obj2int(limitMap.get("limit")));
						sdkAllotSchedule.setScheduleFixed(NvwaUtils.obj2Empty(limitMap.get("times")));
						sdkAllotSchedule.setAllotmentId(sdkAllotment.getId());
						sdkAllotSchedule.setAdPosId(NvwaUtils.obj2int(scheduleForm.get("id")));
						sdkAllotSchedule.setIsSmooth(NvwaUtils.obj2int(scheduleForm.get("deliveryMode")));
						sdkAllotSchedule.setPriority(NvwaUtils.obj2int(scheduleForm.get("priority")));
						
						sdkAllotSchedule.setStraChapter(NvwaUtils.obj2int(paramMap.get("straChapter")));
						sdkAllotSchedule.setStraPage(NvwaUtils.obj2int(paramMap.get("straPage")));
						sdkAllotSchedule.setStraTime(NvwaUtils.obj2int(paramMap.get("straTime")));

						sdkAllotSchedules.add(sdkAllotSchedule);
					}
				}

				r2 = sdkAllotScheduleMapper.batchInsert(sdkAllotSchedules);
			}
			 
			LOG.info("sdkAllotment add. insertSdkAllotment: {}, insertSdkAllotSchedule: {}", r1, r2);
			if (r1 + r1 > 0) {
				return JsonUtils.STATUS_OK();
			}
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			LOG.error("", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> get(Integer id) {
		Map<String, Object> form = new HashMap<String, Object>();
		SdkAllotment sdkAllotment = sdkAllotmentMapper.selectByPrimaryKey(id);
		if (sdkAllotment == null) {
			return null;
		}
		
		form.put("id", sdkAllotment.getId());
		form.put("allotmentName", sdkAllotment.getAllotmentName());
		form.put("type", sdkAllotment.getType());
		form.put("appId", sdkAllotment.getAppId());
		form.put("platformId", sdkAllotment.getPlatformId());
		form.put("appVersion", sdkAllotment.getAppVersion());
		form.put("channelId", sdkAllotment.getChannelId());
		form.put("frequency", sdkAllotment.getFrequency());
		form.put("chargeType", sdkAllotment.getChargeType());
		
		BeanUtils.copyProperties(sdkAllotment, form);
		SdkAllotScheduleExample example = new SdkAllotScheduleExample();
		example.createCriteria().andAllotmentIdEqualTo(sdkAllotment.getId());
		List<SdkAllotSchedule> scheduleList = sdkAllotScheduleMapper.selectByExample(example);
		
		List<Map<String, Object>> scheduleForms = new ArrayList<Map<String, Object>>();
		AdPosition adPosition = null;
		Set<Integer> posIdSet = new HashSet<Integer>();
		if (scheduleList != null && !scheduleList.isEmpty()) {
			Map<String, Object> scheduleMap = null;
			List<Map<String, Object>> tempScheduleForms = null;
			for(SdkAllotSchedule sdkAllotSchedule : scheduleList) {
				scheduleMap = new HashMap<String, Object>();
				posIdSet.add(sdkAllotSchedule.getAdPosId());
				if (sdkAllotSchedule.getAdPosId() == NvwaUtils.obj2int(scheduleMap.get("id"))) {
					tempScheduleForms = (List<Map<String, Object>>)scheduleMap.get("limits");
				} else {
					tempScheduleForms = new ArrayList<Map<String, Object>>();
				}
				scheduleMap.put("id", sdkAllotSchedule.getAdPosId());
				scheduleMap.put("deliveryMode", sdkAllotSchedule.getIsSmooth());
				scheduleMap.put("priority", sdkAllotSchedule.getPriority());
				
				scheduleMap.put("straTime", sdkAllotSchedule.getStraTime());
				scheduleMap.put("straChapter", sdkAllotSchedule.getStraChapter());
				scheduleMap.put("straPage", sdkAllotSchedule.getStraPage());

				adPosition = adPositionMapper.selectByPrimaryKey(sdkAllotSchedule.getAdPosId());
				scheduleMap.put("name", adPosition == null ? "" : adPosition.getName());
				
				Map<String, Object> tempScheduleMap = new HashMap<String, Object>();
				tempScheduleMap.put("date", sdkAllotSchedule.getStartDay() + "-" + sdkAllotSchedule.getEndDay());
				tempScheduleMap.put("times", sdkAllotSchedule.getScheduleFixed());
				tempScheduleMap.put("limit", sdkAllotSchedule.getExposureLimit());
				tempScheduleMap.put("id", sdkAllotSchedule.getId());
				tempScheduleForms.add(tempScheduleMap);
				
				scheduleMap.put("limits", tempScheduleForms);
				
				scheduleForms.add(scheduleMap);
			}
		}
		
		form.put("posIds", org.apache.commons.lang3.StringUtils.join(posIdSet.toArray(), ","));
		form.put("scheduleList", scheduleForms);
		
		return form;
	}

	@Override
	public SdkAllotmentForm info(Integer id) {
		SdkAllotmentForm form = new SdkAllotmentForm();
		SdkAllotment sdkAllotment = sdkAllotmentMapper.selectByPrimaryKey(id);
		if (sdkAllotment == null) {
			return null;
		}
		BeanUtils.copyProperties(sdkAllotment, form);
		SdkAllotScheduleExample example = new SdkAllotScheduleExample();
		example.createCriteria().andAllotmentIdEqualTo(sdkAllotment.getId());
		List<SdkAllotSchedule> scheduleList = sdkAllotScheduleMapper.selectByExample(example);
		
		List<ScheduleForm> scheduleForms = new ArrayList<ScheduleForm>();
		String posIds = StringUtils.EMPTY;
		AdPosition adPosition = null;
		if (scheduleList != null && !scheduleList.isEmpty()) {
			ScheduleForm scheduleForm = null;
			for(SdkAllotSchedule sdkAllotSchedule : scheduleList) {
				scheduleForm = new ScheduleForm();
				adPosition = adPositionMapper.selectByPrimaryKey(sdkAllotSchedule.getAdPosId());
				scheduleForm.setPosName(adPosition == null ? "" : adPosition.getName());
				
				BeanUtils.copyProperties(sdkAllotSchedule, scheduleForm);
				posIds += sdkAllotSchedule.getAdPosId() + ",";
				
				scheduleForms.add(scheduleForm);
			}
		}
		form.setPosIds(StringUtils.isBlank(posIds) ? posIds : posIds.substring(0, posIds.length() - 1));
		form.setScheduleList(scheduleForms);

		Apps apps = appsMapper.selectByPrimaryKey(form.getAppId());
		FlowConsumer flowConsumer = flowConsumerMapper.selectByPrimaryKey(form.getPlatformId());
		
		form.setAppName(apps == null ? "" : apps.getAppName());
		form.setPlaName(flowConsumer == null ? "" : flowConsumer.getConsumerName());
		
		return form;
	}
	
	@Override
	public String getSchedule(String posIds, Integer month, Integer allotId, Integer type) {
		String result = null;
		try {
			List<Map<String, Object>> resutList = new ArrayList<Map<String,Object>>();
			int maxDay = DateUtils.getMaxDayByDate(DateUtils.parse(String.valueOf(month), DateUtils.SHORT_FORMAT_MONTH));
			String[] posIdArray = posIds.split(",");
			Map<String, Object> resultMap = null;
			AdPosition adPosition = null;
			for (int i = 0; i < posIdArray.length; i++) {
				Map<String, Object> parMap = new HashMap<String, Object>();
				parMap.put("type", type);
				parMap.put("posIds", posIdArray[i]);
//				parMap.put("month", month);
				List<Map<String, Object>> list = sdkAllotScheduleMapper.getScheduleList(parMap);
				int sechedule = 0;
				int editSchedule = 0;
				resultMap = new HashMap<String, Object>();
				int straTime = 0, straChapter = 0, straPage = 0;
				
				for(Map<String, Object> schedule : list) {
					if (NvwaUtils.obj2int(schedule.get("monthPeriod")) == month) {
						if (allotId != null && allotId == NvwaUtils.obj2int(schedule.get("allotId"))) { 
							editSchedule = NvwaUtils.obj2int(schedule.get("schedulePeriod"));
							if(NvwaUtils.obj2int(schedule.get("straTime")) > 0) {
								straTime = NvwaUtils.obj2int(schedule.get("straTime"));
							}
							if(NvwaUtils.obj2int(schedule.get("straChapter")) > 0) {
								straChapter = NvwaUtils.obj2int(schedule.get("straChapter"));
							}
							if(NvwaUtils.obj2int(schedule.get("straPage")) > 0) {
								straPage = NvwaUtils.obj2int(schedule.get("straPage"));
							}
						}
						sechedule = NvwaUtils.obj2int(schedule.get("schedulePeriod")) | sechedule;
					}
				}
				resultMap.put("straTime", straTime);
				resultMap.put("straChapter", straChapter);
				resultMap.put("straPage", straPage);
				
				resultMap.put("adPosId", posIdArray[i]);
				adPosition = adPositionMapper.selectByPrimaryKey(NvwaUtils.obj2int(posIdArray[i]));
				resultMap.put("posName", adPosition == null ? "" : adPosition.getName());
				
				List<Integer> times = new ArrayList<Integer>();
				if(sechedule == 0) {
					for (int j = 0; j < maxDay; j++) {
						times.add(0);
					}
				} else {
					String days = null;
					if (allotId != null) {
						days = buildUnusualBinnryString(editSchedule, sechedule, maxDay);
					} else {
						days = NvwaUtils.toBinnryString(sechedule, maxDay);
					}
					String[] arr = days.split("");
					for (String str : Arrays.asList(arr)) {
						times.add(Integer.valueOf(str));
					}
				}
				resultMap.put("times", times);
				resutList.add(resultMap);
			}
			result = JsonUtils.STATUS_OK(resutList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public String prepare() {
		String result = null;
		try {
			// APP及广告位 list
			List<Map<String, Object>> appList = sdkAllotmentMapper.getAppList();
			appList = appList == null ? new ArrayList<Map<String,Object>>() : appList;
			
			List<Map<String, Object>> platformList = sdkAllotmentMapper.getPlatformList();
			platformList = platformList == null ? new ArrayList<Map<String,Object>>() : platformList;
			
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("apps", appList);
			resultMap.put("platforms", platformList);
			result = JsonUtils.STATUS_OK(resultMap);
		} catch (Exception e) {
			LOG.error("prepare error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@Override
	public String adPostion(Integer appId, Integer platformId) {
		String result = null;
		try {
			List<Map<String, Object>> platformList = sdkAllotmentMapper.getAdPostionList(appId, platformId);
			if (platformList == null || platformList.isEmpty()) {
				return JsonUtils.DATA_NOT_FIND();
			}
			result = JsonUtils.STATUS_OK(platformList);
		} catch (Exception e) {
			LOG.error("adPostion error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	@Override
	public String adpStatus(Integer allotId) {
		String result = null;
		try {
			sdkAllotmentTask.updateSdkAllotSechduleStatus();
			// tempList 按monthPeriod倒序排列
			List<Map<String, Object>> dbList = sdkAllotScheduleMapper.getScheduleListByAllotmentId(allotId);
			if (CollectionUtils.isEmpty(dbList)) {
				return JsonUtils.DATA_NOT_FIND();
			}
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			Set<Integer> posIdSet = new HashSet<Integer>();
			int tempPosId = 0;
			Date date = new Date();
			int maxDay = DateUtils.getMaxDayByDate(new Date());
    		String currentDay = DateUtils.format(date, DateUtils.SHORT_FORMAT);
    		Map<Integer, List<Map<String, Object>>> tempMap = new HashMap<Integer, List<Map<String, Object>>>();
    		List<Map<String, Object>> tempList = null;
			for(Map<String, Object> map : dbList) {
				tempPosId = NvwaUtils.obj2int(map.get("posId"));
				if (posIdSet.contains(tempPosId)) {
					tempMap.get(tempPosId).add(map);
					continue;
				}
				tempList = new ArrayList<Map<String,Object>>();
				tempList.add(map);
				tempMap.put(tempPosId, tempList);
				posIdSet.add(tempPosId);
			}
			out:
			for(Integer posId : posIdSet) {
				tempList = tempMap.get(posId);
				for(Map<String, Object> map : tempList) {
					if (NvwaUtils.obj2int(map.get("status")) == 1) {
						list.add(map);
						continue out;
					}
				}
				for(Map<String, Object> map : tempList) {
					if (NvwaUtils.obj2int(map.get("monthPeriod")) == NvwaUtils.obj2int(currentDay.substring(0, 6))
							&& hasScheduleFuture(currentDay, maxDay, NvwaUtils.obj2int(map.get("schedulePeriod")))) {
						list.add(map);
						continue out;
					}
				}
				for(Map<String, Object> map : tempList) {
					if (NvwaUtils.obj2int(map.get("monthPeriod")) > NvwaUtils.obj2int(currentDay.substring(0, 6)) &&
							NvwaUtils.obj2int(map.get("schedulePeriod")) > 0) { // 未开始
						map.remove("monthPeriod");
						map.remove("schedulePeriod");
						list.add(map);
						continue out;
					}
				}
				for(Map<String, Object> map : tempList) {
					if (NvwaUtils.obj2int(map.get("monthPeriod")) < NvwaUtils.obj2int(currentDay.substring(0, 6)) ||
							hasScheduleFormer(currentDay, maxDay, NvwaUtils.obj2int(map.get("schedulePeriod")))) { // 已完成
						map.remove("monthPeriod");
						map.remove("schedulePeriod");
						map.put("status", 2);
						list.add(map);
						continue out;
					}
				}
			}
			result = JsonUtils.STATUS_OK(list);
		} catch (Exception e) {
			LOG.error("adpStatus error. ", e);
			result = JsonUtils.EXCEPTION_ERROR();
		}
		return result;
	}
	
	private String buildUnusualBinnryString(int editSchedule, int sechedule, int len) {
		int i = editSchedule & sechedule;
		
		char[] array = NvwaUtils.toBinnryString(i, len).toCharArray();
		char[] arrayS2 = NvwaUtils.toBinnryString(sechedule, len).toCharArray();
		
		List<Integer> temp = new ArrayList<>();
		for (int j = 0; j < array.length; j++) {
			if (array[j] == '1') {
				temp.add(j);
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int j = 0; j < arrayS2.length; j++){
			if (temp.contains(j)) {
				sb.append('2');
				continue;
			}
			sb.append(arrayS2[j]);
		}
		return sb.toString();
	}
	
	// 判断当前月今天以后是否有排期
	public static boolean hasScheduleFuture(String currentDay, int maxDay, int period) {
		boolean result = false;
		try {
			String periodStr = NvwaUtils.toBinnryString(period, maxDay);
			int day = Integer.valueOf(currentDay.substring(6, 8));
			char[] array = periodStr.toCharArray();
			for (int i = day - 1; i < array.length; i++) {
				if (array[i] == '1') {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			LOG.error("hasSche error. ", e);
		}
		return result;
	}
	
	// 判断当前月今天之前是否有排期
	public static boolean hasScheduleFormer(String currentDay, int maxDay, int period) {
		boolean result = false;
		try {
			String periodStr = NvwaUtils.toBinnryString(period, maxDay);
			int day = Integer.valueOf(currentDay.substring(6, 8));
			char[] array = periodStr.toCharArray();
			for (int i = 0; i < day - 1; i++) {
				if (array[i] == '1') {
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			LOG.error("hasSche error. ", e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		
		System.out.println("----------------------" + hasScheduleFuture("20190626", 30, 72));
		
		int temp = Integer.parseInt("000000000000000000000001000000", 2);
		System.out.println("-------" + temp);
		
		Date date = new Date();
		String currentDay = DateUtils.format(date, DateUtils.SHORT_FORMAT);
		int maxDay = DateUtils.getMaxDayByDate(new Date());
		String period = NvwaUtils.toBinnryString(NvwaUtils.obj2int(temp), maxDay);
		System.out.println("-------" + period);
		int day = Integer.valueOf(currentDay.substring(6, 8));
		char[] array = period.toCharArray();
		
		System.out.println(maxDay + "------" + day);
		System.out.println("------" + JsonUtils.TO_JSON(array));
		
		for (int i = 0; i < day - 1; i++) {
			System.out.println(array[i]);
		}
		
		if (array[(day - 1)] == '1') {
			// 更新数据，status=1
			//updateExe(tempMap, 1);
			System.err.println("-----" + 1);
		} else {
			// 更新数据，status=0
			//updateExe(tempMap, 0);
		}
	}
}
