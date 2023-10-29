package com.fftime.ffmob.common.spy;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetClient.Priority;
import com.fftime.ffmob.common.network.NetRequest.Method;
import com.fftime.ffmob.common.network.PlainADNetResponse;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.common.status.DeviceStatus;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.StringUtil;

public class Spy {
  private static final String TAG = "007";
  @SuppressWarnings("unused")
  private static final int TDay = 1000 * 3600 * 72;
  private static final int OneS = 10000;
  private static final int MINIMAL_CTIME = OneS;// 3Day
  private static final AtomicBoolean done = new AtomicBoolean(false);

  public static void work(final Context context) {
    SharedPreferences SP = context.getSharedPreferences("fft_cfg", Context.MODE_PRIVATE);
    long lastCheckTime = SP.getLong("sctime", 0);
    if (System.currentTimeMillis() - lastCheckTime < MINIMAL_CTIME) {
      return;
    }
    if (done.compareAndSet(false, true)) {
      SP.edit().putLong("sctime", System.currentTimeMillis()).commit();
      PlainNetRequest req =
          new PlainNetRequest(Constants.SPY_REQ_URL, Method.GET, null,
              new PlainNetRequest.CallBack() {
                @Override
                public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                  dealWork(context, resp);
                }

                @Override
                public void onError(Exception e) {
                  if (Constants.DEBUG) FFTLoger.d(TAG, "error while get task", e);
                }
              });
      DeviceStatus DS = StatusManager.getInstance().getDeviceStatus();
      if (DS != null) {
        String did = DS.getDid();
        if (!StringUtil.isEmpty(did) && !(DeviceStatus.UNKNOWN_DEVICEID.equals(did))) {
          req.addParam("did", did);
          //req.addParam("conn", String.valueOf(DS.getNetworkType().getConnValue()));
          req.addParam("carrier", DS.getCarrier().name());
          req.addParam("lastchecktime", String.valueOf(lastCheckTime));
          NetClient.getInstance().excute(req, Priority.High);
        } else {
          FFTLoger.d(TAG, "Unknown Device ID,CancelTask");
        }
      }
    }
  }

  @SuppressWarnings({"unchecked", "deprecation"})
  private static void dealWork(final Context context, PlainADNetResponse resp) {
    try {
      String taskStr;
      taskStr = resp.getContentAsString();
      if (!StringUtil.isEmpty(taskStr)) {
        JSONObject taskObj = new JSONObject(taskStr);
        int ret = -1;
        if ((ret = taskObj.optInt("ret", -1)) != 0) {
          if (Constants.DEBUG) {
            FFTLoger.d(TAG, "error while handle task str,ret=" + ret);
          }
          return;
        }
        JSONObject dataObj = taskObj.optJSONObject("data");
        if (dataObj == null) {
          if (Constants.DEBUG) {
            FFTLoger.d(TAG, "error while handle task,data=null");
          }
          return;
        }
        Map<Object, Object> rtData = new HashMap<Object, Object>();
        Iterator<String> pkgs = dataObj.keys();
        PackageManager pm = context.getPackageManager();
        while (pkgs.hasNext()) {
          Map<String, Object> taskResult = new HashMap<String, Object>();
          taskResult.put("istd", 0);
          String pkg = pkgs.next();
          JSONObject taskDetail = dataObj.getJSONObject(pkg);
          if (Constants.DEBUG) {
            FFTLoger.d(TAG, "deal task,pkg=" + pkg + "" + taskDetail);
          }
          int id = taskDetail.optInt("id");
          int version = taskDetail.optInt("version");
          try {
            PackageInfo pi = pm.getPackageInfo(pkg, PackageManager.GET_ACTIVITIES);
            if (pi != null) {
              taskResult.put("istd", 1);
              taskResult.put("ov", pi.versionCode < version ? 1 : 0);
            }
          } catch (NameNotFoundException e) {}
          rtData.put(String.valueOf(id), taskResult);
        }

        PlainNetRequest netReq =
            new PlainNetRequest(Constants.SPY_RESP_URL, Method.GET, null, null);

        DeviceStatus DS = StatusManager.getInstance().getDeviceStatus();
        if (DS != null) {
          netReq.addParam("did", DS.getDid());
        }
        netReq.addParam("data", URLEncoder.encode(new JSONObject(rtData).toString()));
        NetClient.getInstance().excute(netReq, Priority.High);
      }
    } catch (IOException e) {
      if (Constants.DEBUG) FFTLoger.d(TAG, "error while retrive task str", e);
    } catch (JSONException e) {
      if (Constants.DEBUG) FFTLoger.d(TAG, "error while parse task str", e);
    }
  }
}
