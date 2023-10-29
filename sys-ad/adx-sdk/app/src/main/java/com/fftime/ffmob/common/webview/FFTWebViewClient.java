package com.fftime.ffmob.common.webview;

import com.fftime.ffmob.common.FFTLoger;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FFTWebViewClient extends WebViewClient {

  private static final String TAG = "WVClient";

  @Override
  public void onPageStarted(WebView view, String url, Bitmap favicon) {
    super.onPageStarted(view, url, favicon);
  }

  @Override
  public boolean shouldOverrideUrlLoading(WebView view, String url) {
    try {
      if (view instanceof FFTWebview) {
        FFTWebview webview = (FFTWebview) view;
        webview.getBridge().onReceived();
        webview.getBridge().exec(url);
      }
    } catch (Throwable err) {
      FFTLoger.w(TAG, "error while deal url loading by webview client", err);
    }
    return true;
  }
}
