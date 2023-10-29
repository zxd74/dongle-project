package com.fftime.ffmob.video;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fftime.ffmob.common.adservices.ClickService;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Tony on 2019/10/8.
 *
 * 插页视频
 * 为了防止和之前不冲突，重新写一个
 */

public class VideoWindowsAdView {

    private static final String TAG = "fftvideoad";
    private FFTVideoView videoView;
    private VideoWindowsAdListener listener;
    private NatiAd videoAdData;
    private AtomicBoolean volumeOff = new AtomicBoolean(true);
    private MediaPlayer mediaPlayer;
    private Context context;
    private TextView sourceTxt;
    private ImageView imageAdView;
    private ProgressBar progressBar;
    private boolean isPreload;
    private RelativeLayout vadContainer;
    private boolean isPlayed = false;
    private boolean isloaded = false;
    private String posId;


    public RelativeLayout getVadContainer() {
        return vadContainer;
    }

    public void setListener(VideoWindowsAdListener listener) {
        this.listener = listener;
        listener.onLoadFinish();
        if (isPreload) {
            asyncLoadFullVideo(videoAdData);
        }
    }
    private AtomicBoolean clicked=new AtomicBoolean(false);

    public VideoWindowsAdView(Context context,NatiAd videoAdData, VideoWindowsAdListener listener) {
        this(context,videoAdData,true, false,"");
    }

    public VideoWindowsAdView(Context context, NatiAd videoAdData,boolean isPreload) {
        this(context,videoAdData,true, isPreload,"");
    }

    public VideoWindowsAdView(Context context, NatiAd videoAdData, boolean isFullscreen, boolean isPreload,String posId) {
        this.videoView = new FFTVideoView(context);
        this.videoAdData = videoAdData;
        this.isPreload = isPreload;
        this.context = context;
        this.posId = posId;
        videoView.setFullscreen(isFullscreen);
        initVideoView(context);
    }

    private void asyncLoadFullVideo(final NatiAd videoAdData) {
        String localCacheFile = readCacheVideoFile(videoAdData.getVideo());
        if (!StringUtil.isEmpty(localCacheFile)) {
            isloaded = true;
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
                                            if(listener != null){
                                                isloaded = true;
                                                listener.onVideoLoaded();
                                                ViewParent parent = vadContainer.getParent();
                                                if(parent != null ){
                                                    while (parent instanceof View) {
                                                        if (((View) parent).getVisibility() != View.VISIBLE) {
                                                            return;
                                                        }
                                                        parent = parent.getParent();
                                                        if (parent != null && !(parent instanceof View)) {
                                                            break;
                                                        }
                                                    }
                                                    if (!isPlayed) {
                                                        show();
                                                        isPlayed = true;
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } catch (Exception ex) {
                                    Log.e(TAG, "cache video file error", ex);
                                    if(listener != null){
                                        listener.onLoadFail();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            if(listener != null){
                                listener.onLoadFail();
                            }
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
            File cacheFile = new File(context.getExternalCacheDir().getAbsolutePath()+"/videocache", Md5Util.encode(video)+posId);
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
        File cacheVideoFile = new File(context.getExternalCacheDir().getAbsolutePath()+"/videocache", Md5Util.encode(videoUrl)+posId);
        if (cacheVideoFile.exists()) {
            return cacheVideoFile.getAbsolutePath();
        }
        return null;
    }

    private void initVideoView(final Context context) {
        vadContainer = new RelativeLayout(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        vadContainer.setLayoutParams(lp);
        vadContainer.addView(videoView);

        imageAdView = new ImageView(context);
        RelativeLayout.LayoutParams imagelp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageAdView.setLayoutParams(imagelp);
        imageAdView.setTag(videoAdData.getImg());
        imageAdView.setScaleType(ImageView.ScaleType.FIT_XY);
        vadContainer.addView(imageAdView);
        if(!TextUtils.isEmpty(videoAdData.getImg())){
            fetchImage();
        }else {
            imageAdView.setImageBitmap(BitmapUtils.toBitmap(AssetsUtils.getResourceAsStream(context, Constants.VIDEO_DEFAULT_COVER)));
        }
        progressBar = new ProgressBar(context);
        RelativeLayout.LayoutParams progressBarLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        progressBarLp.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(progressBarLp);
        vadContainer.addView(progressBar);
        videoView.setMeasurelistener(new FFTVideoView.OnMeasureListener() {
            @Override
            public void measureListener(int w, int h) {
                RelativeLayout.LayoutParams imagelp = (RelativeLayout.LayoutParams) imageAdView.getLayoutParams();
                imagelp.width = w;
                imagelp.height = h;
            }
        });
        vadContainer.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                ViewParent parent = vadContainer.getParent();
                if(parent != null ){
                    while (parent instanceof View) {
                        if (((View) parent).getVisibility() != View.VISIBLE) {
                            return;
                        }
                        parent = parent.getParent();
                        if (parent != null && !(parent instanceof View)) {
                            break;
                        }
                    }
                    if (!isPlayed && isloaded) {
                        show();
                        isPlayed = true;
                    }
                }
            }
        });
        this.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (volumeOff.get()) {
                    mp.setVolume(0f, 0f);
                }
                mediaPlayer = mp;
                mediaPlayer.setLooping(false);
                if(isPlayed){
                    mp.start();
                }
                progressBar.setVisibility(View.GONE);
                imageAdView.setVisibility(View.GONE);
                //adContainer.addView(vadContainer);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer = null;
                if(listener != null){
                    listener.onCompletion();
                }
                vcmReport();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e(TAG, String.format("what: %s, extra: %s", what, extra));
                return true;
            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if(what==MediaPlayer.MEDIA_INFO_BUFFERING_START ){
                    progressBar.setVisibility(View.VISIBLE);
                }else if(what==MediaPlayer.MEDIA_INFO_BUFFERING_END ){
                    progressBar.setVisibility(View.GONE);
                }
                return false;
            }
        });
        if (!isPreload) {
            videoView.setPathUrl(videoAdData.getVideo());
            videoView.setVideoURI(Uri.parse(videoAdData.getVideo()));
        }
        //adContainer.addView(vadContainer);
    }

    private void fetchImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String imgUrl = videoAdData.getImg();
                String cacheKey = Md5Util.encode(imgUrl);
                //adSource.setText(natiAd.getSource());
                Bitmap bitmap = tryFromCache(cacheKey);
                try {
                if (bitmap == null) {
                    URL url = new URL(imgUrl);
                    URLConnection conn = null;
                    conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    showImage(bitmap);
                    doCache(bitmap, cacheKey);
                } else {
                    showImage(bitmap);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void showImage(final Bitmap bitmap) {
        if (bitmap != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Activity activity = (Activity) context;
                        imageAdView.setImageBitmap(bitmap);
                        //adView.setAdjustViewBounds(true);
                        imageAdView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageAdView.setVisibility(View.VISIBLE);
                    } catch (Throwable e) {
                    }
                }
            });
        }
    }
    private String getCloseIcon(Context context) {
        int dpi = SizeUtil.dpi((Activity) context);
        if (dpi > 320) {
            return Constants.CLOSE_ICON_ASSETS_URL_LARGE;
        }
        return Constants.CLOSE_ICON_ASSETS_URL;
    }


    public void show() {
        if (isPreload) {
            String localFile = readCacheVideoFile(videoAdData.getVideo());
            if (localFile == null) {
                videoView.setPathUrl(videoAdData.getVideo());
                videoView.setVideoURI(Uri.parse(videoAdData.getVideo()));
            } else {
                videoView.setVideoPath(localFile);
            }
        }
        if (!videoView.isPlaying()) {
            videoView.requestFocus();
            if(listener != null){
                listener.onStart();
            }
            vasmReport();
            videoView.start();
        }
        //adContainer.addView(videoView);
    }

    public void destroy() {
        Log.i(TAG,"videoView stop");
        videoView.stopPlayback();
        File cacheVideoFile = new File(context.getExternalCacheDir().getAbsolutePath()+"/videocache");
        if (cacheVideoFile.exists()) {
            File[] files = cacheVideoFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if(!f.isDirectory()){
                    f.delete();
                }
            }
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

    private void vasmReport(){
        List<String> vasms = videoAdData.getVasm();
        if(vasms != null && vasms.size()>0){
            for (String vasm : vasms) {
                ClickService.getInstance().dlmUrl(vasm);
            }
        }
    }
    private void vcmReport(){
        List<String> vcms = videoAdData.getVcm();
        if(vcms != null && vcms.size()>0){
            for (String vcm : vcms) {
                ClickService.getInstance().dlmUrl(vcm);
            }
        }
    }
}
