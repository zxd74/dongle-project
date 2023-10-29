package com.fftime.ffmob.common.webview.handlers.impl;

import org.json.JSONObject;

import com.fftime.ffmob.common.AdSize;
import com.fftime.ffmob.common.AdType;
import com.fftime.ffmob.common.adservices.LoadCallback;
import com.fftime.ffmob.common.adservices.LoadRequest;
import com.fftime.ffmob.common.adservices.LoadService;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Message;
import com.fftime.ffmob.common.webview.bridge.Request;
import com.fftime.ffmob.common.webview.bridge.Message.RespStatus;
import com.fftime.ffmob.common.webview.handlers.FFTHandler;

public class LoadADHandler implements FFTHandler {

  private static final class Holder {
    private static final LoadADHandler INSTANCE = new LoadADHandler();
  }

  public static final LoadADHandler getInstance() {
    return Holder.INSTANCE;
  }

  private LoadADHandler() {}

  @Override
  public void execute(final FFTWebview webView, final Request request) {
    AdType adType = webView.getDelegate().getADType();
    AdSize size = AdSize.adapt(adType);
    int count = request.getParaObj().optInt("count", 1);
    LoadRequest lreq =
        new LoadRequest(webView.getDelegate().getADAppID(),
            webView.getDelegate().getADPositionId(), size.getWidth(), size.getHeight(), count);
    LoadService.getInstance().loadAD(lreq, new LoadCallback() {
      @Override
      public void onSucc(JSONObject data) {
        webView.getBridge().resp(
            new Message(request.getCallbackId(), data.toString(), RespStatus.OK));
      }

      @Override
      public void onError() {
        webView.getBridge().resp(new Message(request.getCallbackId(), null, RespStatus.ERROR));
      }
    });
  }

  @Override
  public String getName() {
    return "loadad";
  }

}
