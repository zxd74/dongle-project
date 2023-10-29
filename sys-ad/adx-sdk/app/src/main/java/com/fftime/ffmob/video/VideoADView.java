package com.fftime.ffmob.video;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetRequest;
import com.fftime.ffmob.common.network.PlainADNetResponse;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.common.view.FFTVideoView;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.util.AssetsUtils;
import com.fftime.ffmob.util.BitmapUtils;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.Md5Util;
import com.fftime.ffmob.util.SizeUtil;
import com.fftime.ffmob.util.StringUtil;
import com.fftime.ffmob.util.ViewIdUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class VideoADView {
    private static final String TAG = "fftvideoad";
    private FFTVideoView videoView;
    private VideoADListener listener;
    private NatiAd videoAdData;
    private AtomicBoolean volumeOff = new AtomicBoolean(true);
    private MediaPlayer mediaPlayer;
    private ViewGroup adContainer;
    private boolean isFullscreen;
    private ImageView closeBtn;
    private TextView countDown;
    private TextView sourceTxt;
    private RelativeLayout vadContainer;
    private Context context;
    private boolean isPreload;
    private AtomicBoolean clicked=new AtomicBoolean(false);

    public VideoADView(Context context, ViewGroup adContainer, NatiAd videoAdData, VideoADListener listener) {
        this(context, adContainer, videoAdData, listener, true, false);
    }

    public VideoADView(Context context, ViewGroup adContainer, NatiAd videoAdData, VideoADListener listener, boolean isPreload) {
        this(context, adContainer, videoAdData, listener, true, isPreload);
    }

    public VideoADView(Context context, ViewGroup adContainer, NatiAd videoAdData, VideoADListener listener, boolean isFullscreen, boolean isPreload) {
        this.videoView = new FFTVideoView(context);
        this.videoView.setClickable(true);
        this.listener = listener;
        this.videoAdData = videoAdData;
        this.adContainer = adContainer;
        this.isFullscreen = isFullscreen;
        this.isPreload = isPreload;
        this.context = context;

        initVideoView(context);
        if (isPreload) {
            asyncLoadFullVideo(videoAdData);
        }
    }

    private void asyncLoadFullVideo(final NatiAd videoAdData) {
        String localCacheFile = readCacheVideoFile(videoAdData.getVideo());
        if (!StringUtil.isEmpty(localCacheFile)) {
            listener.onVideoLoaded();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PlainNetRequest req = new PlainNetRequest(videoAdData.getVideo(), NetRequest.Method.GET,
                            null, new PlainNetRequest.CallBack() {
                        @Override
                        public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                            int statusCode = resp.getStatusCode();
                            if (statusCode != 200) {
                                onError(new RuntimeException("load video error"));
                            } else {
                                try {
                                    InputStream videoIn = resp.getRawContent();
                                    cacheVideoFile(videoIn, videoAdData.getVideo());
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            listener.onVideoLoaded();
                                        }
                                    });
                                } catch (Exception ex) {
                                    Log.e(TAG, "cache video file error", ex);
                                    listener.onLoadFail();
                                }
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            listener.onLoadFail();
                        }
                    });
                    NetClient.getInstance().excute(req, NetClient.Priority.High);
                } catch (Exception ex) {
                }
            }
        }).start();
    }

    private void cacheVideoFile(InputStream videoIn, String video) throws IOException {
        try {
            File cacheFile = new File(context.getExternalCacheDir(), Md5Util.encode(video));
            if (!cacheFile.getParentFile().exists()) cacheFile.getParentFile().mkdirs();
            byte[] buffer = new byte[1024 * 16];
            BufferedInputStream in = new BufferedInputStream(videoIn);
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(cacheFile));
            int len = 0;
            while ((len = in.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            os.close();
            in.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String readCacheVideoFile(String videoUrl) {
        File cacheVideoFile = new File(context.getExternalCacheDir(), Md5Util.encode(videoUrl));
        if (cacheVideoFile.exists()) {
            return cacheVideoFile.getAbsolutePath();
        }
        return null;
    }

    private void initVideoView(final Context context) {
        vadContainer = new RelativeLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        vadContainer.setVisibility(View.INVISIBLE);
        vadContainer.setLayoutParams(lp);
        vadContainer.addView(videoView);

        countDown = new TextView(context);
        countDown.setId(ViewIdUtils.generateViewId());
        countDown.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams countDownLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        countDownLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        countDownLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        countDownLayoutParams.topMargin = SizeUtil.dp2px(context, 5);
        countDownLayoutParams.leftMargin = SizeUtil.dp2px(context, 10);

        countDown.setTextColor(Color.WHITE);
        countDown.setGravity(Gravity.CENTER);
        countDown.setBackgroundColor(Color.GRAY);
        countDown.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        countDown.setLayoutParams(countDownLayoutParams);
        vadContainer.addView(countDown);

        //ctlLayout.addView(countDown);
        if (isFullscreen) {
            closeBtn = new ImageView(context);
            closeBtn.setBackgroundColor(Color.GRAY);
            closeBtn.setVisibility(View.INVISIBLE);
            closeBtn.setClickable(true);
            closeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "close videoAd activity");
                    listener.onClose();
                    //listener.onCompletion();
                }
            });
            RelativeLayout.LayoutParams skipLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            skipLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            skipLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            skipLayoutParams.topMargin = SizeUtil.dp2px(context, 5);
            skipLayoutParams.rightMargin = SizeUtil.dp2px(context, 10);
            closeBtn.setLayoutParams(skipLayoutParams);
            closeBtn.setImageBitmap(BitmapUtils.toBitmap(AssetsUtils.getResourceAsStream(context, getCloseIcon(context))));
            vadContainer.addView(closeBtn);
        }

        final ImageView volumeCtl = new ImageView(context);
        volumeCtl.setVisibility(View.INVISIBLE);
        volumeCtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //true
                if (volumeOff.get()) {
                    volumeOff.set(false);
                    volumeCtl.setImageBitmap(BitmapUtils.toBitmap(AssetsUtils.getResourceAsStream(context,
                            getVOnIcon(context))));
                    mediaPlayer.setVolume(1, 1);
                } else {
                    volumeOff.set(true);
                    volumeCtl.setImageBitmap(BitmapUtils.toBitmap(AssetsUtils.getResourceAsStream(context,
                            getVOffIcon(context))));
                    mediaPlayer.setVolume(0, 0);
                }
            }
        });
        //volumeCtl.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams volumeCtlLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        volumeCtlLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        volumeCtlLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        volumeCtlLayoutParams.bottomMargin = SizeUtil.dp2px(context, 5);
        volumeCtlLayoutParams.leftMargin = SizeUtil.dp2px(context, 10);
        volumeCtl.setImageBitmap(BitmapUtils.toBitmap(AssetsUtils.getResourceAsStream(context, getVOffIcon(context))));
        volumeCtl.setLayoutParams(volumeCtlLayoutParams);
        volumeCtl.setBackgroundColor(Color.GRAY);
        vadContainer.addView(volumeCtl);

        if (!TextUtils.isEmpty(videoAdData.getSource())) {
            sourceTxt = new TextView(context);
            RelativeLayout.LayoutParams sourceTxtLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            sourceTxtLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            sourceTxtLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            sourceTxtLp.bottomMargin = SizeUtil.dp2px(context, 5);
            sourceTxtLp.rightMargin = SizeUtil.dp2px(context, 10);
            sourceTxt.setLayoutParams(sourceTxtLp);
            sourceTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            sourceTxt.setTextColor(Color.WHITE);
            sourceTxt.setBackgroundColor(Color.GRAY);
            sourceTxt.setText(videoAdData.getSource());
            sourceTxt.setVisibility(View.INVISIBLE);
            vadContainer.addView(sourceTxt);
        }

        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (volumeOff.get()) {
                    mp.setVolume(0f, 0f);
                }
                mediaPlayer = mp;
                videoAdData.display();
                updateCountDownTxt();
                handler.post(run);
                countDown.setVisibility(View.VISIBLE);
                if (closeBtn != null) closeBtn.setVisibility(View.VISIBLE);
                volumeCtl.setVisibility(View.VISIBLE);
                if (sourceTxt != null) sourceTxt.setVisibility(View.VISIBLE);
                vadContainer.setVisibility(View.VISIBLE);
                mp.start();
                //adContainer.addView(vadContainer);
                listener.onStart();
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "视频播放完成");
                handler.removeCallbacks(run);
                mediaPlayer = null;
                listener.onCompletion();
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    Log.i(TAG, "视频点击");
                    videoAdData.click((Activity) context);
                }
                return false;
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i(TAG, String.format("what: %s, extra: %s", what, extra));
                return true;
            }
        });
        if (!isPreload) {
            videoView.setVideoURI(Uri.parse(videoAdData.getVideo()));
        }
        //adContainer.addView(vadContainer);
    }

    private String getCloseIcon(Context context) {
        int dpi = SizeUtil.dpi((Activity) context);
        if (dpi > 320) {
            return Constants.CLOSE_ICON_ASSETS_URL_LARGE;
        }
        return Constants.CLOSE_ICON_ASSETS_URL;
    }

    private String getVOffIcon(Context context) {
        int dpi = SizeUtil.dpi((Activity) context);
        if (dpi > 320) {
            return Constants.VOLUME_OFF_ICON_ASSETS_URL_LARGE;
        }
        return Constants.VOLUME_OFF_ICON_ASSETS_URL;
    }

    private String getVOnIcon(Context context) {
        int dpi = SizeUtil.dpi((Activity) context);
        if (dpi > 320) {
            return Constants.VOLUME_ON_ICON_ASSETS_URL_LARGE;
        }
        return Constants.VOLUME_ON_ICON_ASSETS_URL;
    }

    private void updateCountDownTxt() {
        try {
            if (mediaPlayer == null) return;
            if (this.videoView.isPlaying()) {
                this.videoView.getDuration();
                this.videoView.getCurrentPosition();

                int currentPosition = videoView.getCurrentPosition();
                int duration = videoView.getDuration();
                int left = duration - currentPosition;
                countDown.setText(left / 1000 + "s");
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        public void run() {
            updateCountDownTxt();
            handler.postDelayed(run, 1000);
        }
    };

    public void show() {
        adContainer.addView(vadContainer);
        if (isPreload) {
            String localFile = readCacheVideoFile(videoAdData.getVideo());
            if (localFile == null) {
                videoView.setVideoURI(Uri.parse(videoAdData.getVideo()));
            } else {
                videoView.setVideoPath(localFile);
            }
        }
        if (!videoView.isPlaying()) {
            videoView.requestFocus();
            videoView.start();
        }
        //adContainer.addView(videoView);
    }

    public void destroy() {
        Log.i(TAG,"videoView stop");
        videoView.stopPlayback();
    }

    public VideoView getVideoView() {
        return this.videoView;
    }
}
