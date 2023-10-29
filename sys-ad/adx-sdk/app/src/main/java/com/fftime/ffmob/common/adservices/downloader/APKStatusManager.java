package com.fftime.ffmob.common.adservices.downloader;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fftime.ffmob.common.adservices.downloader.APKManager.APKStatus;

public class APKStatusManager {
  private Map<String, APKStatus> statusCache =
      new ConcurrentHashMap<String, APKManager.APKStatus>();
  private final InnerStatusListener listener;


  public APKStatusManager(InnerStatusListener listener) {
    this.listener = listener;
  }


  public APKStatus getAPKStatus(Context context, String pkgName) {
    APKStatus rt = statusCache.get(pkgName);
    if (rt == null) {
      rt = initStatus(context, pkgName);
      statusCache.put(pkgName, rt);
      listener.onStatusUpdate(pkgName, APKStatus.NONE, rt);
    }
    return rt == null ? APKStatus.NONE : rt;
  }



  public void startDownloading(String pkgName) {
    APKStatus oldStatus = this.statusCache.get(pkgName);
    oldStatus = oldStatus == null ? APKStatus.NONE : oldStatus;
    this.statusCache.put(pkgName, APKStatus.DOWNLOADING);
    listener.onStatusUpdate(pkgName, oldStatus, APKStatus.DOWNLOADING);
  }

  public void failToDown(String pkgName) {
    APKStatus oldStatus = this.statusCache.get(pkgName);
    oldStatus = oldStatus == null ? APKStatus.NONE : oldStatus;
    this.statusCache.put(pkgName, APKStatus.DOWNFAILED);
    listener.onStatusUpdate(pkgName, oldStatus, APKStatus.DOWNFAILED);
  }

  public void succToDown(String pkgName) {
    APKStatus oldStatus = this.statusCache.get(pkgName);
    oldStatus = oldStatus == null ? APKStatus.NONE : oldStatus;
    this.statusCache.put(pkgName, APKStatus.DOWNLOADED);
    listener.onStatusUpdate(pkgName, oldStatus, APKStatus.DOWNLOADED);
  }

  public void installed(String pkgName) {
    APKStatus oldStatus = this.statusCache.get(pkgName);
    oldStatus = oldStatus == null ? APKStatus.NONE : oldStatus;
    this.statusCache.put(pkgName, APKStatus.INSTALLED);
    listener.onStatusUpdate(pkgName, oldStatus, APKStatus.INSTALLED);
  }

  private APKStatus initStatus(Context context, String pkgName) {
    if (isInstalled(context, pkgName)) {
      return APKStatus.INSTALLED;
    }
    if (isDownLoaded(context, pkgName)) {
      return APKStatus.DOWNLOADED;
    }
    return APKStatus.NONE;
  }

  boolean isInstalled(Context context, String pkgName) {
    PackageManager pm = context.getPackageManager();
    try {
      PackageInfo pinfo = pm.getPackageInfo(pkgName, PackageManager.GET_ACTIVITIES);
      return pinfo != null;
    } catch (Throwable e) {
      return false;
    } finally {}
  }

  private boolean isDownLoaded(Context context, String pkgName) {
    File apkFile = APKFileUtil.getFinalFile(context, pkgName);
    if (apkFile != null && apkFile.exists()) {
      PackageManager pm = context.getPackageManager();
      PackageInfo pkgInfo =
          pm.getPackageArchiveInfo(apkFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
      return pkgInfo != null;
    } else {
      return false;
    }
  }

  public void resetStatus(String pkgName) {
    this.statusCache.remove(pkgName);
  }
}
