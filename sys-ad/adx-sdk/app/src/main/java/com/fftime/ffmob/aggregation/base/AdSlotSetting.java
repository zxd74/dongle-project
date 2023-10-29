package com.fftime.ffmob.aggregation.base;


public class AdSlotSetting {
    public enum VideoAutoPlayPolicy {
        WIFI, ALWAYS
    }

    private String appId;
    private String posId;
    private int width;
    private int height;
    private boolean isSupportDeeplink;
    private int adCount;
    private int refreshTime;
    private VideoAutoPlayPolicy videoAutoPlayPolicy=VideoAutoPlayPolicy.WIFI;
    private boolean videoAutoPlayMuted = true;

    public String getAppId() {
        return appId;
    }

    public String getPosId() {
        return posId;
    }

    public boolean isVideoAutoPlayMuted() {
        return videoAutoPlayMuted;
    }

    public int getWidth() {
        return width;
    }

    public VideoAutoPlayPolicy getVideoAutoPlayPolicy() {
        return videoAutoPlayPolicy;
    }

    public int getHeight() {
        return height;
    }

    public boolean isSupportDeeplink() {
        return isSupportDeeplink;
    }

    public int getAdCount() {
        return adCount;
    }

    public int getRefreshTime() {
        return refreshTime;
    }

    private AdSlotSetting(Builder builder) {
        appId = builder.appId;
        posId = builder.posId;
        width = builder.width;
        height = builder.height;
        isSupportDeeplink = builder.isSupportDeeplink;
        adCount = builder.adCount;
        refreshTime = builder.refreshTime;
        videoAutoPlayPolicy = builder.videoAutoPlayPolicy;
        videoAutoPlayMuted = builder.videoAutoPlayMuted;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private String appId;
        private String posId;
        private int width;
        private int height;
        private boolean isSupportDeeplink;
        private int adCount;
        private int refreshTime;
        private VideoAutoPlayPolicy videoAutoPlayPolicy;
        private boolean videoAutoPlayMuted;

        private Builder() {
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder posId(String val) {
            posId = val;
            return this;
        }

        public Builder width(int val) {
            width = val;
            return this;
        }

        public Builder height(int val) {
            height = val;
            return this;
        }

        public Builder isSupportDeeplink(boolean val) {
            isSupportDeeplink = val;
            return this;
        }

        public Builder adCount(int val) {
            adCount = val;
            return this;
        }

        public Builder refreshTime(int val) {
            refreshTime = val;
            return this;
        }

        public Builder videoAutoPlayPolicy(VideoAutoPlayPolicy val) {
            videoAutoPlayPolicy = val;
            return this;
        }

        public Builder videoAutoPlayMuted(boolean val) {
            videoAutoPlayMuted = val;
            return this;
        }

        public AdSlotSetting build() {
            return new AdSlotSetting(this);
        }
    }
}
