package com.fftime.ffmob.model;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.fftime.ffmob.common.adservices.ClickService;
import com.fftime.ffmob.common.adservices.DispService;
import com.fftime.ffmob.nativead.ClkEventData;
import com.fftime.ffmob.util.Constants;
import com.fftime.ffmob.util.StringUtil;
import com.fftime.ffmob.video.VideoWindowsAdListener;
import com.fftime.ffmob.video.VideoWindowsAdView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NatiAd extends BaseAd {
    private String name; //名称
    private String title; //标题
    private String icon;  //图标
    private String ctatext; //按钮文字
    private List<String> imgs; //组图
    private String desc; //描述
    private boolean isTouch;
    private long lastClickTime;
    private int dpts;

    public int getDpts() {
        return dpts;
    }

    public void setDpts(int dpts) {
        this.dpts = dpts;
    }

    private VideoWindowsAdView mVideoAdView;
    private final JSONObject ad;
    private final ClkEventData.Builder clkEventData = ClkEventData.newBuilder();

    public VideoWindowsAdView getVideoAdView() {
        return mVideoAdView;
    }

    public void setVideoAdView(VideoWindowsAdView mVideoAdView) {
        this.mVideoAdView = mVideoAdView;
    }
    @Override
    public String getImg() {
        String img = super.getImg();
        if (StringUtil.isEmpty(img)) {
            List<String> imgs = getImgs();
            if (imgs != null && !imgs.isEmpty()) {
                img = imgs.get(0);
            }
        }
        return img;
    }

    public NatiAd() {
        this.ad = null;
    }

    public NatiAd(JSONObject ad) {
        this.ad = ad;
        Log.i("natiAd", ad.toString());
        JSONObject feed = ad.optJSONObject("feed");
        if (feed != null) {
            this.setCtatext(feed.optString("button"));
            this.setDesc(feed.optString("desc"));
            this.setIcon(feed.optString("portrait"));
            this.setName(feed.optString("name"));
            this.setTitle(feed.optString("title"));
            this.setTmid(feed.optString("tmid"));
            this.setVideo(feed.optString("video"));
            this.setDpts(feed.optInt("dpts"));
            List<String> imgs = new ArrayList<>();
            JSONArray imgArray = feed.optJSONArray("imgs");
            if (imgArray != null && imgArray.length() > 0) {
                for (int i = 0; i < imgArray.length(); i++) {
                    imgs.add(imgArray.optString(i));
                }
            }
            this.setImgs(imgs);
        }

        this.setLdp(ad.optString("ldp"));
        this.setImg(ad.optString("img"));
        this.setFallback(ad.optString("fallback"));
        //this.setTmid(ad.optString("tmid"));
        this.setSource(ad.optString("source"));
        this.setMute(ad.optBoolean("mute", true));
        this.setSkipAdTime(ad.optInt("skipAdTime"));
        this.setGdtAd(ad.optBoolean("gdtAd"));

        List<String> exp = new ArrayList<>();
        List<String> clk = new ArrayList<>();
        List<String> dlm = new ArrayList<>();
        List<String> dlcm = new ArrayList<>();
        List<String> dpsm = new ArrayList<>();
        List<String> dpfm = new ArrayList<>();
        List<String> vasm = new ArrayList<>();
        List<String> vcm = new ArrayList<>();

        JSONArray expArray = ad.optJSONArray("exp");
        JSONArray clkArray = ad.optJSONArray("clk");
        JSONArray dlmArray = ad.optJSONArray("dlm");
        JSONArray dlcmArray = ad.optJSONArray("dlcm");
        JSONArray dpsmArray = ad.optJSONArray("dpsm");
        JSONArray dpfmArray = ad.optJSONArray("dpfm");
        JSONArray vasmArray = ad.optJSONArray("vasm");
        JSONArray vcmArray = ad.optJSONArray("vcm");


        if (expArray != null && expArray.length() > 0) {
            for (int i = 0; i < expArray.length(); i++) {
                exp.add(expArray.optString(i));
            }
        }

        if (clkArray != null && clkArray.length() > 0) {
            for (int i = 0; i < clkArray.length(); i++) {
                clk.add(clkArray.optString(i));
            }
        }

        if (dlmArray != null && dlmArray.length() > 0) {
            for (int i = 0; i < dlmArray.length(); i++) {
                dlm.add(dlmArray.optString(i));
            }
        }

        if (dlcmArray != null && dlcmArray.length() > 0) {
            for (int i = 0; i < dlcmArray.length(); i++) {
                dlcm.add(dlcmArray.optString(i));
            }
        }

        if (dpsmArray != null && dpsmArray.length() > 0) {
            for (int i = 0; i < dpsmArray.length(); i++) {
                dpsm.add(dpsmArray.optString(i));
            }
        }

        if (dpfmArray != null && dpfmArray.length() > 0) {
            for (int i = 0; i < dpfmArray.length(); i++) {
                dpfm.add(dpfmArray.optString(i));
            }
        }

        if (vasmArray != null && vasmArray.length() > 0) {
            for (int i = 0; i < vasmArray.length(); i++) {
                vasm.add(vasmArray.optString(i));
            }
        }

        if (vcmArray != null && vcmArray.length() > 0) {
            for (int i = 0; i < vcmArray.length(); i++) {
                vcm.add(vcmArray.optString(i));
            }
        }
        this.setExp(exp);
        this.setClk(clk);
        this.setDlm(dlm);
        this.setDpsm(dpsm);
        this.setDpfm(dpfm);
        this.setDlcm(dlcm);
        this.setVasm(vasm);
        this.setVcm(vcm);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCtatext() {
        return ctatext;
    }

    public void setCtatext(String ctatext) {
        this.ctatext = ctatext;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void clk(Activity activity, View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isTouch  = true;
                        clkEventData.downX((int) event.getX()).downY((int) event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        clkEventData.upX((int) event.getX()).upY((int) event.getY());
                        break;
                }
                return false;
            }
        });
    }

    public void click(Activity activity) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < 1000) {
            return;
        }
        lastClickTime = clickTime;
        ClkEventData d = clkEventData.build();
        if (isTouch) {
            activity.getIntent().putExtra(Constants.INTENT_VAR_CLK_EVENT_DATA,d);
        }
        ClickService.getInstance().dealClick(activity, this.ad);
    }

    public void display() {
        DispService.getInstance().disp(exp);
    }

    @Override
    public String toString() {
        return "NatiAd{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", ctatext='" + ctatext + '\'' +
                ", imgs=" + imgs +
                ", desc='" + desc + '\'' +
                ", ad=" + ad +
                ", ldp='" + ldp + '\'' +
                ", exp=" + exp +
                ", clk=" + clk +
                ", img='" + img + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", fallback='" + fallback + '\'' +
                ", source='" + source + '\'' +
                ", tmid='" + tmid + '\'' +
                '}';
    }
}
