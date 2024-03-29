package com.fftime.ffmob.aggregation.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAppDownloadInfo;
import com.bytedance.sdk.openadsdk.TTGlobalAppDownloadListener;
import com.fftime.ffmob.util.StringUtils;


/**
 * 实现TTGlobalAppDownloadListener接口，实现监听SDK内部下载进度状态回调
 * 如果你不允许SDK内部弹出Notification,可以在此回调中自如弹出Notification
 */

@SuppressWarnings("WeakerAccess")
public class AppDownloadStatusListener implements TTGlobalAppDownloadListener {
    public static final int DOWNLOAD_STATUS_ACTIVE = 1;
    public static final int DOWNLOAD_STATUS_WATING = 2;
    public static final int DOWNLOAD_STATUS_FINISH = 3;
    public static final int DOWNLOAD_STATUS_DELETE = 4;
    public static final int DOWNLOAD_STATUS_FAILED = 5;

    private final Context mContext;

    public AppDownloadStatusListener(Context context) {
        mContext = context.getApplicationContext();
    }

    @Override
    public void onDownloadActive(TTAppDownloadInfo info) {
//        updateNotification(DOWNLOAD_STATUS_ACTIVE, info);
        Log.d("TTGlobalDownload", "下载中----" + info.getAppName() + "---" + getDownloadPercent(info) + "%");
    }

    @Override
    public void onDownloadPaused(TTAppDownloadInfo info) {
//        updateNotification(DOWNLOAD_STATUS_WATING, info);
        Log.d("TTGlobalDownload", "暂停----" + info.getAppName() + "---" + getDownloadPercent(info) + "%");


    }

    @Override
    public void onDownloadFinished(TTAppDownloadInfo info) {
//        updateNotification(DOWNLOAD_STATUS_FINISH, info);
        Log.d("TTGlobalDownload", "下载完成----" + info.getAppName() + "---" + getDownloadPercent(info) + "%" + "  file: " + info.getFileName());

    }

    @Override
    public void onInstalled(String pkgName, String appName, long id, int status) {
        Log.d("TTGlobalDownload", "安装完成----" + "pkgName: " + pkgName);
    }

    @Override
    public void onDownloadFailed(TTAppDownloadInfo info) {
//        updateNotification(DOWNLOAD_STATUS_FINISH, info);
    }

    private int getDownloadPercent(TTAppDownloadInfo info) {
        if (info == null) {
            return 0;
        }
        double percentD = 0D;
        try {
            percentD = (double) info.getCurrBytes() / (double) info.getTotalBytes();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        int percent = (int) (percentD * 100);
        if (percent < 0) {
            percent = 0;
        }
        return percent;
    }


    @SuppressWarnings({"deprecation", "unused"})
    private void updateNotification(int type, TTAppDownloadInfo info) {
        if (info == null) {
            return;
        }

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(
                mContext);

        int smallIcon = android.R.drawable.stat_sys_warning;
        switch (type) {
            case DOWNLOAD_STATUS_ACTIVE:
                smallIcon = android.R.drawable.stat_sys_download;
                break;
            case DOWNLOAD_STATUS_WATING:
                smallIcon = android.R.drawable.stat_sys_warning;
                break;
            case DOWNLOAD_STATUS_FINISH:
                smallIcon = android.R.drawable.stat_sys_download_done;
                break;
        }

        int contentAction = DOWNLOAD_STATUS_DELETE;
        int action = 1;
        String downloadSize = "";
        String downloadStatus = "";
        String buttonText = "";
        switch (type) {
            case DOWNLOAD_STATUS_ACTIVE:
                downloadSize = StringUtils.bytesToHuman(info.getCurrBytes()) + "/" + StringUtils.bytesToHuman(info.getTotalBytes());
                downloadStatus = "正在下载";
                buttonText = "暂停";

                action = DOWNLOAD_STATUS_WATING;
                break;
            case DOWNLOAD_STATUS_WATING:
                downloadSize = StringUtils.bytesToHuman(info.getCurrBytes()) + "/" + StringUtils.bytesToHuman(info.getTotalBytes());
                downloadStatus = "暂停";
                buttonText = "继续";

                action = DOWNLOAD_STATUS_ACTIVE;
                break;
            case DOWNLOAD_STATUS_FINISH:
                downloadSize = StringUtils.bytesToHuman(info.getTotalBytes());
                downloadStatus = "点击安装";
                buttonText = "安装";

                action = DOWNLOAD_STATUS_FINISH;
                contentAction = DOWNLOAD_STATUS_FINISH;
                break;
            case DOWNLOAD_STATUS_FAILED:
                downloadSize = StringUtils.bytesToHuman(info.getTotalBytes());
                downloadStatus = "下载失败";
                buttonText = "重新下载";

                action = DOWNLOAD_STATUS_FINISH;
                contentAction = DOWNLOAD_STATUS_FINISH;
                break;
        }

        //删除通知栏 intent
        Intent hintIntent = new Intent(mContext, AppDownloadService.class);
        hintIntent.putExtra("action", DOWNLOAD_STATUS_DELETE);
        hintIntent.putExtra("id", info.getId());
        notifyBuilder.setDeleteIntent(PendingIntent.getService(mContext, (int) SystemClock.uptimeMillis(), hintIntent, 0));


        Intent contentIntent = new Intent(mContext, AppDownloadService.class);
        contentIntent.putExtra("action", contentAction);
        contentIntent.putExtra("id", info.getId());
        contentIntent.putExtra("internalStatusKey", info.getInternalStatusKey());
        notifyBuilder.setContentIntent(PendingIntent.getService(mContext, (int) SystemClock.uptimeMillis(), contentIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        notifyBuilder.setAutoCancel(true);

        buttonText = buttonText + info.getId();
        downloadStatus = downloadStatus + info.getId();

        Intent intent = new Intent(mContext, AppDownloadService.class);
        intent.putExtra("action", action);
        intent.putExtra("id", info.getId());
        intent.putExtra("internalStatusKey", info.getInternalStatusKey());
        notifyBuilder.setAutoCancel(true);
        notifyBuilder.setSmallIcon(smallIcon);

        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(info.getId() + "", (int) info.getId(), notifyBuilder.build());
        }

    }

}
