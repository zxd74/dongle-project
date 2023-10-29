package com.fftime.ffmob.common.webview.handlers;

import java.util.HashMap;

import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Request;
import com.fftime.ffmob.common.webview.bridge.Response;
import com.fftime.ffmob.common.webview.bridge.Message.RespStatus;

public class FFTHandlerManager {
  private HashMap<String, FFTHandler> handlers;
  private FFTWebview webView;

  public FFTHandlerManager(FFTWebview webView) {
    this.webView = webView;
    handlers = new HashMap<String, FFTHandler>();
  }

  public void addHandler(FFTHandler handler) {
    handlers.put(handler.getName(), handler);
  }

  public FFTHandler getHandler(String service) {
    FFTHandler handler = handlers.get(service);
    return handler;
  }

  public void exec(Request request) {
    FFTHandler handler = getHandler(request.getService());
    if (handler != null) {
      handler.execute(webView, request);
    } else {
      webView.getBridge().resp(new Response(request, RespStatus.ERROR, "Handler Not Found"));;
    }
  }
}
