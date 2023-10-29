package com.fftime.ffmob.interstitial;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnShowListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Surface;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.fftime.ffmob.common.AdType;
import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.spy.Spy;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.common.webview.BaseADWebviewBuilder;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.FFTWebviewDelegate;
import com.fftime.ffmob.common.webview.eventbus.ADLifeEvent;
import com.fftime.ffmob.common.webview.eventbus.Event;
import com.fftime.ffmob.common.webview.eventbus.EventListener;
import com.fftime.ffmob.util.Constants;

public class InterstitialAD {
  private static final String TAG = "InterstitialAD";
  private static final AtomicBoolean interstitialInShow = new AtomicBoolean(false);
  private static final int[][] SUPPOSED_SIZE_ARRAY = new int[][] { {360, 300}, {300, 250},
      {240, 200}};



  private static final int RESET_ORIENTATION_NEVER = -999;
  private final String appId;
  private final String posId;
  private final InterstitialADListener listener;
  private final Activity activity;
  private boolean pageInited;

  @SuppressWarnings("unused")
  private int widthInDP, heightInDP, widthInPix, heightInPix;
  private FFTWebview webview;
  private int resetOrientation = RESET_ORIENTATION_NEVER;
  private Dialog dialog;


  public InterstitialAD(Activity activity, String appId, String posId,
      InterstitialADListener listener) {
    StatusManager.getInstance().init(activity);
    Spy.work(activity);
    this.appId = appId;
    this.posId = posId;
    this.listener = listener;
    this.activity = activity;
    this.webview = initWebView();
  }

  private FFTWebview initWebView() {
    FFTWebview wv = BaseADWebviewBuilder.build(activity, delegate, adLifeEventListener);
    wv.setLayoutParams(adaptLayoutSize());
    return wv;
  }

  private LayoutParams adaptLayoutSize() {
    if (this.widthInPix == 0) {
      for (int i = 0; i < SUPPOSED_SIZE_ARRAY.length; i++) {
        int wInDP = SUPPOSED_SIZE_ARRAY[i][0];
        int wInPix =
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, wInDP, activity
                .getResources().getDisplayMetrics());
        if (wInPix < StatusManager.getInstance().getDeviceStatus().getDeviceWidth() - 20) {
          this.widthInDP = wInDP;
          this.heightInDP = SUPPOSED_SIZE_ARRAY[i][1];
          this.widthInPix = wInPix;
          this.heightInPix =
              (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.heightInDP,
                  activity.getResources().getDisplayMetrics());
          break;
        }
      }
    }
    FFTLoger.d(TAG, "dp:" + StatusManager.getInstance().getDeviceStatus().getDeviceDensity());
    FFTLoger.d(TAG, "haha" + this.widthInPix + this.heightInPix);
    return new LayoutParams(widthInPix, heightInPix);
  }

  public void load() {
    if (!pageInited) {
      this.webview.loadUrl(Constants.INTERSTITIAL_PAGE + "#posid=" + posId);
      pageInited = true;
    } else {
      this.webview.getBridge().fire(new com.fftime.ffmob.common.webview.bridge.Event("reload"));
    }
  }

  public void show() {
    if (interstitialInShow.compareAndSet(false, true)) {
      lockOrientation();
      Dialog dialog = getDialog();
      dialog.show();
      this.webview.getBridge().fire(new com.fftime.ffmob.common.webview.bridge.Event("expand"));
    } else {
      FFTLoger.e(TAG, "Interstitial AD Already in show");
    }
  }


  private Dialog getDialog() {
    if (this.dialog == null) {
      this.dialog = new Dialog(activity);
      this.dialog.setOwnerActivity(activity);
      dialog.setCanceledOnTouchOutside(false);
      dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
      dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);
      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
      this.dialog.setContentView(this.webview, new LayoutParams(this.webview.getLayoutParams()));
      this.dialog.setOnDismissListener(new OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
          onDialogDismiss();
        }
      });
      this.dialog.setOnShowListener(new OnShowListener() {

        @Override
        public void onShow(DialogInterface dialog) {
          onDialogShow();
        }
      });
    }
    return this.dialog;
  }


  /**
   * 需要dialog锁定屏幕朝向的Case GG 搞这么多配置是要哪样啊~又踩坑~~NM~~~
   */
  @SuppressLint("InlinedApi")
  private static final Set<Integer> caredRequestOrientation = Collections
      .unmodifiableSet(new HashSet<Integer>(Arrays.asList(
          ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED, //
          ActivityInfo.SCREEN_ORIENTATION_USER, ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR,
          ActivityInfo.SCREEN_ORIENTATION_SENSOR, ActivityInfo.SCREEN_ORIENTATION_FULL_USER)));


  @SuppressLint("InlinedApi")
  private void lockOrientation() {
    // holdingActivity所设置的Orientation，如portrait、landscape、unspecified
    int requestOrientation = activity.getRequestedOrientation();
    if (caredRequestOrientation.contains(requestOrientation)) {
      int cfgOri = activity.getResources().getConfiguration().orientation;
      // 当前屏幕的朝向，如0 90 180 270等
      int currentRotation = activity.getWindowManager().getDefaultDisplay().getRotation();

      if (cfgOri == Configuration.ORIENTATION_LANDSCAPE) {
        if (isReverseOrientationSupported()
            && (currentRotation == Surface.ROTATION_180 || currentRotation == Surface.ROTATION_270)) {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        } else {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
      } else {
        if (isReverseOrientationSupported()
            && (currentRotation == Surface.ROTATION_180 || currentRotation == Surface.ROTATION_90)) {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        } else {
          activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
      }
      this.resetOrientation = requestOrientation;
    } else {
      // doNothing
      this.resetOrientation = RESET_ORIENTATION_NEVER;
    }

  }

  private boolean isReverseOrientationSupported() {
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO;
  }



  void onDialogShow() {
    if (this.listener != null) {
      this.listener.onADPresent();
    }
  }

  void onDialogDismiss() {
    if (resetOrientation != RESET_ORIENTATION_NEVER) {
      activity.setRequestedOrientation(resetOrientation);
      resetOrientation = RESET_ORIENTATION_NEVER;
    }
    interstitialInShow.set(false);
    if (this.listener != null) {
      this.listener.onADClosed();
    }
  }


  private final FFTWebviewDelegate delegate = new FFTWebviewDelegate() {
    @Override
    public AdType getADType() {
      return AdType.INTERSTITIAL;
    }

    @Override
    public String getADPositionId() {
      return posId;
    }

    @Override
    public String getADAppID() {
      return appId;
    }
  };

  private final EventListener adLifeEventListener = new EventListener() {

    @Override
    public void handle(Event evt) {
      if (evt instanceof ADLifeEvent) {
        ADLifeEvent lifeEvent = (ADLifeEvent) evt;
        switch (lifeEvent.getType()) {
          case ADReady:
            if (listener != null) {
              listener.onADReady();
            }
            break;
          case ADLoadFail:
            if (listener != null) {
              listener.onADFail();
            }
            break;
          case ADClosed:
            dialog.dismiss();
            break;
          default:
            break;
        }
      }
    }
  };
}
