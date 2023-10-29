package com.fftime.ffmob.util;

import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.NetClient.Priority;
import com.fftime.ffmob.common.network.NetRequest.Method;
import com.fftime.ffmob.common.network.PlainNetRequest;

public class Ping {
  public static void ping(String url) {
    PlainNetRequest req = new PlainNetRequest(url, Method.GET, null, null);
    NetClient.getInstance().excute(req, Priority.High);
  }
}
