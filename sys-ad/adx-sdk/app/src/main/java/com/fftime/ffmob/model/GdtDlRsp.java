package com.fftime.ffmob.model;

public class GdtDlRsp {
    private int ret;
    private GdtDlInfo data;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public GdtDlInfo getData() {
        return data;
    }

    public void setData(GdtDlInfo data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GdtDlRsp{" +
                "ret=" + ret +
                ", data=" + data +
                '}';
    }
}
