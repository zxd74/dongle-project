package com.fftime.ffmob.common.adservices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.adservices.downloader.APKDownTask;
import com.fftime.ffmob.common.adservices.downloader.APKManager;
import com.fftime.ffmob.common.adservices.downloader.APKManager.APKStatus;
import com.fftime.ffmob.common.adservices.downloader.APKParas;
import com.fftime.ffmob.common.adservices.downloader.builtin.AppInfo;
import com.fftime.ffmob.common.adservices.downloader.builtin.BuiltinApkDownloader;
import com.fftime.ffmob.common.adservices.downloader.builtin.DownloadManagerUtils;
import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetRequest;
import com.fftime.ffmob.common.network.PlainADNetResponse;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.common.webview.BuiltinWebBrowserActivity;
import com.fftime.ffmob.model.GdtDlInfo;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.model.SdkMacros;
import com.fftime.ffmob.nativead.ClkEventData;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.HandlerUtils;
import com.fftime.ffmob.util.PermissionUtils;
import com.fftime.ffmob.util.PhoneFormatCheckUtils;
import com.fftime.ffmob.util.Ping;
import com.fftime.ffmob.util.StringUtil;
import com.fftime.ffmob.util.URLUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class ClickService {
    private static final String TAG = "ClickService";

    public void dlmUrl(String url){
        String dlm = url.replaceAll(Constants.PATTERN_MACRO, "");
        String time = System.currentTimeMillis() + "";
        dlm = dlm.replace(SdkMacros.EVNET_TIME,time);
        NetClient.getInstance().excute(new PlainNetRequest(dlm, NetRequest.Method.GET, null, null),
                NetClient.Priority.High);
    }

    public void dlm(String[] dlmArray) {
        for (int i = 0; i < dlmArray.length; i++) {
            dlmUrl(dlmArray[i]);
        }
    }

    private static final class Holder {
        private static final ClickService INSTANCE = new ClickService();
    }

    public static final ClickService getInstance() {
        return Holder.INSTANCE;
    }

    private ClickService() {

    }


    public void cm(JSONArray cmList) {
        for (int i = 0; i < cmList.length(); i++) {
            String clickMonitorUrl = cmList.optString(i);
            dlmUrl(clickMonitorUrl);
        }
    }

    public void dealClick(final Context context, JSONObject adInfo) {
        if (adInfo != null) {
            JSONArray cmList = adInfo.optJSONArray("clk");
            cm(cmList, (Activity) context);
            String clickUrl = adInfo.optString("ldp");
            int adType = adInfo.optInt("adType");
            int cid = adInfo.optInt("cid");
            JSONObject appInfo = adInfo.optJSONObject("appInfo");
            ClkEventData clkEventData = (ClkEventData) ((Activity)context).getIntent().getSerializableExtra(Constants.INTENT_VAR_CLK_EVENT_DATA);
            if(clkEventData != null){
                clickUrl = SdkMacros.replaceClkEventMacros(clickUrl, clkEventData);
            }
            if(adType == 5 && !TextUtils.isEmpty(clickUrl)){
                cmPhone(context,clickUrl,adInfo.optString("fallback"),adInfo.optInt("phoneType"),adInfo);
            }else if (appInfo != null && appInfo.length() > 0 && (appInfo.has("pkgName") || appInfo.has("appName"))) {
                dealAPPADClick(context, clickUrl, cid, appInfo, adInfo.optString("fallback"), adInfo);
            } else {
                dealLinkADClick(context, clickUrl, adInfo.optString("fallback"), adInfo);
            }
        } else {
            FFTLoger.e(TAG, "ADInfo not exist in click param");
        }
    }


    private void cmPhone(Context context, String phone,String fallback,int phoneType,JSONObject adInfo){
        if(PermissionUtils.checkPermission(context, Manifest.permission.CALL_PHONE)){
            if(phoneType == 1){
                ApiService.getInstance().getCallPhone(phone, new ApiService.GetPhoneCallBack() {
                    @Override
                    public void onSucc(String json) {
                        try {
                            JSONObject object = new JSONObject(json);
                            if(object.optInt("code") == 0){
                                String callPhone = object.optString("phone");
                                callPhone(callPhone, context);
                            }else {
                                dealLinkADClick(context,fallback,null,adInfo);
                            }
                        } catch (JSONException e) {
                            dealLinkADClick(context,fallback,null,adInfo);
                        }
                    }

                    @Override
                    public void OnFail() {
                        dealLinkADClick(context,fallback,null,adInfo);
                    }
                });
            }else {
                callPhone(phone,context);
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void callPhone(String callPhone, Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + callPhone);
        intent.setData(data);
        context.startActivity(intent);
    }

    private void dealAPPADClick(Context context, String clickUrl, int cid, JSONObject appInfo, String fallback, JSONObject adInfo) {
        if (isAcceptedScheme(clickUrl)) {
            dealAPPADClick(context, clickUrl, cid, appInfo, adInfo);
        } else {
            //如果落地页是deeplink, 则先尝试打开deeplink, 如果deeplink打开失败，则尝试利用fallback地址直接下载
            final Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(clickUrl));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            if (isInstalledApp(context, intent)) {
                sendMonitorEvent(adInfo.optJSONArray("dpem"));
                try {
                    context.startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(context, "您所打开的第三方App未安装", Toast.LENGTH_SHORT).show();
                    if (!StringUtil.isEmpty(fallback) && isAcceptedScheme(fallback)) {
                        dealAPPADClick(context, fallback, cid, appInfo, adInfo);
                    }
                }
                HandlerUtils.getUiThreadHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAppInBackground(context)) {
                            sendMonitorEvent(adInfo.optJSONArray("dpsm"));
                        } else {
                            sendMonitorEvent(adInfo.optJSONArray("dpfm"));
                        }
                    }
                }, adInfo.optInt("dpts")*1000);
            } else {
                sendMonitorEvent(adInfo.optJSONArray("dpnm"));
                String dpn = adInfo.optString("dpn");
                if(!TextUtils.isEmpty(dpn) && isAcceptedScheme(dpn)){
                    int dpnType = adInfo.optInt("dpnType",0);
                    if(dpnType == 1){ //web浏览
                        dealLinkADClick(context,dpn,fallback,adInfo);
                    }else if(dpnType == 2){ //download下载
                        dealAPPADClick(context,dpn,cid,appInfo,adInfo);
                    }
                }else {
                    // 没有安装并且没有头条的dpn的情况
                    sendMonitorEvent(adInfo.optJSONArray("dpfm"));
                    Toast.makeText(context, "您所打开的第三方App未安装", Toast.LENGTH_SHORT).show();
                    if (!StringUtil.isEmpty(fallback) && isAcceptedScheme(fallback)) {
                        dealAPPADClick(context, fallback, cid, appInfo, adInfo);
                    }
                }

            }
        }
    }

    private void cm(JSONArray cmList, Activity activity) {
        ClkEventData clkEventData = (ClkEventData) activity.getIntent().getSerializableExtra(Constants.INTENT_VAR_CLK_EVENT_DATA);
        if (clkEventData == null) {
            cm(cmList);
        } else {
            for (int i = 0; i < cmList.length(); i++) {
                String cm = cmList.optString(i);
                cm = SdkMacros.replaceClkEventMacros(cm, clkEventData);
                dlmUrl(cm);
            }
        }
    }

    public void dealClickForVideo(final Context context, JSONObject adInfo) {
        if (adInfo != null) {
            String clickUrl = adInfo.optString("ldp");
            int cid = adInfo.optInt("cid");
            JSONObject appInfo = adInfo.optJSONObject("appInfo");
            if (appInfo != null && appInfo.length() > 0 && appInfo.has("pkgName")) {
                dealAPPADClick(context, clickUrl, cid, appInfo, adInfo);
            } else {
                dealLinkADClick(context, clickUrl, clickUrl, adInfo);
            }
        } else {
            FFTLoger.e(TAG, "ADInfo not exist in click param");
        }
    }

    private void dealAPPADClick(final Context context, final String clickURL, final int cid,
                                final JSONObject appInfo, final JSONObject adInfo) {
        final NatiAd natiAd = new NatiAd(adInfo);
        if (natiAd.isGdtAd()) {
            ApiService.getInstance().getGdtDlInfo(clickURL, new ApiService.GdtApiInfoCallback() {
                @Override
                public void onSucc(JSONObject data) {
                    final GdtDlInfo gdtDlInfo = new GdtDlInfo(data);
                    String clickid = gdtDlInfo.getClickid();

                    List<String> dlmList = natiAd.getDlm();
                    List<String> dlcmList = natiAd.getDlcm();

                    final JSONArray dlmArray = new JSONArray();
                    final JSONArray dlcmArray = new JSONArray();

                    if (dlmList != null && !dlmList.isEmpty()) {
                        for (String dlm : dlmList) {
                            dlmArray.put(dlm.replaceAll("__ACTION_ID__", "5").replaceAll("__CLICK_ID__", clickid));
                        }
                    }

                    if (dlcmList != null && !dlcmList.isEmpty()) {
                        for (String dlcm : dlcmList) {
                            dlcmArray.put(dlcm.replaceAll("__ACTION_ID__", "7").replaceAll("__CLICK_ID__", clickid));
                        }
                    }

                    final String pkgName = appInfo.optString("pkgName");
                    HandlerUtils.getUiThreadHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            if (DownloadManagerUtils.downloadManagerAvailable(context)) {
                                if (dlmArray.length() > 0) {
                                    sendMonitorEvent(dlmArray);
                                }
                                BuiltinApkDownloader.create(context, AppInfo.create(appInfo.optString("appName"),
                                        pkgName, gdtDlInfo.getDstlink(),adInfo.optJSONArray("dlcm"),adInfo.optJSONArray("dlfm"),adInfo.optJSONArray("installed")));

                            } else {
                                dealLinkADClick(context, gdtDlInfo.getDstlink(), null, adInfo);
                            }
                        }
                    });
                }

                @Override
                public void onFail(int code, String msg) {

                }
            });
        } else {
            final String pkgName = appInfo.optString("pkgName");
            if (DownloadManagerUtils.downloadManagerAvailable(context)) {
                BuiltinApkDownloader.create(context, AppInfo.create(appInfo.optString("appName"), pkgName, clickURL,adInfo.optJSONArray("dlcm"),adInfo.optJSONArray("dlfm"),adInfo.optJSONArray("installed")));
                sendMonitorEvent(adInfo.optJSONArray("dlm"));
            } else {
                dealLinkADClick(context, clickURL, null, adInfo);
            }
        }
    }

    public void sendMonitorEvent(JSONArray eventUrlList) {
        try {
            if (eventUrlList == null || eventUrlList.length() == 0) {
                return;
            }
            for (int i = 0; i < eventUrlList.length(); i++) {
                dlmUrl(eventUrlList.optString(i));
            }
        } catch (Exception ex) {
            Log.e(TAG, "发送广告监测事件异常", ex);
        }
    }

    private void dealLinkADClick(final Context context, final String url, String fallback, JSONObject adInfo) {
        if (isAcceptedScheme(url)) {
            //如果浏览器可以处理的协议，则交由内部浏览器处理
            Intent intent = new Intent(context, BuiltinWebBrowserActivity.class);
            intent.putExtra("url", url);
            NatiAd natiAd = new NatiAd(adInfo);
            intent.putExtra("dlm", natiAd.getDlm().toArray(new String[]{}));
            JSONArray dlcm = adInfo.optJSONArray("dlcm");
            if(dlcm != null && dlcm.length() >0 ){
                intent.putExtra("dlcm", dlcm.toString());
            }
            JSONArray installed = adInfo.optJSONArray("installed");
            if(installed != null && installed.length() >0 ){
                intent.putExtra("installed", installed.toString());
            }
            JSONArray dlfmArry = adInfo.optJSONArray("dlfm");
            if(dlfmArry != null && dlfmArry.length() >0 ){
                intent.putExtra("dlfm", dlfmArry.toString());
            }
            context.startActivity(intent);
        } else {
            final NatiAd natiAd = new NatiAd(adInfo);
            if (natiAd.isGdtAd()) {
                ApiService.getInstance().getGdtDlInfo(url, new ApiService.GdtApiInfoCallback() {
                    @Override
                    public void onSucc(JSONObject data) {
                        GdtDlInfo gdtDlInfo = new GdtDlInfo(data);
                        String clickid = gdtDlInfo.getClickid();

                        List<String> dpsmList = natiAd.getDpsm();
                        List<String> dpfmList = natiAd.getDpfm();

                        JSONArray dpsmArray = new JSONArray();
                        JSONArray dpfmArray = new JSONArray();

                        if (dpsmList != null && !dpsmList.isEmpty()) {
                            for (String dpsm : dpsmList) {
                                dpsmArray.put(dpsm.replaceAll("__ACTION_ID__", "138").replaceAll("__CLICK_ID__", clickid));
                            }
                        }

                        if (dpfmList != null && !dpfmList.isEmpty()) {
                            for (String dpfm : dpfmList) {
                                dpfmArray.put(dpfm.replaceAll("__ACTION_ID__", "137").replaceAll("__CLICK_ID__", clickid));
                            }
                        }

                        try {
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            context.startActivity(intent);
                            sendMonitorEvent(dpsmArray);
                        } catch (Throwable ex) {
                            sendMonitorEvent(dpfmArray);
                        }
                    }

                    @Override
                    public void onFail(int code, String msg) {

                    }
                });
            } else {  //走Deeplink
                final Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if (isInstalledApp(context, intent)) {
                    sendMonitorEvent(adInfo.optJSONArray("dpem"));
                    try {
                        context.startActivity(intent);
                    } catch (Exception ex) {
                        sendMonitorEvent(adInfo.optJSONArray("dpfm"));
                        if (!StringUtil.isEmpty(fallback) && isAcceptedScheme(fallback)) {
                            dealLinkADClick(context, fallback, null, adInfo);
                        }
                    }
                    HandlerUtils.getUiThreadHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isAppInBackground(context)) {
                                sendMonitorEvent(adInfo.optJSONArray("dpsm"));
                            } else {
                                sendMonitorEvent(adInfo.optJSONArray("dpfm"));
                            }
                        }
                    }, natiAd.getDpts()*1000);
                }else {
                    sendMonitorEvent(adInfo.optJSONArray("dpnm"));
                    String dpn = adInfo.optString("dpn");
                    if(!TextUtils.isEmpty(dpn) && isAcceptedScheme(dpn)){
                        int dpnType = adInfo.optInt("dpnType");
                        if(dpnType == 1){
                            dealLinkADClick(context, dpn, null, adInfo);
                        }else if(dpnType == 2){
                            if (DownloadManagerUtils.downloadManagerAvailable(context)) {
                                BuiltinApkDownloader.create(context, AppInfo.create(null, null, dpn,adInfo.optJSONArray("dlcm"),adInfo.optJSONArray("dlfm"),adInfo.optJSONArray("installed")));
                                sendMonitorEvent(adInfo.optJSONArray("dlm")); //开始下载打点
                            } else {
                                if (!StringUtil.isEmpty(fallback) && isAcceptedScheme(fallback)) {
                                    dealLinkADClick(context, fallback, null, adInfo);
                                }
                            }
                        }
                    }else {
                        if (!StringUtil.isEmpty(fallback) && isAcceptedScheme(fallback)) {
                            dealLinkADClick(context, fallback, null, adInfo);
                        }
                    }
                }
            }
        }
    }

    private boolean isAcceptedScheme(String url) {
        String lowerCaseUrl = url.toLowerCase();
        Matcher acceptedUrlSchemeMatcher = Constants.ACCEPTED_URI_SCHEME.matcher(lowerCaseUrl);
        if (acceptedUrlSchemeMatcher.matches()) {
            return true;
        }
        return false;
    }

    private boolean isInstalledApp(Context context,Intent intent){
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
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) { // Android5.0及以后的检测方法
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
