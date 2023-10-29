package com.fftime.ffmob.video;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class FullscreenVideoADActivity extends Activity {
    static VideoAD videoAD;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(bundle);
        FullscreenVideoAD.setVideoActivity(this);
        setContentView(videoAD.getAdContainer());
        videoAD.showAD();
    }

    @Override
    protected void onResume() {
        Log.i("fullscreenVideoAD","activity onsume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i("====","onPause");
        //videoAD.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i("====","videoad activity destroy");
        try {
            videoAD.destroy();
            super.onDestroy();
            videoAD = null;
        }catch(Throwable ex){

        }
    }
}
