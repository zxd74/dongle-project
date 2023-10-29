package com.fftime.ffmob.common.adservices;

import org.json.JSONObject;

public interface LoadCallback {
  void onSucc(JSONObject data);

  void onError();
}
