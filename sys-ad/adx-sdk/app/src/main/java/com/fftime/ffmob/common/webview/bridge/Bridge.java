package com.fftime.ffmob.common.webview.bridge;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import com.fftime.ffmob.common.FFTLoger;
import com.fftime.ffmob.common.webview.FFTWebview;
import com.fftime.ffmob.common.webview.handlers.FFTHandler;
import com.fftime.ffmob.common.webview.handlers.FFTHandlerManager;
import com.fftime.ffmob.util.StringUtil;

public class Bridge {
    private static final Event EVENT_ONRECEIVE = new Event("onReceived", null);
    private static final String TAG = "Bridge";
    private static final String FFTIME_HOST = "fftime.com";
    private static final String FFTIME_SCHEMA = "fftime";
    private FFTWebview webView;
    private FFTHandlerManager handlerManager;

    public Bridge(FFTWebview webView) {
        this.webView = webView;
        handlerManager = new FFTHandlerManager(webView);
    }

    public void bindHandler(FFTHandler handler) {
        this.handlerManager.addHandler(handler);
    }

    public void bindHandlers(Collection<FFTHandler> handlers) {
        for (FFTHandler handler : handlers) {
            this.bindHandler(handler);
        }
    }

    public void bindHandlers(FFTHandler... handlers) {
        for (FFTHandler handler : handlers) {
            this.bindHandler(handler);
        }
    }

    public boolean exec(String url) {
        try {
            Uri uri = Uri.parse(url);
            Request request = parseFFTRequest(uri);
            if (request != null) {
                handlerManager.exec(request);
                return true;
            } else {
                return false;
            }
        } catch (Throwable e) {
            FFTLoger.w(TAG, "Exception while exec JS request", e);
            return false;
        }
    }

    public void resp(final Message response) {
        this.sendBridgeMsg(response);
    }

    public void fire(final Event event) {
        this.sendBridgeMsg(new EventMsg(event));
    }

    private void sendBridgeMsg(final Message msg) {
        if (msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            try {
                                webView.evaluateJavascript(msg.getEncodeJsResponse(), null);
                            } catch (Exception ex) {
                                webView.loadUrl("javascript:" + msg.getEncodeJsResponse());
                            }
                        } else {
                            webView.loadUrl("javascript:" + msg.getEncodeJsResponse());
                        }
                    } catch (Throwable e) {
                        FFTLoger.w(TAG, "Exception while send JS response", e);
                    }
                }
            });
        }
    }

    public void onReceived() {
        fire(EVENT_ONRECEIVE);
    }

    private Request parseFFTRequest(Uri uri) {
        if (!isFFTMessage(uri)) {
            return null;
        }
        if (uri != null) {
            String service = null;
            if (uri.getPathSegments().size() == 1) {
                service = uri.getPathSegments().get(0);
            }
            String callBackId = uri.getQueryParameter("c");
            String paras = uri.getQueryParameter("q");
            if (!StringUtil.isEmpty(service) && !StringUtil.isEmpty(callBackId)) {
                JSONObject paraObj;
                try {
                    paraObj = StringUtil.isEmpty(paras) ? null : new JSONObject(paras);
                    return new Request(service, callBackId, paraObj);
                } catch (JSONException e) {
                    FFTLoger.w(TAG, "Exception while parse JS Request", e);
                }
            }
        }
        return null;
    }

    private static boolean isFFTMessage(Uri uri) {
        if (uri == null || !uri.isHierarchical()) {
            return false;
        }
        if (!(FFTIME_SCHEMA.equalsIgnoreCase(uri.getScheme()) && FFTIME_HOST.equalsIgnoreCase(uri
                .getHost()))) {
            return false;
        }
        return true;
    }
}
