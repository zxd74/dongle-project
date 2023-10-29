package com.fftime.ffmob.demos;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fftime.ffmob.R;
import com.fftime.ffmob.SdkSettings;
import com.fftime.ffmob.aggregation.api.AggrSplashAD;
import com.fftime.ffmob.aggregation.base.listener.SplashAdListener;
import com.fftime.ffmob.aggregation.fftime.splash.FFTSplashAD;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.fftime.ffmob.splash.ISplashAdListener;
import com.fftime.ffmob.splash.SplashAD;
import com.fftime.ffmob.splash.SplashADListener;
import com.fftime.ffmob.util.PermissionUtils;
import com.fftime.ffmob.video.VideoPlayListener;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends Activity implements ISplashAdListener, VideoPlayListener {

    private static final String appID = "EzqIze";
    private static final String posID = "7b2aia";

    private final String TAG = getClass().getSimpleName();

    @Override
    @SuppressWarnings("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SdkSettings.getInstance().channel("10021").apiDomain("api-exchange-qa.cread.com");
        PermissionUtils.getPersimmions(this);

        setContentView(R.layout.activity_splash);
        RelativeLayout adContainer = findViewById(R.id.container_ad);

        SplashAD splashAD = new SplashAD(this, appID, posID, adContainer, this);
        splashAD.fetchAndShow();
    }

    @Override
    public void onAdShow() {
    }

    @Override
    public void onSplashFinish() {
        Log.i("SplashAD", "SplashADFinish");
        jump();
        this.finish();
    }

    @Override
    public void onSplashFail() {
        Log.i("SplashAD", "SplashADFail");
        jump();
        this.finish();
    }

    @Override
    public void onStartPlay() {

    }

    @Override
    public void onCompletion() {
        Toast.makeText(this, "视屏播放完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdClick() {

    }

    /**
     * ----------非常重要----------
     * <p>
     * Android6.0以上的权限适配简单示例：
     * <p>
     * 如果targetSDKVersion >= 23，那么必须要申请到所需要的权限，再调用广点通SDK，否则广点通SDK不会工作。
     * <p>
     * Demo代码里是一个基本的权限申请示例，请开发者根据自己的场景合理地编写这部分代码来实现权限申请。
     * 注意：下面的`checkSelfPermission`和`requestPermissions`方法都是在Android6.0的SDK中增加的API，如果您的App还没有适配到Android6.0以上，则不需要调用这些方法，直接调用广点通SDK即可。
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        // 权限都已经有了，那么直接调用SDK
        if (lackedPermission.size() == 0) {
            //goNext();
        } else {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }

    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                Log.i("SplashActivity", "");
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1024 && hasAllPermissionsGranted(grantResults)) {
            //goNext();
            Toast.makeText(this, "权限申请通过", Toast.LENGTH_LONG).show();
        } else {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("SplashActivity", "权限：" + permissions[i] + "申请结果：" + grantResults[i]);
            }
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
//            Toast.makeText(this, "应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            intent.setData(Uri.parse("package:" + getPackageName()));
//            startActivity(intent);
//            finish();
        }
    }


    private void jump() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
