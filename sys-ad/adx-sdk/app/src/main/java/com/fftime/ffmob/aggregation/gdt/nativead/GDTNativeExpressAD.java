package com.fftime.ffmob.aggregation.gdt.nativead;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.aggregation.ads.NativeExpressAD;
import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.fftime.ffmob.aggregation.base.listener.NativeExpressAdListener;
import com.fftime.ffmob.aggregation.gdt.GDTUtils;
import com.fftime.ffmob.aggregation.model.FFAdError;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class GDTNativeExpressAD implements NativeExpressAD, com.qq.e.ads.nativ.NativeExpressAD.NativeExpressADListener {
    private com.qq.e.ads.nativ.NativeExpressAD nativeExpressAD;
    private NativeExpressAdListener listener;
    private NativeExpressADView nativeExpressADView;
    private ViewGroup adContainer;

    public GDTNativeExpressAD(Activity activity, ViewGroup adContainer, AdSlotSetting adSlotSetting, NativeExpressAdListener listener) {
        nativeExpressAD = new com.qq.e.ads.nativ.NativeExpressAD(activity,
                GDTUtils.toADSize(adSlotSetting), adSlotSetting.getAppId(), adSlotSetting.getPosId(), this);
        nativeExpressAD.setVideoOption(GDTUtils.toVideoOption(adSlotSetting));
        this.listener = listener;
        this.adContainer = adContainer;
    }

    @Override
    public void loadAD() {
        nativeExpressAD.loadAD(1);
    }

    @Override
    public void onNoAD(AdError adError) {
        listener.onAdFailed(new FFAdError(adError.getErrorCode(), adError.getErrorMsg()));
    }

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {
        if (nativeExpressADView != null) nativeExpressADView.destroy();

        nativeExpressADView = list.get(0);
        if (adContainer.getVisibility() != View.VISIBLE) {
            adContainer.setVisibility(View.VISIBLE);
        }
        if (adContainer.getChildCount() > 0) {
            adContainer.removeAllViews();
        }
        adContainer.addView(nativeExpressADView);
        nativeExpressADView.render();
        listener.onAdSuccess();
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {
        listener.onAdFailed(new FFAdError(-1, "render ad error"));
    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {
        listener.onAdExposure();
    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {
        listener.onAdClick();
    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {
    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
    }
}
