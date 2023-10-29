package com.fftime.ffmob.common.webview;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.adservices.downloader.APKManager;
import com.fftime.ffmob.common.adservices.downloader.APKStatusMonitor;
import com.fftime.ffmob.common.adservices.downloader.DownProgress;
import com.fftime.ffmob.common.adservices.downloader.APKManager.APKStatus;
import com.fftime.ffmob.common.webview.bridge.Bridge;
import com.fftime.ffmob.common.webview.eventbus.Event;
import com.fftime.ffmob.common.webview.eventbus.EventBus;
import com.fftime.ffmob.common.webview.eventbus.EventBusImpl;
import com.fftime.ffmob.common.webview.eventbus.EventListener;
import com.fftime.ffmob.util.Constants;

@SuppressLint("SetJavaScriptEnabled")
public class FFTWebview extends WebView {
  private static final String H5CACHE_DIR = "fft_h5cache";
  private static final String TAG = "FFTWV";
  private final FFTWebviewDelegate delegate;
  private final EventBus eventBus;
  private final Bridge bridge;
  private final APKStatusMonitor monitor = new APKStatusMonitor() {

    @Override
    public void onAPKStatusUpdate(String pkgName, APKStatus status, DownProgress progress) {
      FFTLoger.d(TAG, "apkStatusUpdate");
      if (bridge != null) {
        Map<String, Object> paras = new HashMap<String, Object>();
        paras.put("pkgName", pkgName);
        paras.put("status", status);
        if (progress != null) {
          paras.put("progress", progress.getPercent());
        }
        bridge.fire(new com.fftime.ffmob.common.webview.bridge.Event("apkStatusChange", paras));
      }
    }
  };

  public FFTWebview(Context context, FFTWebviewDelegate delegate, boolean enableCache) {
    super(context);
    if (enableCache) {
      this.enableHTML5Cache();
    }
    this.enableConsole();
    this.setWebViewClient(new FFTWebViewClient());
    this.eventBus = new EventBusImpl();
    this.delegate = delegate;
    this.bridge = new Bridge(this);
    this.getSettings().setJavaScriptEnabled(true);
  }

  public Bridge getBridge() {
    return bridge;
  }

  public FFTWebviewDelegate getDelegate() {
    return delegate;
  }

  private void enableConsole() {
    if (Constants.DEBUG) {
      this.setWebChromeClient(new WebChromeClient() {
        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
          FFTLoger.d(TAG,
              "Console:\t[" + consoleMessage.lineNumber() + "]\t" + consoleMessage.sourceId()
                  + "\t" + consoleMessage.message());
          return true;
        }
      });
    }
  }

  @SuppressWarnings("deprecation")
  private void enableHTML5Cache() {
    WebSettings settings = this.getSettings();
    File cacheDir = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
      cacheDir = getContext().getExternalCacheDir();
    }
    if (cacheDir == null) {
      cacheDir = getContext().getCacheDir();
    }
    File gdtcache = new File(cacheDir, H5CACHE_DIR);
    if (!gdtcache.exists()) {
      gdtcache.mkdirs();
    }
    String cachePath = gdtcache.getAbsolutePath();
    settings.setAllowFileAccess(true);
    settings.setAppCachePath(cachePath);
    settings.setAppCacheMaxSize(8 * 1024 * 1024);
    settings.setAppCacheEnabled(true);
    settings.setLoadsImagesAutomatically(true);
    settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    settings.setDomStorageEnabled(true);
  }

  public void addEventListener(Class<? extends Event> type, EventListener listener) {
    this.eventBus.regListener(type, listener);
  }

  public void fireEvent(Event event) {
    this.eventBus.fire(event);
  }

  private static final com.fftime.ffmob.common.webview.bridge.Event layoutEvent =
      new com.fftime.ffmob.common.webview.bridge.Event("webviewLayout");

  @SuppressWarnings("deprecation")
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    FFTLoger.d(TAG, "wvlayouted");
    bridge.fire(layoutEvent);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    APKManager.getInstance(getContext()).addStatusMonitor(monitor);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    APKManager.getInstance(getContext()).rmStatusMonitor(monitor);
  }
}
