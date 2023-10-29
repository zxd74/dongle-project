/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.model;

/**
 * 
 * @author wangwp
 */
public class AuditResult {
	private Integer auditStatus;
	private String auditMsg;

	public AuditResult(Integer auditStatus, String auditMsg) {
		this.auditStatus = auditStatus;
		this.auditMsg = auditMsg;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAutidStatus(Integer autidStatus) {
		this.auditStatus = autidStatus;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}
}
