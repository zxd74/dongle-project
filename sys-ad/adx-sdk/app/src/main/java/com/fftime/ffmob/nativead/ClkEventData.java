package com.fftime.ffmob.nativead;

import java.io.Serializable;

public class ClkEventData implements Serializable {
    private int downX;
    private int downY;
    private int upX;
    private int upY;

    public int getDownX() {
        return downX;
    }

    public int getDownY() {
        return downY;
    }

    public int getUpX() {
        return upX;
    }

    public int getUpY() {
        return upY;
    }

    @Override
    public String toString() {
        return "ClkEventData{" +
                "downX=" + downX +
                ", downY=" + downY +
                ", upX=" + upX +
                ", upY=" + upY +
                '}';
    }

    private ClkEventData(Builder builder) {
        downX = builder.downX;
        downY = builder.downY;
        upX = builder.upX;
        upY = builder.upY;
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private int downX;
        private int downY;
        private int upX;
        private int upY;

        private Builder() {
        }

        public Builder downX(int val) {
            downX = val;
            return this;
        }

        public Builder downY(int val) {
            downY = val;
            return this;
        }

        public Builder upX(int val) {
            upX = val;
            return this;
        }

        public Builder upY(int val) {
            upY = val;
            return this;
        }

        public ClkEventData build() {
            return new ClkEventData(this);
        }
    }
}
