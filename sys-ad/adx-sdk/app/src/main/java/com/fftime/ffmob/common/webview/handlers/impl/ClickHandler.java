package com.fftime.ffmob.common.webview.handlers.impl;

import org.json.JSONObject;

import com.fftime.ffmob.common.adservices.ClickService;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Request;
import com.fftime.ffmob.common.webview.handlers.FFTHandler;

public class ClickHandler implements FFTHandler {


  private static final class Holder {
    static final ClickHandler INSTANCE = new ClickHandler();
  }

  public static final ClickHandler getInstance() {
    return Holder.INSTANCE;
  }

  private ClickHandler() {

  }

  @Override
  public void execute(final FFTWebview webView, final Request request) {
    JSONObject adInfo = request.getParaObj().optJSONObject("adInfo");
    ClickService.getInstance().dealClick(webView.getContext(), adInfo);
  }



  @Override
  public String getName() {
    return "click";
  }

}
