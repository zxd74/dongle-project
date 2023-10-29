package com.fftime.ffmob.common.view;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.VideoView;

//自定义VideoView, 支持全屏
public class FFTVideoView extends VideoView {
    //最终的视频资源宽度
    private int mVideoWidth=480;
    //最终视频资源高度
    private int mVideoHeight=480;

    //视频资源原始宽度
    private int videoRealW=1;
    //视频资源原始高度
    private int videoRealH=1;
    private String pathUrl;

    public void setMeasurelistener(OnMeasureListener measurelistener) {
        this.measurelistener = measurelistener;
    }

    private OnMeasureListener measurelistener;
    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    private boolean isFullscreen;

    public FFTVideoView(Context context) {
        super(context);
    }

    public FFTVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FFTVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FFTVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);

        if(height>width){
            //竖屏
            mVideoWidth=width;
            float r = 1.78f;
            mVideoHeight= (int) (mVideoWidth/r);
        }else {
            //横屏
            if(videoRealH>videoRealW){
                //如果视频资源是竖屏
                //宽度占满，高度保存比例
                mVideoHeight=height;
                float r=videoRealW/(float)videoRealH;
                mVideoWidth= (int) (mVideoHeight*r);
            }else {
                //如果视频资源是横屏
                //占满屏幕
                mVideoHeight=height;
                mVideoWidth=width;
            }
        }
        if(isFullscreen){
            //如果是全屏视频 则优先按照全屏视频进行展示
            setMeasuredDimension(width,height);
        }else{
            if(measurelistener != null){
                measurelistener.measureListener(mVideoWidth,mVideoHeight);
            }
            setMeasuredDimension(mVideoWidth, mVideoHeight);
        }

    }

    public interface OnMeasureListener{
        void measureListener(int w,int h);
    }
}
