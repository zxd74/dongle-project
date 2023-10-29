package com.fftime.ffmob.common.webview.handlers;

import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.bridge.Request;

public interface FFTHandler {
  void execute(final FFTWebview webView, final Request request);

  public String getName();
}
