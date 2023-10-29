package com.fftime.ffmob.demos;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.fftime.ffmob.R;
import com.fftime.ffmob.SdkSettings;
import com.fftime.ffmob.aggregation.ads.NativeAD;
import com.fftime.ffmob.aggregation.ads.NativeExpressAD;
import com.fftime.ffmob.aggregation.api.AggrInterstitialAD;
import com.fftime.ffmob.aggregation.api.AggrNativeAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.InterstitialAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeAdListener;
import com.fftime.ffmob.aggregation.base.listener.NativeExpressAdListener;
import com.fftime.ffmob.aggregation.gdt.GDTADFactory;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.aggregation.model.NativeAdData;
import com.fftime.ffmob.banner.BannerAD;
import com.fftime.ffmob.basead.BaseAD;
import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.interstitial.InterstitialAD;
import com.fftime.ffmob.interstitial.InterstitialADListener;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.nativead.NativeADListener;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.HandlerUtils;
import com.fftime.ffmob.util.PermissionUtils;
import com.fftime.ffmob.video.FullscreenVideoAD;
import com.fftime.ffmob.video.VideoAD;
import com.fftime.ffmob.video.VideoADListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.in;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
    private static final String TAG = "MainDemoActivity";

    private static final String APPID = "7r2MNf";
    private static final String POSID = "qM7Jza";
    @SuppressWarnings("unused")
    private BannerAD banner;
    private InterstitialAD interstitialAD;
    private FullscreenVideoAD vad;

    @Override
    protected void onResume() {
        Log.i(TAG,"activity onsume");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SdkSettings.getInstance().channel("10021");
        PermissionUtils.getPersimmions(this);

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        HashMap<String, String> ext = new HashMap<>();
        ext.put("zyqEne_tmids", "19,21");
        getIntent().putExtra(Constants.INTENT_VAR_REQ_EXT_PARAMS, ext);

        setContentView(R.layout.activity_main);
        com.fftime.ffmob.aggregation.ads.InterstitialAD iAd = new AggrInterstitialAD(MainActivity.this, AdSlotSetting.newBuilder().posId(POSID).appId(APPID).build(), new InterstitialAdListener() {
            @Override
            public void onAdFailed(FFAdError error) {
            }
            @Override
            public void onAdSuccess() {
                //回调里面展示广告
            }
            @Override
            public void onAdClick() {
            }

            @Override
            public void onAdExposure() {
            }
        });
        iAd.loadAD();
        iAd.showAD();

        this.interstitialAD = new InterstitialAD(this, APPID, POSID, new InterstitialADListener() {

            @Override
            public void onADReady() {
                FFTLoger.d(TAG, "interstitial ad ready");
                interstitialAD.show();
            }

            @Override
            public void onADPresent() {
                FFTLoger.d(TAG, "Interstitial ad Present");
            }

            @Override
            public void onADFail() {
                FFTLoger.d(TAG, "Interstitial ad Failed");
            }

            @Override
            public void onADClosed() {
                FFTLoger.d(TAG, "Interstitial ad Closed");
            }
        });
        this.findViewById(R.id.loadInterstitial).setOnClickListener(v -> {
            try {
                final Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("cread://com.mianfeizs.book"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if(isInstalledApp(MainActivity.this,intent)){
                    startActivity(intent);
                    HandlerUtils.getUiThreadHandler().postDelayed(() -> {
                        if (isAppInBackground(MainActivity.this)) {
                            Toast.makeText(MainActivity.this, "调起成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "调起失败", Toast.LENGTH_SHORT).show();
                        }
                    }, 5000);
                }
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "调起失败", Toast.LENGTH_SHORT).show();
            }
        });

        this.findViewById(R.id.loadNatiExpressAd).setOnClickListener(v -> {
        });

        String videoPosId = "vqm2Qj";
        String vappid = "qqInyy";
        final FrameLayout videoAdContainer = findViewById(R.id.fullvideocontainer);

        final boolean isPreload = true;
        vad = new FullscreenVideoAD(MainActivity.this, vappid, videoPosId, new VideoADListener() {
            @Override
            public void onLoadFinish() {
                Log.i("===", "视频广告加载完成");
                if(!isPreload) showVideoAD();
            }

            @Override
            public void onLoadFail() {
                Log.i("===", "视频广告加载失败");
            }

            @Override
            public void onVideoLoaded() {
                Log.i("===", "视频加载完成");
                if(isPreload) {
                    showVideoAD();
                }
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onCompletion() {
                Log.i("", "视频播放完成");
                videoAdContainer.removeAllViews();
                videoAdContainer.getLayoutParams().height= FrameLayout.LayoutParams.WRAP_CONTENT;
            }

            @Override
            public void onClose() {
                videoAdContainer.removeAllViews();
            }
        },isPreload);

        this.findViewById(R.id.loadVideo).setOnClickListener(v -> vad.loadAD());

        /**
         * 加载窗口视频的
         */
        this.findViewById(R.id.loadWindosVideo).setOnClickListener(v -> startActivity(new Intent(MainActivity.this,VideoADDemoActivity.class)));

        SdkSettings.getInstance().apiDomain("api-exchange-qa.cread.com").channel("50004");

        //f2time
        String nativeAppID = "qqInyy";
        String nativePosID = "uUvIFj";

        this.findViewById(R.id.loadFeeds).setOnClickListener(v -> {
            com.fftime.ffmob.nativead.NativeAD nativeAD  = new com.fftime.ffmob.nativead.NativeAD(MainActivity.this,nativeAppID,nativePosID);
            nativeAD.loadAD(new NativeADListener() {
                @Override
                public void onSucc(NatiAd natiAd) {
                    ImageView adView = findViewById(R.id.feedsImg);
                    adView.setOnClickListener(v1 -> natiAd.click(MainActivity.this));
                    adView.setVisibility(View.VISIBLE);
                        new Thread(() -> {
                            try {
                                URL url = new URL(natiAd.getImgs().get(0));
                                URLConnection conn = url.openConnection();
                                InputStream in = conn.getInputStream();
                                Bitmap bitmap1 = BitmapFactory.decodeStream(in);
                                MainActivity.this.runOnUiThread(() -> {
                                    adView.setImageBitmap(bitmap1);
                                    natiAd.display();
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }).start();
                }

                @Override
                public void onFail(int errCode, String msg) {
                    Log.e("guoxl", "加载失败：" + errCode + "    " + msg);
                }
            });
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("guoxl","onSaveInstanceState");
    }

    private void showVideoAD() {
        if (vad != null) vad.showAD();
    }

    private boolean isInstalledApp(Context context, Intent intent){
        if(intent == null){
            return false;
        }
        PackageManager m = context.getPackageManager();
        List<ResolveInfo> list = m.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        if(list != null && list.size() > 0){
            return true;
        }
        return false;
    }

    public static boolean isAppInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            // Android5.0及以后的检测方法
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                //前台程序
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }
}
