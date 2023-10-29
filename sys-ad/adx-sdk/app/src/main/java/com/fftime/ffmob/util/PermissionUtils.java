package com.fftime.ffmob.util;

//安卓权限工具类，处理安卓6.0之后的动态权限申请

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtils {

    @TargetApi(23)
    public static void getPersimmions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();

            addPermission(activity, permissions, Manifest.permission.READ_EXTERNAL_STORAGE);
            addPermission(activity, permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            addPermission(activity, permissions, Manifest.permission.READ_PHONE_STATE);
            addPermission(activity, permissions, Manifest.permission.CALL_PHONE);
            //addPermission(activity,permissions,Manifest.permission.ACCESS_NETWORK_STATE);
            //addPermission(activity,permissions,Manifest.permission.ACCESS_WIFI_STATE);
            addPermission(activity, permissions, Manifest.permission.ACCESS_COARSE_LOCATION);
            addPermission(activity, permissions, Manifest.permission.ACCESS_FINE_LOCATION);
            addPermission(activity, permissions, Manifest.permission.REQUEST_INSTALL_PACKAGES);

            if (permissions.size() > 0) {
                activity.requestPermissions(permissions.toArray(new String[permissions.size()]), 1024);
            }

        }
    }

    @TargetApi(23)
    private static boolean addPermission(Activity activity, List<String> permissionsList, String permission) {
        // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
        if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED) {
            if (activity.shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        //Context context = IApplication.getContext();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || context.getPackageManager().checkPermission(permission,
                context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }else{
            ActivityCompat.requestPermissions((Activity) context, new String[]{permission},1);
        }
        return false;
    }
}
