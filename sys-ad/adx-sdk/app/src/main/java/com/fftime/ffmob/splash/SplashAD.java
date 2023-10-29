package com.fftime.ffmob.splash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.nativead.ClkEventData;
import com.fftime.ffmob.nativead.NativeAD;
import com.fftime.ffmob.nativead.NativeADListener;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.Md5Util;
import com.fftime.ffmob.util.SizeUtil;
import com.fftime.ffmob.util.ViewIdUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.Reference;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

//新的开屏广告实现
public class SplashAD {
    private static final String LOG_TAG = SplashAD.class.getSimpleName();

    //private ViewGroup adContainer;
    private TextView skipView;
    private TextView adLogo;
    private TextView adSource;
    private ImageView adView;
    private NatiAd natiAd;

    private final NativeAD nativeAD;
    private final SplashADListener listener;
    private boolean inTime;

    private final Context context;
    private static final String SKIP_VIEW_TXT = "跳过 %ds";
    private final CountDownTimer timer;
    private AtomicBoolean finished = new AtomicBoolean(false);


    public SplashAD(Activity context, String appID, String posID, ViewGroup adContainer, final SplashADListener listener) {
        this.context = context;
        this.listener = listener;

        //RelativeLayout splashAdContainer = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        adContainer.setLayoutParams(layoutParams);
        adContainer.setBackgroundColor(Color.WHITE);

        LinearLayout imageContainer = new LinearLayout(context);
        imageContainer.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        adView = new ImageView(context);
        adView.setId(ViewIdUtils.generateViewId());
        adView.setBackgroundColor(Color.RED);
        //adView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        adView.setVisibility(View.INVISIBLE);
        //adView.setAdjustViewBounds(true);
        RelativeLayout.LayoutParams splashImageViewLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        //splashImageViewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        adView.setLayoutParams(splashImageViewLayoutParams);
        //splashImageViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        imageContainer.addView(adView);

        skipView = new TextView(context);
        skipView.setId(ViewIdUtils.generateViewId());
        skipView.setClickable(true);
        skipView.setBackgroundColor(Color.GRAY);
        RelativeLayout.LayoutParams skipViewLayoutParams = new RelativeLayout.LayoutParams(SizeUtil.dp2px(context, 80),
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        skipViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        skipViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        int margin = SizeUtil.dp2px(context, 16);
        //左、上、右、下
        skipViewLayoutParams.setMargins(0, SizeUtil.dp2px(context, 5), margin, 0);

        skipView.setLayoutParams(skipViewLayoutParams);

        skipView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        skipView.setTextColor(Color.WHITE);
        skipView.setGravity(Gravity.CENTER);
        skipView.setVisibility(View.INVISIBLE);
        int padding = SizeUtil.dp2px(context, 6);
        skipView.setPadding(padding, padding, padding, padding);
        skipView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishSplashAd();
            }
        });

        adLogo = new TextView(context);
        adLogo.setId(ViewIdUtils.generateViewId());
        adLogo.setClickable(false);
        adLogo.setText("广告");
        adLogo.setTextColor(Color.WHITE);
        //adLogo.setBackgroundColor(Color.GRAY);
        //adLogo.setGravity(Gravity.CENTER);
        adLogo.setVisibility(View.INVISIBLE);
        adLogo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        RelativeLayout.LayoutParams adLogoLayoutParams = new RelativeLayout.LayoutParams(SizeUtil.dp2px(context, 100),
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        adLogoLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        adLogoLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //int margin = SizeUtil.dp2px(context, 16);
        //左、上、右、下
        adLogoLayoutParams.setMargins(SizeUtil.dp2px(context, 1), SizeUtil.dp2px(context, 5), 0, 0);

        //padding = SizeUtil.dp2px(context,6);
        adLogo.setPadding(padding, padding, padding, padding);
        adLogo.setLayoutParams(adLogoLayoutParams);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //Android系统大于等于API16，使用setBackground
            GradientDrawable bg = new GradientDrawable();
            bg.setStroke(SizeUtil.dp2px(context, 1), Color.WHITE);
            bg.setCornerRadius(SizeUtil.dp2px(context, 6));
            skipView.setBackground(bg);
        }

        //广告来源标识
        adSource = new TextView(context);
        adSource.setId(ViewIdUtils.generateViewId());
        adSource.setClickable(false);
        //adSource.setTextAlignment(T);
        RelativeLayout.LayoutParams adSourceLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adSourceLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adSourceLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

//        int margin = SizeUtil.dp2px(context, 16);
//        //左、上、右、下
//        skipViewLayoutParams.setMargins(0, SizeUtil.dp2px(context, 5), margin, 0);
        //左、上、右、下
        margin = SizeUtil.dp2px(context, 5);
        adSourceLayoutParams.rightMargin=margin;

        //adSourceLayoutParams.setMargins(0, 0, 1, SizeUtil.dp2px(context, 5));
        //adSource.setPadding(padding, padding, padding, padding);
        adSource.setLayoutParams(adSourceLayoutParams);
        adSource.setTextColor(Color.WHITE);
        adSource.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        adSource.setVisibility(View.INVISIBLE);

        adContainer.addView(imageContainer);
        adContainer.addView(skipView);
        adContainer.addView(adLogo);
        adContainer.addView(adSource);
        //adContainer.addView(splashAdContainer);

        //context.setContentView(splashAdContainer);
        nativeAD = new NativeAD(context, appID, posID);

        timer = new CountDownTimer(5000 + 500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //String text = "";
                skipView.setText(String.format(SKIP_VIEW_TXT, millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                inTime = false;
                finishSplashAd();
            }
        };
    }

    private void finishSplashAd() {
        //finished.compareAndSet(false,true);
        if (finished.compareAndSet(false, true)) {
            if (timer != null) timer.cancel();
            listener.onSplashFinish();
        }
    }

    private void failedSplashAd() {
        if (timer != null) timer.cancel();
        listener.onSplashFail();
    }

    public void fetchAndShow() {
        inTime = true;
        timer.start();

        this.nativeAD.loadAD(new NativeADListener() {
            @Override
            public void onSucc(final NatiAd natiAd) {
                SplashAD.this.natiAd = natiAd;
                if (!inTime) onFail(-1, "NoAd");
                if (natiAd.getImg() == null || natiAd.getImg().length() == 0)
                    onFail(-1, "NoAd");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            downAndShowSplashImage(natiAd);
                        } catch (Exception ex) {
                            onFail(-1, ex.getMessage());
                        }
                    }
                }).start();
            }

            @Override
            public void onFail(int errCode, String msg) {
                if (inTime) {
                    failedSplashAd();
                }
            }
        });
    }

    private void downAndShowSplashImage(NatiAd natiAd) throws Exception {
        String imgUrl = natiAd.getImg();
        String cacheKey = Md5Util.encode(imgUrl);
        //adSource.setText(natiAd.getSource());
        Bitmap bitmap = tryFromCache(cacheKey);
        if (bitmap == null) {
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            showImage(bitmap,natiAd);
            doCache(bitmap, cacheKey);
        } else {
            showImage(bitmap,natiAd);
        }
    }

    private void showImage(final Bitmap bitmap,final NatiAd natiAd) {
        if (inTime && bitmap != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Activity activity = (Activity) context;
                        final ClkEventData.Builder clkEventData = ClkEventData.newBuilder();

                        adView.setImageBitmap(bitmap);
                        //adView.setAdjustViewBounds(true);
                        adView.setScaleType(ImageView.ScaleType.FIT_XY);
                        natiAd.clk(activity,adView);
                        adView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finishSplashAd();
                                natiAd.click(activity);
                            }
                        });
                        adView.setVisibility(View.VISIBLE);
                        skipView.setVisibility(View.VISIBLE);
                        adLogo.setVisibility(View.VISIBLE);
                        adSource.setText(natiAd.getSource());
                        if (adSource.getText() != null && adSource.getText().length() > 0) {
                            adSource.setVisibility(View.VISIBLE);
                        }
                        //timerView.setVisibility(View.VISIBLE);
                        if(listener instanceof ISplashAdListener){
                            ((ISplashAdListener) listener).onAdShow();
                        }
                        natiAd.display();
                    } catch (Throwable e) {
                        failedSplashAd();
                    }
                }
            });
        }
    }

    private void doCache(Bitmap bm, String cacheFileName) {
        File cacheFile = new File(getCacheDir(), cacheFileName);
        try {
            FileOutputStream fo = new FileOutputStream(cacheFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fo);
            fo.close();
        } catch (Throwable e) {
            cacheFile.delete();
        }
    }

    private Bitmap tryFromCache(String cacheFileName) {
        File cacheFile = new File(getCacheDir(), cacheFileName);
        if (cacheFile.exists()) {
            return BitmapFactory.decodeFile(cacheFile.getAbsolutePath());
        }
        return null;
    }

    private File getCacheDir() {
        File cacheDir = new File(context.getCacheDir(), "fftcache");
        cacheDir.mkdirs();
        return cacheDir;
    }
}
