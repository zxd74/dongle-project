package com.iwanvi.nvwa.openapi.dsp.service.distribution;

public class Customer {

	private int id;
	private int responseCode;
	private int timeOut;
	private long elapsed;
	private String name;
	private String requestUrl;
	private String exception;

	private String parameterString;
	private byte[] parametersByteArray;
	
	private String responseString;
	private byte[] responseByteArray;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}
	
	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getParameterString() {
		return parameterString;
	}

	public void setParameterString(String parameterString) {
		this.parameterString = parameterString;
	}

	public byte[] getParametersByteArray() {
		return parametersByteArray;
	}

	public void setParametersByteArray(byte[] parametersByteArray) {
		this.parametersByteArray = parametersByteArray;
	}

	public String getResponseString() {
		return responseString;
	}

	public void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public byte[] getResponseByteArray() {
		return responseByteArray;
	}

	public void setResponseByteArray(byte[] responseByteArray) {
		this.responseByteArray = responseByteArray;
	}
	
	public Customer(int id, int timeOut, String name, String requestUrl, String parameterString) {
		this.id = id;
		this.timeOut = timeOut;
		this.name = name;
		this.requestUrl = requestUrl;
		this.parameterString = parameterString;
	}

	public Customer(int id, int timeOut, String name, String requestUrl, byte[] parametersByteArray) {
		this.id = id;
		this.timeOut = timeOut;
		this.name = name;
		this.requestUrl = requestUrl;
		this.parametersByteArray = parametersByteArray;
	}
	
	public String toString() {
		return "id=" + id + ", requestUrl=" + requestUrl + ", timeOut=" + timeOut 
				+ ", elapsed=" + elapsed + ", responseCode=" + responseCode + ", exception=" + exception;
	}

}
