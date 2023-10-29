package com.fftime.ffmob.common.webview.handlers.impl;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.PowerManager;
import android.view.View;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Message.RespStatus;
import com.fftime.ffmob.common.webview.bridge.Request;
import com.fftime.ffmob.common.webview.bridge.Response;
import com.fftime.ffmob.common.webview.handlers.FFTHandler;

public class RollableHandler implements FFTHandler {
  private static final String TAG = "RollableHandler";

  private static final class Holder {
    private static final RollableHandler INSTANCE = new RollableHandler();
  }

  public static final RollableHandler getInstance() {
    return Holder.INSTANCE;
  }
  private PowerManager pm; 

  private RollableHandler() {}

  @Override
  public void execute(FFTWebview webView, Request request) {
    FFTLoger.d(TAG, "in RollableHandler");
    boolean rollable =
        (webView.getWindowVisibility() == View.VISIBLE && webView.getVisibility() == View.VISIBLE && getPM(webView.getContext()).isScreenOn());
    JSONObject respJO = new JSONObject();
    try {
      respJO.put("rollable", rollable);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    webView.getBridge().resp(new Response(request, RespStatus.OK, respJO));
  }

  private PowerManager getPM(Context context){
    if(pm == null){
      pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    }
    return pm;
  }
  @Override
  public String getName() {
    return "rollable";
  }

}
