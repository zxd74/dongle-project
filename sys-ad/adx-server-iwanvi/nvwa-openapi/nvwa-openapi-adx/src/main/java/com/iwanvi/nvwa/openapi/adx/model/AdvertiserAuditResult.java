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
public class AdvertiserAuditResult extends HashMap<String, AuditResult> {

	public AdvertiserAuditResult(String advertiserId, AuditResult auditResult) {
		put(advertiserId, auditResult);
	}
}
