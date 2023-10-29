package com.fftime.ffmob.rewardvideo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.adservices.ClickService;
import com.fftime.ffmob.common.adservices.DispService;
import com.fftime.ffmob.common.view.CustomVideoView;
import com.fftime.ffmob.common.view.DrawCrossMarkView;
import com.fftime.ffmob.util.Md5Util;
import com.fftime.ffmob.util.StringUtil;
import com.fftime.ffmob.video.VideoPlayListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by duan .
 */

public class FFRewardVideoActivity extends Activity implements MediaPlayer.OnPreparedListener {
    private boolean videoCompletion = true;
    private RelativeLayout relativeLayout;
    private CustomVideoView videoView;
    private ImageView imageView;
    private TextView textView;
    private DrawCrossMarkView drawCrossMarkView;
    private JSONObject data;
    private JSONObject adData;
    private String videoUrl;
    private String imgUrl;
    private String downloadUrl;
    private String exp;
    private String clk;
    public static WeakReference<VideoPlayListener> playListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(this);
        setContentView(relativeLayout);
        handleData();
        initView();
    }

    private void handleData() {
        String posId = getIntent().getStringExtra("posID");
        try {
            data = new JSONObject(getIntent().getStringExtra("data"));
        } catch (Throwable e) {
            FFTLoger.e("VIDEO", "解析JSON失败", e);
            finish();
        }
        if (data.optInt("ret") != 0) {
            FFTLoger.e("VIDEO", "解析JSON失败");
            finish();
        } else {
            JSONObject data2, posData;
            if ((data2 = data.optJSONObject("data")) == null
                    || (posData = data2.optJSONObject(posId)) == null) {
                FFTLoger.e("VIDEO", "广告返回为空");
                finish();
            } else {
                if (posData.optInt("ret") != 0) {
                    FFTLoger.e("VIDEO", posData.optString("msg"));
                    finish();
                } else {
                    JSONArray adArray = posData.optJSONArray("list");
                    adData = adArray.optJSONObject(0);
                    if (adData != null) {
                        clk = adData.optJSONArray("clk").optString(0);
                        exp = adData.optJSONArray("exp").optString(0);
                        imgUrl = adData.optString("img");
                        if (StringUtil.isEmpty(imgUrl)) {
                            FFTLoger.e("VIDEO", "imgUrl for VIDEOAD is empty");
                            finish();
                        }
                        videoUrl = adData.optString("video");
                        if (StringUtil.isEmpty(videoUrl)) {
                            FFTLoger.e("VIDEO", "videoUrl for VIDEOAD is empty");
                            finish();
                        }
                        downloadUrl = adData.optString("ldp");
                        if (StringUtil.isEmpty(downloadUrl)) {
                            FFTLoger.e("VIDEO", "downloadUrl for VIDEOAD is empty");
                            finish();
                        }
                        videoCompletion = false;
                    }
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.resume();
            videoView.start();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void initView() {
        if (StringUtil.isEmpty(videoUrl) || StringUtil.isEmpty(imgUrl) || StringUtil.isEmpty(downloadUrl)) {
            return;
        }
        if (videoView == null) {
            videoView = new CustomVideoView(this);
            relativeLayout.addView(videoView);
            MediaController controller = new MediaController(FFRewardVideoActivity.this);
            controller.setVisibility(View.GONE);
            videoView.setMediaController(controller);
            videoView.setVideoPath(videoUrl);
            videoView.start();
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //播放完成
                    videoCompletion = true;
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                    videoView.setVisibility(View.GONE);
                    drawCrossMarkView.setVisibility(View.VISIBLE);
                    if (playListener != null && playListener.get() != null) {
                        playListener.get().onCompletion();
                        //playListener.clear();
                    }
                }
            });
            videoView.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
            videoView.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
            videoView.setOnPreparedListener(this);
        } else {
            videoView.setVideoPath(videoUrl);
            videoView.start();
        }

        if (textView == null) {
            textView = new TextView(this);
            relativeLayout.addView(textView);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setPadding(0, 30, 30, 0);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            textView.setLayoutParams(params);
        }
        if (imageView == null) {
            imageView = new ImageView(this);
            relativeLayout.addView(imageView);
            imageView.setVisibility(View.GONE);
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
            imageView.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //点击下载
                    ClickService.getInstance().dealClickForVideo(FFRewardVideoActivity.this, adData);
                    DispService.getInstance().disp(clk);
                    if(playListener!=null && playListener.get()!=null) {
                        playListener.get().onAdClick();
                        playListener.clear();
                    }
                    finish();
                }
            });
            //加载图片
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    loadImg();
                }
            });
            thread.start();
        }

        if (drawCrossMarkView == null) {
            drawCrossMarkView = new DrawCrossMarkView(this);
            relativeLayout.addView(drawCrossMarkView);
            //退出按钮
            drawCrossMarkView.setVisibility(View.GONE);
            drawCrossMarkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            int size = (int) DrawCrossMarkView.dp2px(this, 30);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.setMargins(30, 30, 30, 30);
            drawCrossMarkView.setLayoutParams(params);
        }
    }

    private void loadImg() {
        try {
            String cacheFileName = Md5Util.encode(imgUrl);
            Bitmap bm = tryFromCache(cacheFileName);
            if (bm == null) {
                URL url = new URL(imgUrl);
                URLConnection conn = url.openConnection();
                InputStream in = conn.getInputStream();
                bm = BitmapFactory.decodeStream(in);
                showImage(bm);
                doCache(bm, cacheFileName);
            } else {
                showImage(bm);
            }
        } catch (Throwable e) {
            FFTLoger.e("VIDEO", "图片加载失败", e);
        }
    }

    @Override
    public void finish() {
        if (videoCompletion) {
            super.finish();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
            //开始播放
            DispService.getInstance().disp(exp);
            if(playListener!=null && playListener.get()!=null){
                playListener.get().onStartPlay();
            }
            updateTxt();
        handler.post(run);
    }

    private void updateTxt() {
        int currentPosition = videoView.getCurrentPosition();
        int duration = videoView.getDuration();
        int left = duration - currentPosition;
        textView.setText(left / 1000 + "");
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(run);
        super.onDestroy();
    }


    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        public void run() {
            updateTxt();
            handler.postDelayed(run, 1000);
        }
    };

    private Bitmap tryFromCache(String cacheFileName) {
        File cacheFile = new File(getCacheDir(), cacheFileName);
        if (cacheFile.exists()) {
            return BitmapFactory.decodeFile(cacheFile.getAbsolutePath());
        }
        return null;
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

    private void showImage(final Bitmap bm) {
        if (!isFinishing() && bm != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        imageView.setImageBitmap(bm);
                    } catch (Throwable e) {
                        FFTLoger.e("VIDEO", "图片加载失败", e);
                    }
                }
            });
        }
    }
}
