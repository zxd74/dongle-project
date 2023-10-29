package com.fftime.ffmob.common.adservices;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.fftime.ffmob.SdkSettings;
import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetClient.Priority;
import com.fftime.ffmob.common.network.NetRequest.Method;
import com.fftime.ffmob.common.network.PlainADNetResponse;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.common.network.PlainNetRequest.CallBack;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.util.Constants;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoadService {
    private static final String TAG = "LoadService";
    private static final String LOAD_CGI = Constants.LOADAD_CGI;

    private static class HolderClass {
        static final LoadService instance = new LoadService();
    }

    public static final LoadService getInstance() {
        return HolderClass.instance;
    }

    public void loadAD(final LoadRequest request, final LoadCallback callback) {
        PlainNetRequest netReq = new PlainNetRequest(LOAD_CGI, Method.GET, null, new CallBack() {

            @Override
            public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                String respStr;
                try {
                    respStr = resp.getContentAsString();
                    final JSONObject respObj = new JSONObject(respStr);
                    new Handler(Looper.getMainLooper()).post(() -> callback.onSucc(respObj));
                } catch (Throwable e) {
                    FFTLoger.e(TAG, "加载广告失败", e);
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError());
                }
            }

            @Override
            public void onError(Exception e) {
                FFTLoger.e(TAG, "加载广告失败", e);
                new Handler(Looper.getMainLooper()).post(() -> callback.onError());
            }
        });
        netReq.addParam("appId", request.getAppId()).addParam("pid", request.getPosId())
//        .addParam("pw", String.valueOf(request.getPosW()))
//        .addParam("ph", String.valueOf(request.getPosH()))
                .addParam("count", String.valueOf(request.getCount()));

        Map<String, Object> ext = new HashMap<String, Object>();
        ext.put("mf", StatusManager.getInstance().getDeviceStatus().manufactory);
        ext.put("device", StatusManager.getInstance().getDeviceStatus().model);
        ext.put("did", StatusManager.getInstance().getDeviceStatus().getDid());
        ext.put("cw", StatusManager.getInstance().getDeviceStatus().getDeviceWidth());
        ext.put("ch", StatusManager.getInstance().getDeviceStatus().getDeviceHeight());
        ext.put("density", StatusManager.getInstance().getDeviceStatus().getDeviceDensity());
        ext.put("lng", StatusManager.getInstance().getDeviceStatus().getLng());
        ext.put("lat", StatusManager.getInstance().getDeviceStatus().getLat());

        ext.put("appid", StatusManager.getInstance().getAppStatus().getAppId());
        ext.put("appv", StatusManager.getInstance().getAppStatus().getAppVersion());
        ext.put("conn", StatusManager.getInstance().getDeviceStatus().getConnectType());
        ext.put("carrier", StatusManager.getInstance().getDeviceStatus().getCarrier().getValue());
        ext.put("lag", StatusManager.getInstance().getDeviceStatus().getLanguage());
        ext.put("sdkv", Constants.VERSION_CODE);
        ext.put("os", 2);
        ext.put("ip", StatusManager.getInstance().getDeviceStatus().getIp());
        ext.put("dt", StatusManager.getInstance().getDeviceStatus().getDeviceType());
        ext.put("ua", StatusManager.getInstance().getDeviceStatus().getUserAgent());
        ext.put("osv", StatusManager.getInstance().getDeviceStatus().getOsv());
        ext.put("channel", SdkSettings.getInstance().getChannel());
        ext.put("oaid", SdkSettings.getInstance().getOaid());
        ext.put("tmids", StatusManager.getInstance().getAppStatus().getAppExtParam(request.getPosId() + "_tmids"));
        ext.put("appn", StatusManager.getInstance().getAppStatus().getAppName());
        ext.put("adid", StatusManager.getInstance().getDeviceStatus().getAndoidId());
        ext.put("mac", StatusManager.getInstance().getDeviceStatus().getMac());


        try {
            if (request.getParameters() != null) {
                ext.putAll(request.getParameters());
            }
            ext.putAll(StatusManager.getInstance().getAppStatus().getAppExtParams());
        } catch (Exception ex) {
        }

        try {
            String adt = StatusManager.getInstance().getAppStatus().getAppExtParam("adt");
            if(!TextUtils.isEmpty(adt)){
                netReq.addParam("adt",adt);
            }
            netReq.addParam("ext", URLEncoder.encode(new JSONObject(ext).toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            FFTLoger.w(TAG, "", e1);
        }
        //NetClient.getInstance().syncExecute(netReq);
        NetClient.getInstance().excute(netReq, Priority.High);
    }
}
