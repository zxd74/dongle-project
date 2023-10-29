package com.fftime.ffmob.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetRequest;
import com.fftime.ffmob.common.network.PlainADNetResponse;
import com.fftime.ffmob.common.network.PlainNetRequest;

import org.json.JSONObject;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;

public final class IpUtils {

    public static String getIp() {
       return null;
    }

    public static final class IpCallback {
        public static String ip;
        public static AtomicBoolean finished = new AtomicBoolean(false);
        public static boolean success = false;
    }

    public static void fetchIp() {
        if (IpCallback.success) {
            return;
        }
        NetClient.getInstance().excute(new PlainNetRequest(Constants.TAOBAO_IP_SERVICE_URL,
                NetRequest.Method.GET, null, new PlainNetRequest.CallBack() {
            @Override
            public void onResponse(PlainNetRequest req, PlainADNetResponse resp) {
                try {
                    String respBody = resp.getContentAsString();
                    if (respBody != null) {
                        JSONObject ipJSON = new JSONObject(respBody);
                        int code = ipJSON.optInt("code", -1);
                        if (code == 0) {
                            IpCallback.ip = ipJSON.optString("ip");
                            IpCallback.success = true;
                        }
                    }
                } catch (Exception ex) {

                } finally {
                    IpCallback.finished.set(true);
                }
            }

            @Override
            public void onError(Exception e) {
                IpCallback.finished.set(true);
            }
        }), NetClient.Priority.High);
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
