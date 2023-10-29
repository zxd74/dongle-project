package com.fftime.ffmob.basead;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;

import com.fftime.ffmob.common.AdSize;
import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.adservices.ClickService;
import com.fftime.ffmob.common.adservices.DispService;
import com.fftime.ffmob.common.adservices.LoadCallback;
import com.fftime.ffmob.common.adservices.LoadRequest;
import com.fftime.ffmob.common.adservices.LoadService;
import com.fftime.ffmob.common.spy.Spy;
import com.fftime.ffmob.common.status.StatusManager;
import com.fftime.ffmob.model.NatiAd;
import com.fftime.ffmob.util.StringUtil;

public  class BaseAD {
  private static final String TAG = "Feeds";
  private static final WeakHashMap<BaseADItem, PrivateFeedsADItem> adsCache =
      new WeakHashMap<BaseADItem, BaseAD.PrivateFeedsADItem>();
  private final String appId;
  private final String posId;

  public BaseAD(Context context, String appId, String posId) {
    StatusManager.getInstance().init(context);
    Spy.work(context);
    this.appId = appId;
    this.posId = posId;
  }

  public void loadAD(int count, final BaseADListener listener) {
    LoadRequest lreq =
        new LoadRequest(appId, posId, AdSize.FEEDS_1000.getWidth(), AdSize.FEEDS_1000.getHeight(),
            count);
    LoadService.getInstance().loadAD(lreq, new LoadCallback() {

      @Override
      public void onSucc(JSONObject data) {
        if (data.optInt("ret") != 0) {
          listener.onFail(BaseADListener.ERROR_RESPONSE, data.optString("msg"));
        } else {
          JSONObject data2, posData;
          if ((data2 = data.optJSONObject("data")) == null
              || (posData = data2.optJSONObject(posId)) == null) {
            listener.onFail(BaseADListener.ERROR_RESPONSE, "广告返回为空");
          } else {
            if (posData.optInt("ret") != 0) {
              listener.onFail(posData.optInt("ret"), posData.optString("msg"));
            } else {
              JSONArray adArray = posData.optJSONArray("list");
              List<BaseADItem> ads = new ArrayList<BaseADItem>();

              for (int i = 0; i < adArray.length(); i++) {
                JSONObject ad = adArray.optJSONObject(i);
                if (ad != null) {
                  PrivateFeedsADItem privateItem =
                      new PrivateFeedsADItem(ad.optString("disurl"), ad.optString("clkurl"), ad);
                  BaseADItem pubItem = new BaseADItem(ad.optString("img"), BaseAD.this);
                  adsCache.put(pubItem, privateItem);
                  if (StringUtil.isEmpty(pubItem.getImg())) {
                    FFTLoger.e(TAG, "Image URL for FeedsAD is empty");
                  } else {
                    ads.add(pubItem);
                  }
                }
              }
              listener.onSucc(ads);
            }
          }
        }
      }

      @Override
      public void onError() {
        listener.onFail(BaseADListener.ERROR_NETWORK, "网络或服务器异常，请检查设备网络");
      }
    });
  }


//  protected abstract void onSUCC(JSONArray adJSONArray);

  boolean disp(BaseADItem adItem) {
    PrivateFeedsADItem pItem = adsCache.get(adItem);
    if (pItem == null) {
      FFTLoger.e(TAG, "曝光Feeds广告失败，只能使用加载广告的Feeds对象上报曝光");
      return false;
    } else {
      DispService.getInstance().disp(pItem.dispUrl);
      return true;
    }
  }

  boolean click(BaseADItem adItem, Activity context) {
    PrivateFeedsADItem pItem = adsCache.get(adItem);
    if (pItem == null) {
      FFTLoger.e(TAG, "Feeds广告点击触发失败，只能使用加载广告的Feeds对象触发点击");
      return false;
    } else {
      ClickService.getInstance().dealClick(context, pItem.adData);
      return true;
    }
  }

  private static class PrivateFeedsADItem {
    private final String dispUrl;
    @SuppressWarnings("unused")
    private final String clkUrl;
    private final JSONObject adData;

    public PrivateFeedsADItem(String dispUrl, String clkUrl, JSONObject adData) {
      super();
      this.dispUrl = dispUrl;
      this.clkUrl = clkUrl;
      this.adData = adData;
    }

  }

}
