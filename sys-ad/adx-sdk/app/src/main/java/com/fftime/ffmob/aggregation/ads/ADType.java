package com.fftime.ffmob.aggregation.ads;

public enum ADType {
    Banner(2), Interstitial(3), Splash(4),
    Native(7), Video(5), NativeVideo(6), NativeExpress(8);

    private int value;

    ADType(int value) {
        this.value = value;
    }

    public static ADType valueOf(int value) {
        switch (value) {
            case 2:
                return Banner;
            case 3:
                return Interstitial;
            case 4:
                return Splash;
            case 5:
                return Video;
            case 6:
                return NativeVideo;
            case 7:
                return Native;
            case 8:
                return NativeExpress;
            default:
                return null;
        }
    }
}
