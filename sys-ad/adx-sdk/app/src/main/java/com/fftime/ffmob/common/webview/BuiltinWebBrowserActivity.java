package com.fftime.ffmob.common.webview;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fftime.ffmob.common.adservices.ClickService;
import com.fftime.ffmob.common.adservices.downloader.builtin.AppInfo;
import com.fftime.ffmob.common.adservices.downloader.builtin.BuiltinApkDownloader;
import com.fftime.ffmob.util.WebViewUtils;

import org.json.JSONArray;
import org.json.JSONException;


public class BuiltinWebBrowserActivity extends AppCompatActivity {
    private BuiltinApkDownloader downloader;
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return false; //
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String url = getIntent().getStringExtra("url");
        final WebView wv = new WebView(this);

        WebSettings webSettings = wv.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setDefaultFontSize(14);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (WebViewUtils.isAcceptedScheme(url)) {
                    view.loadUrl(url);
                    return true;
                } else {
                    Log.i("===", "builtin browser open deeplink: " + url);
                    try {
                        final Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(url));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } catch (Throwable ex) {
                        //DO NOTHING
                    }
                }
                return true;
            }
        });

        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition,
                                        String mimetype,
                                        long contentLength) {
                String[] dlmArray = getIntent().getStringArrayExtra("dlm");
                String dlcm = getIntent().getStringExtra("dlcm");
                String installed = getIntent().getStringExtra("installed");
                String dlfm = getIntent().getStringExtra("dlfm");
                if (dlmArray != null && dlmArray.length > 0) {
                    try {
                        ClickService.getInstance().dlm(dlmArray);
                    } catch (Exception ex) {

                    }
                }
                JSONArray dlcmArray = null;
                if(!TextUtils.isEmpty(dlcm)){
                    try {
                        dlcmArray = new JSONArray(dlcm);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                JSONArray dlfmArray = null;
                if(!TextUtils.isEmpty(dlfm)){
                    try {
                        dlfmArray = new JSONArray(dlfm);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                JSONArray installedArray = null;
                if(!TextUtils.isEmpty(installed)){
                    try {
                        installedArray = new JSONArray(installed);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

//                String fileName = URLUtil.guessFileName(url, contentDisposition, mimetype);
                downloader =  BuiltinApkDownloader.create(BuiltinWebBrowserActivity.this, AppInfo.create(null, null, url,mimetype,dlcmArray,dlfmArray,installedArray));
            }
        });

        wv.setWebChromeClient(new WebChromeClient());
        wv.loadUrl(url);

        this.setContentView(wv);

    }

    @Override
    protected void onDestroy() {
        if(downloader != null){
            try {
                unregisterReceiver(downloader.receiver);
                downloader = null;
            }catch (Exception e){

            }
        }
        super.onDestroy();
    }
}
