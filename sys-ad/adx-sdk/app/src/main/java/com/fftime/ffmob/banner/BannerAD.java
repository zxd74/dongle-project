package com.fftime.ffmob.banner;

import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;

import com.bytedance.sdk.openadsdk.TTAdNative;
import com.fftime.ffmob.common.AdType;
import com.fftime.ffmob.common.spy.Spy;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.common.webview.BaseADWebviewBuilder;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.FFTWebviewDelegate;
import com.fftime.ffmob.common.webview.bridge.Event;
import com.fftime.ffmob.common.webview.eventbus.ADLifeEvent;
import com.fftime.ffmob.common.webview.eventbus.EventListener;
import com.fftime.ffmob.util.Constants;

/**
 * <pre>
 * Banner广告尺寸：
 * 横向   :   Match Parent
 * 纵向   :   50dp
 * </pre>
 * 
 * @author robotz@yeah.net
 *
 */
public class BannerAD extends FrameLayout {
  private static final String BANNER_PAGE = Constants.BANNER_PAGE;
  private static final int BANNER_HEIGHT_DIP = 50;
  private final Activity activity;
  private final String posId;
  private final String appId;
  private BannerADListener adListener;
  private final AtomicBoolean wvLoaded = new AtomicBoolean(false);

  private FFTWebview webview;
  private FFTWebviewDelegate delegate;
  private EventListener adLifeEventListener;


  public BannerAD(Activity activity, String appId, String posId, BannerADListener adListener) {
      super(activity);
      StatusManager.getInstance().init(activity.getApplicationContext());
      Spy.work(activity);
      this.activity = activity;
      this.appId = appId;
      this.posId = posId;
    this.adListener = adListener;
    this.delegate = new WebviewDelegateImpl();
    this.adLifeEventListener = new ADLifeEventListenerImpl();
    this.webview = BaseADWebviewBuilder.build(this.activity, delegate, adLifeEventListener);
    this.webview.setVisibility(View.GONE);
    int heightDP =
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, BANNER_HEIGHT_DIP,
  getContext().getResources().getDisplayMetrics());
    this.addView(this.webview, new LayoutParams(LayoutParams.MATCH_PARENT, heightDP));
}


  public void setAdListener(BannerADListener adListener){
    this.adListener=adListener;
  }

  public void loadAD(BannerADRequest req) {
    if (wvLoaded.compareAndSet(false, true)) {
      webview.loadUrl(this.buildPageURL(req));
    } else {
      this.webview.getBridge().fire(new Event("reload"));
    }
  }

  @SuppressLint("DefaultLocale")
  private String buildPageURL(BannerADRequest req) {
    return String.format(BANNER_PAGE + "#posid=%s&rf=%d", this.posId, req.getRollingTime());
  }

  private class WebviewDelegateImpl implements FFTWebviewDelegate {

    @Override
    public String getADAppID() {
      return appId;
    }

    @Override
    public String getADPositionId() {
      return posId;
    }

    @Override
    public AdType getADType() {
      return AdType.BANNER;
    }
  }

  private class ADLifeEventListenerImpl implements EventListener {
    @Override
    public void handle(com.fftime.ffmob.common.webview.eventbus.Event event) {
      if (event instanceof ADLifeEvent) {
        ADLifeEvent lifeEvent = (ADLifeEvent) event;
        switch (lifeEvent.getType()) {
          case ADReady:
            webview.setVisibility(VISIBLE);
            if (adListener != null) {
              adListener.onADReady();
            }
            break;
          case ADLoadFail:
            if (adListener != null) {
              adListener.onADFail();
            }
            break;
          default:
            break;
        }
      }
    }
  }

}
