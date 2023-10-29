package com.iwanvi.nvwa.web.service.impl;

import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.dao.SyslogMapper;
import com.iwanvi.nvwa.dao.model.Syslog;
import com.iwanvi.nvwa.web.service.SyslogService;

@Service
public class SyslogServiceImpl implements SyslogService {
	private static final Logger logger = LoggerFactory.getLogger(SyslogServiceImpl.class);

	@Autowired
	private SyslogMapper syslogMapper;

	@Override
	public void addSyslog(Integer userId, Integer userType, Object objData, String className, String methodName) {
		addSyslog(userId, userType, objData, null, className, methodName);
	}

	@Override
	public void addSyslog(Integer userId, Integer userType, Object objData, Object oldObjData,String className, String methodName) {
		if (objData != null) {
			Syslog syslog = new Syslog();
			syslog.setUserId(userId);
			syslog.setUserType(userType);
			syslog.setObjectData(JsonUtils.TO_JSON(objData));
			if (oldObjData != null) {
				syslog.setOldData(JsonUtils.TO_JSON(oldObjData));
			}
			syslog.setClassType(className);
			syslog.setOpType(methodName);
			syslog.setOpTime(new Date());
			try {
				Class objClass = objData.getClass();
				Method method = objClass.getMethod("getId");
				syslog.setObjectId((Integer) method.invoke(objData));
			} catch (Exception e) {
				e.printStackTrace();
			}
			syslogMapper.insertSelective(syslog);
		}
	}
}