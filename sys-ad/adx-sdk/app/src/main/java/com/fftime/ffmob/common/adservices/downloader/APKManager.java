package com.fftime.ffmob.common.adservices.downloader;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.util.SizeUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


public class APKManager implements InnerDownProgressListener, InnerStatusListener {
  public static final String NOTIFY_TAG = "FFT_APKDOWN_NOTIFY";
  private static final String TAG = "APKManager";
  private static final String PKG_ALL = "*";
  private static APKManager INSTANCE;
  private Map<String, Collection<WeakReference<APKStatusMonitor>>> monitors =
      new ConcurrentHashMap<String, Collection<WeakReference<APKStatusMonitor>>>();

  public static synchronized final APKManager getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new APKManager(context);
    }
    return INSTANCE;
  }

  public static enum APKStatus {
    NONE, //
    DOWNLOADING, //
    DOWNFAILED, //
    DOWNLOADED, //
    INSTALLED
  }

  private final Context context;
  private final APKStatusManager statusManager;
  private final APKDownloader downloader;
  private final FFTAPKInstaller installer;

  private APKManager(Context context) {
    this.context = context.getApplicationContext();
    this.statusManager = new APKStatusManager(this);
    this.downloader = new APKDownloader(statusManager, this);
    this.installer = new FFTAPKInstaller(statusManager);
  }


  public void addAPKDownloadTask(APKDownTask task) {
    this.downloader.addTask(task, context);
  }


  //
  public void startAPK(String pkgName) {
    PackageManager pm = context.getPackageManager();
    Intent startIntent = pm.getLaunchIntentForPackage(pkgName);
    if (startIntent != null) {
      context.startActivity(startIntent);
    }
  }

  // 尝试安装APK，如果APK文件不能被解析安装则需要重置StatusManager的状态
  public boolean installAPK(APKDownTask task) {
    return this.installer.startInstall(context, APKFileUtil.getFinalFile(context, task.pkgName),
        task);
  }

  public APKStatus getStatus(String pkgName) {
    return this.statusManager.getAPKStatus(context, pkgName);
  }

  public void resetStatus(String pkgName) {
    this.statusManager.resetStatus(pkgName);
  }


  @Override
  public void onStatusUpdate(String pkgName, APKStatus oldStatus, APKStatus newStatus) {
    FFTLoger.d(TAG, String.format("Status of'%s' update %s -> %s", pkgName, oldStatus, newStatus));
    notifyMonitors(pkgName, newStatus, null);
  }


  @Override
  public void onProgressUpdate(APKDownTask task, final DownProgress progress) {
    FFTNotificationBuilder builder = task.getNb(context);
    builder.setAutoCancel(false).setContentTitle("应用下载中");
    if (progress == null) {
      builder.setProgress(100, 0, true);
    } else {
      builder.setProgress(100, progress.getPercent(), false);
      if (progress.getCurrent() == progress.getTotal()) {
        Intent installIntent =
            this.installer.buildInstallIntent(APKFileUtil.getFinalFile(context, task.pkgName));
        PendingIntent contentIntent =
            PendingIntent.getActivity(context.getApplicationContext(), task.cid, installIntent, 0);
        builder.setContentIntent(contentIntent).setContentText("下载完成，点击打开");
      } else {
        builder.setContentText("已下载：" + progress.getPercentStr() + " 剩余："
            + SizeUtil.asH(progress.getTotal() - progress.getCurrent()));
      }
    }
    Notification notification = builder.build();
    if (notification != null) {
      getNotificationManager().notify(NOTIFY_TAG, task.cid, notification);
    }
    notifyMonitors(task.pkgName, APKStatus.DOWNLOADING, progress);
  }

  private NotificationManager nm;

  private NotificationManager getNotificationManager() {
    if (nm == null) {
      nm = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
    return nm;
  }

  public void addStatusMonitor(String pkgName, APKStatusMonitor monitor) {
    Collection<WeakReference<APKStatusMonitor>> c = this.monitors.get(pkgName);
    if (c == null) {
      c = new ArrayList<WeakReference<APKStatusMonitor>>();
    }
    this.monitors.put(pkgName, c);
    c.add(new WeakReference<APKStatusMonitor>(monitor));
  }

  public void rmStatusMonitor(String pkgName, APKStatusMonitor monitor) {
    Collection<WeakReference<APKStatusMonitor>> c = this.monitors.get(pkgName);
    if (c != null && !c.isEmpty()) {
      Iterator<WeakReference<APKStatusMonitor>> iter = c.iterator();
      while (iter.hasNext()) {
        if (iter.next().get() == monitor) {
          iter.remove();
        }
      }
      if (c.isEmpty()) {
        this.monitors.remove(pkgName);
      }
    }
  }

  public void addStatusMonitor(APKStatusMonitor monitor) {
    this.addStatusMonitor(PKG_ALL, monitor);
  }

  public void rmStatusMonitor(APKStatusMonitor monitor) {
    this.rmStatusMonitor(PKG_ALL, monitor);
  }


  private void notifyMonitors(String pkgName, APKStatus status, DownProgress progress) {
    Collection<WeakReference<APKStatusMonitor>> c = this.monitors.get(pkgName);
    if (c != null) {
      Iterator<WeakReference<APKStatusMonitor>> iter = c.iterator();
      APKStatusMonitor m = null;
      while (iter.hasNext()) {
        if ((m = iter.next().get()) == null) {
          iter.remove();
        } else {
          m.onAPKStatusUpdate(pkgName, status, progress);
        }
      }
      if (c.isEmpty()) {
        this.monitors.remove(pkgName);
      }
    }
    if (!PKG_ALL.equals(pkgName)) {
      notifyMonitors(PKG_ALL, status, progress);
    }
  }



}
