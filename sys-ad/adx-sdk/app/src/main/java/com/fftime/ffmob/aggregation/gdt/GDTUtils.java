package com.fftime.ffmob.aggregation.gdt;

import com.fftime.ffmob.aggregation.base.AdSlotSetting;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;

public final class GDTUtils {

    public static ADSize toADSize(AdSlotSetting adSlotSetting) {
        int width = adSlotSetting.getWidth();
        int height = adSlotSetting.getHeight();


        if (width <= 0) width = ADSize.FULL_WIDTH;
        if (height <= 0) height = ADSize.AUTO_HEIGHT;

        return new ADSize(width, height);
    }

    public static VideoOption toVideoOption(AdSlotSetting adSlotSetting) {
        AdSlotSetting.VideoAutoPlayPolicy autoPlayPolicy = adSlotSetting.getVideoAutoPlayPolicy();
        int autoPlayPolicyValue = autoPlayPolicy == AdSlotSetting.VideoAutoPlayPolicy.WIFI ?
                VideoOption.AutoPlayPolicy.WIFI : VideoOption.AutoPlayPolicy.ALWAYS;
        VideoOption videoOption = new VideoOption.Builder().setAutoPlayMuted(adSlotSetting.isVideoAutoPlayMuted()).setAutoPlayPolicy(autoPlayPolicyValue).build();

        return videoOption;
    }
}
