package com.fftime.ffmob.common.webview.handlers.impl;

import org.json.JSONObject;

import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Request;
import com.fftime.ffmob.common.webview.eventbus.ADLifeEvent;
import com.fftime.ffmob.common.webview.eventbus.ADLifeEvent.ADLifeEventType;
import com.fftime.ffmob.common.webview.handlers.FFTHandler;

public class ADLifeEventHandler implements FFTHandler {
  private static final class Holder {
    static final ADLifeEventHandler INSTANCE = new ADLifeEventHandler();
  }

  public static final ADLifeEventHandler getInstance() {
    return Holder.INSTANCE;
  }

  private ADLifeEventHandler() {

  }

  @Override
  public void execute(FFTWebview webView, Request request) {
    // webView.
    String lifeEventType = request.getParaObj().optString("type");
    JSONObject eventParas = request.getParaObj().optJSONObject("paras");
    ADLifeEventType typeEnmu = ADLifeEventType.fromString(lifeEventType);
    if (typeEnmu != null) {
      webView.fireEvent(new ADLifeEvent(typeEnmu, eventParas));
    }
  }

  @Override
  public String getName() {
    return "adlifeevent";
  }

}
