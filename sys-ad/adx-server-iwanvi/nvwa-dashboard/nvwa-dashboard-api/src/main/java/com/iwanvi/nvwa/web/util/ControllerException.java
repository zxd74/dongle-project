package com.iwanvi.nvwa.web.util;

@SuppressWarnings("serial")
public class ControllerException extends RuntimeException {
	private final String code;

	public ControllerException(String code, String message) {
		super(message);
		this.code = code;
	}

	public ControllerException(String message) {
		super(message);
		this.code = "E000000";
	}

	public ControllerException(Exception ex) {
		super(ex);
		this.code = "E000000";
	}

	public String getCode() {
		return code;
	}
}
