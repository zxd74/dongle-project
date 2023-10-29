package com.fftime.ffmob.common.webview.bridge;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Event {
  private String type;
  private Map<String, Object> param;

  public Event(String type) {
    this(type, null);
  }

  public Event(String type, Map<String, Object> data) {
    this.type = type;
    this.param = data;
  }

  public JSONObject toJSON() {
    Map<String, Object> m = new HashMap<String, Object>();
    m.put("type", type);
    m.put("param", param);
    return new JSONObject(m);
  }
}
