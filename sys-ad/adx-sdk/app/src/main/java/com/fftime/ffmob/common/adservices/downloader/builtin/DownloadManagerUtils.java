package com.fftime.ffmob.common.adservices.downloader.builtin;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.File;

/**
 * 下载管理器工具类
 */
public class DownloadManagerUtils {

    public static boolean isDownloadManagerAvailable(Context context) {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_USER
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED
                    || context.getPackageManager()
                    .getApplicationEnabledSetting(
                            "com.android.providers.downloads") == context
                    .getPackageManager().COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {

                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean downloadManagerAvailable(Context context) {
        int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {

            String pkgName = "com.android.providers.downloads";
            Intent intent = null;
            try {
                intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:$packageName"));
                context.startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                context.startActivity(intent);
            }
            return false;
        }

        return true;
    }

    public static boolean isValidPkg(PackageManager pkgManager, File file) {
        try {
            PackageInfo pkgInfo = pkgManager.getPackageArchiveInfo(file.getAbsolutePath(),
                    PackageManager.GET_ACTIVITIES);
            if (pkgInfo != null) return true;
        } catch (Exception ex) {
            //Log.e("",ex);
        }
        return false;
    }
    public static String getAppName(PackageManager pkgManager, File file) {
        try {
            PackageInfo pkgInfo = pkgManager.getPackageArchiveInfo(file.getAbsolutePath(),
                    PackageManager.GET_ACTIVITIES);
            if (pkgInfo != null)
                return pkgInfo.packageName;
        } catch (Exception ex) {
            //Log.e("",ex);
        }
        return null;
    }
}
