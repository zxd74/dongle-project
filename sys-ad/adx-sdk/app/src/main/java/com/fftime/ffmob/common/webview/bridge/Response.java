package com.fftime.ffmob.common.webview.bridge;

import org.json.JSONObject;

/**
 * response to the JS-native service request
 * 
 * @author MR.Z
 *
 */
public class Response extends Message {
  public Response(Request req, RespStatus status, String message) {
    super(req.getCallbackId(), message, status);
  }

  public Response(Request req, RespStatus status, JSONObject message) {
    super(req.getCallbackId(), message != null ? message.toString() : "", status);
  }
}
