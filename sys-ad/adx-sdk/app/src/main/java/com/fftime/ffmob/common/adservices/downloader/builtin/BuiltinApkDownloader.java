package com.fftime.ffmob.common.adservices.downloader.builtin;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.fftime.ffmob.common.adservices.ClickService;
import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetRequest;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.Md5Util;
import com.fftime.ffmob.util.StringUtil;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 安卓apk下载管理器，采用安卓系统内置下载器进行下载
 *
 * @author weiping wang
 */
public final class BuiltinApkDownloader {
    private DownloadManager downloadManager;
    private final Context context;
    private long downloadId;
    private String pathstr;


    private final AppInfo appInfo;
    private static List<String> DOWNLOADING_PKGS = new CopyOnWriteArrayList<>();
    private static HashSet<String> INSTALL_PKGS = new HashSet<>();

    private BuiltinApkDownloader(Context context, AppInfo appInfo) {
        this.context = context.getApplicationContext();
        this.appInfo = appInfo;
        downloadApk(appInfo);
    }

    public static BuiltinApkDownloader create(Context context, AppInfo appInfo) {
        return new BuiltinApkDownloader(context, appInfo);
    }

    private void downloadApk(AppInfo appInfo) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addDataScheme("package");
        context.registerReceiver(receiver, filter);
        //创建下载任务
        if (StringUtil.isEmpty(appInfo.getPkgName())) {
            if (!StringUtil.isEmpty(appInfo.getAppName())) {
                appInfo.setPkgName(appInfo.getAppName());
            }
        }
        String fileName = null;
        String pkgId = Md5Util.encode(appInfo.getDownloadUrl());
        if(StringUtil.isEmpty(appInfo.getPkgName())){
            fileName = pkgId;
        }else {
            fileName = appInfo.getPkgName();
        }
        if(!fileName.endsWith(".apk")){
            fileName = fileName+".apk";
        }
        File file = new File(context.getExternalFilesDir("").getAbsolutePath(),
                "FFTDOWNLOAD" + File.separator + fileName);

        pathstr = file.getAbsolutePath();
        //如果下载完成且包不完整的话，则重新下载；如果有同样的包正在下载，则什么都不做直接返回
        if (file.exists()) {
            if (DOWNLOADING_PKGS.contains(pkgId)) {
                return;
            }
            //如果下载完成且包不完整的话，则重新下载
            if (DownloadManagerUtils.isValidPkg(context.getPackageManager(), file)) {
                installAPK();
                return;
            } else {
                Log.e("fftdm", "安装包不完整，重新下载");
            }
        }
        try {
            file.delete();
        } catch (Exception ex) {
        }
        Toast.makeText(context, "开始下载", Toast.LENGTH_LONG).show();
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(appInfo.getDownloadUrl()));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //移动网络情况下是否允许漫游
        //request.setAllowedOverRoaming(false);
        //在通知栏中显示，默认就是显示的
        request.setMimeType("application/vnd.android.package-archive");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("应用下载中 " + appInfo.getAppName() == null ? "" : appInfo.getAppName());
        if (appInfo.getAppName() != null) {
            request.setDescription(appInfo.getAppName());
        }
        request.setVisibleInDownloadsUi(true);

        //设置下载的路径
        //File file = new File(Environment.getExternalStorageDirectory(), "FFTDOWNLOAD" + File.separator + appInfo.getAppName());
        request.setDestinationUri(Uri.fromFile(file));
        //pathstr = file.getAbsolutePath();
        //获取DownloadManager
        if (downloadManager == null)
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        if (downloadManager != null) {
            downloadId = downloadManager.enqueue(request);
            DOWNLOADING_PKGS.add(pkgId);
        }

        context.registerReceiver(receiver,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        context.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));


    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("fftdm", "========" + intent.getAction());
            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                if(DOWNLOADING_PKGS.contains(Md5Util.encode(appInfo.getDownloadUrl()))){
                    DOWNLOADING_PKGS.remove(Md5Util.encode(appInfo.getDownloadUrl()));
                    checkStatus();
                }
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(viewDownloadIntent);
            }else if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                String packageName = intent.getDataString();
                if(packageName.equals("package:"+appInfo.getPkgName())){
                    if(INSTALL_PKGS.contains(appInfo.getPkgName())){
                        ClickService.getInstance().sendMonitorEvent(appInfo.getInstalledArray());
                        INSTALL_PKGS.remove(appInfo.getPkgName());
                    }
                }
            }
        }
    };

    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    ClickService.getInstance().sendMonitorEvent(appInfo.getDlcmArray());
                    //下载完成安装APK
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        boolean haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
                        if (haveInstallPermission) {
                            installAPK();
                        } else {
                            if (context instanceof Activity) {
                                //ToastUtil.makeText(MyApplication.getContext(), MyApplication.getContext().getString(R.string.string_install_unknow_apk_note), false);
                                startInstallPermissionSettingActivity();
                                return;
                            }
                        }
                    } else {
                        installAPK();
                    }
                    cursor.close();
                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    ClickService.getInstance().sendMonitorEvent(appInfo.getDlfmArray());
                    DOWNLOADING_PKGS.remove(Md5Util.encode(appInfo.getDownloadUrl()));
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    context.unregisterReceiver(receiver);
                    break;
            }
        }
    }

    private void installAPK() {
        setPermission(pathstr);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0以上要使用FileProvider
        File file = (new File(pathstr));
        if (!DownloadManagerUtils.isValidPkg(context.getPackageManager(), file)) {
            Log.w("fftdm", "不支持非apk包直接安装");
            return;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            String authority = context.getPackageName() + ".fileprovider";
            Uri apkUri = FileProvider.getUriForFile(context, authority, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        appInfo.setPkgName(DownloadManagerUtils.getAppName(context.getPackageManager(),file));
        INSTALL_PKGS.add(appInfo.getPkgName());
        context.startActivity(intent);
    }

    //修改文件权限
    private void setPermission(String absolutePath) {
        String command = "chmod " + "777" + " " + absolutePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
