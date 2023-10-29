package com.iwanvi.nvwa.web.service;

public interface SyslogService {

	public void addSyslog(Integer userId, Integer userType, Object objData, String className, String methodName);

	public void addSyslog(Integer userId, Integer userType, Object objData, Object oldObjData, String className,
			String methodName);
}
