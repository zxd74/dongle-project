package com.fftime.ffmob.common.webview.bridge;

import org.json.JSONObject;

/**
 * request from JS to Native
 * 
 * @author MR.Z
 *
 */
public class Request {
  private final String service;
  private final String callbackId;
  private final JSONObject paraObj;

  public Request(String service, String callbackId, JSONObject paraObj) {
    this.service = service;
    this.callbackId = callbackId;
    this.paraObj = paraObj;
  }

  public String getService() {
    return service;
  }

  public String getCallbackId() {
    return callbackId;
  }

  public JSONObject getParaObj() {
    return paraObj;
  }

  @Override
  public String toString() {
    return "FftimeJsRequest [service=" + service + ", callbackId=" + callbackId + ", paraObj="
        + paraObj + "]";
  }

}
