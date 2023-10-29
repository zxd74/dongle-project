package com.fftime.ffmob.common.webview.bridge;

import org.json.JSONObject;

/**
 * Base class for all message from Native to JS
 *@author MR.Z 
 *
 */
public class Message {
  private static final String CALLBACK_FUNCTION_NAME = "bridge.callback";

  private final String callbackId;
  private final String message;
  private final RespStatus status;
  private JSONObject msgJSON;


  public static enum RespStatus {
    OK, ERROR
  }

  public Message(String callbackId, String message, RespStatus status) {
    this.callbackId = callbackId;
    try {
      //JSONObject json = new JSONObject(message);
      this.msgJSON= new JSONObject(message);
    }catch(Exception ex){

    }
    //this.message = JSONObject.quote(message);
    this.message=JSONObject.quote(message);
    this.status = status;
  }

  public String getCallbackId() {
    return callbackId;
  }

  public RespStatus getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public String getEncodeJsResponse() {
    JSONObject obj = new JSONObject();
    try {
      obj.putOpt("callbackid", callbackId);
      obj.putOpt("status", status.ordinal());
      obj.putOpt("data",this.msgJSON);
    }catch(Exception ex){

    }

     StringBuilder sb = new StringBuilder();
  sb.append(CALLBACK_FUNCTION_NAME).append("('").append(obj.toString()).append("')");
  //sb.append(CALLBACK_FUNCTION_NAME).append("({'"callbackid':'").append(callbackId)
  //          .append("','status':").append(status.ordinal()).append(",'data':").append(message)
  //        .append("})");
    return sb.toString();
}

}
