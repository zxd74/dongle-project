package com.iwanvi.nvwa.openapi.dsp.model;

import java.util.HashSet;
import java.util.Set;

public class Count {

	private static final Set<String> reqPartKeySet = new HashSet<String>();
	private static final Set<String> cataLogReqPartKeySet = new HashSet<String>();
	
	private static final Count INSTANCE = new Count();
	
	private Count() {
		
	}
	
	public static Count getInstance() {
		return INSTANCE;
	}
	
	public void clearReqPartKeySet() {
		reqPartKeySet.clear();
	}
	
	public void addReqPartKeySet(String reqPartKey) {
		reqPartKeySet.add(reqPartKey);
	}
	public void addCataLogReqPartKeySet(String cataLogReqPartKey) {
		cataLogReqPartKeySet.add(cataLogReqPartKey);
	}
	public Set<String> getReqPartKeySet() {
		return reqPartKeySet;
	}
	public Set<String> getCataLogReqPartKeySet() {
		return cataLogReqPartKeySet;
	}
	
}
