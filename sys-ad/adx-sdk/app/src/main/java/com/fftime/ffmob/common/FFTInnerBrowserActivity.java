package com.fftime.ffmob.common;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class FFTInnerBrowserActivity extends Activity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String url = getIntent().getStringExtra("url");
    WebView wv = new WebView(this);
    this.setContentView(wv);
    wv.getSettings().setJavaScriptEnabled(true);
    wv.loadUrl(url);
  }
}
