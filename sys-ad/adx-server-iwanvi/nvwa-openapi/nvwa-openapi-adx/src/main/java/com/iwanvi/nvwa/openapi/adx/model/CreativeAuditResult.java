/*
 * Copyright 2019 iwanvi.com All right reserved.
 */
package com.iwanvi.nvwa.openapi.adx.model;

import java.util.HashMap;

/**
 * 
 * @author wangwp
 */
@SuppressWarnings("serial")
public class CreativeAuditResult extends HashMap<String, AuditResult> {

	public CreativeAuditResult(String creativeId, AuditResult auditResult) {
		put(creativeId, auditResult);
	}
}
