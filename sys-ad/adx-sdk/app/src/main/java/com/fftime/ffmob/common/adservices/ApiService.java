package com.fftime.ffmob.common.adservices;

import android.util.Log;

import com.fftime.ffmob.SdkSettings;
import com.fftime.ffmob.aggregation.ads.ADType;
import com.fftime.ffmob.aggregation.model.AdPlatform;
import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetRequest;
import com.fftime.ffmob.common.network.PlainADNetResponse;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String TAG = "ApiService";
    //包段
    private static final String PLATFORMS_CGI = Constants.FETCH_AD_PLATFORMS;

    private static class HolderClass {
        static final ApiService instance = new ApiService();
    }

    public static ApiService getInstance() {
        return ApiService.HolderClass.instance;
    }

    public void getSdkAdsByCPT(String appId, String posId, final GetSdkAdsCallback callback) {
        getSdkAds(appId, posId, Constants.SDK_ADTYPE_CPT, callback);
    }

    public void getSdkAdsByCPM(String appId, String posId, final GetSdkAdsCallback callback) {
        getSdkAds(appId, posId, Constants.SDK_ADTYPE_CPM, callback);
    }

    public void loadOrderAD(String appId, String posId, final GetAdCallback cb) {
        loadAdxAD(appId, posId, 1, cb);
    }

    public void loadDefaultAD(String appId, String posId, final GetAdCallback cb) {
        loadAdxAD(appId, posId, 3, cb);
    }

    public void getGdtDlInfo(String url, final GdtApiInfoCallback cb) {
        PlainNetRequest req = new PlainNetRequest(url, NetRequest.Method.GET, null, new PlainNetRequest.CallBack() {
            @Override
            public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                try {
                    String respBody = resp.getContentAsString();
                    JSONObject gdtDlRspJSON = new JSONObject(respBody);

                    int ret = gdtDlRspJSON.optInt("ret");
                    if (ret != 0) {
                        cb.onFail(ret,gdtDlRspJSON.optString("msg"));
                        return;
                    }

                    JSONObject gdtDlInfoJSON = gdtDlRspJSON.getJSONObject("data");
                    cb.onSucc(gdtDlInfoJSON);
                } catch (Throwable ex) {
                    cb.onFail(-1,ex.getMessage());
                }
            }

            @Override
            public void onError(Exception e) {
                cb.onFail(-1,e.getMessage());
            }
        });

        NetClient.getInstance().excute(req, NetClient.Priority.High,2);
    }

    public  void getCallPhone(String url,final GetPhoneCallBack callBack){
        PlainNetRequest request = new PlainNetRequest(url, NetRequest.Method.GET, null, new PlainNetRequest.CallBack() {
            @Override
            public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                try {
                    String respBody = resp.getContentAsString();
                    callBack.onSucc(respBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                callBack.OnFail();
            }
        });
        NetClient.getInstance().excute(request, NetClient.Priority.High,2);
    }

    //获取adx广告，adtype: 1-订单广告；2-rtb广告; 3-打底广告
    public void loadAdxAD(String appId, String posId, int adtype, final GetAdCallback cb) {
        LoadRequest loadOrderADReq = new LoadRequest(appId, posId, 0, 0, 1);
        loadOrderADReq.addParameter("adt", String.valueOf(adtype));

        LoadService.getInstance().loadAD(loadOrderADReq, new LoadCallback() {
            @Override
            public void onSucc(JSONObject data) {
                NatiAd natiAd = new NatiAd(data);
                cb.onSuccess(natiAd);
            }

            @Override
            public void onError() {
                cb.onFail();
            }
        });
    }

    public void getSdkAds(String appId, String posId, final int type, final GetSdkAdsCallback callback) {
        PlainNetRequest request = (PlainNetRequest) new PlainNetRequest(PLATFORMS_CGI, NetRequest.Method.GET, null,
                new PlainNetRequest.CallBack() {
                    @Override
                    public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                        try {
                            String responseBody = resp.getContentAsString();
                            JSONObject json = new JSONObject(responseBody);
                            int ret = json.optInt("ret");

                            if (ret == 0) {
                                List<AdPlatform> adPlatforms = new ArrayList<>();
                                JSONArray adpJSONList = json.optJSONArray("data");
                                for (int i = 0; i < adpJSONList.length(); i++) {
                                    JSONObject adpJSON = adpJSONList.getJSONObject(i);
                                    AdPlatform adp = new AdPlatform();
                                    adp.setAppId(adpJSON.optString("appId"));
                                    adp.setPosId(adpJSON.optString("posId"));
                                    adp.setId(adpJSON.optString("platformId"));
                                    ADType adType = ADType.valueOf(adpJSON.optInt("posType"));
                                    if (adType == null) continue;

                                    adPlatforms.add(adp);
                                }
                                callback.onApiResponse(adPlatforms);
                            } else {
                                Log.e(TAG, "没有符合条件的sdk广告, 广告类型："+type);
                                callback.onFail();
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, ex.getMessage());
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onFail();
                    }
                })
                .addParam("appId", appId).addParam("posId", posId)
                .addParam("channel", SdkSettings.getInstance().getChannel())
                .addParam("oaid",SdkSettings.getInstance().getOaid())
                .addParam("version", StatusManager.getInstance().getAppStatus().getAppVersion())
                .addParam("did", StatusManager.getInstance().getDeviceStatus().getDid())
                .addParam("type", String.valueOf(type));

        NetClient.getInstance().excute(request, NetClient.Priority.High);
    }

    public interface GdtApiInfoCallback {
        void onSucc(JSONObject data);

        void onFail(int code,String msg);
    }
    public interface GetPhoneCallBack{
        void onSucc(String json);
        void OnFail();
    }
    public interface GetAdCallback {
        void onSuccess(NatiAd natiAd);

        void onFail();
    }

    public interface GetSdkAdsCallback {
        void onApiResponse(List<AdPlatform> adPlatforms);

        void onFail();
    }

    public static abstract class DefGetSdkAdsCallback implements GetSdkAdsCallback {
        public void onFail() {
            //DO NOTHING
        }
    }
}
