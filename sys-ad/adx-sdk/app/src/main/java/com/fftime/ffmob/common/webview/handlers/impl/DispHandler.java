package com.fftime.ffmob.common.webview.handlers.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.fftime.ffmob.common.adservices.DispService;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Request;
import com.fftime.ffmob.common.webview.handlers.FFTHandler;
import com.fftime.ffmob.util.StringUtil;

public class DispHandler implements FFTHandler {

  private static final class Holder {
    private static final DispHandler INSTANCE = new DispHandler();
  }

  public static final DispHandler getInstance() {
    return Holder.INSTANCE;
  }

  private DispHandler() {}

  @Override
  public void execute(FFTWebview webView, Request request) {
    JSONArray array = request.getParaObj().optJSONArray("dispurls");
    List<String> lst = new ArrayList<String>();
    for (int i = 0; i < array.length(); i++) {
      String str = array.optString(i);
      if (!StringUtil.isEmpty(str)) {
        lst.add(str);
      }
    }
    DispService.getInstance().disp(lst);
  }

  @Override
  public String getName() {
    return "disp";
  }

}
